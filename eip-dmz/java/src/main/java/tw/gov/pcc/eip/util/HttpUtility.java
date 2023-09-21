package tw.gov.pcc.eip.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.common.helper.HttpHelper;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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

    /**
     * 建立 {@link HttpHeaders} 並加入 errors 欄位
     * @param errorMessage 錯誤訊息
     * @return {@link HttpHeaders}
     */
    @NonNull
    public static HttpHeaders createErrorHeaders(@Nullable String errorMessage) {
        return createErrorHeaders(errorMessage, false);
    }

    /**
     * 建立 {@link HttpHeaders} 並加入 errors 欄位
     * @param errorMessage 錯誤訊息
     * @param isAlert 是否為前端 alert 視窗
     * @return {@link HttpHeaders}
     */
    @NonNull
    public static HttpHeaders createErrorHeaders(@Nullable String errorMessage, boolean isAlert) {
        HttpHeaders headers = new HttpHeaders();
        try {
            if (isNotEmpty(errorMessage)) {
                headers.add("errors", URLEncoder.encode(errorMessage, "UTF-8"));
                if (isAlert) {
                    headers.add("alert", StringUtility.normalizeString("true"));
                } else {
                    headers.add("alert", StringUtility.normalizeString("false"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return headers;
    }

    /**
     * 將 {@link BindingResult} 的所有錯誤訊息取出變成字串，不同錯誤之間以<code>\r\n</code>分隔
     * @param bindingResult {@link BindingResult}
     * @return error message
     */
    @NonNull
    public static String convertBindingResultErrors(@NonNull BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    if (errors.length() > 0) {
                        errors.append("\r\n");
                    }
                    errors.append(object.getDefaultMessage());
                } else {
                    if (object.getDefaultMessage() != null) {
                        if (errors.length() > 0) {
                            errors.append("\r\n");
                        }
                        errors.append(object.getDefaultMessage());
                    }
                }
            }
        }
        return errors.toString();
    }

}
