package tw.gov.pcc.eip.apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.dao.RoitemDao;
import tw.gov.pcc.common.domain.Roitem;
import tw.gov.pcc.eip.adm.cases.Eip00w010Case;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.util.List;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip08w060Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w060Service.class);

    @Autowired
    UserBean userData;
    @Autowired
    private User_rolesDao userRolesDao;
    @Autowired
    private UsersDao usersDao;
	@Autowired
	private RoitemDao roitemDao;

	public void initCase(Eip00w010Case eipadm0w010Case) {
    	
    }
	/**
	 * 新增資料
	 *
	 * @param caseData
	 */
	public void add(Eip08w060Case caseData) throws Exception{
//		String sysDateTime = DateUtil.getNowWestDateTime(true);
//		String sysDate = sysDateTime.substring(0, 8);
		//計算流水號
		List<Roitem> count =roitemDao.count(caseData.getApply_date());
		String number = "0"+String.valueOf(count.size()+1);

		int itemNO =0;
		for (Eip08w060Case eip08W060Case :caseData.getEip08w060CaseList()) {
			if ("".equals(eip08W060Case.getItem())){
				break;
			}
			Roitem roitem= new Roitem();
			roitem.setItemid("RO"+caseData.getApply_date()+number);
			roitem.setItemno(String.valueOf(++itemNO));
			roitem.setItem(eip08W060Case.getItem());
			roitem.setDesc_memo(eip08W060Case.getDesc_memo());
			roitem.setCnt(eip08W060Case.getCnt());//要轉int
			roitem.setUnit(eip08W060Case.getUnit());
			roitem.setApply_type(caseData.getApplyTpNm().substring(0,1));//修字串 caseData.getApplyTpNm()
			roitem.setApply_staff(caseData.getUser());
			roitem.setApply_date(caseData.getApplyDate());
			roitem.setTempmk(caseData.getSave());
			roitem.setCre_user(caseData.getUser());
			roitem.setCre_datetime(caseData.getCre_datetime());
			roitem.setUpd_user("");
			roitem.setUpd_datetime("");

			caseData.setItemId(roitem.getItemid());
			roitemDao.insert(roitem);
		}

	}

	/**
	 * 查詢資料
	 *
	 * @param caseData
	 */
	public Eip08w060Case quary(Eip08w060Case caseData) throws Exception{
	caseData.setEip08w060QuaryList(roitemDao.quaryItemId(caseData));
	return caseData;
	}


    

    


    
    private boolean userroleIsExist(String userid) {
    	User_roles userroles = userRolesDao.selectByKey(userid, User_rolesDao.SYSTEM_ADMIN_SYS_ID, User_rolesDao.SYSTEM_ADMIN_DEPT_ID, User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    	return userroles == null ? false:true;
    }

	/**
	 * 刪除資料
	 *
	 * @param caseData
	 */
	public void delete(Eip08w060Case caseData) throws Exception{
			roitemDao.delete(caseData);
		}

	/**
	 * 修改資料
	 *
	 * @param caseData
	 */
	public void update(Eip08w060Case caseData) throws Exception{
		roitemDao.updateRoitem(caseData);
	}


    
    private boolean userIsExist(String userid) {
    	return usersDao.selectByKey(userid) == null ? false:true;
    }

}
