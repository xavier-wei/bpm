package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.entity.Users;

public interface UsersRspoitory extends JpaRepository<Users, String> {
    Users findFirstByUserId(String userId);

}
