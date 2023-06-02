package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.RolesDao;

/**
 * @author 
 */
@Table(RolesDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @PkeyField("SYS_ID")
    @LogField("SYS_ID")
    private String sys_id; 
    @PkeyField("DEPT_ID")
    @LogField("DEPT_ID")
    private String dept_id; 
    @PkeyField("ROLE_ID")
    @LogField("ROLE_ID")
    private String role_id; 
    @LogField("ROLE_DESC")
    private String role_desc; 
    @LogField("CREATE_USER_ID")
    private String create_user_id; 
    @LogField("CREATE_TIMESTAMP")
    private LocalDateTime create_timestamp; 
    @LogField("MODIFY_USER_ID")
    private String modify_user_id; 
    @LogField("MODIFY_TIMESTAMP")
    private LocalDateTime modify_timestamp; 

}
