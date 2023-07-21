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

    private List<String>sectitleseqList;//畫面所選部分標題序號列表(部分列表)

    private List<String>qseqnoList;//畫面所選題目序號列表(子部分列表)

    private List<String> iseqnoList;//畫面所選題目序號列表(選項列表)

    private String selectAction;

    private String mode;

    private String osformno;

    private String qseqno;

    private String topicname;

    private String sectitle;

    private String sectitleseq;

    private List<OsCase> osList = new ArrayList<>();

    private List<Eip00w520PartCase> partList = new ArrayList<>();

    private List<Eip00w520QuestionCase> questionList = new ArrayList<>();

    private List<Eip00w520OptionCase> optionList = new ArrayList<>();

    private List<Eip00w520QuestionCase> previews = new ArrayList<>();

    private Map<Integer,String> osccodeCombobox;

    private Map<String,String> limitvoteCheckboxU;

    private Map<String,String> limitvoteCheckboxE1;

    private Map<String,String> limitvoteCheckboxE2;

    private Map<String,String> limitvoteCheckboxE3;

    private Map<String,String> limitvoteCheckboxE4;

    private Eip00w520ThemeCase themeCase;

    private List<Answer> wricontent;

    @Data
    public static class OsCase {
        private String osformno;
        private String topicname;
        private String osfmdt;
        private String osendt;
        private String status;
        private String statusVal;
    }

    @Data
    public static class Answer {
        private List<Multiple> checkboxList;
        private String text;
        private String isrequired;
        private String optiontype;
    }

    @Data
    public static class Multiple {
        private String checkVal;
        private String text;
    }

    /**
     * 清除request在切換畫面時不需留存的field
     */
    public void clear() {
        this.sectitle = null;
        this.sectitleseq = null;
    }

}
