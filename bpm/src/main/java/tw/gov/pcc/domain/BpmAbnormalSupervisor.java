package tw.gov.pcc.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "dbo",name = "BPM_ABNORMAL_SUPERVISOR")
public class BpmAbnormalSupervisor implements Serializable {
    @Id
    private String peidno;
    private String unitName;
    private String pecard;
    private String pename;
    private String posname;
    private String f1UnitName;
    private String f1Id;
    private String f1Account;
    private String f1Pename;
    private String f1Posname;
    private String f2UnitName;
    private String f2Id;
    private String f2Account;
    private String f2Pename;
    private String f2Posname;
    private String f3Id;
    private String f3Account;
    private String f3Pename;
    private String f3Posname;

    public String getPeidno() {
        return peidno;
    }

    public void setPeidno(String peidno) {
        this.peidno = peidno;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPecard() {
        return pecard;
    }

    public void setPecard(String pecard) {
        this.pecard = pecard;
    }

    public String getPename() {
        return pename;
    }

    public void setPename(String pename) {
        this.pename = pename;
    }

    public String getPosname() {
        return posname;
    }

    public void setPosname(String posname) {
        this.posname = posname;
    }

    public String getF1UnitName() {
        return f1UnitName;
    }

    public void setF1UnitName(String f1UnitName) {
        this.f1UnitName = f1UnitName;
    }

    public String getF1Id() {
        return f1Id;
    }

    public void setF1Id(String f1Id) {
        this.f1Id = f1Id;
    }

    public String getF1Account() {
        return f1Account;
    }

    public void setF1Account(String f1Account) {
        this.f1Account = f1Account;
    }

    public String getF1Pename() {
        return f1Pename;
    }

    public void setF1Pename(String f1Pename) {
        this.f1Pename = f1Pename;
    }

    public String getF1Posname() {
        return f1Posname;
    }

    public void setF1Posname(String f1Posname) {
        this.f1Posname = f1Posname;
    }

    public String getF2UnitName() {
        return f2UnitName;
    }

    public void setF2UnitName(String f2UnitName) {
        this.f2UnitName = f2UnitName;
    }

    public String getF2Id() {
        return f2Id;
    }

    public void setF2Id(String f2Id) {
        this.f2Id = f2Id;
    }

    public String getF2Account() {
        return f2Account;
    }

    public void setF2Account(String f2Account) {
        this.f2Account = f2Account;
    }

    public String getF2Pename() {
        return f2Pename;
    }

    public void setF2Pename(String f2Pename) {
        this.f2Pename = f2Pename;
    }

    public String getF2Posname() {
        return f2Posname;
    }

    public void setF2Posname(String f2Posname) {
        this.f2Posname = f2Posname;
    }

    public String getF3Id() {
        return f3Id;
    }

    public void setF3Id(String f3Id) {
        this.f3Id = f3Id;
    }

    public String getF3Account() {
        return f3Account;
    }

    public void setF3Account(String f3Account) {
        this.f3Account = f3Account;
    }

    public String getF3Pename() {
        return f3Pename;
    }

    public void setF3Pename(String f3Pename) {
        this.f3Pename = f3Pename;
    }

    public String getF3Posname() {
        return f3Posname;
    }

    public void setF3Posname(String f3Posname) {
        this.f3Posname = f3Posname;
    }

    @Override
    public String toString() {
        return "BpmAbnormalSupervisor{" +
            "peidno='" + peidno + '\'' +
            ", unitName='" + unitName + '\'' +
            ", pecard='" + pecard + '\'' +
            ", pename='" + pename + '\'' +
            ", posname='" + posname + '\'' +
            ", f1UnitName='" + f1UnitName + '\'' +
            ", f1Id='" + f1Id + '\'' +
            ", f1Account='" + f1Account + '\'' +
            ", f1Pename='" + f1Pename + '\'' +
            ", f1Posname='" + f1Posname + '\'' +
            ", f2UnitName='" + f2UnitName + '\'' +
            ", f2Id='" + f2Id + '\'' +
            ", f2Account='" + f2Account + '\'' +
            ", f2Pename='" + f2Pename + '\'' +
            ", f2Posname='" + f2Posname + '\'' +
            ", f3Id='" + f3Id + '\'' +
            ", f3Account='" + f3Account + '\'' +
            ", f3Pename='" + f3Pename + '\'' +
            ", f3Posname='" + f3Posname + '\'' +
            '}';
    }
}
