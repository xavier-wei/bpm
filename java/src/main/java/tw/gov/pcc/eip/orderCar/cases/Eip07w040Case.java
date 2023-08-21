package tw.gov.pcc.eip.orderCar.cases;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.validation.RequiredString;
import tw.gov.pcc.eip.report.vo.Eip07w040L_Vo;

/**
 * 秘書處進行派車作業Case
 * 
 * @Ivy
 */
@Data
@NoArgsConstructor
public class Eip07w040Case implements Serializable {

	private static final long serialVersionUID = 1L;

	public interface Print extends Default {}

	public interface Update extends Default {}

	private String carprocess_status;// 派車狀態
	private String applydateStart;// 申請日期(起)
	private String applydateEnd;// 申請日期(迄)
	private String using_date;// 用車日期
	private String using_time_s;// 用車時間起
	private String using_time_e;// 用車時間迄
	private String apply_memo;// 開車事由
	private List<CarBooking> notHandleList;
	private String applyid;

	private boolean check;
	private String apply_user;// 申請人
	private String apply_dept;// 申請單位
	private String print_mk;// 以列印派車單註記
	private List<CarBooking> handledList;
	private String apply_car_type;// 車輛種類
	private String destination;// 目的地
	private Integer num_of_people;// 人數
	private String print;

	@RequiredString(label = "派車車號", groups = { Update.class })
	private String carno;
	private List<CarBase> carnoList;
	private List<CarBooking> carBookingList;
	private CarBooking carBookingDetailData;
	private boolean showEmptyStr;
	private String using;// 使用時間
	private String timeMK;// 時間檢查

	@RequiredString(label = "是否併單", groups = { Update.class })
	private String merge;// 合併
	private String mergeReason;// 併單原因
	private List<Eipcode> carprostsList;// 派單結果
	private boolean showButton;// 是否顯示派單結果按鈕群
	private List<String> applyids;
	private List<Eip07w040L_Vo> reportList;
	private String reprintApplyid;// 補印編號
	
	private String using_time_sStr;// 用車時間起(顯示於query畫面的標題)
	private String using_time_eStr;// 用車時間迄(顯示於query畫面的標題)

	@AssertTrue(message = "申請日期起日不得大於迄日", groups = { Print.class })
	private boolean isApplydateStartAndApplydateEnd() {
		if (StringUtils.isNotEmpty(this.applydateStart) && StringUtils.isNotEmpty(this.applydateEnd)) {
			if (Integer.valueOf(this.applydateStart) > Integer.valueOf(this.applydateEnd)) {
				return false;
			}
		}
		return true;
	}

	@AssertTrue(message = "併單原因未填寫", groups = { Update.class })
	private boolean isAgreeAndMergeReason() {
		if ("Y".equals(this.merge) && StringUtils.isEmpty(this.mergeReason)) {
			return false;
		}
		return true;
	}

	private String mergeApplyid;
	@RequiredString(label = "派車結果選項", groups = { Update.class })
	private String status;
	
}