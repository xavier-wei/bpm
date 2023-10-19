package tw.gov.pcc.eip.bpm.services;

import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.impl.EipcodeDaoImpl;

@Service
public class AESKeyService {

    private final EipcodeDaoImpl eipcodeDao;
    private final String BPM_AES = "BPM_AES";
    public AESKeyService(EipcodeDaoImpl eipcodeDao) {
        this.eipcodeDao = eipcodeDao;
    }

    public String getAESCode() {
        return eipcodeDao.findByCodeKind(BPM_AES).get(0).getCodename();
    }

}
