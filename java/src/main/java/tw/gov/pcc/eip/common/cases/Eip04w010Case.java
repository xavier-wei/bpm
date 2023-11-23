package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 線上報名分類管理Case
 * @author Weith
 */
@Data
public class Eip04w010Case implements Serializable {
    private static final long serialVersionUID = 453987072098821016L;

    private String orccodeList;//畫面所選表單編號列表
    private Long orccode;
    @RequiredString(label = "分類名稱")
    public String orcname;
    @RequiredString(label = "是否為課程")
    public String iscourse;
    @RequiredString(label = "簽到表格式")
    public String signform;
    public String mode;

    @Data
    public static class OrcCase {
        private Long orccode;
        private String orcname;
        private String iscourse;
        private String signform;
        private boolean isStarting;
    }

    private List<OrcCase> orcList = new ArrayList<>();

}
