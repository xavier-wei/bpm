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
import tw.gov.pcc.eip.domain.Car_booking_rec;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.validation.ChineseDate;
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
	
	public interface Query extends Default {}

	@RequiredString(label="派車狀態必須選擇", groups = {Query.class})
	private String carprocess_status;// 派車狀態
	@ChineseDate(label="申請日期(起)", groups = {Query.class})
	private String applydateStart;// 申請日期(起)
	
	@ChineseDate(label="申請日期(迄)", groups = {Query.class})
	private String applydateEnd;// 申請日期(迄)
	private String using_date;// 用車日期
	
	@ChineseDate(label="用車日期(起)", groups = {Query.class})
	private String using_time_s;// 用車時間起
	@ChineseDate(label="用車日期(迄)", groups = {Query.class})
	private String using_time_e;// 用車時間迄
	private String apply_memo;// 開車事由
	private List<CarBooking> notHandleList;
	private String applyid;

	@AssertTrue(message = "申請日期、用車日期請擇一輸入", groups = { Query.class })
	private boolean isApplydateStartAndapplydateEndAndUsing_time_sAndUsing_time_e() {
		if(StringUtils.isAllEmpty(this.applydateStart,this.applydateEnd,this.using_time_s,this.using_time_e)) {
			return false;
		}
		return true;
	}
	
	@AssertTrue(message = "申請起日必須填寫", groups = { Query.class })
	private boolean isApplydateStartAndapplydateEnd() {
		if(StringUtils.isEmpty(this.applydateStart) &&  StringUtils.isNotEmpty(this.applydateEnd)) {
			return false;
		}
		return true;
	}
	
	@AssertTrue(message = "申請日期起日不得大於迄日", groups = {Query.class})
	private boolean isApplydateStartAndApplydateEnd2() {
		if(StringUtils.isNotEmpty(this.applydateStart)&&StringUtils.isNotEmpty(this.applydateEnd)) {			
			if(Integer.valueOf(this.applydateStart) > Integer.valueOf(this.applydateEnd)) {
				return false;
			}
		}
		return true;
	}
	
	
	@AssertTrue(message = "用車起日必須填寫", groups = { Query.class })
	private boolean isUsing_time_sAndUsing_time_e() {
		if(StringUtils.isEmpty(this.using_time_s) &&  StringUtils.isNotEmpty(this.using_time_e)) {
			return false;
		}
		return true;
	}
	
	@AssertTrue(message = "用車日期起日不得大於迄日", groups = {Query.class})
	private boolean isUsing_time_sAndUsing_time_e2() {
		if(StringUtils.isNotEmpty(this.using_time_s)&&StringUtils.isNotEmpty(this.using_time_e)) {			
			if(Integer.valueOf(this.using_time_s) > Integer.valueOf(this.using_time_e)) {
				return false;
			}
		}
		return true;
	}
	
	
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
	
	private String using_time_sStrForTable2;// 用車時間起(顯示於query畫面的標題)
	private String using_time_eStrForTable2;// 用車時間迄(顯示於query畫面的標題)
	
	private String using_time_sStrForTable3;// 用車時間起(顯示於query畫面的標題)
	private String using_time_eStrForTable3;// 用車時間迄(顯示於query畫面的標題)

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
	
	private List<CarBooking>Reconfime_mk2List;
	private String usingTimeList; 
	private Car_booking_rec recData;

	/**
	 * 時
	 */
	private List<String> hourList;

	/**
	 * 分
	 */
	private List<String> minuteList;
	
	/**
	 *用車起:時
	 */
	private String starH;

	/**
	 *用車起:分
	 */
	private String starM;

	/**
	 *用車(迄):時
	 */
	private String endH;

	/**
	 *用車(迄):分
	 */
	private String endM;
	
	/**
	 * 新增資料
	 */
	private List<Eip07w040Case> insterList;
	
	private String approve_using;
	private String approve_using_time_s;
	private String approve_using_time_e;
	private String approve_using_timeStr;
	private String fillmk;//補單註記
}