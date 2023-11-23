package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 線上報名分類列表Case
 * @author Weith
 */
@Data
public class Eip05w010Case implements Serializable {
    private static final long serialVersionUID = 74386991231014363L;

    private String osccodeList;//畫面所選表單編號列表
    private String osccode;
    @RequiredString(label = "分類名稱")
    public String oscname;
    public String mode;

    @Data
    public static class OscCase {
        private String osccode;
        private String oscname;
        private String upddt;
        private boolean isStarting;
    }

    private List<OscCase> oscList = new ArrayList<>();

}
