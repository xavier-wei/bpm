package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.eip.common.cases.Eip06w060Case;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.dao.RoomIsableDao;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.RoomIsable;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 會議室管理
 * @author 2207003
 */
@Service
public class Eip06w060Service extends OnlineRegService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip06w060Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    MeetingCodeDao meetingCodeDao;

    @Autowired
    RoomIsableDao roomIsableDao;

    @Autowired
    private TimeConversionService timeConversionService;


    /**
     * 查詢會議室清單
     * @param caseData
     */

    public void getMeetingRoomList(Eip06w060Case caseData) {
        List<Eip06w060Case.MeetingCodeCase> list = meetingCodeDao.selectDataByItemTypeF(caseData.getItemTyp()).stream().map(t -> {
            Eip06w060Case.MeetingCodeCase meetingCodeCase = new Eip06w060Case.MeetingCodeCase();
            meetingCodeCase.setItemTyp(t.getItemTyp());
            meetingCodeCase.setItemId(t.getItemId());
            meetingCodeCase.setItemName(t.getItemName());
            meetingCodeCase.setQty(t.getQty());
            return meetingCodeCase;
        }).collect(Collectors.toList());
        caseData.setMeetingCodeCaseList(list);
    }

    /**
     * 查詢部分啟用清單
     * @param caseData
     */
    public void getRoomIsableList(Eip06w060Case caseData) {
        List<Eip06w060Case.RoomIsableCase> list = roomIsableDao.selectDataByItemId(caseData.getItemId()).stream().map(t -> {
            Eip06w060Case.RoomIsableCase roomIsableCase = new Eip06w060Case.RoomIsableCase();
            roomIsableCase.setItemNo(t.getItemNo());
            roomIsableCase.setItemId(t.getItemId());
            roomIsableCase.setItemName(t.getItemName());
            String isableDate=DateUtility.changeDateType(t.getIsableDate());
            roomIsableCase.setIsableDate(isableDate);
            roomIsableCase.setMeetingBegin(t.getMeetingBegin());
            roomIsableCase.setMeetingEnd(t.getMeetingEnd());
            return roomIsableCase;
        }).collect(Collectors.toList());
        caseData.setRoomIsableCaseList(list);
    }

    /**
     * 會議室啟用/禁用管理
     * @param caseData
     */
    public void getIsable(Eip06w060Case caseData) {
        MeetingCode meetingCode = new MeetingCode();
        meetingCode.setItemTyp(caseData.getItemTyp());
        meetingCode.setItemId(caseData.getItemId());
        meetingCode.setItemName(caseData.getItemName());
        meetingCode.setQty(caseData.getQty());
        meetingCodeDao.updateItemId(meetingCode, caseData.getItemId());
    }

    /**
     * 刪除 啟用時間
     * @param caseData
     */
    public void deleteClass(Eip06w060Case caseData) {
        roomIsableDao.deleteData(caseData.getItemId());
    }

    /**
     * 刪除 指定啟用時間
     * @param caseData
     */
    public void deleteSingleClass(Eip06w060Case caseData) {
        roomIsableDao.deleteSingleData(caseData.getItemNo());
    }


    /**
     * 初始化時間 開始選單
     * @param caseData
     */
    public void initSelectBeginList(Eip06w060Case caseData){
        Map<String, String> timeBeginListMap = new TreeMap<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 30) {
                String time = StringUtils.leftPad(String.valueOf(i),2, "0") + StringUtils.leftPad(String.valueOf(j),2, "0");
                timeBeginListMap.put(time, time);
            }
        }
        caseData.setTimeBeginMap(timeBeginListMap);
    }

    /**
     * 初始化時間 結束選單
     * @param caseData
     */
    public void initSelectEndList(Eip06w060Case caseData){
        Map<String, String> timeEndListMap = new TreeMap<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 30) {
                String time = StringUtils.leftPad(String.valueOf(i),2, "0") + StringUtils.leftPad(String.valueOf(j),2, "0");
                timeEndListMap.put(time, time);
            }
            String time = "2359";
            timeEndListMap.put(time, time);
        }
        caseData.setTimeEndMap(timeEndListMap);
    }


    /**
     * 新增(不重複)
     * @param caseData
     */
    public void insertClass(Eip06w060Case caseData) {
        RoomIsable roomIsable=new RoomIsable();
        roomIsable.setItemId(caseData.getItemId());
        roomIsable.setItemName(caseData.getItemName());
        String periodStart= DateUtility.changeDateTypeToWestDate(caseData.getPeriodStart());
        roomIsable.setIsableDate(periodStart);
        roomIsable.setMeetingBegin(caseData.getMeetingBegin());
        roomIsable.setMeetingEnd(caseData.getMeetingEnd());
        String isAbleTime = timeConversionService.to48binary_isAble(caseData.getMeetingBegin(), caseData.getMeetingEnd());
        roomIsable.setIsableTime(isAbleTime);
        roomIsableDao.insertData(roomIsable);
    }

    /**
     * 新增(重複)
     * @param caseData
     */
    public void multiInsertClass(Eip06w060Case caseData) throws ParseException {
        int week=caseData.getWeek(); //第X週
        int day=caseData.getDay(); //星期X
        String periodStart= DateUtility.changeDateTypeToWestDate(caseData.getPeriodStart());//起日
        String periodEnd = DateUtility.changeDateTypeToWestDate(caseData.getPeriodEnd());//訖日
        List<String> dateList = null;

        //判斷重複頻率(日.週.月)
        if(caseData.getDateWeekMonth().equals("date")) {
            dateList = getBeginAndEndDateBetween(periodStart, periodEnd);
        } else if (caseData.getDateWeekMonth().equals("week")) {
            dateList = getDayOfWeekWithinDateInterval(periodStart, periodEnd, day);
        } else if (caseData.getDateWeekMonth().equals("month")) {
            dateList = getDayOfWeekAndMonthWithinDateInterval(periodStart, periodEnd, week, day);
        }

        if(dateList != null){
            for(String date: dateList) {
                RoomIsable roomIsable = new RoomIsable();
                roomIsable.setItemId(caseData.getItemId());
                roomIsable.setItemName(caseData.getItemName());
                roomIsable.setIsableDate(date);
                roomIsable.setMeetingBegin(caseData.getMeetingBegin());
                roomIsable.setMeetingEnd(caseData.getMeetingEnd());
                String isAbleTime = timeConversionService.to48binary_isAble(caseData.getMeetingBegin(), caseData.getMeetingEnd());
                roomIsable.setIsableTime(isAbleTime);
                roomIsableDao.insertData(roomIsable);
            }
        }
    }

    /**
     * 計算 重複每天，共X天
     * @param periodStart
     * @param periodEnd
     * @return
     */
    public static List<String> getBeginAndEndDateBetween(String periodStart, String periodEnd) throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> list = new ArrayList<>();
        LocalDate periodStartObject = LocalDate.parse(periodStart, format);
        LocalDate periodEndObject = LocalDate.parse(periodEnd, format);
        LocalDate date = periodStartObject;
        for (; !date.isAfter(periodEndObject); date=date.plusDays(1)) {//為了改變同一個LocalDate物件，否則會new一個新的
            list.add(date.format(format)); // 將 LocalDate 型態轉為 String
        }
        return list;
    }

    /**
     * 計算 重複每星期X
     * @param periodStart
     * @param periodEnd
     * @param day
     * @return
     */
    public static List<String> getDayOfWeekWithinDateInterval(String periodStart, String periodEnd, int day) throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> list = new ArrayList<>();
        LocalDate periodStartObject = LocalDate.parse(periodStart, format);
        LocalDate periodEndObject = LocalDate.parse(periodEnd, format);

        //找出第一個符合的星期
        LocalDate firstDay = periodStartObject.with(TemporalAdjusters.next(DayOfWeek.of(day)));
        for (; !firstDay.isAfter(periodEndObject); firstDay = firstDay.plusWeeks(1)) {//為了改變同一個LocalDate物件，否則會new一個新的
            list.add(firstDay.format(format)); // 將LocalDate型態轉為String
        }
        return list;
    }

    /**
     * 計算 重複每月第X個 星期X
     * @param periodStart
     * @param periodEnd
     * @param week
     * @param  day
     * @return
     */

    public static List<String> getDayOfWeekAndMonthWithinDateInterval(String periodStart, String periodEnd, int week, int day)  throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> list = new ArrayList<>();
        LocalDate periodStartObject = LocalDate.parse(periodStart, format);
        LocalDate periodEndObject = LocalDate.parse(periodEnd, format);

//        // 找出第一個符合的週
//        LocalDate firstWeek = periodStartObject.with(TemporalAdjusters.dayOfWeekInMonth(week, DayOfWeek.of(day)));
//
//        for (LocalDate date = firstWeek; !date.isAfter(periodEndObject); date = date.plusWeeks(1)) {
//            if (date.equals(periodStartObject)) {
//                list.add(periodStartObject.format(format)); // 將LocalDate型態轉為String
//            }
//        }
//        return list;

        while (!periodStartObject.isAfter(periodEndObject)) {
            LocalDate date = periodStartObject.with(TemporalAdjusters.dayOfWeekInMonth(week, DayOfWeek.of(day)));
            if(periodStartObject.equals(date)){
                list.add(periodStartObject.format(format));//將LocalDate 型態轉為String
            }
            periodStartObject=periodStartObject.plusDays(1);
        }
        return list;
    }


    /**
     * 資料檢查
     * @param caseData {@link Eip06w060Case}
     * @param result {@link BindingResult}
     */
    public void validate(Eip06w060Case caseData, BindingResult result) {
        ObjectError error;
        if (isEmpty(caseData.getPeriodStart())) {
            error = new ObjectError("periodStart","開始日期不得為空");
            result.addError(error);
        }

        if (isEmpty(caseData.getMeetingBegin())) {
            error = new ObjectError("meetingBegin","開啟時間不得為空");
            result.addError(error);
        }
        if (isEmpty(caseData.getMeetingEnd())) {
            error = new ObjectError("meetingEnd","關閉時間不得為空");
            result.addError(error);
        }
        if (isNotEmpty(caseData.getMeetingBegin()) && isNotEmpty(caseData.getMeetingEnd())){
            if (Integer.parseInt(caseData.getMeetingBegin()) >= Integer.parseInt(caseData.getMeetingEnd())){
                error = new ObjectError("meetingEnd","「開啟時間」須早於「關閉時間」");
                result.addError(error);
            }
        }

    }


    /**
     * 查詢是否重覆
     * @param caseData
     */
    public void isableTime(Eip06w060Case caseData, BindingResult result) {
        //畫面民國年，資料庫西元年
        String periodStart=DateUtility.changeDateType(caseData.getPeriodStart());//periodStart等於isableData
        String isableTime=timeConversionService.to48binary_isAble(caseData.getMeetingBegin(), caseData.getMeetingEnd());
        int using=roomIsableDao.findByIsableTime(caseData.getItemId(), periodStart, isableTime);
            if (using == 1) {
                log.debug("輸入isableTime(啟用時間)已經存在");
                result.rejectValue("isableTime", "W0031", new Object[]{"啟用時間"}, "");//{0}已存在，請重新輸入！
            }

        //轉換輸入時間格式
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
        LocalTime begin=LocalTime.parse(caseData.getMeetingBegin(), format);
        LocalTime end=LocalTime.parse(caseData.getMeetingEnd(), format);

        //撈出會議室啟用清單
        List<RoomIsable> datelist=roomIsableDao.selectItemIdByDate(caseData.getItemId(), periodStart);


        //遍歷
        for (RoomIsable roomIsable : datelist){
            LocalTime beginDb=LocalTime.parse(roomIsable.getMeetingBegin(), format);
            LocalTime endDb=LocalTime.parse(roomIsable.getMeetingEnd(), format);
            
            if ((begin.isAfter(beginDb) && begin.isBefore(endDb))//開始介於某段時間之中
                    || (end.isAfter(beginDb) && end.isBefore(endDb))//結束介於某段時間之中
                    || (begin.isBefore(beginDb) && end.isAfter(endDb))//包含某段時間
                    || begin.equals(beginDb) || end.equals(endDb)) {//必重疊
                        log.debug("輸入isableTime(啟用時間)已經存在");
                        result.rejectValue("isableTime", "W0031", new Object[]{"啟用時間"}, "");//{0}已存在，請重新輸入！
                        break;

            }
        }

    }





}
