package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.MsgdepositdirDao;

/**
 * 訊息檔案存放目錄表
 * 
 * @author vita
 *
 */
@Table(MsgdepositdirDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Msgdepositdir implements Serializable {

    private static final long serialVersionUID = 955906747094054736L;

    /** 檔案流水號 */
    @PkeyField("FILESEQ")
    @LogField("FILESEQ")
    private String fileseq;
    /** 屬性 */
    @LogField("ATTRIBUTYPE")
    private String attributype; // 1:公告事項 2:最新消息 3:常用系統及網站 4:下載專區 5:輿情專區 6:人事室-行政院組織改造 7:各處室資訊網-單位簡介
                                // 8:各處室資訊網-業務資訊
    /** 檔案存放父階層 */
    @LogField("EXISTHIER")
    private String existhier; // 第一層為第一碼 第二層為第二碼 . . 編號方式為自大寫A~Z,0~9,小寫a~z
    /** 排序位置 */
    @LogField("SORTORDER")
    private String sortorder; // 在每層下的檔案順序，自1開始編 在父階層下的順序EX: 在父階層為AA下面的第一個檔案就編號1
    /** 檔案路徑 */
    @LogField("FILEPATH")
    private String filepath; // EX:/A/BC/
    /** 檔案/資料夾名稱 */
    @LogField("FILENAME")
    private String filename;
    /** 是否啟用 */
    @LogField("DISABLE")
    private String disable; // NULL:啟用中，Y:資料夾失效
    /** 建立人員 */
    @LogField("CREATID")
    private String creatid;
    /** 建立時間 */
    @LogField("CREATDT")
    private LocalDateTime creatdt;

}
