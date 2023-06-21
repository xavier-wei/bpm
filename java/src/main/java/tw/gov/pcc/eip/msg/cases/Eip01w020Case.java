package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 訊息篇數統計
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w020Case implements Serializable {

    private static final long serialVersionUID = -2337194848638971177L;

    /** 分類名稱(訊息類別) */
    private String msgtype;
    /** 主旨/連結網址 */
    private String subject;
    /** 連絡單位 */
    private String contactunit;
    /** 建立人員 */
    private String creatid;
    /** 更新人員 */
    private String updid;
    /** 上架時間起 */
    private String releasedts;
    /** 上架時間迄 */
    private String releasedte;

    private List<Option> msgtypes = new ArrayList<>(); // 分類名稱 訊息類別選單

    private List<Option> contactunits = new ArrayList<>(); // 聯絡單位選單

    /** 畫面選單用 */
    @Data
    public static class Option {

        private String codeno;

        private String codename;
    }

    private List<Eip01w020PageCase> qryList = null; // 畫面上篇數統計

}
