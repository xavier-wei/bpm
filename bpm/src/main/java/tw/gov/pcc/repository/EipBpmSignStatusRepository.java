package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.entity.EipBpmSignStatus;
import tw.gov.pcc.domain.entity.EipBpmSignStatusPrimaryKey;

@Repository
public interface EipBpmSignStatusRepository extends JpaRepository<EipBpmSignStatus, EipBpmSignStatusPrimaryKey> {

}
