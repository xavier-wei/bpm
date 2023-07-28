package tw.gov.pcc.eip.tableau.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.common.services.LoginService;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip01w040Service;
import tw.gov.pcc.eip.tableau.cases.TableauUserCase;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 使用者登入
 *
 * @author Goston
 */
@Controller
public class TableauController extends BaseController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TableauController.class);
    private static final String INDEX_PAGE = "/index";
    private static final String ERROR = "/unauthorized";
    private static final String LOGOUT_PAGE = "/logout";
    private static final String MSG_AUTO_LOGIN = "msg.framework.autoReLogin";
    private final LoginService loginService;
    private final MsgdataDao msgdataDao;
    private final UserBean userData;
    private final Eip01w040Service eip01w040Service;
    private final EipcodeDao eipcodeDao;


    public TableauController(LoginService loginService, MsgdataDao msgdataDao, UserBean userData, Eip01w040Service eip01w040Service, EipcodeDao eipcodeDao) {
        this.loginService = loginService;
        this.msgdataDao = msgdataDao;
        this.userData = userData;
        this.eip01w040Service = eip01w040Service;
        this.eipcodeDao = eipcodeDao;
    }


    @RequestMapping("/getUserData")
    @ResponseBody
    public TableauUserCase getUserdata() {
        TableauUserCase caseData = new TableauUserCase();
        caseData.setUser(userData.getUserId());
        return ObjectUtility.normalizeObject(caseData);
    }

}
