package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmAbnormalSupervisor;

@Repository
public interface BpmAbnormalSupervisorRepository extends JpaRepository<BpmAbnormalSupervisor, String> {

}
