package tw.gov.pcc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A BpmL410ApplyManage.
 */
@Entity
@Table(name = "BPM_L410_APPLY_MANAGE")
public class BpmL410ApplyManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    @Column(name = "system_apply", length = 50, nullable = false)
    private String systemApply;

    @Size(max = 40)
    @Column(name = "system_apply_name", length = 40)
    private String systemApplyName;
    @Column(name = "checkbox")
    private String checkbox;

    @Column(name = "sys")
    private String sys;

    @Size(max = 50)
    @Column(name = "system_apply_input", length = 50)
    private String systemApplyInput;

    @Size(max = 50)
    @Column(name = "sys_change", length = 50)
    private String sysChange;

    @Size(max = 20)
    @Column(name = "email_apply1", length = 20)
    private String emailApply1;

    @Size(max = 1)
    @Column(name = "email_apply2", length = 1)
    private String emailApply2;

    @Size(max = 1)
    @Column(name = "is_unit_adm", length = 1)
    private String isUnitAdm;

    @Size(max = 1)
    @Column(name = "is_unit_data_mgr", length = 1)
    private String isUnitDataMgr;

    @Size(max = 1)
    @Column(name = "is_web_site_other", length = 1)
    private String isWebSiteOther;

    @Size(max = 30)
    @Column(name = "other_reason", length = 30)
    private String otherReason;

    @Size(max = 1)
    @Column(name = "adm_unit", length = 1)
    private String admUnit;

    @Size(max = 10)
    @Column(name = "adm_status", length = 10)
    private String admStatus;

    @Column(name = "adm_enable_date")
    private Instant admEnableDate;

    @Size(max = 20)
    @Column(name = "adm_name", length = 20)
    private String admName;

    @Size(max = 20)
    @Column(name = "other_sys", length = 20)
    private String otherSys;

    @Size(max = 20)
    @Column(name = "other_sys_account", length = 20)
    private String otherSysAccount;


    @Size(max = 1)
    @Column(name = "IS_COLON", length = 1)
    private String isColon;

    @Size(max = 1)
    @Column(name = "APPLY_VERSION", length = 1)
    private String applyVersion;

    @Size(max = 1)
    @Column(name = "PERMISSIONS_VERSION", length = 1)
    private String permissionsVersion;

    @NotNull
    @Size(max = 20)
    @Column(name = "create_user", length = 20, nullable = false)
    private String createUser;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getSystemApply() {
        return this.systemApply;
    }

    public BpmL410ApplyManage systemApply(String systemApply) {
        this.setSystemApply(systemApply);
        return this;
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
        return this.checkbox;
    }

    public BpmL410ApplyManage checkbox(String checkbox) {
        this.setCheckbox(checkbox);
        return this;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getSys() {
        return this.sys;
    }

    public BpmL410ApplyManage sys(String sys) {
        this.setSys(sys);
        return this;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSystemApplyInput() {
        return this.systemApplyInput;
    }

    public BpmL410ApplyManage systemApplyInput(String systemApplyInput) {
        this.setSystemApplyInput(systemApplyInput);
        return this;
    }

    public void setSystemApplyInput(String systemApplyInput) {
        this.systemApplyInput = systemApplyInput;
    }

    public String getSysChange() {
        return this.sysChange;
    }

    public BpmL410ApplyManage sysChange(String sysChange) {
        this.setSysChange(sysChange);
        return this;
    }

    public void setSysChange(String sysChange) {
        this.sysChange = sysChange;
    }

    public String getEmailApply1() {
        return this.emailApply1;
    }

    public BpmL410ApplyManage emailApply1(String emailApply1) {
        this.setEmailApply1(emailApply1);
        return this;
    }

    public void setEmailApply1(String emailApply1) {
        this.emailApply1 = emailApply1;
    }

    public String getEmailApply2() {
        return this.emailApply2;
    }

    public BpmL410ApplyManage emailApply2(String emailApply2) {
        this.setEmailApply2(emailApply2);
        return this;
    }

    public void setEmailApply2(String emailApply2) {
        this.emailApply2 = emailApply2;
    }

    public String getIsUnitAdm() {
        return this.isUnitAdm;
    }

    public BpmL410ApplyManage isUnitAdm(String isUnitAdm) {
        this.setIsUnitAdm(isUnitAdm);
        return this;
    }

    public void setIsUnitAdm(String isUnitAdm) {
        this.isUnitAdm = isUnitAdm;
    }

    public String getIsUnitDataMgr() {
        return this.isUnitDataMgr;
    }

    public BpmL410ApplyManage isUnitDataMgr(String isUnitDataMgr) {
        this.setIsUnitDataMgr(isUnitDataMgr);
        return this;
    }

    public void setIsUnitDataMgr(String isUnitDataMgr) {
        this.isUnitDataMgr = isUnitDataMgr;
    }

    public String getIsWebSiteOther() {
        return this.isWebSiteOther;
    }

    public BpmL410ApplyManage isWebSiteOther(String isWebSiteOther) {
        this.setIsWebSiteOther(isWebSiteOther);
        return this;
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
        return this.admUnit;
    }

    public BpmL410ApplyManage admUnit(String admUnit) {
        this.setAdmUnit(admUnit);
        return this;
    }

    public void setAdmUnit(String admUnit) {
        this.admUnit = admUnit;
    }

    public String getAdmStatus() {
        return this.admStatus;
    }

    public BpmL410ApplyManage admStatus(String admStatus) {
        this.setAdmStatus(admStatus);
        return this;
    }

    public void setAdmStatus(String admStatus) {
        this.admStatus = admStatus;
    }

    public Instant getAdmEnableDate() {
        return this.admEnableDate;
    }

    public BpmL410ApplyManage admEnableDate(Instant admEnableDate) {
        this.setAdmEnableDate(admEnableDate);
        return this;
    }

    public void setAdmEnableDate(Instant admEnableDate) {
        this.admEnableDate = admEnableDate;
    }

    public String getAdmName() {
        return this.admName;
    }

    public BpmL410ApplyManage admName(String admName) {
        this.setAdmName(admName);
        return this;
    }

    public void setAdmName(String admName) {
        this.admName = admName;
    }

    public String getOtherSys() {
        return this.otherSys;
    }

    public BpmL410ApplyManage otherSys(String otherSys) {
        this.setOtherSys(otherSys);
        return this;
    }

    public void setOtherSys(String otherSys) {
        this.otherSys = otherSys;
    }

    public String getOtherSysAccount() {
        return this.otherSysAccount;
    }

    public BpmL410ApplyManage otherSysAccount(String otherSysAccount) {
        this.setOtherSysAccount(otherSysAccount);
        return this;
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
        return this.createUser;
    }

    public BpmL410ApplyManage createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public BpmL410ApplyManage createTime(Instant createTime) {
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
        if (!(o instanceof BpmL410ApplyManage)) {
            return false;
        }
        return systemApply != null && systemApply.equals(((BpmL410ApplyManage) o).systemApply);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmL410ApplyManage{" +
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
