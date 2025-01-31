package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.List;

/**
 * 意見調查主題列表-選項Case
 * @author Weith
 */
@Data
public class Eip00w520OptionCase implements Serializable {
    private static final long serialVersionUID = 6992342424329231L;
    private String osformno;//表單編號
    private String iseqno;//選項序號
    private String qseqno;//問題流水號
    @RequiredString(label = "選項排序")
    private String itemseq;//選項排序
    @RequiredString(label = "選項說明")
    private String itemdesc;//選項說明
    @RequiredString(label = "是否增加文字框")
    private String isaddtext;//是否增加文字框
    private String topicname;//主題名稱
    private String sectitleseq;//部分排序
    private String sectitle;//部分標題
    private String topicseq;//題目序號
    private String topic;//題目
    private String mode;
    private List<String> iseqnoList;//畫面所選題目序號列表(選項列表)
}
