package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Roles;

/**
 * 單位角色資料 Dao
 */
@DaoTable(RolesDao.TABLE_NAME)
@Repository
public interface RolesDao {

    String TABLE_NAME = "ROLES";

    Roles selectByKey(String sys_id, String dept_id, String role_id);

	List<Roles> selectDataList(String role_id);
	
	public int insert(Roles roles);

	int deleteByKey(Roles roles);

}