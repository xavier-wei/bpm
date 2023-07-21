package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip00w520ThemeCase;
import tw.gov.pcc.eip.common.cases.Eip00w530Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w530Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 意見調查(會內人員)Controller
 * @author Weith
 */
@Controller
public class Eip00w530Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w530Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w530Controller.class);
    private static final String MAIN_PAGE = "/eip00w530/eip00w530m";//主頁
    private static final String WRITE_PAGE = "/eip00w530/eip00w530x";//明細頁

    @Autowired
    private Eip00w530Service eip00w530Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w530Case getEip00w530Case() {
        Eip00w530Case caseData = new Eip00w530Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip00w530_enter.action")
    public String enter() {
        log.debug("導向 Eip00w530 意見調查");
        return "redirect:/Eip00w530_initQuery.action";
    }

    @RequestMapping("/Eip00w530_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w530Case caseData) {
        try {
            eip00w530Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w530_write.action")
    public String write(@ModelAttribute(CASE_KEY) Eip00w530Case caseData) {
        try {
            log.debug("導向 填寫畫面");
            Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
            eip00w530Service.getSingleFormData(caseData, themeCase);
            eip00w530Service.getPreviewData(caseData);
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return WRITE_PAGE;
    }

    @RequestMapping("/Eip00w530_send.action")
    public String send(@ModelAttribute(CASE_KEY) Eip00w530Case caseData, BindingResult result) {
        try {
            log.debug("送出 意見調查");
            eip00w530Service.advancedValidate(caseData, result);
            if (result.hasErrors()) {
                Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
                eip00w530Service.getSingleFormData(caseData, themeCase);
                eip00w530Service.getPreviewData(caseData);
                caseData.setThemeCase(themeCase);
                return WRITE_PAGE;
            }
            eip00w530Service.insertResult(caseData);
            eip00w530Service.getAllList(caseData);
            setSystemMessage("送出成功!");
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
            eip00w530Service.getSingleFormData(caseData, themeCase);
            eip00w530Service.getPreviewData(caseData);
            caseData.setThemeCase(themeCase);
            setSystemMessage(super.getQueryFailMessage());
            return WRITE_PAGE;
        }
        return MAIN_PAGE;
    }

}
