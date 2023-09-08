package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 清空並同步 cpap.dbo.view_cpape05m -> eip.dbo.view_cpape05m
 * 清空並同步 webitr.dbo.position -> eip.dbo.position
 * 轉換 ldap 資料 並新增到 eip.dbo.users
 * 同步 使用者資料
 * 同步 部門資料
 * 同步 職稱資料
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw010Service {

    private final Eip0aw020Service eip0aw020Service;
    private final Eip0aw030Service eip0aw030Service;
    private final Eip0aw040Service eip0aw040Service;
    private final Eip0aw050Service eip0aw050Service;
    private final Eip0aw060Service eip0aw060Service;
    private final Eip0aw070Service eip0aw070Service;

    public void syncFromLdapAndItr() {
        eip0aw060Service.replaceAllView_cpape05m();
        eip0aw070Service.replaceAllPosition();
        eip0aw020Service.insertUsersFromLdap();
        eip0aw030Service.updateAllUsersFromView_cpape05m();
        eip0aw040Service.updateAllDeptsFromView_out_unit();
        eip0aw050Service.updateAllTitleFromView_cpape05m();
    }
}
