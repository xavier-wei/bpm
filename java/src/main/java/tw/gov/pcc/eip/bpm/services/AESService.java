package tw.gov.pcc.eip.bpm.services;

import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.impl.EipcodeDaoImpl;

@Service
public class AESService {

    private final EipcodeDaoImpl eipcodeDao;

    public AESService(EipcodeDaoImpl eipcodeDao) {
        this.eipcodeDao = eipcodeDao;
    }

    public String getAESCode() {
        String bpmAes = "BPM_AES";
        return eipcodeDao.findByCodeKind(bpmAes).get(0).getCodename();
    }

    public String getBpmCipher() {
        String bpmCipher = "BPM_CIPHER";
        return eipcodeDao.findByCodeKind(bpmCipher).get(0).getCodename();
    }
}
