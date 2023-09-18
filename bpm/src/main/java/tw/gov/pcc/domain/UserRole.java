package tw.gov.pcc.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "USER_ROLES")
@IdClass(UserRolesPrimaryKey.class)
public class UserRole implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String userId;


    @Id
    @Column(name = "SYS_ID")
    private String sysId;

    @Id
    @Column(name = "DEPT_ID")
    private String deptId;

    @Id
    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    @Column(name = "CREATE_TIMESTAMP")
    private Timestamp createTimestamp;

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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

}
