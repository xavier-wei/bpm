package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.UsersDao;

/**
 * 跨部門審核
 * @since 2023/11/29
 * @author Ivy
 * 提供部分使用者可以跨部門審核用
 */
@Table(UsersDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class User_auth_dept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 使用者代號
     */
    @PkeyField
    @LogField
    private String user_id;


    /**
     * 部門代號
     */
    @PkeyField
    @LogField
    private String dept_id;

}