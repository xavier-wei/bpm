package tw.gov.pcc.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "BPM_ISMS_ADDITIONAL")
public class BpmIsmsAdditional implements Serializable {

    @Id
    @Column(name = "FORM_ID", length = 50, nullable = false)
    private String formId;

    @Column(name = "PROCESS_INSTANCE_ID", length = 50, nullable = false)
    private String processInstanceId;

    @Column(name = "MAIN_FORM_ID", length = 50, nullable = false)
    private String mainFormId;

    @Column(name = "MAIN_PROCESS_INSTANCE_ID", length = 50, nullable = false)
    private String mainProcessInstanceId;

    @Column(name = "MAIN_PROCESS_TASK_ID", length = 50, nullable = false)
    private String mainProcessTaskId;

    @Column(name = "REQUESTER_ID", length = 50, nullable = false)
    private String requesterId;

    @Column(name = "REQUESTER", length = 50, nullable = false)
    private String requester;

    @Column(name = "ADDITIONAL_SIGNER_ID", length = 50, nullable = false)
    private String additionalSignerId;

    @Column(name = "ADDITIONAL_SIGNER", length = 50, nullable = false)
    private String additionalSigner;

    @Column(name = "PROCESS_INSTANCE_STATUS")
    private String processInstanceStatus;

    @Column(name = "ADDITIONAL_SIGN_REASON", length = 100, nullable = false)
    private String additionalSignReason;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmIsmsAdditional that = (BpmIsmsAdditional) o;
        return Objects.equals(formId, that.formId) && Objects.equals(processInstanceId, that.processInstanceId) && Objects.equals(mainFormId, that.mainFormId) && Objects.equals(mainProcessInstanceId, that.mainProcessInstanceId) && Objects.equals(mainProcessTaskId, that.mainProcessTaskId) && Objects.equals(requesterId, that.requesterId) && Objects.equals(requester, that.requester) && Objects.equals(additionalSignerId, that.additionalSignerId) && Objects.equals(additionalSigner, that.additionalSigner) && Objects.equals(processInstanceStatus, that.processInstanceStatus) && Objects.equals(additionalSignReason, that.additionalSignReason) && Objects.equals(createTime, that.createTime) && Objects.equals(taskName, that.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, processInstanceId, mainFormId, mainProcessInstanceId, mainProcessTaskId, requesterId, requester, additionalSignerId, additionalSigner, processInstanceStatus, additionalSignReason, createTime, taskName);
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getMainFormId() {
        return mainFormId;
    }

    public void setMainFormId(String mainFormId) {
        this.mainFormId = mainFormId;
    }

    public String getMainProcessInstanceId() {
        return mainProcessInstanceId;
    }

    public void setMainProcessInstanceId(String mainProcessInstanceId) {
        this.mainProcessInstanceId = mainProcessInstanceId;
    }

    public String getMainProcessTaskId() {
        return mainProcessTaskId;
    }

    public void setMainProcessTaskId(String mainProcessTaskId) {
        this.mainProcessTaskId = mainProcessTaskId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getAdditionalSignerId() {
        return additionalSignerId;
    }

    public void setAdditionalSignerId(String additionalSignerId) {
        this.additionalSignerId = additionalSignerId;
    }

    public String getAdditionalSigner() {
        return additionalSigner;
    }

    public void setAdditionalSigner(String additionalSigner) {
        this.additionalSigner = additionalSigner;
    }

    public String getProcessInstanceStatus() {
        return processInstanceStatus;
    }

    public void setProcessInstanceStatus(String processInstanceStatus) {
        this.processInstanceStatus = processInstanceStatus;
    }

    public String getAdditionalSignReason() {
        return additionalSignReason;
    }

    public void setAdditionalSignReason(String additionalSignReason) {
        this.additionalSignReason = additionalSignReason;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
