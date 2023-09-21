package tw.gov.pcc.common.helper;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.domain.SystemFunction;
import tw.gov.pcc.common.util.StrUtil;

/**
 * 用來存放及取得系統環境參數之 Helper
 *
 * @author Goston
 */
public class EnvFacadeHelper {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EnvFacadeHelper.class);
    private static String systemId = null; // 應用系統代號
    private static String systemName = null; // 應用系統名稱
    // 權限控管相關
    // [

    private static boolean undefineUrlControl = true; // 是否管控未被定義於系統功能清單之功能
    // ]
    // Log 相關
    // [
    private static boolean loggingEnabled = false; // Framework Logging 機制是否啟用
    private static boolean undefineUrlLogging = true; // 是否 Log 未定義之 URL 的操作

    // ]
    /**
     * 以 ServletPath 取得 SystemFunction 物件
     *
     * @param path
     * @return
     */
    public static SystemFunction getSystemFunctionByServletPath(FrameworkUserInfoBean userData, String path) {
        if (userData == null || userData.getSystemFunctionMap() == null || StringUtils.isBlank(path)) {
            return null;
        }
        Map<String, SystemFunction> systemFunctionMap = userData.getSystemFunctionMap();
        String url = parseUrlByServletPath(path);
        Optional<Map.Entry<String, SystemFunction>> entry = systemFunctionMap.entrySet().stream().filter(e -> {
            Pattern urlPattern = e.getValue().getUrlPattern();
            if (urlPattern != null) {
                Matcher matcher = urlPattern.matcher(url);
                return matcher.find();
            }
            return false;
        }).findAny();
        return entry.map(me -> me.getValue()).orElse(null);
    }

    /**
     * 檢核使用者所執行的功能是否未被定義
     *
     * @param frameworkUserInfoBean 使用者物件
     * @param path 使用者執行的功能的 ServletPath
     * @return 使用者執行的是未定義的功能回傳 <code>true</code>, 為已定義的功能回傳 <code>false</code>, 傳入之 Path 為 <code>null</code> 或空白回傳 <code>true</code>
     */
    public static boolean isUndefineUrlByServletPath(FrameworkUserInfoBean frameworkUserInfoBean, String path) {
        if (StringUtils.isBlank(path)) return true;
        return getSystemFunctionByServletPath(frameworkUserInfoBean, path) == null;
    }

    /**
     * 傳入的 ServletPath 若為 <code>.jsp</code> / <code>.html</code> / <code>.htm</code> 等網頁頁面<br>
     * 則取出檔名的部份 (不含副檔名) 轉成大寫傳回 (即畫面編號),<br>
     * 若為 .action 之類則不進行轉換<br>
     *
     * @param path 使用者執行的功能的 ServletPath
     * @return 轉換後的字串, 如傳入 <code>"/abc.jsp"</code> 則傳回 <code>"ABC"</code>; 如傳入 <code>"/abc.action"</code> 則傳回 <code>"/abc.action"</code>
     */
    public static String parseUrlByServletPath(String path) {
        if (StringUtils.endsWithIgnoreCase(path, ".jsp") || StringUtils.endsWithIgnoreCase(path, ".htm") || StringUtils.endsWithIgnoreCase(path, ".html")) return StringUtils.upperCase(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")));
         else return path;
    }

    /**
     * 依傳入的 ServletPath 取得 系統項目代碼 (<code>ITEMS.ITEM_ID</code>)<br>
     *
     * @param frameworkUserInfoBean 使用者物件
     * @param path 使用者執行的功能的 ServletPath
     * @return 系統項目代碼, 若該路徑未定義回傳 <code>null</code>
     */
    public static String getItemIdByServletPath(FrameworkUserInfoBean frameworkUserInfoBean, String path) {
        if (isUndefineUrlByServletPath(frameworkUserInfoBean, path)) // 路徑未定義
         return null;
        String url = parseUrlByServletPath(path);
        SystemFunction func = getSystemFunctionByServletPath(frameworkUserInfoBean, url);
        if (func == null) // 功能未定義
         return null;
         else  // 功能有定義
        return func.getItemId();
    }

    /**
     * 依傳入的 ServletPath 取得 系統項目名稱 (<code>ITEMS.ITEM_NAME</code>)<br>
     *
     * @param useframeworkUserInfoBeanData 使用者物件
     * @param path 使用者執行的功能的 ServletPath
     * @return 系統項目名稱, 若該路徑未定義回傳 <code>null</code>
     */
    public static String getItemNameByServletPath(FrameworkUserInfoBean useframeworkUserInfoBeanData, String path) {
        if (isUndefineUrlByServletPath(useframeworkUserInfoBeanData, path)) // 路徑未定義
         return null;
        String url = parseUrlByServletPath(path);
        SystemFunction func = getSystemFunctionByServletPath(useframeworkUserInfoBeanData, url);
        if (func == null) // 功能未定義
         return null;
         else  // 功能有定義
        return func.getItemName();
    }

    /**
     * 檢核目前使用者執行的動作是否需要紀錄 Log
     *
     * @return 需要回傳 <code>true</code>; 不需要回傳 <code>false</code>
     */
    public static boolean isNeedLogging(HttpServletRequest request) {
        if (request == null) return false;
        FrameworkUserInfoBean userData = UserSessionHelper.getFrameworkUserData(request);
        String servlietPath = StringUtils.defaultString(request.getServletPath());
        if (loggingEnabled && userData != null) {
            if (log.isDebugEnabled()) {
                log.debug("檢核路徑是否需要紀錄 Log, 路徑: {}", StrUtil.safeLog(servlietPath));
            }
            if (isUndefineUrlByServletPath(userData, servlietPath)) {
                if (log.isDebugEnabled()) {
                    log.debug("{} 需紀錄 Log, 路徑: {}", ((undefineUrlLogging) ? "" : "不"), StrUtil.safeLog(servlietPath));
                }
                return undefineUrlLogging;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("需紀錄 Log, 路徑: {}", StrUtil.safeLog(servlietPath));
                }
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 取得 應用系統代號
     *
     * @return 應用系統代號
     */
    public static String getSystemId() {
        return systemId;
    }

    /**
     * 設定 應用系統代號
     *
     * @param systemId 應用系統代號
     */
    public static void setSystemId(String systemId) {
        // 應用系統代號 僅可指定一次
        if (EnvFacadeHelper.systemId == null) EnvFacadeHelper.systemId = StringUtils.upperCase(systemId);
    }

    /**
     * 取得 應用系統名稱
     *
     * @return 應用系統名稱
     */
    public static String getSystemName() {
        return systemName;
    }

    /**
     * 設定 應用系統名稱
     *
     * @param systemName 應用系統名稱
     */
    public static void setSystemName(String systemName) {
        // 應用系統名稱 僅可指定一次
        if (EnvFacadeHelper.systemName == null) EnvFacadeHelper.systemName = systemName;
    }

    /**
     * 取得 是否管控未被定義於系統功能清單之功能
     *
     * @return
     */
    public static boolean isUndefineUrlControl() {
        return undefineUrlControl;
    }

    /**
     * 設定 是否管控未被定義於系統功能清單之功能
     *
     * @param undefineUrlControl
     */
    public static void setUndefineUrlControl(boolean undefineUrlControl) {
        EnvFacadeHelper.undefineUrlControl = undefineUrlControl;
    }

    /**
     * 取得 Framework Logging 機制是否啟用
     *
     * @return
     */
    public static boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    /**
     * 設定 Framework Logging 機制是否啟用
     *
     * @param loggingEnabled
     */
    public static void setLoggingEnabled(boolean loggingEnabled) {
        EnvFacadeHelper.loggingEnabled = loggingEnabled;
    }

    /**
     * 取得 是否 Log 未定義之 URL 的操作
     *
     * @return
     */
    public static boolean isUndefineUrlLogging() {
        return undefineUrlLogging;
    }

    /**
     * 設定 是否 Log 未定義之 URL 的操作
     *
     * @param undefineUrlLogging
     */
    public static void setUndefineUrlLogging(boolean undefineUrlLogging) {
        EnvFacadeHelper.undefineUrlLogging = undefineUrlLogging;
    }
}
