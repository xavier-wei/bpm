package tw.gov.pcc.flowable.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.flowable.domain.ProcessReq;
import tw.gov.pcc.flowable.domain.ProcessRes;
import tw.gov.pcc.flowable.service.EipCodeService;
import tw.gov.pcc.flowable.service.ProcessFlowService;
import tw.gov.pcc.flowable.service.dto.CompleteReqDTO;
import tw.gov.pcc.flowable.service.dto.EditVariablesDTO;
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
    public ResponseEntity<String> deleteProcess(@RequestBody Map<String, String> deleteRequest) {
        String deleteProcessLog = "--deleteProcess : ";
        // 刪除流程 區分兩種情況，bpm寫入失敗後刪除，或主動撤銷
        String token = eipCodeService.findCodeName("BPM_TOKEN");
        if (token.equals(deleteRequest.get("token"))) {
            String processInstanceId = deleteRequest.get("processInstanceId");
            try {
                service.deleteProcessInstance(processInstanceId);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "刪除失敗");
            }
            log.info(deleteProcessLog + "processInstance: {} 已刪除", processInstanceId);
            return ResponseEntity.ok().body("刪除完成");
        }
        log.warn(deleteProcessLog + "token不符或不存在，使用者可能使用API測試工具試圖刪除流程");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/getAllSubordinateTask")
    public List<TaskDTO> getAllSubordinateTask(@RequestBody List<String> ids) {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (String id : ids) {
            taskDTOS.addAll(service.queryProcessingTask(id));
        }
        return taskDTOS;
    }

    @RequestMapping("/editVariables")
    public ResponseEntity<String> editVariables(@RequestBody EditVariablesDTO editVariablesDTO) {

        service.changeVariables(editVariablesDTO.getVariable(), editVariablesDTO.getProcessInstanceId());
        return ResponseEntity.ok("OK");
    }


    // 測試時期快速完成用API
    @RequestMapping("/completeTaskTest/{pId}/{tId}")
    public String completeTest(@PathVariable String pId, @PathVariable String tId) {
        service.completeTask(pId, tId);
        return "成功";
    }


}
