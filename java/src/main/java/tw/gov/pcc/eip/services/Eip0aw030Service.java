package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.dao.View_cpape05mDao;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.domain.View_cpape05m;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自VIEW_CAPE05M 更新 USERS 資料 更新原則： 部門、EMAIL、名稱、員工編號、電話、分機、職稱代碼 若空值則帶入VIEW_CAPE05M的值 FROM_HR 強制上Y
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw030Service {

    private final UsersDao usersDao;
    private final User_rolesDao user_rolesDao;
    private final View_cpape05mDao view_cpape05mDao;

    public void updateAllUsersFromView_cpape05m() {
        List<Users> list = usersDao.selectAll();
        updateUsersFromView_cpape05m(list);
    }

    public void updateUsersFromView_cpape05m(List<Users> list) {
        AtomicInteger passCnt = new AtomicInteger();
        AtomicInteger updateCnt = new AtomicInteger();
        AtomicInteger deleteCnt = new AtomicInteger();
        AtomicInteger errCnt = new AtomicInteger();
        list.forEach(x -> {
            try {
                View_cpape05m itrUser = view_cpape05mDao.selectMaxPeupdateRecordByPecard(x.getUser_id());
                Optional.ofNullable(itrUser).ifPresentOrElse(r -> {
                    Users u = (Users) BeanUtility.cloneBean(x);
                    x.setOrg_id(r.getPeorg());
                    x.setDept_id(r.getPeunit());
                    x.setEmail(r.getEmail());
                    x.setUser_name(r.getPename());
                    x.setEmp_id(r.getPecard());
                    x.setTel1(r.getCt_tel());
                    x.setTel2(r.getCt_mobile());
                    x.setTitle_id(r.getPetit());

                    //20231206 將離職日期轉西元，若離職日期不是空值，且離職日期<系統日期，DELETE USERS
                    boolean isLeave = StringUtils.isNotBlank(r.getPelevdate()) &&
                            Integer.parseInt(DateUtility.changeDateTypeToWestDate(r.getPelevdate())) < Integer.parseInt(DateUtility.getNowWestDate());

                    assert u != null;
                    if (isLeave) {
                        //先判斷離職人員註銷
                        log.info("VIEW_CPAPE05M使用者{}於{}已離職，註銷使用者資料。", ObjectUtility.normalizeObject(x.getUser_id()), DateUtility.changeDateTypeToWestDate(r.getPelevdate()));
                        x.setAcnt_is_valid("N");
                        usersDao.updateByKey(x);
                        deleteCnt.getAndIncrement();
                    } else if (x.equals(u)) {
                        log.info("VIEW_CPAPE05M使用者{}資料無異動", ObjectUtility.normalizeObject(x.getUser_id()));
                        passCnt.getAndIncrement();
                    } else {
                        log.info("VIEW_CPAPE05M使用者{}資料異動", ObjectUtility.normalizeObject(x.getUser_id()));
                        x.setFrom_hr("Y");
                        x.setModify_timestamp(LocalDateTime.now());
                        x.setModify_user_id("SYS");
                        usersDao.updateByKey(x);
                        updateCnt.getAndIncrement();
                    }
                }, () -> {
                    log.debug("VIEW_CPAPE05M使用者{}不存在", ObjectUtility.normalizeObject(x.getUser_id()));
                    passCnt.incrementAndGet();
                });
            } catch (Exception e) {
                log.error("自VIEW_CAPE05M更新USERS {} 失敗 {} ", ObjectUtility.normalizeObject(x.getUser_id()), ExceptionUtility.getStackTrace(e));
                errCnt.getAndIncrement();
            }
        });
        log.info("USERS更新資料結束，更新{}筆，註銷{}筆，無更新{}筆，失敗{}筆。", updateCnt.get(), deleteCnt.get(), passCnt.get(), errCnt.get());
    }
}
