package tw.gov.pcc.eip.framework.spring.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 強制性擋掉弱掃，必須放在第一個
 *
 * @author swho
 */
public class SafeUploadInterceptor extends HandlerInterceptorAdapter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SafeUploadInterceptor.class);
    private static final String[] BLOCKED = new String[]{"webinspect", ".exe"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ((request.getContentType() != null && request.getContentType()
                .toLowerCase()
                .startsWith("multipart/")) && ClassUtils.isAssignableValue(AbstractMultipartHttpServletRequest.class, request)) {
            AbstractMultipartHttpServletRequest multipartRequest = (AbstractMultipartHttpServletRequest) request;
            boolean pass = multipartRequest.getFileMap()
                    .values()
                    .stream()
                    .allMatch(file -> Arrays.stream(BLOCKED)
                            .noneMatch(keyword -> StringUtils.containsIgnoreCase(file.getOriginalFilename(), keyword)));
            if (!pass) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            return pass;
        } else {
            return true;
        }
    }
}
