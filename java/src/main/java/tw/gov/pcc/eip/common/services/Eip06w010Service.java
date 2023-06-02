package tw.gov.pcc.eip.common.services;

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
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.dao.MeetingItemDao;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.MeetingItem;
import tw.gov.pcc.eip.domain.MeetingItemAndMeetingCode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private MeetingCodeDao meetingCodeDao;
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private MeetingItemDao meetingItemDao;
    /**
     * 初始化下拉式選單
     * @param caseData
     */
    public void initSelectList(Eip06w010Case caseData){
        List<MeetingCode> roomIdList = meetingCodeDao.selectDataByItemType("F");
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
        });
        resultList.forEach(a->{
            List<MeetingItem> mtItem =  meetingItemDao.selectDataByMeetingIdAndItemID(a.getMeetingId(), "A");
            a.setOrderFood(mtItem.size() > 0);
        });
        caseData.setResultList(resultList.stream().map(Eip06w010Case::new).collect(Collectors.toList()));

        //初始化會議起始時間下拉選單
        Map<String, String> meetingTimeMap = new TreeMap<>();
         for (int i = 0; i < 24; i++) {
             for (int j = 0; j < 60; j += 30) {
                 String time = StringUtils.leftPad(String.valueOf(i),2, "0") + StringUtils.leftPad(String.valueOf(j),2, "0");
                 meetingTimeMap.put(time, time);
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
    public List<Map<String, String>> findSelectedItemandFood(String meetingId){
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
        List<Eip06w010Case> resultList = meetingDao.selectDataByColumns(caseData);
        resultList.forEach(a->{
            if(a.getOrganizerId().equals(userData.getUserId())){
                a.setEditable(true);
            }else {
                a.setEditable(false);
            }
        });
        resultList.forEach(a->{
            List<MeetingItem> mtItem =  meetingItemDao.selectDataByMeetingIdAndItemID(a.getMeetingId(), "A");
            a.setOrderFood(mtItem.size() > 0);
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
        caseData.setOrganizerId(mt.getOrganizerId());
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
        String using = to48binary(meetingBegin, meetingEnd);
        return meetingCodeDao.findValidRoominclBookedByDtandUsing(meetingId, meetingDt, using);
    }

    /**
     * 起訖時間轉為48位二進制編碼
     * 0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000
     * ex 1300~1430 0000_0000_0000_0000_0000_0000_0011_1000_0000_0000_0000_0000
     * @param begin
     * @param end
     * @return
     */
    public String to48binary(String begin, String end){
        StringBuilder using = new StringBuilder(); //回傳值
        //取得時間差間隔幾個半小時
        LocalTime beginTime = LocalTime.parse(begin.substring(0,2) + ":" + begin.substring(2));
        LocalTime endTime = LocalTime.parse(end.substring(0,2) + ":" + end.substring(2));
        Duration duration = Duration.between(beginTime, endTime);
        int halfHours = Integer.parseInt(String.valueOf(duration.getSeconds()/1800));
        //取得開始時間位置
        //前兩位如果是00，後兩位是30? +0 : +1
        //前兩位如果不是00，取前兩位數/100*2，後兩位是30? +0 : +1
        int beginPosition = 0;
        if(begin.startsWith("00")){
            beginPosition += StringUtils.equals(begin.substring(2),"30")? 1 : 0;
        }else {
            beginPosition = Integer.parseInt(begin.substring(0,2) + "00")/100*2;
            beginPosition += StringUtils.equals(begin.substring(2),"30")? 1 : 0;
        }

        //生成48字元字串
        for(int i = 0 ; using.length() < 48 ; i++){
            if(i!=beginPosition){
                using.append("0");
            }else {
                //將開始時間+半小時個數填為1 其餘皆為0
                for (int j = 0; j < halfHours ; j++ ){
                    using.append("1");
                }
                using.append("0");
            }
        }
        //若訖的時間為2330 則將第48位元轉成1
        if(StringUtils.equals(end,"2330")){
            using.setCharAt(using.length()-1, '1');
        }
        return using.toString();
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

        String using = to48binary(caseData.getMeetingBegin(), caseData.getMeetingEnd());

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
    }
}
