package tw.gov.pcc.eip.orderCar.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.groups.Default;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.framework.validation.RequiredString;

/**
 * 派車預約暨派車結果查詢作業Case
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip07w030Case implements Serializable {

	private static final long serialVersionUID = 1L;
	public interface Update extends Default {}

	private String applydateStart;//申請日期(起)
	
	private String applydateEnd;//申請日期(迄)
	
	private List<Eip07w030Case>dataList;
	private boolean check;
	private String applyid;
	private String apply_user;
	private String apply_dept;
	private String using_date;
	private String using_time_s;
	private String using_time_e;
	private String apply_memo;
	private String carprocess_status;
	private List<String>applyIdList;
	@RequiredString(label = "同意/不同意", groups = {Update.class})
	private String agree;
	private String hidden;//增加一個hidden欄位，不要一進來就foucus在日曆，讓日曆跳出來
	
}