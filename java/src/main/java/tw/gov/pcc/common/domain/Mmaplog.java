package tw.gov.pcc.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * AP Log Domain 物件
 *
 * @author Goston
 */
@Data
@NoArgsConstructor
public class Mmaplog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 異動 TABLE 名稱
     */
    private String tableName;
    /**
     * 異動 TABLE 主鍵
     */
    private String pkField;
    /**
     * 異動時間
     */
    private Timestamp chgTime;
    /**
     * 程式名稱
     */
    private String pgmName;
    /**
     * 程式代碼
     */
    private String pgmCode;
    /**
     * 異動員工部門代號
     */
    private String deptId;
    /**
     * 異動員工編號
     */
    private String modifyMan;
    /**
     * 終端機位址
     */
    private String termEd;
    /**
     * 異動代號
     */
    private String chgCode;
    /**
     * 異動欄位
     */
    private String field;
    /**
     * 改前內容
     */
    private String befImg;
    /**
     * 改後內容
     */
    private String aftImg;
    /**
     * 證號
     */
    private String idNo;
    /**
     * 備註
     */
    private String memo;
    /**
     * 編號
     */
    private String sno;

}
