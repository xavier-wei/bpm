package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.Eip04w030Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip04w030Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.ByteArrayOutputStream;

/**
 * 線上報名Controller
 * @author Weith
 */
@Controller
public class Eip04w030Controller extends BaseController {
    public static final String CASE_KEY = "_eip04w030Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip04w030Controller.class);
    private static final String MAIN_PAGE = "/eip04w030/eip04w030m";//主頁
    private static final String CONTENT_PAGE = "/eip04w030/eip04w030x";//明細頁

    @Autowired
    private Eip04w030Service eip04w030Service;

    @ModelAttribute(CASE_KEY)
    public Eip04w030Case getEip04w030Case() {
        Eip04w030Case caseData = new Eip04w030Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip04w030_enter.action")
    public String enter() {
        log.debug("導向 Eip04w030 線上報名");
        return "redirect:/Eip04w030_initQuery.action";
    }

    @RequestMapping("/Eip04w030_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip04w030Case caseData) {
        try {
            eip04w030Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w030_content.action")
    public String content(@ModelAttribute(CASE_KEY) Eip04w030Case caseData) {
        try {
            log.debug("導向 內容畫面");
            eip04w030Service.getContent(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return CONTENT_PAGE;
    }

    @RequestMapping("/Eip04w030_register.action")
    public String register(@ModelAttribute(CASE_KEY) @Validated Eip04w030Case caseData, BindingResult result) {
        try {
            log.debug("處理報名");
            // 20230804改為用畫面控管
//            eip04w030Service.validRegister(caseData,result);
            if (result.hasErrors()) {
                eip04w030Service.getAllList(caseData);
                return MAIN_PAGE;
            }
            eip04w030Service.register(caseData);
            eip04w030Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("報名失敗！");
            return MAIN_PAGE;
        }
        setSystemMessage("報名成功！");
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w030_cancel.action")
    public String cancel(@ModelAttribute(CASE_KEY) Eip04w030Case caseData) {
        try {
            log.debug("處理取消報名");
            eip04w030Service.registerCancel(caseData);
            eip04w030Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("取消報名失敗！");
            return MAIN_PAGE;
        }
        setSystemMessage("已取消報名。");
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w030_download.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip04w030Case caseData) {
        try {
            ByteArrayOutputStream baos = eip04w030Service.downloadFile(caseData);
            if (baos == null) {
                eip04w030Service.getContent(caseData);
                setSystemMessage("下載失敗，無法取得目錄或檔案!");
                return new ModelAndView(CONTENT_PAGE);
            }
            return new ModelAndView(new FileOutputView(baos, caseData.getFilename(), FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("下載失敗!");
            return new ModelAndView(MAIN_PAGE);
        }
    }
}
