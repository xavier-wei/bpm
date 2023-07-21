package tw.gov.pcc.eip.msg.controllers;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.msg.cases.Eip01w070Case;
import tw.gov.pcc.eip.msg.cases.Eip01wFileCase;
import tw.gov.pcc.eip.services.Eip01w070Service;
import tw.gov.pcc.eip.services.Eip01wFileService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 業務資訊
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w070Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w070Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w070/eip01w070q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w070Service eip01w070Service;
    @Autowired
    private Eip01wFileService eip01wFileService;

    /**
     * 進入 業務資訊
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w070_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w070Case caseData) {
        log.debug("導向 Eip01w070_enter 業務資訊 畫面初始");
        eip01w070Service.initOptions(caseData, userData.getDeptId());
        return MAIN_PAGE;
    }

    /**
     * 透過ajax 查詢明細資料
     * 
     * @param contactunit 畫面選擇之聯絡單位
     * @return
     */
    @RequestMapping("/Eip01w070_getDetail.action")
    @ResponseBody
    public Eip01w070Case getDetail(@RequestParam("contactunit") String contactunit) {
        log.debug("導向 Eip01w070_getDetail 業務資訊 明細查詢");
        return ObjectUtility.normalizeObject(eip01w070Service.getContent(contactunit, userData.getDeptId()));
    }

    /**
     * 業務資訊 檔案下載
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w070_getFile.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip01w070Case caseData) {
        log.debug("導向 Eip01w070_getFile 業務資訊 檔案下載");
        try {
            eip01w070Service.initOptions(caseData, userData.getDeptId());
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
            log.error("業務資訊 附檔下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
