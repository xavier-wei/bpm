package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.MsgdepositDao;

/**
 * 訊息存放檔案表
 * 
 * @author vita
 *
 */
@Table(MsgdepositDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Msgdeposit implements Serializable {

    private static final long serialVersionUID = -6290585183284874933L;

    /** 項次 */
    @PkeyField("FSEQ")
    @LogField("FSEQ")
    private String fseq; // 系統自動編號
    /** 流水號 */
    @PkeyField("SEQ")
    @LogField("SEQ")
    private String seq; // 自1開紿編號
    /** 檔案種類 */
    @LogField("FILETYPE")
    private String filetype; // 1:附檔 2:內文圖檔
    /** 附加檔案名稱 */
    @LogField("ATTACHFILE")
    private String attachfile; //
    /** 附檔種類 */
    @LogField("ATTACHTYPE")
    private String attachtype; // 附檔種類如：doc,docx,xls,xlsx,pdf,jpg…
    /** 檔案存放名稱 */
    @LogField("REALFILENAME")
    private String realfilename; // 放置於FILESERVER的實際檔名 存放時檔名變造規則：FSEQ+’_’+SEQ+’_’+ATTACHFILE

    private int cnt;
}
