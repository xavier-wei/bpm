package tw.gov.pcc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "dbo",name = "BPM_SPECIAL_SUPERVISOR")
public class BpmSpecialSupervisor implements Serializable {

    @Id
    @Column(name = "PECARD")
    private String pecard;
    @Column(name = "UNIT_NAME")
    private String unitName;
    @Column(name = "PENAME")
    private String pename;
    @Column(name = "POSNAME")
    private String posname;
    @Column(name = "F1_UNIT_NAME")
    private String f1UnitName;
    @Column(name = "F1_ACCOUNT")
    private String f1Account;
    @Column(name = "F1_PENAME")
    private String f1Pename;
    @Column(name = "F1_POSNAME")
    private String f1Posname;
    @Column(name = "F2_UNIT_NAME")
    private String f2UnitName;
    @Column(name = "F2_ACCOUNT")
    private String f2Account;
    @Column(name = "F2_PENAME")
    private String f2Pename;
    @Column(name = "F2_POSNAME")
    private String f2Posname;
    @Column(name = "F3_ACCOUNT")
    private String f3Account;
    @Column(name = "F3_PENAME")
    private String f3Pename;
    @Column(name = "F3_POSNAME")
    private String f3Posname;



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
        return "BpmSpecialSupervisor{" +
            ", unitName='" + unitName + '\'' +
            ", pecard='" + pecard + '\'' +
            ", pename='" + pename + '\'' +
            ", posname='" + posname + '\'' +
            ", f1UnitName='" + f1UnitName + '\'' +
            ", f1Account='" + f1Account + '\'' +
            ", f1Pename='" + f1Pename + '\'' +
            ", f1Posname='" + f1Posname + '\'' +
            ", f2UnitName='" + f2UnitName + '\'' +
            ", f2Account='" + f2Account + '\'' +
            ", f2Pename='" + f2Pename + '\'' +
            ", f2Posname='" + f2Posname + '\'' +
            ", f3Account='" + f3Account + '\'' +
            ", f3Pename='" + f3Pename + '\'' +
            ", f3Posname='" + f3Posname + '\'' +
            '}';
    }
}
