package tw.gov.pcc.eip.apply.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import tw.gov.pcc.eip.framework.validation.NotEmpty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @York
 */
@Data
@NoArgsConstructor
public class Eip08w060Case implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Eip08w060Case> eip08w060CaseList;

	private List<Eip08w060Case> eip08w060QuaryList;

	private List<Eip08w060Case> distinctItemIdList;



	/**
	 * 請購/請修單號 (主鍵值)
	 */
	private String itemId;

	/**
	 *申請人(ID)
	 */
	private String userName;

	/**
	 * 請購/請修單號 (主鍵值)
	 */
	private String itemNo;
	/**
	 * 品名及規格
	 */
	private String item;
	/**
	 * 用途說明
	 */

	private String desc_memo;
	/**
	 * 數量
	 */

	private String cnt;
	/**
	 * 單位
	 */

	private String unit;

	/**
	 * 申請人員
	 */
	private String apply_staff;
	/**
	 * 申請日期
	 */
	private String apply_date;

	/**
	 * 建立人員
	 */
	private String cre_user;
	/**
	 * 建立日期時間
	 */
	private String cre_datetime;
	/**
	 * 修改人員
	 */
	private String upd_user;

	/**
	 * 修改日期時間
	 */
	private String upd_datetime;

	/**
	 * 申請種類(中文)
	 */
	private String applyTpNm;

	/**
	 * 暫存
	 */
	private String save;

	/**
	 * 申請日期
	 */
	private String applyDate;

	/**
	 * 使用者
	 */
	private String user;

	/**
	 * itemID明細
	 */
	private String selectItemID;

	/**
	 * 處理類型 新:A 刪:D 修:U 查:Q
	 */
	private String processTy;


	public interface Query {
	}
}
