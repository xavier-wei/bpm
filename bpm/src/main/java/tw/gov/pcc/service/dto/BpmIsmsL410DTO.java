package tw.gov.pcc.service.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link tw.gov.pcc.domain.BpmIsmsL410} entity.
 */
public class BpmIsmsL410DTO implements Serializable {

    @Size(max = 50)
    private String formId;

    @NotNull
    @Size(max = 50)
    private String processInstanceId;

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
    @Size(max = 20)
    private String appEngName;
    @NotNull
    @Size(max = 30)
    private String appUnit;
    @NotNull
    @Size(max = 20)
    private String position;

    @Size(max = 20)
    private String extNum;

    @Size(max = 1)
    private String isSubmit;

    @Size(max = 1)
    private String appReason;

    @Size(max = 1)
    private String isEnableDate;

    private Timestamp enableDate;

    @Size(max = 1)
    private String isOther;

    @Size(max = 100)
    private String otherReason;

    @Size(max = 1)
    private String isHrSys; // 人事差勤系統

    @Size(max = 1)
    private String hrSys;

    @Size(max = 100)
    private String hrSysChange;

    @Size(max = 20)
    private String hrSysAdmUnit;

    @Size(max = 20)
    private String hrSysStatus;

    private Timestamp hrSysEnableDate;

    @Size(max = 20)
    private String hrSysAdmName;

    @Size(max = 1)
    private String isAdSys; //AD系統帳號

    @Size(max = 20)
    private String adAccount;

    @Size(max = 1)
    private String adSys;

    @Size(max = 20)
    private String adSysChange;

    @Size(max = 20)
    private String adSysAdmUnit;

    @Size(max = 20)
    private String adSysStatus;

    private Timestamp adSysEnableDate;
    @Size(max = 20)
    private String adSysAdmName;
    @Size(max = 1)
    private String isMeetingRoom; // 會議室管理系統
    @Size(max = 1)
    private String meetingRoom;
    @Size(max = 100)
    private String meetingEoomChange;
    @Size(max = 20)
    private String meetingRoomAdmUnit;

    private String meetingRoomStatus;
    @Column(name = "meeting_room_enable_date")
    private Timestamp meetingRoomEnableDate;

    @Size(max = 20)
    private String meetingRoomAdmName;

    @Size(max = 1)
    private String isOdSys; // 公文系統帳號

    @Size(max = 20)
    private String odSysRole;

    @Size(max = 1)
    private String odSys;

    @Size(max = 20)
    private String odSysOther;

    @Size(max = 20)
    private String odSysAdmUnit;

    @Size(max = 20)
    private String odSysStatus;

    private Timestamp odSysEnableDate;

    @Size(max = 20)
    private String odSysAdmName;

    @Size(max = 1)
    private String isEmailSys;  // 電子郵件帳號

    @Size(max = 20)
    private String emailSysAccount;

    @Size(max = 1)
    private String emailSys;

    @Size(max = 20)
    private String emailApply1;

    @Size(max = 20)
    private String emailApply2;

    @Size(max = 20)
    private String emailSysChange;

    @Size(max = 20)
    private String emailSysAdmUnit;

    @Size(max = 20)
    private String emailSysStatus;

    private Timestamp emailSysEnableDate;

    @Size(max = 20)
    private String emailSysAdmName;

    @Size(max = 1)
    private String isWebSite; // 網站帳號

    @Size(max = 1)
    private String isPccWww;

    @Size(max = 1)
    private String isPccHome;

    @Size(max = 1)
    private String webSite;

    @Size(max = 1)
    private String isUnitAdm;

    @Size(max = 1)
    private String isUnitDataMgr;

    @Size(max = 1)
    private String isWebSiteOther;

    @Size(max = 20)
    private String webSiteOther;

    @Size(max = 20)
    private String webSiteAdmUnit;

    @Size(max = 20)
    private String webSiteStatus;

    private Timestamp webSiteEnableDate;

    @Size(max = 20)
    private String webSiteAdmName;

    @Size(max = 1)
    private String isPccPis; // 電子採購網帳號

    @Size(max = 20)
    private String pccPisAccount;

    @Size(max = 1)
    private String pccPis;

    @Size(max = 20)
    private String pccPisChange;

    @Size(max = 20)
    private String pccPisAdmUnit;

    @Size(max = 20)
    private String pccPisStatus;

    private Timestamp pccPisEnableDate;

    @Size(max = 20)
    private String pccPisAdmName;

    @Size(max = 1)
    private String isEngAndPrjInfoSys; // 技師與工程技術顧問公司管理資訊系統

    @Size(max = 20)
    private String engAndPrjInfoSysAccount;

    @Size(max = 1)
    private String engAndPrjInfoSys;

    @Size(max = 20)
    private String engAndPrjInfoSysChange;

    @Size(max = 20)
    private String engAndPrjInfoSysAdmUnit;

    @Size(max = 20)
    private String engAndPrjInfoSysStatus;

    private Timestamp engAndPrjInfoSysEnableDate;

    @Size(max = 20)
    private String engAndPrjInfoSysAdmName;
    @Size(max = 1)
    private String isRevSys; // 公共工程案件審議資訊系統

    @Size(max = 20)
    private String revSysAccount;

    @Size(max = 1)
    private String revSys;

    @Size(max = 20)
    private String revSysChange;

    @Size(max = 20)
    private String revSysAdmUnit;

    @Size(max = 20)
    private String revSysStatus;

    private Timestamp revSysEnableDate;

    @Size(max = 20)
    private String revSysAdmName;

    @Size(max = 1)
    private String isRecSys; // 災後復建工程經費審議及執行資訊系統
    @Size(max = 20)
    private String recSysAccount;

    @Size(max = 1)
    private String recSys;

    @Size(max = 20)
    private String recSysChange;

    @Size(max = 20)
    private String recSysAdmUnit;

    @Size(max = 20)
    private String recSysStatus;

    private Timestamp recSysEnableDate;

    @Size(max = 20)
    private String recSysAdmName;

    @Size(max = 1)
    private String isBidSys; // 公共工程標案管理系統

    @Size(max = 20)
    private String bidSysAccount;

    @Size(max = 1)
    private String bidSys;

    @Size(max = 20)
    private String bidSysChange;

    @Size(max = 20)
    private String bidSysAdmUnit;

    @Size(max = 20)
    private String bidSysStatus;

    private Timestamp bidSysEnableDate;

    @Size(max = 20)
    private String bidSysAdmName;

    @Size(max = 1)
    private String isOtherSys1; // 其他系統1

    @Size(max = 20)
    private String otherSys1ServerName;

    @Size(max = 20)
    private String otherSys1Account;

    @Size(max = 1)
    private String otherSys1;

    @Size(max = 20)
    private String otherSys1Change;

    @Size(max = 20)
    private String otherSys1AdmUnit;

    @Size(max = 20)
    private String otherSys1Status;

    private Timestamp otherSys1EnableDate;

    @Size(max = 20)
    private String otherSys1AdmName;

    @Size(max = 1)
    private String isOtherSys2; // 其他系統2

    @Size(max = 20)
    private String otherSys2ServerName;

    @Size(max = 20)
    private String otherSys2Account;

    @Size(max = 1)
    private String otherSys2;

    @Size(max = 20)
    private String otherSys2Change;

    @Size(max = 20)
    private String otherSys2AdmUnit;

    @Size(max = 20)
    private String otherSys2Status;

    private Timestamp otherSys2EnableDate;

    @Size(max = 20)
    private String otherSys2AdmName;

    @Size(max = 1)
    private String isOtherSys3; // 其他系統3

    @Size(max = 20)
    private String otherSys3ServerName;

    @Size(max = 20)
    private String otherSys3Account;

    @Size(max = 1)
    private String otherSys3;

    @Size(max = 20)
    private String otherSys3Change;

    @Size(max = 20)
    private String otherSys3AdmUnit;

    @Size(max = 20)
    private String otherSys3Status;

    private Timestamp otherSys3EnableDate;

    @Size(max = 20)
    private String otherSys3AdmName;

    @Size(max = 1)
    private String processInstanceStatus;

    @Size(max = 20)
    private String updateUser;

    private Timestamp updateTime;

    @NotNull
    @Size(max = 20)
    private String createUser;

    @NotNull
    private Timestamp createTime;

    private String formName;
    private String signUnit;
    private String signer;

    private List<HashMap<String, HashMap<String, Object>>> l410Variables;

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

    public String getAppUnit() {
        return appUnit;
    }

    public void setAppUnit(String appUnit) {
        this.appUnit = appUnit;
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

    public String getIsEngAndPrjInfoSys() {
        return isEngAndPrjInfoSys;
    }

    public void setIsEngAndPrjInfoSys(String isEngAndPrjInfoSys) {
        this.isEngAndPrjInfoSys = isEngAndPrjInfoSys;
    }

    public String getEngAndPrjInfoSysAccount() {
        return engAndPrjInfoSysAccount;
    }

    public void setEngAndPrjInfoSysAccount(String engAndPrjInfoSysAccount) {
        this.engAndPrjInfoSysAccount = engAndPrjInfoSysAccount;
    }

    public String getEngAndPrjInfoSys() {
        return engAndPrjInfoSys;
    }

    public void setEngAndPrjInfoSys(String engAndPrjInfoSys) {
        this.engAndPrjInfoSys = engAndPrjInfoSys;
    }

    public String getEngAndPrjInfoSysChange() {
        return engAndPrjInfoSysChange;
    }

    public void setEngAndPrjInfoSysChange(String engAndPrjInfoSysChange) {
        this.engAndPrjInfoSysChange = engAndPrjInfoSysChange;
    }

    public String getEngAndPrjInfoSysAdmUnit() {
        return engAndPrjInfoSysAdmUnit;
    }

    public void setEngAndPrjInfoSysAdmUnit(String engAndPrjInfoSysAdmUnit) {
        this.engAndPrjInfoSysAdmUnit = engAndPrjInfoSysAdmUnit;
    }

    public String getEngAndPrjInfoSysStatus() {
        return engAndPrjInfoSysStatus;
    }

    public void setEngAndPrjInfoSysStatus(String engAndPrjInfoSysStatus) {
        this.engAndPrjInfoSysStatus = engAndPrjInfoSysStatus;
    }

    public Timestamp getEngAndPrjInfoSysEnableDate() {
        return engAndPrjInfoSysEnableDate;
    }

    public void setEngAndPrjInfoSysEnableDate(Timestamp engAndPrjInfoSysEnableDate) {
        this.engAndPrjInfoSysEnableDate = engAndPrjInfoSysEnableDate;
    }

    public String getEngAndPrjInfoSysAdmName() {
        return engAndPrjInfoSysAdmName;
    }

    public void setEngAndPrjInfoSysAdmName(String engAndPrjInfoSysAdmName) {
        this.engAndPrjInfoSysAdmName = engAndPrjInfoSysAdmName;
    }

    public String getIsRevSys() {
        return isRevSys;
    }

    public void setIsRevSys(String isRevSys) {
        this.isRevSys = isRevSys;
    }

    public String getRevSysAccount() {
        return revSysAccount;
    }

    public void setRevSysAccount(String revSysAccount) {
        this.revSysAccount = revSysAccount;
    }

    public String getRevSys() {
        return revSys;
    }

    public void setRevSys(String revSys) {
        this.revSys = revSys;
    }

    public String getRevSysChange() {
        return revSysChange;
    }

    public void setRevSysChange(String revSysChange) {
        this.revSysChange = revSysChange;
    }

    public String getRevSysAdmUnit() {
        return revSysAdmUnit;
    }

    public void setRevSysAdmUnit(String revSysAdmUnit) {
        this.revSysAdmUnit = revSysAdmUnit;
    }

    public String getRevSysStatus() {
        return revSysStatus;
    }

    public void setRevSysStatus(String revSysStatus) {
        this.revSysStatus = revSysStatus;
    }

    public Timestamp getRevSysEnableDate() {
        return revSysEnableDate;
    }

    public void setRevSysEnableDate(Timestamp revSysEnableDate) {
        this.revSysEnableDate = revSysEnableDate;
    }

    public String getRevSysAdmName() {
        return revSysAdmName;
    }

    public void setRevSysAdmName(String revSysAdmName) {
        this.revSysAdmName = revSysAdmName;
    }

    public String getIsBidSys() {
        return isBidSys;
    }

    public void setIsBidSys(String isBidSys) {
        this.isBidSys = isBidSys;
    }

    public String getBidSysAccount() {
        return bidSysAccount;
    }

    public void setBidSysAccount(String bidSysAccount) {
        this.bidSysAccount = bidSysAccount;
    }

    public String getBidSys() {
        return bidSys;
    }

    public void setBidSys(String bidSys) {
        this.bidSys = bidSys;
    }

    public String getBidSysChange() {
        return bidSysChange;
    }

    public void setBidSysChange(String bidSysChange) {
        this.bidSysChange = bidSysChange;
    }

    public String getBidSysAdmUnit() {
        return bidSysAdmUnit;
    }

    public void setBidSysAdmUnit(String bidSysAdmUnit) {
        this.bidSysAdmUnit = bidSysAdmUnit;
    }

    public String getBidSysStatus() {
        return bidSysStatus;
    }

    public void setBidSysStatus(String bidSysStatus) {
        this.bidSysStatus = bidSysStatus;
    }

    public Timestamp getBidSysEnableDate() {
        return bidSysEnableDate;
    }

    public void setBidSysEnableDate(Timestamp bidSysEnableDate) {
        this.bidSysEnableDate = bidSysEnableDate;
    }

    public String getBidSysAdmName() {
        return bidSysAdmName;
    }

    public void setBidSysAdmName(String bidSysAdmName) {
        this.bidSysAdmName = bidSysAdmName;
    }

    public String getIsRecSys() {
        return isRecSys;
    }

    public void setIsRecSys(String isRecSys) {
        this.isRecSys = isRecSys;
    }

    public String getRecSysAccount() {
        return recSysAccount;
    }

    public void setRecSysAccount(String recSysAccount) {
        this.recSysAccount = recSysAccount;
    }

    public String getRecSys() {
        return recSys;
    }

    public void setRecSys(String recSys) {
        this.recSys = recSys;
    }

    public String getRecSysChange() {
        return recSysChange;
    }

    public void setRecSysChange(String recSysChange) {
        this.recSysChange = recSysChange;
    }

    public String getRecSysAdmUnit() {
        return recSysAdmUnit;
    }

    public void setRecSysAdmUnit(String recSysAdmUnit) {
        this.recSysAdmUnit = recSysAdmUnit;
    }

    public String getRecSysStatus() {
        return recSysStatus;
    }

    public void setRecSysStatus(String recSysStatus) {
        this.recSysStatus = recSysStatus;
    }

    public Timestamp getRecSysEnableDate() {
        return recSysEnableDate;
    }

    public void setRecSysEnableDate(Timestamp recSysEnableDate) {
        this.recSysEnableDate = recSysEnableDate;
    }

    public String getRecSysAdmName() {
        return recSysAdmName;
    }

    public void setRecSysAdmName(String recSysAdmName) {
        this.recSysAdmName = recSysAdmName;
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

    public List<HashMap<String, HashMap<String, Object>>> getL410Variables() {
        return l410Variables;
    }

    public void setL410Variables(List<HashMap<String, HashMap<String, Object>>> l410Variables) {
        this.l410Variables = l410Variables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmIsmsL410DTO that = (BpmIsmsL410DTO) o;
        return Objects.equals(formId, that.formId) && Objects.equals(processInstanceId, that.processInstanceId) && Objects.equals(applyDate, that.applyDate) && Objects.equals(filEmpid, that.filEmpid) && Objects.equals(filName, that.filName) && Objects.equals(filUnit, that.filUnit) && Objects.equals(appEmpid, that.appEmpid) && Objects.equals(appName, that.appName) && Objects.equals(appEngName, that.appEngName) && Objects.equals(appUnit, that.appUnit) && Objects.equals(position, that.position) && Objects.equals(extNum, that.extNum) && Objects.equals(isSubmit, that.isSubmit) && Objects.equals(appReason, that.appReason) && Objects.equals(isEnableDate, that.isEnableDate) && Objects.equals(enableDate, that.enableDate) && Objects.equals(isOther, that.isOther) && Objects.equals(otherReason, that.otherReason) && Objects.equals(isHrSys, that.isHrSys) && Objects.equals(hrSys, that.hrSys) && Objects.equals(hrSysChange, that.hrSysChange) && Objects.equals(hrSysAdmUnit, that.hrSysAdmUnit) && Objects.equals(hrSysStatus, that.hrSysStatus) && Objects.equals(hrSysEnableDate, that.hrSysEnableDate) && Objects.equals(hrSysAdmName, that.hrSysAdmName) && Objects.equals(isAdSys, that.isAdSys) && Objects.equals(adAccount, that.adAccount) && Objects.equals(adSys, that.adSys) && Objects.equals(adSysChange, that.adSysChange) && Objects.equals(adSysAdmUnit, that.adSysAdmUnit) && Objects.equals(adSysStatus, that.adSysStatus) && Objects.equals(adSysEnableDate, that.adSysEnableDate) && Objects.equals(adSysAdmName, that.adSysAdmName) && Objects.equals(isMeetingRoom, that.isMeetingRoom) && Objects.equals(meetingRoom, that.meetingRoom) && Objects.equals(meetingEoomChange, that.meetingEoomChange) && Objects.equals(meetingRoomAdmUnit, that.meetingRoomAdmUnit) && Objects.equals(meetingRoomStatus, that.meetingRoomStatus) && Objects.equals(meetingRoomEnableDate, that.meetingRoomEnableDate) && Objects.equals(meetingRoomAdmName, that.meetingRoomAdmName) && Objects.equals(isOdSys, that.isOdSys) && Objects.equals(odSysRole, that.odSysRole) && Objects.equals(odSys, that.odSys) && Objects.equals(odSysOther, that.odSysOther) && Objects.equals(odSysAdmUnit, that.odSysAdmUnit) && Objects.equals(odSysStatus, that.odSysStatus) && Objects.equals(odSysEnableDate, that.odSysEnableDate) && Objects.equals(odSysAdmName, that.odSysAdmName) && Objects.equals(isEmailSys, that.isEmailSys) && Objects.equals(emailSysAccount, that.emailSysAccount) && Objects.equals(emailSys, that.emailSys) && Objects.equals(emailApply1, that.emailApply1) && Objects.equals(emailApply2, that.emailApply2) && Objects.equals(emailSysChange, that.emailSysChange) && Objects.equals(emailSysAdmUnit, that.emailSysAdmUnit) && Objects.equals(emailSysStatus, that.emailSysStatus) && Objects.equals(emailSysEnableDate, that.emailSysEnableDate) && Objects.equals(emailSysAdmName, that.emailSysAdmName) && Objects.equals(isWebSite, that.isWebSite) && Objects.equals(isPccWww, that.isPccWww) && Objects.equals(isPccHome, that.isPccHome) && Objects.equals(webSite, that.webSite) && Objects.equals(isUnitAdm, that.isUnitAdm) && Objects.equals(isUnitDataMgr, that.isUnitDataMgr) && Objects.equals(isWebSiteOther, that.isWebSiteOther) && Objects.equals(webSiteOther, that.webSiteOther) && Objects.equals(webSiteAdmUnit, that.webSiteAdmUnit) && Objects.equals(webSiteStatus, that.webSiteStatus) && Objects.equals(webSiteEnableDate, that.webSiteEnableDate) && Objects.equals(webSiteAdmName, that.webSiteAdmName) && Objects.equals(isPccPis, that.isPccPis) && Objects.equals(pccPisAccount, that.pccPisAccount) && Objects.equals(pccPis, that.pccPis) && Objects.equals(pccPisChange, that.pccPisChange) && Objects.equals(pccPisAdmUnit, that.pccPisAdmUnit) && Objects.equals(pccPisStatus, that.pccPisStatus) && Objects.equals(pccPisEnableDate, that.pccPisEnableDate) && Objects.equals(pccPisAdmName, that.pccPisAdmName) && Objects.equals(isEngAndPrjInfoSys, that.isEngAndPrjInfoSys) && Objects.equals(engAndPrjInfoSysAccount, that.engAndPrjInfoSysAccount) && Objects.equals(engAndPrjInfoSys, that.engAndPrjInfoSys) && Objects.equals(engAndPrjInfoSysChange, that.engAndPrjInfoSysChange) && Objects.equals(engAndPrjInfoSysAdmUnit, that.engAndPrjInfoSysAdmUnit) && Objects.equals(engAndPrjInfoSysStatus, that.engAndPrjInfoSysStatus) && Objects.equals(engAndPrjInfoSysEnableDate, that.engAndPrjInfoSysEnableDate) && Objects.equals(engAndPrjInfoSysAdmName, that.engAndPrjInfoSysAdmName) && Objects.equals(isRevSys, that.isRevSys) && Objects.equals(revSysAccount, that.revSysAccount) && Objects.equals(revSys, that.revSys) && Objects.equals(revSysChange, that.revSysChange) && Objects.equals(revSysAdmUnit, that.revSysAdmUnit) && Objects.equals(revSysStatus, that.revSysStatus) && Objects.equals(revSysEnableDate, that.revSysEnableDate) && Objects.equals(revSysAdmName, that.revSysAdmName) && Objects.equals(isRecSys, that.isRecSys) && Objects.equals(recSysAccount, that.recSysAccount) && Objects.equals(recSys, that.recSys) && Objects.equals(recSysChange, that.recSysChange) && Objects.equals(recSysAdmUnit, that.recSysAdmUnit) && Objects.equals(recSysStatus, that.recSysStatus) && Objects.equals(recSysEnableDate, that.recSysEnableDate) && Objects.equals(recSysAdmName, that.recSysAdmName) && Objects.equals(isBidSys, that.isBidSys) && Objects.equals(bidSysAccount, that.bidSysAccount) && Objects.equals(bidSys, that.bidSys) && Objects.equals(bidSysChange, that.bidSysChange) && Objects.equals(bidSysAdmUnit, that.bidSysAdmUnit) && Objects.equals(bidSysStatus, that.bidSysStatus) && Objects.equals(bidSysEnableDate, that.bidSysEnableDate) && Objects.equals(bidSysAdmName, that.bidSysAdmName) && Objects.equals(isOtherSys1, that.isOtherSys1) && Objects.equals(otherSys1ServerName, that.otherSys1ServerName) && Objects.equals(otherSys1Account, that.otherSys1Account) && Objects.equals(otherSys1, that.otherSys1) && Objects.equals(otherSys1Change, that.otherSys1Change) && Objects.equals(otherSys1AdmUnit, that.otherSys1AdmUnit) && Objects.equals(otherSys1Status, that.otherSys1Status) && Objects.equals(otherSys1EnableDate, that.otherSys1EnableDate) && Objects.equals(otherSys1AdmName, that.otherSys1AdmName) && Objects.equals(isOtherSys2, that.isOtherSys2) && Objects.equals(otherSys2ServerName, that.otherSys2ServerName) && Objects.equals(otherSys2Account, that.otherSys2Account) && Objects.equals(otherSys2, that.otherSys2) && Objects.equals(otherSys2Change, that.otherSys2Change) && Objects.equals(otherSys2AdmUnit, that.otherSys2AdmUnit) && Objects.equals(otherSys2Status, that.otherSys2Status) && Objects.equals(otherSys2EnableDate, that.otherSys2EnableDate) && Objects.equals(otherSys2AdmName, that.otherSys2AdmName) && Objects.equals(isOtherSys3, that.isOtherSys3) && Objects.equals(otherSys3ServerName, that.otherSys3ServerName) && Objects.equals(otherSys3Account, that.otherSys3Account) && Objects.equals(otherSys3, that.otherSys3) && Objects.equals(otherSys3Change, that.otherSys3Change) && Objects.equals(otherSys3AdmUnit, that.otherSys3AdmUnit) && Objects.equals(otherSys3Status, that.otherSys3Status) && Objects.equals(otherSys3EnableDate, that.otherSys3EnableDate) && Objects.equals(otherSys3AdmName, that.otherSys3AdmName) && Objects.equals(processInstanceStatus, that.processInstanceStatus) && Objects.equals(updateUser, that.updateUser) && Objects.equals(updateTime, that.updateTime) && Objects.equals(createUser, that.createUser) && Objects.equals(createTime, that.createTime) && Objects.equals(formName, that.formName) && Objects.equals(signUnit, that.signUnit) && Objects.equals(signer, that.signer) && Objects.equals(l410Variables, that.l410Variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, processInstanceId, applyDate, filEmpid, filName, filUnit, appEmpid, appName, appEngName, appUnit, position, extNum, isSubmit, appReason, isEnableDate, enableDate, isOther, otherReason, isHrSys, hrSys, hrSysChange, hrSysAdmUnit, hrSysStatus, hrSysEnableDate, hrSysAdmName, isAdSys, adAccount, adSys, adSysChange, adSysAdmUnit, adSysStatus, adSysEnableDate, adSysAdmName, isMeetingRoom, meetingRoom, meetingEoomChange, meetingRoomAdmUnit, meetingRoomStatus, meetingRoomEnableDate, meetingRoomAdmName, isOdSys, odSysRole, odSys, odSysOther, odSysAdmUnit, odSysStatus, odSysEnableDate, odSysAdmName, isEmailSys, emailSysAccount, emailSys, emailApply1, emailApply2, emailSysChange, emailSysAdmUnit, emailSysStatus, emailSysEnableDate, emailSysAdmName, isWebSite, isPccWww, isPccHome, webSite, isUnitAdm, isUnitDataMgr, isWebSiteOther, webSiteOther, webSiteAdmUnit, webSiteStatus, webSiteEnableDate, webSiteAdmName, isPccPis, pccPisAccount, pccPis, pccPisChange, pccPisAdmUnit, pccPisStatus, pccPisEnableDate, pccPisAdmName, isEngAndPrjInfoSys, engAndPrjInfoSysAccount, engAndPrjInfoSys, engAndPrjInfoSysChange, engAndPrjInfoSysAdmUnit, engAndPrjInfoSysStatus, engAndPrjInfoSysEnableDate, engAndPrjInfoSysAdmName, isRevSys, revSysAccount, revSys, revSysChange, revSysAdmUnit, revSysStatus, revSysEnableDate, revSysAdmName, isRecSys, recSysAccount, recSys, recSysChange, recSysAdmUnit, recSysStatus, recSysEnableDate, recSysAdmName, isBidSys, bidSysAccount, bidSys, bidSysChange, bidSysAdmUnit, bidSysStatus, bidSysEnableDate, bidSysAdmName, isOtherSys1, otherSys1ServerName, otherSys1Account, otherSys1, otherSys1Change, otherSys1AdmUnit, otherSys1Status, otherSys1EnableDate, otherSys1AdmName, isOtherSys2, otherSys2ServerName, otherSys2Account, otherSys2, otherSys2Change, otherSys2AdmUnit, otherSys2Status, otherSys2EnableDate, otherSys2AdmName, isOtherSys3, otherSys3ServerName, otherSys3Account, otherSys3, otherSys3Change, otherSys3AdmUnit, otherSys3Status, otherSys3EnableDate, otherSys3AdmName, processInstanceStatus, updateUser, updateTime, createUser, createTime, formName, signUnit, signer, l410Variables);
    }

    @Override
    public String toString() {
        return "BpmIsmsL410DTO{" +
            "formId='" + formId + '\'' +
            ", processInstanceId='" + processInstanceId + '\'' +
            ", applyDate=" + applyDate +
            ", filEmpid='" + filEmpid + '\'' +
            ", filName='" + filName + '\'' +
            ", filUnit='" + filUnit + '\'' +
            ", appEmpid='" + appEmpid + '\'' +
            ", appName='" + appName + '\'' +
            ", appEngName='" + appEngName + '\'' +
            ", appUnit='" + appUnit + '\'' +
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
            ", isEngAndPrjInfoSys='" + isEngAndPrjInfoSys + '\'' +
            ", engAndPrjInfoSysAccount='" + engAndPrjInfoSysAccount + '\'' +
            ", engAndPrjInfoSys='" + engAndPrjInfoSys + '\'' +
            ", engAndPrjInfoSysChange='" + engAndPrjInfoSysChange + '\'' +
            ", engAndPrjInfoSysAdmUnit='" + engAndPrjInfoSysAdmUnit + '\'' +
            ", engAndPrjInfoSysStatus='" + engAndPrjInfoSysStatus + '\'' +
            ", engAndPrjInfoSysEnableDate=" + engAndPrjInfoSysEnableDate +
            ", engAndPrjInfoSysAdmName='" + engAndPrjInfoSysAdmName + '\'' +
            ", isRevSys='" + isRevSys + '\'' +
            ", revSysAccount='" + revSysAccount + '\'' +
            ", revSys='" + revSys + '\'' +
            ", revSysChange='" + revSysChange + '\'' +
            ", revSysAdmUnit='" + revSysAdmUnit + '\'' +
            ", revSysStatus='" + revSysStatus + '\'' +
            ", revSysEnableDate=" + revSysEnableDate +
            ", revSysAdmName='" + revSysAdmName + '\'' +
            ", isRecSys='" + isRecSys + '\'' +
            ", recSysAccount='" + recSysAccount + '\'' +
            ", recSys='" + recSys + '\'' +
            ", recSysChange='" + recSysChange + '\'' +
            ", recSysAdmUnit='" + recSysAdmUnit + '\'' +
            ", recSysStatus='" + recSysStatus + '\'' +
            ", recSysEnableDate=" + recSysEnableDate +
            ", recSysAdmName='" + recSysAdmName + '\'' +
            ", isBidSys='" + isBidSys + '\'' +
            ", bidSysAccount='" + bidSysAccount + '\'' +
            ", bidSys='" + bidSys + '\'' +
            ", bidSysChange='" + bidSysChange + '\'' +
            ", bidSysAdmUnit='" + bidSysAdmUnit + '\'' +
            ", bidSysStatus='" + bidSysStatus + '\'' +
            ", bidSysEnableDate=" + bidSysEnableDate +
            ", bidSysAdmName='" + bidSysAdmName + '\'' +
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
            ", formName='" + formName + '\'' +
            ", signUnit='" + signUnit + '\'' +
            ", signer='" + signer + '\'' +
            ", l410Variables=" + l410Variables +
            '}';
    }
}
