package tw.gov.pcc.filter;

import org.keycloak.adapters.*;
import org.keycloak.adapters.servlet.FilterRequestAuthenticator;
import org.keycloak.adapters.servlet.OIDCFilterSessionStore;
import org.keycloak.adapters.servlet.OIDCServletHttpFacade;
import org.keycloak.adapters.spi.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@WebFilter("/*")
//@Profile("dev")
public class KeycloakOIDCFilter  implements Filter {
    private static final Logger log = Logger.getLogger("" + KeycloakOIDCFilter.class);
    public static final String SKIP_PATTERN_PARAM = "keycloak.config.skipPattern";
    public static final String ID_MAPPER_PARAM = "keycloak.config.idMapper";
    public static final String CONFIG_RESOLVER_PARAM = "keycloak.config.resolver";
    public static final String CONFIG_FILE_PARAM = "keycloak.config.file";
    public static final String CONFIG_PATH_PARAM = "keycloak.config.path";
    protected AdapterDeploymentContext deploymentContext;
    protected SessionIdMapper idMapper;
    protected NodesRegistrationManagement nodesRegistrationManagement;
    protected Pattern skipPattern;
    private final KeycloakConfigResolver definedconfigResolver;

    public KeycloakOIDCFilter(KeycloakConfigResolver definedconfigResolver) {
        this.idMapper = new InMemorySessionIdMapper();
        this.definedconfigResolver = definedconfigResolver;
    }

    public KeycloakOIDCFilter() {
        this((KeycloakConfigResolver)null);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String skipPatternDefinition = filterConfig.getInitParameter("keycloak.config.skipPattern");
        if (skipPatternDefinition != null) {
            this.skipPattern = Pattern.compile(skipPatternDefinition, 32);
        }

        String idMapperClassName = filterConfig.getInitParameter("keycloak.config.idMapper");
        Object is;
        if (idMapperClassName != null) {
            try {
                Class<?> idMapperClass = this.getClass().getClassLoader().loadClass(idMapperClassName);
                Constructor<?> idMapperConstructor = idMapperClass.getDeclaredConstructor();
                is = null;
                if (idMapperConstructor.getModifiers() == 2) {
                    is = idMapperClass.getMethod("getInstance").invoke((Object)null);
                } else {
                    is = idMapperConstructor.newInstance();
                }

                if (is instanceof SessionIdMapper) {
                    this.idMapper = (SessionIdMapper)is;
                } else {
                    log.log(Level.WARNING, "SessionIdMapper class {0} is not instance of org.keycloak.adapters.spi.SessionIdMapper", idMapperClassName);
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException var11) {
                log.log(Level.WARNING, "SessionIdMapper class could not be instanced", var11);
            }
        }

        if (this.definedconfigResolver != null) {
            this.deploymentContext = new AdapterDeploymentContext(this.definedconfigResolver);
            log.log(Level.INFO, "Using {0} to resolve Keycloak configuration on a per-request basis.", this.definedconfigResolver.getClass());
        } else {
            String configResolverClass = filterConfig.getInitParameter("keycloak.config.resolver");
            if (configResolverClass != null) {
                try {
                    KeycloakConfigResolver configResolver = (KeycloakConfigResolver)this.getClass().getClassLoader().loadClass(configResolverClass).newInstance();
                    this.deploymentContext = new AdapterDeploymentContext(configResolver);
                    log.log(Level.INFO, "Using {0} to resolve Keycloak configuration on a per-request basis.", configResolverClass);
                } catch (Exception var10) {
                    log.log(Level.FINE, "The specified resolver {0} could NOT be loaded. Keycloak is unconfigured and will deny all requests. Reason: {1}", new Object[]{configResolverClass, var10.getMessage()});
                    this.deploymentContext = new AdapterDeploymentContext(new KeycloakDeployment());
                }
            } else {
                String fp = filterConfig.getInitParameter("keycloak.config.file");
                is = null;
                if (fp != null) {
                    try {
                        is = new FileInputStream(fp);
                    } catch (FileNotFoundException var9) {
                        throw new RuntimeException(var9);
                    }
                } else {
                    String path = "/WEB-INF/keycloak.json";
                    String pathParam = filterConfig.getInitParameter("keycloak.config.path");
                    if (pathParam != null) {
                        path = pathParam;
                    }

                    is = filterConfig.getServletContext().getResourceAsStream(path);
                }

                KeycloakDeployment kd = this.createKeycloakDeploymentFrom((InputStream)is);
                this.deploymentContext = new AdapterDeploymentContext(kd);
                log.fine("Keycloak is using a per-deployment configuration.");
            }
        }

        filterConfig.getServletContext().setAttribute(AdapterDeploymentContext.class.getName(), this.deploymentContext);
        this.nodesRegistrationManagement = new NodesRegistrationManagement();
    }

    private KeycloakDeployment createKeycloakDeploymentFrom(InputStream is) {
        if (is == null) {
            log.fine("No adapter configuration. Keycloak is unconfigured and will deny all requests.");
            return new KeycloakDeployment();
        } else {
            return KeycloakDeploymentBuilder.build(is);
        }
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.fine("Keycloak OIDC Filter");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String referer = request.getHeader("referer");
//        if (referer == null ||referer.isEmpty()) {
//            response.setContentType("text/html; charset=utf-8");
//            response.getWriter().println("請由EIP首頁進入");
//            return;
//        }
        if (this.shouldSkip(request)) {
            chain.doFilter(req, res);
        } else {
            OIDCServletHttpFacade facade = new OIDCServletHttpFacade(request, response);
            KeycloakDeployment deployment = this.deploymentContext.resolveDeployment(facade);
            if (deployment != null && deployment.isConfigured()) {
                PreAuthActionsHandler preActions = new PreAuthActionsHandler(new KeycloakOIDCFilter.IdMapperUserSessionManagement(), this.deploymentContext, facade);
                if (!preActions.handleRequest()) {
                    this.nodesRegistrationManagement.tryRegister(deployment);
                    OIDCFilterSessionStore tokenStore = new OIDCFilterSessionStore(request, facade, 100000, deployment, this.idMapper);
                    tokenStore.checkCurrentToken();
                    FilterRequestAuthenticator authenticator = new FilterRequestAuthenticator(deployment, tokenStore, facade, request, 8443);
                    AuthOutcome outcome = authenticator.authenticate();
                    if (outcome == AuthOutcome.AUTHENTICATED) {
                        log.fine("AUTHENTICATED");
                        if (!facade.isEnded()) {
                            AuthenticatedActionsHandler actions = new AuthenticatedActionsHandler(deployment, facade);
                            if (!actions.handledRequest()) {
                                HttpServletRequestWrapper wrapper = tokenStore.buildWrapper();
                                chain.doFilter(wrapper, res);
                            }
                        }

                    } else {
                        AuthChallenge challenge = authenticator.getChallenge();
                        if (challenge != null) {
                            log.fine("challenge");
                            challenge.challenge(facade);
                        } else {
                            response.sendError(403);
                        }
                    }
                }
            } else {
                response.sendError(403);
                log.fine("deployment not configured");
            }
        }
    }

    private boolean shouldSkip(HttpServletRequest request) {
        if (this.skipPattern == null) {
            return false;
        } else {
            String requestPath = request.getRequestURI().substring(request.getContextPath().length());
            return this.skipPattern.matcher(requestPath).matches();
        }
    }

    public void destroy() {
    }

    private class IdMapperUserSessionManagement implements UserSessionManagement {
        private IdMapperUserSessionManagement() {
        }

        public void logoutAll() {
            if (KeycloakOIDCFilter.this.idMapper != null) {
                KeycloakOIDCFilter.this.idMapper.clear();
            }

        }

        public void logoutHttpSessions(List<String> ids) {
            KeycloakOIDCFilter.log.fine("**************** logoutHttpSessions");
            Iterator var2 = ids.iterator();

            while(var2.hasNext()) {
                String id = (String)var2.next();
                KeycloakOIDCFilter.log.finest("removed idMapper: " + id);
                KeycloakOIDCFilter.this.idMapper.removeSession(id);
            }

        }
    }
}
