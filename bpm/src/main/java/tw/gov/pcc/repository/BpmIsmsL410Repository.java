package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmIsmsL410;

import java.util.List;
import java.util.Map;

/**
 * Spring Data SQL repository for the BpmIsmsL410 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmIsmsL410Repository extends JpaRepository<BpmIsmsL410, String> {

    @Query(value = " select top 1 * from BPM_ISMS_L410 order by CREATE_TIME desc  ",nativeQuery = true)
    List<BpmIsmsL410> getMaxFormId();
    @Query("SELECT l410 FROM BpmIsmsL410 l410 " +
        "WHERE (LENGTH(COALESCE(:word,'')) = 0 OR l410.formId = :word) " +
        "AND (LENGTH(COALESCE(:appEmpid,'')) = 0 OR l410.appEmpid = :appEmpid)" +
        "AND (LENGTH(COALESCE(:processInstanceStatus,'')) = 0 OR l410.processInstanceStatus = :processInstanceStatus)" +
        "ORDER BY l410.updateTime DESC ")
    List<BpmIsmsL410> findByWord(@Param("word") String word, @Param("appEmpid") String appEmpid, @Param("processInstanceStatus") String processInstanceStatus);

    @Query(value = "SELECT * FROM BPM_ISMS_L410 WHERE FORM_ID = :formId ", nativeQuery = true)
    List<Map<String,Object>> findByFormId(@Param("formId") String formId);


}
