package tw.gov.pcc.common.tag.func;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.HtmlUtils;

import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.util.ObjectUtility;
import tw.gov.pcc.eip.util.StringUtility;

/**
 * 畫面代碼顯示：以UserId撈username
 */
public class UsernameTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String userid;

    @Override
    public int doStartTag() {
        try {
        	UsersDao usersDao = SpringHelper.getBeanByClass(UsersDao.class);
        	Users userData = usersDao.selectByKey(userid);
            if (userData!=null) {
                pageContext.getOut().write(ObjectUtility.normalizeObject(HtmlUtils.htmlEscape(userData.getUser_name())));
            }else {
            	pageContext.getOut().write(StringUtility.normalizeString(HtmlUtils.htmlEscape(userid)));
            }
            return SKIP_BODY;
        } catch (final java.lang.Throwable $ex) {
            throw new RuntimeException($ex);
        }
    }
    
    public void setUserid(final String userid) {
        this.userid = userid;
    }
}