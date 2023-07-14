package tw.gov.pcc.eip.framework.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.services.LoginService;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.services.Eip01w040Service;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    private final Eip01w040Service eip01w040Service;
    private final EipcodeDao eipcodeDao;


    public LoginController(LoginService loginService, MsgdataDao msgdataDao, UserBean userData, Eip01w040Service eip01w040Service, EipcodeDao eipcodeDao) {
        this.loginService = loginService;
        this.msgdataDao = msgdataDao;
        this.userData = userData;
        this.eip01w040Service = eip01w040Service;
        this.eipcodeDao = eipcodeDao;
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
    public String logout(HttpServletRequest request) {
        try {
            UserSessionHelper.logoutUser(request);
            return LOGOUT_PAGE;
        } catch (Exception e) {
            log.error("使用者登出失敗 - {}", ExceptionUtility.getStackTrace(e));
            return LOGOUT_PAGE;
        }
    }


    /**
     * 公告
     *
     * @return
     */
    @ModelAttribute("msgdata")
    public List<Eip01wPopCase> getMsgdata() {
        return ObjectUtility.normalizeObject(msgdataDao.getEip01w030LatestDataList());
    }

    /**
     * 下載
     * @return
     */
    @ModelAttribute("downloaddata")
    public List<Eip01wPopCase> getDownloadData() {
        Eip01w040Case caseData = new Eip01w040Case();
        eip01w040Service.defaultQuery(caseData, userData.getDeptId());
        return caseData.getQryList()
                .stream()
                .peek(x -> x.setUpddt(StringUtils.remove(x.getUpddt(), "/"))) //日期顯示一致性
                .collect(Collectors.toList());
    }
    
    @ModelAttribute("sys_site")
    public List<Eipcode> getSys_site() {
        return eipcodeDao.findByCodeKindOrderByScodeno("SYS_SITE");
    }
}
