package tw.gov.pcc.eip.util;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.helper.HttpHelper;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * Http 相關公用程式
 *
 * @author Goston
 */
public class HttpUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HttpUtility.class);

    /**
     * 取得使用者 IP
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.trimToEmpty(ip);
    }

    /**
     * 取得各瀏覽器適用的下載檔案檔名
     *
     * @param filename 檔案名稱
     * @return 各瀏覽器能正確解析的檔案名稱字串
     */
    public static String getFilenameByBrowser(String filename) {
        try {
            String userAagent = StringUtils.defaultString(HttpHelper.getHttpRequest().getHeader("user-agent"));
            if (userAagent.indexOf("Edge") > -1) {
                // 因為 Microsoft Edge 的 user-agent 含有 Chrome 和 Safari 這兩個字串，故先處理
                return URLEncoder.encode(filename, "UTF-8");
            }
            if (userAagent.indexOf("Chrome") > -1 || userAagent.indexOf("Firefox") > -1 || userAagent.indexOf("Safari") > -1) {
                // Google Chrome、Mozilla Firefox、Apple Safari
                return MimeUtility.encodeWord(StringUtils.defaultString(filename), "UTF-8", "Q");
            } else {
                // Microsoft Internet Explorer
                return URLEncoder.encode(filename, "UTF-8");
            }
        } catch (Exception e) {
            return StringUtils.defaultString(filename);
        }
    }
}
