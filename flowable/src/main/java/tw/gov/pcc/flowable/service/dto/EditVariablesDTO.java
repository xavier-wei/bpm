package tw.gov.pcc.flowable.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EditVariablesDTO {
    private Map<String, Object> variable;

    private String processInstanceId;
}
