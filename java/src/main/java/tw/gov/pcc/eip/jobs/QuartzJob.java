package tw.gov.pcc.eip.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Quartz job 一定要繼承這個才會自動注入
 */
public abstract class QuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        handler(context);
    }
    
    protected abstract void handler(JobExecutionContext context) throws JobExecutionException;

}
