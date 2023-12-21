package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.ApplyitemDao;

/**
 * 領物單作業
 * 
 * @author Ivy
 * @sincr 2023
 *
 */
@Table(ApplyitemDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Applyitem implements Serializable {

    private static final long serialVersionUID = 1L;
	
    @PkeyField("APPLYNO")
	@LogField("APPLYNO")
  	private String applyno;//領物單號(主鍵值)
    
    @LogField("SEQNO")
    @PkeyField("SEQNO")
  	private String seqno;//序號(主鍵值)
    
    @LogField("ITEMKIND")
    @PkeyField("ITEMKIND")
  	private String itemkind;//品名大類(主鍵值)
    
    @LogField("ITEMNO")
    @PkeyField("ITEMNO")
  	private String itemno;//品名(主鍵值)
    
    @LogField("APPLY_USER")
  	private String apply_user;//申請人
    
    @LogField("APPLY_DATE")
  	private String apply_date;//申請日期
    
    @LogField("APPLY_DEPT")
  	private String apply_dept;//申請單位
    
    @LogField("APPLY_MEMO")
  	private String apply_memo;//申請用途
    
    @LogField("WITHHOLD_CNT_B")
  	private Integer withhold_cnt_b;//申請前預扣數量
    
    @LogField("WITHHOLD_CNT_A")
  	private Integer withhold_cnt_a;//申請後預扣數量
    
    @LogField("BOOK_CNT_B")
  	private Integer book_cnt_b;//申請前帳面庫存數量
    
    @LogField("BOOK_CNT_A")
  	private Integer book_cnt_a;//申請後帳面數量
  	
    @LogField("APPLY_CNT")
  	private Integer apply_cnt;//申請數量 
  	
    @LogField("UNIT")
  	private String  unit;//申請單位
    
    @LogField("APPROVE_CNT")
  	private Integer approve_cnt;//核發數量
    
    @LogField("PROCESS_STATUS")
  	private String  process_status;//申請狀態=1已送出 2已審核
    
    @LogField("RECONFIRM_MK")
  	private String  reconfirm_mk;//複核註記Y同意N不同意
    
    @LogField("RECONFIRM_USER")
  	private String reconfirm_user;//複核
    
    @LogField("RECONFIRM_DATE")
  	private String reconfirm_date;//複核日期時間
    
    @LogField("CRE_USER")
  	private String cre_user;//建檔人
    
    @LogField("CRE_DATETIME")
  	private String cre_datetime;//建檔時間
    
    @LogField("UPD_USER")
  	private String upd_user;//更新人
    
    @LogField("UPD_DATETIME")
  	private String upd_datetime;//更新時間
    
    private String itemkind_nm;//品名大類中文+英文(eip08w050)
    private String itemno_nm;//品名：中文+英文(eip08w050)
}