package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.adm.cases.Eip00w020Case;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.RolesDao;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Roles;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.framework.domain.UserBean;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip00w020Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w020Service.class);

    @Autowired
    UserBean userData;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private User_rolesDao user_rolesDao;
    @Autowired
    private RolesDao rolesDao;
    @Autowired
    private DeptsDao deptsDao;
    
    public void init(Eip00w020Case eip00w020Case) {
    	eip00w020Case.setUser_id(null);
    	eip00w020Case.setDept_id(null);
    }
    
    /**
     * 主頁面查詢管理員清單
     *
     */
    public void findUserList(Eip00w020Case eip00w020Case) {
    	eip00w020Case.setUserList(usersDao.selectDataByUserIdAndDeptId(eip00w020Case.getUser_id(),eip00w020Case.getDept_id()));
    }
    
    /**
     * 主頁面查詢管理員清單
     *
     */
    public Users findUser(Eip00w020Case eip00w020Case) {
    	return usersDao.selectByKey(eip00w020Case.getUser_id());
    }
    
    
    /**
     * 查詢可新增角色清單
     *
     */
    public List<Roles> findAddRolesList(Eip00w020Case eip00w020Case) {
    	
    	//查詢出所有角色-此userid已有角色
    	List<Roles> allRoles = rolesDao.selectDataList(null);
    	List<String> userroleList = user_rolesDao.selectDataByUserId(eip00w020Case.getUser_id())
    			                    .stream().map(m -> m.getRole_id()).collect(Collectors.toCollection(ArrayList::new));
    	
    	return allRoles.stream().filter(a -> !userroleList.contains(a.getRole_id())).collect(Collectors.toList());
    }
    
    /**
     * 取得英文名字
     *
     */
    public String findEngName(String email) {
    	
    	if(StringUtils.isBlank(email)) {
    		return StringUtils.EMPTY;
    	}else {
    		String[] strings = email.split("@");
    		return strings[0];
    	}
    }
    
    /**
     * 取得部門名字
     *
     */
    public String findDeptName(String dept_id) {
    	
    	if(StringUtils.isBlank(dept_id)) {
    		return StringUtils.EMPTY;
    	}else {
    		List<Depts> depts = deptsDao.findByDeptid(dept_id);
    		return CollectionUtils.isNotEmpty(depts)?depts.get(0).getDept_name():"";
    	}
    }
    
    /**
     *
     * 更新users資料
     */
    public void updateUsers(Eip00w020Case eip00w020Case) {
    	Users users = usersDao.selectByKey(eip00w020Case.getUsers().getUser_id());
    	String from_hr = users.getFrom_hr();
    	
    	if(StringUtils.equals("Y", eip00w020Case.getUserStatus())) {
    		users.setAcnt_is_valid("Y");
    	}else {
    		users.setAcnt_is_valid("N");
    	}
    	
    	//users.from_hr != Y 才可以更新特定欄位
    	if(!StringUtils.equals(from_hr, "Y")) {
    		users.setDept_id(eip00w020Case.getDept_id());
    		users.setEmp_id(eip00w020Case.getEmp_id());
    		users.setUser_name(eip00w020Case.getUser_name());
    		users.setTel1(eip00w020Case.getTel1());
    		users.setTel2(eip00w020Case.getTel2());
    		users.setTitle_id(eip00w020Case.getTitle_id());
    		users.setEmail(eip00w020Case.getEmail());
    	}
    	
        //users.setLine_token(eip00w020Case.getLine_token());
    	users.setModify_user_id(userData.getUserId());
    	users.setModify_timestamp(LocalDateTime.now());
    	usersDao.updateByKey(users);
    }
    
    /**
    *
    * 刪除user_roles資料
    */
    public void deleteUserRoles(Eip00w020Case eip00w020Case) {
	    Users user = eip00w020Case.getUsers();
	    String deleteRoleid = eip00w020Case.getDelRoleId();
	   
	    User_roles userroles  = new User_roles();
	    userroles.setUser_id(user.getUser_id());
	    userroles.setSys_id("EI");
	    userroles.setDept_id(user.getDept_id());
	    userroles.setRole_id(deleteRoleid);
	    user_rolesDao.deleteByKey(userroles);
    }
    
    /**
    *
    * 新增user_roles資料
    */
    public void addUserRoles(Eip00w020Case eip00w020Case) {
	    Users user = eip00w020Case.getUsers();
	    String addRoleid = eip00w020Case.getAddRoleid();
	    if(StringUtils.isNotBlank(addRoleid)) {
			User_roles addUserRoles = new User_roles();
			addUserRoles.setUser_id(user.getUser_id());
			addUserRoles.setSys_id("EI");
			addUserRoles.setDept_id(user.getDept_id());
			addUserRoles.setRole_id(addRoleid);
			addUserRoles.setCreate_user_id(userData.getUserId());
			addUserRoles.setCreate_timestamp(LocalDateTime.now());
			user_rolesDao.insert(addUserRoles);
	    }
    }
    
    public List<Eipcode> findTitleIdList(){
    	return eipcodeDao.findByCodeKind("TITLE");
    }
    
    public List<Depts> findDeptIdList(){
    	return deptsDao.findByDeptid(null);
    }
    
    //查詢此user_id底下有哪些roles
    public List<Roles> findUserRoleList(String user_id){
    	List<User_roles> userroles= user_rolesDao.selectDataByUserId(user_id);
    	List<Roles> rolesList = new ArrayList<Roles>();
    	for(User_roles ur:userroles) {
    		List<Roles> roless = rolesDao.selectDataList(ur.getRole_id());
    		rolesList.addAll(roless);
    	}
    	return rolesList;
    }
    
}
