package tw.gov.pcc.eip.framework.config;

import java.util.Arrays;
import java.util.Collections;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import tw.gov.pcc.eip.jobs.ExecuteProcedureJob;
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
		jobDataMap.put("procedureName", "sp_eipdaily");
		jobDataMap.put("procedureParams", Arrays.asList(null, null, null));
		cronTriggerFactoryBean.setJobDataMap(jobDataMap);
		cronTriggerFactoryBean.setCronExpression("0 0 0 ? * *"); // 每日 00:00 時整
		return cronTriggerFactoryBean;
	}


	/**
	 * SchedulerFactoryBean
	 *
	 * @param  spTrigger
	 * @return scheduler
	 */
	@Bean
	public SchedulerFactoryBean scheduler( CronTrigger spTrigger) {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
		schedulerFactory.setTriggers( spTrigger);
//		schedulerFactory.setJobDetails(bebp0w010ReportJobDetail);
		schedulerFactory.setOverwriteExistingJobs(false);
		return schedulerFactory;
	}
}