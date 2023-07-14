package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01w050Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.util.ExceptionUtility;

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
        caseData.setQryList(msgdataDao.getEip01w050DataList(deptId)); // TODO deptId
    }

    /**
     * ajax 取得單筆輿情資料內容
     * 
     * @param fseq
     * @return
     */
    public Eip01wPopCase query(String fseq) {
        Eip01wPopCase detail = msgdataDao.getEip01w050Detail(fseq);
        if (detail != null) {
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile)));
        }
        return detail;
    }

    /**
     * 輿情專區 檔案下載
     * 
     * @param caseData
     * @return
     */
    public ByteArrayOutputStream getFile(Eip01w050Case caseData) {
        File file = null; // TODO file
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
