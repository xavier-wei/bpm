package tw.gov.pcc.common.filter;

import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.helper.UserSessionHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Skip CASFilter SSO mechanism and create testing account for run on local debug mode.
 *
 * Important: only for developer local test.
 */
public class DevN5000UserCASFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(DevN5000UserCASFilter.class);
	private final String devUserId = "n5000";
	private final String devDeptId = "iisi";

	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Initial DevN5000UserCASFilter for local develop ...");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
        FrameworkUserInfoBean userData = null;
		if (null == session.getAttribute(KeycloakSecurityContext.class.getName())) {
			try {
                userData = UserSessionHelper.getFrameworkUserData((HttpServletRequest) request);
			} catch (Exception e) {
				// login as develop mode failed.
				log.error("auto login as n5000 user failed.", e);
			} finally {
				if (userData == null) {
					// user undefined.
                    userData = new FrameworkUserInfoBean();
                    userData.setUserId(devUserId);
                    userData.setDeptId(devDeptId);  // journal logger needed.
				}
			}
			// 將使用者資料存入 Session
			session.setAttribute(UserSessionHelper.USER_INFO, userData);
			session.setAttribute(KeycloakSecurityContext.class.getName(), devUserId);
			UserSessionHelper.setUserData(((HttpServletRequest) request), userData);
		}

		chain.doFilter(request, response);
	}

	public void destroy() { }

}
