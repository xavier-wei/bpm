package tw.gov.pcc.common.filter;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.MDC;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import tw.gov.pcc.common.util.StrUtil;
import tw.gov.pcc.eip.common.cases.Eipxx0w030Case;
import tw.gov.pcc.eip.common.controllers.SettingController;
import tw.gov.pcc.eip.services.ProfileService;
import tw.gov.pcc.eip.util.ExceptionUtility;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Framework 權限控管 Filter<br>
 */
public class AuthorizeFilter implements Filter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthorizeFilter.class);
    private static final String SSO_AUTO_RE_LOGIN_URL = "ssoAutoReLoginUrl"; // 啟用 SSO Login 時, 若 SSO 沒 Timeout 但系統 Timeout 要用來處理自動重新登入之 Url - <init-param> 之 <param-name>
    private static final String UNDEFINE_URL_CONTROL = "undefineUrlControl"; // 是否管控未被定義於系統功能清單之功能 - <init-param> 之 <param-name>
    private static final String LOGIN_URL = "loginUrl"; // 系統登入頁面或用來處理登入資訊的 Action URL - <init-param> 之 <param-name>
    private static final String UNAUTHORIZED_URL = "unauthorizedUrl"; // 「沒有權限之訊息頁面」的位址 - <init-param> 之 <param-name>
    private static final String UNDEFINE_FUNCTION_URL = "undefineFunctionUrl"; // 「未定義之功能訊息頁面」的位址 - <init-param> 之 <param-name>
    private static final String REDIRECT_URL = "redirectUrl"; // 「重新導向頁面」的位址 - <init-param> 之 <param-name>
    private String ssoAutoReLoginUrl = null; // 啟用 SSO Login 時, 若 SSO 沒 Timeout 但系統 Timeout 要用來處理自動重新登入之 Url
    private boolean undefineUrlControl = true; // 是否管控未被定義於系統功能清單之功能
    private ArrayList<String> loginUrl = new ArrayList<String>(); // 系統登入頁面或用來處理登入資訊的 Action URL
    private String unauthorizedUrl = null; // 「沒有權限之訊息頁面」的位址
    private String undefineFunctionUrl = null; // 「未定義之功能訊息頁面」的位址
    private String redirectUrl = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        path = StringUtils.removeStartIgnoreCase(path, req.getContextPath() + "/");
        if (isLoginUrl(path)) {
            // begin ... [
            // 如果是登入相關頁面或 Action 則不進行檢核
            chain.doFilter(request, response);
        } else {
            // 不是「沒有權限之訊息頁面」再做檢核
            if (StringUtils.indexOf(req.getRequestURI(), unauthorizedUrl) == -1 && StringUtils.indexOf(req.getRequestURI(), undefineFunctionUrl) == -1 && StringUtils.indexOf(req.getRequestURI(), redirectUrl) == -1) {
                if (log.isDebugEnabled()) {
                    log.debug("檢核使用者之權限...");
                }
                boolean bSuccess = false;
                boolean bFunctionDefined = false;
                FrameworkUserInfoBean userData = UserSessionHelper.getFrameworkUserData(req);
                // 使用者有登入系統再進行檢核, 若無登入則檢核必定為失敗
                if (userData != null) {
                    MDC.put("userId", userData.getUserId());
                    setupProfile(userData.getUserId(), session);
                    // 檢核使用者執行的功能是否未被定義
                    if (EnvFacadeHelper.isUndefineUrlByServletPath(userData, path)) {
                        // 未定義
                        if (!undefineUrlControl) {
                            // 不控管未定義之功能
                            bSuccess = true;
                            bFunctionDefined = false;
                        } else {
                            bSuccess = false;
                            bFunctionDefined = false;
                        }
                    } else {
                        // 有定義
                        // 檢核使用者是否有執行該功能之權限
                        if (userData.hasPrivilege(EnvFacadeHelper.getItemIdByServletPath(userData, path))) {
                            bSuccess = true;
                        }
                        bFunctionDefined = true;
                    }
                }

                if (userData == null && session.getAttribute(KeycloakSecurityContext.class.getName()) != null) {
                    // 如果有定義自動重新登入之位址則自動重新登入
                    if (StringUtils.isNotBlank(ssoAutoReLoginUrl)) {
                        if (StringUtils.indexOf(ssoAutoReLoginUrl, "http://") != 0 && StringUtils.indexOf(ssoAutoReLoginUrl, "https://") != 0) {
                            String reurl = ssoAutoReLoginUrl;
                            redirect(req, res, reurl);
                            return;
                        } else {
                            redirect(req, res, ssoAutoReLoginUrl);
                            return;
                        }
                    } else {
                        redirect(req, res, unauthorizedUrl);
                        return;
                    }
                }
                if (bSuccess) {
                    // 檢核成功
                    if (log.isDebugEnabled()) {
                        log.debug("檢核成功, 使用者代碼: {} 路徑: {}", StrUtil.safeLog(userData.getUserId()), StrUtil.safeLog(path));
                    }
                    chain.doFilter(request, response);
                } else {
                    // 檢核失敗
                    log.info("檢核失敗, 使用者代碼: {} 路徑: {}", ((userData != null) ? StrUtil.safeLog(userData.getUserId()) : "未登入"), StrUtil.safeLog(path));
                    if (userData != null && !bFunctionDefined) {
                        if (StringUtils.indexOf(undefineFunctionUrl, "http://") != 0 && StringUtils.indexOf(undefineFunctionUrl, "https://") != 0) {
                            // 由於權限檢核失敗後, Framework 的 LoggingFilter 並不會被呼叫,
                            // 因此在此需手動紀錄 Portal Log, 至於是否紀錄或 Framework 的 Log 機制是否開啟, 在 doPortalLog 會判斷
                            FrameworkLogUtil.doPortalLog(req);
                            redirect(req, res, undefineFunctionUrl);
                        } else {
                            // 由於權限檢核失敗後, Framework 的 LoggingFilter 並不會被呼叫,
                            // 因此在此需手動紀錄 Portal Log, 至於是否紀錄或 Framework 的 Log 機制是否開啟, 在 doPortalLog 會判斷
                            FrameworkLogUtil.doPortalLog(req);
                            redirect(req, res, undefineFunctionUrl);
                        }
                    } else {
                        if (StringUtils.indexOf(unauthorizedUrl, "http://") != 0 && StringUtils.indexOf(unauthorizedUrl, "https://") != 0) {
                            // 由於權限檢核失敗後, Framework 的 LoggingFilter 並不會被呼叫,
                            // 因此在此需手動紀錄 Portal Log, 至於是否紀錄或 Framework 的 Log 機制是否開啟, 在 doPortalLog 會判斷
                            FrameworkLogUtil.doPortalLog(req);
                            redirect(req, res, unauthorizedUrl);
                        } else {
                            // 由於權限檢核失敗後, Framework 的 LoggingFilter 並不會被呼叫,
                            // 因此在此需手動紀錄 Portal Log, 至於是否紀錄或 Framework 的 Log 機制是否開啟, 在 doPortalLog 會判斷
                            FrameworkLogUtil.doPortalLog(req);
                            redirect(req, res, unauthorizedUrl);
                        }
                    }
                }
            } else {
                // 代表此頁為「沒有權限之訊息頁面」
                chain.doFilter(request, response);
            }
        } // ] ... end if (isLoginUrl(path))
    }

    private void setupProfile(String userId, HttpSession session) {
        try {
            Optional.ofNullable(session.getAttribute(SettingController.SETTING))
                    .ifPresentOrElse(x -> {
                    }, () -> {
                        Eipxx0w030Case eipxx0w030Case = new Eipxx0w030Case();
                        eipxx0w030Case.setEntryListOrder(ObjectUtils.defaultIfNull(SpringHelper.getBeanByClass(ProfileService.class)
                                        .readProfileMap(userId)
                                        .get(ProfileService.ENTRY_LIST_ORDER_KEY), SettingController.ENTRY_LIST_ORDER)
                                .toString());
                        session.setAttribute(SettingController.SETTING, eipxx0w030Case);
                    });
        } catch (Exception e) {
            log.warn("讀取 profile 失敗 {}！", ExceptionUtility.getStackTrace(e));
        }
    }

    /**
     * 檢核使用者執行的功能的 ServletPath 是否為登入相關頁面或 Action
     *
     * @param path 使用者執行的功能的 ServletPath
     * @return 若為登入相關頁面或 Action 回傳 <code>true</code>, 否則回傳 <code>false</code>
     */
    public boolean isLoginUrl(String path) {
        return loginUrl != null && loginUrl.contains(path);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // 啟用 SSO Login 時, 若 SSO 沒 Timeout 但系統 Timeout 要用來處理自動重新登入之 Url

        String sSsoAutoReLoginUrl = filterConfig.getInitParameter(SSO_AUTO_RE_LOGIN_URL);
        if (StringUtils.isNotBlank(sSsoAutoReLoginUrl)) {
            ssoAutoReLoginUrl = sSsoAutoReLoginUrl;
        }

        // 是否管控未被定義於系統功能清單之功能
        // [
        String sUndefineUrlControl = filterConfig.getInitParameter(UNDEFINE_URL_CONTROL);
        if (StringUtils.equalsIgnoreCase(sUndefineUrlControl, "true")) {
            undefineUrlControl = true;
            EnvFacadeHelper.setUndefineUrlControl(true);
        } else {
            undefineUrlControl = false;
            EnvFacadeHelper.setUndefineUrlControl(false);
        }
        // ]
        // 系統登入頁面或用來處理登入資訊的 Action URL
        // [
        String[] sUrlArray = StringUtils.split(StringUtils.defaultString(filterConfig.getInitParameter(LOGIN_URL)), ",");
        if (sUrlArray != null) {
            // 處理路徑, 由於後續比對是以 ServletPath 比對, 故路徑開頭須有 "/"
            for (int i = 0; i < sUrlArray.length; i++) {
                sUrlArray[i] = sUrlArray[i].trim();
                if (!StringUtils.startsWith(sUrlArray[i], "/")) sUrlArray[i] = "/" + sUrlArray[i];
            }
            loginUrl = new ArrayList<String>(Arrays.asList(sUrlArray));
        }
        // ]
        // 「沒有權限之訊息頁面」的位址
        // [
        unauthorizedUrl = filterConfig.getInitParameter(UNAUTHORIZED_URL)
                .trim();
        if (!StringUtils.startsWith(unauthorizedUrl, "http://") && !StringUtils.startsWith(unauthorizedUrl, "https://"))
            unauthorizedUrl = ((StringUtils.indexOf(unauthorizedUrl, "/") != 0) ? "/" : "") + unauthorizedUrl;
        // ]
        // 「未定義之功能訊息頁面」的位址
        // [
        undefineFunctionUrl = filterConfig.getInitParameter(UNDEFINE_FUNCTION_URL)
                .trim();
        if (!StringUtils.startsWith(undefineFunctionUrl, "http://") && !StringUtils.startsWith(undefineFunctionUrl, "https://"))
            undefineFunctionUrl = ((StringUtils.indexOf(undefineFunctionUrl, "/") != 0) ? "/" : "") + undefineFunctionUrl;
        // ]
        // 「重新導向頁面」的位址
        // [
        redirectUrl = filterConfig.getInitParameter(REDIRECT_URL)
                .trim();
        if (!StringUtils.startsWith(redirectUrl, "http://") && !StringUtils.startsWith(redirectUrl, "https://"))
            redirectUrl = ((StringUtils.indexOf(redirectUrl, "/") != 0) ? "/" : "") + redirectUrl;
        // ]
    }

    public void destroy() {
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        request.getSession()
                .setAttribute("redirectUrl", url);
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
    }
}
