package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Collector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.adm.cases.Eip00w070Case;
import tw.gov.pcc.eip.adm.cases.Eip00w230Case;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip00w070Service {
	
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w070Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    RolesDao rolesDao;
    @Autowired
    PortalMenuAclDao portalMenuAclDao;
    @Autowired
    User_rolesDao user_rolesDao;
    @Autowired
    UsersDao usersDao;
    @Autowired
    ItemsDao itemsDao;
    @Autowired
    Role_aclDao role_aclDao;
	@Autowired
	Pwc_tb_tableau_user_infoDao pwc_tb_tableau_user_infoDao;
	@Autowired
	DeptsDao deptsDao;
	@Autowired
	Eip00w230Service eip00w230Service;
    
    private final String SYS_ID = "EI";
    
    
    public void initCase(Eip00w070Case eipadm0w070Case) {
    	eipadm0w070Case.setRole_id(null);
    	eipadm0w070Case.setRole_desc(null);
    	eipadm0w070Case.setRolesList(null);
    	eipadm0w070Case.setUsersList(null);
    }
    
    /**
     * 主頁面查詢角色清單
     *
     */
    public List<Roles> findRolesList(String role_id) {
    	return rolesDao.selectListLikeRoleid(role_id);
    }
    
    /**
     * 查詢角色功能清單
     *
     */
    public List<CursorAcl> findRoleMenu(Eip00w070Case eipadm0w070Case){
    	return portalMenuAclDao.findRoleAcl(SYS_ID, eipadm0w070Case.getRole_id());
    }
    
    /**
     * 設置角色功能代號list
     *
     */
    public void settingMenuList(Eip00w070Case eipadm0w070Case,List<CursorAcl> cursorAclList){
    	List<String> itemidList = new ArrayList<String>();
    	for(CursorAcl c:cursorAclList) {
    		if("Y".equals(c.getIsChecked()) ) {
    			itemidList.add(c.getItemId());
    		}
    	}
    	eipadm0w070Case.setSelectedIdlist(itemidList);
    }
    
    public void findMember(Eip00w070Case eipadm0w070Case){
    	
    	List<User_roles> rsList = user_rolesDao.selectDataByRoleId(eipadm0w070Case.getRole_id());
    	List<Users> usersList = usersDao.selectAll();
    	
    	//查詢部門中文名稱
    	for(Users u:usersList) {
    		List<Depts> deptsList = deptsDao.findByDeptid(u.getDept_id());
    		if(CollectionUtils.isNotEmpty(deptsList)) {
    			u.setDept_cname(deptsList.get(0).getDept_name());
    		}
    	} 
    	//設定checkbox
    	for(Users u:usersList) {
    		for(User_roles ur:rsList) {
    			if(StringUtils.equals(u.getUser_id(),ur.getUser_id())) {
    				u.setCheckbox(true);
    			}
    		}
    	}
    	

    	eipadm0w070Case.setUsersList(usersList);
    }
    
    public void findDeptList(Eip00w070Case eipadm0w070Case){
    	List<Depts> deptList = deptsDao.findByDeptid(null);
    	eipadm0w070Case.setDeptsList(deptList);
    }
    
    public void updMember(Eip00w070Case eipadm0w070Case) {
    	List<Users> usersList = eipadm0w070Case.getUsersList();
    	String roleId = eipadm0w070Case.getRole_id();
    	LocalDateTime nowldt = LocalDateTime.now();
		List<Users> orgUsersList = getUsersByRole(roleId);
		
    	for(Users users:usersList) {
    		//如果user有被勾選擇加入群組
    		if(users.isCheckbox()) {
    			
    			//如果userRoles沒有,則進行新增
    			if(!exsistUserRoles(users.getUser_id(),SYS_ID,StringUtils.defaultIfBlank(users.getDept_id(), Depts.DEFAULT),roleId)) {
        			User_roles addUserRoles = new User_roles();
        			addUserRoles.setUser_id(users.getUser_id());
        			addUserRoles.setSys_id(SYS_ID);
        			addUserRoles.setDept_id(StringUtils.defaultIfBlank(users.getDept_id(), Depts.DEFAULT));
        			addUserRoles.setRole_id(roleId);
        			addUserRoles.setCreate_user_id(userData.getUserId());
        			addUserRoles.setCreate_timestamp(nowldt);
        			user_rolesDao.insert(addUserRoles);
    			}
    			
    		}else {
    			User_roles delUserRoles = new User_roles();
    			delUserRoles.setUser_id(users.getUser_id());
    			delUserRoles.setSys_id(SYS_ID);
    			delUserRoles.setDept_id(StringUtils.defaultIfBlank(users.getDept_id(), Depts.DEFAULT));
    			delUserRoles.setRole_id(roleId);
    			user_rolesDao.deleteByKey(delUserRoles);
    		}
    	}
		orgUsersList.forEach(x -> deleteUnauthorizedTabListByUserId(x.getUser_id()));
    }
    
    /*
     * 新增角色
     */
    public void insertValid(Eip00w070Case eipadm0w070Case, BindingResult bindingResult) {
    	List<Roles> roless = findRolesList(eipadm0w070Case.getRole_id());
    	if(CollectionUtils.isNotEmpty(roless)) {
    		bindingResult.reject(null, "已有此角色代號");
    	}
    }
    
    /*
     * 新增角色
     */
    public void insertCharacter(Eip00w070Case eipadm0w070Case) {
    	LocalDateTime nowldt = LocalDateTime.now();
    	//新增roles
    	this.insertRoles(SYS_ID, userData.getDeptId(), eipadm0w070Case.getRole_id(), eipadm0w070Case.getRole_desc(), userData.getUserId(), nowldt);
    	//新增role_acl
    	this.insertRoleAcl(eipadm0w070Case.getSelectedIdlist(), SYS_ID, eipadm0w070Case.getRole_id(), userData.getDeptId(), userData.getUserId(), nowldt);
    }
    
    /*
     * 編輯角色
     */
    public void editCharacter(Eip00w070Case eipadm0w070Case) {
    	LocalDateTime nowldt = LocalDateTime.now();
		List<Users> orgUsersList = getUsersByRole(eipadm0w070Case.getRole_id());
		
    	//刪除舊的role_acl
    	this.deleteRoleAclByRoleid(eipadm0w070Case.getRole_id());
    	//新增新的role_acl
    	this.insertRoleAcl(eipadm0w070Case.getSelectedIdlist(), SYS_ID, eipadm0w070Case.getRole_id(), userData.getDeptId(), userData.getUserId(), nowldt);

		orgUsersList.forEach(x -> deleteUnauthorizedTabListByUserId(x.getUser_id()));
    }
	
	private List<Users> getUsersByRole(String roleId) {
		Eip00w070Case resultCase = new Eip00w070Case();
		resultCase.setRole_id(roleId);
		this.findMember(resultCase);
		return resultCase.getUsersList().stream().filter(Users::isCheckbox).collect(Collectors.toList());
	}

	private void deleteUnauthorizedTabListByUserId(String userId) {
		Eip00w230Case resultCase = new Eip00w230Case();
		eip00w230Service.findCheckedList(resultCase, userId);
		Set<String> allIds = resultCase.getFullTabList().stream().map(Eip00w230Case.TabCase::getDashboard_fig_id).collect(Collectors.toSet());
		//系統管理者不動
		if (user_rolesDao.selectByKey(userId, User_rolesDao.SYSTEM_ADMIN_SYS_ID, User_rolesDao.SYSTEM_ADMIN_DEPT_ID, User_rolesDao.SYSTEM_ADMIN_ROLE_ID) != null) {
			return;
		}
		pwc_tb_tableau_user_infoDao.findUnauthoriedList().stream().filter(x -> allIds.contains(x.getDashboard_fig_id()) && StringUtils.equalsIgnoreCase(x.getUser_id(), userId)).forEach(pwc_tb_tableau_user_infoDao::deleteByKey);
	}

	/*
     * 刪除角色
     */
    public void deleteCharacter(Eip00w070Case eipadm0w070Case) {
    	this.deleteRolesByRoleid(eipadm0w070Case.getRole_id());
    	this.deleteRoleAclByRoleid(eipadm0w070Case.getRole_id());
		this.deleteUnauthorizedTabListByUserId(eipadm0w070Case.getRole_id());
    }
    	
    private void insertRoles(String sys_id, String dept_id, String role_id, String role_desc, String create_id, LocalDateTime nowldt) {
    	Roles roles = new Roles();
    	roles.setSys_id(sys_id);
    	roles.setDept_id(dept_id);
    	roles.setRole_id(role_id);
    	roles.setRole_desc(role_desc);
    	roles.setCreate_user_id(create_id);
    	roles.setCreate_timestamp(nowldt);
    	roles.setModify_user_id(create_id);
    	roles.setModify_timestamp(nowldt);
    	rolesDao.insert(roles);
    }
    
    private void deleteRolesByRoleid(String role_id) {
    	Roles roles = new Roles();
    	roles.setRole_id(role_id);
    	rolesDao.deleteByKey(roles);
    }
    
    private void insertRoleAcl(List<String> itemIdList, String sys_id, String Role_id, String dept_id, String create_id, LocalDateTime nowldt) {
    	itemIdList.stream().forEach(itemid -> 
    								{    Role_acl roleacl = new Role_acl();
		    		                	 roleacl.setSys_id(sys_id);
		    		                	 roleacl.setRole_id(Role_id);
		    		                	 roleacl.setDept_id(dept_id);
		    		                	 roleacl.setItem_id(itemid);
		    		                	 roleacl.setCreate_user_id(create_id);
		    		                	 roleacl.setCreate_timestamp(nowldt);
		    		                	 role_aclDao.insert(roleacl);
    	                            });
    }
    
    private void deleteRoleAclByRoleid(String role_id) {
    	Role_acl role_acl = new Role_acl();
    	role_acl.setRole_id(role_id);
    	role_aclDao.deleteByRoleid(role_acl);
    }
    
    private boolean exsistUserRoles(String user_id, String sys_id, String dept_id, String role_id) {
    	return user_rolesDao.selectByKey(user_id, sys_id, dept_id, role_id) != null ? true: false;
    }
}
