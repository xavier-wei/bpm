package tw.gov.pcc.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "VIEW_CPAPE05M_FORTEST")
public class Cpape05mForTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pecard;

    private String peorg;

    private String orgName;
    private String peunit;
    private String unitName;
    private String peidno;
    private String loginId;
    private String pename;
    private String email;
    private String petit;
    private String title;
    private String pehyear;
    private String pehmon;
    private Float pehday;
    private String pesex;
    private String pebirthd;
    private String pecrkcod;
    private String crkcodName;
    private String pememcod;
    private String memcodName;
    private Integer peoverhfee;
    private String peactdate;
    private String pelevdate;
    private String pepoint;
    private String peykind;
    private Float perday;
    private Float perday1;
    private Float perday2;
    private String pefstdate;
    private String pehyear2;
    private String pehmon2;
    private String petraining;
    private Integer pearea;
    private String ctAreaCode;
    private String ctTel;
    private String ctExt;
    private String ctMobile;
    private String ctHomeAddr;
    private Boolean isNationality;
    private String peupdate;

    public String getPecard() {
        return pecard;
    }

    public void setPecard(String pecard) {
        this.pecard = pecard;
    }

    public String getPeorg() {
        return peorg;
    }

    public void setPeorg(String peorg) {
        this.peorg = peorg;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPeunit() {
        return peunit;
    }

    public void setPeunit(String peunit) {
        this.peunit = peunit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPeidno() {
        return peidno;
    }

    public void setPeidno(String peidno) {
        this.peidno = peidno;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPename() {
        return pename;
    }

    public void setPename(String pename) {
        this.pename = pename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPetit() {
        return petit;
    }

    public void setPetit(String petit) {
        this.petit = petit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPehyear() {
        return pehyear;
    }

    public void setPehyear(String pehyear) {
        this.pehyear = pehyear;
    }

    public String getPehmon() {
        return pehmon;
    }

    public void setPehmon(String pehmon) {
        this.pehmon = pehmon;
    }

    public Float getPehday() {
        return pehday;
    }

    public void setPehday(Float pehday) {
        this.pehday = pehday;
    }

    public String getPesex() {
        return pesex;
    }

    public void setPesex(String pesex) {
        this.pesex = pesex;
    }

    public String getPebirthd() {
        return pebirthd;
    }

    public void setPebirthd(String pebirthd) {
        this.pebirthd = pebirthd;
    }

    public String getPecrkcod() {
        return pecrkcod;
    }

    public void setPecrkcod(String pecrkcod) {
        this.pecrkcod = pecrkcod;
    }

    public String getCrkcodName() {
        return crkcodName;
    }

    public void setCrkcodName(String crkcodName) {
        this.crkcodName = crkcodName;
    }

    public String getPememcod() {
        return pememcod;
    }

    public void setPememcod(String pememcod) {
        this.pememcod = pememcod;
    }

    public String getMemcodName() {
        return memcodName;
    }

    public void setMemcodName(String memcodName) {
        this.memcodName = memcodName;
    }

    public Integer getPeoverhfee() {
        return peoverhfee;
    }

    public void setPeoverhfee(Integer peoverhfee) {
        this.peoverhfee = peoverhfee;
    }

    public String getPeactdate() {
        return peactdate;
    }

    public void setPeactdate(String peactdate) {
        this.peactdate = peactdate;
    }

    public String getPelevdate() {
        return pelevdate;
    }

    public void setPelevdate(String pelevdate) {
        this.pelevdate = pelevdate;
    }

    public String getPepoint() {
        return pepoint;
    }

    public void setPepoint(String pepoint) {
        this.pepoint = pepoint;
    }

    public String getPeykind() {
        return peykind;
    }

    public void setPeykind(String peykind) {
        this.peykind = peykind;
    }

    public Float getPerday() {
        return perday;
    }

    public void setPerday(Float perday) {
        this.perday = perday;
    }

    public Float getPerday1() {
        return perday1;
    }

    public void setPerday1(Float perday1) {
        this.perday1 = perday1;
    }

    public Float getPerday2() {
        return perday2;
    }

    public void setPerday2(Float perday2) {
        this.perday2 = perday2;
    }

    public String getPefstdate() {
        return pefstdate;
    }

    public void setPefstdate(String pefstdate) {
        this.pefstdate = pefstdate;
    }

    public String getPehyear2() {
        return pehyear2;
    }

    public void setPehyear2(String pehyear2) {
        this.pehyear2 = pehyear2;
    }

    public String getPehmon2() {
        return pehmon2;
    }

    public void setPehmon2(String pehmon2) {
        this.pehmon2 = pehmon2;
    }

    public String getPetraining() {
        return petraining;
    }

    public void setPetraining(String petraining) {
        this.petraining = petraining;
    }

    public Integer getPearea() {
        return pearea;
    }

    public void setPearea(Integer pearea) {
        this.pearea = pearea;
    }

    public String getCtAreaCode() {
        return ctAreaCode;
    }

    public void setCtAreaCode(String ctAreaCode) {
        this.ctAreaCode = ctAreaCode;
    }

    public String getCtTel() {
        return ctTel;
    }

    public void setCtTel(String ctTel) {
        this.ctTel = ctTel;
    }

    public String getCtExt() {
        return ctExt;
    }

    public void setCtExt(String ctExt) {
        this.ctExt = ctExt;
    }

    public String getCtMobile() {
        return ctMobile;
    }

    public void setCtMobile(String ctMobile) {
        this.ctMobile = ctMobile;
    }

    public String getCtHomeAddr() {
        return ctHomeAddr;
    }

    public void setCtHomeAddr(String ctHomeAddr) {
        this.ctHomeAddr = ctHomeAddr;
    }

    public Boolean getNationality() {
        return isNationality;
    }

    public void setNationality(Boolean nationality) {
        isNationality = nationality;
    }

    public String getPeupdate() {
        return peupdate;
    }

    public void setPeupdate(String peupdate) {
        this.peupdate = peupdate;
    }

    @Override
    public String toString() {
        return "Cpape05m{" +
            "pecard='" + pecard + '\'' +
            ", peorg='" + peorg + '\'' +
            ", orgName='" + orgName + '\'' +
            ", peunit='" + peunit + '\'' +
            ", unitName='" + unitName + '\'' +
            ", peidno='" + peidno + '\'' +
            ", loginId='" + loginId + '\'' +
            ", pename='" + pename + '\'' +
            ", email='" + email + '\'' +
            ", petit='" + petit + '\'' +
            ", title='" + title + '\'' +
            ", pehyear='" + pehyear + '\'' +
            ", pehmon='" + pehmon + '\'' +
            ", pehday=" + pehday +
            ", pesex='" + pesex + '\'' +
            ", pebirthd='" + pebirthd + '\'' +
            ", pecrkcod='" + pecrkcod + '\'' +
            ", crkcodName='" + crkcodName + '\'' +
            ", pememcod='" + pememcod + '\'' +
            ", memcodName='" + memcodName + '\'' +
            ", peoverhfee=" + peoverhfee +
            ", peactdate='" + peactdate + '\'' +
            ", pelevdate='" + pelevdate + '\'' +
            ", pepoint='" + pepoint + '\'' +
            ", peykind='" + peykind + '\'' +
            ", perday=" + perday +
            ", perday1=" + perday1 +
            ", perday2=" + perday2 +
            ", pefstdate='" + pefstdate + '\'' +
            ", pehyear2='" + pehyear2 + '\'' +
            ", pehmon2='" + pehmon2 + '\'' +
            ", petraining='" + petraining + '\'' +
            ", pearea=" + pearea +
            ", ctAreaCode='" + ctAreaCode + '\'' +
            ", ctTel='" + ctTel + '\'' +
            ", ctExt='" + ctExt + '\'' +
            ", ctMobile='" + ctMobile + '\'' +
            ", ctHomeAddr='" + ctHomeAddr + '\'' +
            ", isNationality=" + isNationality +
            ", peupdate='" + peupdate + '\'' +
            '}';
    }
}
