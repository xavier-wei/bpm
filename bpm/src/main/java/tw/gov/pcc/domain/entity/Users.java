package tw.gov.pcc.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Data
@Table(schema = "dbo",name = "USERS")
public class Users {

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


}
