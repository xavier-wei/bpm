package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.framework.validation.ChineseDate;
import tw.gov.pcc.eip.framework.validation.RequiredString;

/**
 * 領物單申請複核作業Case
 * 
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip08w030Case implements Serializable {
	public interface Query extends Default {}
	public interface Update extends Default {}
	
	private static final long serialVersionUID = 1L;

	/**
	 * 申請日期(起)
	 */
	@ChineseDate(label = "申請日期(起)", groups = {Query.class})
	private String applydateStart;

	/**
	 * 申請日期(迄)
	 */
	@ChineseDate(label = "申請日期(迄)", groups = {Query.class})
	private String applydateEnd;
	
	@AssertTrue(message = "申請日期起日不得大於迄日", groups = {Query.class})
	private boolean isApplydateStartAndApplydateEnd() {
		if(StringUtils.isNotEmpty(this.applydateStart)&&StringUtils.isNotEmpty(this.applydateEnd)) {			
			if(Integer.valueOf(this.applydateStart) > Integer.valueOf(this.applydateEnd)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 資料列表
	 */
	private List<Eip08w030Case> dataList;
	
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
	private List<Eip08w030Case> detailList;
	
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
	 * 勾選
	 */
	private boolean check;
	
	/**
	 * 同意/不同意
	 */
	@RequiredString(label = "同意/不同意", groups = {Update.class})
	private String agree;
	private String unit;
	
	private List<String>applynoList;
	private String hidden;//解決一進來畫面就跳出日曆的問題
    

}