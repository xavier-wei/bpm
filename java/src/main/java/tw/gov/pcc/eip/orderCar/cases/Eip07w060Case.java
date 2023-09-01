package tw.gov.pcc.eip.orderCar.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.CaruseRec;

/**
 * 駕駛鍵入里程表作業Case
 * @Ivan
 */
@Data
@NoArgsConstructor
public class Eip07w060Case implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CarBase> bosscarList;
	private List<String> hourList;
	private List<String> minuteList;
	private List<CarBooking> thisMomthCarbookingList;//查詢畫面下拉式選單用
	
	private List<CarBooking> bosscarMonthlyList;
	private String keyinYm;//鍵入年月
	private String carType;//Y:首長專用車,N:非首長專用車
	private String applyid;//派車單號
	private String bosscarno;//carno1,carno2
	private String last3carno;//車牌末三碼
	
	private String usehms;
	private String usehme;
	private String startuseH;
	private String startuseM;
	private String enduseH;
	private String enduseM;
	
	private String hms;
	private String hme;
	private String startH;
	private String startM;
	private String endH;
	private String endM;
	private String road;//行駛路線
	private String milageStart;//出場公里數
	private String milageEnd;//回場公里數
	private String milage;//行駛公里數
	private String gasUsed;//耗油公里數
	private String btmk;//出差排程 Y/N
	
	private CarBooking carbooking;
	private CaruseRec caruserec;
	
	
}