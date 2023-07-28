package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.groups.Default;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.framework.validation.ChineseYearMonth;
import tw.gov.pcc.eip.framework.validation.RequiredString;

/**
 * 領物單紀錄查詢及列印作業Case
 * 
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip08w050Case implements Serializable {

	public interface Print extends Default {}

	private static final long serialVersionUID = 1L;

	/**
	 * 申請年月
	 */
	@RequiredString(label = "申請年月")
	@ChineseYearMonth(label = "申請年月")
	private String applyYearMonth;

	private List<Applyitem>unitList;
	
	private List<Applyitem>itemList;
}