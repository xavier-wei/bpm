package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class CursorAcl {
    private static final long serialVersionUID = -1638791944601576476L;

    private String itemNo;
    private String itemNoP;
    private String levelv;
    private String itemId;
    private String parentItemId;
    private String itemName;
    private String url;
    private String isDisable;
    private String isLeaf;
    private String sortOrder;
    private String funcId;
    private String isChecked;

}
