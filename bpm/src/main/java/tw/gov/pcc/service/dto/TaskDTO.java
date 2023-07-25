package tw.gov.pcc.service.dto;

public class TaskDTO {

    private String processInstanceId; // 流程ID
    private String taskId; // 任務ID
    private String taskName; // 任務名稱
    private String createdTime; // 任務創建時間
    private boolean isProcessComplete; // 判斷此任務完成後流程是否結束

    public TaskDTO() {
    }

    public TaskDTO(String processInstanceId, String taskId, String taskName, String createdTime, boolean isProcessComplete) {
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.createdTime = createdTime;
        this.isProcessComplete = isProcessComplete;
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

    public boolean isProcessComplete() {
        return isProcessComplete;
    }

    public void setProcessComplete(boolean processComplete) {
        isProcessComplete = processComplete;
    }

}
