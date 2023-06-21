package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 訊息篇數統計 畫面顯示
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w020PageCase implements Serializable {

    private static final long serialVersionUID = -8266684813651505821L;

    private String deptname;

    private String count;
}
