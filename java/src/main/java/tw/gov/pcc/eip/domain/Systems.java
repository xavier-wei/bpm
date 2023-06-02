package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.SystemsDao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 應用系統資料
 *
 * @author swho
 */
@Table(SystemsDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Systems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系統代號
     */
    @PkeyField
    @LogField
    private String sys_id;

    /**
     * 應用系統名稱
     */
    @LogField
    private String sys_name;

    /**
     * URL
     */
    @LogField
    private String url;

    /**
     * 選單根項目 Root item ID
     */
    @LogField
    private String item_id;

    /**
     * 選單排序
     */
    @LogField
    private BigDecimal sort_order;

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
}