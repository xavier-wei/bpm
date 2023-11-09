package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmSpecialSupervisor;

@Repository
public interface BpmSpecialSupervisorRepository extends JpaRepository<BpmSpecialSupervisor, String> {

}
