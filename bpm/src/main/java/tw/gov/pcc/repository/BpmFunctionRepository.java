package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmFunction;

import java.util.List;

/**
 * Spring Data SQL repository for the BpmFunction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmFunctionRepository extends JpaRepository<BpmFunction, Long> {
    List<BpmFunction> findByStatus(String status);
}
