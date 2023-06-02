package tw.gov.pcc.eip.adm.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.adm.cases.Eip00w020Case;
import tw.gov.pcc.eip.dao.UsersDao;
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
    
    public void init(Eip00w020Case eip00w020Case) {
    	eip00w020Case.setUserid(null);
    	eip00w020Case.setDeptid(null);
    }
    
    /**
     * 主頁面查詢管理員清單
     *
     */
    public void findUserList(Eip00w020Case eip00w020Case) {
    	eip00w020Case.setUserList(usersDao.selectDataByUserIdAndDeptId(eip00w020Case.getUserid(),eip00w020Case.getDeptid()));
    }
    
    /**
     * 主頁面查詢管理員清單
     *
     */
    public Users findUser(Eip00w020Case eip00w020Case) {
    	return usersDao.selectByKey(eip00w020Case.getUserid());
    }
    
    /**
     *
     *
     */
    public void updateUsers(Eip00w020Case eip00w020Case) {
    	Users users = new Users();
    	if(StringUtils.equals("Y", eip00w020Case.getUserStatus())) {
    		users.setAcnt_is_valid("Y");
    	}else {
    		users.setAcnt_is_valid("N");
    	}
    	users.setUser_id(eip00w020Case.getUserid());
    	users.setModify_user_id(userData.getUserId());
    	users.setModify_timestamp(LocalDateTime.now());
    	usersDao.updateAcntisvalidByKey(users);
    }
    
}
