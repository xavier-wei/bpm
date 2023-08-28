package tw.gov.pcc.eip.domain;

import lombok.*;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.EipmaildataDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 郵件寄件資料檔
 *
 * @author swho
 */
@Table(EipmaildataDao.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Eipmaildata implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Mail ID
     */
    @PkeyField
    @LogField
    private String mail_id;

    /**
     * 批號
     */
    @LogField
    private String batch_no;

    /**
     * 電子郵件種類
     */
    @LogField
    private String mail_kind;

    /**
     * 收件者
     */
    @LogField
    private String email;

    /**
     * 主旨
     */
    @LogField
    private String subject;

    /**
     * 內容
     */
    @LogField
    private String message;

    /**
     * 附件實際路徑
     */
    @LogField
    private String file_path;

    /**
     * 是否發信
     */
    @LogField
    private String is_mailed;

    /**
     * 處理時間
     */
    @LogField
    private LocalDateTime process_timestamp;

    /**
     * 回傳訊息
     */
    @LogField
    private String return_message;

    /**
     * 附件檔名
     */
    @LogField
    private String attach_file_name;

    /**
     * IS_MAILED 種類
     */
    @Getter
    public enum IS_MAILED{
        /**
         * 未處理，排程寄信
         */
        UNPROCESSED(""),
        /**
         * 未處理，線上即時寄信
         */
        ONLINE("O"),
        /**
         * 已處理，錯誤
         */
        ERROR("E"),
        /**
         * 已處理，已寄出
         */
        PROCESSED("Y");
        private final String value;
        IS_MAILED(String value) {
            this.value=value;
        }
    }
    

}