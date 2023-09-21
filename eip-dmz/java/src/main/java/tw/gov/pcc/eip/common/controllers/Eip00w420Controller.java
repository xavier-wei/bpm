package tw.gov.pcc.eip.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.Eip00w420Case;
import tw.gov.pcc.eip.common.cases.Eip00w420ManualCase;
import tw.gov.pcc.eip.common.cases.Eip00w420ModifyCase;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip00w420Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.FilenameUtility;

import java.io.ByteArrayOutputStream;

/**
 * 線上報名列表Controller
 * @author Weith
 */
@Controller
public class Eip00w420Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w420Controller_caseData";
    public static final String MODIFY_CASE_KEY = "_eip00w420Controller_modifyCaseData";
    public static final String MANUAL_CASE_KEY = "_eip00w420Controller_manualCaseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w420Controller.class);
    private static final String MAIN_PAGE = "/eip00w420/eip00w420m";//主頁
    private static final String MODIFY_PAGE = "/eip00w420/eip00w420x";//新增修改頁
    private static final String HISTORY_PAGE = "/eip00w420/eip00w424x";//修改歷程
    private static final String VERIFY_PAGE = "/eip00w420/eip00w422x";//審核
    private static final String CERTIFIED_PAGE = "/eip00w420/eip00w423x";//時數認證
    private static final String MANUAL_PAGE = "/eip00w420/eip00w421x";//人工報名
    @Autowired
    private UserBean userData;

    @Autowired
    private Eip00w420Service eip00w420Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w420Case getEip00w420Case() {
        return new Eip00w420Case();
    }

    @ModelAttribute(MODIFY_CASE_KEY)
    public Eip00w420ModifyCase getEip00w420ModifyCase() {
        return new Eip00w420ModifyCase();
    }

    @ModelAttribute(MANUAL_CASE_KEY)
    public Eip00w420ManualCase getEip00w420ManualCase() { return new Eip00w420ManualCase(); }

    @RequestMapping("/Eip00w420_enter.action")
    public String enter() {
        log.debug("導向 Eip00w420 線上報名列表");
        return "redirect:/Eip00w420_initQuery.action";
    }

    @RequestMapping("/Eip00w420_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_advQuery.action")
    public String advQuery(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("查詢 依進階條件");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.getOrlist(caseData, true);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_selectInsertUpdate.action")
    public String selectInsertUpdate(@ModelAttribute(CASE_KEY) Eip00w420Case caseData, @ModelAttribute(MODIFY_CASE_KEY) Eip00w420ModifyCase modifyCaseData) {
        try {
            log.debug("導向 新增或修改畫面");
            eip00w420Service.initCombobox(caseData);
            if ("A".equals(caseData.getMode())) {
                log.debug("導向新增畫面");
                eip00w420Service.getInsertFormData(modifyCaseData);
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向更新畫面");
                eip00w420Service.getSingleFormData(caseData, modifyCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }

    @RequestMapping("/Eip00w420_confirm.action")
    public String confirm(@ModelAttribute(CASE_KEY) Eip00w420Case caseData,
                                  @Validated @ModelAttribute(MODIFY_CASE_KEY) Eip00w420ModifyCase modifyCaseData,
                                  BindingResult result) {
        try {
            log.debug("新增或修改線上報名表單");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            eip00w420Service.initCombobox(caseData);
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            eip00w420Service.insertUpdateOrfData(modifyCaseData, caseData.getMode());
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return MODIFY_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_delete.action")
    public String delete(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("刪除 線上報名表單");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.deleteCheckedForm(caseData);
            eip00w420Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_put.action")
    public String put(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("上架 線上報名表單");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.putCheckedForm(caseData);
            eip00w420Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_disabled.action")
    public String disabled(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("停辦 線上報名表單");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.disabledCheckedForm(caseData);
            eip00w420Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_copy.action")
    public String copy(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("複製 線上報名表單");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.copyFormdata(caseData);
            eip00w420Service.getOrlist(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_history.action")
    public String history(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("查詢 修改歷程");
            eip00w420Service.getHisData(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return HISTORY_PAGE;
    }

    @RequestMapping("/Eip00w420_verify.action")
    public String verify(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("導向 報名審核");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.getVerifyData(caseData, "S".equals(caseData.getMode()));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return VERIFY_PAGE;
    }

    @RequestMapping("/Eip00w420_verifyConfirm.action")
    public String verifyConfirm(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("更新 審核狀態");
            eip00w420Service.initCombobox(caseData);
            eip00w420Service.verify(caseData);
            eip00w420Service.getVerifyData(caseData, false);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return VERIFY_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return VERIFY_PAGE;
    }

    @RequestMapping("/Eip00w420_certified.action")
    public String certified(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("導向 時數認證");
            eip00w420Service.getCertifiedData(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return CERTIFIED_PAGE;
    }

    @RequestMapping("/Eip00w420_certifiedConfirm.action")
    public String certifiedConfirm(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        try {
            log.debug("更新 時數認證");
            eip00w420Service.updateCertifiedData(caseData);
            eip00w420Service.getCertifiedData(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return CERTIFIED_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return CERTIFIED_PAGE;
    }

    @RequestMapping("/Eip00w420_printSigninList.action")
    public ModelAndView printSigninList(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("列印 簽到表");
            baos = eip00w420Service.prodWord(caseData);
            if (baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip00w420Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP00W420L00","docx");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip00w420_printCSV.action")
    public ModelAndView printCSV(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 學習時數");
            byte[] databytes = eip00w420Service.prodCertihoursCsv(caseData);
            if (databytes == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip00w420Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            baos.write(databytes);
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP00W420L01","csv");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip00w420_printExcel.action")
    public ModelAndView printExcel(@ModelAttribute(CASE_KEY) Eip00w420Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 報名資料");
            baos = eip00w420Service.prodExcel(caseData);
            if (baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip00w420Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP00W420L02","xls");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip00w420_manualReg.action")
    public String manualReg(@ModelAttribute(CASE_KEY) Eip00w420Case caseData, @ModelAttribute(MANUAL_CASE_KEY) Eip00w420ManualCase manualCase) {
        try {
            log.debug("導向 人工報名");
            eip00w420Service.initCombobox(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return MANUAL_PAGE;
    }

    @RequestMapping("/Eip00w420_manualRegSingle.action")
    public String manualRegSingle(@ModelAttribute(CASE_KEY) Eip00w420Case caseData, @Validated({Eip00w420ManualCase.Single.class}) @ModelAttribute(MANUAL_CASE_KEY) Eip00w420ManualCase manualCaseData,
                          BindingResult result) {
        try {
            log.debug("人工報名-單筆");
            eip00w420Service.initCombobox(caseData);
            if (result.hasErrors()) {
                return MANUAL_PAGE;
            }
            eip00w420Service.manualRegSingle(manualCaseData);
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage("人工報名成功!");
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("人工報名失敗!");
            return MANUAL_PAGE;
        }
        return MAIN_PAGE;
    }
    @RequestMapping("/Eip00w420_manualRegBatch.action")
    public String manualRegBatch(@ModelAttribute(CASE_KEY) Eip00w420Case caseData, @Validated({Eip00w420ManualCase.Batch.class}) @ModelAttribute(MANUAL_CASE_KEY) Eip00w420ManualCase manualCaseData,
                                   BindingResult result) {
        try {
            log.debug("人工報名-批次");
            eip00w420Service.initCombobox(caseData);
            if (result.hasErrors()) {
                return MANUAL_PAGE;
            }
            eip00w420Service.manualRegBatch(manualCaseData);
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage("人工報名成功!");
        } catch (IllegalArgumentException ie) {
            setSystemMessage(ie.getMessage()+"，請確認資料正確後再進行上傳!");
            return MANUAL_PAGE;
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage("人工報名失敗!");
            return MANUAL_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w420_downloadExample.action")
    public ModelAndView downloadExample(@ModelAttribute(CASE_KEY) Eip00w420Case caseData, @ModelAttribute(MANUAL_CASE_KEY) Eip00w420ManualCase manualCaseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("下載範本");
            baos = eip00w420Service.downloadCsv();
            if (baos == null) {
                log.debug("查無CSV範本資料");
                setSystemMessage(getQueryEmptyMessage());
                eip00w420Service.getOrlist(caseData, false);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = "批次報名匯入檔案範本.csv";
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w420Service.getOrlist(caseData, false);
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
