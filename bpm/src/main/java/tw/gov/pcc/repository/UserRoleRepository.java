package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.UserRole;
import tw.gov.pcc.domain.UserRolesPrimaryKey;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolesPrimaryKey> {

    List<UserRole> findByRoleIdIn(List<String> roleIds);


}
