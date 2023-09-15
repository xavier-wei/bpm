package tw.gov.pcc.eip.adm.cases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 個人儀錶板 case
 */
@Data
@NoArgsConstructor
public class Eip00w230Case implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<TabCase> tabList;
    private List<TabCase> pickTabList;
    private List<TabCase> fullTabList;
    private String pickTabListString;

    @Data
    @AllArgsConstructor
    @Builder
    public static class TabCase {
        private String item_name;
        private String dashboard_fig_id;
    }
}
