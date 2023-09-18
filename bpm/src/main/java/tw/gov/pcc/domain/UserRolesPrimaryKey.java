package tw.gov.pcc.domain;

import java.io.Serializable;
import java.util.Objects;

public class UserRolesPrimaryKey implements Serializable {
    private String userId;
    private String sysId;
    private String deptId;
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolesPrimaryKey that = (UserRolesPrimaryKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(sysId, that.sysId) && Objects.equals(deptId, that.deptId) && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sysId, deptId, roleId);
    }
}
