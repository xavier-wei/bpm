package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 儲存畫面共用參數
 *
 * @author swho
 */
@Data
@NoArgsConstructor
public class Eipxx0w030Case implements Serializable {
    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Exclude
    private String closed;
    private String entryListOrder;
}
