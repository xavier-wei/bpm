package tw.gov.pcc.eip.jobs;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Osformdata;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.services.MailService;
import tw.gov.pcc.eip.services.OnlineRegService;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@DisallowConcurrentExecution
public class UpdStatusJob extends QuartzJob {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UpdStatusJob.class);

	@Autowired
	private OrformdataDao orformdataDao;
	
	@Autowired
	private OsformdataDao osformdataDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private MailService mailService;

	DateTimeFormatter minguoDateformatter = DateTimeFormatter.ofPattern("yyy/MM/dd(e)")
			.withChronology(MinguoChronology.INSTANCE)
			.withLocale(Locale.TAIWAN);

	@Override
	protected void handler(JobExecutionContext context) throws JobExecutionException {
		try {
			log.debug("開始執行修改表單狀態排程...");
			// 意見調查
			// 取得上架中清單
			List<String> pList = osformdataDao.getListByMultiCondition(null, List.of("P")).stream().map(Osformdata::getOsformno).collect(Collectors.toList());
			// P：上架中 → I：調查中 (系統時間 >= OSFMDT	開始日期時間 and 系統時間 < OSENDT 結束日期時間)
			// I：調查中 → C：已結束 (系統時間 >= OSENDT 結束日期時間)
			osformdataDao.updateStatusBatch();
			// 處理信件寄送
			sendInviteMail(pList);
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

	/**
	 * 取得收件者mail
	 * @return
	 */
	private void sendInviteMail(List<String>pList) {
		// 取得調查中清單
		List<Osformdata> iList = osformdataDao.getListByMultiCondition(null, List.of("I"));
		// 寄送清單
		List<Osformdata> sendList = new ArrayList<>();
		// 取得有異動之調查主題單號
		iList.forEach(i -> {
			log.debug(i.getOsformno());
			if (pList.contains(i.getOsformno())) {
				sendList.add(i);
			}
		});
		for (Osformdata osformdata : sendList) {
			List<String> deptList = Arrays.stream(osformdata.getLimitvote().split(",")).filter(s -> StringUtils.startsWith(s, "D")).map(t -> StringUtils.substring(t, 1)).collect(Collectors.toList());
			List<String> titleList = Arrays.stream(osformdata.getLimitvote().split(",")).filter(s -> !StringUtils.startsWith(s, "D")).collect(Collectors.toList());
			List<Users> usersList = usersDao.getUsersByDeptOrTitle(deptList, titleList);
			replaceMailContent(osformdata);
			for (Users users : usersList) {
				log.debug("發送郵件至:" + users.getEmail());
				mailService.sendEmailNow(osformdata.getMailsubject(), users.getEmail(), osformdata.getMailmsg());
			}
		}
	}

	/**
	 * 取代郵件內容
	 * @param osformdata
	 * @return
	 */
	private void replaceMailContent(Osformdata osformdata) {
		String subject = osformdata.getMailsubject();
		String msg = osformdata.getMailmsg();
		String[] dayoftheweek = {"日","一","二","三","四","五","六"};
		subject = StringUtils.replace(subject, "【主辦單位】", osformdata.getOrganizer());
		subject = StringUtils.replace(subject, "【主題名稱】", osformdata.getTopicname());
		msg = StringUtils.replace(msg, "【主題名稱】", osformdata.getTopicname());
		msg = StringUtils.replace(msg, "【開始日期(星期)】", osformdata.getOsfmdt().format(minguoDateformatter));
		msg = StringUtils.replace(msg, "【結束日期(星期)】", osformdata.getOsendt().format(minguoDateformatter));
		for (int i = 1; i <= 7; i++) {
			if (msg.contains("("+i+")")) {
				msg = StringUtils.replace(msg, "("+i+")", "("+dayoftheweek[i-1]+")");
			}
		}
		osformdata.setMailsubject(subject);
		osformdata.setMailmsg(msg);
	}
}
