package tw.gov.pcc.eip.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.common.cases.Eip02w010Case.addressBook;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Users;

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

    /**
     * 通訊錄查詢
     * 
     * @param dept_id
     * @param user_name
     * @param user_id
     * @param ename
     * @param email
     * @param titlename 職稱
     * @return
     */
    public List<addressBook> getEip02wUsers(String dept_id, String user_name, String user_id, String ename,
            String email, String titlename);

    public List<Users> getEip03wUsers(List<String> deptID);

    /**
     *
     * @param userIDs
     * @return
     */
    public List<Users> findNameByMultiID(List<String> userIDs);

    /**
     * 依照user_name查詢ID
     * @param userName
     * @return
     */
    public List<Users> findUserIDByUserName(String userName);

    void insertUsersFromView_cpape05m();
}