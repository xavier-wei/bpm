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
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.services.Eip01w040Service;
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

    /**
     * 進入 下載專區
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        log.debug("導向 Eip01w040_enter 下載專區 畫面初始");
        m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
        eip01w040Service.defaultQuery(caseData, userData.getDeptId());
        return MAIN_PAGE;
    }

    /**
     * 關鍵字查詢
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_query.action")
    public String query(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        log.debug("導向 Eip01w040_enter 下載專區 關鍵字查詢");
        m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
        eip01w040Service.keywordQuery(caseData, userData.getDeptId());
        return MAIN_PAGE;
    }

    /**
     * 路徑查詢
     * 
     * @param caseData
     * @param m
     * @return
     */
    @RequestMapping("/Eip01w040_pathQuery.action")
    public String path(@ModelAttribute(CASE_KEY) Eip01w040Case caseData, ModelMap m) {
        log.debug("導向 Eip01w040_detail 下載專區 路徑查詢");
        m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
        eip01w040Service.pathQuery(caseData, userData.getDeptId());
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
    public Eip01w040Case.Detail detail(@RequestParam("fseq") String fseq) {
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
        log.debug("導向 Eip01w040_getFile 下載專區 附檔下載");
        m.put("items", eip01w040Service.getTree(caseData, userData.getDeptId()));
        try {
            ByteArrayOutputStream baos = null;
            baos = eip01w040Service.getFile(caseData);
            if (baos != null) {
                return new ModelAndView(new FileOutputView(baos, caseData.getFilename(), FileOutputView.GENERAL_FILE));
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
