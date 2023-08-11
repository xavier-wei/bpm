package tw.gov.pcc.eip.common.controllers;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.common.cases.Eip06w030Case;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.services.Eip06w010Service;
import tw.gov.pcc.eip.services.Eip06w030Service;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tw.gov.pcc.eip.util.HttpUtility.convertBindingResultErrors;
import static tw.gov.pcc.eip.util.HttpUtility.createErrorHeaders;

/**
 * 會議室批次預約作業
 * @author 2201009
 */

@Controller
public class Eip06w030Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip06w030Controller.class);

    public static final String CASE_KEY = "_eipi06w030Controller_caseData";
    private static final String INSERT_ADMIN_PAGE =  "/eip06w030/eip06w030a"; //新增會議(管理員)

    @Autowired
    private UserBean userData;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private Eip06w030Service eip06w030Service;
    @Autowired
    private Eip06w010Service eip06w010Service;


    @ModelAttribute(CASE_KEY)
    public Eip06w030Case getEip06w030Case(){
        Eip06w030Case caseData = new Eip06w030Case();
        caseData.setOrganizerId(userData.getUserId() + "-" + userData.getUserName());
        eip06w030Service.initSelectList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip06w030_enter.action")
    public String enter() {
        log.debug("導向會議室批次預約作業");
        return INSERT_ADMIN_PAGE;
    }


    /**
     * 依據不同的模式執行對應的操作
     * @param caseData {@link Eip06w030Case}
     * @return 產出通知單或是錯誤訊息
     */
    @SneakyThrows
    @RequestMapping("/Eip06w030_validate.action")
    public ResponseEntity<?> process(Eip06w030Case caseData, BindingResult bindingResult) {
        log.debug("導入 validate");
        eip06w030Service.validate(caseData, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(createErrorHeaders(convertBindingResultErrors(bindingResult)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }


    /**
     * 查詢會議室人數最大值
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w030_findExistedMeeting.action")
    @ResponseBody
    public  Map<String,List<String>>  findExistedMeeting(@RequestBody Map<Object, Object> map) throws ParseException {
        List<Meeting> ml = eip06w030Service.findExistedMeeting(map);
        List<String> showList = new ArrayList<>();
        List<String> hideList = new ArrayList<>();
        ml.forEach(a -> {
            String meetings = DateUtility.changeDateTypeToChineseDate(a.getMeetingdt()) + "-" + a.getMeetingName() + "-" + usersDao.selectByKey(a.getOrganizerId()).getUser_name();
            hideList.add(a.getMeetingId().toString());
            showList.add(meetings);
        });
        if(showList.size() == 0){
            showList.add("no");
        }
        Map<String,List<String>> returnMap = new HashMap<>();
        returnMap.put("show",showList);
        returnMap.put("hide",hideList);
        return returnMap;
    }

    /**
     * 儲存會議
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w030_save.action")
    public String save(@ModelAttribute(CASE_KEY) Eip06w030Case caseData, BindingResult result) {
        try {
            if (result.hasErrors()){
                return INSERT_ADMIN_PAGE;
            }
//            if(caseData.isRepeat()){
            caseData.setRepeat(true);
            eip06w030Service.saveMultiMeeting(caseData);
//            }else {
//                eip06w030Service.saveMeeting(caseData);
//            }
            setSystemMessage(getSaveSuccessMessage());
        }catch (Exception e){
            log.error("新增失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getSaveFailMessage());
        }
        //TODO: 新增成功回到查詢頁面
        eip06w010Service.initSelectList(new Eip06w010Case());
        return "redirect:/Eip06w010_enter.action";
    }
}
