package tw.gov.pcc.eip.common.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip03w030Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 重要列管事項_解除列管
 * @author 2201009
 */

@Controller
@SessionAttributes({Eip03w030Controller.CASE_KEY})
public class Eip03w030Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip03w030Controller.class);

    public static final String CASE_KEY = "_eip03w030CController_caseData";
    public static final String MIX_CASE_KEY = "_eip03w030CController_mixCaseData";
    private static final String QUERY_PAGE =  "/eip03w030/eip03w030q";
    private static final String DETAIL_PAGE =  "/eip03w030/eip03w030x";

    @Autowired
    private Eip03w030Service eip03w030Service;

    @Autowired
    private UserBean userData;

    @ModelAttribute(CASE_KEY)
    public Eip03w030Case getEip03w030Case(){
        Eip03w030Case caseData = new Eip03w030Case();
        eip03w030Service.initDataList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }
    @ModelAttribute(MIX_CASE_KEY)
    public Eip03w030MixCase getEip03w030MixCase() {
        return new Eip03w030MixCase();
    }



    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip03w030_enter.action")
    public String enter(SessionStatus status) {
        log.debug("導向重要列管事項_解除列管作業");
        status.setComplete();
        return QUERY_PAGE;
    }

    /**
     * 依條件查詢重要列管事項
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip03w030_queryKeepTrk.action")
    public String queryKeepTrk(@Validated @ModelAttribute(CASE_KEY) Eip03w030Case caseData) {
        try {
            eip03w030Service.queryKeepTrk(caseData);
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return QUERY_PAGE;
    }

    /**
     * 依條件查詢重要列管事項  for Detail
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip03w030_queryKeepTrkForDetail.action")
    public String queryKeepTrkForDetail(@ModelAttribute(CASE_KEY) Eip03w030Case caseData, @ModelAttribute(MIX_CASE_KEY)Eip03w030MixCase mixCase) {
        try {
            eip03w030Service.queryKeepTrkDetail(caseData, mixCase);
            return DETAIL_PAGE;
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return QUERY_PAGE;
    }

    /**
     * 儲存
     * @param mixCase
     * @return
     */
    @RequestMapping("/Eip03w030_update.action")
    public String update(@Validated@ModelAttribute(MIX_CASE_KEY)Eip03w030MixCase mixCase) {

        try {
            eip03w030Service.update(userData, mixCase);
            setSystemMessage(getUpdateSuccessMessage());
            //儲存成功後，回到原畫面
            return "redirect:/Eip03w030_queryKeepTrkForDetail.action";
        }catch (Exception e){
            log.error("更新失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return QUERY_PAGE;
    }
}
