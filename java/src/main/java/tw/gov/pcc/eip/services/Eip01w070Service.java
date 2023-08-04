package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01w070Case;

/**
 * 業務資訊
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w070Service {

    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgdepositDao msgdepositDao;

    /**
     * 初始下拉選單
     * 
     * @param caseData
     * @param deptId   登入者部門
     */
    public void initOptions(Eip01w070Case caseData, String deptId) {
        // 分眾
        Map<String, String> resultMap = deptsDao.getRelevantDeptByAttr("7", deptId).stream()
                .collect(Collectors.toMap(Depts::getDept_id, Depts::getDept_name));
        caseData.setDepts(resultMap);
    }

    /**
     * 取得文章內容 及附檔清單
     * 
     * @param contactunit 聯絡單位
     * @param deptId      登入者部門
     * @return
     */
    public Eip01w070Case getContent(String contactunit, String deptId) {
        Eip01w070Case caseData = new Eip01w070Case();
        initOptions(caseData, deptId);
        // 部門
        List<Msgdata> result = msgdataDao.getStatus4Mcontent("7", contactunit);
        List<String> fseqList = result.stream().map(m -> m.getFseq()).collect(Collectors.toCollection(ArrayList::new));
        List<String> contentList = result.stream().map( m -> StringUtils.replace(m.getMcontent(), "\r\n", "<br>"))
                .collect(Collectors.toCollection(ArrayList::new));
        caseData.setMsgs(contentList);

        if (!CollectionUtils.isEmpty(fseqList)) {
            Map<String, String> filesMap = msgdepositDao.findbyfseq(fseqList).stream().collect(
                    Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n, LinkedHashMap::new));
            caseData.setFiles(filesMap);
        }
        caseData.setFseq(result.get(0).getFseq());
        caseData.setSubject(result.get(0).getSubject());
        return caseData;
    }
}
