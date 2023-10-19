package tw.gov.pcc.flowable.service;

import org.springframework.stereotype.Component;
import tw.gov.pcc.flowable.repository.EipCodeRepository;

@Component
public class EipCodeService {

    private final EipCodeRepository eipCodeRepository;

    public EipCodeService(EipCodeRepository eipCodeRepository) {
        this.eipCodeRepository = eipCodeRepository;
    }


    public String findCodeName(String codekind) {
        return eipCodeRepository.findFirstByCodekind(codekind).getCodename();
    }
}
