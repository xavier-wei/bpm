package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip05w020Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.FilenameUtility;

import java.io.ByteArrayOutputStream;

/**
 * 意見調查主題列表Controller
 * @author Weith
 */
@Controller
public class Eip05w020Controller extends BaseController {
    public static final String CASE_KEY = "_eip05w020Controller_caseData";
    public static final String THEME_CASE_KEY = "_eip05w020Controller_themeCaseData";
    public static final String PART_CASE_KEY = "_eip05w020Controller_partCaseData";
    public static final String QUESTION_CASE_KEY = "_eip05w020Controller_questionCaseData";
    public static final String OPTION_CASE_KEY = "_eip05w020Controller_optionCaseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip05w020Controller.class);
    private static final String MAIN_PAGE = "/eip05w020/eip05w020m";//主頁
    private static final String THEME_PAGE = "/eip05w020/eip05w020a";//主題新增修改頁
    private static final String REVIEW_PAGE = "/eip05w020/eip05w021x";//檢視統計表
    private static final String WRITECONTENT_PAGE = "/eip05w020/eip05w027x";//檢視填寫內容表
    private static final String PREVIEW_PAGE = "/eip05w020/eip05w022x";//預覽
    private static final String PARTLIST_PAGE = "/eip05w020/eip05w023m";//部分列表
    private static final String PART_PAGE = "/eip05w020/eip05w023a";//新增修改部分標題
    private static final String QUESTIONLIST_PAGE = "/eip05w020/eip05w024m";//子部分列表
    private static final String QUESTION_PAGE = "/eip05w020/eip05w024a";//新增修改題目
    private static final String OPTION_PAGE = "/eip05w020/eip05w025m";//新增修改刪除題目
    private static final String QUERY_PAGE = "/eip05w020/eip05w026m";//填寫內容查詢

    @Autowired
    private UserBean userData;

    @Autowired
    private Eip05w020Service eip05w020Service;

    @ModelAttribute(CASE_KEY)
    public Eip05w020Case getEip05w020Case() {
        return new Eip05w020Case();
    }

    @ModelAttribute(THEME_CASE_KEY)
    public Eip05w020ThemeCase getEip05w020ThemeCase() {
        return new Eip05w020ThemeCase();
    }

    @ModelAttribute(PART_CASE_KEY)
    public Eip05w020PartCase getEip05w020PartCase() {
        return new Eip05w020PartCase();
    }

    @ModelAttribute(QUESTION_CASE_KEY)
    public Eip05w020QuestionCase getEip05w020QuestionCase() {
        return new Eip05w020QuestionCase();
    }

    @RequestMapping("/Eip05w020_enter.action")
    public String enter() {
        log.debug("導向 Eip05w020 意見調查主題列表");
        return "redirect:/Eip05w020_initQuery.action";
    }

    @RequestMapping("/Eip05w020_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            eip05w020Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w020_review.action")
    public String review(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("導向 檢視畫面");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            String result = eip05w020Service.getReviewStatistics(caseData);
            if ("isEmpty".equals(result)) {
                eip05w020Service.getOslist(caseData);
                setSystemMessage(super.getQueryEmptyMessage());
                return MAIN_PAGE;
            }
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return REVIEW_PAGE;
    }

    @RequestMapping("/Eip05w020_selectInsertUpdate.action")
    public String selectInsertUpdate(@ModelAttribute(CASE_KEY) Eip05w020Case caseData, @ModelAttribute(THEME_CASE_KEY) Eip05w020ThemeCase themeCaseData) {
        try {
            log.debug("導向 新增或修改畫面");
            eip05w020Service.init(caseData);
            if ("A".equals(caseData.getMode())) {
                log.debug("導向新增畫面");
                eip05w020Service.getInsertFormData(themeCaseData);
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向更新畫面");
                eip05w020Service.getSingleFormData(caseData, themeCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return THEME_PAGE;
    }

    @RequestMapping("/Eip05w020_confirm.action")
    public String confirm(@ModelAttribute(CASE_KEY) Eip05w020Case caseData,
                          @Validated @ModelAttribute(THEME_CASE_KEY) Eip05w020ThemeCase themeCaseData,
                          BindingResult result) {
        try {
            log.debug("新增或修改意見調查表單");
            eip05w020Service.init(caseData);
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                return THEME_PAGE;
            }
            eip05w020Service.insertUpdateData(themeCaseData, caseData.getMode());
            eip05w020Service.getOslist(caseData);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return THEME_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w020_partList.action")
    public String partList(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("導向 意見調查部分列表");
            eip05w020Service.getPartList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return PARTLIST_PAGE;
    }

    @RequestMapping("/Eip05w020_selectPartInsertUpdate.action")
    public String selectPartInsertUpdate(@ModelAttribute(CASE_KEY) Eip05w020Case caseData, @ModelAttribute(PART_CASE_KEY) Eip05w020PartCase partCaseData) {
        try {
            log.debug("導向 部分列表新增或修改畫面");
            if ("A".equals(caseData.getMode())) {
                partCaseData.clear();
                log.debug("導向部分列表新增畫面");
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向部分列表修改畫面");
                eip05w020Service.getSinglePartData(caseData, partCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return PARTLIST_PAGE;
        }
        return PART_PAGE;
    }

    @RequestMapping("/Eip05w020_partConfirm.action")
    public String partConfirm(@ModelAttribute(CASE_KEY) Eip05w020Case caseData,
                          @Validated @ModelAttribute(PART_CASE_KEY) Eip05w020PartCase partCaseData,
                          BindingResult result) {
        try {
            log.debug("新增或修改部分標題");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                return PART_PAGE;
            }
            String processMsg = eip05w020Service.insertUpdatePartData(partCaseData, partCaseData.getMode());
            eip05w020Service.getPartList(caseData);
            setSystemMessage(StringUtils.isBlank(processMsg) ? msg : processMsg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return PART_PAGE;
        }
        return PARTLIST_PAGE;
    }

    @RequestMapping("/Eip05w020_questionList.action")
    public String questionList(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("導向 子部分(題目)列表");
            eip05w020Service.getQuestionList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return PARTLIST_PAGE;
        }
        return QUESTIONLIST_PAGE;
    }

    @RequestMapping("/Eip05w020_selectQuestionInsertUpdate.action")
    public String selectQuestionInsertUpdate(@ModelAttribute(CASE_KEY) Eip05w020Case caseData, @ModelAttribute(QUESTION_CASE_KEY) Eip05w020QuestionCase questionCase) {
        try {
            log.debug("導向 子部分列表新增或修改畫面");
            if ("A".equals(caseData.getMode())) {
                log.debug("導向子部分列表新增畫面");
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向子部分列表修改畫面");
                eip05w020Service.getSingleQuestionData(caseData, questionCase);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return QUESTIONLIST_PAGE;
        }
        return QUESTION_PAGE;
    }

    @RequestMapping("/Eip05w020_questionConfirm.action")
    public String questionConfirm(@ModelAttribute(CASE_KEY) Eip05w020Case caseData,
                              @Validated @ModelAttribute(QUESTION_CASE_KEY) Eip05w020QuestionCase questionCase,
                              BindingResult result) {
        try {
            log.debug("新增或修改題目");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                return QUESTION_PAGE;
            }
            eip05w020Service.insertUpdateQuestionData(questionCase, caseData.getMode());
            eip05w020Service.getQuestionList(caseData);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return QUESTION_PAGE;
        }
        return QUESTIONLIST_PAGE;
    }

    @RequestMapping("/Eip05w020_optionList.action")
    public String optionList(@ModelAttribute(CASE_KEY) Eip05w020Case caseData, @ModelAttribute(OPTION_CASE_KEY) Eip05w020OptionCase optionCase) {
        try {
            log.debug("導向 部分列表選項");
            eip05w020Service.getInsertOptionData(caseData, optionCase);
            eip05w020Service.getOptionList(caseData, optionCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return OPTION_PAGE;
        }
        return OPTION_PAGE;
    }

    @RequestMapping("/Eip05w020_optionInsertUpdate.action")
    public String optionInsert(@ModelAttribute(CASE_KEY) Eip05w020Case caseData,
                              @Validated(Eip05w020OptionCase.update.class) @ModelAttribute(OPTION_CASE_KEY) Eip05w020OptionCase optionCase,
                              BindingResult result) {
        try {
            log.debug("新增或修改選項");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                eip05w020Service.getOptionList(caseData,optionCase);
                return OPTION_PAGE;
            }
            eip05w020Service.insertUpdateOptionData(optionCase, optionCase.getMode());
            eip05w020Service.getOptionList(caseData,optionCase);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return OPTION_PAGE;
        }
        return OPTION_PAGE;
    }

    @RequestMapping("/Eip05w020_preview.action")
    public String preview(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("導向 預覽畫面");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            eip05w020Service.getPreviewData(caseData);
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return PREVIEW_PAGE;
    }

    @RequestMapping("/Eip05w020_contentQuery.action")
    public String contentQuery(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("導向 填寫內容查詢畫面");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            eip05w020Service.getContentQuery(caseData);
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return QUERY_PAGE;
    }

    @RequestMapping("/Eip05w020_writeContent.action")
    public String writeContent(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("導向 填寫內容表");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            caseData.setThemeCase(themeCase);
            String result = eip05w020Service.getWriteContents(caseData);
            if ("isEmpty".equals(result)) {
                eip05w020Service.getContentQuery(caseData);
                setSystemMessage(super.getQueryEmptyMessage());
                return QUERY_PAGE;
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return QUERY_PAGE;
        }
        return WRITECONTENT_PAGE;
    }

    @RequestMapping("/Eip05w020_printStatistics.action")
    public ModelAndView printStatistics(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 統計表");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            caseData.setThemeCase(themeCase);
            String result = eip05w020Service.getReviewStatistics(caseData);
            baos = eip05w020Service.prodStatistics(caseData);
            if ("isEmpty".equals(result) || baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip05w020Service.getOslist(caseData);
                return new ModelAndView(MAIN_PAGE);
            }
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP05W020L00","xls");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    @RequestMapping("/Eip05w020_printWriteContent.action")
    public ModelAndView printWriteContent(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 全部填寫內容(主畫面)");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            caseData.setThemeCase(themeCase);
            String result = eip05w020Service.getAllWriteContents(caseData);
            if ("isEmpty".equals(result)) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip05w020Service.getOslist(caseData);
                return new ModelAndView(MAIN_PAGE);
            }
            baos = eip05w020Service.prodWriteContent(caseData);
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP05W020L01","xls");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
            return new ModelAndView(MAIN_PAGE);
        }
    }

    @RequestMapping("/Eip05w020_printQueryWriteContent.action")
    public ModelAndView printQueryWriteContent(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        try {
            log.debug("匯出 選擇填寫內容(查詢)");
            Eip05w020ThemeCase themeCase = new Eip05w020ThemeCase();
            eip05w020Service.getSingleFormData(caseData, themeCase);
            caseData.setThemeCase(themeCase);
            String result = eip05w020Service.getWriteContents(caseData);
            if ("isEmpty".equals(result)) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                eip05w020Service.getContentQuery(caseData);
                return new ModelAndView(QUERY_PAGE);
            }
            baos = eip05w020Service.prodWriteContent(caseData);
            String filename = FilenameUtility.getFileName(userData.getUserId(), "EIP05W020L01","xls");
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.GENERAL_FILE));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getReportErrorMessage());
            return new ModelAndView(QUERY_PAGE);
        }
    }

    @RequestMapping("/Eip05w020_copy.action")
    public String copy(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("複製 意見調查主題");
            eip05w020Service.copyFormData(caseData);
            eip05w020Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getSaveFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getSaveSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w020_delete.action")
    public String delete(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("刪除 意見調查主題");
            eip05w020Service.deleteCheckedForm(caseData, "F");
            eip05w020Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w020_partDelete.action")
    public String partDelete(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("刪除 部分標題");
            eip05w020Service.deleteCheckedForm(caseData, "P");
            eip05w020Service.getPartList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return PARTLIST_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return PARTLIST_PAGE;
    }

    @RequestMapping("/Eip05w020_questionDelete.action")
    public String questionDelete(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("刪除 題目");
            eip05w020Service.deleteCheckedForm(caseData, "Q");
            eip05w020Service.getQuestionList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return QUESTIONLIST_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return QUESTIONLIST_PAGE;
    }

    @RequestMapping("/Eip05w020_optionDelete.action")
    public String optionDelete(@ModelAttribute(CASE_KEY) Eip05w020Case caseData,
                                 @Validated @ModelAttribute(OPTION_CASE_KEY) Eip05w020OptionCase optionCase) {
        try {
            log.debug("刪除 選項");
            caseData.setIseqnoList(optionCase.getIseqnoList());
            eip05w020Service.deleteCheckedForm(caseData, "I");
            eip05w020Service.getOptionList(caseData, optionCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return OPTION_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return OPTION_PAGE;
    }

    @RequestMapping("/Eip05w020_put.action")
    public String put(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("上架 意見調查表單");
            eip05w020Service.putForm(caseData);
            eip05w020Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w020_off.action")
    public String off(@ModelAttribute(CASE_KEY) Eip05w020Case caseData) {
        try {
            log.debug("下架 意見調查表單");
            eip05w020Service.offForm(caseData);
            eip05w020Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }
}
