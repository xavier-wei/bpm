package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.eip.domain.CursorDeptAcl;

import java.util.List;

@Repository
public interface PortalMenuAclDao {

    List<CursorDeptAcl> findDeptAcl(String sysId, String deptId);

	List<CursorDeptAcl> findRoleAcl(String sysId, String roleid, String deptid);
}