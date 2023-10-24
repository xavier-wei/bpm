package tw.gov.pcc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.utils.ParameterUtil;

@Component
public class InitialConfig implements CommandLineRunner {
    private final EipcodeDao eipcodeDao;



    public InitialConfig(EipcodeDao eipcodeDao) {
        this.eipcodeDao = eipcodeDao;
    }

    @Override
    public void run(String... args) throws Exception {
        String bpmToken = "BPM_TOKEN";
        String bpmAes = "BPM_AES";
        String bpmCipher = "BPM_CIPHER";
        ParameterUtil.setToken(eipcodeDao.findByCodeKindOrderByScodeno(bpmToken).get(0).getCodename());
        ParameterUtil.setAesKey(eipcodeDao.findByCodeKindOrderByScodeno(bpmAes).get(0).getCodename());
        ParameterUtil.setBpmCipher(eipcodeDao.findByCodeKindOrderByScodeno(bpmCipher).get(0).getCodename());
    }
}
