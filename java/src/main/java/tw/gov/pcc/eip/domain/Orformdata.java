package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OrclassDao;
import tw.gov.pcc.eip.dao.OrformdataDao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 線上報名表單資料
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OrformdataDao.TABLE_NAME)
public class Orformdata {
  @PkeyField("ORFORMNO")
  @LogField("ORFORMNO")
  private String orformno;
  @LogField("orccode")
  private Long orccode;
  @LogField("courseclacode")
  private Long courseclacode;
  @LogField("coursecode")
  private String coursecode;
  @LogField("classcode")
  private String classcode;
  @LogField("period")
  private Long period;
  @LogField("topicname")
  private String topicname;
  @LogField("topicdesc")
  private String topicdesc;
  @LogField("status")
  private String status;
  @LogField("organizer")
  private String organizer;
  @LogField("contacter")
  private String contacter;
  @LogField("contactnum")
  private String contactnum;
  @LogField("fax")
  private String fax;
  @LogField("email")
  private String email;
  @LogField("addres")
  private String addres;
  @LogField("country")
  private String country;
  @LogField("profmdt")
  private LocalDateTime profmdt;
  @LogField("proendt")
  private LocalDateTime proendt;
  @LogField("acceptappnum")
  private Long acceptappnum;
  @LogField("allowappnum")
  private Long allowappnum;
  @LogField("allowappway")
  private String allowappway;
  @LogField("fee")
  private BigDecimal fee;
  @LogField("account")
  private String account;
  @LogField("ISMEALS")
  private String ismeals;
  @LogField("CLASSHOURS")
  private String classhours;
  @LogField("CERTIHOURS")
  private String certihours;
  @LogField("REGISFMDT")
  private LocalDateTime regisfmdt;
  private String regisfmdtStr;
  @LogField("REGISENDT")
  private LocalDateTime regisendt;
  private String regisendtStr;
  @LogField("LECTURERCODE")
  private String lecturercode;
  @LogField("PASSMSG")
  private String passmsg;
  @LogField("REJECTMST")
  private String rejectmst;
  @LogField("REGISQUAL")
  private String regisqual;
  @LogField("REMARK")
  private String remark;
  @LogField("CREUSER")
  private String creuser;
  @LogField("CREDT")
  private LocalDateTime credt;
  @LogField("UPDUSER")
  private String upduser;
  @LogField("UPDDT")
  private LocalDateTime upddt;

  @LogField("SUBJECT")
  private String subject;
  private Long actualappnum;
  private Long passnum;
}
