package tw.gov.pcc.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tw.gov.pcc.flowable.config.BpmSetting;
import tw.gov.pcc.flowable.config.BpmUrlBean;

@SpringBootApplication
public class FlowableApplication {

    public FlowableApplication(BpmUrlBean bpmUrlBean) {
        BpmSetting.url = bpmUrlBean.getUrl();
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

}
