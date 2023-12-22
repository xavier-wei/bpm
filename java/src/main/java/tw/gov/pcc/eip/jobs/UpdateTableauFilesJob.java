package tw.gov.pcc.eip.jobs;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import tw.gov.pcc.eip.tableau.service.UpdateTableauFilesService;
import tw.gov.pcc.eip.util.ExceptionUtility;

@Slf4j
@Setter
@DisallowConcurrentExecution
public class UpdateTableauFilesJob extends QuartzJob {
    @Autowired
    private UpdateTableauFilesService updateTableauFilesService;

    @Override
    protected void handler(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("UpdateTableauFilesJob 處理開始");
            updateTableauFilesService.updateTableauFiles();
        }catch (Exception e){
            log.error("UpdateTableauFilesJob 處理失敗 - {}", ExceptionUtility.getStackTrace(e));
        }
    }
}
