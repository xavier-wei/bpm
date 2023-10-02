package tw.gov.pcc.eip.report.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Eip07w040L_Vo implements Serializable {
	
	private static final long serialVersionUID = 1L;
    		
	private String apply_user;//申請人
	private String apply_dept;//申請單位
	private String apply_date;//申請日期
	private String apply_memo;//用車事由
	private String destination;//目的地
	private String apply_car_type;//車輛種類
	private String num_of_people;//人數
	private String using_date;//用車日期
	private String using_time_s;//用車時間起
	private String using_time_e;//用車時間迄
	private String combine_mk;//並單註記
	private String combine_applyid;//併單派車單號
	private String combine_reason;//並單原因
	private String name;//駕駛人姓名
	private String cellphone;//手機號碼
	private String carno1;//車牌車號1
	private String carno2;//車牌車號2
	private String cartype;//車輛種類
	private String carcolor;//顏色
	private String applyid;//派車單號
	private String approve_using_time_s;//核定派車區間起
	private String approve_using_time_e;//核定派車區間迄
	private String carprocess_status;//表單狀態
	
}