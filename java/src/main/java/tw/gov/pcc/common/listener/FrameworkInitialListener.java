package tw.gov.pcc.common.listener;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.ConstantKey;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.helper.SpringHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Framework 環境初始化<br>
 * <br>
 * 欲使用 Framework 提供的各項 Logging 及權限控管功能, 必須於 <code>web.xml</code> 定義此 Listener:<br>
 * <code><pre>
 *  &lt;!-- Define System ID for Framework --&gt;
 *  &lt;context-param&gt;
 *      &lt;param-name&gt;systemId&lt;/param-name&gt;
 *      &lt;param-value&gt;BA&lt;/param-value&gt;
 *  &lt;/context-param&gt;
 *
 *  &lt;!-- Define System Name for Framework --&gt;
 *  &lt;context-param&gt;
 *      &lt;param-name&gt;systemName&lt;/param-name&gt;
 *  &lt;/context-param&gt;
 *
 *  &lt;!-- Initial Framework Environment --&gt;
 *  &lt;listener&gt;
 *      &lt;listener-class&gt;tw.gov.pcc.common.listener.FrameworkInitialListener&lt;/listener-class&gt;
 *  &lt;/listener&gt;
 * </pre></code>
 *
 * @author Goston
 */
public class FrameworkInitialListener implements ServletContextListener {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FrameworkInitialListener.class);

    public void contextInitialized(ServletContextEvent event) {
        log.info("===== Framework 環境 初始化開始 =====");
        ServletContext ctx = event.getServletContext();
        // 設定 應用系統代號
        EnvFacadeHelper.setSystemId(ctx.getInitParameter(ConstantKey.SYSTEM_ID));
        log.info("設定 應用系統代號 完成");
        // 設定 應用系統名稱
        EnvFacadeHelper.setSystemName(ctx.getInitParameter(ConstantKey.SYSTEM_NAME));
        log.info("設定 應用系統名稱 完成");
        // 設定 SpringHelper 中的 ServletContext
        SpringHelper.setCtx(ctx);
        log.info("設定 SpringHelper 完成");
        log.info("===== Framework 環境 初始化完成! =====");
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
