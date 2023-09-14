package tw.gov.pcc.flowable.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;
import tw.gov.pcc.flowable.domain.ProcessEnum;
import tw.gov.pcc.flowable.domain.ProcessRes;
import tw.gov.pcc.flowable.domain.SupervisorSignerEnum;
import tw.gov.pcc.flowable.service.dto.TaskDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessFlowService {
    private final Integer[] SIGNATURE_STATUS = {1, 0};
    public final String[] MESSAGE = {"完成", "無此簽核任務"};
    private final RuntimeService runtimeService;

    private final TaskService taskService;
    private final HistoryService historyService;

    public ProcessFlowService(RuntimeService runtimeService, TaskService taskService, HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.historyService = historyService;
    }

    // 開啟流程，並且在isSubmit為"1"的狀態，直接跳過申請者確認進到下個步驟，若為temp則需進行確認
    public TaskDTO startProcess(String processKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        Task task = taskService.createTaskQuery()
                .taskCandidateOrAssigned((String) variables.get("applier"))
                .processInstanceId(processInstance.getId()).singleResult();
        TaskDTO taskDTO = new TaskDTO(task);

        if ("1".equals(variables.get("isSubmit"))) {
            taskService.complete(task.getId());
            String processInstanceId = processInstance.getId();
            jumpIfSupervisor(processKey, variables, processInstanceId);
        }

        return taskDTO;

    }

    // for completing task

    public ProcessRes completeTask(String processInstanceId, String taskId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId).singleResult();
        if (task.getId() != null) {
            taskService.complete(task.getId(), variables);
            return new ProcessRes(SIGNATURE_STATUS[0], MESSAGE[0]);
        }
        return new ProcessRes(SIGNATURE_STATUS[1], MESSAGE[1]);
    }

    public ProcessRes completeTask(String processInstanceId, String taskId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId).singleResult();
        if (task.getId() != null) {
            taskService.complete(task.getId());
            if ("applierConfirm".equals(task.getTaskDefinitionKey())) {
                Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
                String processKey = task.getProcessDefinitionId().split(":")[0];
                jumpIfSupervisor(processKey, variables, processInstanceId);

            }
            return new ProcessRes(SIGNATURE_STATUS[0], MESSAGE[0]);
        }
        return new ProcessRes(SIGNATURE_STATUS[1], MESSAGE[1]);
    }

    // query single task by processInstanceId

    public TaskDTO querySingleTask(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        return new TaskDTO(task);
    }
    // query task

    public List<TaskDTO> queryProcessingTask(String id) {

        // 查出所有加簽任務
        List<ProcessInstance> additionalProcesses = runtimeService.createProcessInstanceQuery().processDefinitionKey("AdditionalProcess").list();
        List<String> mainProcessInstanceIds = additionalProcesses
                .stream()
                .map(additionalProcess ->
                        (String) runtimeService
                                .getVariables(additionalProcess.getProcessInstanceId())
                                .get("mainProcessInstanceId"))
                .collect(Collectors.toList());

        return taskService.createTaskQuery()
                .taskCandidateOrAssigned(id)
                .orderByTaskCreateTime()
                .desc()
                .list()
                .stream()
                .map(TaskDTO::new)
                .filter(taskDTO -> "Additional".equals(taskDTO.getFormName()) || !mainProcessInstanceIds.contains(taskDTO.getProcessInstanceId())
                ).collect(Collectors.toList());
    }

    public List<TaskDTO> queryProcessingAllTask(String id) {
        return taskService.createTaskQuery()
                .taskCandidateOrAssigned(id)
                .orderByTaskCreateTime()
                .desc()
                .list()
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }
    // delete processInstance

    public void deleteProcessInstance(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, "delete");
    }

    private TaskDTO getTaskDTO(Task task) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        return new TaskDTO(task);
    }

    public boolean isProcessComplete(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        return (historicProcessInstance != null && historicProcessInstance.getEndTime() != null);
    }

    // query processInstance is complete return true

    public boolean isProcessComplete(String processInstanceId, String formName) {
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .processDefinitionKey(ProcessEnum.getProcessKeyByKey(formName))
                .singleResult();
        return (historicProcessInstance != null && historicProcessInstance.getEndTime() != null);
    }

    private void jumpIfSupervisor(String processKey, Map<String, Object> variables, String processInstanceId) {
        String[] supervisors = SupervisorSignerEnum.getSupervisors(processKey);
        if (supervisors != null) {
            Arrays.stream(supervisors)
                    .filter(supervisor -> "NO_SIGN".equals(variables.get(supervisor)))
                    .forEach(supervisor -> {
                        Task supervisorTask = taskService.createTaskQuery()
                                .taskCandidateOrAssigned((String) variables.get(supervisor))
                                .processInstanceId(processInstanceId).singleResult();

                        variables.put(supervisor + "Decision", 1);
                        taskService.complete(supervisorTask.getId(), variables);
                    });
        }
    }
    public List<TaskDTO> queryList(String id) {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().taskAssignee(id).list();
        taskService.createTaskQuery().taskAssignee(id).list().forEach(task -> taskDTOS.add(new TaskDTO(task)) );
        historicTaskInstances.forEach(historicTaskInstance -> taskDTOS.add(new TaskDTO(historicTaskInstance)));
        return taskDTOS;
    }

}
