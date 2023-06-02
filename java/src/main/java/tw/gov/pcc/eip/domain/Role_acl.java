package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.Role_aclDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 單位角色權限資料
 *
 * @author swho
 */
@Table(Role_aclDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Role_acl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系統代碼：SYSTEMS.SYS_ID
     */
    @PkeyField
    @LogField
    private String sys_id;

    /**
     * 單位角色代碼：ROLES.ROLE_ID
     */
    @PkeyField
    @LogField
    private String role_id;

    /**
     * 單位代碼：DEPTS.DEPT_ID
     */
    @PkeyField
    @LogField
    private String dept_id;

    /**
     * 系統項目代碼：ITEMS.ITEM_ID
     */
    @PkeyField
    @LogField
    private String item_id;

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