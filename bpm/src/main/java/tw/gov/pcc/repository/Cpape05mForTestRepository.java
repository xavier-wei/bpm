package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.Cpape05mForTest;

import java.util.Optional;

public interface Cpape05mForTestRepository extends JpaRepository<Cpape05mForTest,String> {
    Optional<Cpape05mForTest> findByPecard(String userId);

}
