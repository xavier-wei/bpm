package tw.gov.pcc.eip.msg.controllers;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.MessageKey;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.msg.cases.Eip01wFileCase;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.services.Eip01w040Service;
import tw.gov.pcc.eip.services.Eip01wFileService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 下載專區
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w040Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w040Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w040/eip01w040q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w040Service eip01w040Service;
    @Autowired
    private Eip01wFileService eip01wFileService;

    /**
     * 進入 下載專區
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        try {
            m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
            eip01w040Service.defaultQuery(caseData, userData.getDeptId());
        } catch (Exception e) {
            log.error("下載專區 - 預設查詢 " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(MessageKey.MSG_QUERY_FAIL);
        }
        return MAIN_PAGE;
    }

    /**
     * (點選資料夾)路徑查詢
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_path.action")
    public String path(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        try {
            m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
            eip01w040Service.pathQuery(caseData, userData.getDeptId());
        } catch (Exception e) {
            log.error("下載專區 - 路徑查詢 " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(MessageKey.MSG_QUERY_FAIL);
        }
        return MAIN_PAGE;
    }

    /**
     * 關鍵字查詢
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_keyword.action")
    public String keyword(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        try {
            m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
            eip01w040Service.keywordQuery(caseData, userData.getDeptId());
        } catch (Exception e) {
            log.error("下載專區 - 關鍵字查詢 " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(MessageKey.MSG_QUERY_FAIL);
        }
        return MAIN_PAGE;
    }

    /**
     * 透過ajax 查詢明細資料
     * 
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Eip01w040_detail.action")
    @ResponseBody
    public Eip01wPopCase detail(@RequestParam("fseq") String fseq) {
        log.debug("導向 Eip01w040_detail 下載專區 明細查詢");
        return ObjectUtility.normalizeObject(eip01w040Service.getDetail(fseq));
    }

    /**
     * 下載專區 附檔下載
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_getFile.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        try {
            m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
            Eip01wFileCase filecase = new Eip01wFileCase();
            filecase.setFseq(caseData.getFseq());
            filecase.setSeq(caseData.getSeq());
            filecase.setSubject(caseData.getSubject());
            ByteArrayOutputStream baos = eip01wFileService.getFile(filecase);
            if (baos != null) {
                return new ModelAndView(new FileOutputView(baos, filecase.getFilename(), FileOutputView.GENERAL_FILE));
            } else {
                setSystemMessage("查無下載檔案");
            }
        } catch (Exception e) {
            log.error("下載專區 附檔下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
