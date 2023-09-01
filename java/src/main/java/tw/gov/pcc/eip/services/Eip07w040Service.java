package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.CarBaseDao;
import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.Car_booking_recDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Car_booking_rec;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w040Case;
import tw.gov.pcc.eip.report.Eip07w040l00;
import tw.gov.pcc.eip.report.vo.Eip07w040L_Vo;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 派車預約暨派車結果查詢作業Service
 *
 * @author ivy
 */
@Service
public class Eip07w040Service {

	@Autowired
	UserBean userData;
	@Autowired
	private EipcodeDao eipcodeDao;
	@Autowired
	private CarBookingDao carBookingDao;
	@Autowired
	private CarBaseDao carBaseDao;
	@Autowired
	private Car_booking_recDao car_booking_recDao;
	@Autowired
	private TimeConversionService timeConversionService;
	
	
	/**
	 * 依照申請日期、用車日期起迄搜尋審核資料
	 *		將資料分類為 1.待處理派車案件：notHandleList(carprocessstatus=2)
	 *				  2.秘書處已複核通過案件：HandledList(carProcessstatus=3,4)
	 * @param caseData
	 */
	public void getDefaultData(Eip07w040Case caseData) throws Exception {
		List<CarBooking> notHandleList = new ArrayList<>();
		List<CarBooking> handledList = new ArrayList<>();
		List<CarBooking> Reconfime_mk2List = new ArrayList<>();
		
		handledList = carBookingDao.handledDataForEip07w040(caseData,"default");
		caseData.setHandledList(handledList);
		
		notHandleList = carBookingDao.notHandledDataForEip07w040(caseData,"default");
		caseData.setNotHandleList(notHandleList);
		
		Reconfime_mk2List = carBookingDao.selectByReconfime_mk2ForEip07w040(caseData,"default");
		caseData.setReconfime_mk2List(Reconfime_mk2List);
		
		int lastDay = DateUtility.lastDay(DateUtility.getNowWestDate(),false);
		caseData.setUsing_time_sStrForTable2(DateUtility.getNowChineseYearMonth()+"01");
		caseData.setUsing_time_eStrForTable2(DateUtility.getNowChineseYearMonth()+String.valueOf(lastDay));
		caseData.setUsing_time_sStrForTable3(DateUtility.getNowChineseYearMonth()+"01");
		caseData.setUsing_time_eStrForTable3(DateUtility.getNowChineseYearMonth()+String.valueOf(lastDay));
	}
	
	/**
	 * 依照申請日期、用車日期起迄搜尋審核資料
	 *		將資料分類為 1.待處理派車案件：notHandleList(carprocessstatus=2)
	 *				  2.秘書處已複核通過案件：HandledList(carProcessstatus=3,4)
	 * @param caseData
	 */
	public void getDataByCondition(Eip07w040Case caseData) throws Exception {
		List<CarBooking> notHandleList = new ArrayList<>();
		List<CarBooking> handledList = new ArrayList<>();
		List<CarBooking> Reconfime_mk2List = new ArrayList<>();
		
		if("1".equals(caseData.getCarprocess_status())) {
			notHandleList = carBookingDao.notHandledDataForEip07w040(caseData,"condition");
		} else if("2".equals(caseData.getCarprocess_status())) {
			handledList = carBookingDao.handledDataForEip07w040(caseData,"condition");
			caseData.setUsing_time_sStrForTable2(caseData.getUsing_time_s());
			caseData.setUsing_time_eStrForTable2(caseData.getUsing_time_e());
		} else if("3".equals(caseData.getCarprocess_status())) {
			Reconfime_mk2List = carBookingDao.selectByReconfime_mk2ForEip07w040(caseData,"Y");
			caseData.setUsing_time_sStrForTable3(caseData.getUsing_time_s());
			caseData.setUsing_time_eStrForTable3(caseData.getUsing_time_e());
		} else if("4".equals(caseData.getCarprocess_status())) {
			Reconfime_mk2List = carBookingDao.selectByReconfime_mk2ForEip07w040(caseData,"N");
			caseData.setUsing_time_sStrForTable3(caseData.getUsing_time_s());
			caseData.setUsing_time_eStrForTable3(caseData.getUsing_time_e());
		}
		
		if(!"2".equals(caseData.getCarprocess_status())){
			caseData.setUsing_time_sStrForTable2("");
			caseData.setUsing_time_eStrForTable2("");
		}
		
		if(!"3,4".contains(caseData.getCarprocess_status())){
			caseData.setUsing_time_sStrForTable3("");
			caseData.setUsing_time_eStrForTable3("");
		}
		
		caseData.setHandledList(handledList);
		caseData.setNotHandleList(notHandleList);
		caseData.setReconfime_mk2List(Reconfime_mk2List);
	}

	/**
	 * 取得案件明細資料
	 * 
	 * @param caseData
	 * 
	 */
	public void getDetailData(Eip07w040Case caseData) {
		CarBooking carBookingDetailData = carBookingDao.selectByApplyId(caseData.getApplyid());
		Eipcode eipcode = new Eipcode();
		eipcode.setCodekind("CARTYPE");
		eipcode.setCodeno(carBookingDetailData.getApply_car_type());
		Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
		carBookingDetailData.setApply_car_type(code.getCodename());
	
		String[] usingTime = timeConversionService.timeStringToBeginEndTime(carBookingDetailData.getUsing());
		carBookingDetailData.setUsingStr(usingTime[0].substring(0,2)+":"+usingTime[0].substring(2,4) + "~"+usingTime[1].substring(0,2)+":"+usingTime[1].substring(2,4));
		carBookingDetailData.setApply_user(carBookingDetailData.getApply_user());
		
//		Depts deptName = deptsDao.findByPk(carBookingDetailData.getApply_dept());
//		carBookingDetailData.setApply_dept(deptName.getDept_name());
		caseData.setCarBookingDetailData(carBookingDetailData);//案件明細資料
		
		List<CarBase> carList = carBaseDao.getAllData();//取得所有非首長&&carstatus=1的車輛
		List<CarBase>carnoList = carList.stream().filter(e -> "N".equals(e.getBoss_mk()) && "1".equals(e.getCarstatus())).collect(Collectors.toList());
		caseData.setCarnoList(carnoList);

	}
	
	/**
	 * 以車輛號碼、用車日期取得今天是否有其他
	 * 
	 * @param caseData
	 * 
	 */
	public void getUsingData(Eip07w040Case caseData) {
		CarBooking cb = new CarBooking();
		cb.setCarno1(caseData.getCarno().replaceAll("-",""));
		cb.setCarno2("");
		cb.setUsing_date(caseData.getUsing_date());
		List<CarBooking> list = carBookingDao.getDataByCarnoAndUsing_date(cb);
		if(CollectionUtils.isNotEmpty(list)) {
			caseData.setCarBookingList(list);
			for(CarBooking carbooking : list) {//caseData.getUsing()：本案件的用車時間，逐一比對當日是否有人同時要用車
				CarBooking check = carBookingDao.checkTime(caseData.getUsing(),carbooking.getUsing_rec());
				if("Y".equals(check.getUsing())) {
					caseData.setTimeMK("Y");//顯示該用車時間已有人預約
				}

//				carbooking.setApply_user(getUserName(carbooking.getApply_user()));
				
//				Depts deptName = deptsDao.findByPk(carbooking.getApply_dept());
//				carbooking.setApply_dept(deptName.getDept_name());
				
				String[] usingTime = timeConversionService.timeStringToBeginEndTime(carbooking.getUsing_rec());
				carbooking.setUsing_time_s(usingTime[0]);
				carbooking.setUsing_time_e(usingTime[1].trim());
				
				String using = carbooking.getUsing_rec();
				
				char[] chars = using.toCharArray();
				StringBuffer num = new StringBuffer();
				for(int i=0; i<chars.length; i++) {
					if('1' == chars[i]) {
						num.append(i+",");
					}
				}
				carbooking.setUsingTimeList(StringUtils.removeEnd(num.toString(), ","));
				
			}
		} else {
			caseData.setCarBookingList(null);//顯示今日尚未有人預約使用
			caseData.setShowEmptyStr(true);
		}
		
		if(StringUtils.isEmpty(caseData.getTimeMK())) {
			caseData.setTimeMK("N");//顯示該用車時間無人預約
		}
		
		List<Eipcode> carStsList = eipcodeDao.findByCodeKind("CARPROSTS"); 
		caseData.setCarprostsList(carStsList);//派車狀態List
		caseData.setShowButton(true);//選擇完車號，才顯示下方併單&派車的選項
	}

	/**
	 * 更新eip07w040資料
	 * 
	 * @param caseData
	 * 
	 */
	public void updateAll(Eip07w040Case caseData) {
		String dateTime = DateUtility.getNowWestDateTime(true);

		CarBooking carBooking = carBookingDao.selectByApplyId(caseData.getCarBookingDetailData().getApplyid());
		String [] carno = caseData.getCarno().split("-");
		CarBooking queryData = carBookingDao.selectCarDriveDataBycarno(carno[0], carno[1]);
		carBooking.setName(queryData.getName());
		carBooking.setCellphone(queryData.getCellphone());
		carBooking.setCartype(queryData.getCartype());
		carBooking.setCarcolor(queryData.getCarcolor());
		carBooking.setCombine_mk(caseData.getMerge());
		if("Y".equals(caseData.getMerge()) && CollectionUtils.isNotEmpty(caseData.getCarBookingList())) {
			carBooking.setCombine_applyid(caseData.getMergeApplyid());
			carBooking.setCombine_reason(caseData.getMergeReason());
		}
		
		CarBase carData = carBaseDao.selectCarAndDriverByCarno(carno[0],carno[1]);
		if(carData == null) {
			throw new RuntimeException();
		}
		carBooking.setName(carData.getName());
		carBooking.setCellphone(carData.getCellphone());
		carBooking.setCarno1(carno[0]);
		carBooking.setCarno2(carno[1]);
		carBooking.setCartype(carData.getCartype());
		carBooking.setCarcolor(carData.getCarcolor());
		carBooking.setUpd_user(userData.getUserId());
		carBooking.setUpd_datetime(dateTime);
		
//		@派車結果選項:若為3，則表單狀態=3派全程
//	              若為4，則表單狀態=4派單程
//		若為5，則表單狀態=5已派滿
//		若為6，則表單狀態=6併單,派全程
//		若為7，則表單狀態=7併單,派單程
//		若為8，則表單狀態=8乘坐人數超過座車人數限制，無法受理


		carBooking.setCarprocess_status(caseData.getStatus());
		carBookingDao.updateByKey(carBooking);
		
		Car_booking_rec rec = new Car_booking_rec();
		rec.setApplyid(carBooking.getApplyid());
		rec.setCarno1(carno[0]);
		rec.setCarno2(carno[1]);
		rec.setUsing_date(carBooking.getUsing_date());
		rec.setUsing_rec(carBooking.getUsing());
		rec.setCombine_mk(caseData.getMerge());
		rec.setCombine_reason(StringUtils.isNotEmpty(caseData.getMergeReason()) ? caseData.getMergeReason() : "");
		if("Y".equals(caseData.getMerge()) && CollectionUtils.isNotEmpty(caseData.getCarBookingList())) {
			rec.setCombine_applyid(caseData.getCarBookingList().get(0).getApplyid());
		}

		rec.setCre_user(userData.getUserId());
		rec.setCre_datetime(dateTime);
		rec.setUpd_user(userData.getUserId());
		rec.setUpd_datetime(dateTime);
		
		car_booking_recDao.insert(rec);
	}
	
	/**
	 * 列印報表
	 * 
	 * @param caseData
	 * 
	 */
	public ByteArrayOutputStream getEip07w040LReport(Eip07w040Case caseData) throws Exception {
		String printType = "";
		List<String> applyids = new ArrayList<>();
		if(StringUtils.isEmpty(caseData.getReprintApplyid())){
			printType = "1";//列印
			applyids = caseData.getApplyids().stream().filter(it -> StringUtils.isNotBlank(it)).collect(Collectors.toList());
			caseData.setApplyids(applyids);
		} else{
			applyids.add(caseData.getReprintApplyid());
			printType = "2";//補印
		}

		List<Eip07w040L_Vo>reportList = new ArrayList<>();
		for(String applyid : applyids) {
			Eip07w040L_Vo vo = new Eip07w040L_Vo();
			CarBooking data = carBookingDao.selectByApplyId(applyid);
			vo.setApply_user(data.getApply_user());//申請人
//			Depts deptName = deptsDao.findByPk(data.getApply_dept());
			vo.setApply_dept(data.getApply_dept());
			vo.setApply_date(data.getApply_date());//申請日期
			vo.setApply_memo(data.getApply_memo());//用車事由
			vo.setDestination(data.getDestination());//目的地
			Eipcode eipcode = new Eipcode();
			eipcode.setCodekind("CARTYPE");
			eipcode.setCodeno(data.getApply_car_type());
			Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
			vo.setApply_car_type(code.getCodename());//車輛種類
			vo.setNum_of_people(data.getNum_of_people());//人數
			vo.setUsing_date(data.getUsing_date());//用車日期
			vo.setUsing_time_s(data.getUsing_time_s());//用車時間起
			vo.setUsing_time_e(data.getUsing_time_e());//用車時間迄
			vo.setCombine_mk(data.getCombine_mk());//並單註記
			vo.setCombine_applyid(data.getCombine_applyid());//併單派車單號
			vo.setCombine_reason(data.getCombine_reason());//併單原因
			vo.setName(data.getName());//駕駛人姓名
			vo.setCellphone(data.getCellphone());//手機號碼
			vo.setCarno1(data.getCarno1());//車牌車號1
			vo.setCarno2(data.getCarno2());//車牌車號2
			vo.setCartype(code.getCodename());//車輛種類
			vo.setCarcolor(data.getCarcolor());//顏色
			vo.setApplyid(data.getApplyid());//派車單號
			if("1".equals(printType)) {
				data.setPrint_mk("Y");
			}
			carBookingDao.updateByKey(data);
			
			reportList.add(vo);
		}
		
		caseData.setReportList(reportList);
		Eip07w040l00 report = new Eip07w040l00();
		report.createPdf(caseData);
		
		caseData.setReprintApplyid("");//將資料清空
		caseData.setApplyids(null);

		return report.getOutputStream();
	}
	
	public void cancelData(Eip07w040Case caseData) {
		
		CarBooking cb = carBookingDao.selectByApplyId(caseData.getApplyid());
		cb.setCarprocess_status("9");
		cb.setUpd_datetime(DateUtility.getNowWestDateTime(true));
		cb.setUpd_user(userData.getUserId());
		carBookingDao.updateByKey(cb);
		
		
		Car_booking_rec rec = new Car_booking_rec();
		rec.setApplyid(caseData.getApplyid());
		car_booking_recDao.deleteByKey(rec);

	}
	
}