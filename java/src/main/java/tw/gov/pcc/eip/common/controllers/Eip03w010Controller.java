package tw.gov.pcc.eip.common.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tw.gov.pcc.eip.adm.controllers.Eip00w040Controller;
import tw.gov.pcc.eip.common.cases.Eip03w010Case;
import tw.gov.pcc.eip.common.cases.Eip03w010MixCase;
import tw.gov.pcc.eip.common.cases.Eip03w030Case;
import tw.gov.pcc.eip.common.cases.Eip03w030MixCase;
import tw.gov.pcc.eip.dao.KeepTrkDtlDao;
import tw.gov.pcc.eip.dao.KeepTrkMstDao;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip03w010Service;
import tw.gov.pcc.eip.services.Eip03w030Service;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 重要列管事項_重要列管事項維護
 * @author 2201009
 */

@Controller
@SessionAttributes({Eip03w010Controller.CASE_KEY})
public class Eip03w010Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip03w010Controller.class);

    public static final String CASE_KEY = "_eip03w010CController_caseData";
    public static final String MIX_CASE_KEY = "_eip03w010CController_mixCaseData";
    private static final String QUERY_PAGE =  "/eip03w010/eip03w010q";
    private static final String INSERT_PAGE = "/eip03w010/eip03w010a";
    private static final String MODIFY_PAGE = "/eip03w010/eip03w010c";
    private static final String DETAIL_PAGE = "/eip03w010/eip03w010x";
    @Autowired
    private UserBean userData;
    @Autowired
    private Eip03w010Service eip03w010Service;
    @Autowired
    private Eip03w030Service eip03w030Service;
    @Autowired
    private KeepTrkDtlDao keepTrkDtlDao;

    @ModelAttribute(CASE_KEY)
    public Eip03w010Case getEip03w010Case(){
        Eip03w010Case caseData = new Eip03w010Case();
        eip03w010Service.initDataList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip03w010_enter.action")
    public String enter() {
        log.debug("導向重要列管事項_重要列管事項維護作業");
        getEip03w010Case();
        return QUERY_PAGE;
    }

    /**
     * 依條件查詢重要列管事項
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip03w010_queryKeepTrk.action")
    public String queryKeepTrk(@Validated @ModelAttribute(CASE_KEY) Eip03w010Case caseData, BindingResult result) {
        try {
            if(result.hasErrors()){
                return QUERY_PAGE;
            }
            eip03w010Service.queryKeepTrk(caseData);
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return QUERY_PAGE;
    }

    /**
     * 導回查詢頁
     *
     */
    @RequestMapping("/Eip03w010_backHome.action")
    public String backHome(SessionStatus status) {
        log.debug("導回查詢頁");
        status.setComplete();
        return "redirect:/Eip03w010_enter.action";
    }


    /**
     * 導至新增列管事項頁面
     * @return
     */
    @RequestMapping("/Eip03w010_forwardToInsert.action")
    public String forwardToInsert(@ModelAttribute(CASE_KEY) Eip03w010Case caseData){
        String today = DateUtility.getNowChineseDate();
        caseData.setTrkID(keepTrkDtlDao.getNextTrkIDNumber(today));
        caseData.setAllStDt(today);
        caseData.setTrkCont("");
        caseData.setTrkFrom("");
        caseData.setCreDept(userData.getDeptId());
        caseData.setCreUser(userData.getUserId());
        caseData.setTrkObjCombobox(eip03w010Service.getAllTrkObj());
        caseData.setTrkFromCombobox(eip03w010Service.getAllTrkFrom());
        return INSERT_PAGE;
    }

    /**
     * 導至修改列管事項頁面
     * @return
     */
    @RequestMapping("/Eip03w010_forwardToModify.action")
    public String forwardToModify(@ModelAttribute(CASE_KEY) Eip03w010Case caseData){
        eip03w010Service.queryKeepTrkDetailByTrkID(caseData);
        return MODIFY_PAGE;
    }

    /**
     * 導至明細列管事項頁面
     * @return
     */
    @RequestMapping("/Eip03w010_forwardToDetail.action")
    public String forwardToDetail(@ModelAttribute(CASE_KEY) Eip03w010Case caseData, @ModelAttribute(MIX_CASE_KEY)Eip03w010MixCase mixCase){
        try {
            eip03w010Service.queryKeepTrkDetail(caseData, mixCase);
            return DETAIL_PAGE;
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return QUERY_PAGE;
    }

    /**
     * 刪除暫存資料
     * @return
     */
    @RequestMapping("/Eip03w010_forwardToDelete.action")
    public String forwardToDelete(@ModelAttribute(CASE_KEY) Eip03w010Case caseData){
        try {
            eip03w010Service.deleteMstAndDtl(caseData.getSelectedTrkID());
            setSystemMessage(getDeleteSuccessMessage());
            return "redirect:/Eip03w010_backHome.action";
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
        }
        return "redirect:/Eip03w010_enter.action";
    }

    /**
     * 作廢資料
     * @return
     */
    @RequestMapping("/Eip03w010_forwardToCancel.action")
    public String forwardToCancel(@ModelAttribute(CASE_KEY) Eip03w010Case caseData){
        try {
            eip03w010Service.cancelMst(caseData.getSelectedTrkID());

            setSystemMessage(getUpdateSuccessMessage());
            return "redirect:/Eip03w010_backHome.action";
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return "redirect:/Eip03w010_enter.action";
    }

    /**
     * 新增列管事項
     * @return
     */
    @RequestMapping("/Eip03w010_insert.action")
    public String insert(@Validated @ModelAttribute(CASE_KEY) Eip03w010Case caseData, BindingResult result) throws JsonProcessingException {
        try {
            if(result.hasErrors()){
                return INSERT_PAGE;
            }
            eip03w010Service.insert(caseData, userData);
            setSystemMessage(getSaveSuccessMessage());
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        caseData.clear();
        eip03w010Service.initDataList(caseData);
        return "redirect:/Eip03w010_enter.action";
    }

    /**
     * 新增列管事項
     * @return
     */
    @RequestMapping("/Eip03w010_update.action")
    public String update(@Validated @ModelAttribute(CASE_KEY) Eip03w010Case caseData, BindingResult result, SessionStatus status) throws JsonProcessingException {
        try {
            if(result.hasErrors()){
                return MODIFY_PAGE;
            }
            eip03w010Service.update(caseData, userData);
            status.setComplete();
            setSystemMessage(getUpdateSuccessMessage());
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        caseData.clear();
        eip03w010Service.initDataList(caseData);
        return "redirect:/Eip03w010_enter.action";
    }
}
