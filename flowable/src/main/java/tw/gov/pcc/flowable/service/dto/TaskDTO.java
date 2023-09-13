package tw.gov.pcc.flowable.service.dto;

import lombok.Data;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import tw.gov.pcc.flowable.domain.ProcessEnum;
import tw.gov.pcc.flowable.utils.ConvertTimeZone;

@Data
public class TaskDTO {
    private String formName; // 表單名稱
    private String processInstanceId; // 流程ID
    private String taskId; // 任務ID
    private String taskName; // 任務名稱
    private String createdTime; // 任務創建時間
    public TaskDTO(Task task) {
        this.formName= ProcessEnum.getFormNameByProcessKey(task.getProcessDefinitionId().split(":")[0]);
        this.taskId = task.getId();
        this.taskName = task.getName();
        this.processInstanceId = task.getProcessInstanceId();
        this.createdTime = ConvertTimeZone.convertTimezoneTaipei(task.getCreateTime());
    }

    public TaskDTO(HistoricTaskInstance historicTaskInstance) {
        this.formName = ProcessEnum.getFormNameByProcessKey(historicTaskInstance.getProcessDefinitionId().split(":")[0]);
        this.taskId = historicTaskInstance.getParentTaskId();
        this.taskName = historicTaskInstance.getName();
        this.processInstanceId = historicTaskInstance.getProcessInstanceId();
        this.createdTime = ConvertTimeZone.convertTimezoneTaipei(historicTaskInstance.getCreateTime());
    }
}
