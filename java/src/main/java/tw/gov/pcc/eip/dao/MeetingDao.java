package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.domain.*;

import java.util.List;

/**
 * 會議主檔(MEETING)
 *
 * @author 2201009
 */
public interface MeetingDao {

    public static final String TABLE_NAME = "MEETING";
    public int insert(Meeting data);
    public int update(Meeting data);
    public int delete(Meeting data);
    public List<Meeting> selectAllData();
    public Meeting selectDataByPrimaryKey(Meeting meeting);
    /**
     * 新增資料
     * @param data
     * @return
     */
    public int insertData(Meeting data);

    /**
     * 查詢會議室ID最大值
     * @return
     */
    public Meeting findMaxMeetingId();

    /**
     * 依欄位查詢會議室
     * @param caseData
     * @return
     */
    public List<Eip06w010Case> selectDataByColumns(Eip06w010Case caseData);

    /**
     * 依日期查詢會議室
     * @param meetingdt
     * @return
     */
    public List<Eip06w040Report> selectValidMeetingByMeetingdt(String meetingdt);

    /**
     * 依儲存時間查詢所有受影響會議
     * @param dateList
     * @param using
     * @param roomId
     * @return
     */
    public List<Meeting> findExistedMeeting(List<String> dateList, String using, String roomId);


    /**
     * 依會議日期查詢所有會議
     * @param dueDate
     * @return
     */
    public List<Meeting> findDueMeeting(String dueDate);

    /**
     * 依MeetingIdItemID刪除會議細項
     * @param meetingId
     * @return
     */
    public void deleteDataByMeetingId(List<Integer> meetingId);

}
