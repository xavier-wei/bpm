package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.domain.BpmSupervisor;

import java.util.List;

@Repository
public interface BpmSpecialSupervisorRepository extends JpaRepository<BpmSpecialSupervisor, String> {

    @Query("SELECT bpmSpecialSupervisor FROM BpmSpecialSupervisor bpmSpecialSupervisor " +
        "WHERE (LENGTH(COALESCE(:pecard,'')) = 0 OR bpmSpecialSupervisor.pecard LIKE :pecard) ")
    List<BpmSpecialSupervisor>  findAllByPecard(@Param("pecard") String pecard );

}
