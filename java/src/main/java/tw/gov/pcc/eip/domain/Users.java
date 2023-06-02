package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.UsersDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

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
     * 帳號有效到期日
     */
    @LogField
    private String acnt_expired_date;
        
    /**
     * 帳號是否有效
     */
    @LogField
    private String acnt_is_valid;
        
    /**
     * LOGIN次數
     */
    @LogField
    private BigDecimal count;
        
    /**
     * CREATE_TIMESTAMP
     */
    @LogField
    private LocalDateTime create_timestamp;
        
    /**
     * CREATE_USER_ID
     */
    @LogField
    private String create_user_id;
        
    /**
     * 單位代碼：DEPTS.DEPT_ID
     */
    @LogField
    private String dept_id;
        
    /**
     * REPORT_TO_USER_ID
     */
    @LogField
    private String report_to_user_id;
        
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
     * ID No.
     */
    @LogField
    private String idno;
        
    /**
     * 是否為單位管理員
     */
    @LogField
    private String is_admin;
        
    /**
     * 是否使用憑證
     */
    @LogField
    private String is_use_cert;
        
    /**
     * USER_KIND
     */
    @LogField
    private String user_kind;
        
    /**
     * 上次LOGIN日期
     */
    @LogField
    private String last_login_date;
        
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
     * LEAVING_DATE
     */
    @LogField
    private LocalDateTime leaving_date;
        
    /**
     * MODIFY_USER_ID
     */
    @LogField
    private String modify_user_id;
        
    /**
     * MODIFY_TIMESTAMP
     */
    @LogField
    private LocalDateTime modify_timestamp;
        
    /**
     * 使用者姓名
     */
    @LogField
    private String user_name;
        
    /**
     * 原使用者代碼
     */
    @LogField
    private String old_user_id;
        
    /**
     * 密碼有效到日期
     */
    @LogField
    private String pw_expired_date;
        
    /**
     * 密碼政策
     */
    @LogField
    private String pw_policy;
        
    /**
     * 電話一
     */
    @LogField
    private String tel1;
        
    /**
     * 電話二
     */
    @LogField
    private String tel2;
        
    /**
     * 職稱代碼：TITLES.TITLE_ID
     */
    @LogField
    private String title_id;
    
    
    private boolean checkbox;
        
}