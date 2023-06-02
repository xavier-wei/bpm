package tw.gov.pcc.common.filter;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * HTTP Method Override檢測處理
 *
 * @author Hugo
 */
public class HttpFilter implements Filter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HttpFilter.class);
    private static final List<String> RejectParametersBlackList = Collections.singletonList(")(");

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (isMethodOverride(req, res, chain)) {
            return;
        }
        if (isInRejectParametersBlackList(req)) {
            return;
        }
        setupHSTS(res);
        setupCSP(res);
        setupSidSameSiteCookies(req, res);
        chain.doFilter(request, response);
    }

    private void setupCSP(HttpServletResponse res) {
        res.setHeader("Content-Security-Policy", "frame-ancestors 'self'");
    }

    private void setupSidSameSiteCookies(final HttpServletRequest req, final HttpServletResponse res) {
        if (req.getSession() != null) {
            String sid = req.getSession().getId();
            if (StringUtils.isNotBlank(sid)) {
                res.setHeader("Set-Cookie", "JSESSIONID=" + sid + "; httpOnly; SameSite=Lax");
            }
        }
    }

    private void setupHSTS(final HttpServletResponse res) {
        res.setHeader("Strict-Transport-Security", "max age=31536000;includeSubdomains; preload");
    }

    private boolean isInRejectParametersBlackList(final HttpServletRequest req) {
        for (String[] values : req.getParameterMap().values()) {
            for (String value : values) {
                for (String wrong : RejectParametersBlackList) {
                    if (StringUtils.contains(value, wrong)) {
                        log.error("Found request with parameters in rejection list!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isMethodOverride(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getHeader("X-HTTP-METHOD")) || org.apache.commons.lang3.StringUtils.isNotBlank(request.getHeader("X-HTTP-Method-Override")) || org.apache.commons.lang3.StringUtils.isNotBlank(request.getHeader("X-METHOD-OVERRIDE"))) {
            log.error("偵測到X-METHOD-OVERRIDE");
            response.setStatus(405);
            return true;
        }
        if (request.getParameterMap().containsKey("_method")) {
            log.error("偵測到_method");
            response.setStatus(405);
            return true;
        }
        return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
