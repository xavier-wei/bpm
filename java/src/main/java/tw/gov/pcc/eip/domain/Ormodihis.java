package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.dao.OrmodihisDao;

import java.time.LocalDateTime;

/**
 * 線上報名修改歷程檔
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OrmodihisDao.TABLE_NAME)
public class Ormodihis {
  @PkeyField("ORFORMNO")
  @LogField("ORFORMNO")
  private String orformno;
  @PkeyField("SEQNO")
  @LogField("SEQNO")
  private String seqno;
  @LogField("CHGTYPE")
  private String chgtype;
  @LogField("USERINFO")
  private String userinfo;
  @LogField("USERDEPT")
  private String userdept;
  @LogField("CREUSER")
  private String creuser;
  @LogField("CREDT")
  private LocalDateTime credt;
  @LogField("UPDUSER")
  private String upduser;
  @LogField("UPDDT")
  private LocalDateTime upddt;

}
