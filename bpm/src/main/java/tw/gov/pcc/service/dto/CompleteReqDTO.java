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

    // 科長簽核：  "chiefDecision": "0"(不同意) || "1" (同意) || "2"(補件)
    // 主管簽核：  "directorDecision": "0"(不同意) || "1" (同意) || "2"(補件)
    // 簡任技正簽核： "seniorTechSpecialistDecision": "0"(不同意) || "1" (同意))
}
