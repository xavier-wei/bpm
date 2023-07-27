package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.EipBpmIsmsL414;

import java.util.List;
import java.util.Map;

/**
 * Spring Data SQL repository for the EipBpmIsmsL414 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EipBpmIsmsL414Repository extends JpaRepository<EipBpmIsmsL414, String> {


    @Query(value = " select top 1 * from EIP_BPM_ISMS_L414 order by CREATE_TIME desc  ",nativeQuery = true)
    List<EipBpmIsmsL414> getMaxFormId();



}
