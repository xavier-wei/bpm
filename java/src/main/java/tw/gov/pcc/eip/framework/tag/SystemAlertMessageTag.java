package tw.gov.pcc.eip.framework.tag;

import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.StringUtility;
import tw.gov.pcc.common.helper.HttpHelper;
import static tw.gov.pcc.eip.framework.tag.ValidationMessageTag.VALIDATION_MESSAGE_SEPERATOR;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLib - 系統訊息以 Alert 顯示
 *
 * @author Goston
 */
public class SystemAlertMessageTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SystemAlertMessageTag.class);
    private static final long serialVersionUID = -8214470323566537837L;
    public static final String SYSTEM_ALERT_MESSAGE_SESSION_KEY = "___systemAlertMsg";

    public int doStartTag() {
        try {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                String message = (String) session.getAttribute(SYSTEM_ALERT_MESSAGE_SESSION_KEY);
                session.removeAttribute(SYSTEM_ALERT_MESSAGE_SESSION_KEY);
                JspWriter out = pageContext.getOut();
                out.print(Encode.forHtml(StringUtility.normalizeString(StringUtils.replace(StringUtils.defaultString(message), "\r\n", VALIDATION_MESSAGE_SEPERATOR))));
            }
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: SystemAlertMessageTag 原因: {}", ExceptionUtility.getStackTrace(e));
        }
        return SKIP_BODY;
    }
}
