package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.common.cases.Eip02w010Case;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip02w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 通訊錄查詢
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip02w010Controller extends BaseController {

    public static final String CASE_KEY = "_eip02w010Controller_casekey";
    private static final String MAIN_PAGE = "/eip02w010/eip02w010q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip02w010Service eip02w010Service;

    /**
     * 進入
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip02w010_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip02w010Case caseData) {
        log.debug("導向 Eip02w010_enter 通訊錄查詢");
        try {
            eip02w010Service.initOptions(caseData);
            eip02w010Service.initQuery(caseData, userData.getDeptId());
        } catch (Exception e) {
            log.error("通訊錄查詢 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    /**
     * 查詢
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip02w010_query.action")
    public ModelAndView query(@ModelAttribute(CASE_KEY) Eip02w010Case caseData) {
        log.debug("導向 Eip02w010_query 通訊錄查詢");
        try {
            eip02w010Service.initOptions(caseData);
            eip02w010Service.query(caseData);
        } catch (Exception e) {
            log.error("通訊錄查詢 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
