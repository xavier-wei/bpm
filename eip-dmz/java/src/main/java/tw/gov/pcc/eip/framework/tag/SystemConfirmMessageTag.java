package tw.gov.pcc.eip.framework.tag;

import org.apache.commons.collections.CollectionUtils;
import org.owasp.encoder.Encode;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.StringUtility;
import tw.gov.pcc.common.helper.HttpHelper;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * TagLib - 系統訊息以 Confirm 顯示
 */
public class SystemConfirmMessageTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SystemConfirmMessageTag.class);
    public static final String SYSTEM_CONFIRM_MESSAGE_SESSION_KEY = "___systemConfirmMsg";
    public static final String SYSTEM_CONFIRM_MESSAGE_ID_SEPERATOR = "|@|@|";
    public static final String SYSTEM_CONFIRM_MESSAGE_SEPERATOR = "|||";
    private static final long serialVersionUID = -1;

    public int doStartTag() {
        try {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                @SuppressWarnings("unchecked")
                List<String> confirmList = (List<String>) session.getAttribute(SYSTEM_CONFIRM_MESSAGE_SESSION_KEY);
                session.removeAttribute(SYSTEM_CONFIRM_MESSAGE_SESSION_KEY);
                if (CollectionUtils.isEmpty(confirmList)) {
                    return SKIP_BODY;
                }
                JspWriter out = pageContext.getOut();
                String confirmListString = String.join(SYSTEM_CONFIRM_MESSAGE_SEPERATOR, confirmList);
                out.print(Encode.forHtml(StringUtility.normalizeString(confirmListString)));
            }
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: SystemConfirmMessageTag 原因: {}", ExceptionUtility.getStackTrace(e));
        }
        return SKIP_BODY;
    }
}
