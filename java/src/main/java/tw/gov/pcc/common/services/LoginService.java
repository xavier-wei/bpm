package tw.gov.pcc.common.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.common.dao.PortalDao;
import tw.gov.pcc.common.dao.PortalLogDao;
import tw.gov.pcc.common.dao.SysfuncDao;
import tw.gov.pcc.common.domain.KeycloakUser;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.domain.Menu;
import tw.gov.pcc.common.domain.MenuItem;
import tw.gov.pcc.common.domain.PortalLog;
import tw.gov.pcc.common.domain.UserInfo;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.util.*;

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
    private final SysfuncDao sysfuncDao;
    private final SysfuncService sysfuncService;

    public LoginService(PortalDao portalDao, PortalLogDao portalLogDao, SysfuncDao sysfuncDao, SysfuncService sysfuncService) {
        this.portalDao = portalDao;
        this.portalLogDao = portalLogDao;
        this.sysfuncDao = sysfuncDao;
        this.sysfuncService = sysfuncService;
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
        HttpSession session = request.getSession();
        String userId = session.getAttribute(KeycloakSecurityContext.class.getName()).toString(); // 使用者代碼
        String userName = ""; // 使用者名稱
        String deptId = ""; // 部門代碼
        String empNo = ""; // 員工編號
        String loginIP = ""; // 使用者 IP
        String token = ""; // 檢查資訊 Token

        if (request.getAttribute(KeycloakSecurityContext.class.getName()) != null) {

            KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
            log.info("User Greeting for {}", keycloakSecurityContext.getToken()
                    .getPreferredUsername());

            userId = keycloakSecurityContext.getToken()
                    .getPreferredUsername();
        }
        if (StringUtils.isBlank(userId)) return false;
        KeycloakUser keyCloakUser = portalDao.selectCasUser(userId);
        if (keyCloakUser == null) return false;
        userName = StringUtils.defaultString(keyCloakUser.getUserName()); // 使用者名稱
        deptId = StringUtils.defaultString(keyCloakUser.getDeptId()); // 部門代碼
        empNo = StringUtils.defaultString(keyCloakUser.getEmpId()); // 員工編號
        loginIP = StringUtils.defaultString(HttpUtil.getClientIP(request)); // 使用者 IP
        token = userId + DateUtil.getNowWestDateTime(true); // 檢查資訊 Token

        // 沒有 使用者代碼 及 檢查資訊 Token 一切免談
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(token)) return false;
        if (log.isDebugEnabled()) {
            log.debug("取得使用者資訊: PERSONID = {} / UserName = {} / DEPTID = {} / EmpNO = {} / USERIP = {} / TKN = {}", userId, userName, deptId, empNo, loginIP, token);
        }
        // 取得 登入日期 及 登入時間
        String loginDateTime = DateUtil.getNowChineseDateTime(false);
        // 取得使用者功能選單資料
        List<HashMap<String, String>> menuMapList = portalDao.selectCasUserMenu(EnvFacadeHelper.getSystemId(), userId, deptId);
        // 設定 Framework 使用者資料
        FrameworkUserInfoBean frameworkUserInfoBean = new FrameworkUserInfoBean();
        frameworkUserInfoBean.setUserId(userId);
        frameworkUserInfoBean.setUserName(userName);
        frameworkUserInfoBean.setDeptId(deptId);
        frameworkUserInfoBean.setEmpNo(empNo);
        frameworkUserInfoBean.setLoginIP(loginIP);
        frameworkUserInfoBean.setToken(token);
        frameworkUserInfoBean.setLoginDate(DateUtil.splitChineseDateTime(loginDateTime, true));
        frameworkUserInfoBean.setLoginTime(DateUtil.splitChineseDateTime(loginDateTime, false));
        frameworkUserInfoBean.setMenu(generateUserMenu(menuMapList));
        frameworkUserInfoBean.setHeaderFileName(getHeaderFileName());
        frameworkUserInfoBean.setSystemFunctionMap(sysfuncService.buildSystemFunctionMap(menuMapList));
        // 設定應用系統使用者資料
        userData.setUserId(userId);
        userData.setUserName(userName);
        userData.setDeptId(deptId);
        userData.setEmpNo(empNo);
        userData.setLoginIP(loginIP);
        userData.setLoginDate(DateUtil.splitChineseDateTime(loginDateTime, true));
        userData.setLoginTime(DateUtil.splitChineseDateTime(loginDateTime, false));
        if (log.isDebugEnabled()) {
            log.debug("設定使用者資料完成, 使用者代碼: {}", StrUtil.safeLog(userId));
        }

        // 取得使用者可執行之所有系統項目代碼 (ITEMS.ITEM_ID)
        List<String> itemIdList = portalDao.selectCasUserItemList(EnvFacadeHelper.getSystemId(), userId, deptId);
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
     * @return
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

    /**
     * 取得要使用的系統 Header 圖檔
     *
     * @return
     */
    private String getHeaderFileName() {
        String fileName = "title.gif";
        try {
            String now = DateUtility.getNowWestDate();
            int year = Integer.parseInt(StringUtils.substring(now, 0, 4));
            int month = Integer.parseInt(StringUtils.substring(now, 4, 6));
            int day = Integer.parseInt(StringUtils.substring(now, 6, 8));
            // 春節
            String date = Calendar.sCalendarLundarToSolar(year, 1, 1);
            if (Integer.parseInt(now) >= Integer.parseInt(DateUtility.calDay(date, -14)) && Integer.parseInt(now) <= Integer.parseInt(DateUtility.calDay(date, 7)))
                fileName = "title0101.gif";
            // 端午
            date = Calendar.sCalendarLundarToSolar(year, 5, 5);
            if (Integer.parseInt(now) >= Integer.parseInt(DateUtility.calDay(date, -14)) && Integer.parseInt(now) <= Integer.parseInt(DateUtility.calDay(date, 7)))
                fileName = "title0505.gif";
            // 中秋
            date = Calendar.sCalendarLundarToSolar(year, 8, 15);
            if (Integer.parseInt(now) >= Integer.parseInt(DateUtility.calDay(date, -14)) && Integer.parseInt(now) <= Integer.parseInt(DateUtility.calDay(date, 7)))
                fileName = "title0815.gif";
            // 聖誕
            date = year + "1225";
            if (Integer.parseInt(now) >= Integer.parseInt(DateUtility.calDay(date, -14)) && Integer.parseInt(now) <= Integer.parseInt(DateUtility.calDay(date, 7)))
                fileName = "title1225.gif";
        } catch (Exception e) {
            return fileName;
        }
        return fileName;
    }
}

class Calendar {
    // Array lIntLunarDay is stored in the monthly day information in every year from 1901 to 2100 of the lunar calendar,
    // The lunar calendar can only be 29 or 30 days every month, express with 12(or 13) pieces of binary bit in one year,
    // it is 30 days for 1 form in the corresponding location , otherwise it is 29 days
    private static final int[] iLunarMonthDaysTable = {19168, 42352, 21096, 53856, 55632, 27304, 22176, 39632, 19176, 19168,  // 1910
            42200, 42192, 53840, 54600, 46416, 22176, 38608, 38320, 18872, 18864,  // 1920
            42160, 45656, 27216, 27968, 44456, 11104, 38256, 18808, 18800, 25776,  // 1930
            54432, 59984, 27976, 23248, 11104, 37744, 37600, 51560, 51536, 54432,  // 1940
            55888, 46416, 22176, 43736, 9680, 37584, 51544, 43344, 46248, 27808,  // 1950
            46416, 21928, 19872, 42416, 21176, 21168, 43344, 59728, 27296, 44368,  // 1960
            43856, 19296, 42352, 42352, 21088, 59696, 55632, 23208, 22176, 38608,  // 1970
            19176, 19152, 42192, 53864, 53840, 54568, 46400, 46752, 38608, 38320,  // 1980
            18864, 42168, 42160, 45656, 27216, 27968, 44448, 43872, 37744, 18808,  // 1990
            18800, 25776, 27216, 59984, 27432, 23232, 43872, 37736, 37600, 51552,  // 2000
            54440, 54432, 55888, 23208, 22176, 43736, 9680, 37584, 51544, 43344,  // 2010
            46240, 46416, 46416, 21928, 19360, 42416, 21176, 21168, 43312, 29864,  // 2020
            27296, 44368, 19880, 19296, 38256, 42208, 53856, 59696, 54576, 23200,  // 2030
            27472, 38608, 19176, 19152, 42192, 53848, 53840, 54560, 55968, 46496,  // 2040
            22224, 19160, 18864, 42168, 42160, 43600, 46376, 27936, 44448, 21936 // 2050
    };
    // Array iLunarLeapMonthTable preserves the lunar calendar leap month from 1901 to 2050,
    // if it is 0 express not to have , every byte was stored for two years
    private static final char[] iLunarLeapMonthTable = {0, 80, 4, 0, 32,  // 1910
            96, 5, 0, 32, 112,  // 1920
            5, 0, 64, 2, 6,  // 1930
            0, 80, 3, 7, 0,  // 1940
            96, 4, 0, 32, 112,  // 1950
            5, 0, 48, 128, 6,  // 1960
            0, 64, 3, 7, 0,  // 1970
            80, 4, 8, 0, 96,  // 1980
            4, 10, 0, 96, 5,  // 1990
            0, 48, 128, 5, 0,  // 2000
            64, 2, 7, 0, 80,  // 2010
            4, 9, 0, 96, 4,  // 2020
            0, 32, 96, 5, 0,  // 2030
            48, 176, 6, 0, 80,  // 2040
            2, 7, 0, 80, 3 // 2050
    };
    // Array iSolarLunarTable stored the offset days
    // in New Year of solar calendar and lunar calendar from 1901 to 2050;
    private static final char[] iSolarLunarOffsetTable = {49, 38, 28, 46, 34, 24, 43, 32, 21, 40,  // 1910
            29, 48, 36, 25, 44, 34, 22, 41, 31, 50,  // 1920
            38, 27, 46, 35, 23, 43, 32, 22, 40, 29,  // 1930
            47, 36, 25, 44, 34, 23, 41, 30, 49, 38,  // 1940
            26, 45, 35, 24, 43, 32, 21, 40, 28, 47,  // 1950
            36, 26, 44, 33, 23, 42, 30, 48, 38, 27,  // 1960
            45, 35, 24, 43, 32, 20, 39, 29, 47, 36,  // 1970
            26, 45, 33, 22, 41, 30, 48, 37, 27, 46,  // 1980
            35, 24, 43, 32, 50, 39, 28, 47, 36, 26,  // 1990
            45, 34, 22, 40, 30, 49, 37, 27, 46, 35,  // 2000
            23, 42, 31, 21, 39, 28, 48, 37, 25, 44,  // 2010
            33, 23, 41, 31, 50, 39, 28, 47, 35, 24,  // 2020
            42, 30, 21, 40, 28, 47, 36, 25, 43, 33,  // 2030
            22, 41, 30, 49, 37, 26, 44, 33, 23, 42,  // 2040
            31, 21, 40, 29, 47, 36, 25, 44, 32, 22};

    // 2050
    static boolean bIsSolarLeapYear(int iYear) {
        return ((iYear % 4 == 0) && (iYear % 100 != 0) || iYear % 400 == 0);
    }

    // The days in the month of solar calendar
    static int iGetSYearMonthDays(int iYear, int iMonth) {
        if ((iMonth == 1) || (iMonth == 3) || (iMonth == 5) || (iMonth == 7) || (iMonth == 8) || (iMonth == 10) || (iMonth == 12))
            return 31;
        else if ((iMonth == 4) || (iMonth == 6) || (iMonth == 9) || (iMonth == 11)) return 30;
        else if (iMonth == 2) {
            if (bIsSolarLeapYear(iYear)) return 29;
            else return 28;
        } else return 0;
    }

    // The offset days from New Year and the day when point out in solar calendar
    static int iGetSNewYearOffsetDays(int iYear, int iMonth, int iDay) {
        int iOffsetDays = 0;
        for (int i = 1; i < iMonth; i++) {
            iOffsetDays += iGetSYearMonthDays(iYear, i);
        }
        iOffsetDays += iDay - 1;
        return iOffsetDays;
    }

    static int iGetLLeapMonth(int iYear) {
        char iMonth = iLunarLeapMonthTable[(iYear - 1901) / 2];
        if (iYear % 2 == 0) return (iMonth & 15);
        else return (iMonth & 240) >> 4;
    }

    static int iGetLMonthDays(int iYear, int iMonth) {
        int iLeapMonth = iGetLLeapMonth(iYear);
        if ((iMonth > 12) && (iMonth - 12 != iLeapMonth) || (iMonth < 0)) {
            System.out.println("Wrong month, ^_^ , i think you are want a -1, go to death!");
            return -1;
        }
        if (iMonth - 12 == iLeapMonth) {
            if ((iLunarMonthDaysTable[iYear - 1901] & (32768 >> iLeapMonth)) == 0) return 29;
            else return 30;
        }
        if ((iLeapMonth > 0) && (iMonth > iLeapMonth)) iMonth++;
        if ((iLunarMonthDaysTable[iYear - 1901] & (32768 >> (iMonth - 1))) == 0) return 29;
        else return 30;
    }

    // Days in this year of lunar calendar
    static int iGetLYearDays(int iYear) {
        int iYearDays = 0;
        int iLeapMonth = iGetLLeapMonth(iYear);
        for (int i = 1; i < 13; i++) iYearDays += iGetLMonthDays(iYear, i);
        if (iLeapMonth > 0) iYearDays += iGetLMonthDays(iYear, iLeapMonth + 12);
        return iYearDays;
    }

    static int iGetLNewYearOffsetDays(int iYear, int iMonth, int iDay) {
        int iOffsetDays = 0;
        int iLeapMonth = iGetLLeapMonth(iYear);
        if ((iLeapMonth > 0) && (iLeapMonth == iMonth - 12)) {
            iMonth = iLeapMonth;
            iOffsetDays += iGetLMonthDays(iYear, iMonth);
        }
        for (int i = 1; i < iMonth; i++) {
            iOffsetDays += iGetLMonthDays(iYear, i);
            if (i == iLeapMonth) iOffsetDays += iGetLMonthDays(iYear, iLeapMonth + 12);
        }
        iOffsetDays += iDay - 1;
        return iOffsetDays;
    }

    // The solar calendar is turned into the lunar calendar
    static String sCalendarSolarToLundar(int iYear, int iMonth, int iDay) {
        int iLDay;
        int iLMonth;
        int iLYear;
        int iOffsetDays = iGetSNewYearOffsetDays(iYear, iMonth, iDay);
        int iLeapMonth = iGetLLeapMonth(iYear);
        if (iOffsetDays < iSolarLunarOffsetTable[iYear - 1901]) {
            iLYear = iYear - 1;
            iOffsetDays = iSolarLunarOffsetTable[iYear - 1901] - iOffsetDays;
            iLDay = iOffsetDays;
            for (iLMonth = 12; iOffsetDays > iGetLMonthDays(iLYear, iLMonth); iLMonth--) {
                iLDay = iOffsetDays;
                iOffsetDays -= iGetLMonthDays(iLYear, iLMonth);
            }
            if (0 == iLDay) iLDay = 1;
            else iLDay = iGetLMonthDays(iLYear, iLMonth) - iOffsetDays + 1;
        } else {
            iLYear = iYear;
            iOffsetDays -= iSolarLunarOffsetTable[iYear - 1901];
            iLDay = iOffsetDays + 1;
            for (iLMonth = 1; iOffsetDays >= 0; iLMonth++) {
                iLDay = iOffsetDays + 1;
                iOffsetDays -= iGetLMonthDays(iLYear, iLMonth);
                if ((iLeapMonth == iLMonth) && (iOffsetDays > 0)) {
                    iLDay = iOffsetDays;
                    iOffsetDays -= iGetLMonthDays(iLYear, iLMonth + 12);
                    if (iOffsetDays <= 0) {
                        iLMonth += 12 + 1;
                        break;
                    }
                }
            }
            iLMonth--;
        }
        return iLYear + (iLMonth > 9 ? String.valueOf(iLMonth) : "0" + iLMonth) + (iLDay > 9 ? String.valueOf(iLDay) : "0" + iLDay);
    }

    // The lunar calendar is turned into the Solar calendar
    static String sCalendarLundarToSolar(int iYear, int iMonth, int iDay) {
        int iSYear;
        int iSMonth;
        int iSDay;
        int iOffsetDays = iGetLNewYearOffsetDays(iYear, iMonth, iDay) + iSolarLunarOffsetTable[iYear - 1901];
        int iYearDays = bIsSolarLeapYear(iYear) ? 366 : 365;
        if (iOffsetDays >= iYearDays) {
            iSYear = iYear + 1;
            iOffsetDays -= iYearDays;
        } else {
            iSYear = iYear;
        }
        iSDay = iOffsetDays + 1;
        for (iSMonth = 1; iOffsetDays >= 0; iSMonth++) {
            iSDay = iOffsetDays + 1;
            iOffsetDays -= iGetSYearMonthDays(iSYear, iSMonth);
        }
        iSMonth--;
        return iSYear + (iSMonth > 9 ? String.valueOf(iSMonth) : "0" + iSMonth) + (iSDay > 9 ? String.valueOf(iSDay) : "0" + iSDay);
    }
}
