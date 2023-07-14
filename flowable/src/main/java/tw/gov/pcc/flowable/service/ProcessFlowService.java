package tw.gov.pcc.flowable.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import tw.gov.pcc.flowable.domain.ProcessRes;
import tw.gov.pcc.flowable.domain.TaskDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessFlowService {
    private final Integer[] SIGNATURE_STATUS = {1,0};
    public final String[] MESSAGE = {"完成","無此簽核任務"};
    private final RuntimeService runtimeService;

    private final TaskService taskService;
    private final HistoryService historyService;

    public ProcessFlowService(RuntimeService runtimeService, TaskService taskService, HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.historyService = historyService;
    }

    // 開啟流程，並且在applierConfirm為confirm的狀態，直接跳過申請者確認進到下個步驟，若為temp則需進行確認
    public String startProcess(String processKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        if (variables.get("applierConfirm").equals("confirm")) {
            Task task = taskService.createTaskQuery()
                    .taskCandidateOrAssigned((String) variables.get("applier"))
                    .processInstanceId(processInstance.getId()).singleResult();
            taskService.complete(task.getId());
        }
        return processInstance.getId();
    }

    // 完成task用，如為exclusive gateway前，則需一個Map傳入決策
    public ProcessRes completeTask(String processInstanceId, String taskId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId).singleResult();
        if (task.getId() != null) {
            taskService.complete(task.getId(), variables);
            return new ProcessRes(SIGNATURE_STATUS[0], MESSAGE[0]);
        }
        return new  ProcessRes(SIGNATURE_STATUS[1], MESSAGE[1]);
    }

    public ProcessRes completeTask(String processInstanceId, String taskId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId).singleResult();
        if (task.getId() != null) {
            taskService.complete(task.getId());
            return new ProcessRes(SIGNATURE_STATUS[0], MESSAGE[0]);
        }
        return new  ProcessRes(SIGNATURE_STATUS[1], MESSAGE[1]);
    }

    // 查詢個人所有任務
    public List<TaskDTO> queryProcessingTask(String id) {
        return taskService.createTaskQuery()
                .taskCandidateOrAssigned(id)
                .list()
                .stream()
                .map((Task task) -> new TaskDTO(task,isProcessComplete(task.getProcessInstanceId())))
                .collect(Collectors.toList());
    }

    public boolean isProcessComplete(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService
                                                          .createHistoricProcessInstanceQuery()
                                                          .processInstanceId(processInstanceId)
                                                          .singleResult();
        return (historicProcessInstance != null && historicProcessInstance.getEndTime() != null);
    }
}
