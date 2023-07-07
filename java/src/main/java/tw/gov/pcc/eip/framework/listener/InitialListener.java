package tw.gov.pcc.eip.framework.listener;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.servlet.KeycloakOIDCFilter;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 初始化作業
 * 1. 轉換keycloak的log到slf4j
 *
 * @author swho
 */
@Slf4j
public class InitialListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        try {
            initKeycloakLogRedirect(); //for keycloak
            initSlf4jBridge();
        } catch (IOException ignored) {
        }
    }

    private void initSlf4jBridge() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private void initKeycloakLogRedirect() throws IOException {
        Logger logger = Logger.getLogger(String.valueOf(KeycloakOIDCFilter.class));
        logger.setLevel(Level.FINE);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
