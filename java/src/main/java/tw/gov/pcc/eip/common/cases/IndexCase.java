package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 首頁共用參數
 *
 * @author swho
 */
@Data
@NoArgsConstructor
public class IndexCase implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fseq;
    private String seq;
    private String key;
    private String subject;
}
