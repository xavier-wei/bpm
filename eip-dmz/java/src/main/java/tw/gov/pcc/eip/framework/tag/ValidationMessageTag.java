package tw.gov.pcc.eip.framework.tag;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.StringUtility;
import tw.gov.pcc.common.helper.HttpHelper;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TagLib - Validation 訊息顯示
 *
 * @author Goston
 */
public class ValidationMessageTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ValidationMessageTag.class);
    private static final long serialVersionUID = -77428040634207385L;
    public static final String VALIDATION_MESSAGE_SESSION_KEY = "___validationMsg";
    public static final String VALIDATION_ID_SESSION_KEY = "___validationId";
    public static final String VALIDATION_MESSAGE_SEPERATOR = "|||";
    public static final String VALIDATION_MESSAGE_ID_SEPERATOR = "|@|@|";

    @SuppressWarnings("unchecked")
    public int doStartTag() {
        try {
            HttpSession session = HttpHelper.getHttpRequest().getSession();
            if (session != null) {
                List<String> list = (List<String>) session.getAttribute(VALIDATION_MESSAGE_SESSION_KEY);
                session.removeAttribute(VALIDATION_MESSAGE_SESSION_KEY);
                if (list != null && list.size() > 0) {
                    JspWriter out = pageContext.getOut();
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        str.append(list.get(i));
                        if (i != (list.size() - 1)) str.append(VALIDATION_MESSAGE_SEPERATOR);
                    }
                    List<String> idList = new ArrayList<>();
                    if (session.getAttribute(VALIDATION_ID_SESSION_KEY) != null) {
                        idList = (List<String>) ObjectUtils.defaultIfNull(session.getAttribute(VALIDATION_ID_SESSION_KEY), new ArrayList<>()); //20220418, BF收到NPE，所以加上defaultIfNull
                        session.removeAttribute(VALIDATION_ID_SESSION_KEY);
                    }
                    out.print(Encode.forHtml(String.format("%s%s%s", idList.stream().collect(Collectors.joining(",", ",", ",")), VALIDATION_MESSAGE_ID_SEPERATOR, StringUtility.normalizeString(StringUtils.replace(str.toString(), "\r\n", VALIDATION_MESSAGE_SEPERATOR)))));
                }
            }
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: ValidationMessageTag 原因: {}", ExceptionUtility.getStackTrace(e));
        }
        return SKIP_BODY;
    }
}
