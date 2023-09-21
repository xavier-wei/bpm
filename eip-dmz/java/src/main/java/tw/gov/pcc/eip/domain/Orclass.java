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
  private Long orccode;//分類代碼
  @LogField("ORCNAME")
  private String orcname;//分類名稱
  @LogField("ISCOURSE")
  private String iscourse;//是否為課程
  @LogField("SIGNFORM")
  private String signform;//簽到表格式
  @LogField("CREUSER")
  private String creuser;//建立人員
  @LogField("CREDT")
  private LocalDateTime credt;//建立時間
  @LogField("UPDUSER")
  private String upduser;//更新人員
  @LogField("UPDDT")
  private LocalDateTime upddt;//更新時間

}
