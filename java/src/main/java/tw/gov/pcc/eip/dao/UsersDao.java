package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Users;
import java.math.BigDecimal;
import java.util.List;

/**
 * 使用者資料 Dao
 */
@DaoTable(UsersDao.TABLE_NAME)
@Repository
public interface UsersDao {

    public String TABLE_NAME = "USERS";

    public Users selectByKey(String user_id);

    public int insert(Users users);

    public int updateByKey(Users users);

    public int deleteByKey(Users users);

	public int updateAcntisvalidByKey(Users users);

	public List<Users> selectDataByUserIdAndDeptId(String user_id, String dept_id);

	public List<Users> selectAll();
	

}