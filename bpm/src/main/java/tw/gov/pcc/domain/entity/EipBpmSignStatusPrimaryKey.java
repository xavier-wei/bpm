package tw.gov.pcc.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EipBpmSignStatusPrimaryKey implements Serializable {

    private String formId;
    private String processInstanceId;
    private String taskId;
    private String signerId;

}
