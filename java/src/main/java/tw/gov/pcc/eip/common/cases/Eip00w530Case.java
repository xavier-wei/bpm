package tw.gov.pcc.eip.common.cases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 意見調查Case
 * @author Weith
 */
@Data
public class Eip00w530Case implements Serializable {
    private static final long serialVersionUID = 8238531139929455L;

    private String osformno;

    private String promptmsg;

    private List<OsCase> osList;

    private List<OsCase> hisList;

    private Eip00w520ThemeCase themeCase;

    private List<Answer> wricontent;

    private List<Eip00w520QuestionCase> previews = new ArrayList<>();

    @Data
    public static class OsCase {
        private String osformno;
        private String topicname;
        private String status;
        private String iscompleted;
        private String isdisstatic;
    }

    @Data
    public static class Answer {
        @JsonProperty("os")
        private List<Multiple> checkboxList;
        @JsonProperty("t")
        private String text;
        @JsonIgnore
        private String isrequired;
        @JsonIgnore
        private String optiontype;
    }

    @Data
    public static class Multiple {
        @JsonProperty("v")
        private String checkVal;
        @JsonProperty("t")
        private String text;
    }
}
