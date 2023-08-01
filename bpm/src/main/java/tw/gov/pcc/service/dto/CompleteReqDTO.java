package tw.gov.pcc.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Data
public class CompleteReqDTO {
    @NotNull
    private String signer;

    @NotNull
    private String signerId;

    @NotNull
    private String signUnit;

    @NotNull
    private String processInstanceId;

    @NotNull
    private String taskId;

    private HashMap<String, Object> variables;

}
