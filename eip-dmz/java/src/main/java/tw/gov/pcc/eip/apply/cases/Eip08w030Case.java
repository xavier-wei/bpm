package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.groups.Default;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 領物單申請複核作業Case
 * 
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip08w030Case implements Serializable {
	public interface Insert extends Default {}

	private static final long serialVersionUID = 1L;

	/**
	 * 申請日期(起)
	 */
	private String apply_dateStart;

	/**
	 * 申請日期(迄)
	 */
	private String apply_dateEnd;
	
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
	private String agree;
	
	private String unit;
	
	private List<String>applynoList;

}