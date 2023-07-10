package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 儲存畫面共用參數
 *
 * @author swho
 */
@Data
@NoArgsConstructor
public class Eipxx0w030Case {
    @EqualsAndHashCode.Exclude
    private String closed;
    private String entryListOrder;
}
