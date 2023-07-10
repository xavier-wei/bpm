package tw.gov.pcc.eip.orderCar.controllers;

import org.apache.commons.lang3.StringUtils;
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
import tw.gov.pcc.eip.apply.service.Eip08w060CaseValidator;
import tw.gov.pcc.eip.apply.service.Eip08w060Service;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.orderCar.service.Eip07w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 派車基本資料建置作業
 *
 * @author York
 */
@Controller
@SessionAttributes({Eip07w010Controller.CASE_KEY})
public class Eip07w010Controller extends BaseController {
    public static final String CASE_KEY = "_eip07w010_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w010Controller.class);
    private static final String QUERY_PAGE = "/eip07w010/eip07w010q";//選擇頁
    private static final String ADD_APGE = "/eip07w010/eip07w010x";//新增頁
//    private static final String QUERY_DATA_APGE = "/eip08w060/eip08w060x";//查詢頁
//    private static final String Details_DATA_APGE = "/eip08w060/eip08w060d";//明細頁

    @Autowired
    private Eip07w010Service eip07w010Service;

    @Autowired
    private UserBean userData;

//    @Autowired
//    private Eip08w060CaseValidator eip08w060CaseValidator;

    @ModelAttribute(CASE_KEY)
    public Eip07w010Case getEip07w010Case() {
        Eip07w010Case caseData = new Eip07w010Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip07w010_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w010Case caseData) {
        log.debug("導向 Eip07w010Case_enter 派車基本資料建置作業");
        caseData.setProcessTy("D");
        caseData.setWorkTy("A");
        caseData.setName("");
        caseData.setCarno1("");
        caseData.setCarno2("");
        return new ModelAndView(QUERY_PAGE);
    }

    /**
     * 物品請購/修繕請修  查詢
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_query.action")
    public String query(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_enter 派車基本資料查詢作業");
        if (result.hasErrors()) {
            return QUERY_PAGE;
        }
        try {

//            if (caseData.getBrdte().isEmpty()) {
//                log.debug("查無資料");
//                setSystemMessage(getQueryEmptyMessage());
//                return QUERY_PAGE;
//            }
        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return QUERY_PAGE;
        }
        return ADD_APGE;

    }

    /**
     * 物品請購/修繕請修  新增
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_inster.action")
    public String inster(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_enter 派車基本資料新增作業");
        if (result.hasErrors()) {
            return ADD_APGE;
        }
        try {

//            if (caseData.getBrdte().isEmpty()) {
//                log.debug("查無資料");
//                setSystemMessage(getQueryEmptyMessage());
//                return QUERY_PAGE;
//            }
        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return QUERY_PAGE;
        }
        return ADD_APGE;

    }


}