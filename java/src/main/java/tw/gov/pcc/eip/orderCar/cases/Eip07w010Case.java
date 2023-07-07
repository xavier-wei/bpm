package tw.gov.pcc.eip.orderCar.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;

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




	public interface Query {
	}
}
