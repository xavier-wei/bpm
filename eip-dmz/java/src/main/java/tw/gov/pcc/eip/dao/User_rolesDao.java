package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.User_roles;

import java.util.List;

/**
 * 使用者可使用的角色資料 Dao
 */
@DaoTable(User_rolesDao.TABLE_NAME)
@Repository
public interface User_rolesDao {

    String TABLE_NAME = "USER_ROLES";
    String SYSTEM_ADMIN_SYS_ID = "EI";
    String SYSTEM_ADMIN_DEPT_ID = "ADMIN";
    String SYSTEM_ADMIN_ROLE_ID = "SYSADMIN";

    User_roles selectByKey(String user_id, String sys_id, String dept_id, String role_id);

    int insert(User_roles user_roles);

    int updateByKey(User_roles user_roles);

    int deleteByKey(User_roles user_roles);

    List<User_roles> selectDataByRoleId(String role_id);

}