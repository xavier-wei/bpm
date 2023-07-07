package tw.gov.pcc.eip.adm.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.adm.cases.Eip00w020Case;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Eipcode;
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
    	}
    	
    	users.setEmail(eip00w020Case.getEmail());
    	users.setLine_token(eip00w020Case.getLine_token());
    	users.setModify_user_id(userData.getUserId());
    	users.setModify_timestamp(LocalDateTime.now());
    	usersDao.updateByKey(users);
    }
    
    public List<Eipcode> findTitleIdList(){
    	return eipcodeDao.findByCodeKind("TITLE");
    }
    
    public List<Eipcode> findDeptIdList(){
    	return eipcodeDao.findByCodeKind("DEPT");
    }
    
}
