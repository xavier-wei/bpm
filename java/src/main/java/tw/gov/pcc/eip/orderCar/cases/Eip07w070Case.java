package tw.gov.pcc.eip.orderCar.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.framework.validation.ChineseDate;
import tw.gov.pcc.eip.framework.validation.RequiredString;

/**
 * 派車記錄查詢及列印作業Case
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip07w070Case implements Serializable {

	private static final long serialVersionUID = 1L;
	public interface Print extends Default {}
	public interface Query extends Default {}

	@RequiredString(label = "用車日期起日")
	@ChineseDate(label = "用車日期起日")
	private String using_date_s;//用車日
	private String using_date_e;//用車日
	@RequiredString(label = "排序條件", groups = {Print.class})
	private String orderCondition;//排序條件
	private String carno1;//車牌號碼
	private String carno2;//車牌號碼
	private List<Eip07w070Case>dataList;
    private String using_time_s;
    private String using_time_e;
    private String fillmk;
    
    
	@AssertTrue(message = "用車日期起日不得大於迄日", groups = {Query.class})
	private boolean isUusing_date_sAndUusing_date_e() {
		if(!StringUtils.isAnyEmpty(this.using_date_s,this.using_date_e)) {			
			if(Integer.valueOf(this.using_date_s) > Integer.valueOf(this.using_date_e)) {
				return false;
			}
		}
		return true;
	}
	
	private List<CarBase>carnoList;
	private String hidden;//增加一個hidden欄位，不要一進來就foucus在日曆，讓日曆跳出來
	
	//以下為查詢及報表畫面欄位
	private String usingdate;//用車日期
	private String approve_using_time_s;//核定用車時間起
	private String approve_using_time_e;//核定用車時間迄
	private String carno;//車牌號碼
	private String name;//駕駛人姓名
	private String apply_memo;//用車事由
	private String destination;//目的地
	private String applyid;//派車單號
}