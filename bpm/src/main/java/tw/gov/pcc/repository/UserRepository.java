package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUserId(String userId);

}
