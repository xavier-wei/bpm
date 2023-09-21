package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01w060Case;

/**
 * 單位簡介
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w060Service {
    @Autowired
    private EipcodeDao eipCodeDao;
    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgdepositDao msgdepositDao;

    /**
     * 初始下拉選單
     * 
     * @param caseData
     */
    public void initOptions(Eip01w060Case caseData) {
        // 分眾
        Map<String, String> resultMap = eipCodeDao.getRelevantDepByAttr("6").stream()
                .collect(Collectors.toMap(Eipcode::getCodeno, Eipcode::getCodename));
        caseData.setDepts(resultMap);
    }

    /**
     * 取得文章內容 及附檔資訊
     * 
     * @param attr
     * @return
     */
    public Eip01w060Case getContent(String attr) {
        Eip01w060Case caseData = new Eip01w060Case();
        initOptions(caseData);
        // 部門
        List<Msgdata> result = msgdataDao.getMcontentWithStatus4("6", attr);
        List<String> fseqList = result.stream().map(m -> m.getFseq()).collect(Collectors.toCollection(ArrayList::new));
        List<String> contentList = result.stream().map(m -> m.getMcontent())
                .collect(Collectors.toCollection(ArrayList::new));
        caseData.setMsgs(contentList);

        if (!CollectionUtils.isEmpty(fseqList)) {
            Map<String, String> filesMap = msgdepositDao.findbyfseq(fseqList).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile));
            caseData.setFiles(filesMap);
        }
        return caseData;
    }
}
