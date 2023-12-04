package tw.gov.pcc.eip.dao;
import tw.gov.pcc.eip.common.cases.Eip06w060Case;
import tw.gov.pcc.eip.domain.RoomIsable;
import java.util.List;

/**
 * 會議室啟用(ROOMISABLE)
 * @author 2207003
 *
 */
public interface RoomIsableDao {
	public static final String TABLE_NAME = "ROOMISABLE";
	public List<RoomIsable> selectAllData();
	public int insertData(RoomIsable data);
	public int updateData(RoomIsable data);
	public int deleteData(Eip06w060Case caseData);

	public int deleteSingleData(Eip06w060Case caseData);

	public int deletePastData(Eip06w060Case caseData);

	/**
	 * 查詢 部分啟用會議室
	 * @param itemId
	 * @return
	 */
	public List<RoomIsable> selectDataByItemId(String itemId);

	/**
	 * 查詢 會議室當日啟用時段
	 * @param itemId
	 * @param isableDate
	 * @return
	 */
	public List<RoomIsable> selectItemIdByDate(String itemId, String isableDate);



}
