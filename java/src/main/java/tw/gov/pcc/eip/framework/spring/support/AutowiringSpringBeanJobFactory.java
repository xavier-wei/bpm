package tw.gov.pcc.eip.framework.spring.support;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 用於 Inject Bean reference 到 Quartz Job，
 * <p>
 * 注意：只支援 By Type 的 Setter injection。
 *
 * @author Goston
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AutowiringSpringBeanJobFactory.class);
    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        // beanFactory.autowireBean(job);
        beanFactory.autowireBeanProperties(job, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        return job;
    }
}
