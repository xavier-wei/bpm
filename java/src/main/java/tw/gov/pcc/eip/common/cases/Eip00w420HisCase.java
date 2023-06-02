package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 線上報名列表-修改歷程Case
 * @author Weith
 */
@Data
public class Eip00w420HisCase implements Serializable {
    private static final long serialVersionUID = 3473429960342933L;

    private String uppdt;

    private String chgtype;

    private String operator;

    private String operatorDept;

    private String orformno;

}
