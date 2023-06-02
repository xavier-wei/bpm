package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.domain.Code;
import tw.gov.pcc.eip.domain.Eip06w040Report;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.domain.MeetingItemAndMeetingCode;

import java.util.List;
import java.util.Optional;

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
    public List<Eip06w040Report> selectDataByMeetingdt(String meetingdt);
}
