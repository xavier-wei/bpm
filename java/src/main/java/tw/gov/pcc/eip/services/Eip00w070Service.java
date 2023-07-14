package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.adm.cases.Eip00w070Case;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.dao.PortalMenuAclDao;
import tw.gov.pcc.eip.dao.Role_aclDao;
import tw.gov.pcc.eip.dao.RolesDao;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.CursorAcl;
import tw.gov.pcc.eip.domain.Role_acl;
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
    	return rolesDao.selectDataList(role_id);
    }
    
    /**
     * 查詢角色功能清單
     *
     */
    public List<CursorAcl> findRoleMenu(Eip00w070Case eipadm0w070Case){
    	return portalMenuAclDao.findRoleAcl(SYS_ID, eipadm0w070Case.getRole_id());
    }
    
    public void findMember(Eip00w070Case eipadm0w070Case){
    	
    	List<User_roles> rsList = user_rolesDao.selectDataByRoleId(eipadm0w070Case.getRole_id());
    	List<Users> usersList = usersDao.selectAll();
    	
    	for(Users u:usersList) {
    		for(User_roles ur:rsList) {
    			if(StringUtils.equals(u.getUser_id(),ur.getUser_id())) {
    				u.setCheckbox(true);
    			}
    		}
    	}

    	eipadm0w070Case.setUsersList(usersList);
    }
    
    public void updMember(Eip00w070Case eipadm0w070Case) {
    	List<Users> usersList = eipadm0w070Case.getUsersList();
    	String roleId = eipadm0w070Case.getRole_id();
    	LocalDateTime nowldt = LocalDateTime.now();
    	
    	for(Users users:usersList) {
    		//如果user有被勾選擇加入群組
    		if(users.isCheckbox()) {
    			
    			//如果userRoles沒有,則進行新增
    			if(!exsistUserRoles(users.getUser_id(),SYS_ID,users.getDept_id(),roleId)) {
        			User_roles addUserRoles = new User_roles();
        			addUserRoles.setUser_id(users.getUser_id());
        			addUserRoles.setSys_id(SYS_ID);
        			addUserRoles.setDept_id(users.getDept_id());
        			addUserRoles.setRole_id(roleId);
        			addUserRoles.setCreate_user_id(userData.getUserId());
        			addUserRoles.setCreate_timestamp(nowldt);
        			user_rolesDao.insert(addUserRoles);
    			}
    			
    		}else {
    			User_roles delUserRoles = new User_roles();
    			delUserRoles.setUser_id(users.getUser_id());
    			delUserRoles.setSys_id(SYS_ID);
    			delUserRoles.setDept_id(users.getDept_id());
    			delUserRoles.setRole_id(roleId);
    			user_rolesDao.deleteByKey(delUserRoles);
    		}
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
    	//刪除舊的role_acl
    	this.deleteRoleAclByRoleid(eipadm0w070Case.getRole_id());
    	//新增新的role_acl
    	this.insertRoleAcl(eipadm0w070Case.getSelectedIdlist(), SYS_ID, eipadm0w070Case.getRole_id(), userData.getDeptId(), userData.getUserId(), nowldt);
    }
    
    /*
     * 刪除角色
     */
    public void deleteCharacter(Eip00w070Case eipadm0w070Case) {
    	this.deleteRolesByRoleid(eipadm0w070Case.getRole_id());
    	this.deleteRoleAclByRoleid(eipadm0w070Case.getRole_id());
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
