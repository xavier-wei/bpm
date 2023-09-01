package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;

import java.util.List;

@Repository
public interface BpmIsmsAdditionalRepository extends JpaRepository<BpmIsmsAdditional,String> {

    @Query(value = " select top 1 * from BPM_ISMS_ADDITIONAL order by CREATE_TIME desc  ",nativeQuery = true)
    List<BpmIsmsAdditional> getMaxFormId();

}
