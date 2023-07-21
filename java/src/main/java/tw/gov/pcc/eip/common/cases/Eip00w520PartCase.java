package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 意見調查主題列表-部分Case
 * @author Weith
 */
@Data
public class Eip00w520PartCase implements Serializable {
    private static final long serialVersionUID = 43912344325198372L;
    private String osformno;
    private String qseqno;
    private String topicname;
    @RequiredString(label = "部分排序")
    private String sectitleseq;
    @RequiredString(label = "部分標題")
    private String sectitle;
    private String mode;
    private List<Eip00w520QuestionCase> questionList = new ArrayList<>();
    private int length;

    /**
     * 清除request在切換畫面時不需留存的field
     */
    public void clear() {
        this.sectitle = null;
        this.sectitleseq = null;
    }
}
