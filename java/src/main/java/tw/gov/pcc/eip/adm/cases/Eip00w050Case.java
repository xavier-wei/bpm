package tw.gov.pcc.eip.adm.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Systems;

import java.io.Serializable;
import java.util.List;

/**
 * 選單資料維護
 *
 * @author swho
 */
@Data
@NoArgsConstructor
public class Eip00w050Case implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sys_id;
    private String item_id;
    private String pid;
    private String action_type;
    private String item_name;
    private String hyperlink;
    private String sort_order;
    private String sub_link;
    private String disable;

    private List<Systems> systems;
}
