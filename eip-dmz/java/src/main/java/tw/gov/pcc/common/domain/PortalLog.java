package tw.gov.pcc.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Portal Log Domain 物件
 *
 * @author Goston
 */
@Data
@NoArgsConstructor
public class PortalLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 編號
     */
    private String sno;
    /**
     * 紀錄時間
     */
    private String logDateTime;
    /**
     * 用戶代號
     */
    private String userId;
    /**
     * 用戶 IP 位址
     */
    private String userIP;
    /**
     * 用戶執行動作
     */
    private String userAction;
    /**
     * 應用系統代號
     */
    private String apCode;
    /**
     * 應用系統名稱
     */
    private String apName;
    /**
     * 應用系統功能代號
     */
    private String apFunctionCode;
    /**
     * 應用系統功能名稱
     */
    private String apFunctionName;
    /**
     * 應用系統網址
     */
    private String apUrl;
    /**
     * 說明
     */
    private String logDescript;
    /**
     * 系統時間
     */
    private Timestamp dateTime;
    /**
     * 登入代號(使用者+時間)
     */
    private String token;

}
