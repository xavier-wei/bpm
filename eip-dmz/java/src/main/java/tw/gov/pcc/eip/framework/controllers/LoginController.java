package tw.gov.pcc.eip.framework.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.services.LoginService;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 使用者登入
 * 顯示首頁相關資料
 *
 * @author swho
 */
@Controller
@AllArgsConstructor
@Slf4j
public class LoginController extends BaseController {
    public static final String CASE_KEY = "_login_caseData";
    private static final String INDEX_PAGE = "/index";
    private static final String ERROR = "/unauthorized";
    private static final String LOGOUT_PAGE = "/logout";
    private static final String MSG_AUTO_LOGIN = "msg.framework.autoReLogin";
    private final LoginService loginService;
    private final UserBean userData;


    @RequestMapping({"/Login.action", "/"})
    public String login(HttpServletRequest request) {
        try {
            if (loginService.getUserLoginData(userData, request)) {
                // 取得 登入日期 及 登入時間
                String loginDateTime = DateUtility.getNowChineseDateTime(false);
                userData.setLoginDate(DateUtility.splitChineseDateTime(loginDateTime, true));
                userData.setLoginTime(DateUtility.splitChineseDateTime(loginDateTime, false));
                // 將使用者資料存入 Session
                UserSessionHelper.setUserData(request, userData);
                // 如果是 自動重新登入, 則於訊息區顯示自動重新登入訊息

                String reLogin = request.getParameter("reLogin");
                if (StringUtils.isNotBlank(reLogin)) {
                    setSystemMessage(getMessage(MSG_AUTO_LOGIN));
                }

                return "redirect:/LoginForward.action";
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            log.error("使用者登入失敗 - {}", ExceptionUtility.getStackTrace(e));
            return ERROR;
        }
    }

    @RequestMapping("/LoginForward.action")
    public String loginForward() {
        return INDEX_PAGE;
    }

    @RequestMapping("/Logout.action")
    public String logout(HttpServletRequest request, HttpSession session) {
        try {
            KeycloakSecurityContext attribute = (KeycloakSecurityContext) session.getAttribute(KeycloakSecurityContext.class.getName());
            String keycloakLogoutUrl = attribute
                    .getIdToken()
                    .getIssuer() + "/protocol/openid-connect/logout";

            UserSessionHelper.logoutUser(request);
            return "redirect:" + keycloakLogoutUrl;
        } catch (Exception e) {
            log.error("使用者登出失敗 - {}", ExceptionUtility.getStackTrace(e));
            return LOGOUT_PAGE;
        }
    }

}
