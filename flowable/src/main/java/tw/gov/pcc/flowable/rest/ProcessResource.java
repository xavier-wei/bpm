package tw.gov.pcc.flowable.rest;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.flowable.domain.ProcessReq;
import tw.gov.pcc.flowable.domain.ProcessRes;
import tw.gov.pcc.flowable.service.ProcessFlowService;
import tw.gov.pcc.flowable.service.dto.CompleteReqDTO;
import tw.gov.pcc.flowable.service.dto.EndEventDTO;
import tw.gov.pcc.flowable.service.dto.ProcessReqDTO;
import tw.gov.pcc.flowable.service.dto.TaskDTO;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessResource {
    private final ProcessFlowService service;

    public ProcessResource(ProcessFlowService service) {
        this.service = service;
    }

    @Value("${bpm.token}")
    private String TOKEN;

    @RequestMapping("/startProcess")
    public TaskDTO startProcess(@RequestBody ProcessReqDTO processReqDTO) {

        ProcessReq processReq = new ProcessReq(processReqDTO);
        if (processReq.getProcessKey() != null) {
            return service.startProcess(processReq.getProcessKey(), processReq.getVariables());
        }

        return null;
    }


    @RequestMapping("/queryProcessingTask")
    public List<TaskDTO> queryProcessingTask(@RequestBody String id) {

        return service.queryProcessingTask(id);
    }

    @RequestMapping("/completeTask")
    public ProcessRes completeTask(@Validated @RequestBody CompleteReqDTO completeReqDTO) {
        if (completeReqDTO.getVariables() != null && !completeReqDTO.getVariables().isEmpty()) {
            return service.completeTask(completeReqDTO.getProcessInstanceId(), completeReqDTO.getTaskId(), completeReqDTO.getVariables());
        }
        return service.completeTask(completeReqDTO.getProcessInstanceId(), completeReqDTO.getTaskId());
    }

    @RequestMapping("/deleteProcess")
    public String deleteProcess(@RequestBody HashMap<String, String> deleteRequest) {
        if (TOKEN.equals(deleteRequest.get("token"))) {
            String processInstanceId = deleteRequest.get("processInstanceId");
            System.out.println(processInstanceId);
            TaskDTO taskDTO = service.querySingleTask(processInstanceId);
            EndEventDTO endEventDTO = new EndEventDTO(processInstanceId, TOKEN, taskDTO.getFormName(), "2");
            service.deleteProcessInstance(processInstanceId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Gson gson = new Gson();
            HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(endEventDTO), headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/bpm/api/process/receiveEndEvent", HttpMethod.POST, requestEntity, String.class);
            return "Delete process instance: " + exchange.getStatusCodeValue();
        } else {
            return "Bad request";
        }
    }

    // todo 測試完成用api，上線需刪除
    @RequestMapping("/completeTaskTest/{pId}/{tId}")
    public String completeTest(@PathVariable String pId, @PathVariable String tId) {


        service.completeTask(pId, tId);

        return "成功";
    }
}
