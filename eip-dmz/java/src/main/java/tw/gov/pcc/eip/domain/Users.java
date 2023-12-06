package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.UsersDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 使用者資料
 *
 * @author swho
 */
@Table(UsersDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 使用者代號
     */
    @PkeyField
    @LogField
    private String user_id;

    /**
     * 帳號是否有效
     */
    @LogField
    private String acnt_is_valid;

    /**
     * 建立時間
     */
    @LogField
    private LocalDateTime create_timestamp;

    /**
     * 建立人員
     */
    @LogField
    private String create_user_id;

    /**
     * 單位代碼
     */
    @LogField
    private String dept_id;

    /**
     * 電子郵件
     */
    @LogField
    private String email;

    /**
     * 員工編號
     */
    @LogField
    private String emp_id;

    /**
     * 上次LOGIN日期
     */
    @LogField
    private LocalDateTime last_login_date;

    /**
     * 上次LOGIN IP
     */
    @LogField
    private String last_login_ip;

    /**
     * 目錄伺服器登入帳號
     */
    @LogField
    private String ldap_id;

    /**
     * 修改人員
     */
    @LogField
    private String modify_user_id;

    /**
     * 修改時間
     */
    @LogField
    private LocalDateTime modify_timestamp;

    /**
     * 使用者姓名
     */
    @LogField
    private String user_name;

    /**
     * 連絡電話
     */
    @LogField
    private String tel1;

    /**
     * 分機號碼
     */
    @LogField
    private String tel2;

    /**
     * 職稱代碼
     */
    @LogField
    private String title_id;

    /**
     * LINE TOKEN
     */
    @LogField
    private String line_token;
    
    /**
     * from_hr
     */
    @LogField
    private String from_hr;

    /**
     * org_id
     */
    private String org_id;
    
    private boolean checkbox;
    private String dept_cname;//部門中文名稱
    
    

}