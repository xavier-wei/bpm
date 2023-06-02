package tw.gov.pcc.eip.framework.helper;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import tw.gov.pcc.eip.Global;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.common.helper.SpringHelper;

/**
 * Quartz 排程
 *
 * @author Goston
 */
public class ScheduleHelper {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ScheduleHelper.class);
    private static final long SCHEDULE_DELAY = 5000L;

    /**
     * 立即排程
     *
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param userId   使用者代碼
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleNow(Class<? extends Job> jobClass, Map<String, String> dataMap, String userId) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String jobId = StringUtils.defaultString(userId) + now + "J";
            String triggerId = StringUtils.defaultString(userId) + now + "T";
            // String groupId = jobClass.getSimpleName();
            long startTime = System.currentTimeMillis() + SCHEDULE_DELAY;
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).startAt(new Date(startTime)).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById(Global.SCHEDULER_ID);
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("立即排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

    /**
     * 以指定的 jobId 當作 Job ID 立即排程
     *
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param jobId    Job ID (必填，請勿以 <code>J</code> 結尾)
     * @param userId   使用者代碼
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleNowWithJobId(Class<? extends Job> jobClass, Map<String, String> dataMap, String jobId, String userId) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String triggerId = jobId + "T";
            // String groupId = jobClass.getSimpleName();
            long startTime = System.currentTimeMillis() + SCHEDULE_DELAY;
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).startAt(new Date(startTime)).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById(Global.SCHEDULER_ID);
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("立即排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

    /**
     * 於指定時間進行排程工作
     *
     * @param dateTime 排程時間 年 月 日 時 分 （注意不含秒，可傳西元或民國）
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param userId   使用者代碼
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleAtMoment(String dateTime, Class<? extends Job> jobClass, Map<String, String> dataMap, String userId) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String jobId = StringUtils.defaultString(userId) + now + "J";
            String triggerId = StringUtils.defaultString(userId) + now + "T";
            // String groupId = jobClass.getSimpleName();
            String scheduleDateTime = dateTime;
            if (StringUtils.length(scheduleDateTime) == 11) scheduleDateTime = DateUtility.changeDateType(StringUtils.substring(scheduleDateTime, 0, 7)) + StringUtils.substring(scheduleDateTime, 7, 11);
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.TAIWAN);
            Date tm = df.parse(scheduleDateTime + "01");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tm);
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).startAt(calendar.getTime()).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById(Global.SCHEDULER_ID);
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("定時排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

    /**
     * 以指定的 jobId 當作 Job ID 於指定時間進行排程工作
     *
     * @param dateTime 排程時間 年 月 日 時 分 （注意不含秒，可傳西元或民國）
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param jobId    Job ID (必填，請勿以 <code>J</code> 結尾)
     * @param userId   使用者代碼
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleAtMomentWithJobId(String dateTime, Class<? extends Job> jobClass, Map<String, String> dataMap, String jobId, String userId) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String triggerId = jobId + "T";
            // String groupId = jobClass.getSimpleName();
            String scheduleDateTime = dateTime;
            if (StringUtils.length(scheduleDateTime) == 11) scheduleDateTime = DateUtility.changeDateType(StringUtils.substring(scheduleDateTime, 0, 7)) + StringUtils.substring(scheduleDateTime, 7, 11);
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.TAIWAN);
            Date tm = df.parse(scheduleDateTime + "01");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tm);
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).startAt(calendar.getTime()).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById(Global.SCHEDULER_ID);
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("定時排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

    /**
     * 使用 crontab 表示式排程
     *
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param userId   使用者代碼
     * @param cronExp  crontab 描述
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleWitbCron(Class<? extends Job> jobClass, Map<String, String> dataMap, String userId, String cronExp) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String jobId = StringUtils.defaultString(userId) + now + "J";
            String triggerId = StringUtils.defaultString(userId) + now + "T";
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).withSchedule(cronSchedule(cronExp)).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById("quartzScheduler");
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("Cron Job 排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

    /**
     * 以指定的 jobId 當作 Job ID 使用 crontab 表示式排程
     *
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param jobId    Job ID (必填，請勿以 <code>J</code> 結尾)
     * @param userId   使用者代碼
     * @param cronExp  crontab 描述
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleWitbCronWithJobId(Class<? extends Job> jobClass, Map<String, String> dataMap, String jobId, String userId, String cronExp) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String triggerId = jobId + "T";
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).withSchedule(cronSchedule(cronExp)).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById("quartzScheduler");
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("Cron Job 排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

    /**
     * 取消排程
     *
     * @param jobId  Job ID
     * @param userId 使用者代碼
     * @return 成功回傳 true，失敗回傳 false
     */
    public static boolean unscheduleJob(String jobId, String userId) {
        try {
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById(Global.SCHEDULER_ID);
            TriggerKey key;
            if (StringUtils.endsWith(jobId, "J")) // 表示排程時「沒有」自行指定 Job ID
             key = new TriggerKey(StringUtils.substring(jobId, 0, StringUtils.length(jobId) - 1) + "T");
             else  // 表示排程時「有」自行指定 Job ID
            key = new TriggerKey(jobId + "T");
            quartzScheduler.unscheduleJob(key);
            // BatchLogHelper.cancelSchedule(jobId, userId);
            return true;
        } catch (Exception e) {
            log.error("取消排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return false;
        }
    }

    /**
     * 立即排程
     *
     * @param jobClass 排程的 Job （e.g.: Job1.class）
     * @param dataMap  JobDataMap （沒有則傳入 null）
     * @param userId   使用者代碼
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleNowWithObjectMap(Class<? extends Job> jobClass, Map<String, Object> dataMap, String userId) {
        try {
            if (StringUtils.isBlank(userId)) {
                log.info("使用者代碼不得為空白");
                return null;
            }
            String now = DateUtility.getNowWestDateTimeMs();
            String jobId = StringUtils.defaultString(userId) + now + "J";
            String triggerId = StringUtils.defaultString(userId) + now + "T";
            // String groupId = jobClass.getSimpleName();
            long startTime = System.currentTimeMillis() + SCHEDULE_DELAY;
            JobDetail jobDetail = newJob(jobClass).withIdentity(jobId).build();
            if (dataMap != null) {
                jobDetail.getJobDataMap().putAll(dataMap);
            }
            Trigger trigger = newTrigger().withIdentity(triggerId).startAt(new Date(startTime)).build();
            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById(Global.SCHEDULER_ID);
            quartzScheduler.scheduleJob(jobDetail, trigger);
            // BatchLogHelper.addSchedule(jobId, userId, now);
            return jobId;
        } catch (Exception e) {
            log.error("立即排程失敗: {}", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }
}
