package tw.gov.pcc.flowable.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
@Slf4j
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

    // 開啟流程，並且在isSubmit為"1"的狀態，直接跳過申請者確認進到下個步驟，若為0或其他任何值則需進行確認
    public TaskDTO startProcess(String processKey, Map<String, Object> variables) {
        String startProcessLog = "--startProcess : {} ";

        // 流程啟動
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        // 取得申請者確認之task，並轉成taskDTO，若未來不需要申請者確認的欄位，則這邊邏輯必須改寫
        Task task = taskService.createTaskQuery()
                .taskCandidateOrAssigned((String) variables.get("applier"))
                .processInstanceId(processInstance.getId()).singleResult();
        TaskDTO taskDTO = new TaskDTO(task);
        log.info(startProcessLog, processInstance.getProcessInstanceId() + " started");
        /* 如果isSubmit為1，則申請者不再確認，接著第一審核者及第二審核者若為NO_SIGN，亦不需簽核
         * 第一審核者、第二審核者跳過規則：
         * 1. 連兩個跳過 OK
         * 2. 跳過第一審核者，僅須第二審核者簽核 OK
         * 3. 第一審核者須簽核，但無第二審核者 不OK <== 因為邏輯為連續判斷，所以遇此情況，請直接把第一審核者放入第二審核者同，第 2.
         */
        if ("1".equals(variables.get("isSubmit"))) {
            taskService.complete(task.getId());
            String processInstanceId = processInstance.getId();
            skipIfSupervisorNoSign(processKey, variables, processInstanceId);
        }

        return taskDTO;

    }

    // for completing task
    public ProcessRes completeTask(String processInstanceId, String taskId, Map<String, Object> variables) {
        String completeTaskLog = "--completeTask : {} ";
        // 先確認須完成的task是否存在
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId).singleResult();
        if (task.getId() != null) {
            taskService.complete(task.getId(), variables);
            // 辨識是否為申請人確認
            applierConfirmAndSkipSign(processInstanceId, task);
            log.info(completeTaskLog, task.getId() + " complete");
            return new ProcessRes(SIGNATURE_STATUS[0], MESSAGE[0]);
        }
        log.info(completeTaskLog, "Task not found");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "任務或流程不存在，可能已由其他處理人處理完成，請F5刷新");
    }


    // for completing task
    public ProcessRes completeTask(String processInstanceId, String taskId) {
        String completeTaskLog = "--completeTask : {} ";
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId).singleResult();
        if (task.getId() != null) {
            taskService.complete(task.getId());
            // 辨識是否為申請人確認
            applierConfirmAndSkipSign(processInstanceId, task);
            log.info(completeTaskLog, task.getId() + " complete");
            return new ProcessRes(SIGNATURE_STATUS[0], MESSAGE[0]);
        }
        log.info(completeTaskLog, "Task not found");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "任務或流程不存在，可能已由其他處理人處理完成，請F5刷新");
    }

    /*
     * 如果是暫存後確認則必須設定整個流程的variables並判斷是否需要跳過第一、第二簽核者
     */
    private void applierConfirmAndSkipSign(String processInstanceId, Task task) {
        if ("applierConfirm".equals(task.getTaskDefinitionKey())) {
            Map<String, Object> variables2 = runtimeService.getVariables(processInstanceId);
            String processKey = task.getProcessDefinitionId().split(":")[0];
            skipIfSupervisorNoSign(processKey, variables2, processInstanceId);
        }
    }

    // query自己未處理的任務(但扣除加簽中的)
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

    public Integer queryProcessingTaskNumbers(String id) {
        return queryProcessingTask(id).size();
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

    /*
     * 第一審核者、第二審核者跳過規則：
     * 1. 連兩個跳過 OK
     * 2. 跳過第一審核者，僅須第二審核者簽核 OK
     * 3. 第一審核者須簽核，但無第二審核者 不OK <== 因為邏輯為連續判斷，所以遇此情況，請直接把第一審核者放入第二審核者同，第 2.
     */
    private void skipIfSupervisorNoSign(String processKey, Map<String, Object> variables, String processInstanceId) {
        String[] supervisors = SupervisorSignerEnum.getSupervisors(processKey);
        if (supervisors.length > 0) {
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

    /*
     * 查詢自己已處理過的所有任務，但不包含處理中的任務
     */
    public List<TaskDTO> queryList(String id) {

        List<String> historicInstanceIds = new ArrayList<>();
        // 取得已處理過的任務實體，並將其Id放入processInstanceIds
        List<TaskDTO> histoicTaskDTO = getHistoricTaskDTO(id, historicInstanceIds);
        if (!historicInstanceIds.isEmpty()) {
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
        return List.of();
    }

    private List<TaskDTO> getHistoricTaskDTO(String id, List<String> historicInstanceIds) {

        // createHistoricTaskInstanceQuery 為查出自己已處理過之任務，並非整個流程實體已完成
        return Stream.concat
                        (
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
