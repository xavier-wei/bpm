package tw.gov.pcc.eip.dao;
import tw.gov.pcc.eip.domain.MeetingCode;
import  java.util.List;

/**
 * 參數(MEETINGCODE)
 * @author 2207003
 *
 */
public interface MeetingCodeDao {
	public static final String TABLE_NAME = "MEETINGCODE";
	public List<MeetingCode> selectAllData();
	public int insertData(MeetingCode data);
	public int updateData(MeetingCode data);
	public int deleteData(MeetingCode data);

	/**
	 * 查詢by ItemType
	 * @param itemtyp
	 * @return
	 */
	public List<MeetingCode> selectDataByItemType(String itemtyp);

	/**
	 * 查詢by itemId
	 * @param itemId
	 * @return
	 */
	public List<MeetingCode> selectDataByItemId(String itemId);

	/**
	 * 查詢by itemName
	 * @param itemName
	 * @return
	 */
	public List<MeetingCode> selectDataByItemName(String itemName);

	/**
	 * 依會議日期及區間查詢Meeting / Roomdiable可用會議室
	 * @param meetingDt
	 * @param using
	 * @return
	 */
	public List<MeetingCode> findValidRoomByDtandUsing(String meetingDt, String using);


	/**
	 * 依會議日期及區間查詢Meeting / Roomdiable可用會議室(包含使用者選取的會議時間)
	 * @param meetingId
	 * @param meetingDt
	 * @param using
	 * @return
	 */
	public List<MeetingCode> findValidRoominclBookedByDtandUsing(String meetingId, String meetingDt, String using);
}