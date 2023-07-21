package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.eip.common.cases.Eip06w030Case;
import tw.gov.pcc.eip.common.cases.Eip06w050Case;
import tw.gov.pcc.eip.common.cases.Eip06w060Case;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.dao.MeetingItemDao;
import tw.gov.pcc.eip.dao.RoomIsableDao;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.RoomIsable;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
            for (int j = 29; j < 60; j += 30) {
                String time = StringUtils.leftPad(String.valueOf(i),2, "0") + StringUtils.leftPad(String.valueOf(j),2, "0");
                timeEndListMap.put(time, time);
            }
        }
        caseData.setTimeEndMap(timeEndListMap);
    }


    /**
     * 新增
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
    }





}
