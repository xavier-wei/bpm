package tw.gov.pcc.eip.dao;

import org.springframework.lang.Nullable;
import tw.gov.pcc.eip.domain.MeetingItem;
import tw.gov.pcc.eip.domain.MeetingItemAndMeetingCode;

import java.util.List;

/**
 * 會議物件(MEETINGITEM)
 *
 * @author 2201009
 */
public interface MeetingItemDao {

    public static final String TABLE_NAME = "MEETINGITEM";

    public List<MeetingItem> selectAllData();

    public int insertData(MeetingItem data);

    public int updateData(MeetingItem data);

    public int deleteData(MeetingItem data);

    /**
     * 依MeetingIdItemID查詢會議細項
     * @param meetingId
     * @param itemID
     * @return
     */
    public List<MeetingItem> selectDataByMeetingIdAndItemID(String meetingId, @Nullable String itemID);



    /**
     * 依會議ID查詢MeetingItem and MeetingCode
     *
     * @param meetingId
     * @param itemTyp
     * @return
     */
    public List<MeetingItemAndMeetingCode> selectDataByMeetingId(String meetingId, String itemTyp);

    /**
     * 依MeetingIdItemID刪除會議細項
     * @param meetingId
     * @return
     */
    public void deleteDataByMeetingId(List<Integer> meetingId);

}
