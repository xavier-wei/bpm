package tw.gov.pcc.service.dto;

import tw.gov.pcc.domain.BpmIsmsL414;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link BpmIsmsL414} entity.
 */

public class BpmIsmsL414DTO implements Serializable {

    @Size(max = 50)
    private String formId;

    @Size(max = 50)
    private String processInstanceId;
    @Size(max = 50)
    private String taskId;

    @Size(max = 50)
    private String taskName;

    @Size(max = 50)
    private String decisionRole;

    @NotNull
    private Timestamp applyDate;

    @NotNull
    @Size(max = 20)
    private String filEmpid;

    @NotNull
    @Size(max = 20)
    private String filName;

    @NotNull
    @Size(max = 100)
    private String filUnit;

    @NotNull
    @Size(max = 20)
    private String appEmpid;

    @NotNull
    @Size(max = 20)
    private String appName;

    @NotNull
    @Size(max = 100)
    private String appUnit;

    @NotNull
    @Size(max = 1)
    private String isSubmit;

    @NotNull
    @Size(max = 1)
    private String isEnable;

    @NotNull
    @Size(max = 1)
    private String enableTime;

    @Size(max = 100)
    private String workingTime;
    @Size(max = 100)
    private String otherEnableTime;

    @NotNull
    @Size(max = 1)
    private String selecteEdateType;

    private Timestamp sdate;

    private Timestamp edate;

    @Size(max = 100)
    private String othereEdate;

    private Timestamp delEnableDate;

    @Size(max = 100)
    private String sourceIp;

    @Size(max = 100)
    private String targetIp;

    @Size(max = 50)
    private String port;

    @Size(max = 1)
    private String isTcp;

    @Size(max = 1)
    private String isUdp;

    @Size(max = 1000)
    private String instructions;

    @Size(max = 1)
    private String agreeType;

    private Timestamp scheduleDate;

    @Size(max = 500)
    private String partialAgreeReason;
    @Size(max = 500)
    private String notAgreeReason;

    @Size(max = 1)
    private String isExternalFirewall;

    @Size(max = 1)
    private String isInternalFirewall;

    @Size(max = 1000)
    private String firewallContent;

    private Timestamp finishDatetime;

    @Size(max = 1)
    private String processInstanceStatus;

    @Size(max = 20)
    private String updateUser;

    private Timestamp updateTime;

    @Size(max = 20)
    private String createUser;

    private Timestamp createTime;
    private String formName;
    private String signUnit;
    private String signer;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDecisionRole() {
        return decisionRole;
    }

    public void setDecisionRole(String decisionRole) {
        this.decisionRole = decisionRole;
    }

    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }

    public String getFilEmpid() {
        return filEmpid;
    }

    public void setFilEmpid(String filEmpid) {
        this.filEmpid = filEmpid;
    }

    public String getFilName() {
        return filName;
    }

    public void setFilName(String filName) {
        this.filName = filName;
    }

    public String getFilUnit() {
        return filUnit;
    }

    public void setFilUnit(String filUnit) {
        this.filUnit = filUnit;
    }

    public String getAppEmpid() {
        return appEmpid;
    }

    public void setAppEmpid(String appEmpid) {
        this.appEmpid = appEmpid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUnit() {
        return appUnit;
    }

    public void setAppUnit(String appUnit) {
        this.appUnit = appUnit;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getOtherEnableTime() {
        return otherEnableTime;
    }

    public void setOtherEnableTime(String otherEnableTime) {
        this.otherEnableTime = otherEnableTime;
    }

    public String getSelecteEdateType() {
        return selecteEdateType;
    }

    public void setSelecteEdateType(String selecteEdateType) {
        this.selecteEdateType = selecteEdateType;
    }

    public Timestamp getSdate() {
        return sdate;
    }

    public void setSdate(Timestamp sdate) {
        this.sdate = sdate;
    }

    public Timestamp getEdate() {
        return edate;
    }

    public void setEdate(Timestamp edate) {
        this.edate = edate;
    }

    public String getOthereEdate() {
        return othereEdate;
    }

    public void setOthereEdate(String othereEdate) {
        this.othereEdate = othereEdate;
    }

    public Timestamp getDelEnableDate() {
        return delEnableDate;
    }

    public void setDelEnableDate(Timestamp delEnableDate) {
        this.delEnableDate = delEnableDate;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIsTcp() {
        return isTcp;
    }

    public void setIsTcp(String isTcp) {
        this.isTcp = isTcp;
    }

    public String getIsUdp() {
        return isUdp;
    }

    public void setIsUdp(String isUdp) {
        this.isUdp = isUdp;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAgreeType() {
        return agreeType;
    }

    public void setAgreeType(String agreeType) {
        this.agreeType = agreeType;
    }

    public Timestamp getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Timestamp scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getPartialAgreeReason() {
        return partialAgreeReason;
    }

    public void setPartialAgreeReason(String partialAgreeReason) {
        this.partialAgreeReason = partialAgreeReason;
    }

    public String getNotAgreeReason() {
        return notAgreeReason;
    }

    public void setNotAgreeReason(String notAgreeReason) {
        this.notAgreeReason = notAgreeReason;
    }

    public String getIsExternalFirewall() {
        return isExternalFirewall;
    }

    public void setIsExternalFirewall(String isExternalFirewall) {
        this.isExternalFirewall = isExternalFirewall;
    }

    public String getIsInternalFirewall() {
        return isInternalFirewall;
    }

    public void setIsInternalFirewall(String isInternalFirewall) {
        this.isInternalFirewall = isInternalFirewall;
    }

    public String getFirewallContent() {
        return firewallContent;
    }

    public void setFirewallContent(String firewallContent) {
        this.firewallContent = firewallContent;
    }

    public Timestamp getFinishDatetime() {
        return finishDatetime;
    }

    public void setFinishDatetime(Timestamp finishDatetime) {
        this.finishDatetime = finishDatetime;
    }

    public String getProcessInstanceStatus() {
        return processInstanceStatus;
    }

    public void setProcessInstanceStatus(String processInstanceStatus) {
        this.processInstanceStatus = processInstanceStatus;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    @Override
    public String toString() {
        return "BpmIsmsL414DTO{" +
            "formId='" + formId + '\'' +
            ", processInstanceId='" + processInstanceId + '\'' +
            ", taskId='" + taskId + '\'' +
            ", taskName='" + taskName + '\'' +
            ", decisionRole='" + decisionRole + '\'' +
            ", applyDate=" + applyDate +
            ", filEmpid='" + filEmpid + '\'' +
            ", filName='" + filName + '\'' +
            ", filUnit='" + filUnit + '\'' +
            ", appEmpid='" + appEmpid + '\'' +
            ", appName='" + appName + '\'' +
            ", appUnit='" + appUnit + '\'' +
            ", isSubmit='" + isSubmit + '\'' +
            ", isEnable='" + isEnable + '\'' +
            ", enableTime='" + enableTime + '\'' +
            ", workingTime='" + workingTime + '\'' +
            ", otherEnableTime='" + otherEnableTime + '\'' +
            ", selecteEdateType='" + selecteEdateType + '\'' +
            ", sdate=" + sdate +
            ", edate=" + edate +
            ", othereEdate='" + othereEdate + '\'' +
            ", delEnableDate=" + delEnableDate +
            ", sourceIp='" + sourceIp + '\'' +
            ", targetIp='" + targetIp + '\'' +
            ", port='" + port + '\'' +
            ", isTcp='" + isTcp + '\'' +
            ", isUdp='" + isUdp + '\'' +
            ", instructions='" + instructions + '\'' +
            ", agreeType='" + agreeType + '\'' +
            ", scheduleDate=" + scheduleDate +
            ", partialAgreeReason='" + partialAgreeReason + '\'' +
            ", notAgreeReason='" + notAgreeReason + '\'' +
            ", isExternalFirewall='" + isExternalFirewall + '\'' +
            ", isInternalFirewall='" + isInternalFirewall + '\'' +
            ", firewallContent='" + firewallContent + '\'' +
            ", finishDatetime=" + finishDatetime +
            ", processInstanceStatus='" + processInstanceStatus + '\'' +
            ", updateUser='" + updateUser + '\'' +
            ", updateTime=" + updateTime +
            ", createUser='" + createUser + '\'' +
            ", createTime=" + createTime +
            ", formName='" + formName + '\'' +
            ", signUnit='" + signUnit + '\'' +
            ", signer='" + signer + '\'' +
            '}';
    }
}
