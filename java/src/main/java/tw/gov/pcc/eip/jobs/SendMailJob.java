package tw.gov.pcc.eip.jobs;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import tw.gov.pcc.eip.dao.EipmaildataDao;
import tw.gov.pcc.eip.domain.Eipmaildata;
import tw.gov.pcc.eip.framework.helper.MailHelper;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * 寄信子排程
 *
 * @author swho
 */
@Slf4j
@Setter
public class SendMailJob extends QuartzJob {
    private static final String PGID = "SendMailJob";
    private static final String JOBNAME = "寄送電子郵件";
    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private EipmaildataDao eipmaildataDao;

    private Eipmaildata eipmaildata;
    private String jobId;

    @Override
    protected void handler(JobExecutionContext context) throws JobExecutionException {
        String subject = eipmaildata.getSubject();
        String message = eipmaildata.getMessage();
        String receiver = eipmaildata.getEmail();
        String fileName = eipmaildata.getAttach_file_name();
        try {
            log(jobId, PGID, JOBNAME, "START", "準備寄送 MAIL，主旨為 " + subject, null);
            byte[] fileContent = getFileContent(eipmaildata.getFile_path());
            mailHelper.sendEmail(subject, receiver, "", "", message, fileName, fileContent);
            log(jobId, PGID, JOBNAME, "END", "已寄送 MAIL，主旨為 " + subject, null);
            eipmaildata.setIs_mailed(Eipmaildata.IS_MAILED.PROCESSED.getValue()); // 寄送成功
        } catch (Exception e) {
            handelException(e, subject);
        }
        eipmaildata.setProcess_timestamp(LocalDateTime.now());
        eipmaildataDao.updateByKey(eipmaildata);
    }


    private void handelException(Exception e, String subject) {
        String message;
        int maxLength = Math.min(ExceptionUtility.getStackTrace(e).length(), 500);
        message = subject + " 處理失敗 - {}" + ExceptionUtility.getStackTrace(e).substring(0, maxLength);
        loge(jobId, PGID, JOBNAME, "EXCEPTION", message, null);
        eipmaildata.setIs_mailed(Eipmaildata.IS_MAILED.ERROR.getValue()); // 寄送失敗
        eipmaildata.setReturn_message(message);
    }

    private byte[] getFileContent(String filePath) throws IOException {
        return StringUtils.isNotBlank(filePath) ? FileUtils.readFileToByteArray(Paths.get(FilenameUtils.getFullPath(filePath), FilenameUtils.getName(filePath)).toFile()) : null;
    }

    private void log(String jobno, String jobid, String jobname, String step, String stepinfo, String memo) {
        log.info("寄信排程 {} {} {} {} {} {}", jobno, jobid, jobname, step, stepinfo, memo);
    }

    private void loge(String jobno, String jobid, String jobname, String step, String stepinfo, String memo) {
        log.error("寄信排程錯誤 {} {} {} {} {} {}", jobno, jobid, jobname, step, stepinfo, memo);
    }

    public enum PARAMS {
        eipmaildata,
        jobId
    }
}
