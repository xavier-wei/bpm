package tw.gov.pcc.eip.jobs;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import tw.gov.pcc.eip.services.MailService;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 處理 Bemaildata 批次信件
 *
 * @author tunhao.hsu
 */
@Slf4j
@Setter
@DisallowConcurrentExecution
public class SendMailsJob extends QuartzJob {

    @Autowired
    private MailService mailService;

    @Override
    protected void handler(JobExecutionContext context) throws JobExecutionException {
        try {
            mailService.sendBatchEmail();
        } catch (Exception e) {
            log.error("SendMailsJob 處理失敗 - {}", ExceptionUtility.getStackTrace(e));
        }
    }


}
