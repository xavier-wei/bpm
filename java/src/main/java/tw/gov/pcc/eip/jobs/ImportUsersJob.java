package tw.gov.pcc.eip.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import tw.gov.pcc.eip.services.Eip0aw010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 自LDAP匯入帳號，並且對所有資料進行更新(空白欄位才更新)
 *
 * @author swho
 */
@DisallowConcurrentExecution
@Slf4j
public class ImportUsersJob extends QuartzJob {
  @Autowired 
  private Eip0aw010Service eip0aw010Service;

  @Override
  protected void handler(JobExecutionContext context) throws JobExecutionException {
    log.info("ImportUsersJob start....");
    try {
      eip0aw010Service.syncFromLdapAndItr();
    } catch (Exception e) {
      log.error("ImportUsersJob Exception: {}", ExceptionUtility.getStackTrace(e));
    }
    log.info("ImportUsersJob end....");
  }
}
