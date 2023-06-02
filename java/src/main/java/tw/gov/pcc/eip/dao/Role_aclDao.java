package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Role_acl;

import java.util.List;

/**
 * 單位角色權限資料 Dao
 */
@DaoTable(Role_aclDao.TABLE_NAME)
@Repository
public interface Role_aclDao {

    String TABLE_NAME = "ROLE_ACL";

    Role_acl selectByKey(String sys_id, String role_id, String dept_id, String item_id);

    int insert(Role_acl role_acl);

    int updateByKey(Role_acl role_acl);

    int deleteByKey(Role_acl role_acl);

    List<Role_acl> findByItemAndExcludeAdmin(String itemId);

    List<Role_acl> findByItem(String itemId);
}