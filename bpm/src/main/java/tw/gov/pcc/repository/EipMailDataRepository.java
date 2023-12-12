package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.domain.EipMailData;

public interface EipMailDataRepository extends JpaRepository<EipMailData, String> {
}
