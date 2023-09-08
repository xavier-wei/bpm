package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.repository.custom.BpmIsmsAdditionalRepositoryCustom;

import java.util.List;
import java.util.Map;

@Repository
public interface BpmIsmsAdditionalRepository extends JpaRepository<BpmIsmsAdditional,String>, BpmIsmsAdditionalRepositoryCustom {

    @Query(value = " select top 1 * from BPM_ISMS_ADDITIONAL order by CREATE_TIME desc  ",nativeQuery = true)
    List<BpmIsmsAdditional> getMaxFormId();

    BpmIsmsAdditional findFirstByProcessInstanceId(String processInstanceId);
    BpmIsmsAdditional findByProcessInstanceId(String processInstanceId);

    @Query(value = " select * " +
        "from (select l414.FORM_ID, " +
        "             l414.PROCESS_INSTANCE_ID, " +
        "             l414.APPLY_DATE, " +
        "             l414.APP_NAME, " +
        "             l414.FIL_NAME " +
        "      from BPM_ISMS_L414 l414 " +
        "      union " +
        "      select l410.form_id, " +
        "             l410.PROCESS_INSTANCE_ID, " +
        "             l410.APPLY_DATE, " +
        "             l410.APP_NAME, " +
        "             l410.FIL_NAME " +
        "      from BPM_ISMS_L410 l410) allData " +
        "where allData.PROCESS_INSTANCE_ID = :processInstanceId ", nativeQuery = true)
    List<Map<String,Object>> findAllByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

}
