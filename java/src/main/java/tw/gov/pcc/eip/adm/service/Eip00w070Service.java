package tw.gov.pcc.eip.adm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.adm.cases.Eip00w070Case;
import tw.gov.pcc.eip.dao.PortalMenuAclDao;
import tw.gov.pcc.eip.dao.RolesDao;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.CursorDeptAcl;
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
    User_rolesDao userrolesDao;
    @Autowired
    UsersDao usersDao;
    
    /**
     * 主頁面查詢角色清單
     *
     */
    public List<Roles> findRolesList(String role_id) {
    	return rolesDao.selectDataList(role_id);
    }
    
    public List<CursorDeptAcl> findMenu(){
    	return portalMenuAclDao.findDeptAcl("EI", "");
    }
    
    public void findMember(Eip00w070Case eipadm0w070Case){
    	
    	List<User_roles> rsList = userrolesDao.selectDataByRoleId(eipadm0w070Case.getRole_id());
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
    	

}
