package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip04w010Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip04w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 線上報名分類管理Controller
 * @author Weith
 */
@Controller
public class Eip04w010Controller extends BaseController {
    public static final String CASE_KEY = "_eip04w010Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip04w010Controller.class);
    private static final String MAIN_PAGE = "/eip04w010/eip04w010m";//主頁
    private static final String MODIFY_PAGE = "/eip04w010/eip04w010x";//明細頁

    @Autowired
    private Eip04w010Service eip04w010Service;

    @ModelAttribute(CASE_KEY)
    public Eip04w010Case getEip04w010Case() {
        Eip04w010Case caseData = new Eip04w010Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip04w010_enter.action")
    public String enter() {
        log.debug("導向 Eip04w010 線上報名分類管理");
        return "redirect:/Eip04w010_initQuery.action";
    }

    @RequestMapping("/Eip04w010_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip04w010Case caseData) {
        try {
            eip04w010Service.getOrclassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w010_selectAction.action")
    public String selectAction(@ModelAttribute(CASE_KEY) Eip04w010Case caseData) {
        try {
            log.debug("導向 線上報名分類新增或修改畫面");
            if (StringUtils.equals("U",caseData.getMode())){
                eip04w010Service.getSingleClass(caseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }

    @RequestMapping("/Eip04w010_delete.action")
    public String deleteClass(@ModelAttribute(CASE_KEY) Eip04w010Case caseData) {
        try {
            log.debug("刪除 線上報名分類");
            eip04w010Service.deleteClass(caseData);
            eip04w010Service.getOrclassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w010_modify.action")
    public String modifyClass(@ModelAttribute(CASE_KEY) Eip04w010Case caseData) {
        try {
            log.debug("修改 線上報名分類");
            eip04w010Service.getOrclassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MODIFY_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip04w010_saveOrModify.action")
    public String saveOrModifyClass(@Validated @ModelAttribute(CASE_KEY) Eip04w010Case caseData, BindingResult result) {
        try {
            log.debug("儲存或更新線上報名分類");
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            if (StringUtils.equals(caseData.getMode(), "A")) {
                eip04w010Service.insertClass(caseData);
                setSystemMessage(getSaveSuccessMessage());
            } else if (StringUtils.equals(caseData.getMode(), "U")) {
                eip04w010Service.modifyClass(caseData);
                setSystemMessage(getUpdateSuccessMessage());
            }
            //重新取得分類
            eip04w010Service.getOrclassList(caseData);
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
}
