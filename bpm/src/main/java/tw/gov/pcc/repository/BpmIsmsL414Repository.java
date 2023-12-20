package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.repository.custom.BpmIsmsL414RepositoryCustom;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Spring Data SQL repository for the BpmIsmsL414 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmIsmsL414Repository extends JpaRepository<BpmIsmsL414, String>, BpmIsmsL414RepositoryCustom, JpaSpecificationExecutor<BpmIsmsL414> {


    @Query(value = " select top 1 * from BPM_ISMS_L414 order by CREATE_TIME desc  ", nativeQuery = true)
    List<BpmIsmsL414> getMaxFormId();

    Optional<BpmIsmsL414> findFirstByProcessInstanceId(String processInstanceId);

    @Query("SELECT l414 FROM BpmIsmsL414 l414 " +
            "WHERE (LENGTH(COALESCE(:word,'')) = 0 OR l414.formId LIKE CONCAT('%', :word, '%')) " +
            "AND (LENGTH(COALESCE(:appEmpid,'')) = 0 OR l414.appEmpid = :appEmpid)" +
            "AND (LENGTH(COALESCE(:processInstanceStatus,'')) = 0 OR l414.processInstanceStatus = :processInstanceStatus)" +
            "ORDER BY l414.updateTime DESC")
    List<BpmIsmsL414> findByWord(@Param("word") String word, @Param("appEmpid") String appEmpid, @Param("processInstanceStatus") String processInstanceStatus);

    @Query(value = "SELECT * FROM BPM_ISMS_L414 WHERE FORM_ID = :formId ", nativeQuery = true)
    List<Map<String,Object>> findByFormId(@Param("formId") String formId);


}
