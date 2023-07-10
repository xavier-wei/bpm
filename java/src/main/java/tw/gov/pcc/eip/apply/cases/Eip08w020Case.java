package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.validation.RequiredString;

/**
 * 領物單申請作業Case
 * 
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip08w020Case implements Serializable {

	public interface Insert extends Default {
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 申請人
	 */
	private String apply_user;

	/**
	 * 申請日期
	 */
	private String apply_date;

	/**
	 * 申請單位
	 */
	private String apply_dept;

	/**
	 * 申請用途
	 */
	@RequiredString(label = "申請用途", message = "「申請用途」必須輸入", groups = { Insert.class })
	private String apply_memo;

	/**
	 * 庫存數量(此庫存數量指的是已申請預扣後(帳面)庫存數量)
	 */
	private Integer withhold_cnt;
	
	/**
	 * 庫存數量
	 */
	private Integer book_cnt;

	/**
	 * 品名大類
	 */
	private String itemkind;

	/**
	 * 品名(主鍵值)
	 */
	private String itemno;

	/**
	 * 申請數量
	 */
	private Integer apply_cnt;

	/**
	 * 品名大類 List
	 */
	private List<Itemcode> itemList;

	/**
	 * 品名 List
	 */
	private List<Itemcode> itemnoList;

	/**
	 * 單位
	 */
	private String unit;

	/**
	 * 品名 List
	 */
	private List<Eip08w020Case> allData;

	private Integer num;

	@AssertTrue(message = "申請數量不得大於庫存數量", groups = {Insert.class})
	private boolean isAllData() {
		for(Eip08w020Case data : this.allData) {
			
			if(data.getApply_cnt()!=null && data.getWithhold_cnt()!=null && data.getApply_cnt() > data.getWithhold_cnt()) {
				return false;
			}
		}
		return true;
	}
}