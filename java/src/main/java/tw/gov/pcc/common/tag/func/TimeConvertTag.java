package tw.gov.pcc.common.tag.func;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.HtmlUtils;

import tw.gov.pcc.common.util.ExceptionUtil;

public class TimeConvertTag extends TagSupport {
	
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TimeConvertTag.class);
    private static final long serialVersionUID = -5176664237145052068L;
    private Object value;

    public int doStartTag() {
    	if (value == null && value.toString().length()<4) {
    		return SKIP_BODY;
    	}

    	try {  		
        	String str = value.toString().substring(0,2)+":"+value.toString().substring(2,4);
    		pageContext.getOut().write(HtmlUtils.htmlEscape(str));

        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: NowDateTimeTag 原因: {}", ExceptionUtil.getStackTrace(e));
        }
        return SKIP_BODY;
    }
    
	public void setValue(final Object value) {
		this.value = value;
	}

}