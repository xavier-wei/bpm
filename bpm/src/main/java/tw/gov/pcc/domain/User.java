package tw.gov.pcc.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ACNT_IS_VALID")
    private String acntIsValid;

    @Column(name = "CREATE_TIMESTAMP")
    private Timestamp createTimestamp;

    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    @Column(name = "DEPT_ID")
    private String deptId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "EMP_ID")
    private String empId;

    @Column(name = "LAST_LOGIN_DATE")
    private Timestamp lastLoginDate;

    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIp;

    @Column(name = "LDAP_ID")
    private String ldapId;

    @Column(name = "MODIFY_USER_ID")
    private String modifyUserId;

    @Column(name = "MODIFY_TIMESTAMP")
    private Timestamp modifyTimestamp;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "tel1")
    private String tel1;

    @Column(name = "tel2")
    private String tel2;

    @Column(name = "TITLE_ID")
    private String titleId;

    @Column(name = "LINE_TOKEN")
    private String lineToken;

    @Column(name = "FROM_HR")
    private String fromHr;

    @Column(name = "ORG_ID")
    private String orgId;

    @Transient
    private String titleName;

    @Transient
    private String userRole;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcntIsValid() {
        return acntIsValid;
    }

    public void setAcntIsValid(String acntIsValid) {
        this.acntIsValid = acntIsValid;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLdapId() {
        return ldapId;
    }

    public void setLdapId(String ldapId) {
        this.ldapId = ldapId;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Timestamp getModifyTimestamp() {
        return modifyTimestamp;
    }

    public void setModifyTimestamp(Timestamp modifyTimestamp) {
        this.modifyTimestamp = modifyTimestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getFromHr() {
        return fromHr;
    }

    public void setFromHr(String fromHr) {
        this.fromHr = fromHr;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId='" + userId + '\'' +
            ", acntIsValid=" + acntIsValid +
            ", createTimestamp=" + createTimestamp +
            ", createUserId='" + createUserId + '\'' +
            ", deptId='" + deptId + '\'' +
            ", email='" + email + '\'' +
            ", empId='" + empId + '\'' +
            ", lastLoginDate=" + lastLoginDate +
            ", lastLoginIp='" + lastLoginIp + '\'' +
            ", ldapId='" + ldapId + '\'' +
            ", modifyUserId='" + modifyUserId + '\'' +
            ", modifyTimestamp=" + modifyTimestamp +
            ", userName='" + userName + '\'' +
            ", tel1='" + tel1 + '\'' +
            ", tel2='" + tel2 + '\'' +
            ", titleId='" + titleId + '\'' +
            ", lineToken='" + lineToken + '\'' +
            ", fromHr='" + fromHr + '\'' +
            ", orgId='" + orgId + '\'' +
            ", titleName='" + titleName + '\'' +
            ", userRole='" + userRole + '\'' +
            '}';
    }
}
