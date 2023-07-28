package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w520Service;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 意見調查主題列表Controller
 * @author Weith
 */
@Controller
public class Eip00w520Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w520Controller_caseData";
    public static final String THEME_CASE_KEY = "_eip00w520Controller_themeCaseData";
    public static final String PART_CASE_KEY = "_eip00w520Controller_partCaseData";
    public static final String QUESTION_CASE_KEY = "_eip00w520Controller_questionCaseData";
    public static final String OPTION_CASE_KEY = "_eip00w520Controller_optionCaseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w520Controller.class);
    private static final String MAIN_PAGE = "/eip00w520/eip00w520m";//主頁
    private static final String THEME_PAGE = "/eip00w520/eip00w520a";//主題新增修改頁
    private static final String REVIEW_PAGE = "/eip00w520/eip00w521x";//檢視統計表
    private static final String WRITECONTENT_PAGE = "/eip00w520/eip00w527x";//檢視填寫內容表
    private static final String PREVIEW_PAGE = "/eip00w520/eip00w522x";//預覽
    private static final String PARTLIST_PAGE = "/eip00w520/eip00w523m";//部分列表
    private static final String PART_PAGE = "/eip00w520/eip00w523a";//新增修改部分標題
    private static final String QUESTIONLIST_PAGE = "/eip00w520/eip00w524m";//子部分列表
    private static final String QUESTION_PAGE = "/eip00w520/eip00w524a";//新增修改題目
    private static final String OPTION_PAGE = "/eip00w520/eip00w525m";//新增修改刪除題目
    private static final String QUERY_PAGE = "/eip00w520/eip00w526m";//填寫內容查詢

    @Autowired
    private UserBean userData;

    @Autowired
    private Eip00w520Service eip00w520Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w520Case getEip00w520Case() {
        return new Eip00w520Case();
    }

    @ModelAttribute(THEME_CASE_KEY)
    public Eip00w520ThemeCase getEip00w520ThemeCase() {
        return new Eip00w520ThemeCase();
    }

    @ModelAttribute(PART_CASE_KEY)
    public Eip00w520PartCase getEip00w520PartCase() {
        return new Eip00w520PartCase();
    }

    @ModelAttribute(QUESTION_CASE_KEY)
    public Eip00w520QuestionCase getEip00w520QuestionCase() {
        return new Eip00w520QuestionCase();
    }

    @RequestMapping("/Eip00w520_enter.action")
    public String enter() {
        log.debug("導向 Eip00w520 意見調查主題列表");
        return "redirect:/Eip00w520_initQuery.action";
    }

    @RequestMapping("/Eip00w520_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_review.action")
    public String review(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("導向 檢視畫面");
            Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
            eip00w520Service.getSingleFormData(caseData, themeCase);
            String result = eip00w520Service.getReviewStatistics(caseData);
            if ("isEmpty".equals(result)) {
                eip00w520Service.getOslist(caseData);
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

    @RequestMapping("/Eip00w520_selectInsertUpdate.action")
    public String selectInsertUpdate(@ModelAttribute(CASE_KEY) Eip00w520Case caseData, @ModelAttribute(THEME_CASE_KEY) Eip00w520ThemeCase themeCaseData) {
        try {
            log.debug("導向 新增或修改畫面");
            eip00w520Service.init(caseData);
            if ("A".equals(caseData.getMode())) {
                log.debug("導向新增畫面");
                eip00w520Service.getInsertFormData(themeCaseData);
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向更新畫面");
                eip00w520Service.getSingleFormData(caseData, themeCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getOslist(caseData);
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return THEME_PAGE;
    }

    @RequestMapping("/Eip00w520_confirm.action")
    public String confirm(@ModelAttribute(CASE_KEY) Eip00w520Case caseData,
                          @Validated @ModelAttribute(THEME_CASE_KEY) Eip00w520ThemeCase themeCaseData,
                          BindingResult result) {
        try {
            log.debug("新增或修改意見調查表單");
            eip00w520Service.init(caseData);
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            eip00w520Service.advancedValidate(themeCaseData, result);
            if (result.hasErrors()) {
                return THEME_PAGE;
            }
            eip00w520Service.insertUpdateData(themeCaseData, caseData.getMode());
            eip00w520Service.getOslist(caseData);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return THEME_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_partList.action")
    public String partList(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("導向 意見調查部分列表");
            eip00w520Service.getPartList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getOslist(caseData);
            setSystemMessage(getQueryFailMessage());
            return MAIN_PAGE;
        }
        return PARTLIST_PAGE;
    }

    @RequestMapping("/Eip00w520_selectPartInsertUpdate.action")
    public String selectPartInsertUpdate(@ModelAttribute(CASE_KEY) Eip00w520Case caseData, @ModelAttribute(PART_CASE_KEY) Eip00w520PartCase partCaseData) {
        try {
            log.debug("導向 部分列表新增或修改畫面");
            if ("A".equals(caseData.getMode())) {
                partCaseData.clear();
                log.debug("導向部分列表新增畫面");
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向部分列表修改畫面");
                eip00w520Service.getSinglePartData(caseData, partCaseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getPartList(caseData);
            setSystemMessage(getQueryFailMessage());
            return PARTLIST_PAGE;
        }
        return PART_PAGE;
    }

    @RequestMapping("/Eip00w520_partConfirm.action")
    public String partConfirm(@ModelAttribute(CASE_KEY) Eip00w520Case caseData,
                          @Validated @ModelAttribute(PART_CASE_KEY) Eip00w520PartCase partCaseData,
                          BindingResult result) {
        try {
            log.debug("新增或修改部分標題");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                return PART_PAGE;
            }
            String processMsg = eip00w520Service.insertUpdatePartData(partCaseData, partCaseData.getMode());
            eip00w520Service.getPartList(caseData);
            setSystemMessage(StringUtils.isBlank(processMsg) ? msg : processMsg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return PART_PAGE;
        }
        return PARTLIST_PAGE;
    }

    @RequestMapping("/Eip00w520_questionList.action")
    public String questionList(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("導向 子部分(題目)列表");
            eip00w520Service.getQuestionList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            eip00w520Service.getPartList(caseData);
            return PARTLIST_PAGE;
        }
        return QUESTIONLIST_PAGE;
    }

    @RequestMapping("/Eip00w520_selectQuestionInsertUpdate.action")
    public String selectQuestionInsertUpdate(@ModelAttribute(CASE_KEY) Eip00w520Case caseData, @ModelAttribute(QUESTION_CASE_KEY) Eip00w520QuestionCase questionCase) {
        try {
            log.debug("導向 子部分列表新增或修改畫面");
            if ("A".equals(caseData.getMode())) {
                log.debug("導向子部分列表新增畫面");
            }
            if ("U".equals(caseData.getMode())) {
                log.debug("導向子部分列表修改畫面");
                eip00w520Service.getSingleQuestionData(caseData, questionCase);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getQuestionList(caseData);
            setSystemMessage(getQueryFailMessage());
            return QUESTIONLIST_PAGE;
        }
        return QUESTION_PAGE;
    }

    @RequestMapping("/Eip00w520_questionConfirm.action")
    public String questionConfirm(@ModelAttribute(CASE_KEY) Eip00w520Case caseData,
                              @Validated @ModelAttribute(QUESTION_CASE_KEY) Eip00w520QuestionCase questionCase,
                              BindingResult result) {
        try {
            log.debug("新增或修改題目");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                return QUESTION_PAGE;
            }
            eip00w520Service.insertUpdateQuestionData(questionCase, caseData.getMode());
            eip00w520Service.getQuestionList(caseData);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(failmsg);
            return QUESTION_PAGE;
        }
        return QUESTIONLIST_PAGE;
    }

    @RequestMapping("/Eip00w520_optionList.action")
    public String optionList(@ModelAttribute(CASE_KEY) Eip00w520Case caseData, @ModelAttribute(OPTION_CASE_KEY) Eip00w520OptionCase optionCase) {
        try {
            log.debug("導向 部分列表選項");
            eip00w520Service.getInsertOptionData(caseData, optionCase);
            eip00w520Service.getOptionList(caseData, optionCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getQuestionList(caseData);
            setSystemMessage(getQueryFailMessage());
            return OPTION_PAGE;
        }
        return OPTION_PAGE;
    }

    @RequestMapping("/Eip00w520_optionInsertUpdate.action")
    public String optionInsert(@ModelAttribute(CASE_KEY) Eip00w520Case caseData,
                              @Validated @ModelAttribute(OPTION_CASE_KEY) Eip00w520OptionCase optionCase,
                              BindingResult result) {
        try {
            log.debug("新增或修改選項");
            String msg = "A".equals(caseData.getMode()) ? getSaveSuccessMessage() : getUpdateSuccessMessage();
            if (result.hasErrors()) {
                eip00w520Service.getOptionList(caseData,optionCase);
                return OPTION_PAGE;
            }
            eip00w520Service.insertUpdateOptionData(optionCase, optionCase.getMode());
            eip00w520Service.getOptionList(caseData,optionCase);
            setSystemMessage(msg);
        } catch (Exception e) {
            String failmsg = "A".equals(caseData.getMode()) ? getSaveFailMessage() : getUpdateFailMessage();
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getOptionList(caseData,optionCase);
            setSystemMessage(failmsg);
            return OPTION_PAGE;
        }
        return OPTION_PAGE;
    }

    @RequestMapping("/Eip00w520_preview.action")
    public String preview(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("導向 預覽畫面");
            Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
            eip00w520Service.getSingleFormData(caseData, themeCase);
            eip00w520Service.getPreviewData(caseData);
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return PREVIEW_PAGE;
    }

    @RequestMapping("/Eip00w520_contentQuery.action")
    public String contentQuery(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("導向 填寫內容查詢畫面");
            Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
            eip00w520Service.getSingleFormData(caseData, themeCase);
            eip00w520Service.getContentQuery(caseData);
            caseData.setThemeCase(themeCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(super.getQueryFailMessage());
            return MAIN_PAGE;
        }
        return QUERY_PAGE;
    }

    @RequestMapping("/Eip00w520_writeContent.action")
    public String writeContent(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("導向 填寫內容統計表");
            Eip00w520ThemeCase themeCase = new Eip00w520ThemeCase();
            eip00w520Service.getSingleFormData(caseData, themeCase);
            caseData.setThemeCase(themeCase);
            eip00w520Service.getWriteContents(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getContentQuery(caseData);
            setSystemMessage(super.getQueryFailMessage());
            return QUERY_PAGE;
        }
        return WRITECONTENT_PAGE;
    }

    @RequestMapping("/Eip00w520_copy.action")
    public String copy(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("複製 意見調查主題");
            eip00w520Service.copyFormData(caseData);
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getSaveFailMessage());
            eip00w520Service.getOslist(caseData);
            return MAIN_PAGE;
        }
        setSystemMessage(getSaveSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_delete.action")
    public String delete(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("刪除 意見調查主題");
            eip00w520Service.deleteCheckedForm(caseData, "F");
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            eip00w520Service.getOslist(caseData);
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_partDelete.action")
    public String partDelete(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("刪除 部分標題");
            eip00w520Service.deleteCheckedForm(caseData, "P");
            eip00w520Service.getPartList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getPartList(caseData);
            setSystemMessage(getDeleteFailMessage());
            return PARTLIST_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return PARTLIST_PAGE;
    }

    @RequestMapping("/Eip00w520_questionDelete.action")
    public String questionDelete(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("刪除 題目");
            eip00w520Service.deleteCheckedForm(caseData, "Q");
            eip00w520Service.getQuestionList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getQuestionList(caseData);
            setSystemMessage(getDeleteFailMessage());
            return QUESTIONLIST_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return QUESTIONLIST_PAGE;
    }

    @RequestMapping("/Eip00w520_optionDelete.action")
    public String optionDelete(@ModelAttribute(CASE_KEY) Eip00w520Case caseData,
                                 @Validated @ModelAttribute(OPTION_CASE_KEY) Eip00w520OptionCase optionCase) {
        try {
            log.debug("刪除 選項");
            caseData.setIseqnoList(optionCase.getIseqnoList());
            eip00w520Service.deleteCheckedForm(caseData, "I");
            eip00w520Service.getOptionList(caseData, optionCase);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            eip00w520Service.getOptionList(caseData, optionCase);
            setSystemMessage(getDeleteFailMessage());
            return OPTION_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return OPTION_PAGE;
    }

    @RequestMapping("/Eip00w520_put.action")
    public String put(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("上架 意見調查表單");
            eip00w520Service.putForm(caseData);
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w520_off.action")
    public String off(@ModelAttribute(CASE_KEY) Eip00w520Case caseData) {
        try {
            log.debug("下架 意見調查表單");
            eip00w520Service.offForm(caseData);
            eip00w520Service.getOslist(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }
}
