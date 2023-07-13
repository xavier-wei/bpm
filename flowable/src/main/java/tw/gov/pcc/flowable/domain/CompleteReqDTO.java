package tw.gov.pcc.flowable.domain;

import lombok.Data;

import java.util.HashMap;

@Data
public class CompleteReqDTO {
    private String processInstanceId;
    private String taskId;
    private HashMap<String, Object> variables;
}
