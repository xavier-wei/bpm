package tw.gov.pcc.eip.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tw.gov.pcc.eip.framework.spring.support.DatabaseDrivenMessageSource;

/**
 * 單機 排程設定
 */
@EnableScheduling
@Configuration
public class StandAloneSchedulerConfig {

    @Autowired
    private DatabaseDrivenMessageSource messageSource;

    @Scheduled(fixedRate = 60000)
    public void reloadMessage() {
        messageSource.reload();
    }

}