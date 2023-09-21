package tw.gov.pcc.common.listener;

import tw.gov.pcc.common.ConstantKey;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.domain.PortalLog;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.services.LoggingService;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Timestamp;

/**
 * Framework HttpSession Listener For Logout Log
 *
 * @author Goston
 */
public class FrameworkHttpSessionListener implements HttpSessionListener {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FrameworkHttpSessionListener.class);

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            FrameworkUserInfoBean userData = (FrameworkUserInfoBean) se.getSession().getAttribute(UserSessionHelper.FRAMEWORK_USER_INFO);
            if (userData != null) {
                // Portal Log - Logout Log
                // [
                if (EnvFacadeHelper.isLoggingEnabled()) {
                    LoggingService loggingService = (LoggingService) SpringHelper.getBeanById(ConstantKey.LOGGING_SERVICE_ID);
                    Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
                    String now = DateUtil.parseTimestampToWestDateTime(date, true);
                    PortalLog logData = new PortalLog();
                    logData.setLogDateTime(now); // 紀錄時間
                    logData.setUserId(userData.getUserId()); // 用戶代號
                    logData.setUserIP(userData.getLoginIP()); // 用戶 IP 位址
                    logData.setUserAction("Logout"); // 用戶執行動作
                    logData.setApCode(EnvFacadeHelper.getSystemId()); // 應用系統代號
                    logData.setApName(EnvFacadeHelper.getSystemName()); // 應用系統名稱
                    logData.setApFunctionCode("LOGOUT"); // 應用系統功能代號
                    logData.setApFunctionName("LOGOUT"); // 應用系統功能名稱
                    logData.setApUrl(""); // 應用系統網址
                    logData.setLogDescript("使用者登出系統"); // 說明
                    logData.setDateTime(date); // 系統時間
                    logData.setToken(userData.getToken()); // 檢查資訊
                    loggingService.loggingPortalLog(logData);
                }
            }
        } catch (
        // ]
        Exception e) {
            FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.PORTALLOG, e);
        }
    }
}
