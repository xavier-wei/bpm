package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.adm.cases.Eip00w010Case;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.util.DateUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author york
 */
@Service
public class Eip07w010Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w010Service.class);

    @Autowired
    UserBean userData;
    @Autowired
    private User_rolesDao userRolesDao;
    @Autowired
    private UsersDao usersDao;
	@Autowired
	private DriverBaseDao driverBaseDao;
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private CarBaseDao carBaseDao;
    @Autowired
    private CaruseRecDao caruseRecDao;
    @Autowired
    private GasRecDao gasRecDao;
    String sysDateTime = DateUtil.getNowWestDateTime(true);
    String sysDate = sysDateTime.substring(0, 8);

	public void initCase(Eip00w010Case eipadm0w010Case) {
    	
    }

    private boolean userroleIsExist(String userid) {
    	User_roles userroles = userRolesDao.selectByKey(userid, User_rolesDao.SYSTEM_ADMIN_SYS_ID, User_rolesDao.SYSTEM_ADMIN_DEPT_ID, User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    	return userroles == null ? false:true;
    }

    /**
     * 新增駕駛人資料
     *
     * @param caseData
     */
    public void add(Eip07w010Case caseData) throws Exception{

        //取driverid
        List<Eip07w010Case> driveid=driverBaseDao.getDriverid();
        int maxDriverid=0;
        if (driveid.size()>0){
            maxDriverid=Integer.parseInt(StringUtils.substring(driveid.get(0).getDriverid(),2 ))+1;
        }
        else {
        maxDriverid= 1;
        }
        //將carNo拆分成carNo1 carNo2
        caseData.setCarno1(StringUtils.substringBefore(caseData.getCarno(),"-"));
        caseData.setCarno2(StringUtils.substringAfter(caseData.getCarno(),"-"));

       caseData.setDriverid("D"+String.format("%4s", maxDriverid).replace(' ', '0'));
       caseData.setUpdUser(userData.getUserId());
        caseData.setCreUser(userData.getUserId());
        caseData.setCreDatetime(sysDate);
        caseData.setUpdDatetime(sysDate);
        caseData.setBrdte(DateUtility.changeDateType(caseData.getBrdte()));
        caseData.setStartworkDate(DateUtility.changeDateType(caseData.getStartworkDate()));
        caseData.setEndworkDate(DateUtility.changeDateType(caseData.getEndworkDate()));
        caseData.setLicenceExpireDate(DateUtility.changeDateType(caseData.getLicenceExpireDate()));
        driverBaseDao.insert(caseData);
    }

    public List<Eip07w010Case> driveIsExist(Eip07w010Case caseData)throws Exception {
        List<Eip07w010Case> driveIsExist= driverBaseDao.queryDriver(caseData);
        for (Eip07w010Case date:driveIsExist) {
            date.setBrdte(DateUtility.changeDateType(date.getBrdte()));
            date.setStartworkDate(DateUtility.changeDateType(date.getStartworkDate()));
            date.setEndworkDate(DateUtility.changeDateType(date.getEndworkDate()));
            date.setLicenceExpireDate(DateUtility.changeDateType(date.getLicenceExpireDate()));
            date.setCarno(date.getCarno1()+"-"+date.getCarno2());
        }

        return driveIsExist;
    }

    /**
     * 取得下拉選單資料
     *
     * @param caseData
     */
    public void getSelectList(Eip07w010Case caseData) {
        List<Eipcode> getTitle = eipcodeDao.findByCodeKind("DRIVETITLE");
        List<Eipcode> getCarstatus = eipcodeDao.findByCodeKind("CARSTATUS");
        List<Eip07w010Case> tempStaff = driverBaseDao.getTempStaff();
        List<CarBase> carNoList = carBaseDao.getCarno();
        caseData.setTitleList(getTitle);
        caseData.setTempStaffList(tempStaff);
        caseData.setCarnoList(carNoList);
        caseData.setCarstatusList(getCarstatus);
    }



    /**
     * 刪除資料依driverid
     *
     * @param caseData
     */
    public void delete(Eip07w010Case caseData)throws Exception {
        driverBaseDao.delete(caseData);
    }

    /**
     * update資料依driverid
     *
     * @param caseData
     */
    public void updateDriverBase(Eip07w010Case caseData)throws Exception {
        //將carNo拆分成carNo1 carNo2
        caseData.setCarno1(StringUtils.substringBefore(caseData.getCarno(),"-"));
        caseData.setCarno2(StringUtils.substringAfter(caseData.getCarno(),"-"));
        caseData.setUpdUser(userData.getUserId());
        caseData.setUpdDatetime(sysDate);
        caseData.setBrdte(DateUtility.changeDateType(caseData.getBrdte()));
        caseData.setStartworkDate(DateUtility.changeDateType(caseData.getStartworkDate()));
        caseData.setEndworkDate(DateUtility.changeDateType(caseData.getEndworkDate()));
        caseData.setLicenceExpireDate(DateUtility.changeDateType(caseData.getLicenceExpireDate()));
        driverBaseDao.updateDriverBase(caseData);
    }

    /**
     * 新增車籍資料
     *
     * @param caseData
     */
    public void addCarData(Eip07w010Case caseData) throws Exception{

        caseData.setUpdUser(userData.getUserId());
        caseData.setCreUser(userData.getUserId());
        caseData.setCreDatetime(sysDate);
        caseData.setUpdDatetime(sysDate);
        caseData.setInsuranceStart(DateUtility.changeDateType(caseData.getInsuranceStart()));
        caseData.setInsuranceEnd(DateUtility.changeDateType(caseData.getInsuranceEnd()));

        carBaseDao.insert(caseData);
    }

    /**
     * 查詢車籍資料
     *
     * @param data
     */
    public  List<Eip07w010Case> quaryCarBase(Eip07w010Case data) throws Exception{

        List<Eip07w010Case> carData= carBaseDao.quaryCarBase(data);

        for (Eip07w010Case car:carData) {
            car.setInsuranceStart(DateUtility.changeDateType(car.getInsuranceStart()));
            car.setInsuranceEnd(DateUtility.changeDateType(car.getInsuranceEnd()));
            car.setCarYear(String.format("%3s", car.getCarYear()).replace(' ', '0'));
            //判斷是否為首長中文
            if (StringUtils.isNotBlank(car.getBossMk())){
            if ("Y".equals(car.getBossMk())){
                car.setBossMkNm("-是");
            }else {
                car.setBossMkNm("-否");
            }
            }
            //判斷是否為車型中文
                if ("1".equals(car.getCarType())){
                    car.setCarTypeNm("4人座");
                } else if ("2".equals(car.getCarType())) {
                    car.setCarTypeNm("7人座");
                }else {
                    car.setCarTypeNm("其它");
                }
         }



        return carData;
    }

    /**
     * update資料依driverid
     *
     * @param updateData
     */
    public void updateCarBase(Eip07w010Case updateData)throws Exception {
        updateData.setUpdUser(userData.getUserId());
        updateData.setUpdDatetime(sysDate);
        updateData.setInsuranceStart(DateUtility.changeDateType(updateData.getInsuranceStart()));
        updateData.setInsuranceEnd(DateUtility.changeDateType(updateData.getInsuranceEnd()));
        carBaseDao.updateCarBase(updateData);
    }

    /**
     * 取得下拉選單資料
     *
     * @param caseData
     */
    public void getCarDetails(Eip07w010Case caseData)throws Exception {
        List<Eip07w010Case> oilList= new ArrayList<>();
        List<CaruseRec> mileageList= new ArrayList<>();
        mileageList=caruseRecDao.quaryCaruseRec(caseData.getEip07w010QueryDataList().get(0));
        oilList= gasRecDao.quaryGasRec(caseData.getEip07w010QueryDataList().get(0));
        if (mileageList.isEmpty()&&oilList.isEmpty()){
            mileageList=caruseRecDao.quaryCaruseRec(caseData);
            oilList= gasRecDao.quaryGasRec(caseData);
        }
        caseData.setOilList(oilList);
        caseData.setMileageList(mileageList);
    }

    public void gasAdd(GasRec gasData,Eip07w010Case caseData) throws Exception{
        gasData.setCarno1(caseData.getEip07w010CarDataList().get(0).getCarno1());
        gasData.setCarno2(caseData.getEip07w010CarDataList().get(0).getCarno2());
        gasData.setFuel_date(DateUtility.changeDateType(gasData.getFuel_date()));
        gasData.setFuel_time(caseData.getGasH()+caseData.getGasM());
        gasData.setUpd_user(userData.getUserId());
        gasData.setCre_user(userData.getUserId());
        gasData.setSys_time(DateUtil.getNowWestDateTime(true));
        gasData.setCre_datetime(sysDate);
        gasData.setUpd_datetime(sysDate);
        gasRecDao.insert(gasData);
        gasData.setFuel_date(DateUtility.changeDateType(gasData.getFuel_date()));
    }

    /**
     * 刪除資料依driverid
     *
     * @param caseData
     */
    public void deleteCar(Eip07w010Case caseData)throws Exception {
        carBaseDao.delete(caseData);
    }

    /**
     * 時1~23
     * 分1~59
     *
     * @param caseData
     */
    public void getTimeList(Eip07w010Case caseData) {
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


    private boolean userIsExist(String userid) {
    	return usersDao.selectByKey(userid) == null ? false:true;
    }

}
