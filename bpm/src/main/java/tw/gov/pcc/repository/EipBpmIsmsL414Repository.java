package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.EipBpmIsmsL414;

/**
 * Spring Data SQL repository for the EipBpmIsmsL414 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EipBpmIsmsL414Repository extends JpaRepository<EipBpmIsmsL414, String> {}
