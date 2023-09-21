package tw.gov.pcc.eip.services;

import tw.gov.pcc.eip.dao.SysmsgDao;
import tw.gov.pcc.eip.domain.Sysmsg;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for 系統訊息處理
 *
 * @author Goston
 */
@Service
public class MessageResourceService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MessageResourceService.class);
    private final SysmsgDao sysmsgDao;

    public MessageResourceService(SysmsgDao sysmsgDao) {
        this.sysmsgDao = sysmsgDao;
    }

    /**
     * 取得 系統訊息檔 的所有內容
     *
     * @return
     */
    public List<Sysmsg> selectAllData() {
        return sysmsgDao.selectAllData();
    }
}
