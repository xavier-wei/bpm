package tw.gov.pcc.common.tag.func;

import tw.gov.pcc.eip.util.StringUtility;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.lang.management.ManagementFactory;

/**
 * TagLib - 主機資訊
 *
 * @author Goston
 */
public class ServerInfoTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerInfoTag.class);
    private static final long serialVersionUID = 759594226069183493L;

    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.print(StringUtility.normalizeString("PID: " + ManagementFactory.getRuntimeMXBean().getName()));
        } catch (Exception e) {
            log.error("ServerInfoTag.doStartTag() 發生錯誤");
        }
        return SKIP_BODY;
    }
}
