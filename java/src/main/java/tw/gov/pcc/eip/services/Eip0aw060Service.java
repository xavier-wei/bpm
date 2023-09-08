package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.CPAP_View_cpape05mDao;
import tw.gov.pcc.eip.dao.View_cpape05mDao;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * cape.dbo.view_cpape05m TO eip.dbo.view_cpape05m
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw060Service {

    private final View_cpape05mDao view_cpape05mDao;
    private final CPAP_View_cpape05mDao cpap_view_cpape05mDao;

    public void replaceAllView_cpape05m() {
        AtomicInteger insertCnt = new AtomicInteger();
        AtomicInteger errorCnt = new AtomicInteger();

        view_cpape05mDao.truncateAll();
        cpap_view_cpape05mDao.selectAll().forEach(x -> {
            try {
                view_cpape05mDao.insert(x);
                insertCnt.getAndIncrement();
                log.info("CPAP.DBO.VIEW_CPAPE05M {}新增到VIEW_CPAPE05M", ObjectUtility.normalizeObject(x));
            } catch (Exception e) {
                log.error("新增到VIEW_CPAPE05M {}資料新增失敗 {}", ObjectUtility.normalizeObject(x), ExceptionUtility.getStackTrace(e));
                errorCnt.getAndIncrement();
            }
        });
        log.info("新增到VIEW_CPAPE05M更新資料結束，寫入{}筆，失敗{}筆", insertCnt.get(), errorCnt.get());
    }
}
