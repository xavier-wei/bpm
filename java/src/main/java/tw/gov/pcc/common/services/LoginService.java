package tw.gov.pcc.common.services;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.dao.PortalDao;
import tw.gov.pcc.common.dao.PortalLogDao;
import tw.gov.pcc.common.domain.*;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import tw.gov.pcc.common.util.HttpUtil;
import tw.gov.pcc.common.util.StrUtil;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Users;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 檢核使用者 Portal 登入狀態並取得使用者相關資料及權限
 *
 * @author Goston
 */
@Service
public class LoginService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginService.class);
    private final PortalDao portalDao;
    private final PortalLogDao portalLogDao;
    private final SysfuncService sysfuncService;
    private final User_rolesDao user_rolesDao;
    private final UsersDao usersDao;
    private final DeptsDao deptsDao;

    public LoginService(PortalDao portalDao, PortalLogDao portalLogDao, SysfuncService sysfuncService, User_rolesDao userRolesDao, UsersDao usersDao, DeptsDao deptsDao) {
        this.portalDao = portalDao;
        this.portalLogDao = portalLogDao;
        this.sysfuncService = sysfuncService;
        user_rolesDao = userRolesDao;
        this.usersDao = usersDao;
        this.deptsDao = deptsDao;
    }

    /**
     * 檢核使用者 Portal 登入狀態, 並將傳入的使用者物件內容初始化
     *
     * @param userData 應用系統實作 <code>tw.gov.pcc.common.domain.UserInfo</code> 的使用者物件
     * @param request  HttpServletRequest
     * @return 登入成功回傳 <code>true</code>, 失敗回傳 <code>false</code>
     */
    public boolean getUserLoginData(UserInfo userData, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("檢核使用者 Portal 登入狀態及使用者物件初始化 開始...");
        }
        String userId; // 使用者代碼
        String userName; // 使用者名稱
        String deptId; // 部門代碼
        String deptName; //部門名稱
        String empId; // 員工編號
        String loginIP; // 使用者 IP
        String token; // 檢查資訊 Token
        String tel1;
        String tel2;
        String email;
        String titleId;
        String lineToken;
        String orgId;

        if (request.getAttribute(KeycloakSecurityContext.class.getName()) != null) {

            KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
            log.info("User Greeting for {}", keycloakSecurityContext.getToken()
                    .getPreferredUsername());

            userId = keycloakSecurityContext.getToken()
                    .getPreferredUsername();
        } else {
            userId = StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(userId)) return false;
        User user = portalDao.selectUser(userId);
        if (user == null) return false;
        userName = StringUtils.defaultString(user.getUserName()); // 使用者名稱
        deptId = StringUtils.defaultString(user.getDeptId()); // 部門代碼
        deptName = Optional.ofNullable(deptsDao.findByPk(deptId)).map(Depts::getDept_name).orElse(StringUtils.EMPTY);
        empId = StringUtils.defaultString(user.getEmpId()); // 員工編號
        loginIP = StringUtils.defaultString(HttpUtil.getClientIP(request)); // 使用者 IP
        token = userId + DateUtil.getNowWestDateTime(true); // 檢查資訊 Token
        tel1 = StringUtils.defaultString(user.getTel1());
        tel2 = StringUtils.defaultString(user.getTel2());
        email = StringUtils.defaultString(user.getEmail());
        titleId = StringUtils.defaultString(user.getTitleId());
        lineToken = StringUtils.defaultString(user.getLineToken());
        orgId = StringUtils.defaultString(user.getOrgId());

        // 沒有 使用者代碼 及 檢查資訊 Token 一切免談
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(token)) return false;
        if (log.isDebugEnabled()) {
            log.debug("取得使用者資訊: PERSONID = {} / UserName = {} / DEPTID = {} / EmpId = {} / USERIP = {} / TKN = {}", userId, userName, deptId, empId, loginIP, token);
        }
        // 取得 登入日期 及 登入時間
        String loginDateTime = DateUtil.getNowChineseDateTime(false);
        // 取得使用者功能選單資料
        List<HashMap<String, String>> menuMapList = portalDao.selectUserMenu(EnvFacadeHelper.getSystemId(), userId, deptId);
        Optional.ofNullable(user_rolesDao.selectByKey(userId, EnvFacadeHelper.getSystemId(), User_rolesDao.SYSTEM_ADMIN_DEPT_ID, User_rolesDao.SYSTEM_ADMIN_ROLE_ID))
                .ifPresent(x -> {
                    log.info("系統管理者登入：{}", userId);
                });

        // 設定 Framework 使用者資料
        FrameworkUserInfoBean frameworkUserInfoBean = new FrameworkUserInfoBean();
        frameworkUserInfoBean.setUserId(userId);
        frameworkUserInfoBean.setUserName(userName);
        frameworkUserInfoBean.setDeptId(deptId);
        frameworkUserInfoBean.setDeptName(deptName);
        frameworkUserInfoBean.setEmpId(empId);
        frameworkUserInfoBean.setLoginIP(loginIP);
        frameworkUserInfoBean.setEmail(email);
        frameworkUserInfoBean.setTel1(tel1);
        frameworkUserInfoBean.setTel2(tel2);
        frameworkUserInfoBean.setLineToken(lineToken);
        frameworkUserInfoBean.setTitleId(titleId);
        frameworkUserInfoBean.setOrgId(orgId);
        frameworkUserInfoBean.setToken(token);
        frameworkUserInfoBean.setLoginDate(DateUtil.splitChineseDateTime(loginDateTime, true));
        frameworkUserInfoBean.setLoginTime(DateUtil.splitChineseDateTime(loginDateTime, false));
        frameworkUserInfoBean.setMenu(generateUserMenu(menuMapList));
        frameworkUserInfoBean.setSystemFunctionMap(sysfuncService.buildSystemFunctionMap(menuMapList));
        // 設定應用系統使用者資料
        userData.setUserId(userId);
        userData.setUserName(userName);
        userData.setDeptId(deptId);
        userData.setDeptName(deptName);
        userData.setEmpId(empId);
        userData.setLoginIP(loginIP);
        userData.setEmail(email);
        userData.setTel1(tel1);
        userData.setTel2(tel2);
        userData.setLineToken(lineToken);
        userData.setTitleId(titleId);
        userData.setOrgId(orgId);
        userData.setLoginDate(DateUtil.splitChineseDateTime(loginDateTime, true));
        userData.setLoginTime(DateUtil.splitChineseDateTime(loginDateTime, false));
        if (log.isDebugEnabled()) {
            log.debug("設定使用者資料完成, 使用者代碼: {}", StrUtil.safeLog(userId));
        }

        Users users = usersDao.selectByKey(userId);
        users.setLast_login_ip(frameworkUserInfoBean.getLoginIP());
        users.setLast_login_date(LocalDateTime.now());
        usersDao.updateByKey(users);

        // 取得使用者可執行之所有系統項目代碼 (ITEMS.ITEM_ID)
        List<String> itemIdList = portalDao.selectUserItemList(EnvFacadeHelper.getSystemId(), userId, deptId);
        frameworkUserInfoBean.setItemIdList(itemIdList);

        if (log.isDebugEnabled()) {
            log.debug("設定使用者可執行之系統項目代碼資料完成, 使用者代碼: {}", StrUtil.safeLog(userId));
        }
        // 將使用者資料存入 Session (不可拿掉，若有開啟 Log 機制的話下方的 Portal Log 會用到)
        UserSessionHelper.setFrameworkUserData(request, frameworkUserInfoBean);
        // 將應用系統使用者物件存入 Session
        UserSessionHelper.setUserData(request, userData);
        if (log.isDebugEnabled()) {
            log.debug("檢核使用者 Portal 登入狀態及使用者物件初始化 完成...");
        }
        // Portal Log - Login Log
        // [
        if (EnvFacadeHelper.isNeedLogging(request)) {
            try {
                String path = request.getServletPath();
                Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
                String now = DateUtil.parseTimestampToWestDateTime(date, true);
                PortalLog logData = new PortalLog();
                logData.setLogDateTime(now); // 紀錄時間
                logData.setUserId(frameworkUserInfoBean.getUserId()); // 用戶代號
                logData.setUserIP(frameworkUserInfoBean.getLoginIP()); // 用戶 IP 位址
                logData.setUserAction("Login"); // 用戶執行動作
                logData.setApCode(EnvFacadeHelper.getSystemId()); // 應用系統代號
                logData.setApName(EnvFacadeHelper.getSystemName()); // 應用系統名稱
                logData.setApFunctionCode(EnvFacadeHelper.getItemIdByServletPath(frameworkUserInfoBean, path)); // 應用系統功能代號
                logData.setApFunctionName(EnvFacadeHelper.getItemNameByServletPath(frameworkUserInfoBean, path)); // 應用系統功能名稱
                logData.setApUrl(request.getServletPath()); // 應用系統網址
                logData.setLogDescript("使用者登入系統"); // 說明
                logData.setDateTime(date); // 系統時間
                logData.setToken(frameworkUserInfoBean.getToken()); // 檢查資訊
                String sno = portalLogDao.insertData(logData);
                frameworkUserInfoBean.setSno(sno);
                frameworkUserInfoBean.setApFunctionCode(EnvFacadeHelper.getItemIdByServletPath(frameworkUserInfoBean, request.getServletPath()));
                frameworkUserInfoBean.setApFunctionName(EnvFacadeHelper.getItemNameByServletPath(frameworkUserInfoBean, request.getServletPath()));
            } catch (Exception e) {
                FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.PORTALLOG, e);
            }
        }
        // ]
        // 表示使用者沒有執行本系統任何功能的權限
        return frameworkUserInfoBean.getItemIdList() != null && frameworkUserInfoBean.getItemIdList()
                .size() != 0;
    }

    /**
     * 產生使用者功能選單物件
     *
     * @param menuMapList 從資料庫撈出來的使用者選單授權資料
     * @return menu
     */
    private Menu generateUserMenu(List<HashMap<String, String>> menuMapList) {
        Menu menu = new Menu();
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        HashMap<String, MenuItem> map = new HashMap<String, MenuItem>();
        String previousLevel2Id = null;

        for (HashMap<String, String> item : menuMapList) {
            String itemId = item.get("itemId");
            String itemIdP = item.get("itemIdP");
            String levelNo = item.get("levelNo");
            MenuItem menuItem = new MenuItem(levelNo, item.get("itemName"), StringUtils.defaultIfBlank(item.get("url"), "#"));
            if (StringUtils.equals(levelNo, "2")) {
                if (previousLevel2Id == null) previousLevel2Id = itemId;
                if (!map.isEmpty()) {
                    menuItemList.add(map.get(previousLevel2Id));
                    map = new HashMap<String, MenuItem>();
                    previousLevel2Id = itemId;
                }
                map.put(item.get("itemId"), menuItem);
            } else {
                if (map.get(itemIdP) != null) map.get(itemIdP)
                        .addSub(menuItem);
                map.put(itemId, menuItem);
            }
        }
        if (!map.isEmpty()) {
            menuItemList.add(map.get(previousLevel2Id));
        }
        // 加上系統登出項目
        menuItemList.add(new MenuItem("2", "登出系統", "/Logout.action"));
        menu.setMenuItemList(menuItemList);
        return menu;
    }

}
