package tw.gov.pcc.eip.orderCar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.dao.RoitemDao;
import tw.gov.pcc.common.domain.Roitem;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.apply.report.Eip08w060l00;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.util.DateUtility;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 
 *
 * @author ivan
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
    String sysDateTime = DateUtil.getNowWestDateTime(true);
    String sysDate = sysDateTime.substring(0, 8);


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
       int count= driverBaseDao.getDriverid().size()+1;
       caseData.setDriverid("D"+String.format("%4s", count).replace(' ', '0'));
       caseData.setUpdUser(userData.getUserId());
        caseData.setCreUser(userData.getUserId());
        caseData.setCreDatetime(sysDate);
        caseData.setUpdDatetime(sysDate);
        driverBaseDao.insert(caseData);
    }

    public List<Eip07w010Case> driveIsExist(Eip07w010Case caseData)throws Exception {
        List<Eip07w010Case> driveIsExist= driverBaseDao.queryDriver(caseData);
        return driveIsExist;
    }

    /**
     * 取得下拉選單資料
     *
     * @param caseData
     */
    public void getSelectList(Eip07w010Case caseData) {
        List<Eipcode> getTitle = eipcodeDao.findByCodeKind("TITLE");
        List<Eip07w010Case> tempStaff = driverBaseDao.getTempStaff();
        caseData.setTitleList(getTitle);
        caseData.setTempStaffList(tempStaff);
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
        caseData.setUpdUser(userData.getUserId());
        caseData.setUpdDatetime(sysDate);
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
        carBaseDao.insert(caseData);
    }


    private boolean userIsExist(String userid) {
    	return usersDao.selectByKey(userid) == null ? false:true;
    }

}
