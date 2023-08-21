package tw.gov.pcc.eip.common.controllers;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.services.Eip06w010Service;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 會議室查詢/維護作業
 * @author 2201009
 */

@Controller
public class Eip06w010Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip06w010Controller.class);

    public static final String CASE_KEY = "_eipi06w010Controller_caseData";
    private static final String QUERY_PAGE =  "/eip06w010/eip06w010q"; //查詢會議(非管理員)
    private static final String QUERY_ADMIN_PAGE =  "/eip06w010/eip06w011q"; //查詢會議(管理員)
    private static final String UPDATE_PAGE =  "/eip06w010/eip06w010c"; // 更新會議
    private static final String DETAIL_PAGE =  "/eip06w010/eip06w010x"; // 查詢會議

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip06w010Service eip06w010Service;
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private MeetingCodeDao meetingCodeDao;

    @ModelAttribute(CASE_KEY)
    public Eip06w010Case getEip06w010Case(){
        Eip06w010Case caseData = new Eip06w010Case();
        eip06w010Service.initSelectList(caseData);
        caseData.setMeetingdtBegin(DateUtility.getNowChineseDate());
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip06w010_enter.action")
    public String enter() {
        log.debug("導向會會議室查詢/維護作業");
        return QUERY_PAGE;
    }

    /**
     * 進入頁面(管理員）
     *
     */
    @RequestMapping("/Eip06w011_enter.action")
    public String enterAdmin() {
        log.debug("導向會議室查詢/維護作業(管理員)");
        return QUERY_ADMIN_PAGE;
    }

    /**
     * 依條件查詢會議
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w010_queryAllMeeting.action")
    public String queryAllMeeting(@Validated @ModelAttribute(CASE_KEY) Eip06w010Case caseData) {
        try {
            eip06w010Service.queryAllMeeting(caseData);
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        caseData.setMeetingdtBegin(caseData.getMeetingdtBegin() != null ? DateUtility.changeDateTypeToChineseDate(caseData.getMeetingdtBegin()) : "");
        caseData.setMeetingdtEnd(caseData.getMeetingdtEnd() != null ? DateUtility.changeDateTypeToChineseDate(caseData.getMeetingdtEnd()) : "");
        if(caseData.isAdmin()){
            return QUERY_ADMIN_PAGE;
        }else {
            return QUERY_PAGE;
        }
    }

    /**
     * 取得申請書
     * @param caseData
     * @return
     */
    @RequestMapping ("/Eip06w010_getApplyForm.action")
    public String getApplyForm(@Validated @ModelAttribute(CASE_KEY) Eip06w010Case caseData, HttpServletResponse response) throws IOException {

        // 創建Word文檔
        XWPFDocument document = eip06w010Service.prodReport(caseData);

        // 設定中文文件名
        String fileName = "會議室使用申請表_會議室簽呈.docx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

        // 設置響應標頭
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        // 將Word寫入響應流
        document.write(response.getOutputStream());

        // 關閉文檔
        response.getOutputStream().close();
        if(caseData.isAdmin()){
            return "redirect:/Eip06w011_enter.action";
        }else {
            return "redirect:/Eip06w010_enter.action";
        }
    }


    /**
     * 查詢會議內容 for Update
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w010_queryMeetingForUpdate.action")
    public String queryMeetingForUpdate(@Validated @ModelAttribute(CASE_KEY) Eip06w010Case caseData) {
        try {
            eip06w010Service.queryMeeting(caseData);
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return UPDATE_PAGE;
    }

    /**
     * 查詢會議內容 for Detail
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w010_queryMeetingForDetail.action")
    public String queryMeetingForDetail(@Validated @ModelAttribute(CASE_KEY) Eip06w010Case caseData) {
        try {
            eip06w010Service.queryMeeting(caseData);
        }catch (Exception e){
            log.error("查詢失敗  - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return DETAIL_PAGE;
    }

    /**
     * 查詢會議室人數最大值
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w010_findMaxMeetingQty.action")
    @ResponseBody
    public String findMaxMeetingQty(@RequestBody Map<Object, Object> map) {
        return eip06w010Service.findMaxMeetingQty(map.get("roomId").toString());
    }

    /**
     * 查詢已選會議物品和食物
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w010_findSelectedItemandFood.action")
    @ResponseBody
    public List<Map<String, String>> findSelectedItemandFood(@RequestBody Map<Object, Object> map) {
        return eip06w010Service.findSelectedItemAndFood(map.get("meetingId").toString());
    }

    /**
     * 欄位驗證: 判斷會議訖>起?
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w010_isMeetingEndValid.action")
    @ResponseBody
    public boolean isMeetingEndValid(@RequestBody Map<Object, Object> map) {
        if (Integer.parseInt(map.get("meetingBegin").toString()) >= Integer.parseInt(map.get("meetingEnd").toString())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 欄位驗證: 選擇會議日期、時間判斷會議室是否已預約或禁用(包含使用者選取的會議時間)
     * ajax
     * @param map
     * @return
     */
    @RequestMapping("/Eip06w010_findValidRoominclBooked.action")
    @ResponseBody
    public Eip06w010Case findValidRoominclBooked(@RequestBody Map<Object, Object> map, @ModelAttribute(CASE_KEY) Eip06w010Case caseData) {
        String meetingDt = map.get("meetingdt").toString().replace("-","");
        String meetingBegin = map.get("meetingBegin").toString();
        String meetingEnd  = map.get("meetingEnd").toString();
        String meetingId =  map.get("meetingId").toString();

        List<MeetingCode> roomList = eip06w010Service.findValidRoominclBooked(meetingId, meetingDt, meetingBegin, meetingEnd);

        Meeting mt = new Meeting();
        mt.setMeetingId(Integer.parseInt(meetingId));
        mt = meetingDao.selectDataByPrimaryKey(mt);

        //初始化會議場地下拉選單
        Map<String, String> meetingRoomMap = new LinkedHashMap<>();
//        先將原本預定會議室放進map後再放其餘的(當會議室條件一樣時才可以加入)
        if(DateUtility.changeDateTypeToChineseDate(meetingDt.replaceAll("/","")).equals(DateUtility.changeDateTypeToChineseDate(mt.getMeetingdt())) &&
           meetingBegin.equals(mt.getMeetingBegin()) &&
           meetingEnd.equals(mt.getMeetingEnd())){
            meetingRoomMap.put(mt.getRoomId(), mt.getRoomId()+'-'+meetingCodeDao.selectDataByItemId(mt.getRoomId()).get(0).getItemName());
        }
        // 使用stream和collect過濾不重複的RoomId，然後塞入meetingRoomMap中
        roomList.stream()
                .map(MeetingCode::getItemId) // 取得RoomId
                .distinct() // 過濾出不重複的RoomId
                .forEach(roomId -> meetingRoomMap.put(roomId, roomId + '-' + meetingCodeDao.selectDataByItemId(roomId).get(0).getItemName()));

//        for (MeetingCode mc: roomList) {
//            if(StringUtils.equals(mt.getRoomId(), mc.getItemId())){
//                meetingRoomMap.put(mc.getItemId(), mc.getItemId()+'-'+mc.getItemName());
//                roomList.remove(mc);
//                break;
//            }
//        }
//        if(roomList.size() > 0){
//            roomList.forEach(a->{
//                meetingRoomMap.put(a.getItemId(), a.getItemId()+'-'+a.getItemName());
//            });
//        }

        caseData.setMeetingRoomCombobox(meetingRoomMap);
        return caseData;
    }

    /**
     * 更新會議
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w010_update.action")
    public String update(@Validated(Eip06w010Case.Sequence.class) @ModelAttribute(CASE_KEY) Eip06w010Case caseData, BindingResult result) {
        try {
            if(result.hasErrors()){
                return UPDATE_PAGE;
            }
            eip06w010Service.updateMeeting(caseData);
            setSystemMessage(getUpdateSuccessMessage());
        }catch (Exception e){
            log.error(ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        caseData.clear();
        eip06w010Service.initSelectList(caseData);
        if(caseData.isAdmin()){
            return QUERY_ADMIN_PAGE;
        }else {
            return QUERY_PAGE;
        }
    }

    /**
     * 返回首頁
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w010_backHome.action")
    public String backHome(@ModelAttribute(CASE_KEY) Eip06w010Case caseData) {
        caseData.clear();
        eip06w010Service.initSelectList(caseData);
        if(caseData.isAdmin()){
            return QUERY_ADMIN_PAGE;
        }else {
            return QUERY_PAGE;
        }
    }

    /**
     * 刪除會議
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w010_deleteMeeting.action")
    public String deleteMeeting(@ModelAttribute(CASE_KEY) Eip06w010Case caseData) {
        eip06w010Service.deleteMeeting(caseData);
        caseData.clear();
        eip06w010Service.initSelectList(caseData);
        setSystemMessage(getDeleteSuccessMessage());
        if(caseData.isAdmin()){
            return QUERY_ADMIN_PAGE;
        }else {
            return QUERY_PAGE;
        }
    }
}
