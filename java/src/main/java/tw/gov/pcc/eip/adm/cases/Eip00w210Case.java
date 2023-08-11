package tw.gov.pcc.eip.adm.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import tw.gov.pcc.eip.framework.validation.MaxValue;
import tw.gov.pcc.eip.framework.validation.MinValue;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.List;

/**
 * @author swho
 */
@Data
@NoArgsConstructor
public class Eip00w210Case implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Pair<String, String>> levelList;

    @RequiredString(label = "顯示階層")
    @MaxValue(label = "階層", value = 2)
    @MinValue(label = "階層", value = 1)
    private String level;
}
