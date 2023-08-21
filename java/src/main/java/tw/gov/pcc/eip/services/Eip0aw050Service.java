package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.View_cpape05mDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.View_cpape05m;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * VIEW_CPAPE05M TO EIPCODE(TITLE)
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw050Service {

    private final View_cpape05mDao view_cpape05mDao;
    private final EipcodeDao eipcodeDao;

    public void updateAllTitleFromView_cpape05m() {
        AtomicInteger passCnt = new AtomicInteger();
        AtomicInteger updateCnt = new AtomicInteger();
        AtomicInteger insertCnt = new AtomicInteger();
        AtomicInteger errorCnt = new AtomicInteger();
        view_cpape05mDao.selectAllPetitTitle().forEach(x -> {
            try {
                Optional<Eipcode> eipcodeOptional = eipcodeDao.findByCodeKindCodeNo("TITLE", x.getPetit());
                eipcodeOptional.ifPresentOrElse(y -> {
                    Eipcode org = (Eipcode) BeanUtility.cloneBean(y);
                    transView_cpape05mToTitle(y, x);
                    if (!y.equals(org)) {
                        y.setPrcdat(LocalDateTime.now());
                        y.setStaff("SYS");
                        log.info("EIPCODE(TITLE) {}資料異動", ObjectUtility.normalizeObject(y.getCodeno()));
                        eipcodeDao.updateByKey(y);
                        updateCnt.getAndIncrement();
                    } else {
                        passCnt.getAndIncrement();
                    }
                }, () -> {
                    Eipcode y = new Eipcode();
                    transView_cpape05mToTitle(y, x);
                    y.setPrcdat(LocalDateTime.now());
                    y.setStaff("SYS");
                    log.info("VIEW_CPAPE05M {}新增到EIPCODE(TITLE)", ObjectUtility.normalizeObject(y.getCodeno()));
                    eipcodeDao.insertEipcode(y);
                    insertCnt.getAndIncrement();
                });
            } catch (Exception e) {
                log.error("VIEW_CPAPE05M {}資料異動失敗 {}", ObjectUtility.normalizeObject(x.getPetit()), ExceptionUtility.getStackTrace(e));
                errorCnt.getAndIncrement();
            }
        });
        log.info("EIPCODE(TITLE)更新資料結束，寫入{}筆，更新{}筆，無更新{}筆，失敗{}筆", insertCnt.get(), updateCnt.get(), passCnt.get(), errorCnt.get());
    }

    private void transView_cpape05mToTitle(Eipcode y, View_cpape05m x) {
        y.setCodekind("TITLE");
        y.setCodeno(x.getPetit());
        y.setCodename(x.getTitle());
    }
}
