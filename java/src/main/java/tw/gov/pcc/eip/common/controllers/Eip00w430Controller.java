package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.Eip00w430Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip00w430Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.ByteArrayOutputStream;

/**
 * 線上報名Controller
 * @author Weith
 */
@Controller
public class Eip00w430Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w430Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w430Controller.class);
    private static final String MAIN_PAGE = "/eip00w430/eip00w430m";//主頁
    private static final String CONTENT_PAGE = "/eip00w430/eip00w430x";//明細頁

    @Autowired
    private Eip00w430Service eip00w430Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w430Case getEip00w430Case() {
        Eip00w430Case caseData = new Eip00w430Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip00w430_enter.action")
    public String enter() {
        log.debug("導向 Eip00w430 線上報名");
        return "redirect:/Eip00w430_initQuery.action";
    }

    @RequestMapping("/Eip00w430_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w430Case caseData) {
        try {
            eip00w430Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w430_content.action")
    public String content(@ModelAttribute(CASE_KEY) Eip00w430Case caseData) {
        try {
            log.debug("導向 內容畫面");
            eip00w430Service.getContent(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return CONTENT_PAGE;
    }

    @RequestMapping("/Eip00w430_register.action")
    public String register(@ModelAttribute(CASE_KEY) @Validated Eip00w430Case caseData, BindingResult result) {
        try {
            log.debug("處理報名");
            // 20230804改為用畫面控管
//            eip00w430Service.validRegister(caseData,result);
            if (result.hasErrors()) {
                eip00w430Service.getAllList(caseData);
                return MAIN_PAGE;
            }
            eip00w430Service.register(caseData);
            eip00w430Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("報名失敗！");
            return MAIN_PAGE;
        }
        setSystemMessage("報名成功！");
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w430_cancel.action")
    public String cancel(@ModelAttribute(CASE_KEY) Eip00w430Case caseData) {
        try {
            log.debug("處理取消報名");
            eip00w430Service.registerCancel(caseData);
            eip00w430Service.getAllList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("取消報名失敗！");
            return MAIN_PAGE;
        }
        setSystemMessage("已取消報名。");
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w430_download.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip00w430Case caseData) {
        try {
            ByteArrayOutputStream baos = eip00w430Service.downloadFile(caseData);
            if (baos == null) {
                eip00w430Service.getContent(caseData);
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
