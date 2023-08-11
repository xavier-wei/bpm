package tw.gov.pcc.eip.services;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;

/**
 * 公告事項
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w030Service {

    @Autowired
    private EipcodeDao eipCodeDao;
    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgdepositDao msgdepositDao;

    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm")
            .withChronology(MinguoChronology.INSTANCE).withLocale(Locale.TAIWAN);

    /**
     * 初始下拉選單
     * 
     * @param caseData
     */
    public void initOptions(Eip01w030Case caseData) {
        // 分類名稱 訊息類別
        List<Eip01w030Case.Option> msgtypes = eipCodeDao.findByCodekindScodekindOrderByCodeno("MESSAGETYPE", "1")
                .stream().sorted(Comparator.comparing(Eipcode::getCodeno)).map(m -> {
                    Eip01w030Case.Option option = new Eip01w030Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setOptList(msgtypes);
    }

    /**
     * 初始畫面 最新的公告事項
     * 
     * @param caseData
     */
    public void getLatestData(Eip01w030Case caseData, String deptId) {
        List<Eip01wPopCase> qryList = msgdataDao.getEip01w030DataList(deptId, null, null);
        caseData.setQryList(qryList);
    }

    /**
     * 依畫面條件 查詢的公告事項
     * 
     * @param caseData
     */
    public void query(Eip01w030Case caseData, String deptId) {
        String msgtype = trimToNull(caseData.getMsgtype());
        String subject = trimToNull(caseData.getTheme());
        List<Eip01wPopCase> qryList = msgdataDao.getEip01w030DataList(deptId, msgtype, subject);
        caseData.setQryList(qryList);
    }

    /**
     * ajax 取得單筆公告事項文章內容
     * 
     * @param fseq
     * @return
     */
    public Eip01wPopCase query(String fseq) {
        Eip01wPopCase detail = msgdataDao.getEip01wDetail(fseq, "1");
        if (detail != null) {
            detail.setMcontent(StringUtils.replace(detail.getMcontent(), "\r\n", "<br>"));
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n,
                            LinkedHashMap::new)));
        }
        return detail;
    }

}
