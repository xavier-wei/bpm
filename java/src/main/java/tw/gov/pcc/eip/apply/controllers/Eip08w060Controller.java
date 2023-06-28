package tw.gov.pcc.eip.apply.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.apply.service.Eip08w060Service;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * 設定系統管理員
 *
 * @author York
 */
@Controller
@SessionAttributes({Eip08w060Controller.CASE_KEY})
public class Eip08w060Controller extends BaseController {
    public static final String CASE_KEY = "_eip08w060_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w060Controller.class);
    private static final String QUERY_PAGE = "/eip08w060/eip08w060q";//選擇頁
    private static final String ADD_APGE = "/eip08w060/eip08w060a";//新增頁
    private static final String QUERY_DATA_APGE = "/eip08w060/eip08w060x";//查詢頁
    private static final String Details_DATA_APGE = "/eip08w060/eip08w060d";//明細頁

    @Autowired
    private Eip08w060Service eip08W060Service;

    @Autowired
    private UserBean userData;

    @ModelAttribute(CASE_KEY)
    public Eip08w060Case getEip08W060Case() {
        Eip08w060Case caseData = new Eip08w060Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip08w060_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip08w060Case caseData) {
        log.debug("導向 adm0w010_enter 設定系統管理員");
        caseData.setUser(userData.getUserId());
        caseData.setApplyTpNm("I-物品請購");
        caseData.setSave("N");
        caseData.setApplyDate(DateUtil.getNowWestDateTime(true).substring(0, 8));
        List<Eip08w060Case> newList =new ArrayList<Eip08w060Case>();
        caseData.setEip08w060QuaryList(newList);
        caseData.setEip08w060CaseList(newList);
        caseData.setItemId("");

        return new ModelAndView(QUERY_PAGE);
    }

    /**
     * 物品請購/修繕請修  查詢
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip08w060_query.action")
    public String query(@Validated @ModelAttribute(CASE_KEY) Eip08w060Case caseData, BindingResult result) {

        log.debug("導向   eip08W060Q   物品請購/修繕請修查詢作業");

        if (result.hasErrors()) {
            return QUERY_PAGE;
        }


        try {

            eip08W060Service.quary(caseData);
            if (caseData.getEip08w060QuaryList().isEmpty()) {
                log.debug("查無資料");
                setSystemMessage(getQueryEmptyMessage());
                return QUERY_PAGE;
            }
            setSystemMessage(getQuerySuccessMessage());
        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return QUERY_PAGE;
        }
        return QUERY_DATA_APGE;

    }
    /**
     * 物品請購/修繕請修  畫面控制
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip08w060_transfer.action")
    public String add(@Validated @ModelAttribute(CASE_KEY) Eip08w060Case caseData, BindingResult result) {
    if ("A".equals(caseData.getProcessTy())){
        log.debug("導向   eip08W060Q   物品請購/修繕請修新增作業");
        return ADD_APGE;
    }
        log.debug("導向   eip08W060Q   物品請購/修繕請修明細作業畫面");
        return Details_DATA_APGE;
    }

    /**
     * 物品請購/修繕請修  執行新增
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip08w060_inster.action")
    public String inster(@Validated @ModelAttribute(CASE_KEY) Eip08w060Case caseData, BindingResult result) {

        log.debug("導向   eip08W060Q   物品請購/修繕請修新增作業");
        try {

            String sysDateTime = DateUtil.getNowWestDateTime(true);
            String sysDate = sysDateTime.substring(0, 8);
            caseData.setApply_date(sysDate);
            caseData.setCre_datetime(sysDateTime);
            eip08W060Service.add(caseData);
            setSystemMessage(getSaveSuccessMessage()+"_請購單號:"+caseData.getItemId());
        }catch (Exception e){
            log.error("新增失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_APGE;
        }
        //list初始化
        List<Eip08w060Case> newList =new ArrayList<Eip08w060Case>();
        caseData.setEip08w060QuaryList(newList);
        caseData.setEip08w060CaseList(newList);
          return QUERY_PAGE;
    }

    @RequestMapping("/Eip08w060_updatePage.action")
    public String updatePage(@Validated @ModelAttribute(CASE_KEY) Eip08w060Case caseData, BindingResult result) {

        log.debug("導向   eip08W060Q   物品請購/修繕請修維護畫面");
        ArrayList<Eip08w060Case> arrayList = new ArrayList<Eip08w060Case>();
        for (Eip08w060Case data:caseData.getEip08w060QuaryList()) {
            if (data.getItemId().equals(caseData.getSelectItemID())){
                arrayList.add(data);
            }
        }
        caseData.setEip08w060CaseList(arrayList);
        return Details_DATA_APGE;
    }

    @RequestMapping("/Eip08w060_delete.action")
    public String delete(@Validated @ModelAttribute(CASE_KEY) Eip08w060Case caseData, BindingResult result) {

        log.debug("導向   eip08W060Q   物品請購/修繕請修刪除作業");
        try {

            eip08W060Service.delete(caseData.getEip08w060CaseList().get(0));
            setSystemMessage(getDeleteSuccessMessage());
        }catch (Exception e){
            log.error("刪除失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return Details_DATA_APGE;
        }
        //list初始化
        List<Eip08w060Case> newList =new ArrayList<Eip08w060Case>();
        caseData.setEip08w060QuaryList(newList);
        caseData.setEip08w060CaseList(newList);

        return QUERY_PAGE;
    }
    @RequestMapping("/Eip08w060_update.action")
    public String update(@Validated @ModelAttribute(CASE_KEY) Eip08w060Case caseData, BindingResult result) {

        log.debug("導向   eip08W060Q   物品請購/修繕申請修改作業");
        try {
            for (Eip08w060Case upData:caseData.getEip08w060CaseList()) {
                caseData.setUpd_datetime(DateUtil.getNowWestDateTime(true));
                caseData.setUpd_user(userData.getUserId());
                eip08W060Service.update(upData);
            }
            setSystemMessage(getUpdateSuccessMessage());
        }catch (Exception e){
            log.error("刪除失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return Details_DATA_APGE;
        }
        //list初始化
        List<Eip08w060Case> newList =new ArrayList<Eip08w060Case>();
        caseData.setEip08w060QuaryList(newList);
        caseData.setEip08w060CaseList(newList);

        return QUERY_PAGE;
    }

}