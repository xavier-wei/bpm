package tw.gov.pcc.common.tag.acl;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.util.ExceptionUtil;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLib - 取得螢幕編號
 *
 * @author Goston
 */
public class GetProgIdTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GetProgIdTag.class);
    private static final long serialVersionUID = -157502058655084681L;

    public int doStartTag() {
        try {
            String progId = StringUtils.defaultString((String) pageContext.getAttribute("_page_progId"));
            JspWriter out = pageContext.getOut();
            if (progId.equals("")) {
                log.error("Taglib 發生錯誤, Tag: GetProgIdTag 原因: progId 未定義");
                out.print("<font color=\'red\'>Undefined progId</font>");
            } else {
                out.print(progId);
            }
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: GetProgIdTag 原因: {}", ExceptionUtil.getStackTrace(e));
        }
        return SKIP_BODY;
    }
}
