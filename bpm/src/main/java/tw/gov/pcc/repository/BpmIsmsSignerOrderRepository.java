package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.BpmIsmsSignerOrder;
import tw.gov.pcc.domain.BpmIsmsSignerOrderPrimaryKey;

import java.util.List;

public interface BpmIsmsSignerOrderRepository extends JpaRepository<BpmIsmsSignerOrder, BpmIsmsSignerOrderPrimaryKey> {

    List<BpmIsmsSignerOrder> findByBpmIsmsFormOrderBySortAsc(String formName);
}
