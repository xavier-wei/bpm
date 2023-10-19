package tw.gov.pcc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.utils.ParameterUtil;

@Component
public class IntialConfig implements CommandLineRunner {
    private final EipcodeDao eipcodeDao;

    private final String BPM_TOKEN = "BPM_TOKEN";

    public final String BPM_AES = "BPM_AES";

    public IntialConfig(EipcodeDao eipcodeDao) {
        this.eipcodeDao = eipcodeDao;
    }

    @Override
    public void run(String... args) throws Exception {
        ParameterUtil.TOKEN=eipcodeDao.findByCodeKindOrderByScodeno(BPM_TOKEN).get(0).getCodename();
        ParameterUtil.AESKey = eipcodeDao.findByCodeKindOrderByScodeno(BPM_AES).get(0).getCodename();
        System.out.println("ParameterUtil.TOKEN = " + ParameterUtil.TOKEN);
        System.out.println("ParameterUtil.AESKey = " + ParameterUtil.AESKey);
    }
}
