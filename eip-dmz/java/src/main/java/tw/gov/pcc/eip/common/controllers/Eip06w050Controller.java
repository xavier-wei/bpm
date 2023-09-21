package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.common.cases.Eip06w050Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip06w050Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * 會議室參數維護
 * @author 2207003
 */
@Controller
public class Eip06w050Controller extends BaseController {
    public static final String CASE_KEY = "_eip06w050Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip06w050Controller.class);
    private static final String MAIN_PAGE = "/eip06w050/eip06w050m";//主頁

    private static final String MODIFY_PAGE = "/eip06w050/eip06w050x";//明細頁

    @Autowired
    private Eip06w050Service eip06w050Service;

    @ModelAttribute(CASE_KEY)
    public Eip06w050Case getEip06w050Case() {
        Eip06w050Case caseData = new Eip06w050Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip06w050_enter.action")
    public String enter() {
        log.debug("導向 Eip06w050 會議室參數維護");
        return "redirect:/Eip06w050_initQuery.action";
    }

    @RequestMapping("/Eip06w050_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip06w050Case caseData) {
        try {
            eip06w050Service.initializeOption(caseData);
            eip06w050Service.getMeetingCodeList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip06w050_query.action")
    public String query(@ModelAttribute(CASE_KEY) Eip06w050Case caseData) {
        try {
            eip06w050Service.initializeOption(caseData);
            eip06w050Service.getMeetingCodeAndItemTypList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }



    @RequestMapping("/Eip06w050_selectAction.action")
    public String selectAction(@ModelAttribute(CASE_KEY) Eip06w050Case caseData) {
        try {
            log.debug("新增 會議室參數維護");
            //新增時移除D(預約天數)、FX(會議室取消)，已包含於F(會議室邏輯)
            Map<String, String> map =  eip06w050Service.initializeOptionAll(caseData);
            map.entrySet().removeIf(entry -> entry.getKey().equals("D")|| entry.getKey().equals("FX"));
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MODIFY_PAGE;
    }


    @RequestMapping("/Eip06w050_modify.action")
    public String modifyClass(@ModelAttribute(CASE_KEY) Eip06w050Case caseData) {
        try {
            log.debug("修改 會議室參數維護");
            eip06w050Service.initializeOptionAll(caseData);
            eip06w050Service.getSingleClass(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return MODIFY_PAGE;
    }

    /**
     * 提示確認視窗
     * @param caseData
     * @return alert相關資訊
     */

    @RequestMapping("/Eip06w050_showAlert.action")
    @ResponseBody
    public Map<String, String> showAlert(@RequestBody Eip06w050Case caseData) {
        Map<String, String> map = new HashMap<>();
        String itemIdIsUse = eip06w050Service.deleteClass(caseData);
        map.put("itemIdIsUse", itemIdIsUse);
        return ObjectUtility.normalizeObject(map);
    }


    @RequestMapping("/Eip06w050_delete.action")
    public String deleteClass(@ModelAttribute(CASE_KEY) Eip06w050Case caseData) {
        try {
            log.debug("刪除 會議室參數維護");
            eip06w050Service.getMeetingCodeList(caseData);
            setSystemMessage(getDeleteSuccessMessage());
            eip06w050Service.initializeOption(caseData);

        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        return MAIN_PAGE;
    }


    @RequestMapping("/Eip06w050_saveMeetingCodeModify.action")
    public String saveMeetingCodeModifyClass(@Validated @ModelAttribute(CASE_KEY) Eip06w050Case caseData, BindingResult result) {
        try {
            log.debug("儲存或更新 會議室參數維護");
            eip06w050Service.initializeOption(caseData);
            if (result.hasErrors()) {
                return MODIFY_PAGE;
            }
            if (StringUtils.equals(caseData.getMode(), "A")) {
                eip06w050Service.itemIsUse(caseData, result);
                if (result.hasErrors()) {
                    return MODIFY_PAGE;
                }
                eip06w050Service.insertClass(caseData);
                setSystemMessage(getSaveSuccessMessage());
            } else if (StringUtils.equals(caseData.getMode(), "U")) {
                //檢查名稱是否重複，名稱無更新，不需檢查是否重複
                String updateResult= eip06w050Service.updateClass(caseData);
                  if("Y".equals(updateResult)){
                      eip06w050Service.itemIsUse(caseData, result);
                  }
                if (result.hasErrors()) {
                    return MODIFY_PAGE;
                }
                eip06w050Service.modifyClass(caseData);
                setSystemMessage(getUpdateSuccessMessage());
            }
            //重新取得分類
            eip06w050Service.getMeetingCodeList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            if (StringUtils.equals(caseData.getMode(), "A")) {
             setSystemMessage(getSaveFailMessage());
            }
            else if (StringUtils.equals(caseData.getMode(), "U")) {
                setSystemMessage(getUpdateFailMessage());
            }
            return MODIFY_PAGE;
        }
        return MAIN_PAGE;

    }
}
