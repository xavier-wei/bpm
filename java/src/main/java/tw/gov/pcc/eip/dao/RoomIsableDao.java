package tw.gov.pcc.eip.dao;
import tw.gov.pcc.eip.domain.RoomIsable;
import java.util.List;

/**
 * 會議室禁用(ROOMISABLE)
 * @author 2207003
 *
 */
public interface RoomIsableDao {
	public static final String TABLE_NAME = "ROOMISABLE";
	public List<RoomIsable> selectAllData();
	public int insertData(RoomIsable data);
	public int updateData(RoomIsable data);
	public int deleteData(RoomIsable data);

}
