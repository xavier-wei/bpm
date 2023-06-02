package tw.gov.pcc.eip.framework.spring.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import tw.gov.pcc.eip.domain.Sysmsg;
import tw.gov.pcc.eip.services.MessageResourceService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 從 Database 讀取 Message Source
 *
 * @author Goston
 */
public class DatabaseDrivenMessageSource extends AbstractMessageSource implements ResourceLoaderAware {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DatabaseDrivenMessageSource.class);
    private ResourceLoader resourceLoader;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, String> properties = new HashMap<String, String>();
    @Autowired
    private MessageResourceService messageResourceService;

    public DatabaseDrivenMessageSource() {
        reload();
    }

    public DatabaseDrivenMessageSource(MessageResourceService messageResourceService) {
        log.info("===== 載入 Database Driven MessageSource =====");
        this.messageResourceService = messageResourceService;
        reload();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        if (msg != null) return createMessageFormat(msg, locale);
         else return null;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getText(code, locale);
    }

    private String getText(String code, Locale locale) {
        Lock lock = this.lock.readLock();
        String text; // 不管 Locale 了
        try {
            lock.lock();
            text = properties.get(code);
        } finally {
            lock.unlock();
        }
        try {
            if (text == null) text = getParentMessageSource().getMessage(code, null, locale);
            return text != null ? text : code;
        } catch (Exception e) {
        }
        // do nothing
        return null;
    }

    public void reload() {
        Lock lock = this.lock.writeLock();
        try {
            lock.lock();
            properties.clear();
            properties.putAll(loadTexts());
        } finally {
            lock.unlock();
        }
    }

    protected Map<String, String> loadTexts() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<Sysmsg> list = messageResourceService.selectAllData();
            for (Sysmsg sysmsg : list) {
                map.put(sysmsg.getCode(), sysmsg.getValue());
            }
        } catch (Exception e) {
            log.error("讀取 errormsg 失敗 {}", ExceptionUtility.getStackTrace(e));
        }
        return map;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
    }
}
