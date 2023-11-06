package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.PositionDao;
import tw.gov.pcc.eip.dao.WEBITR_PositionDao;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * cape.dbo.position TO eip.dbo.position
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw070Service {

    private final PositionDao positionDao;
    private final WEBITR_PositionDao webitr_positionDao;

    public void replaceAllPosition() {
        AtomicInteger insertCnt = new AtomicInteger();
        AtomicInteger errorCnt = new AtomicInteger();

        positionDao.truncateAll();
        webitr_positionDao.selectAll().forEach(x -> {
            try {
                positionDao.insert(x);
                insertCnt.getAndIncrement();
                log.info("CPAP.DBO.POSITION {}新增到POSITION", ObjectUtility.normalizeObject(x));
            } catch (Exception e) {
                log.error("新增到POSITION {}資料新增失敗 {}", ObjectUtility.normalizeObject(x), ExceptionUtility.getStackTrace(e));
                errorCnt.getAndIncrement();
            }
        });
        log.info("新增到POSITION結束，寫入{}筆，失敗{}筆", insertCnt.get(), errorCnt.get());
    }
}
