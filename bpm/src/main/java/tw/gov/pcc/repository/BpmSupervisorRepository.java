package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;

@Repository
public interface BpmSupervisorRepository extends JpaRepository<BpmSupervisor, BpmSupervisorPrimayKey> {

}