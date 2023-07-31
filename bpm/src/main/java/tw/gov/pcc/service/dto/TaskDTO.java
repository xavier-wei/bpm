package tw.gov.pcc.service.dto;

import lombok.Data;

@Data
public class TaskDTO {

    private String processInstanceId; // 流程ID
    private String taskId; // 任務ID
    private String taskName; // 任務名稱
    private String createdTime; // 任務創建時間

}
