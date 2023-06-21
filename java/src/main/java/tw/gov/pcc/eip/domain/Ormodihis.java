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
  private String orformno;//表單編號
  @PkeyField("SEQNO")
  @LogField("SEQNO")
  private String seqno;//流水號
  @LogField("CHGTYPE")
  private String chgtype;//變更動作
  @LogField("USERINFO")
  private String userinfo;//操作者
  @LogField("USERDEPT")
  private String userdept;//操作者當時所屬部門
  @LogField("CREUSER")
  private String creuser;//建立人員
  @LogField("CREDT")
  private LocalDateTime credt;//建立時間
  @LogField("UPDUSER")
  private String upduser;//更新人員
  @LogField("UPDDT")
  private LocalDateTime upddt;//更新時間

}
