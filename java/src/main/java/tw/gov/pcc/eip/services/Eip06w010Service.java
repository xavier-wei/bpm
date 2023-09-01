package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.common.controllers.Eip06w010Controller;
import tw.gov.pcc.eip.common.report.Eip06w010l00;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 會議室查詢/維護作業
 * @author 2201009
 */
@Service
public class Eip06w010Service {
    private static final Logger log = LoggerFactory.getLogger(Eip06w010Controller.class);

    @Autowired
    UserBean userData;
    @Autowired
    UsersDao usersDao;
    @Autowired
    DeptsDao deptsDao;
    @Autowired
    private MeetingCodeDao meetingCodeDao;
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private MeetingItemDao meetingItemDao;
    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgavaildepDao msgavaildepDao;
    @Autowired
    private TimeConversionService timeConversionService;
    /**
     * 初始化下拉式選單
     * @param caseData
     */
    public void initSelectList(Eip06w010Case caseData){
        List<String> itemTyps = new ArrayList<>();
        itemTyps.add("F");
        itemTyps.add("FX");
        List<MeetingCode> roomIdList = meetingCodeDao.selectDataByItemTypes(itemTyps);
        caseData.setRoomIdList(roomIdList.stream().map(Eip06w010Case.Eip06w010OptionCase::new).collect(Collectors.toList()));

        //初始化查詢結果(撈出所有從今天~未來會議)
        String now = LocalDate.now().toString().replace("-","");
        caseData.setMeetingdtBegin(now);
        List<Eip06w010Case> resultList = meetingDao.selectDataByColumns(caseData);
        caseData.setMeetingdtBegin("");
        resultList.forEach(a->{
            if(a.getOrganizerId().equals(userData.getUserId())){
                a.setEditable(true);
            }else {
                a.setEditable(false);
            }
            a.setOrderFood(a.getOrderNum()>0);
            a.setOrganizerId(usersDao.selectByKey(a.getOrganizerId()).getUser_name());
        });

        caseData.setResultList(resultList.stream().map(Eip06w010Case::new).collect(Collectors.toList()));

        //初始化會議起始時間下拉選單
        Map<String, String> meetingTimeMap = new TreeMap<>();
         for (int i = 0; i < 24; i++) {
             for (int j = 0; j < 60; j += 30) {
                 String time = StringUtils.leftPad(String.valueOf(i),2, "0") + StringUtils.leftPad(String.valueOf(j),2, "0");
                 meetingTimeMap.put(time, time.substring(0,2) + ":" + time.substring(2));
             }
         }
        caseData.setMeetingTimeCombobox(meetingTimeMap);
    }

    /**
     * 查詢會議室最大容納人數
     * @param roomId
     * @return
     */
    public String findMaxMeetingQty(String roomId){
        return meetingCodeDao.selectDataByItemId(roomId).get(0).getQty().toString();
    }

    /**
     * 查詢已選會議物品和食物
     * @param meetingId
     * @return
     */
    public List<Map<String, String>> findSelectedItemAndFood(String meetingId){
        //初始化已選項目
        List<MeetingItemAndMeetingCode> selectItemList = meetingItemDao.selectDataByMeetingId(meetingId,"B");
        List<MeetingItemAndMeetingCode> selectFoodList = meetingItemDao.selectDataByMeetingId(meetingId,"A");
        Map<String, String> selectedItemIdMap = new TreeMap<>(); //原選取會議物品清單
        Map<String, String> selectedFoodIdMap = new TreeMap<>(); //原選取會議餐點清單
        selectItemList.forEach(a->{
            selectedItemIdMap.put(a.getItemId()+"-"+a.getItemName(), a.getQty().toString());
        });
        selectFoodList.forEach(a->{
            selectedFoodIdMap.put(a.getItemId()+"-"+a.getItemName(), a.getQty().toString());
        });

        List<Map<String, String>> selectedList = new ArrayList<>();
        selectedList.add(selectedItemIdMap);
        selectedList.add(selectedFoodIdMap);
        return selectedList;
    }

    /**
     * 依條件查詢會議
     *
     * @param caseData
     */
    public void queryAllMeeting(Eip06w010Case caseData){
        if (caseData.getMeetingdtBegin().equals("") && caseData.getMeetingdtEnd().equals("") ){
            String now = LocalDate.now().toString().replace("-","");
            caseData.setMeetingdtBegin(now);
        }
        caseData.setMeetingdtBegin(caseData.getMeetingdtBegin() != null ? DateUtility.changeDateTypeToWestDate(caseData.getMeetingdtBegin()) : "");
        caseData.setMeetingdtEnd(caseData.getMeetingdtEnd() != null ? DateUtility.changeDateTypeToWestDate(caseData.getMeetingdtEnd()) : "");

//        userIdList
        //查詢欄位輸入使用者名稱，故須先至users撈名稱相符ID後，再以userID查詢
        List<Users> usersList = usersDao.findUserIDByUserName(caseData.getOrganizerId());
        List<String> userIdList = new ArrayList<>();
        for (Users users : usersList) {
            userIdList.add(users.getUser_id().toString());
        }
        caseData.setUserIdList(userIdList);

        List<Eip06w010Case> resultList = meetingDao.selectDataByColumns(caseData);
        resultList.forEach(a->{
            if(a.getOrganizerId().equals(userData.getUserId())){
                a.setEditable(true);
            }else {
                a.setEditable(false);
            }
            a.setOrderFood(a.getOrderNum()>0);
            a.setOrganizerId(usersDao.selectByKey(a.getOrganizerId()).getUser_name());
        });

        caseData.setResultList(resultList.stream().map(Eip06w010Case::new).collect(Collectors.toList()));

    }


    public XWPFDocument prodReport(Eip06w010Case caseData) throws IOException{

            // 從sql撈資料
        Meeting mt = new Meeting();
        mt.setMeetingId(Integer.parseInt(caseData.getMeetingId()));
        mt = meetingDao.selectDataByPrimaryKey(mt);
        caseData.setMeetingId(mt.getMeetingId().toString());
        caseData.setMeetingName(mt.getMeetingName());
        caseData.setChairman(mt.getChairman());
        caseData.setMeetingdt(mt.getMeetingdt());
        caseData.setMeetingBegin(mt.getMeetingBegin());
        caseData.setMeetingEnd(mt.getMeetingEnd());
        caseData.setOrganizerId(mt.getOrganizerId());
        caseData.setRoomId(mt.getRoomId() + "-" + meetingCodeDao.selectDataByItemId(mt.getRoomId()).get(0).getItemName());
        caseData.setQty(mt.getQty());
        caseData.setApplydt(mt.getApplydt());

        List<MeetingItem> mtItem = meetingItemDao.selectDataByMeetingIdAndItemID(caseData.getMeetingId(),"A");
        Map<String, Integer> mtItemMap = new HashMap<>();
        mtItem.forEach(a -> {
            mtItemMap.put(a.getItemId(), a.getQty());
        });
        Eip06w010l00 eip06w010l00 = new Eip06w010l00();
        return eip06w010l00.createNewFile(caseData, mtItemMap);
    }

    /**
     * 依選取查詢會議
     *
     * @param caseData
     */
    public void queryMeeting(Eip06w010Case caseData){
        Meeting mt = new Meeting();
        mt.setMeetingId(Integer.valueOf(caseData.getMeetingId()));
        mt = meetingDao.selectDataByPrimaryKey(mt);
        caseData.setMeetingId(mt.getMeetingId().toString());
        caseData.setMeetingName(mt.getMeetingName());
        caseData.setChairman(mt.getChairman());
        caseData.setMeetingdt(DateUtility.changeDateType(mt.getMeetingdt()));
        caseData.setMeetingBegin(mt.getMeetingBegin());
        caseData.setMeetingEnd(mt.getMeetingEnd());
        caseData.setOrganizerId(userData.getUserId() + "-" + usersDao.selectByKey(mt.getOrganizerId()).getUser_name());
        caseData.setRoomId(mt.getRoomId() + "-" + meetingCodeDao.selectDataByItemId(mt.getRoomId()).get(0).getItemName());
        caseData.setMeetingQty(mt.getQty());
        caseData.setApplydt(mt.getApplydt());
//        caseData.setSelectedRoomId(caseData.getRoomId());

        //初始化已選項目
        List<MeetingItemAndMeetingCode> selectItemList = meetingItemDao.selectDataByMeetingId(caseData.getMeetingId(),"B");
        List<MeetingItemAndMeetingCode> selectFoodList = meetingItemDao.selectDataByMeetingId(caseData.getMeetingId(),"A");

        //初始化下拉選單
        List<MeetingCode> itemIdList = meetingCodeDao.selectDataByItemType("B");
        //排除已選項目
        itemIdList = itemIdList.stream()
                .filter(item -> selectItemList.stream().noneMatch(selectItem -> selectItem.getItemId().equals(item.getItemId())))
                .collect(Collectors.toList());

        caseData.setItemIdList(itemIdList.stream().map(Eip06w010Case.Eip06w010OptionCase::new).collect(Collectors.toList()));
        List<MeetingCode> foodIdList = meetingCodeDao.selectDataByItemType("A");
        //排除已選項目
        foodIdList = foodIdList.stream()
                .filter(food -> selectFoodList.stream().noneMatch(selectFood -> selectFood.getItemId().equals(food.getItemId())))
                .collect(Collectors.toList());
        caseData.setFoodIdList(foodIdList.stream().map(Eip06w010Case.Eip06w010OptionCase::new).collect(Collectors.toList()));

    }

    /**
     * 欄位驗證: 選擇會議日期、時間判斷會議室是否已預約或禁用
     * @param meetingId
     * @param meetingDt
     * @param meetingBegin
     * @param meetingEnd
     * @return
     */
    public List<MeetingCode> findValidRoominclBooked(String meetingId, String meetingDt, String meetingBegin, String meetingEnd){
        String using = timeConversionService.to48binary(meetingBegin, meetingEnd);
        return meetingCodeDao.findValidRoominclBookedByDtandUsing(meetingId, DateUtility.changeDateTypeToWestDate(meetingDt), using);
    }



    /**
     * 更新會議
     * @param caseData
     * @return
     */
    public void updateMeeting(Eip06w010Case caseData){
        Meeting mt = new Meeting();
        Meeting newMt = new Meeting();
        int meetingId = Integer.parseInt(caseData.getMeetingId().toString());
        mt.setMeetingId(meetingId);
        mt = meetingDao.selectDataByPrimaryKey(mt);
        BeanUtils.copyProperties(mt, newMt);

        String using = timeConversionService.to48binary(caseData.getMeetingBegin(), caseData.getMeetingEnd());

        newMt.setMeetingName(caseData.getMeetingName());
        newMt.setChairman(caseData.getChairman());
        newMt.setMeetingdt(DateUtility.changeDateTypeToWestDate(caseData.getMeetingdt()));
        newMt.setMeetingBegin(caseData.getMeetingBegin());
        newMt.setMeetingEnd(caseData.getMeetingEnd());
        newMt.setRoomId(caseData.getRoomId());
        newMt.setQty(caseData.getMeetingQty());
        newMt.setUpdt(LocalDateTime.now().withNano(0));
        newMt.setUsing(using);
        meetingDao.update(newMt);
        //update meetingItem
        //先清除原本的
        MeetingItem mtItem = new MeetingItem();
        mtItem.setMeetingId(meetingId);
        meetingItemDao.deleteData(mtItem);
        if(caseData.getItemIds().size()>0){
            for(Object obj : caseData.getItemIds()){
                MeetingItem newMtItem = new MeetingItem();
                newMtItem.setMeetingId(meetingId);
                newMtItem.setItemId(obj.toString());
                newMtItem.setQty(1);
                meetingItemDao.insertData(newMtItem);
            }
        }

        if(caseData.getFoodId_Qty().size()>1){
            for(int i = 0 ; i < caseData.getFoodId_Qty().size() ; i++){
                MeetingItem newMtItem = new MeetingItem();
                String foodId = caseData.getFoodId_Qty().get(i).toString().split(":")[1].replace("\"","");
                int foodIdQty = Integer.parseInt(caseData.getFoodId_Qty().get(i+1).toString().split(":")[1].replace("\"","").replace("}","").replace("]",""));
                newMtItem.setMeetingId(meetingId);
                newMtItem.setItemId(foodId);
                newMtItem.setQty(foodIdQty);
                meetingItemDao.insertData(newMtItem);
                i++;
            }
        }
    }


    /**
     * 刪除會議
     * @param caseData
     * @return
     */
    public void deleteMeeting(Eip06w010Case caseData){
        Meeting mt = new Meeting();
        Meeting newMt = new Meeting();
        int meetingId = Integer.parseInt(caseData.getMeetingId().toString());
        mt.setMeetingId(meetingId);
        mt = meetingDao.selectDataByPrimaryKey(mt);
        BeanUtils.copyProperties(mt, newMt);

        newMt.setStatus("B");
        newMt.setUpdt(LocalDateTime.now().withNano(0));

        meetingDao.update(newMt);

        //發佈公告
        announce(newMt);
    }

    /**
     * 發佈公告
     *
     * @param meeting
     */
    public void announce(Meeting meeting){
        Msgdata ms = new Msgdata();
        String newFseq = msgdataDao.getNextFseq();
        ms.setFseq(newFseq);
        /** 頁面型態  */
        ms.setPagetype("A");  // A:文章 B:連結
        /** 狀態 */
        ms.setStatus("4"); // 表單狀態： 0-處理中(暫存) 1-已上稿 2-已核可 3-核退 4-已上架 5-已下架 X-註銷(畫面自已上架維護成註銷)
        /** 屬性 */
        ms.setAttributype("1"); // 1:公告事項 2:最新消息 3:常用系統及網站 4:下載專區 5:輿情專區 6:人事室-行政院組織改造 7:各處室資訊網-單位簡介
                                // 8:各處室資訊網-業務資訊
        /** 訊息類別 */
        ms.setMsgtype("G");  //要寫啥
        /** 顯示位置 */
        ms.setLocatearea("3"); // 1:登入前 2:登入後 3:各處室資訊網
        /** 是否提供外部查詢 */
        ms.setIssearch("2"); // 1:是 2:否
        /** 顯示順序 */
        ms.setShoworder("0"); // 1~99，數字愈小，優先序愈高-  (順序怎麼定義)
        /** 是否置頂 */
//        ms.setIstop("2"); // 1:是 2:否
        /** 前台是否顯示 */
//        ms.setIsfront("1"); // 1:是 2:否
        /** 主旨/連結網址 */
        ms.setSubject("會議室取消通知");

//        原訂於112年4月12日預約使用十樓第一會議室舉辦之全民督工111年執行績效考核會議，因故取消改場地之登記使用，原登記使用人，
//        若仍有使用場地之需求，請重新辦理預約手續。(公告至:112/4/13)

        String meetingChgDt = DateUtility.changeDateTypeToChineseDate(meeting.getMeetingdt()).substring(0,3) + "年" +
                             meeting.getMeetingdt().substring(4,6) + "月" +
                             meeting.getMeetingdt().substring(6) + "日";

        LocalDate endAnnounceLocalDt = LocalDate.parse(meeting.getMeetingdt().substring(0,4) + "-" + meeting.getMeetingdt().substring(4,6) + "-" + meeting.getMeetingdt().substring(6)).plusDays(1);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endAnnounceDt = DateUtility.changeDateTypeToChineseDate(endAnnounceLocalDt.format(fmt).replaceAll("-",""));

        String content = "原訂於" + meetingChgDt + "預約使用" + meetingCodeDao.selectDataByItemId(meeting.getRoomId()).get(0).getItemName() +
                         "舉辦之" + meeting.getMeetingName() +
                         "會議，因故取消該場地之登記使用，原登記使用人，若仍有使用場地之需求，請重新辦理預約手續。(公告至:" +
                          endAnnounceDt.substring(0,3) + "/" +
                          endAnnounceDt.substring(3,5) + "/" +
                          endAnnounceDt.substring(5) + ")";
        /** 內文 */
        ms.setMcontent(content);
        /** 存放目錄 */
//        ms.setIndir(); // 存放的目錄路徑EX:/A/BC/
        /** 是否需要另開視窗 */
//        ms.setOplink(); // Y:是 N:否
        /** 上架時間 */
        ms.setReleasedt(LocalDate.now().format(fmt).replaceAll("-",""));
        /** 下架時間 */
        ms.setOfftime(endAnnounceLocalDt.format(fmt).replaceAll("-",""));
        /** 連絡單位 */
        ms.setContactunit(deptsDao.findByPk(usersDao.selectByKey(meeting.getOrganizerId()).getDept_id()).getDept_name());
        /** 聯絡人 */
        ms.setContactperson(usersDao.selectByKey(meeting.getOrganizerId()).getUser_name());
        /** 連絡電話 */
//        ms.setContacttel();
        /** 備註 */
//        ms.setMemo();
        /** 下架原因 */
//        ms.setOffreason();
        /** 建立人員 */
        ms.setCreatid(userData.getUserId());
        /** 建立時間 */
        ms.setCreatdt(LocalDateTime.now());
        /** 更新人員 */
//        ms.setUpdid();
        /** 更新時間 */
//        ms.setUpddt();

        msgdataDao.insert(ms);

        Msgavaildep msdep = new Msgavaildep();
        msdep.setFseq(newFseq);
        msdep.setAvailabledep("0");
        msgavaildepDao.insert(msdep);
    }
}
