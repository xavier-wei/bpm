package tw.gov.pcc.common.tag.func;

import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.common.util.ExceptionUtil;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLib - 取得目前系統日期時間字串
 *
 * @author Goston
 */
public class NowDateTimeTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NowDateTimeTag.class);
    private static final long serialVersionUID = -1953992288303496670L;
    private boolean chtType = false;

    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            if (chtType) out.print("民國 " + DateUtil.formatNowChineseDateTimeString(chtType));
             else out.print(DateUtil.formatNowChineseDateTimeString(chtType));
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: NowDateTimeTag 原因: {}", ExceptionUtil.getStackTrace(e));
        }
        return SKIP_BODY;
    }

    public void setChtType(final boolean chtType) {
        this.chtType = chtType;
    }
}
