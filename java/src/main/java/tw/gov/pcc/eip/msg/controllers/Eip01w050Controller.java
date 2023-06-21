package tw.gov.pcc.eip.msg.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

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
import tw.gov.pcc.eip.msg.cases.Eip01w050Case;
import tw.gov.pcc.eip.services.Eip01w050Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 輿情專區
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w050Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w050Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w050/eip01w050q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w050Service eip01w050Service;

    /**
     * 進入 輿情專區
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w050_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w050Case caseData) {
        log.debug("導向 Eip01w050_enter 輿情專區 畫面初始");
        eip01w050Service.initQryList(caseData, userData.getDeptId());
        return MAIN_PAGE;
    }

    /**
     * 透過ajax 查詢明細資料
     * 
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Eip01w050_getDetail.action")
    @ResponseBody
    public Eip01w050Case.Detail getDetail(@RequestParam("fseq") String fseq) {
        log.debug("導向 Eip01w050_getDetail 輿情專區 明細查詢");
        return ObjectUtility.normalizeObject(eip01w050Service.query(fseq));
    }

    /**
     * 輿情專區 檔案下載
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w050_getFile.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip01w050Case caseData) throws FileNotFoundException {
        log.debug("導向 Eip01w050_getFile 輿情專區 檔案下載");
        try {
            eip01w050Service.initQryList(caseData, userData.getDeptId());
            ByteArrayOutputStream baos = null;
            baos = eip01w050Service.getFile(caseData);
            if (baos != null) {
                return new ModelAndView(new FileOutputView(baos, caseData.getFilename(), FileOutputView.GENERAL_FILE));
            } else {
                setSystemMessage("查無下載檔案");
            }
        } catch (Exception e) {
            log.error("公告事項 檔案下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
