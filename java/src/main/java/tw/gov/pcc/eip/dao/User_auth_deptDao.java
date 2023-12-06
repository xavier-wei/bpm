package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.User_auth_dept;

/**
 * 跨部門審核 Dao
 */
@DaoTable(User_auth_deptDao.TABLE_NAME)
@Repository
public interface User_auth_deptDao {

    public String TABLE_NAME = "User_auth_dept";

    public List<User_auth_dept> selectByUser_id(String user_id);

}