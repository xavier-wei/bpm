package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.apply.report.Eip07w020l00;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Car_booking_rec;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;
import tw.gov.pcc.eip.util.DateUtility;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip07w020Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w020Service.class);

    @Autowired
    UserBean userData;
    @Autowired
    private User_rolesDao userRolesDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private CarBookingDao carBookingDao;
    @Autowired
    private Car_booking_recDao car_booking_recDao;
    @Autowired
    private User_rolesDao user_rolesDao;
    @Autowired
    private  TimeConversionService timeConversionService;
    @Autowired
    private  MailService mailService;




    private boolean userroleIsExist(String userid) {
    	User_roles userroles = userRolesDao.selectByKey(userid, User_rolesDao.SYSTEM_ADMIN_SYS_ID, User_rolesDao.SYSTEM_ADMIN_DEPT_ID, User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    	return userroles == null ? false:true;
    }

    /**
     * 新增預約單
     *
     * @param insterData
     */
    public void addReserve(Eip07w020Case insterData,Eip07w020Case caseData) throws Exception{
        String sysDateTime = DateUtil.getNowWestDateTime(true);
        String sysDate = sysDateTime.substring(0, 8);
        String applyId="DC"+sysDate+String.format("%3s", carBookingDao.getApplyCarnoSeq()).replace(' ', '0');
       insterData.setApplyId(applyId);
        //取using  48位元
        String endTime =conversionTime(insterData.getEndH(), insterData.getEndM(),"E");
        String using= timeConversionService.to48binaryForMeeting(conversionTime(insterData.getStarH(), insterData.getStarM(),"S"),conversionTime(insterData.getEndH(), insterData.getEndM(),"M"));
        if ("2330".equals(endTime)){
            using=StringUtils.substring(using,0,47)+"0";
        }
        insterData.setUsing(using);
        insterData.setApplyTimeS(insterData.getStarH()+insterData.getStarM());
        insterData.setApplyTimeE(insterData.getEndH()+insterData.getEndM());
        insterData.setProcessStaus("1");
        insterData.setUpdUser(userData.getUserId());
        insterData.setCreUser(userData.getUserId());
        insterData.setCreDatetime(sysDateTime);
        insterData.setUpdDatetime(sysDateTime);
        insterData.setApplyName(caseData.getApplyName());
        insterData.setApplyUnit(caseData.getApplyUnit());
        insterData.setApplyDate(DateUtility.changeDateType(caseData.getApplyDate()));
        insterData.setUseDate(DateUtility.changeDateType(insterData.getUseDate()));
        CarBooking carBooking =setCarBooking(insterData);
        carBookingDao.insert(carBooking);
        caseData.setApplyId(applyId);
    }


    /**
     * 取得下拉選單資料
     *
     * @param caseData
     */
    public void getSelectList(Eip07w020Case caseData) {
        List<Eipcode> getCarTyList = eipcodeDao.findByCodeKind("CAR_TYPE");
        caseData.setCarTyList(getCarTyList);
    }

    /**
     * 時1~23
     * 分1~59
     *
     * @param caseData
     */
    public void getTimeList(Eip07w020Case caseData) {
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
     * 根據key刪除資料
     *
     * @param caseData
     */
    public void delete(Eip07w020Case caseData)throws Exception {
        CarBooking deleteData =new CarBooking();
        deleteData.setApplyid(caseData.getApplyId());
        carBookingDao.deleteByKey(deleteData);
    }


    /**
     * 查詢資料
     *
     * @param caseData
     */
    public  List<Eip07w020Case> quaryData(Eip07w020Case caseData) throws Exception{

        caseData.setApplyDateStar(DateUtility.changeDateType(caseData.getApplyDateStar()));
        caseData.setApplyDateEnd(DateUtility.changeDateType(caseData.getApplyDateEnd()));
        caseData.setUseDateStar(DateUtility.changeDateType(caseData.getUseDateStar()));
        caseData.setUseDateEnd(DateUtility.changeDateType(caseData.getUseDateEnd()));


        List<CarBooking> quaryList= carBookingDao.quaryData(caseData);
        List<Eip07w020Case> resulList =new ArrayList<>();
        for (CarBooking data:quaryList) {
            Eip07w020Case resul =new Eip07w020Case();
            resul.setApplyId(data.getApplyid());
            resul.setApplyName(data.getApply_user());
            resul.setApplyDate(DateUtility.changeDateType(data.getApply_date()));
            resul.setApplyUnit(data.getApply_dept());
            resul.setUseCarMemo(data.getApply_memo());
            resul.setUseDate(DateUtility.changeDateType(data.getUsing_date()));
            resul.setApplyTimeS(data.getUsing_time_s());
            resul.setApplyTimeE(data.getUsing_time_e());
            resul.setUsing(data.getUsing());
            resul.setDestination(data.getDestination());
            resul.setCarTy(data.getApply_car_type());
            resul.setNumber(data.getNum_of_people());
            resul.setProcessStaus(data.getCarprocess_status());
            resul.setProcessStausNm(data.getCodeName());
            resulList.add(resul);
        }

        return resulList;
    }

    /**
     * update資料依driverid
     *
     * @param updateData
     */
    public void updateCarBooking( CarBooking updateData)throws Exception {
        String sysDateTime = DateUtil.getNowWestDateTime(true);
        String sysDate = sysDateTime.substring(0, 8);
        updateData.setUsing_time_s(updateData.getStarH()+updateData.getStarM());
        updateData.setUsing_time_e(updateData.getEndH()+updateData.getEndM());
        updateData.setApply_date(DateUtility.changeDateType(updateData.getApply_date()));
        updateData.setUsing_date(DateUtility.changeDateType(updateData.getUsing_date()));
        updateData.setUpd_user(userData.getUserId());
        updateData.setUpd_datetime(sysDateTime);
        getEntTime(updateData, updateData);
        carBookingDao.updateByKey(updateData);
    }

    /**
     * update 異動申請
     *
     * @param data
     */
    public void changeApplication( Eip07w020Case data)throws Exception {
        CarBooking changeData = data.getChangeMkList().get(0);
        CarBooking oldData= data.getDetailsList().get(0);
        String status=oldData.getCarprocess_status();
        if ("U".equals(status)||"7".equals(status)||"6".equals(status)){
            data.setRmMemo("2");
        }
        int count ;
        //計算更改次數並加一
        if (StringUtils.isBlank(oldData.getChange_count())){
            count=1;
        }else {
            count= Integer.parseInt(oldData.getChange_count())+1;
        }
        if ("1".equals(data.getRmMemo())){//1.原申請資料變更
            oldData.setChange_mk("Y");
            oldData.setChange_count(String.valueOf(count));
            oldData.setCarprocess_status("U");
            oldData.setB_apply_memo(oldData.getApply_memo());
            oldData.setB_apply_time_s(oldData.getUsing_time_s());
            oldData.setB_apply_time_e(oldData.getUsing_time_e());
            oldData.setB_using(oldData.getUsing());
            oldData.setB_destination(oldData.getDestination());
            oldData.setB_apply_car_type(oldData.getApply_car_type());
            oldData.setB_num_of_people(oldData.getNum_of_people());
            oldData.setApply_memo(changeData.getApply_memo());
            oldData.setUsing_time_s(changeData.getStarH()+changeData.getStarM());
            oldData.setUsing_time_e(changeData.getEndH()+changeData.getEndM());
            //取using  48位元
            getEntTime(changeData, oldData);
            oldData.setDestination(changeData.getDestination());
            oldData.setApply_car_type(changeData.getApply_car_type());
            oldData.setNum_of_people(changeData.getNum_of_people());
            oldData.setCarno1("");
            oldData.setCarno2("");
            oldData.setName("");
            oldData.setCellphone("");
            oldData.setCartype("");
            oldData.setChange_reason("1");
            oldData.setApply_date(DateUtility.changeDateType(oldData.getApply_date()));
            oldData.setUsing_date(DateUtility.changeDateType(oldData.getUsing_date()));
            oldData.setUpd_user(userData.getUserId());
            oldData.setUpd_datetime(DateUtil.getNowWestDateTime(true));
            carBookingDao.updateByKey(oldData);
            data.setDetailsList(Collections.singletonList(oldData));
            Car_booking_rec carBookingRec = new Car_booking_rec();
            carBookingRec.setApplyid(oldData.getApplyid());
            if (!"Y".equals(oldData.getCombine_mk())){
            car_booking_recDao.deleteByKey(carBookingRec);
            }
        }else {//2取消申請
            if ("Y".equals(data.getIsSecretarial())){//判斷是否為秘書室取消
                oldData.setCarprocess_status("9");
                oldData.setUpd_user(userData.getUserId());
                oldData.setApply_date(DateUtility.changeDateType(oldData.getApply_date()));
                oldData.setUsing_date(DateUtility.changeDateType(oldData.getUsing_date()));
                oldData.setUpd_datetime(DateUtil.getNowWestDateTime(true));
            }else {
                oldData.setChange_count(String.valueOf(count));
                oldData.setChange_mk("Y");
                oldData.setChange_reason("2");
                oldData.setCarprocess_status("C");
                oldData.setApply_date(DateUtility.changeDateType(oldData.getApply_date()));
                oldData.setUsing_date(DateUtility.changeDateType(oldData.getUsing_date()));
                oldData.setUpd_user(userData.getUserId());
                oldData.setUpd_datetime(DateUtil.getNowWestDateTime(true));
            }


            Car_booking_rec carBookingRec = new Car_booking_rec();
            carBookingRec.setApplyid(oldData.getApplyid());
            carBookingDao.updateByKey(oldData);
            car_booking_recDao.deleteByKey(carBookingRec);
            //寄maile功能
            Optional<Eipcode> codeName= eipcodeDao.findByCodeKindCodeNo("CARPROCESSSTATUSMAIL","1");
            String mail=codeName.get().getCodename();
            mailService.sendEmailNow("派車單號:"+oldData.getApplyid()+"取消派車",mail,oldData.getApply_user()+"取消派車");
        }
    }

    private void getEntTime(CarBooking changeData, CarBooking oldData) {
        String endTime =conversionTime(changeData.getEndH(), changeData.getEndM(),"E");
        String using= timeConversionService.to48binaryForMeeting(conversionTime(changeData.getStarH(), changeData.getStarM(),"S"),conversionTime(changeData.getEndH(), changeData.getEndM(),"E"));
        if ("2330".equals(endTime)){
            using= StringUtils.substring(using,0,47)+"0";
        }
        oldData.setUsing(using);
    }

    //判斷是否為秘書室人員登入
    public void secretarialLogin(Eip07w020Case data) {
        //判斷登入user是否為秘書室
        List<User_roles> role=user_rolesDao.judgeSecretarialLogin(userData.getUserId(),"EIP07W010");
        if(!role.isEmpty()){
                data.setIsSecretarial("Y");
            }else {
                data.setIsSecretarial("N");
            }
        }


    //判斷秘書室人員是否查詢自己的表單
    public void secretarialChoseApplyid(Eip07w020Case data) {
        if (data.getApplyUnit().equals(data.getDetailsList().get(0).getApply_dept())){
            data.setIsSecretarial("N");
        }else {
            data.setIsSecretarial("Y");
        }
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

     /**
     * 查詢預約單明細資料
     *
     * @param applyId
     */
    public  CarBooking selectByApplyId(String applyId) throws Exception{
        CarBooking detail= carBookingDao.selectByApplyId( applyId);
        Optional<Eipcode> codeName= eipcodeDao.findByCodeKindCodeNo("CARPROCESSSTATUS",detail.getCarprocess_status());
        detail.setCodeName(codeName.get().getCodeno()+"-"+codeName.get().getCodename());

        if (StringUtils.isNotBlank(detail.getCartype())){//有些資料可能尚未派車
            Optional<Eipcode> carTyNm= eipcodeDao.findByCodeKindCodeNo("CARTYPE",detail.getCartype());
            detail.setCarTyNm(carTyNm.get().getCodename());
        }
        detail.setStarH(StringUtils.substring(detail.getUsing_time_s(),0,2));
        detail.setStarM(StringUtils.substring(detail.getUsing_time_s(),2));
        detail.setEndH(StringUtils.substring(detail.getUsing_time_e(),0,2));
        detail.setEndM(StringUtils.substring(detail.getUsing_time_e(),2));
        detail.setUsing_date(DateUtility.changeDateType(detail.getUsing_date()));
        detail.setApply_date(DateUtility.changeDateType(detail.getApply_date()));
        if ("U".equals(detail.getCarprocess_status())){
            detail.setRmStarH(StringUtils.substring(detail.getB_apply_time_s(),0,2));
            detail.setRmStarM(StringUtils.substring(detail.getB_apply_time_s(),2));
            detail.setRmEndH(StringUtils.substring(detail.getB_apply_time_e(),0,2));
            detail.setRmEndM(StringUtils.substring(detail.getB_apply_time_e(),2));
        }
        return detail;
    }

    /**
     * 呼叫報表
     *
     * @param caseData
     */
    public ByteArrayOutputStream print(Eip07w020Case caseData) throws Exception {
        Eip07w020l00 pdf = new Eip07w020l00();
        pdf.createEip07w020DataPdf(caseData.getDetailsList().get(0),caseData);
        return pdf.getOutputStream();
    };

    public  CarBooking setCarBooking(Eip07w020Case book) {
        CarBooking carBooking =new CarBooking();
        carBooking.setApply_user(book.getApplyName());
        carBooking.setApply_dept(book.getApplyUnit());
        carBooking.setApplyid(book.getApplyId());
        carBooking.setUsing(book.getUsing());
        carBooking.setCarprocess_status(book.getProcessStaus());
        carBooking.setApply_date(book.getApplyDate());
        carBooking.setApply_memo(book.getUseCarMemo());
        carBooking.setDestination(book.getDestination());
        carBooking.setApply_car_type(book.getCarTy());
        carBooking.setNum_of_people(book.getNumber());
        carBooking.setUsing_date(book.getUseDate());
        carBooking.setUsing_time_s(book.getApplyTimeS());
        carBooking.setUsing_time_e(book.getApplyTimeE());
        carBooking.setCre_user(book.getCreUser());
        carBooking.setCre_datetime(book.getCreDatetime());
        carBooking.setUpd_user(book.getUpdUser());
        carBooking.setUpd_datetime(book.getUpdDatetime());
        return carBooking;
    }




    private boolean userIsExist(String userid) {
    	return usersDao.selectByKey(userid) == null ? false:true;
    }

}
