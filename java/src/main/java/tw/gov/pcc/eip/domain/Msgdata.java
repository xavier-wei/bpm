package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.MsgdataDao;

/**
 * 訊息上稿
 * 
 * @author vita
 *
 */
@Table(MsgdataDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Msgdata implements Serializable {

    private static final long serialVersionUID = 22905190256633329L;

    /** 項次 */
    @PkeyField("FSEQ")
    @LogField("FSEQ")
    private String fseq; // 系統自動編號
    /** 頁面型態 */
    @LogField("PAGETYPE")
    private String pagetype; // A:文章 B:連結
    /** 狀態 */
    @LogField("STATUS")
    private String status; // 表單狀態： 0-處理中(暫存) 1-已上稿 2-已核可 3-核退 4-已上架 5-已下架 X-註銷(畫面自已上架維護成註銷)
    /** 屬性 */
    @LogField("ATTRIBUTYPE")
    private String attributype; // 1:公告事項 2:最新消息 3:常用系統及網站 4:下載專區 5:輿情專區 6:人事室-行政院組織改造 7:各處室資訊網-單位簡介
                                // 8:各處室資訊網-業務資訊
    /** 訊息類別 */
    @LogField("MSGTYPE")
    private String msgtype;
    /** 顯示位置 */
    @LogField("LOCATEAREA")
    private String locatearea; // 1:登入前 2:登入後 3:各處室資訊網
    /** 是否提供外部查詢 */
    @LogField("ISSEARCH")
    private String issearch; // 1:是 2:否
    /** 顯示順序 */
    @LogField("SHOWORDER")
    private String showorder; // 1~99，數字愈小，優先序愈高
    /** 是否置頂 */
    @LogField("ISTOP")
    private String istop; // 1:是 2:否
    /** 前台是否顯示 */
    @LogField("ISFRONT")
    private String isfront; // 1:是 2:否
    /** 主旨/連結網址 */
    @LogField("SUBJECT")
    private String subject;
    /** 內文 */
    @LogField("MCONTENT")
    private String mcontent;
    /** 存放目錄 */
    @LogField("INDIR")
    private String indir; // 存放的目錄路徑EX:/A/BC/
    /** 是否需要另開視窗 */
    @LogField("OPLINK")
    private String oplink; // Y:是 N:否
    /** 上架時間 */
    @LogField("RELEASEDT")
    private String releasedt;
    /** 下架時間 */
    @LogField("OFFTIME")
    private String offtime;
    /** 連絡單位 */
    @LogField("CONTACTUNIT")
    private String contactunit;
    /** 聯絡人 */
    @LogField("CONTACTPERSON")
    private String contactperson;
    /** 連絡電話 */
    @LogField("CONTACTTEL")
    private String contacttel;
    /** 備註 */
    @LogField("MEMO")
    private String memo;
    /** 下架原因 */
    @LogField("OFFREASON")
    private String offreason;
    /** 建立人員 */
    @LogField("CREATID")
    private String creatid;
    /** 建立時間 */
    @LogField("CREATDT")
    private LocalDateTime creatdt;
    /** 更新人員 */
    @LogField("UPDID")
    private String updid;
    /** 更新時間 */
    @LogField("UPDDT")
    private LocalDateTime upddt;
    
    private String statusText;
    private String attributypeText;
    private String creatName;
    private String creatDeptId;
}
