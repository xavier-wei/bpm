package tw.gov.pcc.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link tw.gov.pcc.domain.BpmL410ApplyManage} entity.
 */
public class BpmL410ApplyManageDTO implements Serializable {

    @NotNull
    @Size(max = 50)
    private String systemApply;

    @Size(max = 40)
    private String systemApplyName;
    private String checkbox;

    private String sys;

    @Size(max = 50)
    private String systemApplyInput;

    @Size(max = 50)
    private String sysChange;

    @Size(max = 20)
    private String emailApply1;

    @Size(max = 1)
    private String emailApply2;

    @Size(max = 1)
    private String isUnitAdm;

    @Size(max = 1)
    private String isUnitDataMgr;

    @Size(max = 1)
    private String isWebSiteOther;
    @Size(max = 30)
    private String otherReason;
    @Size(max = 7)
    private String admUnit;

    @Size(max = 10)
    private String admStatus;

    private Instant admEnableDate;

    @Size(max = 20)
    private String admName;

    @Size(max = 20)
    private String otherSys;

    @Size(max = 20)
    private String otherSysAccount;

    @Size(max = 1)
    private String isColon;
    @Size(max = 1)
    private String applyVersion;
    @Size(max = 1)
    private String permissionsVersion;

    @NotNull
    @Size(max = 20)
    private String createUser;

    @NotNull
    private Instant createTime;


    public String getSystemApply() {
        return systemApply;
    }

    public void setSystemApply(String systemApply) {
        this.systemApply = systemApply;
    }

    public String getSystemApplyName() {
        return systemApplyName;
    }

    public void setSystemApplyName(String systemApplyName) {
        this.systemApplyName = systemApplyName;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSystemApplyInput() {
        return systemApplyInput;
    }

    public void setSystemApplyInput(String systemApplyInput) {
        this.systemApplyInput = systemApplyInput;
    }

    public String getSysChange() {
        return sysChange;
    }

    public void setSysChange(String sysChange) {
        this.sysChange = sysChange;
    }

    public String getEmailApply1() {
        return emailApply1;
    }

    public void setEmailApply1(String emailApply1) {
        this.emailApply1 = emailApply1;
    }

    public String getEmailApply2() {
        return emailApply2;
    }

    public void setEmailApply2(String emailApply2) {
        this.emailApply2 = emailApply2;
    }

    public String getIsUnitAdm() {
        return isUnitAdm;
    }

    public void setIsUnitAdm(String isUnitAdm) {
        this.isUnitAdm = isUnitAdm;
    }

    public String getIsUnitDataMgr() {
        return isUnitDataMgr;
    }

    public void setIsUnitDataMgr(String isUnitDataMgr) {
        this.isUnitDataMgr = isUnitDataMgr;
    }

    public String getIsWebSiteOther() {
        return isWebSiteOther;
    }

    public void setIsWebSiteOther(String isWebSiteOther) {
        this.isWebSiteOther = isWebSiteOther;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public String getAdmUnit() {
        return admUnit;
    }

    public void setAdmUnit(String admUnit) {
        this.admUnit = admUnit;
    }

    public String getAdmStatus() {
        return admStatus;
    }

    public void setAdmStatus(String admStatus) {
        this.admStatus = admStatus;
    }

    public Instant getAdmEnableDate() {
        return admEnableDate;
    }

    public void setAdmEnableDate(Instant admEnableDate) {
        this.admEnableDate = admEnableDate;
    }

    public String getAdmName() {
        return admName;
    }

    public void setAdmName(String admName) {
        this.admName = admName;
    }

    public String getOtherSys() {
        return otherSys;
    }

    public void setOtherSys(String otherSys) {
        this.otherSys = otherSys;
    }

    public String getOtherSysAccount() {
        return otherSysAccount;
    }

    public void setOtherSysAccount(String otherSysAccount) {
        this.otherSysAccount = otherSysAccount;
    }

    public String getIsColon() {
        return isColon;
    }

    public void setIsColon(String isColon) {
        this.isColon = isColon;
    }

    public String getApplyVersion() {
        return applyVersion;
    }

    public void setApplyVersion(String applyVersion) {
        this.applyVersion = applyVersion;
    }

    public String getPermissionsVersion() {
        return permissionsVersion;
    }

    public void setPermissionsVersion(String permissionsVersion) {
        this.permissionsVersion = permissionsVersion;
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
        if (!(o instanceof BpmL410ApplyManageDTO)) {
            return false;
        }

        BpmL410ApplyManageDTO bpmL410ApplyManageDTO = (BpmL410ApplyManageDTO) o;
        if (this.systemApply == null) {
            return false;
        }
        return Objects.equals(this.systemApply, bpmL410ApplyManageDTO.systemApply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.systemApply);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmL410ApplyManageDTO{" +
                "systemApply='" + systemApply + '\'' +
                ", systemApplyName='" + systemApplyName + '\'' +
                ", checkbox='" + checkbox + '\'' +
                ", sys='" + sys + '\'' +
                ", systemApplyInput='" + systemApplyInput + '\'' +
                ", sysChange='" + sysChange + '\'' +
                ", emailApply1='" + emailApply1 + '\'' +
                ", emailApply2='" + emailApply2 + '\'' +
                ", isUnitAdm='" + isUnitAdm + '\'' +
                ", isUnitDataMgr='" + isUnitDataMgr + '\'' +
                ", isWebSiteOther='" + isWebSiteOther + '\'' +
                ", otherReason='" + otherReason + '\'' +
                ", admUnit='" + admUnit + '\'' +
                ", admStatus='" + admStatus + '\'' +
                ", admEnableDate=" + admEnableDate +
                ", admName='" + admName + '\'' +
                ", otherSys='" + otherSys + '\'' +
                ", otherSysAccount='" + otherSysAccount + '\'' +
                ", isColon='" + isColon + '\'' +
                ", applyVersion='" + applyVersion + '\'' +
                ", permissionsVersion='" + permissionsVersion + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
