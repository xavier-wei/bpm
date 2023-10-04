package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUserId(String userId);

    List<User> findByUserIdIn(List<String> ids);

    List<User> findByAcntIsValid(String acntIsValid);
}
