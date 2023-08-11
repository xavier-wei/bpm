package tw.gov.pcc.eip.orderCar.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.orderCar.Validator.Eip07w020Validator;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;
import tw.gov.pcc.eip.services.Eip07w020Service;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 派車預約暨派車結果查詢作業
 *
 * @author York
 */
@Controller
@SessionAttributes({Eip07w020Controller.CASE_KEY})
public class Eip07w020Controller extends BaseController {
    public static final String CASE_KEY = "_eip07w020_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w020Controller.class);
    private static final String QUERY_PAGE = "/eip07w020/eip07w020q";//選擇頁

    private static final String ADD_PAGE = "/eip07w020/eip07w020a";//新增頁

    private static final String DATA_PAGE = "/eip07w020/eip07w020x";//新增頁

    private static final String Details_PAGE = "/eip07w020/eip07w020d";//新增頁



    @Autowired
    private Eip07w020Service eip07w020Service;
    @Autowired
    private UserBean userData;
    @Autowired
    private Eip07w020Validator eip07w020Validator;

    @ModelAttribute(CASE_KEY)
    public Eip07w020Case getEip07w020Case() {
        Eip07w020Case caseData = new Eip07w020Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip07w020_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w020Case caseData) {
        log.debug("導向 Eip07w010Case_enter 派車預約暨派車");
        caseData.setWorkTy("A");
        caseData.setApplyName(userData.getUserId());
        caseData.setApplyUnit(userData.getDeptId());
        caseData.setApplyDate(DateUtil.getNowChineseDate());
        caseData.setUseDateStar("");
        caseData.setUseDateEnd("");
        caseData.setApplyDateStar("");
        caseData.setApplyDateEnd("");
        caseData.setCheckMk("false");
        caseData.setInsterList(new ArrayList<>());
        return new ModelAndView(QUERY_PAGE);
    }

    /**
     * 派車預約暨派車新增畫面
     *
     * @return
     */
    @RequestMapping("/Eip07w020_addPage.action")
    public  String  addPage(@ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result) {
        log.debug("導向 Eip07w020_addPage 派車預約暨派車新增畫面");
        eip07w020Validator.eip07w020QValidate(caseData,result);
        if (result.hasErrors()) {
            return QUERY_PAGE;
        }
        try {
            eip07w020Service.getSelectList(caseData);
            eip07w020Service.getTimeList(caseData);
        }catch (Exception e){
            log.error("新增失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getSaveFailMessage());
            return QUERY_PAGE;
        }
        return ADD_PAGE;
    }

    /**
     * 派車預約暨派車執行新增
     *
     * @return
     */
    @RequestMapping("/Eip07w020_inster.action")
    public String inster(@ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result)  {
        log.debug("導向 Eip07w020_addPage 派車預約暨派車新增");
        try {
            eip07w020Validator.eip07w020AValidate(caseData.getInsterList().get(0),result);
        if (result.hasErrors()) {
            return ADD_PAGE;
        }

            eip07w020Service.addReserve(caseData.getInsterList().get(0),caseData);
            setSystemMessage(getSaveSuccessMessage());
            caseData.setInsterList(new ArrayList<>());
        }catch (Exception e){
            result.reject(null, "新增失敗");
            setSystemMessage(getSaveFailMessage());
            return ADD_PAGE;
        }
        return QUERY_PAGE;
    }


    /**
     * 派車預約暨派車執行新增
     *
     * @return
     */
    @RequestMapping("/Eip07w020_quary.action")
    public String quary(@ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result)  {
        log.debug("導向 Eip07w020_quary 派車預約暨派車查詢");
        eip07w020Validator.eip07w020QValidate(caseData,result);
        if (result.hasErrors()) {
            return QUERY_PAGE;
        }
        try {
            List<Eip07w020Case> data =new ArrayList<>();
            data= eip07w020Service.quaryData(caseData);
            if (data.isEmpty()) {
                caseData.setApplyDateStar(DateUtility.changeDateType(caseData.getApplyDateStar()));
                caseData.setApplyDateEnd(DateUtility.changeDateType(caseData.getApplyDateEnd()));
                caseData.setUseDateStar(DateUtility.changeDateType(caseData.getUseDateStar()));
                caseData.setUseDateEnd(DateUtility.changeDateType(caseData.getUseDateEnd()));
                setSystemMessage("查無資料");
                return QUERY_PAGE;
            }
            caseData.setResultList(data);
            setSystemMessage(getQuerySuccessMessage());
        }catch (Exception e){
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return QUERY_PAGE;
        }
        return DATA_PAGE;
    }

    @RequestMapping("/Eip07w020_updatePage.action")
    public String updatePage(@Validated @ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result) {
        log.debug("導向 Eip07w020_updatePage 派車預約暨派車明細頁");
        eip07w020Service.getSelectList(caseData);
        eip07w020Service.getTimeList(caseData);
        ArrayList<CarBooking> arrayList;
        try {
            arrayList = new ArrayList<>();
            CarBooking detail = eip07w020Service.selectByApplyId(caseData.getApplyId());
            arrayList.add(detail);
            if ("U".equals(detail.getCarprocess_status())){
                caseData.setCheckMk("true");
            }else {
                caseData.setRmMemo("1");
            }
        } catch (Exception e) {
            setSystemMessage(getQueryFailMessage());
            log.error("查詢失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return DATA_PAGE;
        }
        caseData.setDetailsList(arrayList);
        caseData.setChangeMkList(arrayList);
        return Details_PAGE;
    }

    /**
     * 派車預約暨派車作業  修改 限狀態等於1
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w020_update.action")
    public String update(@Validated @ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result) {
        log.debug("導向 Eip07w020_update 派車預約暨派車作業");
        try {
        eip07w020Validator.updateValidate(caseData.getDetailsList().get(0),  result);
        if (result.hasErrors()) {
            return Details_PAGE;
        }

            eip07w020Service.updateCarBooking(caseData.getDetailsList().get(0));
            setSystemMessage(getUpdateSuccessMessage());
        }catch (Exception e){
            setSystemMessage(getUpdateFailMessage());
            log.error("修改失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return Details_PAGE;
        }
        return QUERY_PAGE;
    }

    /**
     * 派車預約暨派車作業  異動申請
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w020_changeApplication.action")
    public String changeApplication(@Validated @ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result) {
        log.debug("導向 Eip07w020_changeApplication 異動申請作業");
        try {
        eip07w020Validator.eip07w020DValidate(caseData, result);
        if (result.hasErrors()) {
            return Details_PAGE;
        }

            eip07w020Service.changeApplication(caseData);
            setSystemMessage(getUpdateSuccessMessage());
        }catch (Exception e){
            setSystemMessage(getUpdateFailMessage());
            log.error("修改失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return Details_PAGE;
        }
        return QUERY_PAGE;

    }
    /**
     * 派車預約暨派車作業  刪除
     *
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip07w020_delete.action")
    public String delete(@Validated @ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult result) {
        log.debug("導向    Eip07w020Case_delete 派車預約暨派車作業刪除作業");
        if (result.hasErrors()) {
            return Details_PAGE;
        }
        try {
            eip07w020Service.delete(caseData);
            setSystemMessage(getDeleteSuccessMessage());
        }catch (Exception e){
            setSystemMessage(getDeleteFailMessage());
            log.error("刪除失敗，原因:{}", ExceptionUtility.getStackTrace(e));
            return Details_PAGE;
        }
        return QUERY_PAGE;

    }

    /**
     * 報表產出
     */
    @RequestMapping("/Eip07w020_print.action")
    public ModelAndView doPrint(@Validated @ModelAttribute(CASE_KEY) Eip07w020Case caseData, BindingResult bindrs) {
        log.info("執行 PDF列印   作業 Eip07w020 caseData");
        try {
            ByteArrayOutputStream baoOutput = eip07w020Service.print(caseData);
            return new ModelAndView(new FileOutputView(baoOutput, "EIP07W020.pdf", FileOutputView.PDF_FILE));
        } catch (Exception e) {
            log.error("查詢部門名稱失敗原因", ExceptionUtility.getStackTrace(e));
            return null;
        }
    }

}