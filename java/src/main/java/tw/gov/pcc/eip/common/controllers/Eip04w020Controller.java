package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.Eip04w020Case;
import tw.gov.pcc.eip.common.cases.Eip04w020ManualCase;
import tw.gov.pcc.eip.common.cases.Eip04w020ModifyCase;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.annotation.SkipCSRFVerify;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip04w020Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.FilenameUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * 線上報名列表Controller
 * @author Weith
 */
@Controller
public class Eip04w020Controller extends BaseController {
    public static final String CASE_KEY = "_eip04w020Controller_caseData";
    public static final String MODIFY_CASE_KEY = "_eip04w020Controller_modifyCaseData";
    public static final String MANUAL_CASE_KEY = "_eip04w020Controller_manualCaseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip04w020Controller.class);
    private static final String MAIN_PAGE = "/eip04w020/eip04w020m";//主頁
    private static final String MODIFY_PAGE = "/eip04w020/eip04w020x";//新增修改頁
    private static final String HISTORY_PAGE = "/eip04w020/eip04w024x";//修改歷程
    private static final String VERIFY_PAGE = "/eip04w020/eip04w022x";//審核
    private static final String CERTIFIED_PAGE = "/eip04w020/eip04w023x";//時數認證
    private static final String MANUAL_PAGE = "/eip04w020/eip04w021x";//人工報名
    @Autowired
    private UserBean userData;

    @Autowired
    private Eip04w020Service eip04w020Service;

    @ModelAttribute(CASE_KEY)
    public Eip04w020Case getEip04w020Case() {
        return new Eip04w020Case();
    }

    @ModelAttribute(MODIFY_CASE_KEY)
    public Eip04w020ModifyCase getEip04w020ModifyCase() {
        return new Eip04w020ModifyCase();
    }

    @ModelAttribute(MANUAL_CASE_KEY)
    public Eip04w020ManualCase getEip04w020ManualCase() { return new Eip04w020ManualCase(); }

    @RequestMapping("/Eip04w020_enter.action")
    public String enter() {
        log.debug("導向 Eip04w020 線上報名列表");
        return "redirect:/Eip04w020_initQuery.action";
    }

    @RequestMapping("/Eip04w020_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_advQuery.action")
    public String advQuery(@Validated({Eip04w020Case.Advquery.class}) @ModelAttribute(CASE_KEY) Eip04w020Case caseData, BindingResult result) {
        try {
            log.debug("查詢 依進階條件");
            eip04w020Service.initCombobox(caseData);
            if (result.hasErrors()) {
                return MAIN_PAGE;
            }
            eip04w020Service.getOrlist(caseData, true);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_selectInsertUpdate.action")
    public String selectInsertUpdate(@ModelAttribute(CASE_KEY) Eip04w020Case caseData, @ModelAttribute(MODIFY_CASE_KEY) Eip04w020ModifyCase modifyCaseData) {
        try {
            log.debug("導向 新增或修改畫面");
            eip04w020Service.initCombobox(caseData);
            if ("A".equals(caseData.getMode())) {
                log.debug("導向新增畫面");
                eip04w020Service.getInsertFormData(modifyCaseData);
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向更新畫面");
                eip04w020Service.getSingleFormData(caseData, modifyCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }

    @RequestMapping("/Eip04w020_confirm.action")
    public String confirm(@ModelAttribute(CASE_KEY) Eip04w020Case caseData,
                                  @Validated @ModelAttribute(MODIFY_CASE_KEY) Eip04w020ModifyCase modifyCaseData,
                                  BindingResult result) {
        try {
            log.debug("新增或修改線上報名表單");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.advancedValidate(modifyCaseData, result);
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            eip04w020Service.insertUpdateOrfData(modifyCaseData, caseData.getMode());
            eip04w020Service.getOrlist(caseData, false);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return MODIFY_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_delete.action")
    public String delete(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("刪除 線上報名表單");
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.deleteCheckedForm(caseData);
            eip04w020Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_put.action")
    public String put(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("上架 線上報名表單");
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.putCheckedForm(caseData);
            eip04w020Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_disabled.action")
    public String disabled(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("停辦 線上報名表單");
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.disabledCheckedForm(caseData);
            eip04w020Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_copy.action")
    public String copy(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("複製 線上報名表單");
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.copyFormdata(caseData);
            eip04w020Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_history.action")
    public String history(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("查詢 修改歷程");
            eip04w020Service.getHisData(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return HISTORY_PAGE;
    }

    @RequestMapping("/Eip04w020_verify.action")
    public String verify(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("導向 報名審核");
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.getVerifyData(caseData, "S".equals(caseData.getMode()));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return VERIFY_PAGE;
    }

    @RequestMapping("/Eip04w020_verifyConfirm.action")
    public String verifyConfirm(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("更新 審核狀態");
            eip04w020Service.initCombobox(caseData);
            eip04w020Service.verify(caseData);
            eip04w020Service.getVerifyData(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return VERIFY_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return VERIFY_PAGE;
    }

    @RequestMapping("/Eip04w020_certified.action")
    public String certified(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("導向 時數認證");
            eip04w020Service.getCertifiedData(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return CERTIFIED_PAGE;
    }

    @RequestMapping("/Eip04w020_certifiedConfirm.action")
    public String certifiedConfirm(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        try {
            log.debug("更新 時數認證");
            eip04w020Service.updateCertifiedData(caseData);
            eip04w020Service.getCertifiedData(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return CERTIFIED_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return CERTIFIED_PAGE;
    }

    @RequestMapping("/Eip04w020_printSigninList.action")
    public ModelAndView printSigninList(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("列印 簽到表");
            baos = eip04w020Service.prodWord(caseData);
            if (baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip04w020Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP04W020L00","docx");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip04w020_printCSV.action")
    public ModelAndView printCSV(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 學習時數");
            byte[] databytes = eip04w020Service.prodCertihoursCsv(caseData);
            if (databytes == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip04w020Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            baos.write(databytes);
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP04W020L01","csv");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip04w020_printExcel.action")
    public ModelAndView printExcel(@ModelAttribute(CASE_KEY) Eip04w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 報名資料");
            baos = eip04w020Service.prodExcel(caseData);
            if (baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip04w020Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP04W020L02","xls");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip04w020_manualReg.action")
    public String manualReg(@ModelAttribute(CASE_KEY) Eip04w020Case caseData, @ModelAttribute(MANUAL_CASE_KEY) Eip04w020ManualCase manualCase) {
        try {
            log.debug("導向 人工報名");
            eip04w020Service.initCombobox(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return MANUAL_PAGE;
    }

    @RequestMapping("/Eip04w020_manualRegSingle.action")
    public String manualRegSingle(@ModelAttribute(CASE_KEY) Eip04w020Case caseData, @Validated({Eip04w020ManualCase.Single.class}) @ModelAttribute(MANUAL_CASE_KEY) Eip04w020ManualCase manualCaseData,
                                  BindingResult result) {
        try {
            log.debug("人工報名-單筆");
            eip04w020Service.initCombobox(caseData);
            if (result.hasErrors()) {
                return MANUAL_PAGE;
            }
            String msg = eip04w020Service.manualRegSingle(manualCaseData);
            eip04w020Service.getOrlist(caseData, false);
            if ("isAtLimit".equals(msg)) {
                setSystemMessage("人工報名失敗-名額已滿!");
                return MANUAL_PAGE;
            } else {
                setSystemMessage("人工報名成功!");
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("人工報名失敗!");
            return MANUAL_PAGE;
        }
        return MAIN_PAGE;
    }
    @RequestMapping("/Eip04w020_manualRegBatch.action")
    public String manualRegBatch(@ModelAttribute(CASE_KEY) Eip04w020Case caseData, @Validated({Eip04w020ManualCase.Batch.class}) @ModelAttribute(MANUAL_CASE_KEY) Eip04w020ManualCase manualCaseData,
                                 BindingResult result) {
        try {
            log.debug("人工報名-批次");
            eip04w020Service.initCombobox(caseData);
            if (result.hasErrors()) {
                return MANUAL_PAGE;
            }
            eip04w020Service.manualRegBatch(manualCaseData);
            eip04w020Service.getOrlist(caseData, false);
            setSystemMessage("人工報名成功!");
        } catch (IllegalArgumentException ie) {
            setSystemMessage(ie.getMessage()+"，請確認資料正確後再進行上傳!");
            return MANUAL_PAGE;
        } catch (Eip04w020Service.LimitException e) {
            setSystemMessage(e.getMessage());
            return MANUAL_PAGE;
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("人工報名失敗!");
            return MANUAL_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w020_downloadExample.action")
    public ModelAndView downloadExample(@ModelAttribute(CASE_KEY) Eip04w020Case caseData, @ModelAttribute(MANUAL_CASE_KEY) Eip04w020ManualCase manualCaseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("下載範本");
            baos = eip04w020Service.downloadCsv();
            if (baos == null) {
                log.debug("查無CSV範本資料");
                setSystemMessage(getQueryEmptyMessage());
                eip04w020Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = "批次報名匯入檔案範本.csv";
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    /**
     * 透過AJAX取得會內人員基本資料
     *
     * @param adaccount
     * @return map
     */
    @RequestMapping(path = "/Eip04w020_getInfo.action")
    @ResponseBody
    @SkipCSRFVerify
    public Map<String, String> getInfo(@RequestParam("adaccount") String adaccount) {
        log.debug("透過AJAX取得會內人員基本資料");
        return ObjectUtility.normalizeObject(eip04w020Service.getUserInfo(adaccount));
    }
}
