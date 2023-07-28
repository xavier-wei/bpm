package tw.gov.pcc.eip.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 重置sequence
 * 
 * @author Ivy
 * @since 2023/07/19
 */
@DisallowConcurrentExecution
public class ResetSequenceJob extends QuartzJob {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ResetSequenceJob.class);
	@Autowired
	private ApplyitemDao applyitemDao;

	@Override
	protected void handler(JobExecutionContext context) throws JobExecutionException {
		log.info("ResetSequenceJob start....");
		try {
			applyitemDao.updateSequence();
		} catch (Exception e) {
			log.error("ResetSequenceJob Exception:}}", ExceptionUtility.getStackTrace(e));
		}
		log.info("ResetSequenceJob end....");
	}
}
