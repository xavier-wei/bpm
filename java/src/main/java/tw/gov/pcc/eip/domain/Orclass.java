package tw.gov.pcc.eip.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OrclassDao;

import java.time.LocalDateTime;

/**
 * 線上報名分類資料
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OrclassDao.TABLE_NAME)
public class Orclass {
  @PkeyField
  @LogField("ORCCODE")
  private Long orccode;
  @LogField("ORCNAME")
  private String orcname;
  @LogField("ISCOURSE")
  private String iscourse;
  @LogField("SIGNFORM")
  private String signform;
  @LogField("CREUSER")
  private String creuser;
  @LogField("CREDT")
  private LocalDateTime credt;
  @LogField("UPDUSER")
  private String upduser;
  @LogField("UPDDT")
  private LocalDateTime upddt;

}
