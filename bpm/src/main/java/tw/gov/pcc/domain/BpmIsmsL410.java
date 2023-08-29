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
 * A BpmIsmsL410.
 */
@Entity
@Table(name = "BPM_ISMS_L410")
public class BpmIsmsL410 implements Serializable {

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
    @Size(max = 20)
    @Column(name = "app_eng_name", length = 20, nullable = false)
    private String appEngName;

    @NotNull
    @Size(max = 30)
    @Column(name = "app_unit1", length = 30, nullable = false)
    private String appUnit1;

    @NotNull
    @Size(max = 30)
    @Column(name = "app_unit2", length = 30, nullable = false)
    private String appUnit2;

    @NotNull
    @Size(max = 20)
    @Column(name = "position", length = 20, nullable = false)
    private String position;

    @Size(max = 20)
    @Column(name = "ext_num", length = 20)
    private String extNum;

    @Size(max = 1)
    @Column(name = "is_submit", length = 1)
    private String isSubmit;

    @Size(max = 1)
    @Column(name = "app_reason", length = 1)
    private String appReason;

    @Size(max = 1)
    @Column(name = "is_enable_date", length = 1)
    private String isEnableDate;

    @Column(name = "enable_date")
    private Instant enableDate;

    @Size(max = 1)
    @Column(name = "is_other", length = 1)
    private String isOther;

    @Size(max = 100)
    @Column(name = "other_reason", length = 100)
    private String otherReason;

    @Size(max = 1)
    @Column(name = "is_hr_sys", length = 1)
    private String isHrSys;

    @Size(max = 1)
    @Column(name = "hr_sys", length = 1)
    private String hrSys;

    @Size(max = 100)
    @Column(name = "hr_sys_change", length = 100)
    private String hrSysChange;

    @Size(max = 20)
    @Column(name = "hr_sys_adm_unit", length = 20)
    private String hrSysAdmUnit;

    @Size(max = 20)
    @Column(name = "hr_sys_status", length = 20)
    private String hrSysStatus;

    @Column(name = "hr_sys_enable_date")
    private Instant hrSysEnableDate;

    @Size(max = 20)
    @Column(name = "hr_sys_adm_name", length = 20)
    private String hrSysAdmName;

    @Size(max = 1)
    @Column(name = "is_ad_sys", length = 1)
    private String isAdSys;

    @Size(max = 20)
    @Column(name = "ad_account", length = 20)
    private String adAccount;

    @Size(max = 1)
    @Column(name = "ad_sys", length = 1)
    private String adSys;

    @Size(max = 20)
    @Column(name = "ad_sys_change", length = 20)
    private String adSysChange;

    @Size(max = 20)
    @Column(name = "ad_sys_adm_unit", length = 20)
    private String adSysAdmUnit;

    @Size(max = 20)
    @Column(name = "ad_sys_status", length = 20)
    private String adSysStatus;

    @Column(name = "ad_sys_enable_date")
    private Instant adSysEnableDate;

    @Size(max = 20)
    @Column(name = "ad_sys_adm_name", length = 20)
    private String adSysAdmName;

    @Size(max = 1)
    @Column(name = "is_meeting_room", length = 1)
    private String isMeetingRoom;

    @Size(max = 1)
    @Column(name = "meeting_room", length = 1)
    private String meetingRoom;

    @Size(max = 100)
    @Column(name = "meeting_room_change", length = 100)
    private String meetingEoomChange;

    @Size(max = 20)
    @Column(name = "meeting_room_adm_unit", length = 20)
    private String meetingRoomAdmUnit;

    @Size(max = 20)
    @Column(name = "meeting_room_status", length = 20)
    private String meetingRoomStatus;

    @Column(name = "meeting_room_enable_date")
    private Instant meetingRoomEnableDate;

    @Size(max = 20)
    @Column(name = "MEETING_ROOM_ADM_NAME", length = 20)
    private String meetingRoomAdmName;

    @Size(max = 1)
    @Column(name = "is_od_sys", length = 1)
    private String isOdSys;

    @Size(max = 20)
    @Column(name = "od_sys_role", length = 20)
    private String odSysRole;

    @Size(max = 1)
    @Column(name = "od_sys", length = 1)
    private String odSys;

    @Size(max = 20)
    @Column(name = "od_sys_other", length = 20)
    private String odSysOther;

    @Size(max = 20)
    @Column(name = "od_sys_adm_unit", length = 20)
    private String odSysAdmUnit;

    @Size(max = 20)
    @Column(name = "od_sys_status", length = 20)
    private String odSysStatus;

    @Column(name = "od_sys_enable_date")
    private Instant odSysEnableDate;

    @Size(max = 20)
    @Column(name = "od_sys_adm_name", length = 20)
    private String odSysAdmName;

    @Size(max = 1)
    @Column(name = "is_email_sys", length = 1)
    private String isEmailSys;

    @Size(max = 20)
    @Column(name = "email_sys_account", length = 20)
    private String emailSysAccount;

    @Size(max = 1)
    @Column(name = "email_sys", length = 1)
    private String emailSys;

    @Size(max = 20)
    @Column(name = "email_apply1", length = 20)
    private String emailApply1;

    @Size(max = 20)
    @Column(name = "email_apply2", length = 20)
    private String emailApply2;

    @Size(max = 20)
    @Column(name = "email_sys_change", length = 20)
    private String emailSysChange;

    @Size(max = 20)
    @Column(name = "email_sys_adm_unit", length = 20)
    private String emailSysAdmUnit;

    @Size(max = 20)
    @Column(name = "email_sys_status", length = 20)
    private String emailSysStatus;

    @Column(name = "email_sys_enable_date")
    private Instant emailSysEnableDate;

    @Size(max = 20)
    @Column(name = "email_sys_adm_name", length = 20)
    private String emailSysAdmName;

    @Size(max = 1)
    @Column(name = "is_web_site", length = 1)
    private String isWebSite;

    @Size(max = 1)
    @Column(name = "is_pcc_www", length = 1)
    private String isPccWww;

    @Size(max = 1)
    @Column(name = "is_pcc_home", length = 1)
    private String isPccHome;

    @Size(max = 1)
    @Column(name = "web_site", length = 1)
    private String webSite;

    @Size(max = 1)
    @Column(name = "is_unit_adm", length = 1)
    private String isUnitAdm;

    @Size(max = 1)
    @Column(name = "is_unit_data_mgr", length = 1)
    private String isUnitDataMgr;

    @Size(max = 1)
    @Column(name = "is_web_site_other", length = 1)
    private String isWebSiteOther;
    @Size(max = 20)
    @Column(name = "web_site_other", length = 20)
    private String webSiteOther;

    @Size(max = 20)
    @Column(name = "web_site_adm_unit", length = 20)
    private String webSiteAdmUnit;

    @Size(max = 20)
    @Column(name = "web_site_status", length = 20)
    private String webSiteStatus;

    @Column(name = "web_site_enable_date")
    private Instant webSiteEnableDate;

    @Size(max = 20)
    @Column(name = "web_site_adm_name", length = 20)
    private String webSiteAdmName;

    @Size(max = 1)
    @Column(name = "is_pcc_pis", length = 1)
    private String isPccPis;

    @Size(max = 20)
    @Column(name = "pcc_pis_account", length = 20)
    private String pccPisAccount;

    @Size(max = 1)
    @Column(name = "pcc_pis", length = 1)
    private String pccPis;

    @Size(max = 20)
    @Column(name = "pcc_pis_change", length = 20)
    private String pccPisChange;

    @Size(max = 20)
    @Column(name = "pcc_pis_adm_unit", length = 20)
    private String pccPisAdmUnit;

    @Size(max = 20)
    @Column(name = "pcc_pis_status", length = 20)
    private String pccPisStatus;

    @Column(name = "pcc_pis_enable_date")
    private Instant pccPisEnableDate;

    @Size(max = 20)
    @Column(name = "pcc_pis_adm_name", length = 20)
    private String pccPisAdmName;

    @Size(max = 1)
    @Column(name = "is_other_sys1", length = 1)
    private String isOtherSys1;

    @Size(max = 20)
    @Column(name = "other_sys1_server_name", length = 20)
    private String otherSys1ServerName;

    @Size(max = 20)
    @Column(name = "other_sys1_account", length = 20)
    private String otherSys1Account;

    @Size(max = 1)
    @Column(name = "other_sys1", length = 1)
    private String otherSys1;

    @Size(max = 20)
    @Column(name = "other_sys1_change", length = 20)
    private String otherSys1Change;

    @Size(max = 20)
    @Column(name = "other_sys1_adm_unit", length = 20)
    private String otherSys1AdmUnit;

    @Size(max = 20)
    @Column(name = "other_sys1_status", length = 20)
    private String otherSys1Status;

    @Column(name = "other_sys1_enable_date")
    private Instant otherSys1EnableDate;

    @Size(max = 20)
    @Column(name = "other_sys1_adm_name", length = 20)
    private String otherSys1AdmName;

    @Size(max = 1)
    @Column(name = "is_other_sys2", length = 1)
    private String isOtherSys2;

    @Size(max = 20)
    @Column(name = "other_sys2_server_name", length = 20)
    private String otherSys2ServerName;

    @Size(max = 20)
    @Column(name = "other_sys2_account", length = 20)
    private String otherSys2Account;

    @Size(max = 1)
    @Column(name = "other_sys2", length = 1)
    private String otherSys2;

    @Size(max = 20)
    @Column(name = "other_sys2_change", length = 20)
    private String otherSys2Change;

    @Size(max = 20)
    @Column(name = "other_sys2_adm_unit", length = 20)
    private String otherSys2AdmUnit;

    @Size(max = 20)
    @Column(name = "other_sys2_status", length = 20)
    private String otherSys2Status;

    @Column(name = "other_sys2_enable_date")
    private Instant otherSys2EnableDate;

    @Size(max = 20)
    @Column(name = "other_sys2_adm_name", length = 20)
    private String otherSys2AdmName;

    @Size(max = 1)
    @Column(name = "is_other_sys3", length = 1)
    private String isOtherSys3;

    @Size(max = 20)
    @Column(name = "other_sys3_server_name", length = 20)
    private String otherSys3ServerName;

    @Size(max = 20)
    @Column(name = "other_sys3_account", length = 20)
    private String otherSys3Account;

    @Size(max = 1)
    @Column(name = "other_sys3", length = 1)
    private String otherSys3;

    @Size(max = 20)
    @Column(name = "other_sys3_change", length = 20)
    private String otherSys3Change;

    @Size(max = 20)
    @Column(name = "other_sys3_adm_unit", length = 20)
    private String otherSys3AdmUnit;

    @Size(max = 20)
    @Column(name = "other_sys3_status", length = 20)
    private String otherSys3Status;

    @Column(name = "other_sys3_enable_date")
    private Instant otherSys3EnableDate;

    @Size(max = 20)
    @Column(name = "other_sys3_adm_name", length = 20)
    private String otherSys3AdmName;

    @Size(max = 1)
    @Column(name = "process_instance_status", length = 1)
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

    public BpmIsmsL410 formId(String formId) {
        this.setFormId(formId);
        return this;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public BpmIsmsL410 processInstanceId(String processInstanceId) {
        this.setProcessInstanceId(processInstanceId);
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Instant getApplyDate() {
        return this.applyDate;
    }

    public BpmIsmsL410 applyDate(Instant applyDate) {
        this.setApplyDate(applyDate);
        return this;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public String getFilEmpid() {
        return this.filEmpid;
    }

    public BpmIsmsL410 filEmpid(String filEmpid) {
        this.setFilEmpid(filEmpid);
        return this;
    }

    public void setFilEmpid(String filEmpid) {
        this.filEmpid = filEmpid;
    }

    public String getFilName() {
        return this.filName;
    }

    public BpmIsmsL410 filName(String filName) {
        this.setFilName(filName);
        return this;
    }

    public void setFilName(String filName) {
        this.filName = filName;
    }

    public String getFilUnit() {
        return this.filUnit;
    }

    public BpmIsmsL410 filUnit(String filUnit) {
        this.setFilUnit(filUnit);
        return this;
    }

    public void setFilUnit(String filUnit) {
        this.filUnit = filUnit;
    }

    public String getAppEmpid() {
        return this.appEmpid;
    }

    public BpmIsmsL410 appEmpid(String appEmpid) {
        this.setAppEmpid(appEmpid);
        return this;
    }

    public void setAppEmpid(String appEmpid) {
        this.appEmpid = appEmpid;
    }

    public String getAppName() {
        return this.appName;
    }

    public BpmIsmsL410 appName(String appName) {
        this.setAppName(appName);
        return this;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppEngName() {
        return this.appEngName;
    }

    public BpmIsmsL410 appEngName(String appEngName) {
        this.setAppEngName(appEngName);
        return this;
    }

    public void setAppEngName(String appEngName) {
        this.appEngName = appEngName;
    }

    public String getAppUnit1() {
        return this.appUnit1;
    }

    public BpmIsmsL410 appUnit1(String appUnit1) {
        this.setAppUnit1(appUnit1);
        return this;
    }

    public void setAppUnit1(String appUnit1) {
        this.appUnit1 = appUnit1;
    }

    public String getAppUnit2() {
        return this.appUnit2;
    }

    public BpmIsmsL410 appUnit2(String appUnit2) {
        this.setAppUnit2(appUnit2);
        return this;
    }

    public void setAppUnit2(String appUnit2) {
        this.appUnit2 = appUnit2;
    }

    public String getPosition() {
        return this.position;
    }

    public BpmIsmsL410 position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getExtNum() {
        return this.extNum;
    }

    public BpmIsmsL410 extNum(String extNum) {
        this.setExtNum(extNum);
        return this;
    }

    public void setExtNum(String extNum) {
        this.extNum = extNum;
    }

    public String getIsSubmit() {
        return this.isSubmit;
    }

    public BpmIsmsL410 isSubmit(String isSubmit) {
        this.setIsSubmit(isSubmit);
        return this;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getAppReason() {
        return this.appReason;
    }

    public BpmIsmsL410 appReason(String appReason) {
        this.setAppReason(appReason);
        return this;
    }

    public void setAppReason(String appReason) {
        this.appReason = appReason;
    }

    public String getIsEnableDate() {
        return this.isEnableDate;
    }

    public BpmIsmsL410 isEnableDate(String isEnableDate) {
        this.setIsEnableDate(isEnableDate);
        return this;
    }

    public void setIsEnableDate(String isEnableDate) {
        this.isEnableDate = isEnableDate;
    }

    public Instant getEnableDate() {
        return this.enableDate;
    }

    public BpmIsmsL410 enableDate(Instant enableDate) {
        this.setEnableDate(enableDate);
        return this;
    }

    public void setEnableDate(Instant enableDate) {
        this.enableDate = enableDate;
    }

    public String getIsOther() {
        return this.isOther;
    }

    public BpmIsmsL410 isOther(String isOther) {
        this.setIsOther(isOther);
        return this;
    }

    public void setIsOther(String isOther) {
        this.isOther = isOther;
    }

    public String getOtherReason() {
        return this.otherReason;
    }

    public BpmIsmsL410 otherReason(String otherReason) {
        this.setOtherReason(otherReason);
        return this;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }


    public String getIsHrSys() {
        return isHrSys;
    }

    public void setIsHrSys(String isHrSys) {
        this.isHrSys = isHrSys;
    }

    public String getHrSys() {
        return this.hrSys;
    }

    public BpmIsmsL410 hrSys(String hrSys) {
        this.setHrSys(hrSys);
        return this;
    }

    public void setHrSys(String hrSys) {
        this.hrSys = hrSys;
    }

    public String getHrSysChange() {
        return this.hrSysChange;
    }

    public BpmIsmsL410 hrSysChange(String hrSysChange) {
        this.setHrSysChange(hrSysChange);
        return this;
    }

    public void setHrSysChange(String hrSysChange) {
        this.hrSysChange = hrSysChange;
    }

    public String getHrSysAdmUnit() {
        return this.hrSysAdmUnit;
    }

    public BpmIsmsL410 hrSysAdmUnit(String hrSysAdmUnit) {
        this.setHrSysAdmUnit(hrSysAdmUnit);
        return this;
    }

    public void setHrSysAdmUnit(String hrSysAdmUnit) {
        this.hrSysAdmUnit = hrSysAdmUnit;
    }

    public String getHrSysStatus() {
        return this.hrSysStatus;
    }

    public BpmIsmsL410 hrSysStatus(String hrSysStatus) {
        this.setHrSysStatus(hrSysStatus);
        return this;
    }

    public void setHrSysStatus(String hrSysStatus) {
        this.hrSysStatus = hrSysStatus;
    }

    public Instant getHrSysEnableDate() {
        return this.hrSysEnableDate;
    }

    public BpmIsmsL410 hrSysEnableDate(Instant hrSysEnableDate) {
        this.setHrSysEnableDate(hrSysEnableDate);
        return this;
    }

    public void setHrSysEnableDate(Instant hrSysEnableDate) {
        this.hrSysEnableDate = hrSysEnableDate;
    }

    public String getHrSysAdmName() {
        return this.hrSysAdmName;
    }

    public BpmIsmsL410 hrSysAdmName(String hrSysAdmName) {
        this.setHrSysAdmName(hrSysAdmName);
        return this;
    }

    public void setHrSysAdmName(String hrSysAdmName) {
        this.hrSysAdmName = hrSysAdmName;
    }

    public String getIsAdSys() {
        return this.isAdSys;
    }

    public BpmIsmsL410 isAdSys(String isAdSys) {
        this.setIsAdSys(isAdSys);
        return this;
    }

    public void setIsAdSys(String isAdSys) {
        this.isAdSys = isAdSys;
    }

    public String getAdAccount() {
        return this.adAccount;
    }

    public BpmIsmsL410 adAccount(String adAccount) {
        this.setAdAccount(adAccount);
        return this;
    }

    public void setAdAccount(String adAccount) {
        this.adAccount = adAccount;
    }

    public String getAdSys() {
        return this.adSys;
    }

    public BpmIsmsL410 adSys(String adSys) {
        this.setAdSys(adSys);
        return this;
    }

    public void setAdSys(String adSys) {
        this.adSys = adSys;
    }

    public String getAdSysChange() {
        return this.adSysChange;
    }

    public BpmIsmsL410 adSysChange(String adSysChange) {
        this.setAdSysChange(adSysChange);
        return this;
    }

    public void setAdSysChange(String adSysChange) {
        this.adSysChange = adSysChange;
    }

    public String getAdSysAdmUnit() {
        return this.adSysAdmUnit;
    }

    public BpmIsmsL410 adSysAdmUnit(String adSysAdmUnit) {
        this.setAdSysAdmUnit(adSysAdmUnit);
        return this;
    }

    public void setAdSysAdmUnit(String adSysAdmUnit) {
        this.adSysAdmUnit = adSysAdmUnit;
    }

    public String getAdSysStatus() {
        return this.adSysStatus;
    }

    public BpmIsmsL410 adSysStatus(String adSysStatus) {
        this.setAdSysStatus(adSysStatus);
        return this;
    }

    public void setAdSysStatus(String adSysStatus) {
        this.adSysStatus = adSysStatus;
    }

    public Instant getAdSysEnableDate() {
        return this.adSysEnableDate;
    }

    public BpmIsmsL410 adSysEnableDate(Instant adSysEnableDate) {
        this.setAdSysEnableDate(adSysEnableDate);
        return this;
    }

    public void setAdSysEnableDate(Instant adSysEnableDate) {
        this.adSysEnableDate = adSysEnableDate;
    }

    public String getAdSysAdmName() {
        return this.adSysAdmName;
    }

    public BpmIsmsL410 adSysAdmName(String adSysAdmName) {
        this.setAdSysAdmName(adSysAdmName);
        return this;
    }

    public void setAdSysAdmName(String adSysAdmName) {
        this.adSysAdmName = adSysAdmName;
    }

    public String getIsMeetingRoom() {
        return isMeetingRoom;
    }

    public void setIsMeetingRoom(String isMeetingRoom) {
        this.isMeetingRoom = isMeetingRoom;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public String getMeetingEoomChange() {
        return meetingEoomChange;
    }

    public void setMeetingEoomChange(String meetingEoomChange) {
        this.meetingEoomChange = meetingEoomChange;
    }

    public String getMeetingRoomAdmUnit() {
        return meetingRoomAdmUnit;
    }

    public void setMeetingRoomAdmUnit(String meetingRoomAdmUnit) {
        this.meetingRoomAdmUnit = meetingRoomAdmUnit;
    }

    public String getMeetingRoomStatus() {
        return meetingRoomStatus;
    }

    public void setMeetingRoomStatus(String meetingRoomStatus) {
        this.meetingRoomStatus = meetingRoomStatus;
    }

    public Instant getMeetingRoomEnableDate() {
        return meetingRoomEnableDate;
    }

    public void setMeetingRoomEnableDate(Instant meetingRoomEnableDate) {
        this.meetingRoomEnableDate = meetingRoomEnableDate;
    }

    public String getMeetingRoomAdmName() {
        return meetingRoomAdmName;
    }

    public void setMeetingRoomAdmName(String meetingRoomAdmName) {
        this.meetingRoomAdmName = meetingRoomAdmName;
    }

    public String getIsOdSys() {
        return this.isOdSys;
    }

    public BpmIsmsL410 isOdSys(String isOdSys) {
        this.setIsOdSys(isOdSys);
        return this;
    }

    public void setIsOdSys(String isOdSys) {
        this.isOdSys = isOdSys;
    }

    public String getOdSysRole() {
        return this.odSysRole;
    }

    public BpmIsmsL410 odSysRole(String odSysRole) {
        this.setOdSysRole(odSysRole);
        return this;
    }

    public void setOdSysRole(String odSysRole) {
        this.odSysRole = odSysRole;
    }

    public String getOdSys() {
        return this.odSys;
    }

    public BpmIsmsL410 odSys(String odSys) {
        this.setOdSys(odSys);
        return this;
    }

    public void setOdSys(String odSys) {
        this.odSys = odSys;
    }

    public String getOdSysOther() {
        return this.odSysOther;
    }

    public BpmIsmsL410 odSysOther(String odSysOther) {
        this.setOdSysOther(odSysOther);
        return this;
    }

    public void setOdSysOther(String odSysOther) {
        this.odSysOther = odSysOther;
    }

    public String getOdSysAdmUnit() {
        return this.odSysAdmUnit;
    }

    public BpmIsmsL410 odSysAdmUnit(String odSysAdmUnit) {
        this.setOdSysAdmUnit(odSysAdmUnit);
        return this;
    }

    public void setOdSysAdmUnit(String odSysAdmUnit) {
        this.odSysAdmUnit = odSysAdmUnit;
    }

    public String getOdSysStatus() {
        return this.odSysStatus;
    }

    public BpmIsmsL410 odSysStatus(String odSysStatus) {
        this.setOdSysStatus(odSysStatus);
        return this;
    }

    public void setOdSysStatus(String odSysStatus) {
        this.odSysStatus = odSysStatus;
    }

    public Instant getOdSysEnableDate() {
        return this.odSysEnableDate;
    }

    public BpmIsmsL410 odSysEnableDate(Instant odSysEnableDate) {
        this.setOdSysEnableDate(odSysEnableDate);
        return this;
    }

    public void setOdSysEnableDate(Instant odSysEnableDate) {
        this.odSysEnableDate = odSysEnableDate;
    }

    public String getOdSysAdmName() {
        return this.odSysAdmName;
    }

    public BpmIsmsL410 odSysAdmName(String odSysAdmName) {
        this.setOdSysAdmName(odSysAdmName);
        return this;
    }

    public void setOdSysAdmName(String odSysAdmName) {
        this.odSysAdmName = odSysAdmName;
    }

    public String getIsEmailSys() {
        return this.isEmailSys;
    }

    public BpmIsmsL410 isEmailSys(String isEmailSys) {
        this.setIsEmailSys(isEmailSys);
        return this;
    }

    public void setIsEmailSys(String isEmailSys) {
        this.isEmailSys = isEmailSys;
    }

    public String getEmailSysAccount() {
        return this.emailSysAccount;
    }

    public BpmIsmsL410 emailSysAccount(String emailSysAccount) {
        this.setEmailSysAccount(emailSysAccount);
        return this;
    }

    public void setEmailSysAccount(String emailSysAccount) {
        this.emailSysAccount = emailSysAccount;
    }

    public String getEmailSys() {
        return this.emailSys;
    }

    public BpmIsmsL410 emailSys(String emailSys) {
        this.setEmailSys(emailSys);
        return this;
    }

    public void setEmailSys(String emailSys) {
        this.emailSys = emailSys;
    }

    public String getEmailApply1() {
        return this.emailApply1;
    }

    public BpmIsmsL410 emailApply1(String emailApply1) {
        this.setEmailApply1(emailApply1);
        return this;
    }

    public void setEmailApply1(String emailApply1) {
        this.emailApply1 = emailApply1;
    }

    public String getEmailApply2() {
        return this.emailApply2;
    }

    public BpmIsmsL410 emailApply2(String emailApply2) {
        this.setEmailApply2(emailApply2);
        return this;
    }

    public void setEmailApply2(String emailApply2) {
        this.emailApply2 = emailApply2;
    }

    public String getEmailSysChange() {
        return this.emailSysChange;
    }

    public BpmIsmsL410 emailSysChange(String emailSysChange) {
        this.setEmailSysChange(emailSysChange);
        return this;
    }

    public void setEmailSysChange(String emailSysChange) {
        this.emailSysChange = emailSysChange;
    }

    public String getEmailSysAdmUnit() {
        return this.emailSysAdmUnit;
    }

    public BpmIsmsL410 emailSysAdmUnit(String emailSysAdmUnit) {
        this.setEmailSysAdmUnit(emailSysAdmUnit);
        return this;
    }

    public void setEmailSysAdmUnit(String emailSysAdmUnit) {
        this.emailSysAdmUnit = emailSysAdmUnit;
    }

    public String getEmailSysStatus() {
        return this.emailSysStatus;
    }

    public BpmIsmsL410 emailSysStatus(String emailSysStatus) {
        this.setEmailSysStatus(emailSysStatus);
        return this;
    }

    public void setEmailSysStatus(String emailSysStatus) {
        this.emailSysStatus = emailSysStatus;
    }

    public Instant getEmailSysEnableDate() {
        return this.emailSysEnableDate;
    }

    public BpmIsmsL410 emailSysEnableDate(Instant emailSysEnableDate) {
        this.setEmailSysEnableDate(emailSysEnableDate);
        return this;
    }

    public void setEmailSysEnableDate(Instant emailSysEnableDate) {
        this.emailSysEnableDate = emailSysEnableDate;
    }

    public String getEmailSysAdmName() {
        return this.emailSysAdmName;
    }

    public BpmIsmsL410 emailSysAdmName(String emailSysAdmName) {
        this.setEmailSysAdmName(emailSysAdmName);
        return this;
    }

    public void setEmailSysAdmName(String emailSysAdmName) {
        this.emailSysAdmName = emailSysAdmName;
    }

    public String getIsWebSite() {
        return this.isWebSite;
    }

    public BpmIsmsL410 isWebSite(String isWebSite) {
        this.setIsWebSite(isWebSite);
        return this;
    }

    public void setIsWebSite(String isWebSite) {
        this.isWebSite = isWebSite;
    }

    public String getIsPccWww() {
        return this.isPccWww;
    }

    public BpmIsmsL410 isPccWww(String isPccWww) {
        this.setIsPccWww(isPccWww);
        return this;
    }

    public void setIsPccWww(String isPccWww) {
        this.isPccWww = isPccWww;
    }

    public String getIsPccHome() {
        return this.isPccHome;
    }

    public BpmIsmsL410 isPccHome(String isPccHome) {
        this.setIsPccHome(isPccHome);
        return this;
    }

    public void setIsPccHome(String isPccHome) {
        this.isPccHome = isPccHome;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public BpmIsmsL410 webSite(String webSite) {
        this.setWebSite(webSite);
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getIsUnitAdm() {
        return this.isUnitAdm;
    }

    public BpmIsmsL410 isUnitAdm(String isUnitAdm) {
        this.setIsUnitAdm(isUnitAdm);
        return this;
    }

    public void setIsUnitAdm(String isUnitAdm) {
        this.isUnitAdm = isUnitAdm;
    }

    public String getIsUnitDataMgr() {
        return this.isUnitDataMgr;
    }

    public BpmIsmsL410 isUnitDataMgr(String isUnitDataMgr) {
        this.setIsUnitDataMgr(isUnitDataMgr);
        return this;
    }

    public void setIsUnitDataMgr(String isUnitDataMgr) {
        this.isUnitDataMgr = isUnitDataMgr;
    }

    public String getIsWebSiteOther() {
        return this.isWebSiteOther;
    }

    public BpmIsmsL410 isWebSiteOther(String isWebSiteOther) {
        this.setIsWebSiteOther(isWebSiteOther);
        return this;
    }

    public void setIsWebSiteOther(String isWebSiteOther) {
        this.isWebSiteOther = isWebSiteOther;
    }

    public String getWebSiteOther() {
        return this.webSiteOther;
    }

    public BpmIsmsL410 webSiteOther(String webSiteOther) {
        this.setWebSiteOther(webSiteOther);
        return this;
    }

    public void setWebSiteOther(String webSiteOther) {
        this.webSiteOther = webSiteOther;
    }

    public String getWebSiteAdmUnit() {
        return this.webSiteAdmUnit;
    }

    public BpmIsmsL410 webSiteAdmUnit(String webSiteAdmUnit) {
        this.setWebSiteAdmUnit(webSiteAdmUnit);
        return this;
    }

    public void setWebSiteAdmUnit(String webSiteAdmUnit) {
        this.webSiteAdmUnit = webSiteAdmUnit;
    }

    public String getWebSiteStatus() {
        return this.webSiteStatus;
    }

    public BpmIsmsL410 webSiteStatus(String webSiteStatus) {
        this.setWebSiteStatus(webSiteStatus);
        return this;
    }

    public void setWebSiteStatus(String webSiteStatus) {
        this.webSiteStatus = webSiteStatus;
    }

    public Instant getWebSiteEnableDate() {
        return this.webSiteEnableDate;
    }

    public BpmIsmsL410 webSiteEnableDate(Instant webSiteEnableDate) {
        this.setWebSiteEnableDate(webSiteEnableDate);
        return this;
    }

    public void setWebSiteEnableDate(Instant webSiteEnableDate) {
        this.webSiteEnableDate = webSiteEnableDate;
    }

    public String getWebSiteAdmName() {
        return this.webSiteAdmName;
    }

    public BpmIsmsL410 webSiteAdmName(String webSiteAdmName) {
        this.setWebSiteAdmName(webSiteAdmName);
        return this;
    }

    public void setWebSiteAdmName(String webSiteAdmName) {
        this.webSiteAdmName = webSiteAdmName;
    }

    public String getIsPccPis() {
        return this.isPccPis;
    }

    public BpmIsmsL410 isPccPis(String isPccPis) {
        this.setIsPccPis(isPccPis);
        return this;
    }

    public void setIsPccPis(String isPccPis) {
        this.isPccPis = isPccPis;
    }

    public String getPccPisAccount() {
        return this.pccPisAccount;
    }

    public BpmIsmsL410 pccPisAccount(String pccPisAccount) {
        this.setPccPisAccount(pccPisAccount);
        return this;
    }

    public void setPccPisAccount(String pccPisAccount) {
        this.pccPisAccount = pccPisAccount;
    }

    public String getPccPis() {
        return this.pccPis;
    }

    public BpmIsmsL410 pccPis(String pccPis) {
        this.setPccPis(pccPis);
        return this;
    }

    public void setPccPis(String pccPis) {
        this.pccPis = pccPis;
    }

    public String getPccPisChange() {
        return this.pccPisChange;
    }

    public BpmIsmsL410 pccPisChange(String pccPisChange) {
        this.setPccPisChange(pccPisChange);
        return this;
    }

    public void setPccPisChange(String pccPisChange) {
        this.pccPisChange = pccPisChange;
    }

    public String getPccPisAdmUnit() {
        return this.pccPisAdmUnit;
    }

    public BpmIsmsL410 pccPisAdmUnit(String pccPisAdmUnit) {
        this.setPccPisAdmUnit(pccPisAdmUnit);
        return this;
    }

    public void setPccPisAdmUnit(String pccPisAdmUnit) {
        this.pccPisAdmUnit = pccPisAdmUnit;
    }

    public String getPccPisStatus() {
        return this.pccPisStatus;
    }

    public BpmIsmsL410 pccPisStatus(String pccPisStatus) {
        this.setPccPisStatus(pccPisStatus);
        return this;
    }

    public void setPccPisStatus(String pccPisStatus) {
        this.pccPisStatus = pccPisStatus;
    }

    public Instant getPccPisEnableDate() {
        return this.pccPisEnableDate;
    }

    public BpmIsmsL410 pccPisEnableDate(Instant pccPisEnableDate) {
        this.setPccPisEnableDate(pccPisEnableDate);
        return this;
    }

    public void setPccPisEnableDate(Instant pccPisEnableDate) {
        this.pccPisEnableDate = pccPisEnableDate;
    }

    public String getPccPisAdmName() {
        return this.pccPisAdmName;
    }

    public BpmIsmsL410 pccPisAdmName(String pccPisAdmName) {
        this.setPccPisAdmName(pccPisAdmName);
        return this;
    }

    public void setPccPisAdmName(String pccPisAdmName) {
        this.pccPisAdmName = pccPisAdmName;
    }

    public String getIsOtherSys1() {
        return this.isOtherSys1;
    }

    public BpmIsmsL410 isOtherSys1(String isOtherSys1) {
        this.setIsOtherSys1(isOtherSys1);
        return this;
    }

    public void setIsOtherSys1(String isOtherSys1) {
        this.isOtherSys1 = isOtherSys1;
    }

    public String getOtherSys1ServerName() {
        return this.otherSys1ServerName;
    }

    public BpmIsmsL410 otherSys1ServerName(String otherSys1ServerName) {
        this.setOtherSys1ServerName(otherSys1ServerName);
        return this;
    }

    public void setOtherSys1ServerName(String otherSys1ServerName) {
        this.otherSys1ServerName = otherSys1ServerName;
    }

    public String getOtherSys1Account() {
        return this.otherSys1Account;
    }

    public BpmIsmsL410 otherSys1Account(String otherSys1Account) {
        this.setOtherSys1Account(otherSys1Account);
        return this;
    }

    public void setOtherSys1Account(String otherSys1Account) {
        this.otherSys1Account = otherSys1Account;
    }

    public String getOtherSys1() {
        return this.otherSys1;
    }

    public BpmIsmsL410 otherSys1(String otherSys1) {
        this.setOtherSys1(otherSys1);
        return this;
    }

    public void setOtherSys1(String otherSys1) {
        this.otherSys1 = otherSys1;
    }

    public String getOtherSys1Change() {
        return this.otherSys1Change;
    }

    public BpmIsmsL410 otherSys1Change(String otherSys1Change) {
        this.setOtherSys1Change(otherSys1Change);
        return this;
    }

    public void setOtherSys1Change(String otherSys1Change) {
        this.otherSys1Change = otherSys1Change;
    }

    public String getOtherSys1AdmUnit() {
        return this.otherSys1AdmUnit;
    }

    public BpmIsmsL410 otherSys1AdmUnit(String otherSys1AdmUnit) {
        this.setOtherSys1AdmUnit(otherSys1AdmUnit);
        return this;
    }

    public void setOtherSys1AdmUnit(String otherSys1AdmUnit) {
        this.otherSys1AdmUnit = otherSys1AdmUnit;
    }

    public String getOtherSys1Status() {
        return this.otherSys1Status;
    }

    public BpmIsmsL410 otherSys1Status(String otherSys1Status) {
        this.setOtherSys1Status(otherSys1Status);
        return this;
    }

    public void setOtherSys1Status(String otherSys1Status) {
        this.otherSys1Status = otherSys1Status;
    }

    public Instant getOtherSys1EnableDate() {
        return this.otherSys1EnableDate;
    }

    public BpmIsmsL410 otherSys1EnableDate(Instant otherSys1EnableDate) {
        this.setOtherSys1EnableDate(otherSys1EnableDate);
        return this;
    }

    public void setOtherSys1EnableDate(Instant otherSys1EnableDate) {
        this.otherSys1EnableDate = otherSys1EnableDate;
    }

    public String getOtherSys1AdmName() {
        return this.otherSys1AdmName;
    }

    public BpmIsmsL410 otherSys1AdmName(String otherSys1AdmName) {
        this.setOtherSys1AdmName(otherSys1AdmName);
        return this;
    }

    public void setOtherSys1AdmName(String otherSys1AdmName) {
        this.otherSys1AdmName = otherSys1AdmName;
    }

    public String getIsOtherSys2() {
        return this.isOtherSys2;
    }

    public BpmIsmsL410 isOtherSys2(String isOtherSys2) {
        this.setIsOtherSys2(isOtherSys2);
        return this;
    }

    public void setIsOtherSys2(String isOtherSys2) {
        this.isOtherSys2 = isOtherSys2;
    }

    public String getOtherSys2ServerName() {
        return this.otherSys2ServerName;
    }

    public BpmIsmsL410 otherSys2ServerName(String otherSys2ServerName) {
        this.setOtherSys2ServerName(otherSys2ServerName);
        return this;
    }

    public void setOtherSys2ServerName(String otherSys2ServerName) {
        this.otherSys2ServerName = otherSys2ServerName;
    }

    public String getOtherSys2Account() {
        return this.otherSys2Account;
    }

    public BpmIsmsL410 otherSys2Account(String otherSys2Account) {
        this.setOtherSys2Account(otherSys2Account);
        return this;
    }

    public void setOtherSys2Account(String otherSys2Account) {
        this.otherSys2Account = otherSys2Account;
    }

    public String getOtherSys2() {
        return this.otherSys2;
    }

    public BpmIsmsL410 otherSys2(String otherSys2) {
        this.setOtherSys2(otherSys2);
        return this;
    }

    public void setOtherSys2(String otherSys2) {
        this.otherSys2 = otherSys2;
    }

    public String getOtherSys2Change() {
        return this.otherSys2Change;
    }

    public BpmIsmsL410 otherSys2Change(String otherSys2Change) {
        this.setOtherSys2Change(otherSys2Change);
        return this;
    }

    public void setOtherSys2Change(String otherSys2Change) {
        this.otherSys2Change = otherSys2Change;
    }

    public String getOtherSys2AdmUnit() {
        return this.otherSys2AdmUnit;
    }

    public BpmIsmsL410 otherSys2AdmUnit(String otherSys2AdmUnit) {
        this.setOtherSys2AdmUnit(otherSys2AdmUnit);
        return this;
    }

    public void setOtherSys2AdmUnit(String otherSys2AdmUnit) {
        this.otherSys2AdmUnit = otherSys2AdmUnit;
    }

    public String getOtherSys2Status() {
        return this.otherSys2Status;
    }

    public BpmIsmsL410 otherSys2Status(String otherSys2Status) {
        this.setOtherSys2Status(otherSys2Status);
        return this;
    }

    public void setOtherSys2Status(String otherSys2Status) {
        this.otherSys2Status = otherSys2Status;
    }

    public Instant getOtherSys2EnableDate() {
        return this.otherSys2EnableDate;
    }

    public BpmIsmsL410 otherSys2EnableDate(Instant otherSys2EnableDate) {
        this.setOtherSys2EnableDate(otherSys2EnableDate);
        return this;
    }

    public void setOtherSys2EnableDate(Instant otherSys2EnableDate) {
        this.otherSys2EnableDate = otherSys2EnableDate;
    }

    public String getOtherSys2AdmName() {
        return this.otherSys2AdmName;
    }

    public BpmIsmsL410 otherSys2AdmName(String otherSys2AdmName) {
        this.setOtherSys2AdmName(otherSys2AdmName);
        return this;
    }

    public void setOtherSys2AdmName(String otherSys2AdmName) {
        this.otherSys2AdmName = otherSys2AdmName;
    }

    public String getIsOtherSys3() {
        return this.isOtherSys3;
    }

    public BpmIsmsL410 isOtherSys3(String isOtherSys3) {
        this.setIsOtherSys3(isOtherSys3);
        return this;
    }

    public void setIsOtherSys3(String isOtherSys3) {
        this.isOtherSys3 = isOtherSys3;
    }

    public String getOtherSys3ServerName() {
        return this.otherSys3ServerName;
    }

    public BpmIsmsL410 otherSys3ServerName(String otherSys3ServerName) {
        this.setOtherSys3ServerName(otherSys3ServerName);
        return this;
    }

    public void setOtherSys3ServerName(String otherSys3ServerName) {
        this.otherSys3ServerName = otherSys3ServerName;
    }

    public String getOtherSys3Account() {
        return this.otherSys3Account;
    }

    public BpmIsmsL410 otherSys3Account(String otherSys3Account) {
        this.setOtherSys3Account(otherSys3Account);
        return this;
    }

    public void setOtherSys3Account(String otherSys3Account) {
        this.otherSys3Account = otherSys3Account;
    }

    public String getOtherSys3() {
        return this.otherSys3;
    }

    public BpmIsmsL410 otherSys3(String otherSys3) {
        this.setOtherSys3(otherSys3);
        return this;
    }

    public void setOtherSys3(String otherSys3) {
        this.otherSys3 = otherSys3;
    }

    public String getOtherSys3Change() {
        return this.otherSys3Change;
    }

    public BpmIsmsL410 otherSys3Change(String otherSys3Change) {
        this.setOtherSys3Change(otherSys3Change);
        return this;
    }

    public void setOtherSys3Change(String otherSys3Change) {
        this.otherSys3Change = otherSys3Change;
    }

    public String getOtherSys3AdmUnit() {
        return this.otherSys3AdmUnit;
    }

    public BpmIsmsL410 otherSys3AdmUnit(String otherSys3AdmUnit) {
        this.setOtherSys3AdmUnit(otherSys3AdmUnit);
        return this;
    }

    public void setOtherSys3AdmUnit(String otherSys3AdmUnit) {
        this.otherSys3AdmUnit = otherSys3AdmUnit;
    }

    public String getOtherSys3Status() {
        return this.otherSys3Status;
    }

    public BpmIsmsL410 otherSys3Status(String otherSys3Status) {
        this.setOtherSys3Status(otherSys3Status);
        return this;
    }

    public void setOtherSys3Status(String otherSys3Status) {
        this.otherSys3Status = otherSys3Status;
    }

    public Instant getOtherSys3EnableDate() {
        return this.otherSys3EnableDate;
    }

    public BpmIsmsL410 otherSys3EnableDate(Instant otherSys3EnableDate) {
        this.setOtherSys3EnableDate(otherSys3EnableDate);
        return this;
    }

    public void setOtherSys3EnableDate(Instant otherSys3EnableDate) {
        this.otherSys3EnableDate = otherSys3EnableDate;
    }

    public String getOtherSys3AdmName() {
        return this.otherSys3AdmName;
    }

    public BpmIsmsL410 otherSys3AdmName(String otherSys3AdmName) {
        this.setOtherSys3AdmName(otherSys3AdmName);
        return this;
    }

    public void setOtherSys3AdmName(String otherSys3AdmName) {
        this.otherSys3AdmName = otherSys3AdmName;
    }

    public String getProcessInstanceStatus() {
        return this.processInstanceStatus;
    }

    public BpmIsmsL410 processInstanceStatus(String processInstanceStatus) {
        this.setProcessInstanceStatus(processInstanceStatus);
        return this;
    }

    public void setProcessInstanceStatus(String processInstanceStatus) {
        this.processInstanceStatus = processInstanceStatus;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public BpmIsmsL410 updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public BpmIsmsL410 updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public BpmIsmsL410 createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public BpmIsmsL410 createTime(Instant createTime) {
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
        if (!(o instanceof BpmIsmsL410)) {
            return false;
        }
        return formId != null && formId.equals(((BpmIsmsL410) o).formId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmIsmsL410{" +
            "formId='" + formId + '\'' +
            ", processInstanceId='" + processInstanceId + '\'' +
            ", applyDate=" + applyDate +
            ", filEmpid='" + filEmpid + '\'' +
            ", filName='" + filName + '\'' +
            ", filUnit='" + filUnit + '\'' +
            ", appEmpid='" + appEmpid + '\'' +
            ", appName='" + appName + '\'' +
            ", appEngName='" + appEngName + '\'' +
            ", appUnit1='" + appUnit1 + '\'' +
            ", appUnit2='" + appUnit2 + '\'' +
            ", position='" + position + '\'' +
            ", extNum='" + extNum + '\'' +
            ", isSubmit='" + isSubmit + '\'' +
            ", appReason='" + appReason + '\'' +
            ", isEnableDate='" + isEnableDate + '\'' +
            ", enableDate=" + enableDate +
            ", isOther='" + isOther + '\'' +
            ", otherReason='" + otherReason + '\'' +
            ", isHrSys='" + isHrSys + '\'' +
            ", hrSys='" + hrSys + '\'' +
            ", hrSysChange='" + hrSysChange + '\'' +
            ", hrSysAdmUnit='" + hrSysAdmUnit + '\'' +
            ", hrSysStatus='" + hrSysStatus + '\'' +
            ", hrSysEnableDate=" + hrSysEnableDate +
            ", hrSysAdmName='" + hrSysAdmName + '\'' +
            ", isAdSys='" + isAdSys + '\'' +
            ", adAccount='" + adAccount + '\'' +
            ", adSys='" + adSys + '\'' +
            ", adSysChange='" + adSysChange + '\'' +
            ", adSysAdmUnit='" + adSysAdmUnit + '\'' +
            ", adSysStatus='" + adSysStatus + '\'' +
            ", adSysEnableDate=" + adSysEnableDate +
            ", adSysAdmName='" + adSysAdmName + '\'' +
            ", isMeetingRoom='" + isMeetingRoom + '\'' +
            ", meetingRoom='" + meetingRoom + '\'' +
            ", meetingEoomChange='" + meetingEoomChange + '\'' +
            ", meetingRoomAdmUnit='" + meetingRoomAdmUnit + '\'' +
            ", meetingRoomStatus='" + meetingRoomStatus + '\'' +
            ", meetingRoomEnableDate=" + meetingRoomEnableDate +
            ", meetingRoomAdmName='" + meetingRoomAdmName + '\'' +
            ", isOdSys='" + isOdSys + '\'' +
            ", odSysRole='" + odSysRole + '\'' +
            ", odSys='" + odSys + '\'' +
            ", odSysOther='" + odSysOther + '\'' +
            ", odSysAdmUnit='" + odSysAdmUnit + '\'' +
            ", odSysStatus='" + odSysStatus + '\'' +
            ", odSysEnableDate=" + odSysEnableDate +
            ", odSysAdmName='" + odSysAdmName + '\'' +
            ", isEmailSys='" + isEmailSys + '\'' +
            ", emailSysAccount='" + emailSysAccount + '\'' +
            ", emailSys='" + emailSys + '\'' +
            ", emailApply1='" + emailApply1 + '\'' +
            ", emailApply2='" + emailApply2 + '\'' +
            ", emailSysChange='" + emailSysChange + '\'' +
            ", emailSysAdmUnit='" + emailSysAdmUnit + '\'' +
            ", emailSysStatus='" + emailSysStatus + '\'' +
            ", emailSysEnableDate=" + emailSysEnableDate +
            ", emailSysAdmName='" + emailSysAdmName + '\'' +
            ", isWebSite='" + isWebSite + '\'' +
            ", isPccWww='" + isPccWww + '\'' +
            ", isPccHome='" + isPccHome + '\'' +
            ", webSite='" + webSite + '\'' +
            ", isUnitAdm='" + isUnitAdm + '\'' +
            ", isUnitDataMgr='" + isUnitDataMgr + '\'' +
            ", isWebSiteOther='" + isWebSiteOther + '\'' +
            ", webSiteOther='" + webSiteOther + '\'' +
            ", webSiteAdmUnit='" + webSiteAdmUnit + '\'' +
            ", webSiteStatus='" + webSiteStatus + '\'' +
            ", webSiteEnableDate=" + webSiteEnableDate +
            ", webSiteAdmName='" + webSiteAdmName + '\'' +
            ", isPccPis='" + isPccPis + '\'' +
            ", pccPisAccount='" + pccPisAccount + '\'' +
            ", pccPis='" + pccPis + '\'' +
            ", pccPisChange='" + pccPisChange + '\'' +
            ", pccPisAdmUnit='" + pccPisAdmUnit + '\'' +
            ", pccPisStatus='" + pccPisStatus + '\'' +
            ", pccPisEnableDate=" + pccPisEnableDate +
            ", pccPisAdmName='" + pccPisAdmName + '\'' +
            ", isOtherSys1='" + isOtherSys1 + '\'' +
            ", otherSys1ServerName='" + otherSys1ServerName + '\'' +
            ", otherSys1Account='" + otherSys1Account + '\'' +
            ", otherSys1='" + otherSys1 + '\'' +
            ", otherSys1Change='" + otherSys1Change + '\'' +
            ", otherSys1AdmUnit='" + otherSys1AdmUnit + '\'' +
            ", otherSys1Status='" + otherSys1Status + '\'' +
            ", otherSys1EnableDate=" + otherSys1EnableDate +
            ", otherSys1AdmName='" + otherSys1AdmName + '\'' +
            ", isOtherSys2='" + isOtherSys2 + '\'' +
            ", otherSys2ServerName='" + otherSys2ServerName + '\'' +
            ", otherSys2Account='" + otherSys2Account + '\'' +
            ", otherSys2='" + otherSys2 + '\'' +
            ", otherSys2Change='" + otherSys2Change + '\'' +
            ", otherSys2AdmUnit='" + otherSys2AdmUnit + '\'' +
            ", otherSys2Status='" + otherSys2Status + '\'' +
            ", otherSys2EnableDate=" + otherSys2EnableDate +
            ", otherSys2AdmName='" + otherSys2AdmName + '\'' +
            ", isOtherSys3='" + isOtherSys3 + '\'' +
            ", otherSys3ServerName='" + otherSys3ServerName + '\'' +
            ", otherSys3Account='" + otherSys3Account + '\'' +
            ", otherSys3='" + otherSys3 + '\'' +
            ", otherSys3Change='" + otherSys3Change + '\'' +
            ", otherSys3AdmUnit='" + otherSys3AdmUnit + '\'' +
            ", otherSys3Status='" + otherSys3Status + '\'' +
            ", otherSys3EnableDate=" + otherSys3EnableDate +
            ", otherSys3AdmName='" + otherSys3AdmName + '\'' +
            ", processInstanceStatus='" + processInstanceStatus + '\'' +
            ", updateUser='" + updateUser + '\'' +
            ", updateTime=" + updateTime +
            ", createUser='" + createUser + '\'' +
            ", createTime=" + createTime +
            '}';
    }
}
