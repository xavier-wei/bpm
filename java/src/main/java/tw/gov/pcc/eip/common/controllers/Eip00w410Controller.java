package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip00w410Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w410Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 線上報名分類管理Controller
 * @author Weith
 */
@Controller
public class Eip00w410Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w410Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w410Controller.class);
    private static final String MAIN_PAGE = "/eip00w410/eip00w410m";//主頁
    private static final String MODIFY_PAGE = "/eip00w410/eip00w410x";//明細頁

    @Autowired
    private Eip00w410Service eip00w410Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w410Case getEip00w410Case() {
        Eip00w410Case caseData = new Eip00w410Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip00w410_enter.action")
    public String enter() {
        log.debug("導向 Eip00w410 線上報名分類管理");
        return "redirect:/Eip00w410_initQuery.action";
    }

    @RequestMapping("/Eip00w410_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w410Case caseData) {
        try {
            eip00w410Service.getOrclassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w410_selectAction.action")
    public String selectAction(@ModelAttribute(CASE_KEY) Eip00w410Case caseData) {
        try {
            log.debug("導向 線上報名分類新增或修改畫面");
            if (StringUtils.equals("U",caseData.getMode())){
                eip00w410Service.getSingleClass(caseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }

    @RequestMapping("/Eip00w410_delete.action")
    public String deleteClass(@ModelAttribute(CASE_KEY) Eip00w410Case caseData) {
        try {
            log.debug("刪除 線上報名分類");
            eip00w410Service.deleteClass(caseData);
            eip00w410Service.getOrclassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w410_modify.action")
    public String modifyClass(@ModelAttribute(CASE_KEY) Eip00w410Case caseData) {
        try {
            log.debug("修改 線上報名分類");
            eip00w410Service.getOrclassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return MODIFY_PAGE;
        }
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w410_saveOrModify.action")
    public String saveOrModifyClass(@Validated @ModelAttribute(CASE_KEY) Eip00w410Case caseData, BindingResult result) {
        try {
            log.debug("儲存或更新線上報名分類");
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            if (StringUtils.equals(caseData.getMode(), "A")) {
                eip00w410Service.insertClass(caseData);
                setSystemMessage(getSaveSuccessMessage());
            } else if (StringUtils.equals(caseData.getMode(), "U")) {
                eip00w410Service.modifyClass(caseData);
                setSystemMessage(getUpdateSuccessMessage());
            }
            //重新取得分類
            eip00w410Service.getOrclassList(caseData);
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
