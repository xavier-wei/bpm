package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.WEBITR_View_oup_unitDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.View_oup_unit;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * VIEW_OUP_UNIT TO DEPTS
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw040Service {

    private final DeptsDao deptsDao;
    private final WEBITR_View_oup_unitDao WEBITRView_oup_unitDao;

    public void updateAllDeptsFromView_oup_unit() {
        AtomicInteger passCnt = new AtomicInteger();
        AtomicInteger updateCnt = new AtomicInteger();
        AtomicInteger insertCnt = new AtomicInteger();
        AtomicInteger errorCnt = new AtomicInteger();
        WEBITRView_oup_unitDao.selectAll().forEach(x -> {
            try {
                Depts dept = deptsDao.findByPk(x.getUnit_id());
                Optional.ofNullable(dept).ifPresentOrElse(y -> {
                    Depts org = (Depts) BeanUtility.cloneBean(y);
                    transView_oup_unitToDepts(y, x);
                    if (!y.equals(org)) {
                        y.setModify_timestamp(LocalDateTime.now());
                        y.setModify_user_id("SYS");
                        log.info("VIEW_OUP_UNIT{}資料異動", ObjectUtility.normalizeObject(x.getUnit_id()));
                        deptsDao.updateByKey(y);
                        updateCnt.getAndIncrement();
                    } else {
                        passCnt.getAndIncrement();
                    }
                }, () -> {
                    Depts y = new Depts();
                    transView_oup_unitToDepts(y, x);
                    y.setCreate_timestamp(LocalDateTime.now());
                    y.setCreate_user_id("SYS");
                    log.info("VIEW_OUP_UNIT{}資料新增至DEPTS", ObjectUtility.normalizeObject(x.getUnit_id()));
                    deptsDao.insert(y);
                    insertCnt.getAndIncrement();
                });
            } catch (Exception e) {
                log.error("VIEW_OUP_UNIT{}資料異動失敗 {}", ObjectUtility.normalizeObject(x.getUnit_id()), ExceptionUtility.getStackTrace(e));
                errorCnt.getAndIncrement();
            }
        });
        log.info("DEPTS更新資料結束，寫入{}筆，更新{}筆，無更新{}筆，失敗{}筆", insertCnt.get(), updateCnt.get(), passCnt.get(), errorCnt.get());
    }

    private void transView_oup_unitToDepts(Depts y, View_oup_unit x) {
        y.setDept_id(x.getUnit_id());
        y.setFrom_hr("Y");
        y.setDept_id_p(x.getFunit_id());
        y.setDept_name(x.getName());
        y.setIs_valid("Y");
        y.setSort_order(Optional.ofNullable(x.getSeq()).map(Integer::valueOf).orElse(null));
    }
}
