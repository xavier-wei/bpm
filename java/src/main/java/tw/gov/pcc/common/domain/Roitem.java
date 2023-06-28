package tw.gov.pcc.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Query Log Domain 物件
 *
 * @author York
 */
@Data
@NoArgsConstructor
public class Roitem implements Serializable {
    private static final long serialVersionUID = 3678427538602217932L;
    /**
     * 請購/請修單號 (主鍵值)
     */
    private String itemid;

    /**
     * 品名序號 (主鍵值)
     */
    private String itemno;

    /**
     * 品名及規格
     */
    private String item;
    /**
     * 用途說明
     */
    private String desc_memo;
    /**
     * 數量
     */
    private String cnt;
    /**
     * 單位
     */
    private String unit;
    /**
     * 申請選項
     */
    private String apply_type;
    /**
     * 申請人員
     */
    private String apply_staff;
    /**
     * 申請日期
     */
    private String apply_date;
    /**
     * 暫存註記 Y是 N否
     */
    private String tempmk;
    /**
     * 建立人員
     */
    private String cre_user;
    /**
     * 建立日期時間
     */
    private String cre_datetime;
    /**
     * 修改人員
     */
    private String upd_user;

    /**
     * 修改日期時間
     */
    private String upd_datetime;

}
