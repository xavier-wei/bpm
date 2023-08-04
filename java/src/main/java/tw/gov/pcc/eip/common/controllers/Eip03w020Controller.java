package tw.gov.pcc.eip.common.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tw.gov.pcc.eip.common.cases.Eip03w020Case;
import tw.gov.pcc.eip.common.cases.Eip03w020MixCase;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.annotation.SkipCSRFVerify;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip03w020Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 重要列管事項_填報辦理進度
 * @author 2201009
 */

@Controller
@SessionAttributes({Eip03w020Controller.CASE_KEY})
public class Eip03w020Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip03w020Controller.class);

    public static final String CASE_KEY = "_eip03w020Controller_caseData";
    public static final String MIX_CASE_KEY = "_eip03w020Controller_mixCaseData";
    private static final String QUERY_PAGE =  "/eip03w020/eip03w020q";
    private static final String DETAIL_PAGE =  "/eip03w020/eip03w020x";

    @Autowired
    private Eip03w020Service eip03w020Service;
    @Autowired
    private UserBean userData;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UsersDao usersDao;

    @ModelAttribute(CASE_KEY)
    public Eip03w020Case getEip03w020Case(){
        Eip03w020Case caseData = new Eip03w020Case();
        eip03w020Service.initDataList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }
    @ModelAttribute(MIX_CASE_KEY)
    public Eip03w020MixCase getEip03w030MixCase() {
        return new Eip03w020MixCase();
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip03w020_enter.action")
    public String enter(SessionStatus status) {
        log.debug("導向重要列管事項_填報辦理進度作業");
        status.setComplete();
        getEip03w020Case();
        return QUERY_PAGE;
    }

        /**
         * 導回查詢頁
         *
         */
        @RequestMapping("/Eip03w020_backHome.action")
        public String backHome(SessionStatus status) {
            log.debug("導回查詢頁");
            status.setComplete();
            return "redirect:/Eip03w020_enter.action";
        }

    /**
     * 依條件查詢重要列管事項
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip03w020_queryKeepTrk.action")
    public String queryKeepTrk(@Validated @ModelAttribute(CASE_KEY) Eip03w020Case caseData, BindingResult result) {
        try {
            if(result.hasErrors()){
                return QUERY_PAGE;
            }
            eip03w020Service.queryKeepTrk(caseData);
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
    @RequestMapping("/Eip03w020_queryKeepTrkForDetail.action")
    public String queryKeepTrkForDetail(@ModelAttribute(CASE_KEY) Eip03w020Case caseData, @ModelAttribute(MIX_CASE_KEY)Eip03w020MixCase mixCase) {
        try {
            eip03w020Service.queryKeepTrkDetail(caseData, mixCase);
            return DETAIL_PAGE;
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return QUERY_PAGE;
    }

    /**
     * ajax取得部門代碼資料
     * @return
     */
    @ResponseBody
    @SkipCSRFVerify
    @RequestMapping("Eip03w020_findRptDept.action")
    public List<Map<String, String>> findRptDept(){
        List<Depts> depts = deptsDao.getEip03wDepts("1", null);
        depts.sort(Comparator.comparing(Depts::getDept_id));
        List<Map<String, String>> deptCodes = new ArrayList<>();
        depts.forEach( a -> {
            Map<String, String> map = new HashMap<>();
            map.put("deptID", a.getDept_id());
            map.put("deptName", a.getDept_name());
            deptCodes.add(map);
        });
        return deptCodes;
    }

    /**
     * ajax取得部門代碼資料
     * @return
     */
    @ResponseBody
    @SkipCSRFVerify
    @RequestMapping("Eip03w020_findRptUser.action")
//
    public List<Map<String, String>> findRptUser( @RequestBody Map<String, String> dataMap){
        List<String> codes = Arrays.stream(dataMap.get("codes").split(";")).collect(Collectors.toList());

        List<Users> users = usersDao.getEip03wUsers(codes);
        users.sort(Comparator.comparing(Users::getEmp_id));
        List<Map<String, String>> userCodes = new ArrayList<>();
        users.forEach( a -> {
            Map<String, String> map = new HashMap<>();
            map.put("empID", a.getEmp_id());
            map.put("userName", a.getUser_name());
            userCodes.add(map);
        });
        return userCodes;
    }

    /**
     * 儲存
     * @param mixCase
     * @return
     */
    @RequestMapping("/Eip03w020_update.action")
    public String update(@Validated @ModelAttribute(MIX_CASE_KEY)Eip03w020MixCase mixCase, @ModelAttribute(CASE_KEY) Eip03w020Case caseData, BindingResult result) {
        try {
            eip03w020Service.update(mixCase);
            setSystemMessage(getUpdateSuccessMessage());
            //儲存成功後，回到原畫面
            return queryKeepTrkForDetail(caseData, mixCase);
        }catch (Exception e){
            log.error("更新失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return "redirect:/Eip03w020_queryKeepTrkForDetail.action";
    }
}
