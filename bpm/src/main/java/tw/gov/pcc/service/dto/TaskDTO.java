package tw.gov.pcc.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class TaskDTO implements Serializable {
    private String formName;
    private String processInstanceId; // 流程ID
    private String taskId; // 任務ID
    private String taskName; // 任務名稱
    private String createdTime; // 任務創建時間
    private String pendingUserId;


    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }



    public TaskDTO(String formName, String processInstanceId, String taskId, String taskName, String createdTime, String pendingUserId) {
        this.formName = formName;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.createdTime = createdTime;
        this.pendingUserId = pendingUserId;
    }

    public TaskDTO() {
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "formName='" + formName + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", pendingUserId='" + pendingUserId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDTO taskDTO = (TaskDTO) o;
        return Objects.equals(formName, taskDTO.formName) && Objects.equals(processInstanceId, taskDTO.processInstanceId) && Objects.equals(taskId, taskDTO.taskId) && Objects.equals(taskName, taskDTO.taskName) && Objects.equals(createdTime, taskDTO.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formName, processInstanceId, taskId, taskName, createdTime);
    }

}
