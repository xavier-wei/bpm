package tw.gov.pcc.eip.orderCar.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.eip.domain.CaruseRec;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.GasRec;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @York
 */
@Data
@NoArgsConstructor
public class Eip07w010Case implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 代理人下拉選單
	 */
	private List<Eip07w010Case> tempStaffList;
	/**
	 * 職稱下拉選單
	 */
	private List<Eipcode> titleList;

	private List<Eip07w010Case> eip07w010QueryDataList;

	private List<Eip07w010Case> eip07w010CarDataList;//車輛基本資料

	private List<Eip07w010Case> oilList;//油料紀錄

	private List<Eip07w010Case> mileageList;//里程紀錄

	/**
	 * 處理種類 D:駕駛人資料 C:車籍資料
	 */
	private String processTy;

	/**
	 * 功能 A新增 Q查詢 D刪除 U修改
	 */
	private String workTy;

	/**
	 *主鍵值
	 */
	private String driverid;

	/**
	 *駕駛人姓名
	 */
	private String name;


	/**
	 *員工編號
	 */
	private String staffno;

	/**
	 *身分證號(駕照證號)
	 */
	private String id  ;



	/**
	 *出生日期
	 */
	private String brdte;

	/**
	 *職稱(1:駕駛)
	 */
	private String title;

	/**
	 *職稱(1:駕駛)
	 */
	private String titleNm;

	/**
	 *手機號碼
	 */
	private String cellphone;

	/**
	 *電話
	 */
	private String phone;

	/**
	 *在職註記: y:是/n:否
	 */
	private String stillWork;

	/**
	 *在職註記: y:是/n:否
	 */
	private String stillWorkNm;

	/**
	 *到職日期
	 */
	private String startworkDate;

	/**
	 *離職日期
	 */
	private String endworkDate;

	/**
	 *駕照到期日
	 */
	private String licenceExpireDate;

	/**
	 *駕照種類 1.小客車2.小客車
	 */
	private String  licenceCarType;

	/**
	 *車牌號碼(預定保管車號)
	 */
	private String carno1;

	/**
	 *車牌號碼(預定保管車號)
	 */
	private String carno2;

	/**
	 *代理人
	 */
	private String tempStaff;

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

//車輛基本資料檔

	/**
	 *財產編號
	 */
	private String owned;

	/**
	 *車輛種類: 1:4人座2:7人座
	 */
	private String carType;

	/**
	 *車輛種類: 1:4人座2:7人座_中文
	 */
	private String carTypeNm;

	/**
	 *車輛廠牌
	 */
	private String carSource;

	/**
	 *購置年份
	 */
	private String carYear;

	/**
	 *顏色
	 */
	private String carColor;

	/**
	 *排放量cc
	 */
	private String ccSize;

	/**
	 *首長專用車: Y是/N否
	 */
	private String bossMk;

	/**
	 *首長專用車: Y是/N否_中文
	 */
	private String bossMkNm;

	/**
	 *首長
	 */
	private String bossName;

	/**
	 *車輛狀態1可使用2不可使用-首長3維修中4報廢
	 */
	private String carStatus;

	/**
	 *保險公司
	 */
	private String insuranceCompany;

	/**
	 *保險期間(起)
	 */
	private String insuranceStart;

	/**
	 *保險期間(迄)
	 */
	private String insuranceEnd;

//車輛加油紀錄檔

	/**
	 *加油日期
	 */
	private String fuelDate;

	/**
	 *加油時間
	 */
	private String fuelTime;

	/**
	 *加油金額
	 */
	private String gasMoney;

	/**
	 *加油量
	 */
	private String gasAmount;

//車輛用車紀錄檔

	/**
	 *派車單號
	 */
	private String applyId;

	/**
	 *用車日期
	 */
	private String useDate;

	/**
	 *用車時間起
	 */
	private String useTimeS;

	/**
	 *用車時間迄
	 */
	private String useTimeE;

	/**
	 *出場里程數
	 */
	private String milageStart;

	/**
	 *回場里程數
	 */
	private String milageEnd;

	/**
	 *行駛公里數
	 */
	private String milage;

	/**
	 *耗油量
	 */
	private String gasUsed;





	/**
	 *點選明細查詢之driverid
	 */
	private String driveridDetail;


	public interface Query {
	}
}
