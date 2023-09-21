package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import java.io.Serializable;

/**
 * 系統訊息檔 (SYSMSG)
 *
 * @author Goston
 */
@Table("SYSMSG")
@NoArgsConstructor
@Data
public class Sysmsg implements Serializable {
    private static final long serialVersionUID = -7654086330097357475L;
    @PkeyField
    @LogField
    private String code; // 訊息代碼
    @LogField
    private String value; // 訊息內容
}
