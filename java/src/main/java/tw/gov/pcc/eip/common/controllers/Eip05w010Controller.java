package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip05w010Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip05w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 意見調查分類列表Controller
 * @author Weith
 */
@Controller
public class Eip05w010Controller extends BaseController {
    public static final String CASE_KEY = "_eip05w010Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip05w010Controller.class);
    private static final String MAIN_PAGE = "/eip05w010/eip05w010m";//主頁
    private static final String MODIFY_PAGE = "/eip05w010/eip05w010x";//新增修改頁

    @Autowired
    private Eip05w010Service eip05w010Service;

    @ModelAttribute(CASE_KEY)
    public Eip05w010Case getEip05w010Case() {
        Eip05w010Case caseData = new Eip05w010Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip05w010_enter.action")
    public String enter() {
        log.debug("導向 Eip05w010 意見調查分類列表");
        return "redirect:/Eip05w010_initQuery.action";
    }

    @RequestMapping("/Eip05w010_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip05w010Case caseData) {
        try {
            eip05w010Service.getClassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w010_selectAction.action")
    public String selectAction(@ModelAttribute(CASE_KEY) Eip05w010Case caseData) {
        try {
            log.debug("導向 線上報名分類新增或修改畫面");
            if (StringUtils.equals("U",caseData.getMode())){
                eip05w010Service.getSingleClass(caseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }

    @RequestMapping("/Eip05w010_saveOrModify.action")
    public String saveOrModifyClass(@Validated @ModelAttribute(CASE_KEY) Eip05w010Case caseData, BindingResult result) {
        try {
            log.debug("儲存或更新意見調查分類");
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            if (StringUtils.equals(caseData.getMode(), "A")) {
                eip05w010Service.insertClass(caseData);
                setSystemMessage(getSaveSuccessMessage());
            } else if (StringUtils.equals(caseData.getMode(), "U")) {
                eip05w010Service.modifyClass(caseData);
                setSystemMessage(getUpdateSuccessMessage());
            }
            //重新取得分類
            eip05w010Service.getClassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            if (StringUtils.equals(caseData.getMode(), "A")) {
                setSystemMessage(getSaveFailMessage());
            } else if (StringUtils.equals(caseData.getMode(), "U")) {
                setSystemMessage(getUpdateFailMessage());
            }
            return MODIFY_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip05w010_delete.action")
    public String deleteClass(@ModelAttribute(CASE_KEY) Eip05w010Case caseData) {
        try {
            log.debug("刪除 線上報名分類");
            eip05w010Service.deleteClass(caseData);
            eip05w010Service.getClassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }
}
