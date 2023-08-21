package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.dao.CarBaseDao;
import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.CaruseRecDao;
import tw.gov.pcc.eip.dao.DriverBaseDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.CaruseRec;
import tw.gov.pcc.eip.domain.DriverBase;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w060Case;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 派車預約暨派車結果查詢作業Service
 *
 * @author ivan
 */
@Service
public class Eip07w060Service {

	@Autowired
	UserBean userData;
	@Autowired
	private EipcodeDao eipcodeDao;
	@Autowired
	private CarBookingDao carBookingDao;
	@Autowired
	private CarBaseDao carBaseDao;
	@Autowired
	private DriverBaseDao driverBaseDao;
	@Autowired
	private CaruseRecDao caruseRecDao;
	
	public void initCase(Eip07w060Case caseData) {
		Eip07w060Case newCase = new Eip07w060Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setBosscarList(getBosscarnoList());
		caseData.setCarType("N");
		caseData.setBtmk("N");
		setTimelist(caseData);
		caseData.setThisMomthCarbookingList(carBookingDao.selectOneMonthApplyidAndStatusIn3467F(DateUtility.getNowWestYearMonth()));
	}
	
	public List<CarBase> getBosscarnoList(){
		return carBaseDao.selectBosscarList();
	}
	
	/**
	 * 查詢資料驗證
	 *
	 * @param caseData
	 * @param bindingResult
	 */
	public void validQuery(Eip07w060Case caseData, BindingResult bindingResult) {
		if(StringUtils.isBlank(caseData.getCarType())) {
			bindingResult.reject(null, "首長專用車為必填欄位");
		}
		
		//為首長車
		if("Y".equals(caseData.getCarType())) {
			if(StringUtils.isBlank(caseData.getBosscarno())) {
				bindingResult.reject(null, "車牌號碼為必填欄位");
			}
			if(StringUtils.isBlank(caseData.getBtmk())) {
				bindingResult.reject(null, "出差排程為必填欄位");
			}
			
		}
		//不為首長車
		else if ("N".equals(caseData.getCarType())) {
			if(StringUtils.isBlank(caseData.getApplyid())) {
				bindingResult.reject(null, "派車單號為必填欄位");
			}
			CarBooking datars = findCarbookingByApplyid(caseData.getApplyid());
			if(datars == null) {
				bindingResult.reject(null, "無此派車單號資料");
			}
		}
	}
	
	/**
	 * 更新資料驗證
	 *
	 * @param caseData
	 * @param bindingResult
	 */
	public void validUpdate(Eip07w060Case caseData, BindingResult bindingResult) {	
		//為首長車
		if("Y".equals(caseData.getCarType())) {
			if(StringUtils.isBlank(caseData.getCarbooking().getApply_memo())) {
				bindingResult.reject(null, "用車事由為必填欄位");
			}
			if(StringUtils.isBlank(caseData.getCarbooking().getUsing_date())) {
				bindingResult.reject(null, "用車日期為必填欄位");
			}
			if(StringUtils.isBlank(caseData.getStartuseH()) || StringUtils.isBlank(caseData.getStartuseM())
					|| StringUtils.isBlank(caseData.getEnduseH()) || StringUtils.isBlank(caseData.getEnduseM())) {
				bindingResult.reject(null, "用車時間起訖為必填欄位");
			}else {
				int start = Integer.parseInt(caseData.getStartuseH()+caseData.getStartuseM());
				int end = Integer.parseInt(caseData.getEnduseH()+caseData.getEnduseM());
				if(end-start <= 0) {
					bindingResult.reject(null, "用車時間起時不可晚於迄時");
				}
			}
		}
		
		if(StringUtils.isBlank(caseData.getRoad())) {
			bindingResult.reject(null, "行駛路線為必填欄位");
		}
		if(StringUtils.isBlank(caseData.getMilageStart())) {
			bindingResult.reject(null, "出場公里數為必填欄位");
		}
		if(StringUtils.isBlank(caseData.getMilageEnd())) {
			bindingResult.reject(null, "回場公里數為必填欄位");
		}
		if(StringUtils.isBlank(caseData.getMilage())) {
			bindingResult.reject(null, "行駛公里數為必填欄位");
		}
		if(StringUtils.isBlank(caseData.getGasUsed())) {
			bindingResult.reject(null, "耗油公里數為必填欄位");
		}
		if(StringUtils.isBlank(caseData.getStartH()) || StringUtils.isBlank(caseData.getStartM())
				|| StringUtils.isBlank(caseData.getEndH()) || StringUtils.isBlank(caseData.getEndM())) {
			bindingResult.reject(null, "開出時間及到達時間為必填欄位");
		}else {
			int start = Integer.parseInt(caseData.getStartH()+caseData.getStartM());
			int end = Integer.parseInt(caseData.getEndH()+caseData.getEndM());
			if(end-start <= 0) {
				bindingResult.reject(null, "開出時間不可晚於到達時間");
			}
		}
	}


	/**
	 * 依派車單號查詢派車單資料
	 *
	 * @param caseData
	 */
	public void setData(Eip07w060Case caseData) throws Exception {
		if(StringUtils.equals("Y", caseData.getCarType())) {
			settingBossCarData(caseData);
		}else if(StringUtils.equals("N", caseData.getCarType())) {
			settingNotBossCarData(caseData);
		}
	}
	
	public void updData(Eip07w060Case caseData) {
		if(StringUtils.equals("Y", caseData.getCarType())) {
			updBossCarRecordData(caseData);
		}else if(StringUtils.equals("N", caseData.getCarType())) {
			updNotBossCarRecordData(caseData);
		}
	}
	
	private void updBossCarRecordData(Eip07w060Case caseData) {
		CarBooking carbooking = caseData.getCarbooking();
		CaruseRec caruserec = new CaruseRec();
		
		caruserec.setCarno1(carbooking.getCarno1());
		caruserec.setCarno2(carbooking.getCarno2());
		caruserec.setApplyid(carbooking.getApplyid());
		caruserec.setUse_date(DateUtility.changeDateType(carbooking.getUsing_date()));
		caruserec.setUse_time_s(caseData.getStartuseH()+caseData.getStartuseM());
		caruserec.setUse_time_e(caseData.getEnduseH()+caseData.getEnduseM());
		caruserec.setMilage_start(caseData.getMilageStart());
		caruserec.setMilage_end(caseData.getMilageEnd());
		caruserec.setMilage(caseData.getMilage());
		caruserec.setGas_used(caseData.getGasUsed());
		caruserec.setDriver_time_s(caseData.getStartH()+caseData.getStartM());
		caruserec.setDriver_time_e(caseData.getEndH()+caseData.getEndM());
		caruserec.setDrive_road(caseData.getRoad());
		caruserec.setCre_user(userData.getUserId());
		caruserec.setCre_datetime(DateUtility.getNowWestDateTime(true));
		caruseRecDao.insert(caruserec);
	}
	
	private void updNotBossCarRecordData(Eip07w060Case caseData) {
		String nowdtString= DateUtility.getNowWestDateTime(true);
		CarBooking carbooking = caseData.getCarbooking();
		CaruseRec caruserec = new CaruseRec();
		
		//1.insert caruser_rec
		caruserec.setCarno1(carbooking.getCarno1());
		caruserec.setCarno2(carbooking.getCarno2());
		caruserec.setApplyid(carbooking.getApplyid());
		caruserec.setUse_date(carbooking.getUsing_date());
		caruserec.setUse_time_s(carbooking.getUsing_time_s());
		caruserec.setUse_time_e(carbooking.getUsing_time_e());
		caruserec.setMilage_start(caseData.getMilageStart());
		caruserec.setMilage_end(caseData.getMilageEnd());
		caruserec.setMilage(caseData.getMilage());
		caruserec.setGas_used(caseData.getGasUsed());
		caruserec.setDriver_time_s(caseData.getStartH()+caseData.getStartM());
		caruserec.setDriver_time_e(caseData.getEndH()+caseData.getEndM());
		caruserec.setDrive_road(caseData.getRoad());
		caruserec.setCre_user(userData.getUserId());
		caruserec.setCre_datetime(nowdtString);
		caruseRecDao.insert(caruserec);
		
		//2.update carbooking
		CarBooking updcb = new CarBooking();
		BeanUtility.copyProperties(updcb, carbooking);
		updcb.setCarprocess_status("F");
		updcb.setUpd_user(userData.getUserId());
		updcb.setUpd_datetime(nowdtString);
		carBookingDao.updateByKey(updcb);
	}
	
	private CarBooking findCarbookingByApplyid(String applyid){
		return carBookingDao.selectByApplyidAndStatusIn3467F(applyid);
	}
	
	//設定首長專用車畫面資料
	private void settingBossCarData(Eip07w060Case caseData) {
		String[] carnos= caseData.getBosscarno().split(",");
		String carnoString = carnos[0]+carnos[1];
		String carnoLast3 = "";
		if(carnoString.length() >3) {
			carnoLast3 = carnoString.substring(carnoString.length()-3,carnoString.length());
		}else {
			carnoLast3 = carnoString;
		}
		
		
		CarBooking insertbooking = new CarBooking();
		if(StringUtils.equals("N", caseData.getBtmk())) {
			insertbooking.setApplyid("DC"+DateUtility.getNowWestYearMonth()+"00000");
			insertbooking.setUsing_date(DateUtility.getNowChineseYearMonth()+DateUtility.lastDay(DateUtility.getNowWestDate()));	
			caseData.setRoad("台灣地區");
			caseData.setStartH("08");
			caseData.setStartM("00");
			caseData.setEndH("18");
			caseData.setEndM("00");
			caseData.setStartuseH("08");
			caseData.setStartuseM("00");
			caseData.setEnduseH("18");
			caseData.setEnduseM("00");
		}
		insertbooking.setCarno1(carnos[0]);
		insertbooking.setCarno2(carnos[1]);
		insertbooking.setApply_memo("接送長官");
		
		insertbooking.setDestination("台灣地區");
		insertbooking.setCarprocess_status("F");
		insertbooking.setName(findDriveName(carnos[0], carnos[1]));
		caseData.setCarbooking(insertbooking);
		caseData.setLast3carno(carnoLast3);
	}
	
	//設定非首長專用車畫面資料
	private void settingNotBossCarData(Eip07w060Case caseData) {
		CarBooking carbooking = findCarbookingByApplyid(caseData.getApplyid());
		caseData.setCarbooking(carbooking);
		
		if(StringUtils.equals("F", carbooking.getCarprocess_status())) {
			CaruseRec caruseRec = new CaruseRec();
			caruseRec.setApplyid(carbooking.getApplyid());
			caseData.setCaruserec(caruseRecDao.selectDataByApplyid(caruseRec));
		}
	}
	
    /**
     * 取得下拉選單資料
     *
     * @param caseData
     */
    private void setTimelist(Eip07w060Case caseData) {
        List<String> hour=new ArrayList<>();
        List<String> minute=new ArrayList<>();
        for (int h=0;h<=59;h++){
            minute.add( String.format("%2s", h).replace(' ', '0'));
        }
        for (int m=0;m<=23;m++){
            hour.add( String.format("%2s", m).replace(' ', '0'));
        }
        caseData.setHourList(hour);
        caseData.setMinuteList(minute);
    }
    
    /**
     * 取得司機中文名稱
     *
     * @param carno1
     * @param carno2
     */
    private String findDriveName(String carno1, String carno2) {
    	DriverBase driverbase = new DriverBase();
    	driverbase.setCarno1(carno1);
    	driverbase.setCarno2(carno2);
    	DriverBase rsdb = driverBaseDao.getDataByCarno12(driverbase);
    	return rsdb != null? rsdb.getName():"";
    }
}