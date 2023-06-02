package tw.gov.pcc.common.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author swho
 */
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

    public KeycloakUser() {
    }

    /**
     * 使用者代碼
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 使用者代碼
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * 使用者名稱
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 使用者名稱
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * 員工編號
     */
    public String getEmpId() {
        return this.empId;
    }

    /**
     * 員工編號
     */
    public void setEmpId(final String empId) {
        this.empId = empId;
    }

    /**
     * 部門代碼
     */
    public String getDeptId() {
        return this.deptId;
    }

    /**
     * 部門代碼
     */
    public void setDeptId(final String deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof KeycloakUser)) return false;
        final KeycloakUser other = (KeycloakUser) o;
        if (!other.canEqual(this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (!Objects.equals(this$userId, other$userId)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (!Objects.equals(this$userName, other$userName)) return false;
        final Object this$empId = this.getEmpId();
        final Object other$empId = other.getEmpId();
        if (!Objects.equals(this$empId, other$empId)) return false;
        final Object this$deptId = this.getDeptId();
        final Object other$deptId = other.getDeptId();
        return Objects.equals(this$deptId, other$deptId);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof KeycloakUser;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $empId = this.getEmpId();
        result = result * PRIME + ($empId == null ? 43 : $empId.hashCode());
        final Object $deptId = this.getDeptId();
        result = result * PRIME + ($deptId == null ? 43 : $deptId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "KeycloakUser(userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", empId=" + this.getEmpId() + ", deptId=" + this.getDeptId() + ")";
    }
}
