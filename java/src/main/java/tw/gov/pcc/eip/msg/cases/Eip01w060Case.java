package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 單位簡介
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w060Case implements Serializable {

    private static final long serialVersionUID = -3753352281956848825L;

    private Map<String, String> depts = null; // 部門清單

    private String dept;

    private List<String> msgs; // 簡介內容

    private Map<String, String> files = null; // 附檔清單

}
