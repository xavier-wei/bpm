package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.entity.BpmSignStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface BpmSignStatusRepository extends JpaRepository<BpmSignStatus, UUID> {

    List<BpmSignStatus> findByProcessInstanceId(String processInstanceId);
    List<BpmSignStatus> findByFormIdOrderBySigningDatetime(String formId);

}
