package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.msg.cases.Eip01wFileCase;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 訊息上稿相關 檔案下載
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01wFileService {
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private MsgdepositDao msgdepositDao;

    /**
     * 檔案下載
     * 
     * @param caseData
     * @return
     */
    public ByteArrayOutputStream getFile(Eip01wFileCase caseData) {
        String dir = eipcodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + "\\"; // 檔案所在資料夾
        String seq = caseData.getSeq();
        if (StringUtils.contains(seq, ",")) { // 多檔
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zipStream = new ZipOutputStream(outputStream);
            List<Msgdeposit> files = msgdepositDao.findbyFseqSeq(caseData.getFseq(), Arrays.asList(seq.split(",")));
            int emptyCnt = 0;
            try (outputStream; zipStream) {
                for (Msgdeposit origin : files) {
                    File file = new File(dir + origin.getRealfilename());
                    if (!file.exists()) {
                        emptyCnt++;
                        continue;
                    }
                    ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
                    try (FileInputStream inputStream = new FileInputStream(file); fileOutputStream) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                        if (fileOutputStream != null) {
                            ZipEntry zipEntry = new ZipEntry(origin.getAttachfile());
                            zipStream.putNextEntry(zipEntry);
                            zipStream.write(fileOutputStream.toByteArray());
                            zipStream.closeEntry();
                        }
                    } catch (IOException e) {
                        log.error(origin.getRealfilename() + " 取檔異常: " + ExceptionUtility.getStackTrace(e));
                    }
                }
            } catch (IOException e) {
                log.error(" 壓縮檔產製異常: " + ExceptionUtility.getStackTrace(e));
                return null;
            }
            if (emptyCnt == files.size()) {
                return null;
            }
            caseData.setFilename(caseData.getSubject() + "_" + DateUtility.getNowChineseDate() + ".zip");
            return outputStream;
        } else { // 單檔
            Msgdeposit origin = msgdepositDao.findbyFseqSeq(caseData.getFseq(), Arrays.asList(seq)).get(0);
            if (origin == null) {
                return null;
            }
            File file = new File(dir + origin.getRealfilename()); // 格式化檔名
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
                log.error(origin.getRealfilename() + " 取檔異常: " + ExceptionUtility.getStackTrace(e));
                return null;
            }
            caseData.setFilename(origin.getAttachfile()); // 原始檔名
            return outputStream;
        }
    }
}
