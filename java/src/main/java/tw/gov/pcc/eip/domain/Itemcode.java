package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.ItemcodeDao;

/**
 * 
 *
 * @author ivan
 */
@Table(ItemcodeDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Itemcode implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * itemkind
     */
    @LogField
    @PkeyField
    private String itemkind; 
    
    /**
     * itemno
     */
    @LogField
    private String itemno; 
    
    /**
     * itemname
     */
    @LogField
    private String itemname; 
    
    /**
     * purchase_cnt
     */
    @LogField
    private Integer purchase_cnt;  
    
    /**
     * return_cnt
     */
    @LogField
    private Integer return_cnt;  
    
    /**
     * final_cnt
     */
    @LogField
    private Integer final_cnt;  
    
    /**
     * withhold_cnt
     */
    @LogField
    private Integer withhold_cnt;
    
    /**
     * book_cnt
     */
    @LogField
    private Integer book_cnt;
    
    /**
     * cre_user
     */
    @LogField
    private String cre_user;

    /**
     * cre_datetime
     */
    @LogField
    private String cre_datetime;

    /**
     * upd_user
     */
    @LogField
    private String upd_user;

    /**
     * upd_Datetime
     */
    @LogField
    private String upd_datetime;
    

    private boolean checkbox;//list checkbox  
    private Integer p_cnt;//11x進貨數量
    private Integer r_cnt;//11x退貨數量
    
    private String itemkind_nm;//品名大類代碼+中文(eip08w040)   
    private String itemno_nm;//品名代碼+中文(eip08w040)
    private Integer apply_cnt;//申請數量(eip08w040)
    private String apply_user;//(eip08w040)
    private String apply_dept;//(eip08w040)
    private String apply_date;//(eip08w040)
    private String apply_memo;//(eip08w040)
    private String seqno;//(eip08w040)
}