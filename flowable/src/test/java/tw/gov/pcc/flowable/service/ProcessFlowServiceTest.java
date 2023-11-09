package tw.gov.pcc.flowable.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tw.gov.pcc.flowable.service.dto.TaskDTO;

import java.util.Map;

@SpringBootTest
public class ProcessFlowServiceTest {

    private final ProcessFlowService processFlowService;

    public ProcessFlowServiceTest(ProcessFlowService processFlowService) {
        this.processFlowService = processFlowService;
    }

    @Test
    public void startProcess() {
        TaskDTO taskDTO = processFlowService.startProcess("ProcessL414", Map.of());
        System.out.println(taskDTO.getProcessInstanceId());
        assert taskDTO != null;
    }

}