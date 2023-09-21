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
  private String orformno;//表單編號
  @PkeyField("SEQNO")
  @LogField("SEQNO")
  private String seqno;//報名序號
  @LogField("REGISWAY")
  private String regisway;//報名方式
  @LogField("REGISNAME")
  private String regisname;//姓名
  @LogField("REGISIDN")
  private String regisidn;//身分證號
  @LogField("REGISSEX")
  private String regissex;//性別
  @LogField("REGISBRTH")
  private String regisbrth;//生日
  @LogField("REGISEMAIL")
  private String regisemail;//電子郵件信箱
  @LogField("REGISPHONE")
  private String regisphone;//連絡電話
  @LogField("FAX")
  private String fax;//傳真
  @LogField("COMPANY")
  private String company;//機關名稱/公司全銜(發票抬頭)
  @LogField("DEGREEN")
  private Integer degreen;//學分學位
  @LogField("DEPT")
  private String dept;//部門
  @LogField("JOGTITLE")
  private String jogtitle;//職稱
  @LogField("REGISADDRES")
  private String regisaddres;//聯絡地址
  @LogField("MEALSTATUS")
  private String mealstatus;//用餐狀況
  @LogField("ISPAY")
  private String ispay;//是否繳費
  @LogField("ISPASS")
  private String ispass;//是否通過審核
  @LogField("REGISDT")
  private LocalDateTime regisdt;//報名日期時間
  @LogField("ISNOTIFY")
  private String isnotify;//是否通知報名者
  @LogField("LASTNOTIDT")
  private LocalDateTime lastnotidt;//上次通知日期
  @LogField("CERTIPHOURS")
  private String certiphours;//認證實體時數
  @LogField("CERTIDHOURS")
  private String certidhours;//認證數位時數
  @LogField("CREUSER")
  private String creuser;//建立人員
  @LogField("CREDT")
  private LocalDateTime credt;//建立日期
  @LogField("UPDUSER")
  private String upduser;//更新人員
  @LogField("UPDDT")
  private LocalDateTime upddt;//更新時間

}
