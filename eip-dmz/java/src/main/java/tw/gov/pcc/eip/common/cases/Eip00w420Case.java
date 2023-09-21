package tw.gov.pcc.eip.common.cases;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 線上報名列表
 * @author Weith
 */
@Data
public class Eip00w420Case implements Serializable {
    private static final long serialVersionUID = 8632137217319987182L;

    private String qKeyword;

    private String qStatus;

    private String qStartYear;

    private String qStartMonth;

    private String qEndYear;

    private String qEndMonth;

    private List<String>orformnoList;//畫面所選表單編號列表

    private String selectAction;

    private Map<String,String> statusCombobox;

    private Map<Long,String> orccodeCombobox;

    private Map<String,String> regstatusCombobox;

    private Map<String,String> regisqualCheckboxU;

    private Map<String,String> regisqualCheckboxE1;

    private Map<String,String> regisqualCheckboxE2;

    private Map<String,String> regisqualCheckboxE3;

    private Map<String,String> regisqualCheckboxE4;

    private Map<String,String> degreenCombobox;

    private String mode;

    private String orformno;

    private String vKeyword;

    private String vStatus;

    private String isPass;

    private String cKeyword;

    private String topicname;

    @Data
    public static class OrCase {
        private String orformno;
        public String orcname;

        public String topicname;

        public String status;
        public String statusVal;
        public long acceptappnum;
        public long actualappnum;
        private String actualappnumAbbr;
        public long passnum;
        private String passnumAbbr;
        public String regisfmdt;
        public String regisendt;
    }

    private List<OrCase> orList = new ArrayList<>();

    private List<Eip00w420HisCase> hisList = new ArrayList<>();

    private List<Eip00w420VerifyCase> verList = new ArrayList<>();

    private List<Eip00w420VerifyCase> cerList = new ArrayList<>();

    private List<String> certihoursList;

}
