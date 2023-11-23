package tw.gov.pcc.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface BpmSupervisorRepository extends JpaRepository<BpmSupervisor, BpmSupervisorPrimayKey> {
    @NotNull Optional<BpmSupervisor> findById(@NotNull BpmSupervisorPrimayKey bpmSupervisorPrimayKey);

    @NotNull List<BpmSupervisor> findAll();

    @Query("SELECT bpmSupervisor FROM BpmSupervisor bpmSupervisor " +
        "WHERE (LENGTH(COALESCE(:title,'')) = 0 OR bpmSupervisor.appTitle LIKE :title) ")
    List<BpmSupervisor>  findAllByTitle(@Param("title") String title );

}
