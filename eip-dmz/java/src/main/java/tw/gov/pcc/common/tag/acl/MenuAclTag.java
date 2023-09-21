package tw.gov.pcc.common.tag.acl;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.util.ExceptionUtil;
import tw.gov.pcc.common.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLib - 選單權限控管
 *
 * @author Goston
 */
public class MenuAclTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MenuAclTag.class);
    private static final long serialVersionUID = -1267194287820429753L;
    private String itemId;

    public int doStartTag() {
        try {
            FrameworkUserInfoBean userData = UserSessionHelper.getFrameworkUserData((HttpServletRequest) pageContext.getRequest());
            if (StringUtils.isBlank(itemId) || userData == null) {
                return SKIP_BODY;
            }
            String[] itemIds = StrUtil.splitTrim(StringUtils.upperCase(itemId), ",");
            for (String id : itemIds) {
                if (userData.hasPrivilege(id)) return EVAL_BODY_INCLUDE;
            }
            return SKIP_BODY;
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: MenuAclTag 原因: {}", ExceptionUtil.getStackTrace(e));
            return SKIP_BODY;
        }
    }

    public int doEndTag() throws JspException {
        try {
            return EVAL_PAGE;
        } catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: MenuAclTag 原因: {}", ExceptionUtil.getStackTrace(e));
            return EVAL_PAGE;
        }
    }

    public void setItemId(final String itemId) {
        this.itemId = itemId;
    }
}
