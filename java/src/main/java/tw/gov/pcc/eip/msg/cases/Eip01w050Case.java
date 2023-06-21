package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 輿情專區
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w050Case implements Serializable {

    private static final long serialVersionUID = -6209324226632288263L;

    private List<Detail> qryList;

    /** 查詢結果選單用 */
    @Data
    public static class Detail {

        private String fseq; // 項次

        private String subject; // 主題

        private String msgtype; // 類別

        private String releasedt; // 上架時間：

        private String contactunit; // 發布單位

        private String mcontent; // 內文

        private Map<String, String> file = null;

        private String upddt; // 更新日期

        private String contactperson; // 聯絡人

        private String contacttel; // 聯絡電話
    }

    private String fseq;

    private String seq;

    private String filename;
}
