package tw.gov.pcc.service.dto;

import java.util.Map;

public class EditVariablesDTO {
    private Map<String, Object> variable;

    private String processInstanceId;

    public Map<String, Object> getVariable() {
        return variable;
    }

    public void setVariable(Map<String, Object> variable) {
        this.variable = variable;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
