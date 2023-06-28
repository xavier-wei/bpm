package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip00w520Case;
import tw.gov.pcc.eip.common.cases.Eip00w520ThemeCase;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w520Service;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 意見調查主題列表Controller
 * @author Weith
 */
@Controller
public class Eip00w520Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w520Controller_caseData";
    public static final String THEME_CASE_KEY = "_eip00w520Controller_themeCaseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w520Controller.class);
    private static final String MAIN_PAGE = "/eip00w520/eip00w520m";//主頁
    private static final String THEME_PAGE = "/eip00w520/eip00w520x";//主題新增修改頁
    @Autowired
    private UserBean userData;

    @Autowired
    private Eip00w520Service eip00w520Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w520Case getEip00w520Case() {
        return new Eip00w520Case();
    }

    @ModelAttribute(THEME_CASE_KEY)
    public Eip00w520ThemeCase getEip00w520ThemeCase() {
        return new Eip00w520ThemeCase();
    }

    @RequestMapping("/Eip00w520_enter.action")
    public String enter() {
        log.debug("導向 Eip00w520 意見調查主題列表");
        return "redirect:/Eip00w520_initQuery.action";
    }

    @RequestMapping("/Eip00w520_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_selectInsertUpdate.action")
    public String selectInsertUpdate(@ModelAttribute(CASE_KEY) Eip00w520Case caseData, @ModelAttribute(THEME_CASE_KEY) Eip00w520ThemeCase themeCaseData) {
        try {
            log.debug("導向 新增或修改畫面");
            eip00w520Service.init(caseData);
            if ("A".equals(caseData.getMode())) {
                log.debug("導向新增畫面");
                eip00w520Service.getInsertFormData(themeCaseData);
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向更新畫面");
                eip00w520Service.getSingleFormData(caseData, themeCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return THEME_PAGE;
    }

    @RequestMapping("/Eip00w520_confirm.action")
    public String confirm(@ModelAttribute(CASE_KEY) Eip00w520Case caseData,
                          @Validated @ModelAttribute(THEME_CASE_KEY) Eip00w520ThemeCase themeCaseData,
                          BindingResult result) {
        try {
            log.debug("新增或修改意見調查表單");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            eip00w520Service.init(caseData);
            eip00w520Service.advancedValidate(themeCaseData, result);
            if (result.hasErrors()) {
                return THEME_PAGE;
            }
            eip00w520Service.insertUpdateData(themeCaseData, caseData.getMode());
            eip00w520Service.getOslist(caseData);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return THEME_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_delete.action")
    public String delete(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("刪除 意見調查主題");
            eip00w520Service.init(caseData);
            eip00w520Service.deleteCheckedForm(caseData);
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }
}
