package tw.gov.pcc.eip.adm.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.framework.validation.RequiredInteger;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;

/**
 * 選單資料維護
 *
 * @author swho
 */
@Data
@NoArgsConstructor
public class Eip00w050Case implements Serializable {

    private static final long serialVersionUID = 1L;

    private String item_id;
    @RequiredString(label = "上層功能")
    private String pid;
    private String action_type;
    @RequiredString(label = "顯示名稱")
    private String item_name;
    private String hyperlink;
    @RequiredString(label = "排序")
    @RequiredInteger(label = "排序")
    private String sort_order;
    @RequiredString(label = "功能編號")
    private String sub_link;
    private String disable;
}
