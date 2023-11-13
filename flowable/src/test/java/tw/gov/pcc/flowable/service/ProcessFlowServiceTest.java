package tw.gov.pcc.flowable.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.flowable.service.dto.TaskDTO;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProcessFlowServiceTest {

    @Autowired
    private ProcessFlowService processFlowService;
    private TaskDTO taskDTO;
    // test ProcessFlowService startProcess
    @Test
    void startProcess() {
        String processKey = "ProcessL414";
        Map<String, Object> variables = new HashMap<>();
        variables.put("applier", "applier");
        variables.put("isSubmit", "0");

        this.taskDTO = processFlowService.startProcess(processKey, variables);
        assertNotNull(taskDTO);
    }

    @Test
    void completeTask() {

    }

    @Test
    void testCompleteTask() {
    }

    @Test
    void queryProcessingTask() {
    }

    @Test
    void queryProcessingTaskNumbers() {
    }

    @Test
    void queryProcessingAllTask() {
    }

    @Test
    void deleteProcessInstance() {
    }

    @Test
    void queryList() {
    }
}