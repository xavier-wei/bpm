package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;

/**
 * 網站導覽管理
 *
 * @author swho
 */
@Service
@Slf4j
@AllArgsConstructor
public class Eip00w210Service {
    public static final String SYS_LEVEL = "SYS_LEVEL";
    public static final String DISPLAY = "DISPLAY";
    public static final String DEFAULT_LEVEL = "2";
    private EipcodeDao eipcodeDao;
    private UserBean userBean;

    public String getLevel() {
        return eipcodeDao.findByCodeKindCodeNo(SYS_LEVEL, DISPLAY)
                .map(Eipcode::getCodename)
                .orElse(DEFAULT_LEVEL);
    }

    public void saveLevel(String level) {
        eipcodeDao.findByCodeKindCodeNo(SYS_LEVEL, DISPLAY)
                .ifPresent(eipcode -> {
                            eipcode.setCodename(level);
                            eipcode.setPrcdat(LocalDateTime.now());
                            eipcode.setStaff(userBean.getUserId());
                            eipcodeDao.updateByKey(eipcode);
                        }
                );
    }
}
