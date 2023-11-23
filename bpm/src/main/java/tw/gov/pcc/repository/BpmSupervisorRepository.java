package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;

import java.util.List;

@Repository
public interface BpmSupervisorRepository extends JpaRepository<BpmSupervisor, BpmSupervisorPrimayKey> {

    @Query("SELECT bpmSupervisor FROM BpmSupervisor bpmSupervisor " +
        "WHERE (LENGTH(COALESCE(:title,'')) = 0 OR bpmSupervisor.appTitle LIKE :title) ")
    List<BpmSupervisor>  findAllByTitle(@Param("title") String title );

}
