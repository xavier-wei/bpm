package tw.gov.pcc.service.dto;

import java.sql.Timestamp;

public class BpmIsmsAdditionalDTO {

    private String formId;//不傳資料
    private String processInstanceId;//不傳資料
    private String mainFormId; //發起加簽的流程FormId
    private String mainProcessInstanceId; // 發起加簽的流程id
    private String mainProcessTaskId; // 發起加簽的TASKID
    private String taskName; // 發起加簽的taskName
    private String requesterId;//ChiefTester
    private String requester;//ChiefTester
    private String additionalSignerId;//AdditionalSignerTester
    private String additionalSigner;//AdditionalSignerTester
    private String additionalSignReason;//加簽理由
    private String processInstanceStatus;//預設'0'
    private Timestamp createTime;//不傳資料

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
