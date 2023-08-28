package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmL410ApplyManage;

/**
 * Spring Data SQL repository for the BpmL410ApplyManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmL410ApplyManageRepository extends JpaRepository<BpmL410ApplyManage, String> {}
