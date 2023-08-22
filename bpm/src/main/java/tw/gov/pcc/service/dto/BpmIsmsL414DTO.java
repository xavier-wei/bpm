package tw.gov.pcc.service.dto;

import tw.gov.pcc.domain.BpmIsmsL414;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

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
    private Instant applyDate;

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
    private String otherEnableTime;

    @NotNull
    @Size(max = 1)
    private String selecteEdateType;

    private Instant sdate;

    private Instant edate;

    @Size(max = 100)
    private String othereEdate;

    private Instant delEnableDate;

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

    private Instant scheduleDate;

    @Size(max = 200)
    private String settingReason;

    @Size(max = 1)
    private String isExternalFirewall;

    @Size(max = 1)
    private String isInternalFirewall;

    @Size(max = 1000)
    private String firewallContent;

    private Instant finishDatetime;

    @Size(max = 1)
    private String processInstanceStatus;

    @Size(max = 20)
    private String updateUser;

    private Instant updateTime;

    @Size(max = 20)
    private String createUser;

    private Instant createTime;
    private String FormName;
    private String signUnit;
    private String signer;

    public BpmIsmsL414DTO() {
    }

    public BpmIsmsL414DTO(String formId, String signUnit, String signer, String processInstanceId, String taskId, String taskName, String decisionRole, Instant applyDate, String filEmpid, String filName, String filUnit, String appEmpid, String appName, String appUnit, String isSubmit, String isEnable, String enableTime, String otherEnableTime, String selecteEdateType, Instant sdate, Instant edate, String othereEdate, Instant delEnableDate, String sourceIp, String targetIp, String port, String isTcp, String isUdp, String instructions, String agreeType, Instant scheduleDate, String settingReason, String isExternalFirewall, String isInternalFirewall, String firewallContent, Instant finishDatetime, String processInstanceStatus, String updateUser, Instant updateTime, String createUser, Instant createTime, String formName) {
        this.formId = formId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.decisionRole = decisionRole;
        this.applyDate = applyDate;
        this.filEmpid = filEmpid;
        this.filName = filName;
        this.filUnit = filUnit;
        this.appEmpid = appEmpid;
        this.appName = appName;
        this.appUnit = appUnit;
        this.isSubmit = isSubmit;
        this.isEnable = isEnable;
        this.enableTime = enableTime;
        this.otherEnableTime = otherEnableTime;
        this.selecteEdateType = selecteEdateType;
        this.sdate = sdate;
        this.edate = edate;
        this.othereEdate = othereEdate;
        this.delEnableDate = delEnableDate;
        this.sourceIp = sourceIp;
        this.targetIp = targetIp;
        this.port = port;
        this.isTcp = isTcp;
        this.isUdp = isUdp;
        this.instructions = instructions;
        this.agreeType = agreeType;
        this.scheduleDate = scheduleDate;
        this.settingReason = settingReason;
        this.isExternalFirewall = isExternalFirewall;
        this.isInternalFirewall = isInternalFirewall;
        this.firewallContent = firewallContent;
        this.finishDatetime = finishDatetime;
        this.processInstanceStatus = processInstanceStatus;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.createTime = createTime;
        FormName = formName;
        this.signUnit = signUnit;
        this.signer = signer;
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

    public Instant getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Instant applyDate) {
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

    public Instant getSdate() {
        return sdate;
    }

    public void setSdate(Instant sdate) {
        this.sdate = sdate;
    }

    public Instant getEdate() {
        return edate;
    }

    public void setEdate(Instant edate) {
        this.edate = edate;
    }

    public String getOthereEdate() {
        return othereEdate;
    }

    public void setOthereEdate(String othereEdate) {
        this.othereEdate = othereEdate;
    }

    public Instant getDelEnableDate() {
        return delEnableDate;
    }

    public void setDelEnableDate(Instant delEnableDate) {
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

    public Instant getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Instant scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSettingReason() {
        return settingReason;
    }

    public void setSettingReason(String settingReason) {
        this.settingReason = settingReason;
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

    public Instant getFinishDatetime() {
        return finishDatetime;
    }

    public void setFinishDatetime(Instant finishDatetime) {
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

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String formName) {
        FormName = formName;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmIsmsL414DTO that = (BpmIsmsL414DTO) o;
        return Objects.equals(formId, that.formId) && Objects.equals(processInstanceId, that.processInstanceId) && Objects.equals(taskId, that.taskId) && Objects.equals(taskName, that.taskName) && Objects.equals(decisionRole, that.decisionRole) && Objects.equals(applyDate, that.applyDate) && Objects.equals(filEmpid, that.filEmpid) && Objects.equals(filName, that.filName) && Objects.equals(filUnit, that.filUnit) && Objects.equals(appEmpid, that.appEmpid) && Objects.equals(appName, that.appName) && Objects.equals(appUnit, that.appUnit) && Objects.equals(isSubmit, that.isSubmit) && Objects.equals(isEnable, that.isEnable) && Objects.equals(enableTime, that.enableTime) && Objects.equals(otherEnableTime, that.otherEnableTime) && Objects.equals(selecteEdateType, that.selecteEdateType) && Objects.equals(sdate, that.sdate) && Objects.equals(edate, that.edate) && Objects.equals(othereEdate, that.othereEdate) && Objects.equals(delEnableDate, that.delEnableDate) && Objects.equals(sourceIp, that.sourceIp) && Objects.equals(targetIp, that.targetIp) && Objects.equals(port, that.port) && Objects.equals(isTcp, that.isTcp) && Objects.equals(isUdp, that.isUdp) && Objects.equals(instructions, that.instructions) && Objects.equals(agreeType, that.agreeType) && Objects.equals(scheduleDate, that.scheduleDate) && Objects.equals(settingReason, that.settingReason) && Objects.equals(isExternalFirewall, that.isExternalFirewall) && Objects.equals(isInternalFirewall, that.isInternalFirewall) && Objects.equals(firewallContent, that.firewallContent) && Objects.equals(finishDatetime, that.finishDatetime) && Objects.equals(processInstanceStatus, that.processInstanceStatus) && Objects.equals(updateUser, that.updateUser) && Objects.equals(updateTime, that.updateTime) && Objects.equals(createUser, that.createUser) && Objects.equals(createTime, that.createTime) && Objects.equals(FormName, that.FormName) && Objects.equals(signUnit, that.signUnit) && Objects.equals(signer, that.signer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, processInstanceId, taskId, taskName, decisionRole, applyDate, filEmpid, filName, filUnit, appEmpid, appName, appUnit, isSubmit, isEnable, enableTime, otherEnableTime, selecteEdateType, sdate, edate, othereEdate, delEnableDate, sourceIp, targetIp, port, isTcp, isUdp, instructions, agreeType, scheduleDate, settingReason, isExternalFirewall, isInternalFirewall, firewallContent, finishDatetime, processInstanceStatus, updateUser, updateTime, createUser, createTime, FormName, signUnit, signer);
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
                ", settingReason='" + settingReason + '\'' +
                ", isExternalFirewall='" + isExternalFirewall + '\'' +
                ", isInternalFirewall='" + isInternalFirewall + '\'' +
                ", firewallContent='" + firewallContent + '\'' +
                ", finishDatetime=" + finishDatetime +
                ", processInstanceStatus='" + processInstanceStatus + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", FormName='" + FormName + '\'' +
                ", signUnit='" + signUnit + '\'' +
                ", signer='" + signer + '\'' +
                '}';
    }
}
