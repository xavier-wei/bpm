package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip06w020Case;
import tw.gov.pcc.eip.common.controllers.Eip06w020Controller;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.dao.MeetingItemDao;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.MeetingItem;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 會議室預約作業
 * @author 2201009
 */
@Service
public class Eip06w020Service {
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
    public void initSelectList(Eip06w020Case caseData){
        List<MeetingCode> itemIdList = meetingCodeDao.selectDataByItemType("B");
        caseData.setItemIdList(itemIdList.stream().map(Eip06w020Case.Eip06w020OptionCase::new).collect(Collectors.toList()));
        List<MeetingCode> foodIdList = meetingCodeDao.selectDataByItemType("A");
        caseData.setFoodIdList(foodIdList.stream().map(Eip06w020Case.Eip06w020OptionCase::new).collect(Collectors.toList()));

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
     * 欄位驗證: 選擇會議日期、時間判斷會議室是否已預約或禁用
     *
     * @param meetingDt
     * @param meetingBegin
     * @param meetingEnd
     * @return
     */
    public List<MeetingCode> findValidRoom(String meetingDt, String meetingBegin, String meetingEnd){
        String using = timeConversionService.to48binary(meetingBegin, meetingEnd);
        return meetingCodeDao.findValidRoomByDtandUsing(DateUtility.changeDateTypeToWestDate(meetingDt), using);
    }

    /**
     * 新增會議
     * @param caseData
     * @return
     */
    public void saveMeeting(Eip06w020Case caseData){
        Meeting meeting = new Meeting();
        String using = timeConversionService.to48binary(caseData.getMeetingBegin(), caseData.getMeetingEnd());
        int maxNum = meetingDao.findMaxMeetingId().getMeetingId() + 1;
        meeting.setMeetingId(maxNum);
        meeting.setMeetingName(caseData.getMeetingName());
        meeting.setChairman(caseData.getChairman());
        meeting.setOrganizerId(userData.getUserId());
        meeting.setMeetingdt(DateUtility.changeDateTypeToWestDate(caseData.getMeetingdt()));
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
}
