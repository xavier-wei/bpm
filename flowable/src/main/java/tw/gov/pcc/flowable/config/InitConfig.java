package tw.gov.pcc.flowable.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.flowable.service.EipCodeService;

@Component
public class InitConfig implements CommandLineRunner {
    @Value("${bpm.url}")
    private String bpmUrl;
    private final EipCodeService eipCodeService;

    public InitConfig(EipCodeService eipCodeService) {
        this.eipCodeService = eipCodeService;
    }

    @Override
    public void run(String... args) {
        BpmSetting.setUrl(bpmUrl);
        BpmSetting.setToken(eipCodeService.findCodeName("BPM_TOKEN"));

    }
}
