package tw.gov.pcc.service.dto;

public class ProcessInstanceIdRequestDTO {
    private String processInstanceId;
    private String key;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ProcessInstanceIdRequestDTO{" +
            "processInstanceId='" + processInstanceId + '\'' +
            ", key='" + key + '\'' +
            '}';
    }
}


