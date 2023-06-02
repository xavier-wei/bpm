package tw.gov.pcc.eip.framework.helper;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.framework.tag.SystemAlertMessageTag;
import tw.gov.pcc.eip.framework.tag.SystemConfirmMessageTag;
import tw.gov.pcc.eip.framework.tag.SystemMessageTag;
import tw.gov.pcc.common.helper.HttpHelper;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 系統訊息顯示處理
 *
 * @author Goston
 */
public class SystemMessageHelper {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SystemMessageHelper.class);

    public static void setMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                session.removeAttribute(SystemMessageTag.SYSTEM_MESSAGE_SESSION_KEY);
                session.setAttribute(SystemMessageTag.SYSTEM_MESSAGE_SESSION_KEY, message);
            }
        }
    }

    public static void setAlertMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                session.removeAttribute(SystemAlertMessageTag.SYSTEM_ALERT_MESSAGE_SESSION_KEY);
                session.setAttribute(SystemAlertMessageTag.SYSTEM_ALERT_MESSAGE_SESSION_KEY, message);
            }
        }
    }

    public static void addConfirmMessage(String message, String id) {
        if (StringUtils.isNotBlank(message)) {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                @SuppressWarnings("unchecked")
                List<String> confirmList = Optional.ofNullable((List<String>) session.getAttribute(SystemConfirmMessageTag.SYSTEM_CONFIRM_MESSAGE_SESSION_KEY)).orElse(new ArrayList<>());
                confirmList.add(message + SystemConfirmMessageTag.SYSTEM_CONFIRM_MESSAGE_ID_SEPERATOR + StringUtils.defaultString(id));
                session.setAttribute(SystemConfirmMessageTag.SYSTEM_CONFIRM_MESSAGE_SESSION_KEY, confirmList);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean hasConfirms() {
        HttpSession session = HttpHelper.getHttpRequest().getSession();
        List<String> confirmList = new ArrayList<>();
        if (session != null) {
            confirmList = Optional.ofNullable((List<String>) session.getAttribute(SystemConfirmMessageTag.SYSTEM_CONFIRM_MESSAGE_SESSION_KEY)).orElse(new ArrayList<>());
        }
        return !confirmList.isEmpty();
    }

    public static void cleanConfirms() {
        HttpHelper.getHttpRequest().getSession().removeAttribute(SystemConfirmMessageTag.SYSTEM_CONFIRM_MESSAGE_SESSION_KEY);
    }
}
