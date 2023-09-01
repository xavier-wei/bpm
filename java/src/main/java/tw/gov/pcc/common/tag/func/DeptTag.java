package tw.gov.pcc.common.tag.func;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.HtmlUtils;

import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.util.ObjectUtility;
import tw.gov.pcc.eip.util.StringUtility;

/**
 * 畫面代碼顯示：以Deptid撈deptname
 */
public class DeptTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String deptid;

    @Override
    public int doStartTag() {
        try {
        	DeptsDao deptsDao = SpringHelper.getBeanByClass(DeptsDao.class);
        	Depts deptData = deptsDao.findByPk(deptid);
            if (deptData!=null) {
                pageContext.getOut().write(ObjectUtility.normalizeObject(HtmlUtils.htmlEscape(deptData.getDept_name())));
            }else {
            	pageContext.getOut().write(StringUtility.normalizeString(HtmlUtils.htmlEscape(deptid)));
            }
            return SKIP_BODY;
        } catch (final java.lang.Throwable $ex) {
            throw new RuntimeException($ex);
        }
    }
    
    public void setDeptid(final String deptid) {
        this.deptid = deptid;
    }
}