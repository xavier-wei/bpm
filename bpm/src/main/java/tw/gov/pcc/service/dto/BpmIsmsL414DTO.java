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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    private String formName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmIsmsL414DTO)) {
            return false;
        }

        BpmIsmsL414DTO bpmIsmsL414DTO = (BpmIsmsL414DTO) o;
        if (this.formId == null) {
            return false;
        }
        return Objects.equals(this.formId, bpmIsmsL414DTO.formId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.formId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EipBpmIsmsL414DTO{" +
            "formId='" + getFormId() + "'" +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", applyDate='" + getApplyDate() + "'" +
            ", filEmpid='" + getFilEmpid() + "'" +
            ", filName='" + getFilName() + "'" +
            ", filUnit='" + getFilUnit() + "'" +
            ", appEmpid='" + getAppEmpid() + "'" +
            ", appName='" + getAppName() + "'" +
            ", appUnit='" + getAppUnit() + "'" +
            ", isSubmit='" + getIsSubmit() + "'" +
            ", isEnable='" + getIsEnable() + "'" +
            ", enableTime='" + getEnableTime() + "'" +
            ", otherEnableTime='" + getOtherEnableTime() + "'" +
            ", selecteEdateType='" + getSelecteEdateType() + "'" +
            ", sdate='" + getSdate() + "'" +
            ", edate='" + getEdate() + "'" +
            ", othereEdate='" + getOthereEdate() + "'" +
            ", delEnableDate='" + getDelEnableDate() + "'" +
            ", sourceIp='" + getSourceIp() + "'" +
            ", targetIp='" + getTargetIp() + "'" +
            ", port='" + getPort() + "'" +
            ", isTcp='" + getIsTcp() + "'" +
            ", isUdp='" + getIsUdp() + "'" +
            ", instructions='" + getInstructions() + "'" +
            ", agreeType='" + getAgreeType() + "'" +
            ", scheduleDate='" + getScheduleDate() + "'" +
            ", settingReason='" + getSettingReason() + "'" +
            ", isExternalFirewall='" + getIsExternalFirewall() + "'" +
            ", isInternalFirewall='" + getIsInternalFirewall() + "'" +
            ", firewallContent='" + getFirewallContent() + "'" +
            ", finishDatetime='" + getFinishDatetime() + "'" +
            ", processInstanceStatus='" + getProcessInstanceStatus() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;

    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
