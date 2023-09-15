package tw.gov.pcc.eip.common.cases;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 線上報名列表
 * @author Weith
 */
@Data
public class Eip04w020Case implements Serializable {
    private static final long serialVersionUID = 8632137217319987182L;

    public interface Advquery {
        // validation group marker interface
    }

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

    private Map<String,String> countryCombobox;

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

    private List<Eip04w020HisCase> hisList = new ArrayList<>();

    private List<Eip04w020VerifyCase> verList = new ArrayList<>();

    private List<Eip04w020VerifyCase> cerList = new ArrayList<>();

    private List<String> certihoursList;

    @AssertTrue(message = "活動起迄區間至少需完整填寫起或迄(例:112/01)，皆不填寫則查詢全部", groups = {Advquery.class})
    private boolean isStartEndValid() {
        int sLength = qStartYear.length() + qStartMonth.length();
        int eLength = qEndYear.length() + qEndMonth.length();
        return sLength != 3 && sLength != 2 && eLength != 3 && eLength != 2;
    }

}
