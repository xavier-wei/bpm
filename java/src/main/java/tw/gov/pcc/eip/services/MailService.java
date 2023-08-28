package tw.gov.pcc.eip.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipmaildataDao;
import tw.gov.pcc.eip.domain.Eipmaildata;
import tw.gov.pcc.eip.framework.helper.ScheduleHelper;
import tw.gov.pcc.eip.jobs.SendMailJob;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.util.Map;


/**
 * MailService - 提供郵件相關的服務功能。
 * 用於發送各種郵件，包括內容和附件。
 * 使用方式範例：
 * <pre>{@code
 * mailService.sendEmailNow("測試文字","test@ws2016","測試<br>純內容");
 * mailService.sendEmailNow("測試檔案1","test@ws2016","測試<br>內容 附件指定檔名","login.jpg","\\a\\login.jpg");
 * mailService.sendEmailNow("測試檔案2","test@ws2016","測試<br>內容 附件使用原檔名",null,"\\a\\login.jpg");
 * mailService.sendEmail("測試檔案3，只寫資料，共用系統排程送出(大量寄發使用)","test@ws2016","測試<br>內容 附件指定檔名","login.jpg","\\a\\login.jpg");
 * }</pre
 *
 * @author swho
 */
@Slf4j
@Service
public class MailService {
    private static final String USERID = "99999";

    @Autowired
    private EipmaildataDao eipmaildataDao;

    /**
     * 新增紀錄，並新增排程寄發 Email(文字訊息)
     *
     * @param subject Mail 標題 (必填)
     * @param email   收件人，如有多個收件人以「,」隔開 (必填)
     * @param message Mail 訊息內容
     */
    public void sendEmailNow(final String subject, final String email, final String message) {
        sendEmail(true, subject, email, message, "", "");
    }

    /**
     * 新增紀錄，並新增排程寄發 Email(文字訊息或文字和附檔)
     *
     * @param subject        Mail 標題 (必填)
     * @param email          收件人，如有多個收件人以「,」隔開 (必填)
     * @param message        Mail 訊息內容
     * @param attachFileName 附件檔案名稱 (選填，<code>null</code>但有filePath就取實際的檔名)
     * @param filePath       附件檔實體路徑 (選填，沒有檔案就<code>null</code>，會忽略fileName，不包含附件)
     */
    public void sendEmailNow(final String subject, final String email, final String message, final String attachFileName, String filePath) {
        sendEmail(true, subject, email, message, attachFileName, filePath);
    }

    /**
     * 新增紀錄，同系統排程一起寄發 Email(文字訊息或文字和附檔)
     *
     * @param subject Mail 標題 (必填)
     * @param email   收件人，如有多個收件人以「,」隔開 (必填)
     * @param message Mail 訊息內容
     */
    public void sendEmail(final String subject, final String email, final String message, final String attachFileName, String filePath) {
        sendEmail(false, subject, email, message, attachFileName, filePath);
    }

    /**
     * 新增紀錄，可新增排程立刻寄發 Email(文字訊息或文字和附檔)
     *
     * @param isNow          是否立即寄發
     * @param subject        Mail 標題 (必填)
     * @param email          收件人，如有多個收件人以「,」隔開 (必填)
     * @param message        Mail 訊息內容
     * @param attachFileName 附件檔案名稱 (選填，<code>null</code>但有filePath就取實際的檔名)
     * @param filePath       附件檔實體路徑 (選填，沒有檔案就<code>null</code>，會忽略fileName，不包含附件)
     */
    private void sendEmail(final boolean isNow, final String subject, final String email, final String message, final String attachFileName, String filePath) {
        Eipmaildata eipmaildata = Eipmaildata.builder()
                .mail_id(eipmaildataDao.getMail_id())
                .subject(subject)
                .email(email)
                .message(message)
                .attach_file_name(StringUtils.defaultIfEmpty(attachFileName, FilenameUtils.getName(filePath)))
                .file_path(filePath)
                .is_mailed(isNow ? Eipmaildata.IS_MAILED.ONLINE.getValue() : Eipmaildata.IS_MAILED.UNPROCESSED.getValue())
                .build();
        eipmaildataDao.insert(eipmaildata);
        if (isNow) {
            ScheduleHelper.scheduleNowWithObjectMap(SendMailJob.class, Map.of(SendMailJob.PARAMS.eipmaildata.name(), eipmaildata, SendMailJob.PARAMS.jobId.name(), StringUtils.EMPTY), USERID);
        }
    }


    /**
     * 寄發排程寄發 Email
     */
    public void sendBatchEmail() {
        eipmaildataDao.getListByIsMailedIsNull().forEach(eipmaildata -> {
            try {
                ScheduleHelper.scheduleNowWithObjectMap(SendMailJob.class, Map.of(SendMailJob.PARAMS.eipmaildata.name(), eipmaildata, SendMailJob.PARAMS.jobId.name(), StringUtils.EMPTY), USERID);
            } catch (Exception e) {
                log.error("Batch_No: " + eipmaildata.getBatch_no() + " EmailService.sendBatchEmail() 發生Exception:"
                        + ExceptionUtility.getStackTrace(e));
            }
        });
    }

}
