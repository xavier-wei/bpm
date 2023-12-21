package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.framework.validation.ChineseDate;

/**
 * 秘書處進行領物單核發作業Case
 * 
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip08w040Case implements Serializable {
	public interface Query extends Default {}
	public interface Insert extends Default {}

	private static final long serialVersionUID = 1L;

	/**
	 * 申請日期(起)
	 */
	@ChineseDate(label = "申請日期(起)", groups = {Query.class})
	private String apply_dateStart;

	/**
	 * 申請日期(迄)
	 */
	@ChineseDate(label = "申請日期(迄)", groups = {Query.class})
	private String apply_dateEnd;
	
	@AssertTrue(message = "申請日期起日不得大於迄日", groups = {Query.class})
	private boolean isApply_dateStartAndApply_dateEnd() {
		if(!StringUtils.isAnyEmpty(this.apply_dateStart,this.apply_dateEnd)) {			
			if(Integer.valueOf(this.apply_dateStart) > Integer.valueOf(this.apply_dateEnd)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 資料列表
	 */
	private List<Eip08w040Case> dataList;
	
	/** 
	 * 申請單號  
	 */
	private String applyno;

	/** 
	 * 申請人 
	 */
	private String apply_user;
	
	/** 
	 * 申請人 
	 */
	private String apply_dept;
	
	/** 
	 * 申請日期
	 */
	private String apply_date;
	
	/** 
	 * 申請用途
	 */
	private String apply_memo;
	
	/**
	 * 資料列表
	 */
	private List<Eip08w040Case> detailList;
	
	/**
	 * 品名大類
	 */
	private String itemkind;
	
	/**
	 * 品名
	 */
	private String itemno;
	
	/**
	 * 申請數量
	 */
	private Integer apply_cnt;
	
	/**
	 * 單位名稱
	 */
	private String unit;
	
	/**
	 * 申請狀態
	 */
	private String process_status;
	
	/**
	 * 品名大類代碼+中文
	 */
    private String itemkind_nm;
    
	/**
	 * 品名代碼+中文
	 */
    private String itemno_nm;//品名代碼+中文
    
	/**
	 * 實際庫存數量
	 */
    private Integer final_cnt;
    
    private Integer provide_num;//核發數量
    private Integer book_cnt;
    private String seqno;
    private Integer withhold_cnt;
    private String hidden;//增加一個hidden欄位，不要一進來就foucus在日曆，讓日曆跳出來
}