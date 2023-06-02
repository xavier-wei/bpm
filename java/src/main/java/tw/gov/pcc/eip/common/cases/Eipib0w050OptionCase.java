package tw.gov.pcc.eip.common.cases;

import java.io.Serializable;
import java.time.LocalDateTime;
import tw.gov.pcc.eip.domain.Code;

public class Eipib0w050OptionCase implements Serializable {
   private static final long serialVersionUID = -5041949837550380538L;
   private String codekind;//主代號類別
   private String codeno;//主代號
   private String scodekind;//副代號類別
   private String scodeno;//副代號
   private String codename;//主代號名稱
   private String staff;//異動者代號
   private LocalDateTime prcdat;//異動日期時間
   private String remark;//說明
   private String delete;//是否刪除

   public Eipib0w050OptionCase(Code code) {
      super();
      this.codekind = code.getCodekind();
      this.codeno = code.getCodeno();
      this.scodekind = code.getScodekind();
      this.scodeno = code.getScodeno();
      this.codename = code.getCodename();
      this.staff = code.getStaff();
      this.prcdat = code.getPrcdat();
      this.remark = code.getRemark();
   }

   public String getCodekind() {
      return this.codekind;
   }

   public String getCodeno() {
      return this.codeno;
   }

   public String getScodekind() {
      return this.scodekind;
   }

   public String getScodeno() {
      return this.scodeno;
   }

   public String getCodename() {
      return this.codename;
   }

   public String getStaff() {
      return this.staff;
   }

   public LocalDateTime getPrcdat() {
      return this.prcdat;
   }

   public String getRemark() {
      return this.remark;
   }

   public String getDelete() {
      return this.delete;
   }

   public void setCodekind(final String codekind) {
      this.codekind = codekind;
   }

   public void setCodeno(final String codeno) {
      this.codeno = codeno;
   }

   public void setScodekind(final String scodekind) {
      this.scodekind = scodekind;
   }

   public void setScodeno(final String scodeno) {
      this.scodeno = scodeno;
   }

   public void setCodename(final String codename) {
      this.codename = codename;
   }

   public void setStaff(final String staff) {
      this.staff = staff;
   }

   public void setPrcdat(final LocalDateTime prcdat) {
      this.prcdat = prcdat;
   }

   public void setRemark(final String remark) {
      this.remark = remark;
   }

   public void setDelete(final String delete) {
      this.delete = delete;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) return true;
      if (!(o instanceof Eipib0w050OptionCase)) return false;
      final Eipib0w050OptionCase other = (Eipib0w050OptionCase) o;
      if (!other.canEqual((Object) this)) return false;
      final Object this$codekind = this.getCodekind();
      final Object other$codekind = other.getCodekind();
      if (this$codekind == null ? other$codekind != null : !this$codekind.equals(other$codekind)) return false;
      final Object this$codeno = this.getCodeno();
      final Object other$codeno = other.getCodeno();
      if (this$codeno == null ? other$codeno != null : !this$codeno.equals(other$codeno)) return false;
      final Object this$scodekind = this.getScodekind();
      final Object other$scodekind = other.getScodekind();
      if (this$scodekind == null ? other$scodekind != null : !this$scodekind.equals(other$scodekind)) return false;
      final Object this$scodeno = this.getScodeno();
      final Object other$scodeno = other.getScodeno();
      if (this$scodeno == null ? other$scodeno != null : !this$scodeno.equals(other$scodeno)) return false;
      final Object this$codename = this.getCodename();
      final Object other$codename = other.getCodename();
      if (this$codename == null ? other$codename != null : !this$codename.equals(other$codename)) return false;
      final Object this$staff = this.getStaff();
      final Object other$staff = other.getStaff();
      if (this$staff == null ? other$staff != null : !this$staff.equals(other$staff)) return false;
      final Object this$prcdat = this.getPrcdat();
      final Object other$prcdat = other.getPrcdat();
      if (this$prcdat == null ? other$prcdat != null : !this$prcdat.equals(other$prcdat)) return false;
      final Object this$remark = this.getRemark();
      final Object other$remark = other.getRemark();
      if (this$remark == null ? other$remark != null : !this$remark.equals(other$remark)) return false;
      final Object this$delete = this.getDelete();
      final Object other$delete = other.getDelete();
      if (this$delete == null ? other$delete != null : !this$delete.equals(other$delete)) return false;
      return true;
   }

   protected boolean canEqual(final Object other) {
      return other instanceof Eipib0w050OptionCase;
   }

   @Override
   public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final Object $codekind = this.getCodekind();
      result = result * PRIME + ($codekind == null ? 43 : $codekind.hashCode());
      final Object $codeno = this.getCodeno();
      result = result * PRIME + ($codeno == null ? 43 : $codeno.hashCode());
      final Object $scodekind = this.getScodekind();
      result = result * PRIME + ($scodekind == null ? 43 : $scodekind.hashCode());
      final Object $scodeno = this.getScodeno();
      result = result * PRIME + ($scodeno == null ? 43 : $scodeno.hashCode());
      final Object $codename = this.getCodename();
      result = result * PRIME + ($codename == null ? 43 : $codename.hashCode());
      final Object $staff = this.getStaff();
      result = result * PRIME + ($staff == null ? 43 : $staff.hashCode());
      final Object $prcdat = this.getPrcdat();
      result = result * PRIME + ($prcdat == null ? 43 : $prcdat.hashCode());
      final Object $remark = this.getRemark();
      result = result * PRIME + ($remark == null ? 43 : $remark.hashCode());
      final Object $delete = this.getDelete();
      result = result * PRIME + ($delete == null ? 43 : $delete.hashCode());
      return result;
   }

   @Override
   public String toString() {
      return "Eipib0w050OptionCase(codekind=" + this.getCodekind() + ", codeno=" + this.getCodeno() + ", scodekind=" + this.getScodekind() + ", scodeno=" + this.getScodeno() + ", codename=" + this.getCodename() + ", staff=" + this.getStaff() + ", prcdat=" + this.getPrcdat() + ", remark=" + this.getRemark() + ", delete=" + this.getDelete() + ")";
   }

   public Eipib0w050OptionCase() {
   }
}
