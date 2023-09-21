package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eip00w510Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w510Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 意見調查分類列表Controller
 * @author Weith
 */
@Controller
public class Eip00w510Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w510Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w510Controller.class);
    private static final String MAIN_PAGE = "/eip00w510/eip00w510m";//主頁
    private static final String MODIFY_PAGE = "/eip00w510/eip00w510x";//新增修改頁

    @Autowired
    private Eip00w510Service eip00w510Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w510Case getEip00w510Case() {
        Eip00w510Case caseData = new Eip00w510Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip00w510_enter.action")
    public String enter() {
        log.debug("導向 Eip00w510 意見調查分類列表");
        return "redirect:/Eip00w510_initQuery.action";
    }

    @RequestMapping("/Eip00w510_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip00w510Case caseData) {
        try {
            eip00w510Service.getClassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip00w510_selectAction.action")
    public String selectAction(@ModelAttribute(CASE_KEY) Eip00w510Case caseData) {
        try {
            log.debug("導向 線上報名分類新增或修改畫面");
            if (StringUtils.equals("U",caseData.getMode())){
                eip00w510Service.getSingleClass(caseData);
            }
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }

    @RequestMapping("/Eip00w510_saveOrModify.action")
    public String saveOrModifyClass(@Validated @ModelAttribute(CASE_KEY) Eip00w510Case caseData, BindingResult result) {
        try {
            log.debug("儲存或更新意見調查分類");
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            if (StringUtils.equals(caseData.getMode(), "A")) {
                eip00w510Service.insertClass(caseData);
                setSystemMessage(getSaveSuccessMessage());
            } else if (StringUtils.equals(caseData.getMode(), "U")) {
                eip00w510Service.modifyClass(caseData);
                setSystemMessage(getUpdateSuccessMessage());
            }
            //重新取得分類
            eip00w510Service.getClassList(caseData);
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

    @RequestMapping("/Eip00w510_delete.action")
    public String deleteClass(@ModelAttribute(CASE_KEY) Eip00w510Case caseData) {
        try {
            log.debug("刪除 線上報名分類");
            eip00w510Service.deleteClass(caseData);
            eip00w510Service.getClassList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        setSystemMessage(getDeleteSuccessMessage());
        return MAIN_PAGE;
    }
}
