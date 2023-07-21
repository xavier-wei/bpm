package tw.gov.pcc.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EipBpmIsmsL414.
 */
@Entity
@Table(name = "EIP_BPM_ISMS_L414")
public class EipBpmIsmsL414 implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    @Column(name = "form_id", length = 50, nullable = false)
    private String formId;

    @NotNull
    @Size(max = 50)
    @Column(name = "process_instance_id", length = 50, nullable = false)
    private String processInstanceId;

    @NotNull
    @Column(name = "apply_date", nullable = false)
    private Instant applyDate;

    @NotNull
    @Size(max = 20)
    @Column(name = "fil_empid", length = 20, nullable = false)
    private String filEmpid;

    @NotNull
    @Size(max = 20)
    @Column(name = "fil_name", length = 20, nullable = false)
    private String filName;

    @NotNull
    @Size(max = 100)
    @Column(name = "fil_unit", length = 100, nullable = false)
    private String filUnit;

    @NotNull
    @Size(max = 20)
    @Column(name = "app_empid", length = 20, nullable = false)
    private String appEmpid;

    @NotNull
    @Size(max = 20)
    @Column(name = "app_name", length = 20, nullable = false)
    private String appName;

    @NotNull
    @Size(max = 100)
    @Column(name = "app_unit", length = 100, nullable = false)
    private String appUnit;

    @NotNull
    @Size(max = 1)
    @Column(name = "is_submit", length = 1, nullable = false)
    private String isSubmit;

    @NotNull
    @Size(max = 1)
    @Column(name = "is_enable", length = 1, nullable = false)
    private String isEnable;

    @NotNull
    @Size(max = 1)
    @Column(name = "enable_time", length = 1, nullable = false)
    private String enableTime;

    @Size(max = 100)
    @Column(name = "other_enable_time", length = 100)
    private String otherEnableTime;

    @NotNull
    @Size(max = 1)
    @Column(name = "selecte_edate_type", length = 1, nullable = false)
    private String selecteEdateType;

    @Column(name = "sdate")
    private Instant sdate;

    @Column(name = "edate")
    private Instant edate;

    @Size(max = 100)
    @Column(name = "othere_edate", length = 100)
    private String othereEdate;

    @Column(name = "del_enable_date")
    private Instant delEnableDate;

    @Size(max = 100)
    @Column(name = "source_ip", length = 100)
    private String sourceIp;

    @Size(max = 100)
    @Column(name = "target_ip", length = 100)
    private String targetIp;

    @Size(max = 50)
    @Column(name = "port", length = 50)
    private String port;

    @Size(max = 1)
    @Column(name = "is_tcp", length = 1)
    private String isTcp;

    @Size(max = 1)
    @Column(name = "is_udp", length = 1)
    private String isUdp;

    @Size(max = 1000)
    @Column(name = "instructions", length = 1000)
    private String instructions;

    @Size(max = 1)
    @Column(name = "agree_type", length = 1)
    private String agreeType;

    @Column(name = "schedule_date")
    private Instant scheduleDate;

    @Size(max = 200)
    @Column(name = "setting_reason", length = 200)
    private String settingReason;

    @Size(max = 1)
    @Column(name = "is_external_firewall", length = 1)
    private String isExternalFirewall;

    @Size(max = 1)
    @Column(name = "is_internal_firewall", length = 1)
    private String isInternalFirewall;

    @Size(max = 1000)
    @Column(name = "firewall_content", length = 1000)
    private String firewallContent;

    @Column(name = "finish_datetime")
    private Instant finishDatetime;

    @NotNull
    @Size(max = 1)
    @Column(name = "process_instance_status", length = 1, nullable = false)
    private String processInstanceStatus;

    @Size(max = 20)
    @Column(name = "update_user", length = 20)
    private String updateUser;

    @Column(name = "update_time")
    private Instant updateTime;

    @NotNull
    @Size(max = 20)
    @Column(name = "create_user", length = 20, nullable = false)
    private String createUser;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getFormId() {
        return this.formId;
    }

    public EipBpmIsmsL414 formId(String formId) {
        this.setFormId(formId);
        return this;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public EipBpmIsmsL414 processInstanceId(String processInstanceId) {
        this.setProcessInstanceId(processInstanceId);
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Instant getApplyDate() {
        return this.applyDate;
    }

    public EipBpmIsmsL414 applyDate(Instant applyDate) {
        this.setApplyDate(applyDate);
        return this;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public String getFilEmpid() {
        return this.filEmpid;
    }

    public EipBpmIsmsL414 filEmpid(String filEmpid) {
        this.setFilEmpid(filEmpid);
        return this;
    }

    public void setFilEmpid(String filEmpid) {
        this.filEmpid = filEmpid;
    }

    public String getFilName() {
        return this.filName;
    }

    public EipBpmIsmsL414 filName(String filName) {
        this.setFilName(filName);
        return this;
    }

    public void setFilName(String filName) {
        this.filName = filName;
    }

    public String getFilUnit() {
        return this.filUnit;
    }

    public EipBpmIsmsL414 filUnit(String filUnit) {
        this.setFilUnit(filUnit);
        return this;
    }

    public void setFilUnit(String filUnit) {
        this.filUnit = filUnit;
    }

    public String getAppEmpid() {
        return this.appEmpid;
    }

    public EipBpmIsmsL414 appEmpid(String appEmpid) {
        this.setAppEmpid(appEmpid);
        return this;
    }

    public void setAppEmpid(String appEmpid) {
        this.appEmpid = appEmpid;
    }

    public String getAppName() {
        return this.appName;
    }

    public EipBpmIsmsL414 appName(String appName) {
        this.setAppName(appName);
        return this;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUnit() {
        return this.appUnit;
    }

    public EipBpmIsmsL414 appUnit(String appUnit) {
        this.setAppUnit(appUnit);
        return this;
    }

    public void setAppUnit(String appUnit) {
        this.appUnit = appUnit;
    }

    public String getIsSubmit() {
        return this.isSubmit;
    }

    public EipBpmIsmsL414 isSubmit(String isSubmit) {
        this.setIsSubmit(isSubmit);
        return this;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getIsEnable() {
        return this.isEnable;
    }

    public EipBpmIsmsL414 isEnable(String isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getEnableTime() {
        return this.enableTime;
    }

    public EipBpmIsmsL414 enableTime(String enableTime) {
        this.setEnableTime(enableTime);
        return this;
    }

    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    public String getOtherEnableTime() {
        return this.otherEnableTime;
    }

    public EipBpmIsmsL414 otherEnableTime(String otherEnableTime) {
        this.setOtherEnableTime(otherEnableTime);
        return this;
    }

    public void setOtherEnableTime(String otherEnableTime) {
        this.otherEnableTime = otherEnableTime;
    }

    public String getSelecteEdateType() {
        return this.selecteEdateType;
    }

    public EipBpmIsmsL414 selecteEdateType(String selecteEdateType) {
        this.setSelecteEdateType(selecteEdateType);
        return this;
    }

    public void setSelecteEdateType(String selecteEdateType) {
        this.selecteEdateType = selecteEdateType;
    }

    public Instant getSdate() {
        return this.sdate;
    }

    public EipBpmIsmsL414 sdate(Instant sdate) {
        this.setSdate(sdate);
        return this;
    }

    public void setSdate(Instant sdate) {
        this.sdate = sdate;
    }

    public Instant getEdate() {
        return this.edate;
    }

    public EipBpmIsmsL414 edate(Instant edate) {
        this.setEdate(edate);
        return this;
    }

    public void setEdate(Instant edate) {
        this.edate = edate;
    }

    public String getOthereEdate() {
        return this.othereEdate;
    }

    public EipBpmIsmsL414 othereEdate(String othereEdate) {
        this.setOthereEdate(othereEdate);
        return this;
    }

    public void setOthereEdate(String othereEdate) {
        this.othereEdate = othereEdate;
    }

    public Instant getDelEnableDate() {
        return this.delEnableDate;
    }

    public EipBpmIsmsL414 delEnableDate(Instant delEnableDate) {
        this.setDelEnableDate(delEnableDate);
        return this;
    }

    public void setDelEnableDate(Instant delEnableDate) {
        this.delEnableDate = delEnableDate;
    }

    public String getSourceIp() {
        return this.sourceIp;
    }

    public EipBpmIsmsL414 sourceIp(String sourceIp) {
        this.setSourceIp(sourceIp);
        return this;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getTargetIp() {
        return this.targetIp;
    }

    public EipBpmIsmsL414 targetIp(String targetIp) {
        this.setTargetIp(targetIp);
        return this;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public String getPort() {
        return this.port;
    }

    public EipBpmIsmsL414 port(String port) {
        this.setPort(port);
        return this;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIsTcp() {
        return this.isTcp;
    }

    public EipBpmIsmsL414 isTcp(String isTcp) {
        this.setIsTcp(isTcp);
        return this;
    }

    public void setIsTcp(String isTcp) {
        this.isTcp = isTcp;
    }

    public String getIsUdp() {
        return this.isUdp;
    }

    public EipBpmIsmsL414 isUdp(String isUdp) {
        this.setIsUdp(isUdp);
        return this;
    }

    public void setIsUdp(String isUdp) {
        this.isUdp = isUdp;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public EipBpmIsmsL414 instructions(String instructions) {
        this.setInstructions(instructions);
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAgreeType() {
        return this.agreeType;
    }

    public EipBpmIsmsL414 agreeType(String agreeType) {
        this.setAgreeType(agreeType);
        return this;
    }

    public void setAgreeType(String agreeType) {
        this.agreeType = agreeType;
    }

    public Instant getScheduleDate() {
        return this.scheduleDate;
    }

    public EipBpmIsmsL414 scheduleDate(Instant scheduleDate) {
        this.setScheduleDate(scheduleDate);
        return this;
    }

    public void setScheduleDate(Instant scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSettingReason() {
        return this.settingReason;
    }

    public EipBpmIsmsL414 settingReason(String settingReason) {
        this.setSettingReason(settingReason);
        return this;
    }

    public void setSettingReason(String settingReason) {
        this.settingReason = settingReason;
    }

    public String getIsExternalFirewall() {
        return this.isExternalFirewall;
    }

    public EipBpmIsmsL414 isExternalFirewall(String isExternalFirewall) {
        this.setIsExternalFirewall(isExternalFirewall);
        return this;
    }

    public void setIsExternalFirewall(String isExternalFirewall) {
        this.isExternalFirewall = isExternalFirewall;
    }

    public String getIsInternalFirewall() {
        return this.isInternalFirewall;
    }

    public EipBpmIsmsL414 isInternalFirewall(String isInternalFirewall) {
        this.setIsInternalFirewall(isInternalFirewall);
        return this;
    }

    public void setIsInternalFirewall(String isInternalFirewall) {
        this.isInternalFirewall = isInternalFirewall;
    }

    public String getFirewallContent() {
        return this.firewallContent;
    }

    public EipBpmIsmsL414 firewallContent(String firewallContent) {
        this.setFirewallContent(firewallContent);
        return this;
    }

    public void setFirewallContent(String firewallContent) {
        this.firewallContent = firewallContent;
    }

    public Instant getFinishDatetime() {
        return this.finishDatetime;
    }

    public EipBpmIsmsL414 finishDatetime(Instant finishDatetime) {
        this.setFinishDatetime(finishDatetime);
        return this;
    }

    public void setFinishDatetime(Instant finishDatetime) {
        this.finishDatetime = finishDatetime;
    }

    public String getProcessInstanceStatus() {
        return this.processInstanceStatus;
    }

    public EipBpmIsmsL414 processInstanceStatus(String processInstanceStatus) {
        this.setProcessInstanceStatus(processInstanceStatus);
        return this;
    }

    public void setProcessInstanceStatus(String processInstanceStatus) {
        this.processInstanceStatus = processInstanceStatus;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public EipBpmIsmsL414 updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public EipBpmIsmsL414 updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public EipBpmIsmsL414 createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public EipBpmIsmsL414 createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EipBpmIsmsL414)) {
            return false;
        }
        return formId != null && formId.equals(((EipBpmIsmsL414) o).formId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EipBpmIsmsL414{" +
            "formId=" + getFormId() +
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
}
