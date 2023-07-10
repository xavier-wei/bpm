package tw.gov.pcc.eip.framework.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.services.LoginService;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 使用者登入
 *
 * @author Goston
 */
@Controller
public class LoginController extends BaseController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginController.class);
    private static final String INDEX_PAGE = "/index";
    private static final String ERROR = "/unauthorized";
    private static final String LOGOUT_PAGE = "/logout";
    private static final String MSG_AUTO_LOGIN = "msg.framework.autoReLogin";
    private final LoginService loginService;
    private final MsgdataDao msgdataDao;
    private final UserBean userData;


    public LoginController(LoginService loginService, MsgdataDao msgdataDao, UserBean userData) {
        this.loginService = loginService;
        this.msgdataDao = msgdataDao;
        this.userData = userData;
    }

    @RequestMapping("/Login.action")
    public String login(HttpServletRequest request) {
        try {
            if (loginService.getUserLoginData(userData, request)) {
                // 取得 登入日期 及 登入時間
                String loginDateTime = DateUtility.getNowChineseDateTime(false);
                userData.setLoginDate(DateUtility.splitChineseDateTime(loginDateTime, true));
                userData.setLoginTime(DateUtility.splitChineseDateTime(loginDateTime, false));
                // 將使用者資料存入 Session
                UserSessionHelper.setUserData(request, userData);
                // 如果是 CAS 自動重新登入, 則於訊息區顯示自動重新登入訊息

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
    public String logout(HttpServletRequest request) {
        try {
            UserSessionHelper.logoutUser(request);
            return LOGOUT_PAGE;
        } catch (Exception e) {
            log.error("使用者登出失敗 - {}", ExceptionUtility.getStackTrace(e));
            return LOGOUT_PAGE;
        }
    }


    @ModelAttribute("msgdata")
    public List<Eip01w030Case.Detail> getMsgdata() {
        return msgdataDao.getEip01w030LatestDataList();
    }
}
