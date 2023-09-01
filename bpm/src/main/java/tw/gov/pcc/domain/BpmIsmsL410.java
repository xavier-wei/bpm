package tw.gov.pcc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


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
    private Timestamp applyDate;

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
    private Timestamp enableDate;

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
    private Timestamp hrSysEnableDate;

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
    private Timestamp adSysEnableDate;

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
    private Timestamp meetingRoomEnableDate;

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
    private Timestamp odSysEnableDate;

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
    private Timestamp emailSysEnableDate;

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
    private Timestamp webSiteEnableDate;

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
    private Timestamp pccPisEnableDate;

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
    private Timestamp otherSys1EnableDate;

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
    private Timestamp otherSys2EnableDate;

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
    private Timestamp otherSys3EnableDate;

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
    private Timestamp updateTime;

    @NotNull
    @Size(max = 20)
    @Column(name = "create_user", length = 20, nullable = false)
    private String createUser;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    public String getAppEngName() {
        return appEngName;
    }

    public void setAppEngName(String appEngName) {
        this.appEngName = appEngName;
    }

    public String getAppUnit1() {
        return appUnit1;
    }

    public void setAppUnit1(String appUnit1) {
        this.appUnit1 = appUnit1;
    }

    public String getAppUnit2() {
        return appUnit2;
    }

    public void setAppUnit2(String appUnit2) {
        this.appUnit2 = appUnit2;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getExtNum() {
        return extNum;
    }

    public void setExtNum(String extNum) {
        this.extNum = extNum;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getAppReason() {
        return appReason;
    }

    public void setAppReason(String appReason) {
        this.appReason = appReason;
    }

    public String getIsEnableDate() {
        return isEnableDate;
    }

    public void setIsEnableDate(String isEnableDate) {
        this.isEnableDate = isEnableDate;
    }

    public Timestamp getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Timestamp enableDate) {
        this.enableDate = enableDate;
    }

    public String getIsOther() {
        return isOther;
    }

    public void setIsOther(String isOther) {
        this.isOther = isOther;
    }

    public String getOtherReason() {
        return otherReason;
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
        return hrSys;
    }

    public void setHrSys(String hrSys) {
        this.hrSys = hrSys;
    }

    public String getHrSysChange() {
        return hrSysChange;
    }

    public void setHrSysChange(String hrSysChange) {
        this.hrSysChange = hrSysChange;
    }

    public String getHrSysAdmUnit() {
        return hrSysAdmUnit;
    }

    public void setHrSysAdmUnit(String hrSysAdmUnit) {
        this.hrSysAdmUnit = hrSysAdmUnit;
    }

    public String getHrSysStatus() {
        return hrSysStatus;
    }

    public void setHrSysStatus(String hrSysStatus) {
        this.hrSysStatus = hrSysStatus;
    }

    public Timestamp getHrSysEnableDate() {
        return hrSysEnableDate;
    }

    public void setHrSysEnableDate(Timestamp hrSysEnableDate) {
        this.hrSysEnableDate = hrSysEnableDate;
    }

    public String getHrSysAdmName() {
        return hrSysAdmName;
    }

    public void setHrSysAdmName(String hrSysAdmName) {
        this.hrSysAdmName = hrSysAdmName;
    }

    public String getIsAdSys() {
        return isAdSys;
    }

    public void setIsAdSys(String isAdSys) {
        this.isAdSys = isAdSys;
    }

    public String getAdAccount() {
        return adAccount;
    }

    public void setAdAccount(String adAccount) {
        this.adAccount = adAccount;
    }

    public String getAdSys() {
        return adSys;
    }

    public void setAdSys(String adSys) {
        this.adSys = adSys;
    }

    public String getAdSysChange() {
        return adSysChange;
    }

    public void setAdSysChange(String adSysChange) {
        this.adSysChange = adSysChange;
    }

    public String getAdSysAdmUnit() {
        return adSysAdmUnit;
    }

    public void setAdSysAdmUnit(String adSysAdmUnit) {
        this.adSysAdmUnit = adSysAdmUnit;
    }

    public String getAdSysStatus() {
        return adSysStatus;
    }

    public void setAdSysStatus(String adSysStatus) {
        this.adSysStatus = adSysStatus;
    }

    public Timestamp getAdSysEnableDate() {
        return adSysEnableDate;
    }

    public void setAdSysEnableDate(Timestamp adSysEnableDate) {
        this.adSysEnableDate = adSysEnableDate;
    }

    public String getAdSysAdmName() {
        return adSysAdmName;
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

    public Timestamp getMeetingRoomEnableDate() {
        return meetingRoomEnableDate;
    }

    public void setMeetingRoomEnableDate(Timestamp meetingRoomEnableDate) {
        this.meetingRoomEnableDate = meetingRoomEnableDate;
    }

    public String getMeetingRoomAdmName() {
        return meetingRoomAdmName;
    }

    public void setMeetingRoomAdmName(String meetingRoomAdmName) {
        this.meetingRoomAdmName = meetingRoomAdmName;
    }

    public String getIsOdSys() {
        return isOdSys;
    }

    public void setIsOdSys(String isOdSys) {
        this.isOdSys = isOdSys;
    }

    public String getOdSysRole() {
        return odSysRole;
    }

    public void setOdSysRole(String odSysRole) {
        this.odSysRole = odSysRole;
    }

    public String getOdSys() {
        return odSys;
    }

    public void setOdSys(String odSys) {
        this.odSys = odSys;
    }

    public String getOdSysOther() {
        return odSysOther;
    }

    public void setOdSysOther(String odSysOther) {
        this.odSysOther = odSysOther;
    }

    public String getOdSysAdmUnit() {
        return odSysAdmUnit;
    }

    public void setOdSysAdmUnit(String odSysAdmUnit) {
        this.odSysAdmUnit = odSysAdmUnit;
    }

    public String getOdSysStatus() {
        return odSysStatus;
    }

    public void setOdSysStatus(String odSysStatus) {
        this.odSysStatus = odSysStatus;
    }

    public Timestamp getOdSysEnableDate() {
        return odSysEnableDate;
    }

    public void setOdSysEnableDate(Timestamp odSysEnableDate) {
        this.odSysEnableDate = odSysEnableDate;
    }

    public String getOdSysAdmName() {
        return odSysAdmName;
    }

    public void setOdSysAdmName(String odSysAdmName) {
        this.odSysAdmName = odSysAdmName;
    }

    public String getIsEmailSys() {
        return isEmailSys;
    }

    public void setIsEmailSys(String isEmailSys) {
        this.isEmailSys = isEmailSys;
    }

    public String getEmailSysAccount() {
        return emailSysAccount;
    }

    public void setEmailSysAccount(String emailSysAccount) {
        this.emailSysAccount = emailSysAccount;
    }

    public String getEmailSys() {
        return emailSys;
    }

    public void setEmailSys(String emailSys) {
        this.emailSys = emailSys;
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

    public String getEmailSysChange() {
        return emailSysChange;
    }

    public void setEmailSysChange(String emailSysChange) {
        this.emailSysChange = emailSysChange;
    }

    public String getEmailSysAdmUnit() {
        return emailSysAdmUnit;
    }

    public void setEmailSysAdmUnit(String emailSysAdmUnit) {
        this.emailSysAdmUnit = emailSysAdmUnit;
    }

    public String getEmailSysStatus() {
        return emailSysStatus;
    }

    public void setEmailSysStatus(String emailSysStatus) {
        this.emailSysStatus = emailSysStatus;
    }

    public Timestamp getEmailSysEnableDate() {
        return emailSysEnableDate;
    }

    public void setEmailSysEnableDate(Timestamp emailSysEnableDate) {
        this.emailSysEnableDate = emailSysEnableDate;
    }

    public String getEmailSysAdmName() {
        return emailSysAdmName;
    }

    public void setEmailSysAdmName(String emailSysAdmName) {
        this.emailSysAdmName = emailSysAdmName;
    }

    public String getIsWebSite() {
        return isWebSite;
    }

    public void setIsWebSite(String isWebSite) {
        this.isWebSite = isWebSite;
    }

    public String getIsPccWww() {
        return isPccWww;
    }

    public void setIsPccWww(String isPccWww) {
        this.isPccWww = isPccWww;
    }

    public String getIsPccHome() {
        return isPccHome;
    }

    public void setIsPccHome(String isPccHome) {
        this.isPccHome = isPccHome;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
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

    public String getWebSiteOther() {
        return webSiteOther;
    }

    public void setWebSiteOther(String webSiteOther) {
        this.webSiteOther = webSiteOther;
    }

    public String getWebSiteAdmUnit() {
        return webSiteAdmUnit;
    }

    public void setWebSiteAdmUnit(String webSiteAdmUnit) {
        this.webSiteAdmUnit = webSiteAdmUnit;
    }

    public String getWebSiteStatus() {
        return webSiteStatus;
    }

    public void setWebSiteStatus(String webSiteStatus) {
        this.webSiteStatus = webSiteStatus;
    }

    public Timestamp getWebSiteEnableDate() {
        return webSiteEnableDate;
    }

    public void setWebSiteEnableDate(Timestamp webSiteEnableDate) {
        this.webSiteEnableDate = webSiteEnableDate;
    }

    public String getWebSiteAdmName() {
        return webSiteAdmName;
    }

    public void setWebSiteAdmName(String webSiteAdmName) {
        this.webSiteAdmName = webSiteAdmName;
    }

    public String getIsPccPis() {
        return isPccPis;
    }

    public void setIsPccPis(String isPccPis) {
        this.isPccPis = isPccPis;
    }

    public String getPccPisAccount() {
        return pccPisAccount;
    }

    public void setPccPisAccount(String pccPisAccount) {
        this.pccPisAccount = pccPisAccount;
    }

    public String getPccPis() {
        return pccPis;
    }

    public void setPccPis(String pccPis) {
        this.pccPis = pccPis;
    }

    public String getPccPisChange() {
        return pccPisChange;
    }

    public void setPccPisChange(String pccPisChange) {
        this.pccPisChange = pccPisChange;
    }

    public String getPccPisAdmUnit() {
        return pccPisAdmUnit;
    }

    public void setPccPisAdmUnit(String pccPisAdmUnit) {
        this.pccPisAdmUnit = pccPisAdmUnit;
    }

    public String getPccPisStatus() {
        return pccPisStatus;
    }

    public void setPccPisStatus(String pccPisStatus) {
        this.pccPisStatus = pccPisStatus;
    }

    public Timestamp getPccPisEnableDate() {
        return pccPisEnableDate;
    }

    public void setPccPisEnableDate(Timestamp pccPisEnableDate) {
        this.pccPisEnableDate = pccPisEnableDate;
    }

    public String getPccPisAdmName() {
        return pccPisAdmName;
    }

    public void setPccPisAdmName(String pccPisAdmName) {
        this.pccPisAdmName = pccPisAdmName;
    }

    public String getIsOtherSys1() {
        return isOtherSys1;
    }

    public void setIsOtherSys1(String isOtherSys1) {
        this.isOtherSys1 = isOtherSys1;
    }

    public String getOtherSys1ServerName() {
        return otherSys1ServerName;
    }

    public void setOtherSys1ServerName(String otherSys1ServerName) {
        this.otherSys1ServerName = otherSys1ServerName;
    }

    public String getOtherSys1Account() {
        return otherSys1Account;
    }

    public void setOtherSys1Account(String otherSys1Account) {
        this.otherSys1Account = otherSys1Account;
    }

    public String getOtherSys1() {
        return otherSys1;
    }

    public void setOtherSys1(String otherSys1) {
        this.otherSys1 = otherSys1;
    }

    public String getOtherSys1Change() {
        return otherSys1Change;
    }

    public void setOtherSys1Change(String otherSys1Change) {
        this.otherSys1Change = otherSys1Change;
    }

    public String getOtherSys1AdmUnit() {
        return otherSys1AdmUnit;
    }

    public void setOtherSys1AdmUnit(String otherSys1AdmUnit) {
        this.otherSys1AdmUnit = otherSys1AdmUnit;
    }

    public String getOtherSys1Status() {
        return otherSys1Status;
    }

    public void setOtherSys1Status(String otherSys1Status) {
        this.otherSys1Status = otherSys1Status;
    }

    public Timestamp getOtherSys1EnableDate() {
        return otherSys1EnableDate;
    }

    public void setOtherSys1EnableDate(Timestamp otherSys1EnableDate) {
        this.otherSys1EnableDate = otherSys1EnableDate;
    }

    public String getOtherSys1AdmName() {
        return otherSys1AdmName;
    }

    public void setOtherSys1AdmName(String otherSys1AdmName) {
        this.otherSys1AdmName = otherSys1AdmName;
    }

    public String getIsOtherSys2() {
        return isOtherSys2;
    }

    public void setIsOtherSys2(String isOtherSys2) {
        this.isOtherSys2 = isOtherSys2;
    }

    public String getOtherSys2ServerName() {
        return otherSys2ServerName;
    }

    public void setOtherSys2ServerName(String otherSys2ServerName) {
        this.otherSys2ServerName = otherSys2ServerName;
    }

    public String getOtherSys2Account() {
        return otherSys2Account;
    }

    public void setOtherSys2Account(String otherSys2Account) {
        this.otherSys2Account = otherSys2Account;
    }

    public String getOtherSys2() {
        return otherSys2;
    }

    public void setOtherSys2(String otherSys2) {
        this.otherSys2 = otherSys2;
    }

    public String getOtherSys2Change() {
        return otherSys2Change;
    }

    public void setOtherSys2Change(String otherSys2Change) {
        this.otherSys2Change = otherSys2Change;
    }

    public String getOtherSys2AdmUnit() {
        return otherSys2AdmUnit;
    }

    public void setOtherSys2AdmUnit(String otherSys2AdmUnit) {
        this.otherSys2AdmUnit = otherSys2AdmUnit;
    }

    public String getOtherSys2Status() {
        return otherSys2Status;
    }

    public void setOtherSys2Status(String otherSys2Status) {
        this.otherSys2Status = otherSys2Status;
    }

    public Timestamp getOtherSys2EnableDate() {
        return otherSys2EnableDate;
    }

    public void setOtherSys2EnableDate(Timestamp otherSys2EnableDate) {
        this.otherSys2EnableDate = otherSys2EnableDate;
    }

    public String getOtherSys2AdmName() {
        return otherSys2AdmName;
    }

    public void setOtherSys2AdmName(String otherSys2AdmName) {
        this.otherSys2AdmName = otherSys2AdmName;
    }

    public String getIsOtherSys3() {
        return isOtherSys3;
    }

    public void setIsOtherSys3(String isOtherSys3) {
        this.isOtherSys3 = isOtherSys3;
    }

    public String getOtherSys3ServerName() {
        return otherSys3ServerName;
    }

    public void setOtherSys3ServerName(String otherSys3ServerName) {
        this.otherSys3ServerName = otherSys3ServerName;
    }

    public String getOtherSys3Account() {
        return otherSys3Account;
    }

    public void setOtherSys3Account(String otherSys3Account) {
        this.otherSys3Account = otherSys3Account;
    }

    public String getOtherSys3() {
        return otherSys3;
    }

    public void setOtherSys3(String otherSys3) {
        this.otherSys3 = otherSys3;
    }

    public String getOtherSys3Change() {
        return otherSys3Change;
    }

    public void setOtherSys3Change(String otherSys3Change) {
        this.otherSys3Change = otherSys3Change;
    }

    public String getOtherSys3AdmUnit() {
        return otherSys3AdmUnit;
    }

    public void setOtherSys3AdmUnit(String otherSys3AdmUnit) {
        this.otherSys3AdmUnit = otherSys3AdmUnit;
    }

    public String getOtherSys3Status() {
        return otherSys3Status;
    }

    public void setOtherSys3Status(String otherSys3Status) {
        this.otherSys3Status = otherSys3Status;
    }

    public Timestamp getOtherSys3EnableDate() {
        return otherSys3EnableDate;
    }

    public void setOtherSys3EnableDate(Timestamp otherSys3EnableDate) {
        this.otherSys3EnableDate = otherSys3EnableDate;
    }

    public String getOtherSys3AdmName() {
        return otherSys3AdmName;
    }

    public void setOtherSys3AdmName(String otherSys3AdmName) {
        this.otherSys3AdmName = otherSys3AdmName;
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
