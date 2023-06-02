package tw.gov.pcc.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Query Log Domain 物件
 *
 * @author Goston
 */
@Data
@NoArgsConstructor
public class Mmquerylog implements Serializable {
    private static final long serialVersionUID = 3678427538602217932L;
    /**
     * TABLE 名稱
     */
    private String tableName;
    /**
     * 查詢時間
     */
    private String qyTime;
    /**
     * 程式名稱
     */
    private String pgmName;
    /**
     * 程式代碼
     */
    private String pgmCode;
    /**
     * 員工部門代號
     */
    private String deptId;
    /**
     * 員工編號
     */
    private String queryMan;
    /**
     * 終端機位址
     */
    private String termEd;
    /**
     * 查詢代號
     */
    private String qyCode;
    /**
     * 查詢條件
     */
    private String qyCondition;
    /**
     * 證號
     */
    private String idNo;
    /**
     * 備註
     */
    private String memo;
    /**
     * 編號 (MMACCESSLG.SNO)
     */
    private String sno;

}
