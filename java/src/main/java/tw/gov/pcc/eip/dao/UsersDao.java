package tw.gov.pcc.eip.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.common.cases.Eip02w010Case.addressBook;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
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
     * @param sort 1:查全處 2:查單一部門
     * @param user_name
     * @param user_id
     * @param ename
     * @param email
     * @param titlename 職稱
     * @return
     */
    public List<addressBook> getEip02wUsers(String dept_id, String sort, String user_name, String user_id, String ename,
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

    /**
     * 根據多個部門id或多個職稱id取得user清單
     * @param deptID
     * @param titleID
     * @return
     */
    public List<Users> getUsersByDeptOrTitle(List<String> deptID, List<String> titleID);


    /**
     * [重要列管維護之列管單位窗口聯絡對象]
     * @param codeNameList
     * @return
     */
    public List<Users> getEmailList(List<String> codeNameList);

	public List<Users> selectDataByLikeUserIdOrLikeNameAndEqualDeptid(String user_id, String user_name,List<String> deptidList);

    /**
     * 依部門id查詢使用者
     * 
     * @param deptId
     * @return
     */
    public List<Users> getDeptUsers(String deptId);
}