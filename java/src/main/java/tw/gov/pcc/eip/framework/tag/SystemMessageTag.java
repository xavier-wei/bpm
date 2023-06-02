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
 * TagLib - 系統訊息顯示
 *
 * @author Goston
 */
public class SystemMessageTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SystemMessageTag.class);
    private static final long serialVersionUID = 7544468996454850378L;
    public static final String SYSTEM_MESSAGE_SESSION_KEY = "___systemMsg";

    public int doStartTag() {
        try {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                String message = (String) session.getAttribute(SYSTEM_MESSAGE_SESSION_KEY);
                session.removeAttribute(SYSTEM_MESSAGE_SESSION_KEY);
                JspWriter out = pageContext.getOut();
                out.print(Encode.forHtml(StringUtility.normalizeString(StringUtils.replace(org.apache.commons.lang3.StringUtils.defaultString(message), "\r\n", VALIDATION_MESSAGE_SEPERATOR))));
            }
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: SystemMessageTag 原因: {}", ExceptionUtility.getStackTrace(e));
        }
        return SKIP_BODY;
    }
}
