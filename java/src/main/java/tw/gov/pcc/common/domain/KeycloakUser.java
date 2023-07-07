package tw.gov.pcc.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author swho
 */
@Data
public class KeycloakUser implements Serializable {
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


    public String getTel1() {
        return tel1;
    }


    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }


    public String getTel2() {
        return tel2;
    }


    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }


    public String getTitleId() {
        return titleId;
    }


    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }


    public String getLineToken() {
        return lineToken;
    }


    public void setLineToken(String lineToken) {
        this.lineToken = lineToken;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
