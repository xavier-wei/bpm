package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.ItemsDao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 選單項目資料
 *
 * @author swho
 */
@Table(ItemsDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系統項目代碼
     */
    @PkeyField
    @LogField
    private String item_id;

    /**
     * 排序序號
     */
    @LogField
    private BigDecimal sort_order;

    /**
     * 父階系統項目代碼
     */
    @LogField
    private String item_id_p;

    /**
     * 項目名稱
     */
    @LogField
    private String item_name;

    /**
     * 連結位址
     */
    @LogField
    private String hyperlink;

    /**
     * 子網頁清單
     */
    @LogField
    private String sub_link;

    /**
     * 是否顯示
     */
    @LogField
    private String disable;

    /**
     * 是否檢查憑證
     */
    @LogField
    private String check_cert;

    /**
     * 登入方式
     */
    @LogField
    private String login_type;

    /**
     * 保護頁面
     */
    @LogField
    private String page_protect;

    /**
     * 是否開啟新視窗
     */
    @LogField
    private String is_openwindow;

    /**
     * ITEM_NO
     */
    @LogField
    private BigDecimal item_no;

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