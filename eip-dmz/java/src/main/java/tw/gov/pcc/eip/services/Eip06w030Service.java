package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.eip.common.cases.Eip06w030Case;
import tw.gov.pcc.eip.common.controllers.Eip06w020Controller;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.dao.MeetingItemDao;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.MeetingItem;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 會議室批次預約作業
 * @author 2201009
 */
@Service
public class Eip06w030Service {
    private static final Logger log = LoggerFactory.getLogger(Eip06w020Controller.class);

    @Autowired
    UserBean userData;
    @Autowired
    private MeetingCodeDao meetingCodeDao;
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private MeetingItemDao meetingItemDao;

    @Autowired
    private TimeConversionService timeConversionService;

    /**
     * 初始化下拉式選單
     * @param caseData
     */
    public void initSelectList(Eip06w030Case caseData){
        List<MeetingCode> itemIdList = meetingCodeDao.selectDataByItemType("B");
        caseData.setItemIdList(itemIdList.stream().map(Eip06w030Case.Eip06w030OptionCase::new).collect(Collectors.toList()));
        List<MeetingCode> foodIdList = meetingCodeDao.selectDataByItemType("A");
        caseData.setFoodIdList(foodIdList.stream().map(Eip06w030Case.Eip06w030OptionCase::new).collect(Collectors.toList()));

        //初始化會議起始時間下拉選單
        Map<String, String> meetingTimeMap = new TreeMap<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 30) {
                String time = StringUtils.leftPad(String.valueOf(i),2, "0") + StringUtils.leftPad(String.valueOf(j),2, "0");
                meetingTimeMap.put(time, time);
            }
        }

        //初始化會議室場地下拉選單
        List<MeetingCode> roomIdList = meetingCodeDao.selectDataByItemType("F");
        caseData.setRoomIdList(roomIdList.stream().map(Eip06w030Case.Eip06w030OptionCase::new).collect(Collectors.toList()));
        caseData.setMeetingTimeCombobox(meetingTimeMap);
    }


    /**
     * 資料檢查
     * @param caseData {@link Eip06w030Case}
     * @param bindingResult {@link BindingResult}
     */
    public void validate(Eip06w030Case caseData, BindingResult bindingResult) {
        ObjectError error;
        if (isEmpty(caseData.getMeetingName())) {
            error = new ObjectError("meetingName","會議室名稱不得為空");
            bindingResult.addError(error);
        }
        if (isEmpty(caseData.getChairman())) {
            error = new ObjectError("chairman","主持人不得為空");
            bindingResult.addError(error);
        }
        if (isEmpty(caseData.getMeetingBegin())) {
            error = new ObjectError("meetingBegin","會議開始時間不得為空");
            bindingResult.addError(error);
        }
        if (isEmpty(caseData.getMeetingEnd())) {
            error = new ObjectError("meetingEnd","會議結束時間不得為空");
            bindingResult.addError(error);
        }
        if (isNotEmpty(caseData.getMeetingBegin()) && isNotEmpty(caseData.getMeetingEnd())){
            if (Integer.parseInt(caseData.getMeetingBegin()) >= Integer.parseInt(caseData.getMeetingEnd())){
                error = new ObjectError("meetingEnd","「會議開始時間」須早於「會議結束時間」");
                bindingResult.addError(error);
            }
        }
        if (isEmpty(caseData.getPeriodStart())) {
            error = new ObjectError("periodStart","會議開始日期不得為空");
            bindingResult.addError(error);
        }else if(Integer.parseInt(DateUtility.getNowChineseDate()) > Integer.parseInt(caseData.getPeriodStart())){
            error = new ObjectError("periodStart","會議開始日期不得小於今日");
            bindingResult.addError(error);
        }
        if(caseData.isRepeat() && caseData.getPeriodEnd().equals("")){
            error = new ObjectError("periodEnd","會議結束日期不得為空");
            bindingResult.addError(error);
        } else if(caseData.isRepeat() && !caseData.getPeriodStart().equals("") && !caseData.getPeriodEnd().equals("")){
            if (Integer.parseInt(caseData.getPeriodStart()) >= Integer.parseInt(caseData.getPeriodEnd())){
                error = new ObjectError("periodEnd","「會議開始日期」須早於「會議結束日期」");
                bindingResult.addError(error);
            }
            if (Integer.parseInt(DateUtility.getNowChineseDate()) > Integer.parseInt(caseData.getPeriodEnd())){
                error = new ObjectError("periodStart","會議結束日期不得小於今日");
                bindingResult.addError(error);
            }
        }
        if (caseData.getMeetingQty() < 1) {
            error = new ObjectError("meetingQty","開會人數需大於1位");
            bindingResult.addError(error);
        }

        if(caseData.getFood_Qty().size() > 0 && !caseData.getFood_Qty().get(0).toString().equals("[]")){ //如果get(0)包含"]"表示傳入空值
            String s = null;
            boolean underTen = false;
            for(int i = 0 ; i < caseData.getFood_Qty().size() ; i++){
                s = StringUtils.replace(caseData.getFood_Qty().get(i).toString(),"]","");
                s = StringUtils.replace(s,"[","");
                int qty = Integer.parseInt(s);
                if (qty < 10){
                    underTen = true;
                    break;
                }
            }
            if(underTen){
                error = new ObjectError("food_Qty","「餐點數量」需大於或等於10");
                bindingResult.addError(error);
            }
        }
    }

    /**
     * 依儲存時間查詢所有受影響會議
     *
     * @param map
     * @return
     * @throws ParseException
     */
    public List<Meeting> findExistedMeeting(Map<Object, Object> map) throws ParseException {
        String using = timeConversionService.to48binary(map.get("meetingBegin").toString(), map.get("meetingEnd").toString());
        String roomId = map.get("roomId").toString();
        String periodStart = DateUtility.changeDateTypeToWestDate(map.get("periodStart").toString());
        String periodEnd = DateUtility.changeDateTypeToWestDate(map.get("periodEnd").toString());
        periodStart = periodStart.substring(0,4) + "-" + periodStart.substring(4,6) + "-" + periodStart.substring(6);
        periodEnd = periodEnd.substring(0,4) + "-" + periodEnd.substring(4,6) + "-" + periodEnd.substring(6);
        String dateWeekMonth = map.get("dateWeekMonth").toString();
        String repeat = map.get("repeat").toString();
        int day = Integer.parseInt(map.get("day").toString());
        int week = Integer.parseInt(map.get("week").toString());
        List<String> datelist =new ArrayList<>();
        if(repeat.equals("true")){
            if(dateWeekMonth.equals("date")) {   //每天
                datelist = getBeginAndEndDateBetween(periodStart, periodEnd);

            } else if (dateWeekMonth.equals("week")) {  //每周
                datelist = getDayOfWeekWithinDateInterval(periodStart, periodEnd, day);
            } else if (dateWeekMonth.equals("month")) {  //每月第幾週
                datelist = getDayOfWeekAndMonthWithinDateInterval(periodStart, periodEnd, week, day);
            }
        }else {
            datelist.add(periodStart);
        }

        List<String> newDatelist =new ArrayList<>();
        datelist.forEach(a -> {
            newDatelist.add(a.replaceAll("-",""));
        });
        return meetingDao.findExistedMeeting(newDatelist, using, roomId);
    }

    /**
     * 新增會議
     * @param caseData
     * @return
     */
    public void saveMeeting(Eip06w030Case caseData){
        Meeting meeting = new Meeting();
        String using = timeConversionService.to48binary(caseData.getMeetingBegin(), caseData.getMeetingEnd());
        int maxNum = meetingDao.findMaxMeetingId().getMeetingId() + 1;
        meeting.setMeetingId(maxNum);
        meeting.setMeetingName(caseData.getMeetingName());
        meeting.setChairman(caseData.getChairman());
        meeting.setOrganizerId(userData.getUserId());
        meeting.setMeetingdt(DateUtility.changeDateTypeToWestDate(caseData.getPeriodStart()));
        meeting.setMeetingBegin(caseData.getMeetingBegin());
        meeting.setMeetingEnd(caseData.getMeetingEnd());
        meeting.setRoomId(caseData.getRoomId());
        meeting.setQty(caseData.getMeetingQty());
        meeting.setApplydt(LocalDateTime.now());
        meeting.setUsing(using);
        meetingDao.insertData(meeting);

        if(caseData.getItemIds().size()>0){
            for(Object obj : caseData.getItemIds()){
                MeetingItem mtItem = new MeetingItem();
                mtItem.setMeetingId(maxNum);
                mtItem.setItemId(obj.toString());
                mtItem.setQty(1);
                meetingItemDao.insertData(mtItem);
            }
        }

        if(caseData.getFoodId_Qty().size()>1){
            for(int i = 0 ; i < caseData.getFoodId_Qty().size() ; i++){
                MeetingItem mtItem = new MeetingItem();
                String foodId = caseData.getFoodId_Qty().get(i).toString().split(":")[1].replace("\"","");
                int foodIdQty = Integer.parseInt(caseData.getFoodId_Qty().get(i+1).toString().split(":")[1].replace("\"","").replace("}","").replace("]",""));
                mtItem.setMeetingId(maxNum);
                mtItem.setItemId(foodId);
                mtItem.setQty(foodIdQty);
                meetingItemDao.insertData(mtItem);
                i++;
            }
        }
    }

    /**
     * 新增多個會議
     * @param caseData
     * @return
     */
    public void saveMultiMeeting(Eip06w030Case caseData) throws ParseException {
        deleteMeeting(caseData);
        Meeting meeting = new Meeting();
        String using = timeConversionService.to48binary(caseData.getMeetingBegin(), caseData.getMeetingEnd());
        meeting.setMeetingName(caseData.getMeetingName());
        meeting.setOrganizerId(userData.getUserId());
        meeting.setMeetingBegin(caseData.getMeetingBegin());
        meeting.setMeetingEnd(caseData.getMeetingEnd());
        meeting.setRoomId(caseData.getRoomId());
        meeting.setQty(caseData.getMeetingQty());
        meeting.setApplydt(LocalDateTime.now());
        meeting.setUsing(using);

        int maxNum = meetingDao.findMaxMeetingId().getMeetingId();

        String periodStart = DateUtility.changeDateTypeToWestDate(caseData.getPeriodStart());
        String periodEnd = DateUtility.changeDateTypeToWestDate(caseData.getPeriodEnd());
        periodStart = periodStart.substring(0,4) + "-" + periodStart.substring(4,6) + "-" + periodStart.substring(6);
        periodEnd = periodEnd.substring(0,4) + "-" + periodEnd.substring(4,6) + "-" + periodEnd.substring(6);

        if(caseData.getDateWeekMonth().equals("date")) {   //每天
            List<String> dateList = getBeginAndEndDateBetween(periodStart, periodEnd);
            saveMultiMeeting(meeting, maxNum, caseData, dateList);
        } else if (caseData.getDateWeekMonth().equals("week")) {  //每周
            List<String> dateList = getDayOfWeekWithinDateInterval(periodStart, periodEnd, caseData.getDay());
            saveMultiMeeting(meeting, maxNum, caseData, dateList);
        } else if (caseData.getDateWeekMonth().equals("month")) {  //每月第幾週
            List<String> dateList = getDayOfWeekAndMonthWithinDateInterval(periodStart, periodEnd, caseData.getWeek(), caseData.getDay());
            saveMultiMeeting(meeting, maxNum, caseData, dateList);
        }
    }

    /**
     * 獲取兩個日期之間的所有日期
     *
     * @param periodStart
     * @param periodEnd
     * @return
     */
    public static List<String> getBeginAndEndDateBetween(String periodStart, String periodEnd) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        Date dateStart = sdf.parse(periodStart);
        Date dateEnd = sdf.parse(periodEnd);
        Calendar cd = Calendar.getInstance(); //用Calendar進行日期比較判斷
        while (dateStart.getTime() <= dateEnd.getTime()){
            list.add(sdf.format(dateStart));
            cd.setTime(dateStart);
            cd.add(Calendar.DATE, 1); //增加一天放入list
            dateStart = cd.getTime();
        }
        return list;
    }

    /**
     * 獲取兩個日期之間的所有weekDays(星期)
     *
     * @param periodStart
     * @param periodEnd
     * @param weekDays (星期)
     * @return
     * @throws ParseException
     */
    public static List<String> getDayOfWeekWithinDateInterval(String periodStart, String periodEnd, int weekDays) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        Date dateStart = sdf.parse(periodStart);
        Date dateEnd = sdf.parse(periodEnd);
        Calendar cd = Calendar.getInstance(); //用Calendar進行日期比較判斷
        cd.setTime(dateStart);
        while (dateStart.getTime() <= dateEnd.getTime()){
            if((cd.get(Calendar.DAY_OF_WEEK) - 1) == weekDays){
                list.add(sdf.format(dateStart));
            }
            cd.setTime(dateStart);
            cd.add(Calendar.DATE, 1); //增加一天放入list
            dateStart = cd.getTime();
        }
        return list;
    }


    /**
     * 獲取兩個日期之間的第幾個星期
     *
     * @param periodStart
     * @param periodEnd
     * @param week  第幾個星期
     * @param weekDays  星期幾
     * @return
     * @throws ParseException
     */
    public static List<String> getDayOfWeekAndMonthWithinDateInterval(String periodStart, String periodEnd, int week, int weekDays) throws ParseException {
        List<String> list = new ArrayList<>();
        LocalDate dateStart = LocalDate.parse(periodStart);
        LocalDate dateEnd = LocalDate.parse(periodEnd);

        while (dateEnd.compareTo(dateStart) >= 0 ){
            LocalDate date = dateStart.with(TemporalAdjusters.dayOfWeekInMonth(week,DayOfWeek.of(weekDays)));
            if(dateStart.equals(date)){
                list.add(dateStart.toString().replace("-",""));
            }
            dateStart = dateStart.plusDays(1);
        }
        return list;
    }

    /**
     * 新增會議及會議物品
     *
     * @param meeting
     * @param maxNum
     * @param caseData
     * @param dateList
     */
    public void saveMultiMeeting(Meeting meeting, int maxNum, Eip06w030Case caseData, List<String> dateList){
        for (String date : dateList) {
            date = date.replace("-","");
            maxNum += 1;
            meeting.setMeetingId(maxNum);
            meeting.setChairman(caseData.getChairman());
            meeting.setMeetingdt(date);
            meetingDao.insertData(meeting);

            if (caseData.getItemIds().size() > 0) {
                for (Object obj : caseData.getItemIds()) {
                    MeetingItem mtItem = new MeetingItem();
                    mtItem.setMeetingId(maxNum);
                    mtItem.setItemId(obj.toString());
                    mtItem.setQty(1);
                    meetingItemDao.insertData(mtItem);
                }
            }

            if (caseData.getFoodId_Qty().size() > 1) {
                for (int i = 0; i < caseData.getFoodId_Qty().size(); i++) {
                    MeetingItem mtItem = new MeetingItem();
                    String foodId = caseData.getFoodId_Qty().get(i).toString().split(":")[1].replace("\"", "");
                    int foodIdQty = Integer.parseInt(caseData.getFoodId_Qty().get(i + 1).toString().split(":")[1].replace("\"", "").replace("}", "").replace("]", ""));
                    mtItem.setMeetingId(maxNum);
                    mtItem.setItemId(foodId);
                    mtItem.setQty(foodIdQty);
                    meetingItemDao.insertData(mtItem);
                    i++;
                }
            }
        }
    }

    /**
     * 刪除會議
     * @param caseData
     * @return
     */
    public void deleteMeeting(Eip06w030Case caseData){
        List<Integer> meetingIds = new ArrayList<>();
        caseData.getMeetingsNeedCancel().forEach(a -> {
            meetingIds.add(Integer.parseInt(a.toString().substring(0,a.toString().indexOf("-")).replace("\"","")));
        });

        Meeting mt = new Meeting();
        Meeting newMt = new Meeting();

        for ( int meetingId : meetingIds) {
            mt.setMeetingId(meetingId);
            mt = meetingDao.selectDataByPrimaryKey(mt);
            BeanUtils.copyProperties(mt, newMt);

            newMt.setStatus("B");
            newMt.setUpdt(LocalDateTime.now().withNano(0));
            meetingDao.update(newMt);
        }
    }
}
