package tw.gov.pcc.eip.msg.controllers;

import java.io.ByteArrayOutputStream;

import org.apache.commons.collections.CollectionUtils;
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
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.msg.cases.Eip01wFileCase;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.services.Eip01w030Service;
import tw.gov.pcc.eip.services.Eip01wFileService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 公告事項
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w030Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w030Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w030/eip01w030q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w030Service eip01w030Service;
    @Autowired
    private Eip01wFileService eip01wFileService;

    /**
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w030_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w030Case caseData) {
        log.debug("導向 Eip01w030_enter 公告事項 畫面初始");
        eip01w030Service.initOptions(caseData);
        eip01w030Service.getLatestData(caseData);
        return MAIN_PAGE;
    }

    /**
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w030_query.action")
    public String query(@ModelAttribute(CASE_KEY) Eip01w030Case caseData) {
        log.debug("導向 Eip01w030_query 公告事項 條件查詢");
        try {
            eip01w030Service.initOptions(caseData);
            eip01w030Service.query(caseData);
            if (!CollectionUtils.isEmpty(caseData.getQryList())) {
                setSystemMessage(getQuerySuccessMessage());
            } else {
                setSystemMessage(getQueryEmptyMessage());
            }
        } catch (Exception e) {
            log.error("公告事項 條件查詢 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    /**
     * 透過ajax 查詢明細資料
     * 
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Eip01w030_getDetail.action")
    @ResponseBody
    public Eip01wPopCase getDetail(@RequestParam("fseq") String fseq) {
        log.debug("導向 Eip01w030_getDetail 公告事項 明細查詢");
        return ObjectUtility.normalizeObject(eip01w030Service.query(fseq));
    }

    /**
     * 公告事項 檔案下載
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w030_getFile.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip01w030Case caseData) {
        log.debug("導向 Eip01w030_getFile 公告事項 檔案下載");
        try {
            eip01w030Service.initOptions(caseData);
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
            log.error("公告事項 附檔下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
