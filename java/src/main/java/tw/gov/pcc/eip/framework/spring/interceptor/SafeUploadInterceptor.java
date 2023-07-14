package tw.gov.pcc.eip.framework.spring.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 強制性擋掉可疑有害的嘗試，必須放在第一個
 *
 * @author swho
 */
@Slf4j
public class SafeUploadInterceptor implements HandlerInterceptor {
    private static final String[] BLOCKED = new String[]{"webinspect", ".exe"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ((request.getContentType() != null && StringUtils.startsWithIgnoreCase(request.getContentType(),
                "multipart/")) && ClassUtils.isAssignableValue(AbstractMultipartHttpServletRequest.class, request)) {
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
