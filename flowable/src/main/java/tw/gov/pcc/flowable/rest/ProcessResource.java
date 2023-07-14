package tw.gov.pcc.flowable.rest;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.gov.pcc.flowable.domain.*;
import tw.gov.pcc.flowable.service.ProcessFlowService;

import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessResource {
    private final ProcessFlowService service;

    public ProcessResource(ProcessFlowService service) {
        this.service = service;
    }


    @RequestMapping("/startProcess")
    public String startProcess(@RequestBody ProcessReqDTO processReqDTO) {

        ProcessReq processReq = new ProcessReq(processReqDTO);
        if (processReq.getProcessKey() != null) {
            return service.startProcess(processReq.getProcessKey(), processReq.getVariables());
        }

        return "Bad request";
    }


    @RequestMapping("/queryProcessingInstance")
    public List<TaskDTO> queryProcessingInstance(@RequestParam String id) {

        return service.queryProcessingTask(id);
    }

    @RequestMapping("/completeTask")
    public ProcessRes completeTask(@RequestBody CompleteReqDTO completeReqDTO) {
        if (completeReqDTO.getVariables() != null) {
            return service.completeTask(completeReqDTO.getProcessInstanceId(), completeReqDTO.getTaskId(),completeReqDTO.getVariables());
        }
        return service.completeTask(completeReqDTO.getProcessInstanceId(), completeReqDTO.getTaskId());
    }
}
