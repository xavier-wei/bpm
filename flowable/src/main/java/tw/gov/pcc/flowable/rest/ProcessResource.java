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

    @RequestMapping("/queryProcessingAllTask")
    public List<TaskDTO> queryProcessingAllTask(@RequestBody String id) {
        return service.queryProcessingAllTask(id);
    }

    @RequestMapping("/getAllTask/{id}")
    public List<TaskDTO> getAllTask(@RequestBody String id) {
        return service.queryList(id);
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

    @RequestMapping("/startProcessL410")
    public TaskDTO startProcessL410() {


        HashMap<String, Object> variables = new HashMap<>();
        variables.put("isSubmit", "1");
        variables.put("chiefDecision", "1");
        variables.put("directorDecision", "1");
        variables.put("infoUndertakerDecision", "1");
        variables.put("otherInfoSys", "0");
        variables.put("publicWorkBidManageSys", "1");
        variables.put("disasterRecoveryPrjBudgetAndExecuteInfoSys", "1");
        variables.put("publicWorkCaseReviewInfoSys", "1");
        variables.put("engAndPrjConsultantManageInfoSys", "1");
        variables.put("govEProcurementSystem", "1");
        variables.put("pccAccount", null);
        variables.put("emailAccount", null);
        variables.put("meetingRoomManageSys", null);
        variables.put("govDocManageSys", null);
        variables.put("ADAccount", "1");
        variables.put("personnelAttendanceSys", "1");

        return service.startProcess("ProcessL410", variables);

    }

    @RequestMapping("/completeTaskTest/{pId}/{tId}")
    public String completeTest(@PathVariable String pId, @PathVariable String tId) {


        service.completeTask(pId, tId);

        return "成功";
    }


}
