package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.eip.domain.CursorAcl;

import java.util.List;

@Repository
public interface PortalMenuAclDao {

    List<CursorAcl> findAllAcl(String sysId);

	List<CursorAcl> findRoleAcl(String sysId, String roleid);
}