package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.User_auth_deptDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.User_auth_dept;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w030Case;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 派車預約暨派車結果查詢作業Service
 *
 * @author ivy
 */
@Service
public class Eip07w030Service {

	@Autowired
	UserBean userData;
	@Autowired
	private EipcodeDao eipcodeDao;
	@Autowired
	private CarBookingDao carBookingDao;
    @Autowired
    private  MailService mailService;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private User_auth_deptDao user_auth_deptDao;
	
	/**
	 * 依照申請日期起迄搜尋審核資料
	 *
	 * @param caseData
	 */
	public void getData(Eip07w030Case caseData) throws Exception {
		//2023.11.28
		//1.除自己部門外，審核者若為上層單位，則要可以看到下層單位提出的申請
		List<String>dept = deptsDao.findAllDeptId(userData.getDeptId()).stream().map(e->e.getDept_id()).collect(Collectors.toList());
		//2.提供部分使用者可以跨部門審核用，若有值，則表示該使用者還要同時審核到別部門的資料。
		List<User_auth_dept>otherdept = user_auth_deptDao.selectByUser_id(userData.getUserId());
		if(CollectionUtils.isNotEmpty(otherdept)){
			for(User_auth_dept de : otherdept) {
				dept.add(de.getDept_id());
			}
		}
		List<CarBooking> list = carBookingDao.selectByApplydate(caseData.getApplydateStart(),
				caseData.getApplydateEnd(),"eip07w030",dept);
		List<Eip07w030Case> dataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CarBooking car : list) {
				Eip07w030Case data = new Eip07w030Case();
				data.setApplyid(car.getApplyid());
				data.setApply_user(car.getApply_user());
				data.setApply_dept(car.getApply_dept());
				data.setUsing_date(car.getUsing_date());
				data.setUsing_time_s(car.getUsing_time_s());
				data.setUsing_time_e(car.getUsing_time_e());
				data.setApply_memo(car.getApply_memo());
				data.setNum_of_people(car.getNum_of_people());
				data.setDestination(car.getDestination());
				Eipcode eipcode = new Eipcode();
				eipcode.setCodekind("CARPROCESSSTATUS");
				eipcode.setCodeno(car.getCarprocess_status());
				Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
				data.setCarprocess_status(code.getCodeno() + "-" + code.getCodename());
				if(Integer.valueOf(car.getUsing_date())<Integer.valueOf(car.getCre_datetime().substring(0,8))) {
					data.setFillmk("Y");
				}
				dataList.add(data);
			}
		}
		caseData.setDataList(dataList);
	}

	/**
	 * 複核資料
	 * 
	 * @param caseData
	 * @return
	 */
	public void updateAll(Eip07w030Case caseData) throws Exception {
		String nowdatetime = DateUtility.getNowWestDateTime(true);

		for (String applyId : caseData.getApplyIdList()) {
			
			CarBooking updateData = carBookingDao.selectByApplyId(applyId);
			if ("agree".equals(caseData.getAgree())) {
				updateData.setCarprocess_status("2");
				updateData.setReconfirm_mk("Y");
			}

			if ("disagree".equals(caseData.getAgree())) {
				updateData.setCarprocess_status("T");
				updateData.setReconfirm_mk("N");
			}
			updateData.setReconfirm_user(userData.getUserId());
			updateData.setReconfirm_date(nowdatetime.substring(0,8));
			updateData.setUpd_user(userData.getUserId());
			updateData.setUpd_datetime(nowdatetime);
			carBookingDao.updateByKey(updateData);
			
			//處理完畢，寄mail給秘書室
			//		@複核後，發email通知秘書處進行派
			//		信件標題:有派車預約申請，請秘書處進行派車處理
			//		信件內容:
			//		派車單號:
			//		申請人:             申請單位:
			//		用車日期
			//		用車時間起:         迄:
			//		用車事由:
	        
	        Optional<Eipcode> codeName= eipcodeDao.findByCodeKindCodeNo("CARPROCESSSTATUSMAIL","1");
	        String mail=codeName.get().getCodename();
	        StringBuffer sb = new StringBuffer();
	        
	        String using_date = DateUtility.formatChineseDateString(updateData.getUsing_date(),false);
	        String using_time = updateData.getUsing_time_s().substring(0,2)+updateData.getUsing_time_s().substring(2,4)+"~"
	    	        +updateData.getUsing_time_e().substring(0,2)+updateData.getUsing_time_e().substring(2,3);
	        
	        sb.append("派車單號：" + applyId +"\r\n");
	        sb.append("申請人:"+ getUserNameOrDeptName(updateData.getApply_user(),true)+"\r\n");
	        sb.append("申請單位:"+ getUserNameOrDeptName(updateData.getApply_dept(),false)+"\r\n");
	        sb.append("用車日期" + using_date +"\r\n");
	        sb.append("用車時間" + using_time +"\r\n");
	        sb.append("用車事由" + updateData.getApply_memo());
	        mailService.sendEmailNow("【有派車預約申請】請秘書處進行派車處理，用車時間："+using_date+"，"+using_time ,mail,sb.toString());
		}
		

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

}