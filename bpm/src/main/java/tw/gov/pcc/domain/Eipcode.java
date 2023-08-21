package tw.gov.pcc.domain;

import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.annotation.LogField;
import tw.gov.pcc.eip.annotation.PkeyField;
import tw.gov.pcc.eip.annotation.Table;
import tw.gov.pcc.eip.dao.EipcodeDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Weith
 * EIPCODE 代碼檔
 */
@Table(EipcodeDao.TABLE_NAME)
@NoArgsConstructor
public class Eipcode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 98432953901114332L;

    @PkeyField("CODEKIND")
    @LogField("CODEKIND")
    private String codekind;  // 主代號類別

    @PkeyField("CODEKIND")
    @LogField("CODENO")
    private String codeno;  // 主代號

    @LogField("SCODEKIND")
    private String scodekind;  // 副代號類別

    @LogField("SCODENO")
    private String scodeno;  // 副代號

    @LogField("CODENAME")
    private String codename;  // 主代號名稱

    @LogField("PRCDAT")
    private LocalDateTime prcdat;  // 異動日期時間

    @LogField("STAFF")
    private String staff;  // 異動者代號

    @LogField("REMARK")
    private String remark;

    public String getCodekind() {
        return codekind;
    }

    public void setCodekind(String codekind) {
        this.codekind = codekind;
    }

    public String getCodeno() {
        return codeno;
    }

    public void setCodeno(String codeno) {
        this.codeno = codeno;
    }

    public String getScodekind() {
        return scodekind;
    }

    public void setScodekind(String scodekind) {
        this.scodekind = scodekind;
    }

    public String getScodeno() {
        return scodeno;
    }

    public void setScodeno(String scodeno) {
        this.scodeno = scodeno;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public LocalDateTime getPrcdat() {
        return prcdat;
    }

    public void setPrcdat(LocalDateTime prcdat) {
        this.prcdat = prcdat;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Eipcode{" +
                "codekind='" + codekind + '\'' +
                ", codeno='" + codeno + '\'' +
                ", scodekind='" + scodekind + '\'' +
                ", scodeno='" + scodeno + '\'' +
                ", codename='" + codename + '\'' +
                ", prcdat=" + prcdat +
                ", staff='" + staff + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
