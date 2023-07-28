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
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.orderCar.Validator.Eip07w010Validator;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.services.Eip07w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

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
    private static final String ADD_APGE = "/eip07w010/eip07w010a";//新增頁
    private static final String DATA_APGE = "/eip07w010/eip07w010x";//新增頁
    private static final String ADD_CAR = "/eip07w010/eip07w010c";//新增車籍資料

    private static final String CAR_DATA = "/eip07w010/eip07w010d";//車籍查詢結果


    @Autowired
    private Eip07w010Service eip07w010Service;
    @Autowired
    private UserBean userData;
    @Autowired
    private Eip07w010Validator eip07w010Validator;

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
        caseData.setEip07w010QueryDataList(new ArrayList<>());
        caseData.setEip07w010CarDataList(new ArrayList<>());
        return new ModelAndView(QUERY_PAGE);
    }

    /**
     * 派車基本資料建置作業  查詢
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_query.action")
    public String query(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_enter 派車基本資料查詢作業");
        List<Eip07w010Case> queryList= new ArrayList<>();
        try {
            eip07w010Service.getSelectList(caseData);
            if (StringUtils.isBlank(caseData.getName())&&!"A".equals(caseData.getWorkTy())){
                queryList=eip07w010Service.driveIsExist(caseData.getEip07w010QueryDataList().get(0));
            }
            if (queryList.isEmpty()) {
                if ("Q".equals(caseData.getWorkTy())) {
                    result.reject(null, "查無資料");
                    return QUERY_PAGE;
                }
            }else{
                for (Eip07w010Case quaryData:queryList) {
                    //查找在職中文
                    if ("Y".equals(quaryData.getStillWork())){
                        quaryData.setStillWorkNm("是");
                    }else{
                        quaryData.setStillWorkNm("否");
                    }
                    //查找在職職稱中文
                    for (Eipcode title:caseData.getTitleList()) {
                        if (quaryData.getTitle().equals(title.getCodeno())){
                            quaryData.setTitleNm(title.getCodename());
                        }
                    }

                }

                caseData.setEip07w010QueryDataList(queryList);
                if ("A".equals(caseData.getWorkTy())){
                    result.reject(null, "此駕駛人已存在，不可新增");
                    return QUERY_PAGE;
                } else if (queryList.size()>1) {
                    return DATA_APGE;
                }
            }

        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return QUERY_PAGE;
        }
        return ADD_APGE;
    }

    /**
     * 派車基本資料建置作業  新增
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_inster.action")
    public String inster(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_enter 派車基本資料新增作業");
        eip07w010Validator.driverValidate(caseData.getEip07w010QueryDataList().get(0),result);
        if (result.hasErrors()) {
            return ADD_APGE;
        }
        try {
            eip07w010Service.add(caseData.getEip07w010QueryDataList().get(0));
            setSystemMessage(getSaveSuccessMessage());
            caseData.setEip07w010QueryDataList(new ArrayList<>());
        }catch (Exception e){
            log.error("新增失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_APGE;
        }
        return QUERY_PAGE;

    }

    /**
     * 派車基本資料建置作業  刪除
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_delete.action")
    public String delete(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_delete 派車基本資料駕駛刪除作業");
        if (result.hasErrors()) {
            return ADD_APGE;
        }
        try {
            eip07w010Service.delete(caseData.getEip07w010QueryDataList().get(0));
            setSystemMessage(getDeleteSuccessMessage());
            caseData.setEip07w010QueryDataList(new ArrayList<>());
        }catch (Exception e){
            log.error("刪除失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_APGE;
        }
        return QUERY_PAGE;

    }

    /**
     * 派車基本資料建置作業  更新
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_update.action")
    public String update(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_update 派車基本資料駕駛更新作業");
        eip07w010Validator.driverValidate(caseData.getEip07w010QueryDataList().get(0),result);
        if (result.hasErrors()) {
            return ADD_APGE;
        }
        try {
            eip07w010Service.updateDriverBase(caseData.getEip07w010QueryDataList().get(0));
            setSystemMessage(getUpdateSuccessMessage());
            caseData.setEip07w010QueryDataList(new ArrayList<>());
        }catch (Exception e){
            log.error("修改失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_APGE;
        }
        return QUERY_PAGE;

    }
    /**
     * 派車基本資料建置作業_駕駛明細查詢  更新
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_detail.action")
    public String detail(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_update 派車基本資料駕駛更新作業");
        if (result.hasErrors()) {
            return DATA_APGE;
        }
        try {
            List<Eip07w010Case> Detail= new ArrayList<>();
            Detail=eip07w010Service.driveIsExist(caseData);
            caseData.setEip07w010QueryDataList(Detail);
        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return DATA_APGE;
        }
        return ADD_APGE;

    }

    @RequestMapping("/Eip07w010_queryCar.action")
    public String queryCar(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010_queryCar 派車基本資料車籍新增作業");
        if (result.hasErrors()) {
            return DATA_APGE;
        }
        try {
            List<Eip07w010Case> carDetail = new ArrayList<>();
            carDetail = eip07w010Service.quaryCarBase(caseData.getEip07w010QueryDataList().get(0));
            eip07w010Service.getCarDetails(caseData.getEip07w010QueryDataList().get(0));
            if ("A".equals(caseData.getWorkTy())) {//新增
                if (carDetail.isEmpty()) {
                    return ADD_CAR;
                } else if (carDetail.get(0).getCarno1().equals(caseData.getEip07w010QueryDataList().get(0).getCarno1())) {
                    result.reject(null, "此車牌已存在，不可新增");
                    return QUERY_PAGE;
                }
                return ADD_CAR;
            } else {//查詢
                if (carDetail.isEmpty()) {
                    result.reject(null, "查無資料");
                    return QUERY_PAGE;
                } else {
                    caseData.setEip07w010CarDataList(carDetail);
                    if (carDetail.size()>1){
                        return DATA_APGE;//大於一筆畫面轉至統計頁
                    }else {
                        return   CAR_DATA;//畫面轉至明細頁
                    }

                }
            }
        } catch (Exception e) {
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return DATA_APGE;
        }

    }

    /**
     * 派車基本資料建置作業  車輛資料新增
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_carInster.action")
    public String carInster(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_enter 派車基本車輛資料作業");
        eip07w010Validator.carValidate(caseData.getEip07w010CarDataList().get(0),result);
        if (result.hasErrors()) {
            return ADD_CAR;
        }
        try {
            eip07w010Service.addCarData(caseData.getEip07w010CarDataList().get(0));
            setSystemMessage(getSaveSuccessMessage());
            caseData.setEip07w010QueryDataList(new ArrayList<>());
        }catch (Exception e){
            log.error("新增失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_CAR;
        }
        return QUERY_PAGE;

    }

    /**
     * 派車基本資料建置作業  車籍資料更新
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_updateCar.action")
    public String updateCar(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010Case_update 派車基本資料建置作業  車籍資料更新作業");
        eip07w010Validator.carValidate(caseData.getEip07w010CarDataList().get(0),result);
        if (result.hasErrors()) {
            return ADD_APGE;
        }
        try {
            eip07w010Service.updateCarBase(caseData.getEip07w010CarDataList().get(0));
            setSystemMessage(getUpdateSuccessMessage());
            caseData.setEip07w010QueryDataList(new ArrayList<>());
        }catch (Exception e){
            log.error("修改失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_APGE;
        }
        return QUERY_PAGE;

    }

    /**
     * 派車基本資料建置作業_車輛明細查詢
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_carDetail.action")
    public String carDetail(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    Eip07w010_carDetail.action 派車基本資料建置作業_車輛明細查詢");
        if (result.hasErrors()) {
            return DATA_APGE;
        }
        try {
            List<Eip07w010Case> carDetail = new ArrayList<>();
            carDetail = eip07w010Service.quaryCarBase(caseData);
           
            caseData.setEip07w010CarDataList(carDetail);
            eip07w010Service.getCarDetails(caseData);
        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return DATA_APGE;
        }
        return CAR_DATA;

    }

    /**
     * 派車基本資料建置作業  車輛刪除
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w010_carDelete.action")
    public String carDelete(@Validated @ModelAttribute(CASE_KEY) Eip07w010Case caseData, BindingResult result) {
        log.debug("導向    /Eip07w010_carDelete 派車基本資料車輛刪除作業");
        try {
            eip07w010Service.deleteCar(caseData.getEip07w010CarDataList().get(0));
            if (caseData.getOilList().size()>0||caseData.getMileageList().size()>0){
                result.reject(null, "該車籍已有油料紀錄或里程紀錄，不可刪除");
            }
        if (result.hasErrors()) {
            return ADD_APGE;
        }
            setSystemMessage(getDeleteSuccessMessage());
            caseData.setEip07w010QueryDataList(new ArrayList<>());
        }catch (Exception e){
            log.error("刪除失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return ADD_APGE;
        }
        return QUERY_PAGE;

    }
}