package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.ItemcodeuDao;

/**
* ITEMCODEU
*
* @author Ivy
*/
@Data
@NoArgsConstructor
@Table(ItemcodeuDao.TABLE_NAME)
public class Itemcodeu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ITEMKIND
     */
    @PkeyField
    @LogField
    private String itemkind;
    
    /**
     * ITEMNO
     */
    @PkeyField
    @LogField
    private String itemno;
    
    /**
     * U_DATE
     */
    @PkeyField
    @LogField
    private String u_date;
    
    /**
     * U_TIME
     */
    @PkeyField
    @LogField
    private String u_time;
    
    /**
     * U_USER
     */
    @PkeyField
    @LogField
    private String u_user;
    
    /**
     * ITEMNAME
     */
    @LogField
    private String itemname;
        
    /**
     * COCD
     */
    @LogField
    private String cocd;
        
    /**
     * PURCHASE_CNT
     */
    @LogField
    private BigDecimal purchase_cnt;
        
    /**
     * RETURN_CNT
     */
    @LogField
    private BigDecimal return_cnt;
        
    /**
     * FINAL_CNT
     */
    @LogField
    private BigDecimal final_cnt;
        
    /**
     * WITHHOLD_CNT
     */
    @LogField
    private BigDecimal withhold_cnt;
        
    /**
     * BOOK_CNT
     */
    @LogField
    private BigDecimal book_cnt;
        
    /**
     * CRE_USER
     */
    @LogField
    private String cre_user;
        
    /**
     * CRE_DATETIME
     */
    @LogField
    private String cre_datetime;
        
    /**
     * UPD_USER
     */
    @LogField
    private String upd_user;
        
    /**
     * UPD_DATETIME
     */
    @LogField
    private String upd_datetime;
        
}