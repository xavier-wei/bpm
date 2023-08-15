package tw.gov.pcc.flowable.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class EndEventDTO implements Serializable {

    private String processInstanceId;
    private String token;
    private String formName;
    private String processStatus;

    public EndEventDTO(String processInstanceId, String token, String formName, String processStatus) {
        this.processInstanceId = processInstanceId;
        this.token = token;
        this.formName = formName;
        this.processStatus = processStatus;
    }

    public EndEventDTO() {
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndEventDTO that = (EndEventDTO) o;
        return Objects.equals(processInstanceId, that.processInstanceId) && Objects.equals(token, that.token) && Objects.equals(formName, that.formName) && Objects.equals(processStatus, that.processStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processInstanceId, token, formName, processStatus);
    }

    @Override
    public String toString() {
        return "EndEventDTO{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", token='" + token + '\'' +
        ", formName='" + formName + '\'' +
                ", processStatus='" + processStatus + '\'' +
                '}';
    }
}
