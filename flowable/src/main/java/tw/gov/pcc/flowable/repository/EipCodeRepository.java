package tw.gov.pcc.flowable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.gov.pcc.flowable.domain.EipCode;
import tw.gov.pcc.flowable.domain.EipCodePrimaryKey;

public interface EipCodeRepository extends JpaRepository<EipCode, EipCodePrimaryKey> {
    EipCode findFirstByCodekind(String codekind);


}
