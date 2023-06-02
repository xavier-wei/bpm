package tw.gov.pcc.common.tag.acl;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.util.ExceptionUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLib - 設定螢幕編號
 *
 * @author Goston
 */
public class SetProgIdTag extends TagSupport {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SetProgIdTag.class);
    private static final long serialVersionUID = -5010044025742034004L;
    private String progId;

    public int doStartTag() {
        try {
            // 不檢查JSP寫死的progId參數
//            if (!EnvFacadeHelper.isValidProgId(progId) && EnvFacadeHelper.isUndefineUrlControl()) {
//                 頁面程式代號 - 螢幕編號 未定義 且 控管未定義之 Url
//                log.error("Taglib 發生錯誤, Tag: SetProgIdTag 原因: progId 錯誤...");
//            }
//            else {
            pageContext.setAttribute("_page_progId", StringUtils.upperCase(progId));
            // 更新使用者物件中目前執行的功能的 頁面程式代號 - 螢幕編號
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            if (request != null) {
                FrameworkUserInfoBean userData = UserSessionHelper.getFrameworkUserData(request);
                if (userData != null) {
                    userData.setProgId(StringUtils.upperCase(progId));
                }
            }
        } catch (
//            }
        Exception e) {
            log.error("Taglib 發生錯誤, Tag: SetProgIdTag 原因: {}", ExceptionUtil.getStackTrace(e));
        }
        return SKIP_BODY;
    }

    public void setProgId(final String progId) {
        this.progId = progId;
    }
}
