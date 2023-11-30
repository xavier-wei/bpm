package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.groups.Default;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Applyitem;
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

	public interface Query extends Default {}
	public interface Apply extends Default {}
	public interface Insert extends Default {}
	private static final long serialVersionUID = 1L;

	/**
	 * 申請人
	 */
	@RequiredString(label = "申請人" , groups = { Query.class })
	private String apply_user;

	/**
	 * 申請日期(起)
	 */
	private String applydateStart;
	
	/**
	 * 申請日期(迄)
	 */
	private String applydateEnd;
	
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
	 * 品名(主鍵值)
	 */
	private String keepitemno;//保留itemno(防止return時資料流失)
	
	/**
	 * 品名(中文)
	 */
	private String itemname;

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
	

	/**
	 * 
	 */
	private Integer num;

	
	/**
	 * 查詢 List
	 */
	private List<Applyitem>applyitemList;
	
	/**
	 * 領物單號
	 */
	private String applyno;
	
	/**
	 * 查詢明細 List
	 */
	private List<Applyitem>detailList;
	
	private String oriApply_user;//用來保留畫面上原始userid
	private String oriApply_dept;//用來保留畫面上原始dept
	private String processStatus;
	private String SelectType;
	private String hidden;//增加一個hidden欄位，不要一進來就foucus在日曆，讓日曆跳出來
	
	private String itemnoStr;//用於查詢畫面,查詢品名用
	
}