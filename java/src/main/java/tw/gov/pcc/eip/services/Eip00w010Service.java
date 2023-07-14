package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.adm.cases.Eip00w010Case;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.framework.domain.UserBean;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip00w010Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w010Service.class);

    @Autowired
    UserBean userData;
    @Autowired
    private User_rolesDao userRolesDao;
    @Autowired
    private UsersDao usersDao;  
    
    public void initCase(Eip00w010Case eipadm0w010Case) {
    	
    }
    
    /**
     * 主頁面查詢管理員清單
     *
     */
    public List<Users> findAdminUserList() {
    	List<User_roles> usResultsList = userRolesDao.selectDataByRoleId(User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    	
    	List<Users> usersList = new ArrayList<Users>();
    	for(User_roles userroles:usResultsList) {
    		Users userrs = usersDao.selectByKey(userroles.getUser_id());
    		if(userrs != null) {
    			usersList.add(userrs);
    		}
    	}
    
    	return usersList;
    }
    
    /**
     * 刪除系統管理員
     *
     */
    public void deleteAdmin(Eip00w010Case caseData) {
    	List<String> selectuserList = caseData.getSelectedUserid();
    	for(String users:selectuserList) {
    		User_roles userroles = new User_roles();
    		userroles.setUser_id(users);
    		userroles.setSys_id(User_rolesDao.SYSTEM_ADMIN_SYS_ID);
        	userroles.setDept_id(User_rolesDao.SYSTEM_ADMIN_DEPT_ID);
        	userroles.setRole_id(User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    		userRolesDao.deleteByKey(userroles);
    	}
    }
    
    /**
     * 驗證新增系統管理員
     *
     */
    public void insertValidate(Eip00w010Case caseData, BindingResult bindingResult) {
    	if(userIsExist(caseData.getUser_id())) {
        	if(userroleIsExist(caseData.getUser_id())) {
        		bindingResult.reject(null, "此人已是系統管理員");
        	}
    	}else {
    		bindingResult.reject(null, "錯誤的員工編號");
    	}
    }
    
    /**
     * 新增系統管理員
     *
     */
    public void insertAdmin(Eip00w010Case caseData) {
    	User_roles userroles = new User_roles();
    	userroles.setUser_id(caseData.getUser_id());
    	userroles.setSys_id(User_rolesDao.SYSTEM_ADMIN_SYS_ID);
    	userroles.setDept_id(User_rolesDao.SYSTEM_ADMIN_DEPT_ID);
    	userroles.setRole_id(User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    	userroles.setCreate_user_id(userData.getUserId());
    	userroles.setCreate_timestamp(LocalDateTime.now());
    	userRolesDao.insert(userroles);
    }
    
    private boolean userroleIsExist(String userid) {
    	User_roles userroles = userRolesDao.selectByKey(userid, User_rolesDao.SYSTEM_ADMIN_SYS_ID, User_rolesDao.SYSTEM_ADMIN_DEPT_ID, User_rolesDao.SYSTEM_ADMIN_ROLE_ID);
    	return userroles == null ? false:true;
    }
    
    private boolean userIsExist(String userid) {
    	return usersDao.selectByKey(userid) == null ? false:true;
    }

}
