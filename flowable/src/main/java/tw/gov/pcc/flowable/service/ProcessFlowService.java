package tw.gov.pcc.flowable.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import tw.gov.pcc.flowable.domain.ProcessRes;
import tw.gov.pcc.flowable.domain.SupervisorSignerEnum;
import tw.gov.pcc.flowable.service.dto.TaskDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProcessFlowService {
    private static final Integer[] SIGNATURE_STATUS = {1, 0};
    private static final String[] MESSAGE = {"完成", "無此簽核任務"};
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
        // 查出所有加簽任務的主流程任務id
        List<String> mainProcessTaskIds = additionalProcesses
                .stream()
                .map(additionalProcess ->
                        (String) runtimeService
                                .getVariables(additionalProcess.getProcessInstanceId())
                                .get("mainProcessTaskId"))
                .collect(Collectors.toList());

        // 過濾掉加簽中之任務
        return taskService.createTaskQuery()
                .taskCandidateOrAssigned(id)
                .orderByTaskCreateTime()
                .desc()
                .list()
                .stream()
                .map(TaskDTO::new)
                .filter(taskDTO -> "Additional".equals(taskDTO.getFormName()) || !mainProcessTaskIds.contains(taskDTO.getTaskId())
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

        List<String> historicInstanceIds = new ArrayList<>();
        // 取得已處理過的任務實體，並將其Id放入processInstanceIds
        List<TaskDTO> histoicTaskDTO = getHistoricTaskDTO(id, historicInstanceIds);

        // 藉由已處理過任務實體ID找出自己已處理但是還未完成之任務
        List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(historicInstanceIds).list();
        List<String> processingInstanceIds = new ArrayList<>();
        // 因為複數處理人無法使用getAssignee直接取得，所以必須先至IdentityLink table取得後放入，再轉成taskDTO (處理中)
        List<TaskDTO> processingTaskDTO = tasks
                .stream()
                .map(task -> {
                    if (task.getAssignee() == null) {
                        List<String> candidateUsers = taskService.getIdentityLinksForTask(task.getId()).stream().map(IdentityLinkInfo::getUserId).collect(Collectors.toList());
                        task.setAssignee(String.join(",", candidateUsers));
                    }
                    processingInstanceIds.add(task.getProcessInstanceId());
                    return new TaskDTO(task);
                })
                .collect(Collectors.toList());
        // 如果任務仍未完成則把歷史任務中重複的過濾掉
        List<TaskDTO> newTaskDTO = histoicTaskDTO.stream().filter(taskDTO -> !processingInstanceIds.contains(taskDTO.getProcessInstanceId())).collect(Collectors.toList());
        newTaskDTO.addAll(processingTaskDTO);
        return newTaskDTO;
    }

    private List<TaskDTO> getHistoricTaskDTO(String id, List<String> historicInstanceIds) {

        // createHistoricTaskInstanceQuery 為查出自己已處理過之任務，並非整個流程實體已完成
        return Stream
                .concat(
                        historyService.createHistoricTaskInstanceQuery().taskCandidateUser(id).list().stream(),
                        historyService.createHistoricTaskInstanceQuery().taskAssignee(id).list().stream()
                )
                .filter(taskInstance -> {
                    if (!historicInstanceIds.contains(taskInstance.getProcessInstanceId())) {
                        historicInstanceIds.add(taskInstance.getProcessInstanceId());
                        return true;
                    }
                    return false;
                })
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }
}
