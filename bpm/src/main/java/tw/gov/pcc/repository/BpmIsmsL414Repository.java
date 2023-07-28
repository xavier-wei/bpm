package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmIsmsL414;

import java.util.List;

/**
 * Spring Data SQL repository for the BpmIsmsL414 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmIsmsL414Repository extends JpaRepository<BpmIsmsL414, String> {


    @Query(value = " select top 1 * from BPM_ISMS_L414 order by CREATE_TIME desc  ",nativeQuery = true)
    List<BpmIsmsL414> getMaxFormId();



}
