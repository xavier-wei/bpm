package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.Cpape05mOthers;

import java.util.Optional;

public interface Cpape05mOthersRepository extends JpaRepository<Cpape05mOthers,String> {
    Optional<Cpape05mOthers> findByPecard(String userId);

}
