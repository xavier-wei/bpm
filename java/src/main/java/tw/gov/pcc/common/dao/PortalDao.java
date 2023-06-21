package tw.gov.pcc.common.dao;

import java.util.HashMap;
import java.util.List;

import tw.gov.pcc.common.domain.KeycloakUser;

public interface PortalDao {


    /**
     * CAS 登入時, 取得使用者所能執行的系統項目代碼
     *
     * @param systemId 應用系統代號
     * @param userId   使用者代碼
     * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
     */
    List<String> selectCasUserItemList(String systemId, String userId, String deptId);

    /**
     * CAS 登入時, 取得使用者功能選單
     *
     * @param systemId 應用系統代號
     * @param userId   使用者代碼
     * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
     */
    List<HashMap<String, String>> selectCasUserMenu(String systemId, String userId, String deptId);

    /**
     * CAS 登入時, 取得使用者資料
     *
     * @param userId 使用者代碼
     * @return <code>CasUser</code> 物件
     */
    KeycloakUser selectCasUser(String userId);

}