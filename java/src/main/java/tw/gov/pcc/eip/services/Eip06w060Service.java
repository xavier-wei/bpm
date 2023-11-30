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
     * 查詢部分禁用清單
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
     * 會議室禁用/禁用管理
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
     * 刪除 禁用時間
     * @param caseData
     */
    public void deleteClass(Eip06w060Case caseData) {
        roomIsableDao.deleteData(caseData);
    }

    /**
     * 刪除 指定禁用時間
     * @param caseData
     */
    public void deleteSingleClass(Eip06w060Case caseData) {
        roomIsableDao.deleteSingleData(caseData);
    }

    /**
     * 刪除 已過期禁用時間
     * @param caseData
     */
    public void deletePastClass(Eip06w060Case caseData) {
        roomIsableDao.deletePastData(caseData);
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
            getBeginAndEndDateBetween(caseData, periodStart, periodEnd);
        } else if (caseData.getDateWeekMonth().equals("week")) {
            getDayOfWeekWithinDateInterval(caseData, periodStart, periodEnd, day);
        } else if (caseData.getDateWeekMonth().equals("month")) {
            getDayOfWeekAndMonthWithinDateInterval(caseData, periodStart, periodEnd, week, day);
        }
    }

    /**
     * 批次新增
     * @param caseData
     * @param formattedDate
     */
    private void insertRoomIsableData(Eip06w060Case caseData, String formattedDate) {
        RoomIsable roomIsable = new RoomIsable();
        roomIsable.setItemId(caseData.getItemId());
        roomIsable.setItemName(caseData.getItemName());
        roomIsable.setIsableDate(formattedDate);
        roomIsable.setMeetingBegin(caseData.getMeetingBegin());
        roomIsable.setMeetingEnd(caseData.getMeetingEnd());
        String isAbleTime = timeConversionService.to48binary_isAble(caseData.getMeetingBegin(), caseData.getMeetingEnd());
        roomIsable.setIsableTime(isAbleTime);
        roomIsableDao.insertData(roomIsable);
    }

    /**
     * 計算 重複每天，共X天
     * @param periodStart
     * @param periodEnd
     * @return
     */
    public void getBeginAndEndDateBetween(Eip06w060Case caseData, String periodStart, String periodEnd) throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> list = new ArrayList<>();
        LocalDate periodStartObject = LocalDate.parse(periodStart, format);
        LocalDate periodEndObject = LocalDate.parse(periodEnd, format);
        LocalDate date = periodStartObject;
        for (; !date.isAfter(periodEndObject); date=date.plusDays(1)) {//為了改變同一個LocalDate物件，否則會new一個新的
            insertRoomIsableData(caseData, date.format(format));
        }
    }

    /**
     * 計算 重複每星期X
     * @param periodStart
     * @param periodEnd
     * @param day
     * @return
     */
    public void getDayOfWeekWithinDateInterval(Eip06w060Case caseData, String periodStart, String periodEnd, int day) throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> list = new ArrayList<>();
        LocalDate periodStartObject = LocalDate.parse(periodStart, format);
        LocalDate periodEndObject = LocalDate.parse(periodEnd, format);

        //找出第一個符合的星期
        LocalDate date = periodStartObject.with(TemporalAdjusters.next(DayOfWeek.of(day)));
        for (; !date.isAfter(periodEndObject); date = date.plusWeeks(1)) {//為了改變同一個LocalDate物件，否則會new一個新的
            insertRoomIsableData(caseData, date.format(format));
        }
    }

    /**
     * 計算 重複每月第X個 星期X
     * @param periodStart
     * @param periodEnd
     * @param week
     * @param  day
     * @return
     */
    public void getDayOfWeekAndMonthWithinDateInterval(Eip06w060Case caseData, String periodStart, String periodEnd, int week, int day)  throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> list = new ArrayList<>();
        LocalDate periodStartObject = LocalDate.parse(periodStart, format);
        LocalDate periodEndObject = LocalDate.parse(periodEnd, format);
          for (; !periodStartObject.isAfter(periodEndObject); periodStartObject = periodStartObject.plusDays(1)) {
              LocalDate date = periodStartObject.with(TemporalAdjusters.dayOfWeekInMonth(week, DayOfWeek.of(day)));
              if (periodStartObject.equals(date)) {
                  insertRoomIsableData(caseData, date.format(format));
              }
          }
    }


    /**
     * 資料檢查
     * @param caseData {@link Eip06w060Case}
     * @param result {@link BindingResult}
     */
    public void validate(Eip06w060Case caseData, BindingResult result) {
        ObjectError error;
        if(caseData.isRepeat()==false) {
            if (isEmpty(caseData.getPeriodStart())) {
                error = new ObjectError("periodStart", "「日期」為必填");
                result.addError(error);
            }
        }

        if(caseData.isRepeat()==true){
            if (isEmpty(caseData.getPeriodStart()) && isEmpty(caseData.getPeriodEnd())) {
                error = new ObjectError("periodStart", "「日期」為必填");
                result.addError(error);
            } else if (isEmpty(caseData.getPeriodStart())) {
                error = new ObjectError("periodStart", "「日期」為必填");
                result.addError(error);
            } else if (isEmpty(caseData.getPeriodEnd())) {
                error = new ObjectError("periodEnd", "「日期」為必填");
                result.addError(error);
            } else if (Integer.parseInt(caseData.getPeriodStart()) >= Integer.parseInt(caseData.getPeriodEnd())){
               error = new ObjectError("meetingEnd","「起日」須早於「訖日」");
               result.addError(error);
           }
       }

        if (isEmpty(caseData.getMeetingBegin())) {
            error = new ObjectError("meetingBegin","「禁用時間起」為必填");
            result.addError(error);
        }
        if (isEmpty(caseData.getMeetingEnd())) {
            error = new ObjectError("meetingEnd","「禁用時間訖」為必填");
            result.addError(error);
        }

        if (isNotEmpty(caseData.getMeetingBegin()) && isNotEmpty(caseData.getMeetingEnd())){
            if (Integer.parseInt(caseData.getMeetingBegin()) >= Integer.parseInt(caseData.getMeetingEnd())){
                error = new ObjectError("meetingEnd","「開始時間」須早於「結束時間」");
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

        //轉換輸入時間格式
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
        LocalTime begin=LocalTime.parse(caseData.getMeetingBegin(), format);
        LocalTime end=LocalTime.parse(caseData.getMeetingEnd(), format);

        //撈出會議室禁用清單
        List<RoomIsable> datelist=roomIsableDao.selectItemIdByDate(caseData.getItemId(), periodStart);


        //遍歷
        for (RoomIsable roomIsable : datelist){
            LocalTime beginDb=LocalTime.parse(roomIsable.getMeetingBegin(), format);
            LocalTime endDb=LocalTime.parse(roomIsable.getMeetingEnd(), format);
            
            if ((begin.isAfter(beginDb) && begin.isBefore(endDb))//開始介於某段時間之中
                    || (end.isAfter(beginDb) && end.isBefore(endDb))//結束介於某段時間之中
                    || (begin.isBefore(beginDb) && end.isAfter(endDb))//包含某段時間
                    || begin.equals(beginDb) || end.equals(endDb)) {//必重疊
                        log.debug("輸入isableTime(禁用時間)已經存在");
                        result.rejectValue("isableTime", "W0031", new Object[]{"禁用時間"}, "");//{0}已存在，請重新輸入！
                        break;

            }
        }

    }





}
