package tw.gov.pcc.eip.services;

import java.util.Arrays;
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
    public void initOptions(Eip01w060Case caseData, String deptId) {
        // 分眾
        Map<String, String> resultMap = deptsDao.getRelevantDeptByAttr("6", deptId).stream()
                .collect(Collectors.toMap(Depts::getDept_id, Depts::getDept_name, (n, o) -> n, LinkedHashMap::new));
        caseData.setDepts(resultMap);
    }

    /**
     * 取得文章內容 及附檔清單
     * 
     * @param contactunit 聯絡單位
     * @param deptId      登入者部門
     * @return
     */
    public Eip01w060Case getContent(String contactunit, String deptId) {
        Eip01w060Case caseData = new Eip01w060Case();
        initOptions(caseData, deptId);
        // 部門
        List<Msgdata> result = msgdataDao.getStatus4Mcontent("6", contactunit);
        if (!CollectionUtils.isEmpty(result)) {
            Msgdata msgdata = result.get(0);
            caseData.setFseq(msgdata.getFseq());
            caseData.setPagetype(msgdata.getPagetype());
            caseData.setSubject(msgdata.getSubject());
            caseData.setMcontent(StringUtils.replace(msgdata.getMcontent(), "\r\n", "<br>"));
            
            Map<String, String> filesMap = msgdepositDao.findbyfseq(Arrays.asList(msgdata.getFseq())).stream().collect(
                    Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n, LinkedHashMap::new));
            caseData.setFiles(filesMap);
        }
        return caseData;
    }
}
