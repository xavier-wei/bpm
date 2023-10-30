package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUserId(String userId);

    Optional<List<User>> findByUserIdIn(List<String> ids);

    List<User> findByAcntIsValid(String acntIsValid);
}
