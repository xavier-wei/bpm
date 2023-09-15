package tw.gov.pcc.eip.common.cases;

import lombok.Data;

import java.io.Serializable;

/**
 * 線上報名列表-修改歷程Case
 * @author Weith
 */
@Data
public class Eip04w020HisCase implements Serializable {
    private static final long serialVersionUID = 3473429960342933L;

    private String uppdt;

    private String chgtype;

    private String operator;

    private String operatorDept;

    private String orformno;

}
