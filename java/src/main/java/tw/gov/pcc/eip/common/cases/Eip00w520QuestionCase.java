package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 意見調查主題列表-題目Case
 * @author Weith
 */
@Data
public class Eip00w520QuestionCase implements Serializable {
    private static final long serialVersionUID = 43912344325198372L;
    private String osformno;//表單編號
    private String qseqno;//問題流水號
    private String topicname;//主題名稱
    private String sectitleseq;//部分排序
    private String sectitle;//部分標題
    @RequiredString(label = "題目排序")
    private String topicseq;//題目序號
    @RequiredString(label = "題目")
    private String topic;//題目
    @RequiredString(label = "選項類別")
    private String optiontype;//選項類別
    @RequiredString(label = "是否必填")
    private String isrequired;//是否必填
    private String mode;
    private int rowspan;
    private List<Eip00w520OptionCase> optionList = new ArrayList<>();
}
