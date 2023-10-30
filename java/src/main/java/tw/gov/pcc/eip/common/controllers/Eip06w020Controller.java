package tw.gov.pcc.eip.common.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.common.cases.Eip06w020Case;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.services.Eip06w010Service;
import tw.gov.pcc.eip.services.Eip06w020Service;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.*;

/**
 * 會議室預約作業
 * @author 2201009
 */

@Controller
public class Eip06w020Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip06w020Controller.class);

    public static final String CASE_KEY = "_eipi06w020Controller_caseData";
    private static final String INSERT_PAGE =  "/eip06w020/eip06w020a"; //新增會議(非管理員)

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip06w020Service eip06w020Service;
    @Autowired
    private Eip06w010Service eip06w010Service;

    @ModelAttribute(CASE_KEY)
    public Eip06w020Case getEip06w020Case(){
        Eip06w020Case caseData = new Eip06w020Case();
        caseData.setOrganizerId(userData.getUserId());
        caseData.setOrganizerIdName(userData.getUserName());
        eip06w020Service.initSelectList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip06w020_enter.action")
    public String enter() {
        log.debug("導向會議室預約作業");
        getEip06w020Case();
        return INSERT_PAGE;
    }

    /**
     * 儲存會議
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w020_save.action")
    public String save(@Validated(Eip06w020Case.Sequence.class) @ModelAttribute(CASE_KEY) Eip06w020Case caseData, BindingResult result) {
        try {
            if (result.hasErrors()){
                return INSERT_PAGE;
            }
            eip06w020Service.saveMeeting(caseData);
            setSystemMessage(getSaveSuccessMessage());
        }catch (Exception e){
            log.error("新增失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getSaveFailMessage());
        }
        //TODO: 新增成功回到查詢頁面
        eip06w010Service.initSelectList(new Eip06w010Case());
        return "redirect:/Eip06w010_enter.action";
    }

    /**
     * 查詢會議室人數最大值
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w020_findMaxMeetingQty.action")
    @ResponseBody
    public String findMaxMeetingQty(@RequestBody Map<Object, Object> map) {
        return eip06w020Service.findMaxMeetingQty(map.get("roomId").toString());
    }

    /**
     * 欄位驗證: 判斷會議訖>起?
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w020_isMeetingEndValid.action")
    @ResponseBody
    public boolean isMeetingEndValid(@RequestBody Map<Object, Object> map) {
        if (Integer.parseInt(map.get("meetingBegin").toString()) >= Integer.parseInt(map.get("meetingEnd").toString())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 欄位驗證: 選擇會議日期、時間判斷會議室是否已預約或禁用
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w020_findValidRoom.action")
    @ResponseBody
    public Eip06w020Case findValidRoom(@RequestBody Map<Object, Object> map, @ModelAttribute(CASE_KEY) Eip06w020Case caseData) {
        String meetingDt = DateUtility.changeDateTypeToWestDate(map.get("meetingdt").toString().replace("/",""));
        String meetingBegin = map.get("meetingBegin").toString();
        String meetingEnd = map.get("meetingEnd").toString();
        List<MeetingCode> roomList = eip06w020Service.findValidRoom(meetingDt, meetingBegin, meetingEnd);

        //初始化會議場地下拉選單
        Map<String, String> meetingRoomMap =  new LinkedHashMap<>();
        roomList.forEach(a->{
            meetingRoomMap.put(a.getItemId(), a.getItemId()+'-'+a.getItemName());
        });
            caseData.setMeetingRoomCombobox(meetingRoomMap);
        return caseData;
    }
}
