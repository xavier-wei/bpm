package tw.gov.pcc.common.filter;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * for keycloak session state invalid error
 * 配合 keycloak 異常錯誤做的修改，可能升級或調整設定後就不需要這個
 */
@Slf4j
public class KeycloakWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse responseOuter, FilterChain filterChain) throws ServletException, IOException {
        KeycloakWrapperResponse response = new KeycloakWrapperResponse(responseOuter);
        filterChain.doFilter(request, response);
        try {
            if (response.getError() == 400) {
                String originalRequestURL = request.getRequestURL().toString();
                if (StringUtils.isNotBlank(request.getParameter("state"))) {
                    log.info("Detect 400 Bad Request by session state is invalid, skip parameters ! ");
                    responseOuter.sendRedirect(ObjectUtility.normalizeObject(originalRequestURL));
                }
            } else if (response.getError() != 0) {
                responseOuter.sendError(ObjectUtility.normalizeObject(response.getError()));
            }
        } catch (IOException e) {
            log.warn("KeycloakWrapperFilter: {}", ExceptionUtility.getStackTrace(e));
        }
    }

    @Getter
    public static class KeycloakWrapperResponse extends HttpServletResponseWrapper {

        private int error;

        /**
         * Constructs a response adaptor wrapping the given response.
         *
         * @param response
         * @throws IllegalArgumentException if the response is null
         */
        public KeycloakWrapperResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc) {
            this.error = sc;
        }
    }
}
