package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.View_oup_unitDao;
import tw.gov.pcc.eip.dao.WEBITR_View_oup_unitDao;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * WEBITR.dbo.view_oup_unit TO eip.dbo.view_oup_unit
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw080Service {

    private final View_oup_unitDao view_oup_unitDao;
    private final WEBITR_View_oup_unitDao webitr_view_oup_unitDao;

    public void replaceAllView_oup_unit() {
        AtomicInteger insertCnt = new AtomicInteger();
        AtomicInteger errorCnt = new AtomicInteger();

        view_oup_unitDao.truncateAll();
        webitr_view_oup_unitDao.selectAll().forEach(x -> {
            try {
                view_oup_unitDao.insert(x);
                insertCnt.getAndIncrement();
                log.info("WEBITR.DBO.VIEW_OUP_UNIT {}新增到VIEW_OUP_UNIT", ObjectUtility.normalizeObject(x));
            } catch (Exception e) {
                log.error("新增到VIEW_OUP_UNIT {}資料新增失敗 {}", ObjectUtility.normalizeObject(x), ExceptionUtility.getStackTrace(e));
                errorCnt.getAndIncrement();
            }
        });
        log.info("新增到VIEW_OUP_UNIT結束，寫入{}筆，失敗{}筆", insertCnt.get(), errorCnt.get());
    }
}
