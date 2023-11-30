package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.CarBaseDao;
import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.Car_booking_recDao;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Car_booking_rec;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Users;
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
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private  MailService mailService;
	
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
		setHandedListAndReconfime_mk2List(handledList);
		caseData.setHandledList(handledList);
		
		notHandleList = carBookingDao.notHandledDataForEip07w040(caseData,"default");
		setnotHandedList(notHandleList);
		caseData.setNotHandleList(notHandleList);
		
		
		Reconfime_mk2List = carBookingDao.selectByReconfime_mk2ForEip07w040(caseData,"default");
		setHandedListAndReconfime_mk2List(Reconfime_mk2List);
		caseData.setReconfime_mk2List(Reconfime_mk2List);
		
		int lastDay = DateUtility.lastDay(DateUtility.getNowWestDate(),false);
		caseData.setUsing_time_sStrForTable2(DateUtility.getNowChineseYearMonth()+"01");
		caseData.setUsing_time_eStrForTable2(DateUtility.getNowChineseYearMonth()+String.valueOf(lastDay));
		caseData.setUsing_time_sStrForTable3(DateUtility.getNowChineseYearMonth()+"01");
		caseData.setUsing_time_eStrForTable3(DateUtility.getNowChineseYearMonth()+String.valueOf(lastDay));
	}
	
    public void setHandedListAndReconfime_mk2List(List<CarBooking>handledList) {
    	if(CollectionUtils.isNotEmpty(handledList)) {
    		for(CarBooking data : handledList) {
    			Users userData = usersDao.selectByKey(data.getApply_user());
    			data.setApply_user(userData.getUser_name());
    			Depts deptData = deptsDao.findByPk(data.getApply_dept());
    			data.setApply_dept(deptData.getDept_name());
    			data.setApprove_using_time_s(data.getApprove_using_time_s().substring(0,2)+":"+data.getApprove_using_time_s().substring(2,4));
    			data.setApprove_using_time_e(data.getApprove_using_time_e().substring(0,2)+":"+data.getApprove_using_time_e().substring(2,4));
    		}
    	}
    }
	
    public void setnotHandedList(List<CarBooking>notHandleList) {
    	if(CollectionUtils.isNotEmpty(notHandleList)) {  		
    		for(CarBooking data : notHandleList) {
    			Users userData = usersDao.selectByKey(data.getApply_user());
    			data.setApply_user(userData.getUser_name());
    			Depts deptData = deptsDao.findByPk(data.getApply_dept());
    			data.setApply_dept(deptData.getDept_name());
    			data.setUsing_time_s(data.getUsing_time_s().substring(0,2)+":"+data.getUsing_time_s().substring(2,4));
    			data.setUsing_time_e(data.getUsing_time_e().substring(0,2)+":"+data.getUsing_time_e().substring(2,4));
    			Optional<Eipcode> eipcode = eipcodeDao.findByCodeKindCodeNo("CARPROCESSSTATUS", data.getCarprocess_status());
    			data.setCarprocess_status(data.getCarprocess_status()+"-"+eipcode.get().getCodename());
    			if(Integer.valueOf(data.getUsing_date())<Integer.valueOf(data.getCre_datetime().substring(0,8))) {
    				data.setFillmk("Y");
    			}
    		}
    	}
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
			setnotHandedList(notHandleList);
		} else if("2".equals(caseData.getCarprocess_status())) {
			handledList = carBookingDao.handledDataForEip07w040(caseData,"condition");
			setHandedListAndReconfime_mk2List(handledList);
			caseData.setUsing_time_sStrForTable2(caseData.getUsing_time_s());
			caseData.setUsing_time_eStrForTable2(caseData.getUsing_time_e());
		} else if("3".equals(caseData.getCarprocess_status())) {
			Reconfime_mk2List = carBookingDao.selectByReconfime_mk2ForEip07w040(caseData,"Y");
			caseData.setUsing_time_sStrForTable3(caseData.getUsing_time_s());
			caseData.setUsing_time_eStrForTable3(caseData.getUsing_time_e());
			setHandedListAndReconfime_mk2List(Reconfime_mk2List);
		} else if("4".equals(caseData.getCarprocess_status())) {
			Reconfime_mk2List = carBookingDao.selectByReconfime_mk2ForEip07w040(caseData,"N");
			caseData.setUsing_time_sStrForTable3(caseData.getUsing_time_s());
			caseData.setUsing_time_eStrForTable3(caseData.getUsing_time_e());
			setHandedListAndReconfime_mk2List(Reconfime_mk2List);
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
	public void getDetailData(Eip07w040Case caseData,String enterpage) {
		CarBooking carBookingDetailData = carBookingDao.selectByApplyId(caseData.getApplyid());
		Eipcode eipcode = new Eipcode();
		eipcode.setCodekind("CARTYPE");
		eipcode.setCodeno(carBookingDetailData.getApply_car_type());
		Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
		carBookingDetailData.setApply_car_type(code.getCodename());
		
		if(StringUtils.isNotEmpty(carBookingDetailData.getUsing())){
			String[] usingTime = timeConversionService.timeStringToBeginEndTime(carBookingDetailData.getUsing());
			carBookingDetailData.setUsingStr(usingTime[0].substring(0,2)+":"+usingTime[0].substring(2,4) + "~"+usingTime[1].substring(0,2)+":"+usingTime[1].substring(2,4));
			caseData.setApprove_using_timeStr(usingTime[0].substring(0,2)+":"+usingTime[0].substring(2,4) + "~"+usingTime[1].substring(0,2)+":"+usingTime[1].substring(2,4));
		}
		carBookingDetailData.setApply_user(carBookingDetailData.getApply_user());
		

		caseData.setCarBookingDetailData(carBookingDetailData);//案件明細資料		
		
		caseData.setStarH(carBookingDetailData.getUsing_time_s().substring(0,2));
		caseData.setStarM(carBookingDetailData.getUsing_time_s().substring(2,4));
		
		caseData.setEndH(carBookingDetailData.getUsing_time_e().substring(0,2));
		caseData.setEndM(carBookingDetailData.getUsing_time_e().substring(2,4));
		
		
		if("eip07w040x".equals(enterpage)) {
			List<CarBase> carList = carBaseDao.getAllData();//取得所有非首長&&carstatus=1的車輛
			List<CarBase>carnoList = carList.stream().filter(e -> "N".equals(e.getBoss_mk()) && "1".equals(e.getCarstatus())).collect(Collectors.toList());
			caseData.setCarnoList(carnoList);
		} else if("eip07w041x".equals(enterpage)) {
			Car_booking_rec rec = car_booking_recDao.selectByKey(caseData.getApplyid());
			caseData.setRecData(rec);
		}

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
		
        String approveUsing= timeConversionService.to48binary(conversionTime(caseData.getStarH(), caseData.getStarM(),"S"),conversionTime(caseData.getEndH(), caseData.getEndM(),"M"));
        if ("2330".equals(caseData.getApprove_using_time_e())){
        	approveUsing=StringUtils.substring(approveUsing,0,47)+"0";
        }
        caseData.setApprove_using(approveUsing);
		if(CollectionUtils.isNotEmpty(list)) {
			caseData.setCarBookingList(list);
			for(CarBooking carbooking : list) {//caseData.getUsing()：本案件的用車時間，逐一比對當日是否有人同時要用車
				
				
				CarBooking check = carBookingDao.checkTime(caseData.getApprove_using(),carbooking.getUsing_rec());
				if("Y".equals(check.getUsing())) {
					caseData.setTimeMK("Y");//顯示該用車時間已有人預約
				} else {
					caseData.setTimeMK("N");
				}
				
				String[] usingTime = timeConversionService.timeStringToBeginEndTime(carbooking.getUsing_rec());
				carbooking.setUsing_time_s(usingTime[0]);
				carbooking.setUsing_time_e(usingTime[1].trim());;
				
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
			caseData.setTimeMK("N");//顯示該用車時間無人預約
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
		
        String approveUsing= timeConversionService.to48binary(conversionTime(caseData.getStarH(), caseData.getStarM(),"S"),conversionTime(caseData.getEndH(), caseData.getEndM(),"M"));
        if ("2330".equals(caseData.getApprove_using_time_e())){
        	approveUsing=StringUtils.substring(approveUsing,0,47)+"0";
        }
        carBooking.setApprove_using(approveUsing);
        carBooking.setApprove_using_time_s(caseData.getStarH()+caseData.getStarM());
        carBooking.setApprove_using_time_e(caseData.getEndH()+caseData.getEndM());
        
//		@派車結果選項:若為3，則表單狀態=3派全程
//	              若為4，則表單狀態=4派單程
//		若為5，則表單狀態=5已派滿
//		若為6，則表單狀態=6併單,派全程
//		若為7，則表單狀態=7併單,派單程
//		若為8，則表單狀態=8乘坐人數超過座車人數限制，無法受理


		carBooking.setCarprocess_status(caseData.getStatus());
		carBookingDao.updateByKey(carBooking);
		
		if(!"5,8".contains(caseData.getStatus())) { //若表單狀態為五或八則不寫入carBooking_rec
			Car_booking_rec rec = new Car_booking_rec();
			rec.setApplyid(carBooking.getApplyid());
			rec.setCarno1(carno[0]);
			rec.setCarno2(carno[1]);
			rec.setUsing_date(carBooking.getUsing_date());
			rec.setUsing_rec(approveUsing);
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
			vo.setApply_user(getUserNameOrDeptName(data.getApply_user(),true));//申請人
			vo.setApply_dept(getUserNameOrDeptName(data.getApply_dept(),false));
			vo.setApply_date(DateUtility.formatChineseDateString(data.getApply_date(),false));//申請日期
			vo.setApply_memo(data.getApply_memo());//用車事由
			vo.setDestination(data.getDestination());//目的地
			Eipcode eipcode = new Eipcode();
			eipcode.setCodekind("CARTYPE");
			eipcode.setCodeno(data.getApply_car_type());
			Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
			vo.setApply_car_type(code.getCodename());//車輛種類
			vo.setNum_of_people(data.getNum_of_people());//人數
			
			vo.setUsing_date(DateUtility.formatChineseDateString(data.getUsing_date(),false));//用車日期
			vo.setUsing_time_s(data.getUsing_time_s().substring(0,2)+":"+data.getUsing_time_s().substring(2,4));//用車時間起
			vo.setUsing_time_e(data.getUsing_time_e().substring(0,2)+":"+data.getUsing_time_e().substring(2,4));//用車時間迄
			vo.setApprove_using_time_s(data.getApprove_using_time_s());
			vo.setApprove_using_time_e(data.getApprove_using_time_e());
			
			Eipcode eipcode2 = new  Eipcode();
			eipcode2.setCodekind("CARPROCESSSTATUS");
			eipcode2.setCodeno(data.getCarprocess_status());;
			Eipcode CARPROCESS_STATUS = eipcodeDao.selectDataByPrimaryKey(eipcode2);
			vo.setCarprocess_status(data.getCarprocess_status()+"-"+CARPROCESS_STATUS.getCodename());		
			
			vo.setCombine_mk(data.getCombine_mk());//併單註記
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

		
		Users user = usersDao.selectByKey(cb.getApply_user());		
        String using_date = DateUtility.formatChineseDateString(cb.getUsing_date(),false);
        String using_time = cb.getUsing_time_s().substring(0,2)+cb.getUsing_time_s().substring(2,4)+"~"
    	        +cb.getUsing_time_e().substring(0,2)+cb.getUsing_time_e().substring(2,3);
		StringBuffer sb = new StringBuffer();
        sb.append("派車單號：" + cb.getApplyid() +"\r\n");
        sb.append("申請人:"+ getUserNameOrDeptName(cb.getApply_user(),true)+"\r\n");
        sb.append("申請單位:"+ getUserNameOrDeptName(cb.getApply_dept(),false)+"\r\n");
        sb.append("用車日期" + using_date +"\r\n");
        sb.append("用車時間" + using_time +"\r\n");
        sb.append("用車事由" + cb.getApply_memo());
        mailService.sendEmailNow("【秘書處臨時取消派車】用車日期："+using_date+"，"+using_time +"車號："+cb.getCarno1()+"-"+cb.getCarno2()  ,user.getEmail() ,sb.toString());
		
		Car_booking_rec rec = new Car_booking_rec();
		rec.setApplyid(caseData.getApplyid());
		car_booking_recDao.deleteByKey(rec);

	}
	
	public String getUserNameOrDeptName(String id , boolean getUserData) {
		
		if(getUserData) {
			Users user = usersDao.selectByKey(id);
			if(user != null) {
				return StringUtils.isEmpty(user.getUser_name()) ? "" : user.getUser_name();
			}
		} else {
			Depts dept = deptsDao.findByPk(id);
			if(dept != null) {
				return StringUtils.isEmpty(dept.getDept_name()) ? "" : dept.getDept_name();
			}
		}
		
		return "";
	}
	
    /**
     * 時1~23
     * 分1~59
     *
     * @param caseData
     */
    public void getTimeList(Eip07w040Case caseData) {
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
   
    public Map<String,String>ajaxGetTime(Eip07w040Case caseData){
        String starTime= conversionTime(caseData.getStarH(), caseData.getStarM(),"S");
        String endTime =conversionTime(caseData.getEndH(), caseData.getEndM(),"E");
        Map<String,String> timeMap = new HashMap<>();
        timeMap.put("starTime", starTime);
        timeMap.put("endTime", endTime);
        return timeMap;
    }
    
    /**
     * hour 時
     * minute
     * type:S 9:20->0900   9:45->0930
     *      E  9:20->0930   9:45->1000
     * type:S-開始 E-結束
     * @param
     */
    public  String conversionTime(String hour ,String minute ,String type) {
//        Integer.parseInt(
        int H= Integer.parseInt(hour);
        int M= Integer.parseInt(minute);
        String time;
        String time1 = String.format("%2s", H).replace(' ', '0') + "00";
        String time2 = String.format("%2s", H).replace(' ', '0') + "30";
        if ("S".equals(type)){
            if (M>=30){
                time= time2;
            }else {
                time= time1;
            }
            return time;
        }else {
            if (M>30){
                time= String.format("%2s",H+1).replace(' ', '0')+"00";
            }else if (M==0){//如果等於0
                time= time1;
            }

            else {
                time= time2;
            }

            return time;
        }
    }

}