package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.apply.report.Eip07w020l00;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.CarBooking;
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
    private  TimeConversionService timeConversionService;
    String sysDateTime = DateUtil.getNowWestDateTime(true);
    String sysDate = sysDateTime.substring(0, 8);


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
        //取ApplyId
        List<CarBooking> applyId=carBookingDao.getApplyId();
        int maxApplyId= 0;
        if (applyId.size()>0){
            maxApplyId=Integer.parseInt(StringUtils.substring(applyId.get(0).getApplyid(),10 ))+1;
        }
        else {
        maxApplyId= 1;
        }
       insterData.setApplyId("DC"+sysDate+String.format("%3s", maxApplyId).replace(' ', '0'));
        //取using  48位元
        String using= timeConversionService.to48binary(conversionTime(insterData.getStarH(), insterData.getStarM(),"S"),conversionTime(insterData.getEndH(), insterData.getEndM(),"M"));
        insterData.setUsing(using);
        insterData.setApplyTimeS(insterData.getStarH()+insterData.getStarM());
        insterData.setApplyTimeE(insterData.getEndH()+insterData.getEndM());
        insterData.setProcessStaus("1");
        insterData.setUpdUser(userData.getUserId());
        insterData.setCreUser(userData.getUserId());
        insterData.setCreDatetime(sysDate);
        insterData.setUpdDatetime(sysDate);
        insterData.setApplyName(caseData.getApplyName());
        insterData.setApplyUnit(caseData.getApplyUnit());
        insterData.setApplyDate(DateUtility.changeDateType(caseData.getApplyDate()));
        insterData.setUseDate(DateUtility.changeDateType(insterData.getUseDate()));
        carBookingDao.insert(insterData);
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
     * @param CarBooking
     */
    public void updateCarBooking( CarBooking updateData)throws Exception {
        updateData.setUsing_time_s(updateData.getStarH()+updateData.getStarM());
        updateData.setUsing_time_e(updateData.getEndH()+updateData.getEndM());
        carBookingDao.updateByKey(updateData);
    }

    /**
     * update 異動申請
     *
     * @param CarBooking
     */
    public void changeApplication( Eip07w020Case data)throws Exception {
        CarBooking changeData = data.getChangeMkList().get(0);
        CarBooking oldData= data.getDetailsList().get(0);
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
            String using= timeConversionService.to48binary(conversionTime(changeData.getStarH(), changeData.getStarM(),"S"),conversionTime(changeData.getEndH(), changeData.getEndM(),"M"));
            oldData.setUsing(using);
            oldData.setDestination(changeData.getDestination());
            oldData.setApply_car_type(changeData.getApply_car_type());
            oldData.setNum_of_people(changeData.getNum_of_people());
            oldData.setCarno1("");
            oldData.setCarno2("");
            oldData.setName("");
            oldData.setCellphone("");
            oldData.setCartype("");
            carBookingDao.updateByKey(oldData);
            data.setDetailsList(Collections.singletonList(oldData));
        }else {//2取消申請
            oldData.setChange_count(String.valueOf(count));
            oldData.setChange_mk("Y");
            oldData.setChange_reason("2");
            oldData.setCarprocess_status("C");
            carBookingDao.updateByKey(oldData);
        }
    }

    /**
     * hour 時
     * minute
     * type:S 9:20->0900   9:45->0930
     *      E  9:20->0930   9:45->1000
     * type:S-開始 E-結束
     * @param CarBooking
     */
    public  String conversionTime(String hour ,String minute ,String type) {
//        Integer.parseInt(
        int H= Integer.parseInt(hour);
        int M= Integer.parseInt(minute);
        String time;
        if ("S".equals(type)){
            if (M>=30){
                time=  String.format("%2s", H).replace(' ', '0')+"30";
            }else {
                time= String.format("%2s", H).replace(' ', '0')+"00";
            }
            return time;
        }else {
            if (M>=30){
                time= String.format("%2s",H+1).replace(' ', '0')+"00";
            }else {
                time= String.format("%2s", H).replace(' ', '0')+"30";
            }
            return time;
        }
    }

     /**
     * 查詢預約單明細資料
     *
     * @param CarBooking
     */
    public  CarBooking selectByApplyId(String applyId) throws Exception{
        CarBooking detail= carBookingDao.selectByApplyId( applyId);
        Optional<Eipcode> codeName= eipcodeDao.findByCodeKindCodeNo("CARPROCESSSTATUS",detail.getCarprocess_status());
        detail.setCodeName(codeName.get().getCodeno()+"-"+codeName.get().getCodename());
        detail.setStarH(StringUtils.substring(detail.getUsing_time_s(),0,2));
        detail.setStarM(StringUtils.substring(detail.getUsing_time_s(),2));
        detail.setEndH(StringUtils.substring(detail.getUsing_time_e(),0,2));
        detail.setEndM(StringUtils.substring(detail.getUsing_time_e(),2));
        detail.setUsing_date(DateUtility.changeDateType(detail.getUsing_date()));
        detail.setApply_date(DateUtility.changeDateType(detail.getApply_date()));
        return detail;
    }

    /**
     * 呼叫報表
     *
     * @param caseData
     */
    public ByteArrayOutputStream print(Eip07w020Case caseData) throws Exception {
        Eip07w020l00 pdf = new Eip07w020l00();
        pdf.createEip07w020DataPdf(caseData.getDetailsList().get(0));
        return pdf.getOutputStream();
    };


    private boolean userIsExist(String userid) {
    	return usersDao.selectByKey(userid) == null ? false:true;
    }

}
