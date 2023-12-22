package tw.gov.pcc.eip.framework.config;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import tw.gov.pcc.eip.jobs.*;

import java.util.Arrays;

/**
 * QUARTZ 排程設定 排程不會每次重啟就重置排程時間，謹首次運行未註冊時會。
 */
@Configuration
public class QuartzConfig {

    public static final String COMMON_GROUP = "CommonGroup";

    /**
     * 執行Procedure排程
     *
     * @return executeProcedureJobDetail
     */
    @Bean
    public JobDetailFactoryBean executeProcedureJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(ExecuteProcedureJob.class);
        jobDetailFactoryBean.setDescription("執行Procedure排程");
        jobDetailFactoryBean.setName("ExecuteProcedureJob");
        jobDetailFactoryBean.setGroup(COMMON_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /**
     * 執行Procedure排程 TRIGGER
     *
     * @param executeProcedureJobDetail 執行Procedure排程 JOB
     * @return spRecompileTrigger
     */
    @Bean
    public CronTriggerFactoryBean spTrigger(JobDetail executeProcedureJobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(executeProcedureJobDetail);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("procedureName", "USP_EIPDAILY");
        jobDataMap.put("procedureParams", Arrays.asList(null, null, null));
        cronTriggerFactoryBean.setJobDataMap(jobDataMap);
        cronTriggerFactoryBean.setCronExpression("0 0 0 ? * *"); // 每日 00:00 時整
        return cronTriggerFactoryBean;
    }


    /**
     * SchedulerFactoryBean
     *
     * @param spTrigger
     * @return scheduler
     */
    @Bean
    public SchedulerFactoryBean quartzScheduler(CronTrigger spTrigger, CronTrigger resetSequenceTrigger, CronTrigger executeImportUsersJobTrigger, CronTrigger updStatusTrigger, CronTrigger sendMailsJobTrigger, CronTrigger updateTableauFilesJobTrigger) {

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setTriggers(spTrigger, resetSequenceTrigger, executeImportUsersJobTrigger, updStatusTrigger, sendMailsJobTrigger, updateTableauFilesJobTrigger);
        schedulerFactory.setOverwriteExistingJobs(false);
        return schedulerFactory;
    }

    /**
     * 重置Sequence CronTriggerFactoryBean
     *
     * @param resetSequenceJobDetail 重置Sequence JOB
     * @return cronTriggerFactoryBean
     */
    @Bean
    public CronTriggerFactoryBean resetSequenceTrigger(JobDetail resetSequenceJobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(resetSequenceJobDetail);
        cronTriggerFactoryBean.setCronExpression("0 0 0 ? * *"); // 每日 00:00 時整
        return cronTriggerFactoryBean;
    }

    /**
     * 重置Sequence JobDetailFactoryBean
     * @return jobDetailFactoryBean
     */
    @Bean
    public JobDetailFactoryBean resetSequenceJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(ResetSequenceJob.class);
        jobDetailFactoryBean.setDescription("重置Sequence");
        jobDetailFactoryBean.setName("ResetSequenceJob");
        jobDetailFactoryBean.setGroup(COMMON_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /**
     * 執行ImportUsersJob排程 TRIGGER
     *
     * @param executeImportUsersJob 執行ImportUsersJob排程 JOB
     * @return executeImportUsersJobTrigger
     */
    @Bean
    public CronTriggerFactoryBean executeImportUsersJobTrigger(JobDetail executeImportUsersJob) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(executeImportUsersJob);
        cronTriggerFactoryBean.setCronExpression("0 30 0 ? * *"); // 每日 00:30 時整
        return cronTriggerFactoryBean;
    }

    /**
     * 執行ImportUsersJob排程
     *
     * @return executeImportUsersJob
     */
    @Bean
    public JobDetailFactoryBean executeImportUsersJob() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(ImportUsersJob.class);
        jobDetailFactoryBean.setDescription("執行ImportUsersJob排程");
        jobDetailFactoryBean.setName("executeImportUsersJob");
        jobDetailFactoryBean.setGroup(COMMON_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /**
     * 修改表單狀態排程
     *
     * @return updStatusJobDetail
     */
    @Bean
    public JobDetailFactoryBean updStatusJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(UpdStatusJob.class);
        jobDetailFactoryBean.setDescription("修改表單狀態排程");
        jobDetailFactoryBean.setName("UpdStatusJob");
        jobDetailFactoryBean.setGroup(COMMON_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /**
     * 修改表單狀態排程 TRIGGER
     *
     * @param updStatusJobDetail 修改表單狀態排程 JOB
     * @return updStatusTrigger
     */
    @Bean
    public CronTriggerFactoryBean updStatusTrigger(JobDetail updStatusJobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(updStatusJobDetail);
        cronTriggerFactoryBean.setCronExpression("0 0/2 * * * ?"); // 每2分鐘執行一次任務
        return cronTriggerFactoryBean;
    }

    /**
     * 執行SendMailsJob排程 TRIGGER
     *
     * @param executeSendMailsJob 執行SendMailsJob排程 JOB
     * @return executeSendMailsJobTrigger
     */
    @Bean
    public CronTriggerFactoryBean sendMailsJobTrigger(JobDetail executeSendMailsJob) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(executeSendMailsJob);
        cronTriggerFactoryBean.setCronExpression("0 * * ? * *"); // 每分鐘
        return cronTriggerFactoryBean;
    }

    /**
     * 執行SendMailsJob排程
     *
     * @return executeSendMailsJob
     */
    @Bean
    public JobDetailFactoryBean executeSendMailsJob() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(SendMailsJob.class);
        jobDetailFactoryBean.setDescription("執行SendMailsJob排程");
        jobDetailFactoryBean.setName("sendMailsJob");
        jobDetailFactoryBean.setGroup(COMMON_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /**
     * 執行UpdateTableauFilesJob排程 TRIGGER
     * @param executeUpdateTableauFilesJob 執行UpdateTableauFilesJob排程 JOB
     * @return executeSendMailsJobTrigger
     */
    @Bean
    public CronTriggerFactoryBean updateTableauFilesJobTrigger(JobDetail executeUpdateTableauFilesJob) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(executeUpdateTableauFilesJob);
//        cronTriggerFactoryBean.setCronExpression("0 * * ? * *"); // 每分鐘執行
		cronTriggerFactoryBean.setCronExpression("0 30 8 ? * *"); // 每天上午八點半
        return cronTriggerFactoryBean;
    }

    /**
     * 執行UpdateTableauFilesJob排程
     * @return executeUpdateTableauFilesJob
     */
    @Bean
    public JobDetailFactoryBean executeUpdateTableauFilesJob() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(UpdateTableauFilesJob.class);
        jobDetailFactoryBean.setDescription("執行UpdateTableauFilesJob排程");
        jobDetailFactoryBean.setName("UpdateTableauFilesJob");
        jobDetailFactoryBean.setGroup(COMMON_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

}