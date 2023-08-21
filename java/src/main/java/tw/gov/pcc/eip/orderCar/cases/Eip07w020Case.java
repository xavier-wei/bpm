package tw.gov.pcc.eip.orderCar.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Eipcode;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @York
 */
@Data
@NoArgsConstructor
public class Eip07w020Case implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 新增資料
	 */
	private List<Eip07w020Case> insterList;

	/**
	 * 查詢結果資料
	 */
	private List<Eip07w020Case> resultList;

	/**
	 * 明細資料
	 */
	private List<CarBooking> detailsList;

    /**
     * 異動註記資料
     */
    private List<CarBooking> changeMkList;

	/**
	 * 車輛總類下拉選單
	 */
	private List<Eipcode> carTyList;

	/**
	 * 時
	 */
	private List<String> hourList;

	/**
	 * 分
	 */
	private List<String> minuteList;

	/**
	 * 預約單號
	 */
	private String applyId;

	/**
	 * 預約時間48位元
	 */
	private String using;

	/**
	 * 表單狀態
	 */
	private String processStaus;

	/**
	 * 表單狀態(中文)
	 */
	private String processStausNm;

	/**
	 * 處理種類 D:駕駛人資料 C:車籍資料
	 */
	private String processTy;

	/**
	 * 功能 A新增 Q查詢 D刪除 U修改
	 */
	private String workTy;



	/**
	 *申請人
	 */
	private String applyName;


	/**
	 *申請日期
	 */
	private String applyDate;

	/**
	 *申請單位
	 */
	private String applyUnit;

	/**
	 *用車事由
	 */
	private String useCarMemo;

	/**
	 *目的地
	 */
	private String destination;

	/**
	 *車輛總類
	 */
	private String carTy;

	/**
	 *人數
	 */
	private String number;

	/**
	 *用車日期
	 */
	private String useDate;

	/**
	 *用車(起)
	 */
	private String applyTimeS;

	/**
	 *用車(迄)
	 */
	private String applyTimeE;

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
	 * 創建人員
	 */
	private String creUser;

	/**
	 *創建時間
	 */
	private String creDatetime;

	/**
	 * 更新人員
	 */
	private String updUser;

	/**
	 *更新時間
	 */
	private String updDatetime;

	/**
	 *申請日期(起)
	 */
	private String applyDateStar;

	/**
	 *申請日期(迄)
	 */
	private String applyDateEnd;

	/**
	 *用車日期(起)
	 */
	private String useDateStar;

	/**
	 *用車日期(迄)
	 */
	private String useDateEnd;

	/**
	 *併單註記
	 */
	private String combine_mk;

	/**
	 *車子顏色
	 */
	private String carColor;

	/**
	 *車子顏色
	 */
	private String checkMk;

	/**
	 *異動原因
	 */
	@LogField
	private String rmMemo;

	/**
	 *標籤
	 */
	@LogField
	private String lable;


	public interface Query {
	}
}
