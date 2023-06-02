package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OrresultDao;

import java.time.LocalDateTime;

/**
 * 線上報名結果資料
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OrresultDao.TABLE_NAME)
public class Orresult {
  @PkeyField("ORFORMNO")
  @LogField("ORFORMNO")
  private String orformno;
  @PkeyField("SEQNO")
  @LogField("SEQNO")
  private String seqno;
  @LogField("REGISWAY")
  private String regisway;
  @LogField("REGISNAME")
  private String regisname;
  @LogField("REGISIDN")
  private String regisidn;
  @LogField("REGISSEX")
  private String regissex;
  @LogField("REGISBRTH")
  private String regisbrth;
  @LogField("REGISEMAIL")
  private String regisemail;
  @LogField("REGISPHONE")
  private String regisphone;
  @LogField("FAX")
  private String fax;
  @LogField("COMPANY")
  private String company;
  @LogField("DEGREEN")
  private Integer degreen;
  @LogField("DEPT")
  private String dept;
  @LogField("JOGTITLE")
  private String jogtitle;
  @LogField("REGISADDRES")
  private String regisaddres;
  @LogField("MEALSTATUS")
  private String mealstatus;
  @LogField("ISPAY")
  private String ispay;
  @LogField("ISPASS")
  private String ispass;
  @LogField("REGISDT")
  private LocalDateTime regisdt;
  @LogField("ISNOTIFY")
  private String isnotify;
  @LogField("LASTNOTIDT")
  private LocalDateTime lastnotidt;
  @LogField("CERTIPHOURS")
  private String certiphours;
  @LogField("CERTIDHOURS")
  private String certidhours;
  @LogField("CREUSER")
  private String creuser;
  @LogField("CREDT")
  private LocalDateTime credt;
  @LogField("UPDUSER")
  private String upduser;
  @LogField("UPDDT")
  private LocalDateTime upddt;

}
