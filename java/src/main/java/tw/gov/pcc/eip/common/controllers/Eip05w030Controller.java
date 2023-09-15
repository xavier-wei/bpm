package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip05w020Case;
import tw.gov.pcc.eip.common.cases.Eip05w020ThemeCase;
import tw.gov.pcc.eip.common.cases.Eip05w030Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip05w020Service;
import tw.gov.pcc.eip.services.Eip05w030Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 意見調查(會內人員)Controller
 * @author Weith
 */
@Controller
public class Eip05w030Controller extends BaseController {
    public static final String CASE_KEY = "_eip05w030Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip05w030Controller.class);
    private static final String MAIN_PAGE = "/eip05w030/eip05w030m";//主頁
    private static final String WRITE_PAGE = "/eip05w030/eip05w030x";//填寫頁
    private static final String RESULT_PAGE = "/eip05w030/eip05w031x";//結果頁

    @Autowired
    private Eip05w030Service eip05w030Service;

    @Autowired
    private Eip05w020Service eip05w020Service;

    @ModelAttribute(CASE_KEY)
    public Eip05w030Case getEip05w030Case() {
        Eip05w030Case caseData = new Eip05w030Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @ModelAttribute(Eip05w020Controller.CASE_KEY)
    public Eip05w020Case getEip05w020Case() {
        Eip05w020Case caseData = new Eip05w020Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip05w030_enter.action")
    public String enter() {
        log.debug("導向 Eip05w030 意見調查");
        return "redirect:/Eip05w030_initQuery.action";
    }

    @RequestMapping("/Eip05w030_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip05w030Case caseData) {
        try {
            eip05w030Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w030_write.action")
    public String write(@ModelAttribute(CASE_KEY) Eip05w030Case caseData) {
        try {
            log.debug("導向 填寫畫面");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w030Service.getSingleFormData(caseData, themeCase);
            eip05w030Service.getTopicData(caseData);
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return WRITE_PAGE;
    }

    @RequestMapping("/Eip05w030_result.action")
    public String result(@ModelAttribute(Eip05w020Controller.CASE_KEY) Eip05w020Case caseDataB, @ModelAttribute(CASE_KEY) Eip05w030Case caseDataA) {
        try {
            log.debug("導向 結果畫面");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseDataB, themeCase);
            String result = eip05w020Service.getReviewStatistics(caseDataB);
            if ("isEmpty".equals(result)) {
                eip05w030Service.getAllList(caseDataA);
                setSystemMessage(super.getQueryEmptyMessage());
                return MAIN_PAGE;
            }
            caseDataB.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return RESULT_PAGE;
    }

    @RequestMapping("/Eip05w030_send.action")
    public String send(@ModelAttribute(CASE_KEY) Eip05w030Case caseData, BindingResult result) {
        try {
            log.debug("送出 意見調查");
            eip05w030Service.advancedValidate(caseData, result);
            if (result.hasErrors()) {
                Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
                eip05w030Service.getSingleFormData(caseData, themeCase);
                eip05w030Service.getTopicData(caseData);
                caseData.setThemeCase(themeCase);
                return WRITE_PAGE;
            }
            eip05w030Service.insertResult(caseData);
            eip05w030Service.getAllList(caseData);
            setSystemMessage("送出成功!");
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return MAIN_PAGE;
    }

}
