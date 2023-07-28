package tw.gov.pcc.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author swho
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1600459774261048770L;
    /**
     * 使用者代碼
     */
    private String userId;
    /**
     * 使用者名稱
     */
    private String userName;
    /**
     * 員工編號
     */
    private String empId;
    /**
     * 部門代碼
     */
    private String deptId;
    /**
     * 電話
     */
    private String tel1;
    /**
     * 分機
     */
    private String tel2;
    /**
     * 職稱代號
     */
    private String titleId;
    /**
     * LINE TOKEN
     */
    private String lineToken;
    /**
     * 電子信箱
     */
    private String email;
    /**
     * 機關代碼
     */
    private String orgId;
}
