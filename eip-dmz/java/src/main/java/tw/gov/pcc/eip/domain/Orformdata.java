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
  private String orformno;//表單編號
  @LogField("orccode")
  private Long orccode;//分類代碼
  @LogField("courseclacode")
  private Long courseclacode;//課程分類代碼
  @LogField("coursecode")
  private String coursecode;//課程代碼
  @LogField("classcode")
  private String classcode;//班別代碼
  @LogField("period")
  private Long period;//期別
  @LogField("topicname")
  private String topicname;//主題名稱
  @LogField("topicdesc")
  private String topicdesc;//內容說盟
  @LogField("status")
  private String status;//狀態
  @LogField("organizer")
  private String organizer;//主辦單位
  @LogField("contacter")
  private String contacter;//聯絡人
  @LogField("contactnum")
  private String contactnum;//連絡電話
  @LogField("fax")
  private String fax;//傳真
  @LogField("email")
  private String email;//電子郵件信箱
  @LogField("addres")
  private String addres;//地點
  @LogField("country")
  private String country;//上課縣市
  @LogField("profmdt")
  private LocalDateTime profmdt;//辦理開始時間
  @LogField("proendt")
  private LocalDateTime proendt;//辦理結束時間
  @LogField("acceptappnum")
  private Long acceptappnum;//接受報名人數
  @LogField("allowappnum")
  private Long allowappnum;//允許報名人數
  @LogField("allowappway")
  private String allowappway;//允許報名方式
  @LogField("fee")
  private BigDecimal fee;//費用
  @LogField("account")
  private String account;//報名繳費帳號
  @LogField("ISMEALS")
  private String ismeals;//提供餐點
  @LogField("CLASSHOURS")
  private String classhours;//上課時數
  @LogField("CERTIHOURS")
  private String certihours;//認證時數
  @LogField("REGISFMDT")
  private LocalDateTime regisfmdt;//報名開始時間
  private String regisfmdtStr;//報名開始時間中文
  @LogField("REGISENDT")
  private LocalDateTime regisendt;//報名結束時間
  private String regisendtStr;//報名結束時間中文
  @LogField("LECTURERCODE")
  private String lecturercode;//講師代號
  @LogField("PASSMSG")
  private String passmsg;//報名審核通過之電子郵件訊息
  @LogField("REJECTMST")
  private String rejectmst;//報名審核不通過之電子郵件訊息
  @LogField("REGISQUAL")
  private String regisqual;//報名資格
  @LogField("REMARK")
  private String remark;//備註
  @LogField("CREUSER")
  private String creuser;//建立人員
  @LogField("CREDT")
  private LocalDateTime credt;//建立時間
  @LogField("UPDUSER")
  private String upduser;//更新人員
  @LogField("UPDDT")
  private LocalDateTime upddt;//更新時間
  @LogField("SUBJECT")
  private String subject;//主旨
  private Long actualappnum;//實際報名人數
  private Long passnum;//通過審核人數
}
