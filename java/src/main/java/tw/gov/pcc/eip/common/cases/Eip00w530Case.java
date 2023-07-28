package tw.gov.pcc.eip.common.cases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private String radioContent;

    /**
     * 意見調查主畫面用case
     */
    @Data
    public static class OsCase {
        private String osformno;
        private String topicname;
        private String status;
        private String iscompleted;
        private String isdisstatic;
    }

    /**
     * 意見調查答案case
     * 簡化名稱是為了縮減json長度以便能容納更多答案
     * 1.n為問題流水號qseqno
     * 2.os為選擇題答案List，填空題os將不會出現在json內
     * 3.t為填空題答案，選擇題t將不會出現在json內
     */
    @Data
    public static class Answer {
        private String n;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<Multiple> os;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String t;
        @JsonIgnore
        private String isrequired;
        @JsonIgnore
        private String optiontype;
    }

    /**
     * 意見調查選擇題答案case
     * 簡化名稱是為了縮減json長度以便能容納更多答案
     * 1.q為問題流水號qseqno，多選題才會出現在json內
     * 2.v為選擇題答案為iseqno
     * 3.t為選擇題的有輸入框答案，有填才出現在json內
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Multiple {
        private String q;
        private String v;
        private String t;
    }

    @Data
    public static class RadioContent {
        private String n;
        private String v;
        private String t;
    }
}
