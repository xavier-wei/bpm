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
import tw.gov.pcc.eip.common.cases.Eip06w060Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip06w060Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * 會議室管理
 * @author 2207003
 */
@Controller
public class Eip06w060Controller extends BaseController {
    public static final String CASE_KEY = "_eip06w060Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip06w060Controller.class);
    private static final String MAIN_PAGE = "/eip06w060/eip06w060m";//主頁
    private static final String QUERY_PAGE = "/eip06w060/eip06w060q";//查詢頁

    private static final String INSERT_PAGE =  "/eip06w060/eip06w060a"; //新增頁

    @Autowired
    private Eip06w060Service eip06w060Service;

    @ModelAttribute(CASE_KEY)
    public Eip06w060Case getEip06w060Case() {
        Eip06w060Case caseData = new Eip06w060Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    @RequestMapping("/Eip06w060_enter.action")
    public String enter() {
        log.debug("導向 Eip06w060 會議室管理");
        return "redirect:/Eip06w060_initQuery.action";
    }

    @RequestMapping("/Eip06w060_initQuery.action")
    public String initQuery(@ModelAttribute(CASE_KEY) Eip06w060Case caseData) {
        try {
            eip06w060Service.getMeetingRoomList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip06w060_roomIsable.action")
    public String isable(@ModelAttribute(CASE_KEY) Eip06w060Case caseData) {
        try {
            log.debug("啟用/禁用 會議室管理");
            //狀態切換
            eip06w060Service.getIsable(caseData);
            //清空相關資料
            eip06w060Service.deleteClass(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        //重新取得狀態
        eip06w060Service.getMeetingRoomList(caseData);
        setSystemMessage(getUpdateSuccessMessage());
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip06w060_partisable.action")
    public String partisable(@ModelAttribute(CASE_KEY) Eip06w060Case caseData) {
        try {
            log.debug("部分啟用 會議室管理");
            eip06w060Service.getRoomIsableList(caseData);
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return QUERY_PAGE;
    }

    @RequestMapping("/Eip06w060_delete.action")
    public String deleteClass(@ModelAttribute(CASE_KEY) Eip06w060Case caseData) {
        try {
            log.debug("刪除 啟用時間");
            eip06w060Service.deleteClass(caseData);
            setSystemMessage(getDeleteSuccessMessage());
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return MAIN_PAGE;
        }
        return MAIN_PAGE;
    }

    @RequestMapping("/Eip06w060_deleteSingle.action")
    public String deleteSingleClass(@ModelAttribute(CASE_KEY) Eip06w060Case caseData) {
        try {
            log.debug("刪除 指定啟用時間");
            eip06w060Service.deleteSingleClass(caseData);
            setSystemMessage(getDeleteSuccessMessage());
        } catch (Exception e) {
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
            return QUERY_PAGE;
        }
        //重新取得狀態
        eip06w060Service.getRoomIsableList(caseData);
        return QUERY_PAGE;
    }

    @RequestMapping("/Eip06w060_inster.action")
    public String insterClass(@ModelAttribute(CASE_KEY) Eip06w060Case caseData){
        try{
            log.debug("新增 指定啟用時間");
            eip06w060Service.initSelectBeginList(caseData); //時間開始
            eip06w060Service.initSelectEndList(caseData);//時間結束
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return INSERT_PAGE;
    }


    @RequestMapping("/Eip06w060_save.action")
    public String saveClass(@Validated @ModelAttribute(CASE_KEY) Eip06w060Case caseData, BindingResult result){
        try{
            log.debug("儲存 指定啟用時間");
            eip06w060Service.initSelectBeginList(caseData); //時間開始
            eip06w060Service.initSelectEndList(caseData);//時間結束

            eip06w060Service.validate(caseData, result);
            eip06w060Service.isableTime(caseData, result);//檢查 會議室啟用區間是否重複
            if (result.hasErrors()){
                return INSERT_PAGE;
            }
            if(caseData.isRepeat()){
                //重複
                eip06w060Service.multiInsertClass(caseData);
            }else {
                //不重複
                eip06w060Service.insertClass(caseData);
            }
            setSystemMessage(getSaveSuccessMessage());
            //重新取得狀態
            eip06w060Service.getRoomIsableList(caseData);
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getSaveFailMessage());
            return INSERT_PAGE;
        }
        return QUERY_PAGE;
    }



}
