package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.User_rolesDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 使用者可使用的角色資料
 *
 * @author swho
 */
@Table(User_rolesDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class User_roles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 使用者代碼：USERS.USER_ID
     */
    @PkeyField
    @LogField
    private String user_id;

    /**
     * SYS_ID
     */
    @PkeyField
    @LogField
    private String sys_id;

    /**
     * DEPT_ID
     */
    @PkeyField
    @LogField
    private String dept_id;

    /**
     * 單位角色識別碼 單位角色主檔：ROLES.ROLE_ID
     */
    @PkeyField
    @LogField
    private String role_id;

    /**
     * CREATE_USER_ID
     */
    @LogField
    private String create_user_id;

    /**
     * CREATE_TIMESTAMP
     */
    @LogField
    private LocalDateTime create_timestamp;

}