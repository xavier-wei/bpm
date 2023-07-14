package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 訊息上稿相關 檔案下載
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01wFileCase implements Serializable {

    private static final long serialVersionUID = -1969013743829636614L;

    private String fseq;

    private String seq;

    private String filename;

    private String subject;

}
