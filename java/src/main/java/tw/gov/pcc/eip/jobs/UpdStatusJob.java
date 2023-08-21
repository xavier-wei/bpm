package tw.gov.pcc.eip.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.util.ExceptionUtility;

@DisallowConcurrentExecution
public class UpdStatusJob extends QuartzJob {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UpdStatusJob.class);

	@Autowired
	private OrformdataDao orformdataDao;
	
	@Autowired
	private OsformdataDao osformdataDao;

	@Override
	protected void handler(JobExecutionContext context) throws JobExecutionException {
		try {
			log.debug("開始執行修改表單狀態排程...");
			// 意見調查
			// P：上架中 → I：調查中 (系統時間 >= OSFMDT	開始日期時間 and 系統時間 < OSENDT 結束日期時間)
			// I：調查中 → C：已結束 (系統時間 >= OSENDT 結束日期時間)
			osformdataDao.updateStatusBatch();
			
			// 線上報名
			// P：上架中 → A：報名中 (系統時間 >= REGISFMDT 報名開始時間 and 系統時間 < REGISENDT 報名結束時間)
			// A：報名中 → I：進行中 (系統時間 >= PROFMDT 辦理開始時間 and 系統時間 < PROENDT 辦理結束時間)
			// I：進行中 → C：已結束 (系統時間 >= PROENDT 辦理結束時間)
			orformdataDao.updateStatusBatch();
			log.debug("完成執行修改表單狀態排程!");
		} catch (Exception e) {
			log.error("JOB 處理失敗 - " + ExceptionUtility.getStackTrace(e));
			//TODO
//			insertLog("EXCEPTION", "JOB 處理失敗", ExceptionUtility.getStackTrace(e));
		}

	}

}
