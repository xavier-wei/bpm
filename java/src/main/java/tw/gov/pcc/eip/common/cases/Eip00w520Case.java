package tw.gov.pcc.eip.common.cases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

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

    private List<Eip00w520QuestionCase> contents = new ArrayList<>();

    private List<MixCase> reviews = new ArrayList<>();

    private Map<Integer,String> osccodeCombobox;

    private Map<String,String> limitvoteCheckboxU;

    private Map<String,String> limitvoteCheckboxE1;

    private Map<String,String> limitvoteCheckboxE2;

    private Map<String,String> limitvoteCheckboxE3;

    private Map<String,String> limitvoteCheckboxE4;

    private Eip00w520ThemeCase themeCase;

    private List<Answer> wricontent;

    private List<Textcontent> reviewTextcontents;//收集畫面上勾選的流水號與文字內容，供統計查詢使用

    private List<String> reviewMultiplecontents;//收集畫面上勾選的流水號，供統計查詢使用

    Map<String, Statistics> textDataMap = new LinkedHashMap<>();//填空題統計資料，供統計使用

    Map<String, Statistics> multipleDataMap = new LinkedHashMap<>();//選擇題統計資料，供統計使用

    Map<String,List<String>> textUiMap = new LinkedHashMap<>();//填空題列表資料，供統計使用

    Map<String,String> qseqnoTextMap = new HashMap<>();//有填入文字的資料，供統計使用

    Map<String,Integer> titleRowspanMap = new HashMap<>();//合併列數資料，根據sectitleseq分類，供統計使用

    Map<String,Integer> quesRowspanMap = new HashMap<>();//合併列數資料，根據qseno分類，供統計使用

    List<String>writeContentTitle = new ArrayList<>();//填寫內容表頭

    List<List<String>> writeContentData = new ArrayList<>();//填寫內容資料

    @Data
    public static class OsCase {
        private String osformno;
        private String topicname;
        private String osfmdt;
        private String osendt;
        private String status;
        private String statusVal;
        private String isanonymous;
    }

    @Data
    public static class Answer {
        private String n;//填空題題號
        private List<Multiple> os;//選擇題答案
        private String t;//填空題答案
        @JsonIgnore
        private String isrequired;
        @JsonIgnore
        private String optiontype;
    }

    @Data
    public static class Multiple {
        private String q;//選擇題題號
        private String v;//選擇題答案
        private String t;//選擇題文字輸入框
    }

    @Data
    public static class Statistics {
        private String count;
        private String rate;
    }

    @Data
    public static class Textcontent {
        private String qseqno;//填空題題號
        private String text;//填空題內容題號
    }

    @Data
    public static class MixCase {
        private String qseqno;//問題流水號
        private String sectitleseq;//部分排序
        private String sectitle;//部分標題
        private String no;//iseqno
        private String itemname;//題目名稱(文字框)或選項名稱
        private String topic;//選擇題題目名稱
        private String topicseq;//選擇題題目序號
    }

    /**
     * 清除request在切換畫面時不需留存的field
     */
    public void clear() {
        this.sectitle = null;
        this.sectitleseq = null;
    }

}
