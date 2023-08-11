package tw.gov.pcc.eip.services;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01w050Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;

/**
 * 輿情專區
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w050Service {

    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgdepositDao msgdepositDao;

    /**
     * 取得輿情專區初始資料 (依登入者部門查詢)
     * 
     * @param caseData
     * @param deptId
     */
    public void initQryList(Eip01w050Case caseData, String deptId) {
        caseData.setQryList(msgdataDao.getEip01w050DataList(deptId));
    }

    /**
     * ajax 取得單筆輿情資料內容
     * 
     * @param fseq
     * @return
     */
    public Eip01wPopCase query(String fseq) {
        Eip01wPopCase detail = msgdataDao.getEip01wDetail(fseq, "5");
        detail.setMcontent(StringUtils.replace(detail.getMcontent(), "\r\n", "<br>") );
        if (detail != null) {
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n,
                            LinkedHashMap::new)));
        }
        return detail;
    }
}
