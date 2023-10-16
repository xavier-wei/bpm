package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.Cpape05m;

import java.util.Optional;

public interface Cpape05mRepository extends JpaRepository<Cpape05m,String> {
    Optional<Cpape05m> findByPecard(String userId);

}
