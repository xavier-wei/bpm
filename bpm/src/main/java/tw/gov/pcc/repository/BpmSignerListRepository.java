package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.BpmSignerList;
import tw.gov.pcc.domain.BpmSignerListPrimaryKey;

public interface BpmSignerListRepository extends JpaRepository<BpmSignerList, BpmSignerListPrimaryKey> {
}
