package tw.gov.pcc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tw.gov.pcc.web.rest.io.DownloadableResourceMessageConverter;


@Configuration
public class CompostWebConfiguration {

    @Bean
    public DownloadableResourceMessageConverter downloadableResourceMessageConverter() {
        return new DownloadableResourceMessageConverter();
    }

}
