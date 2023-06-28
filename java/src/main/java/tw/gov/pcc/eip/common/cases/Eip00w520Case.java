package tw.gov.pcc.eip.common.cases;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 意見調查主題列表
 * @author Weith
 */
@Data
public class Eip00w520Case implements Serializable {
    private static final long serialVersionUID = 12835339304543359L;

    private List<String>osformnoList;//畫面所選表單編號列表

    private String selectAction;

    private String mode;

    private String osformno;

    private String topicname;

    private List<OsCase> osList = new ArrayList<>();

    private Map<Integer,String> osccodeCombobox;

    private Map<String,String> limitvoteCheckboxU;

    private Map<String,String> limitvoteCheckboxE1;

    private Map<String,String> limitvoteCheckboxE2;

    private Map<String,String> limitvoteCheckboxE3;

    private Map<String,String> limitvoteCheckboxE4;
    @Data
    public static class OsCase {
        private String osformno;
        private String topicname;
        private String osfmdt;
        private String osendt;
        private String status;
        private String statusVal;
    }

}
