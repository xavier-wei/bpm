package tw.gov.pcc.eip.framework.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitialListener implements ServletContextListener {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InitialListener.class);

    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        /*
        // SecureToken 相關
        try {
            log.info("正在初始化  SecureToken.....");

            DataSource ds = (DataSource) SpringHelper.getBeanById(Global.DATASOURCE_ID);
            SecureToken.initail(ds, Global.SYS_ID);

            log.info("SecureToken 初始化完成.....");
        }
        catch (Exception e) {
            log.error("SecureToken 初始化失敗, 原因: {}", ExceptionUtility.getStackTrace(e));
        }
        */
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
