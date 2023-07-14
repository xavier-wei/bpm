package tw.gov.pcc.eip.services;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

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
    public void getLatestData(Eip01w030Case caseData) {
        List<Eip01wPopCase> qryList = msgdataDao.getEip01w030LatestDataList().stream().map(m -> {
            Eip01wPopCase dataCase = new Eip01wPopCase();
            dataCase.setFseq(m.getFseq());
            dataCase.setSubject(m.getSubject());
            dataCase.setMsgtype(m.getMsgtype());
            dataCase.setReleasedt(StringUtils.isBlank(m.getReleasedt()) ? ""
                    : DateUtility.changeDateTypeToChineseDate(m.getReleasedt()));
            dataCase.setContactunit(m.getContactunit());
            return dataCase;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setQryList(qryList);
    }

    /**
     * 依畫面條件 查詢的公告事項
     * 
     * @param caseData
     */
    public void query(Eip01w030Case caseData) {
        String msgtype = trimToNull(caseData.getMsgtype());
        String subject = trimToNull(caseData.getTheme());
        List<Eip01wPopCase> qryList = msgdataDao.getEip01w030DataList(msgtype, subject).stream().map(m -> {
            Eip01wPopCase dataCase = new Eip01wPopCase();
            dataCase.setFseq(m.getFseq());
            dataCase.setSubject(m.getSubject());
            dataCase.setMsgtype(m.getMsgtype());
            dataCase.setReleasedt(StringUtils.isBlank(m.getReleasedt()) ? ""
                    : DateUtility.changeDateTypeToChineseDate(m.getReleasedt()));
            dataCase.setContactunit(m.getContactunit());
            return dataCase;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setQryList(qryList);
    }

    /**
     * ajax 取得單筆公告事項文章內容
     * 
     * @param fseq
     * @return
     */
    public Eip01wPopCase query(String fseq) {
        Eip01wPopCase detail = msgdataDao.getEip01w030Detail(fseq);
        if (detail != null) {
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile)));
        }
        return detail;
    }

    /**
     * 公告事項 檔案下載
     * 
     * @param caseData
     * @return
     */
    public ByteArrayOutputStream getFile(Eip01w030Case caseData) {
        File file = null;
        if (!file.exists()) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (FileInputStream inputStream = new FileInputStream(file); outputStream) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error(ExceptionUtility.getStackTrace(e));
            return null;
        }
        return outputStream;
    }
}
