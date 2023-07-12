package tw.gov.pcc.flowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessL414Service {

    private RuntimeService runtimeService;

    private TaskService taskService;

    public ProcessL414Service(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }


    public String startProcess( Map<String,Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ProcessL414",variables);
        if (variables.get("applierConfirm").equals("confirm")) {
            Task task = taskService.createTaskQuery()
                    .taskCandidateOrAssigned((String) variables.get("applier"))
                    .processInstanceId(processInstance.getId()).singleResult();
            taskService.complete(task.getId());
        }
        return processInstance.getId();
    }


    private String completeTask(String assignee) {
        return null;
    }
    public String completeTask(String assignee,Map<String,Object> variables) {
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(assignee).list();
        taskService.complete(list.get(0).getId(),variables);

        return null;
    }
}
