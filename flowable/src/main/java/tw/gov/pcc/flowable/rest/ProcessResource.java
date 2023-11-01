package tw.gov.pcc.flowable.rest;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.flowable.config.BpmSetting;
import tw.gov.pcc.flowable.domain.ProcessReq;
import tw.gov.pcc.flowable.domain.ProcessRes;
import tw.gov.pcc.flowable.service.EipCodeService;
import tw.gov.pcc.flowable.service.ProcessFlowService;
import tw.gov.pcc.flowable.service.dto.CompleteReqDTO;
import tw.gov.pcc.flowable.service.dto.EndEventDTO;
import tw.gov.pcc.flowable.service.dto.ProcessReqDTO;
import tw.gov.pcc.flowable.service.dto.TaskDTO;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessResource {
    private final ProcessFlowService service;

    public ProcessResource(ProcessFlowService service, EipCodeService eipCodeService) {
        this.service = service;
        this.eipCodeService = eipCodeService;
    }

    private final EipCodeService eipCodeService;

    @RequestMapping("/startProcess")
    public ResponseEntity<TaskDTO> startProcess(@Valid @RequestBody ProcessReqDTO processReqDTO) {

        ProcessReq processReq = new ProcessReq(processReqDTO);
        if (processReq.getProcessKey() != null) {

            TaskDTO taskDTO = service.startProcess(processReq.getProcessKey(), processReq.getVariables());

            return ResponseEntity.ok(taskDTO);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/queryProcessingTask")
    public ResponseEntity<List<TaskDTO>> queryProcessingTask(@RequestBody String id) {

        return ResponseEntity.ok().body(service.queryProcessingTask(id));
    }

    @PostMapping("/queryProcessingTaskNumbers")
    public ResponseEntity<Integer> queryProcessingTaskNumbers(@RequestBody String id) {
        return ResponseEntity.ok().body(service.queryProcessingTaskNumbers(id));
    }

    @PostMapping("/queryProcessingAllTask")
    public ResponseEntity<List<TaskDTO>> queryProcessingAllTask(@RequestBody String id) {
        return ResponseEntity.ok().body(service.queryProcessingAllTask(id));
    }

    @PostMapping("/getAllTask")
    public List<TaskDTO> getAllTask(@RequestBody String id) {
        return service.queryList(id);
    }

    @PostMapping("/completeTask")
    public ProcessRes completeTask(@Validated @RequestBody CompleteReqDTO completeReqDTO) {
        if (completeReqDTO.getVariables() != null && !completeReqDTO.getVariables().isEmpty()) {
            return service.completeTask(completeReqDTO.getProcessInstanceId(), completeReqDTO.getTaskId(), completeReqDTO.getVariables());
        }
        return service.completeTask(completeReqDTO.getProcessInstanceId(), completeReqDTO.getTaskId());
    }

    @PostMapping("/deleteProcess")
    public String deleteProcess(@RequestBody Map<String, String> deleteRequest) {
        String token = eipCodeService.findCodeName("BPM_TOKEN");
        if (token.equals(deleteRequest.get("token"))) {
            String processInstanceId = deleteRequest.get("processInstanceId");
            TaskDTO taskDTO = service.querySingleTask(processInstanceId);
            EndEventDTO endEventDTO = new EndEventDTO(processInstanceId, token, taskDTO.getFormName(), "2");
            service.deleteProcessInstance(processInstanceId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Gson gson = new Gson();
            HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(endEventDTO), headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> exchange = restTemplate.exchange(BpmSetting.getUrl() + "/bpm/api/process/receiveEndEvent", HttpMethod.DELETE, requestEntity, String.class);
            return "Delete process instance: " + exchange.getStatusCodeValue();
        } else {
            return "Bad request";
        }
    }

    @PostMapping("/getAllSubordinateTask")
    public List<TaskDTO> getAllSubordinateTask(@RequestBody List<String> ids) {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (String id : ids) {
            taskDTOS.addAll(service.queryProcessingTask(id));
        }
        return taskDTOS;
    }
    // 測試時期快速完成用API

    @RequestMapping("/completeTaskTest/{pId}/{tId}")
    public String completeTest(@PathVariable String pId, @PathVariable String tId) {
        service.completeTask(pId, tId);
        return "成功";
    }

}
