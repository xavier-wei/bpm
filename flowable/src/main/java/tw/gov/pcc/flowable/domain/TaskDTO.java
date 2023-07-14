package tw.gov.pcc.flowable.domain;

import lombok.Data;
import org.flowable.task.api.Task;
import tw.gov.pcc.flowable.utils.CovertTimeZone;

@Data
public class TaskDTO {

    private String processInstanceId; // 流程ID
    private String taskId; // 任務ID
    private String taskName; // 任務名稱
    private String createdTime; // 任務創建時間
    private boolean isProcessComplete; // 判斷此任務完成後流程是否結束
    public TaskDTO(Task task,Boolean isProcessComplete) {
        this.taskId = task.getId();
        this.taskName = task.getName();
        this.processInstanceId = task.getProcessInstanceId();
        this.createdTime = CovertTimeZone.convertTimezoneTaipei(task.getCreateTime());
        this.isProcessComplete = isProcessComplete;
    }

}
