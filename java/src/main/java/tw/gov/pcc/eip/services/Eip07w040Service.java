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
	
	/**
	 * 依照申請日期起迄搜尋審核資料
	 *
	 * @param caseData
	 */
	public void getData(Eip07w040Case caseData) throws Exception {
		List<CarBooking> list = carBookingDao.selectByStatusIn234(caseData);
		List<CarBooking> notHandleList = new ArrayList<>();
		List<CarBooking> handledList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CarBooking car : list) {
				Eipcode eipcode = new Eipcode();
				eipcode.setCodekind("CARPROCESSSTATUS");
				eipcode.setCodeno(car.getCarprocess_status());
				Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
				car.setCarprocess_status(code.getCodeno() + "-" + code.getCodename());//表單狀態
				car.setPrint_mk(StringUtils.isEmpty(car.getPrint_mk())? "" : car.getPrint_mk());
				
				if(car.getCarprocess_status().startsWith("2")) {
					notHandleList.add(car);
				}
				
				
				if(car.getCarprocess_status().startsWith("3")|| car.getCarprocess_status().startsWith("4")) {
					handledList.add(car);
				}

			}
		}
		caseData.setNotHandleList(notHandleList);
		caseData.setHandledList(handledList);
	}

	/**
	 * 複核資料
	 * 
	 * @param caseData
	 * @return
	 */
	public void getDetailData(Eip07w040Case caseData) {
		CarBooking carBookingDetailData = carBookingDao.selectByApplyId(caseData.getApplyid());
		Eipcode eipcode = new Eipcode();
		eipcode.setCodekind("CARTYPE");
		eipcode.setCodeno(carBookingDetailData.getCarprocess_status());
		Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
		carBookingDetailData.setApply_car_type(code.getCodename());
		caseData.setCarBookingDetailData(carBookingDetailData);
		List<CarBase> carList = carBaseDao.getAllData();
		List<CarBase>carnoList = carList.stream().filter(e -> "N".equals(e.getBoss_mk()) && "1".equals(e.getCarstatus())).collect(Collectors.toList());
		caseData.setCarnoList(carnoList);

	}
	
	public void getUsingData(Eip07w040Case caseData) {
		CarBooking cb = new CarBooking();
		cb.setCarno1(caseData.getCarno().replaceAll("-",""));
		cb.setCarno2("");
		cb.setUsing_date(DateUtility.changeDateType(caseData.getUsing_date()));
		List<CarBooking> list = carBookingDao.getDataByCarnoAndUsing_date(cb);
		if(CollectionUtils.isNotEmpty(list)) {
			caseData.setCarBookingList(list);
			caseData.getUsing();
			for(CarBooking carbooking : list) {
				if("Y".equals(carBookingDao.checkTime(caseData.getUsing(),carbooking.getUsing()))) {
					caseData.setTimeMK("Y");
					break;
				}
			}
		} else {
			caseData.setCarBookingList(null);
			caseData.setShowEmptyStr(true);
		}
		
		if(StringUtils.isEmpty(caseData.getTimeMK())) {
			caseData.setTimeMK("N");
		}
		
		
		List<Eipcode> carStsList = eipcodeDao.findByCodeKind("CARPROSTS"); 
		caseData.setCarprostsList(carStsList);
		caseData.setShowButton(true);
	}

	public void updateAll(Eip07w040Case caseData) {
		String dateTime = DateUtility.getNowWestDateTime(true);
//		update car_booking
//	       Set carno1=[畫面鍵入].車號1
//	           Carno2=[畫面鍵入].車號2
//	       Name=sql2.name              --駕駛人姓名
//	    Cellphone= sql2.Cellphone         --手機號碼 
//		Cartype= sql2.cartype       --車輛種類: 1:4人座2:7人座
//	    carcolor =sql2.carcolor            --顏色
//	    Where applyid=sql1.派車單號;
		
//		  Step3:
//		  @[畫面].併單:Y then
//		     Update car_booking
//		        Set combine_mk=Y
//		            Combine_reason=[畫面鍵入].併單原因
//		            Combine_apply_id=sql1.applyid
//			[車輛相關資料]:
//			 派車單號:
//			 駕駛人姓名: [name]          手機號碼:[ cellphone]
//			 車牌車號: [Carno1][ Carno2]    車輛種類: [cartype]          顏色:[carcolor]

//		      Where applyid=[畫面鍵入].併單之派車單號;

		CarBooking carBooking = carBookingDao.selectByApplyId(caseData.getCarBookingDetailData().getApplyid());
		String [] carno = caseData.getCarno().split("-");
		CarBooking queryData = carBookingDao.selectCarDriveDataBycarno(carno[0], carno[1]);
		carBooking.setName(queryData.getName());
		carBooking.setCellphone(queryData.getCellphone());
		carBooking.setCartype(queryData.getCartype());
		carBooking.setCarcolor(queryData.getCarcolor());
		carBooking.setCombine_mk(caseData.getMerge());
		if("Y".equals(caseData.getMerge()) && CollectionUtils.isNotEmpty(caseData.getCarBookingList())) {
			carBooking.setCombine_applyid(caseData.getCarBookingList().get(0).getApplyid());
			carBooking.setCombine_reason(caseData.getMergeReason());
		}
		
		CarBase carData = carBaseDao.selectCarAndDriverByCarno(carno[0],carno[1]);
		carBooking.setName(carData.getName());
		carBooking.setCellphone(carData.getCellphone());
		carBooking.setCarno1(carno[0]);
		carBooking.setCarno2(carno[1]);
		carBooking.setCartype(carData.getCartype());
		carBooking.setCarcolor(carData.getCarcolor());
		carBooking.setUpd_user(userData.getUserId());
		carBooking.setUpd_datetime(dateTime);
		
//		@派車結果選項:若為1，則表單狀態=3派全程，若為2，則表單狀態=4派單程，若為3，則表單狀態=5已派滿
//					 若為4，則表單狀態=6併單,派全程，若為5，則表單狀態=7併單,派單程
		String sts = "";
		if("1".equals(caseData.getCarprocess_status())){
			sts = "3";
		} else if ("2".equals(caseData.getCarprocess_status())){
			sts = "4";
		} else if ("3".equals(caseData.getCarprocess_status())){
			sts = "5";
		} else if ("4".equals(caseData.getCarprocess_status())){
			sts = "6";
		} else if ("5".equals(caseData.getCarprocess_status())){
			sts = "7";
		}
		carBooking.setCarprocess_status(sts);
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
	
	public ByteArrayOutputStream getEip07w040LReport(Eip07w040Case caseData) throws Exception {
		String printType = "";
		if(StringUtils.isEmpty(caseData.getReprintApplyid())){
			printType = "1";//列印
		} else{
			caseData.getApplyids().add(caseData.getReprintApplyid());
			printType = "2";//補印
		}
		
		List<String> applyids = caseData.getApplyids().stream().filter(it -> StringUtils.isNotBlank(it)).collect(Collectors.toList());
		caseData.setApplyids(applyids);
		List<Eip07w040L_Vo>reportList = new ArrayList<>();
		for(String applyid : applyids) {
			Eip07w040L_Vo vo = new Eip07w040L_Vo();
			CarBooking data = carBookingDao.selectByApplyId(applyid);
			vo.setApply_user(data.getApply_user());//申請人
			vo.setApply_dept(data.getApply_dept());//申請單位
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
	
}