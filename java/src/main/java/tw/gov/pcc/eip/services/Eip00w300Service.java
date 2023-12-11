package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.adm.cases.Eip00w300Case;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.User_auth_deptDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.domain.User_auth_dept;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip00w300Service {
	
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w300Service.class);
    @Autowired
    private UserBean userData;
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private DeptsDao deptsDao;
    @Autowired
    private User_auth_deptDao user_auth_deptDao;
    
    public void initcase(Eip00w300Case eip00w300Case){
		eip00w300Case.setUser_id(null);
    	eip00w300Case.setDept_id(null);
    	eip00w300Case.setQueryUser_auth_dept(null);
    }

	public List<User_auth_dept> queryUser_auth_dept(String userid, String deptid){
    	return user_auth_deptDao.selectByUser_id_OR_Dept_id(userid, deptid);
    }

	public void buildEditCaseData(Eip00w300Case eip00w300Case, User_auth_dept resultDepts){
    	eip00w300Case.setQueryUser_auth_dept(resultDepts);
    	eip00w300Case.setDept_id(resultDepts.getDept_id());
    }
    
    public void validInsert(Eip00w300Case eipadm0w300Case, BindingResult bindingResult) {
    	if(StringUtils.isBlank(eipadm0w300Case.getUser_id()) || StringUtils.isBlank(eipadm0w300Case.getDept_id()) ) {
    		bindingResult.reject(null, "必輸欄位為空值");
    	}
//    	List<User_auth_dept> rs = queryDepts(eipadm0w300Case.getDept_id());
//    	if(!CollectionUtil.isEmpty(rs)) {
//    		bindingResult.reject(null, "此部門代號已存在");
//    	}
    }    

    public void insertUser_auth_dept(Eip00w300Case eipadm0w300Case) {
//    	LocalDateTime ldtnow = LocalDateTime.now();
		User_auth_dept user_auth_dept = new User_auth_dept();
		user_auth_dept.setUser_id(eipadm0w300Case.getUser_id());
		user_auth_dept.setDept_id(eipadm0w300Case.getDept_id());
//    	user_auth_dept.setCreate_user_id(userData.getUserId());
//    	user_auth_dept.setCreate_timestamp(ldtnow);
//    	user_auth_dept.setModify_user_id(userData.getUserId());
//    	user_auth_dept.setModify_timestamp(ldtnow);
		user_auth_deptDao.insert(user_auth_dept);
    }

    public void deleteDepts(Eip00w300Case eipadm0w300Case) {
		User_auth_dept delUser_auth_dept = new User_auth_dept();
		delUser_auth_dept.setUser_id(eipadm0w300Case.getUser_id());
		delUser_auth_dept.setDept_id(eipadm0w300Case.getDept_id());
		user_auth_deptDao.deleteByKey(delUser_auth_dept);
    }

	/**
	 * 初始化下拉選單
	 *
	 * @param caseData
	 */
	public void initOptions(Eip00w300Case caseData) {

		List<Depts> depts = deptsDao.findByDeptid(null);
		// 聯絡單位
		List<Eip00w300Case.Option> unitOptions = depts.stream().filter(f -> !"00".equals(f.getDept_id())).map(m -> {
			Eip00w300Case.Option option = new Eip00w300Case.Option();
			option.setCodeno(m.getDept_id());
			option.setCodename(m.getDept_name());
			return option;
		}).collect(Collectors.toCollection(ArrayList::new));
		caseData.setUnitOptions(unitOptions);

		// 聯絡人
		List<Eip00w300Case.Option> users = usersDao.selectAll().stream().filter(f -> "Y".equals(f.getAcnt_is_valid()))
				.map(m -> {
					Eip00w300Case.Option option = new Eip00w300Case.Option();
					option.setCodeno(m.getUser_id());
					option.setCodename(m.getUser_name());
					return option;
				}).collect(Collectors.toCollection(ArrayList::new));
		caseData.setPersonOptions(users);

		//授權審核部門代號
		caseData.setDeptList(deptsDao.findByDeptid(null));

	}

	/**
	 * 依畫面選擇部門 查詢使用者
	 *
	 * @param dept
	 * @return
	 */
	public Map<String, String> getUsers(String dept) {
		Map<String, String> map = usersDao.getDeptUsers(dept).stream()
				.collect(Collectors.toMap(Users::getUser_id, Users::getUser_name, (n, o) -> n, LinkedHashMap::new));
		return ObjectUtility.normalizeObject(map);
	}
}
