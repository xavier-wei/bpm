package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公告事項
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w030Case implements Serializable {

    private static final long serialVersionUID = 3534337690249641938L;

    private String msgtype; // 訊息類別

    private String theme; // 主題 / 主旨

    private List<Option> optList;

    /** 畫面選單用 */
    @Data
    public static class Option {
        
        private String codeno;
        
        private String codename;
    }

    /** 查詢結果 */
    private List<Eip01wPopCase> qryList;

    // 檔案下載
    private String fseq;

    private String seq;

    private String filename;

    private String subject;

}
