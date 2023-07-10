package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下載專區
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w040Case implements Serializable {

    private static final long serialVersionUID = -4212326298581158962L;

    public List<Detail> qryList = null;

    public String treenode;

    public String path;

    public String keyword;

    public String key = "";

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
