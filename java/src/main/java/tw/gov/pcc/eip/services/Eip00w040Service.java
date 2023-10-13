package tw.gov.pcc.eip.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.adm.cases.Eip00w040Case;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.framework.domain.UserBean;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip00w040Service {
	
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w040Service.class);
    @Autowired
    private UserBean userData;
    @Autowired
    private DeptsDao deptsDao;
    
    public void initcase(Eip00w040Case eip00w040Case){
    	eip00w040Case.setDept_id(null);
    	eip00w040Case.setQueryDepts(null);
    }
  
    public List<Depts> queryDepts(String deptid){
    	return deptsDao.findByLikeDeptid(deptid);
    }
    
    public void buildEditCaseData(Eip00w040Case eip00w040Case, Depts resultDepts){
    	eip00w040Case.setQueryDepts(resultDepts);
    	eip00w040Case.setDept_id(resultDepts.getDept_id());
    	eip00w040Case.setDept_name(resultDepts.getDept_name());
    	eip00w040Case.setDept_desc(resultDepts.getDept_desc());
    }
    
    public void validInsert(Eip00w040Case eipadm0w040Case, BindingResult bindingResult) {
    	if(StringUtils.isBlank(eipadm0w040Case.getDept_id()) || StringUtils.isBlank(eipadm0w040Case.getDept_name()) ) {
    		bindingResult.reject(null, "必輸欄位為空值");
    	}
    	List<Depts> rs = queryDepts(eipadm0w040Case.getDept_id());
    	if(!CollectionUtil.isEmpty(rs)) {
    		bindingResult.reject(null, "此部門代號已存在");
    	}
    }    
    
    public void validUpdate(Eip00w040Case eipadm0w040Case, BindingResult bindingResult) {
    	if(StringUtils.isBlank(eipadm0w040Case.getDept_id()) || StringUtils.isBlank(eipadm0w040Case.getDept_name()) ) {
    		bindingResult.reject(null, "必輸欄位為空值");
    	}
    } 

    public void validDelete(Eip00w040Case eipadm0w040Case, BindingResult bindingResult) {
    	Depts depts = queryDepts(eipadm0w040Case.getQueryDepts().getDept_id()).get(0);
    	if(StringUtils.equals(depts.getFrom_hr(), "Y")) {
    		bindingResult.reject(null, "此部門不可刪除");
    	}
    }
    
    public void insertDepts(Eip00w040Case eipadm0w040Case) {
    	LocalDateTime ldtnow = LocalDateTime.now();
    	Depts depts = new Depts();
    	depts.setDept_id(eipadm0w040Case.getDept_id());
    	depts.setDept_name(eipadm0w040Case.getDept_name());
    	depts.setDept_desc(eipadm0w040Case.getDept_desc());
    	depts.setCreate_user_id(userData.getUserId());
    	depts.setCreate_timestamp(ldtnow);
    	depts.setModify_user_id(userData.getUserId());
    	depts.setModify_timestamp(ldtnow);
    	deptsDao.insert(depts);
    }
    
    public void updateDepts(Eip00w040Case eipadm0w040Case) {
    	LocalDateTime ldtnow = LocalDateTime.now();
		List<Depts> rs = queryDepts(eipadm0w040Case.getQueryDepts().getDept_id());
    	Depts updDepts = rs.get(0);
    	updDepts.setDept_id(eipadm0w040Case.getDept_id());
    	updDepts.setDept_name(eipadm0w040Case.getDept_name());
    	updDepts.setDept_desc(eipadm0w040Case.getDept_desc());
    	updDepts.setModify_user_id(userData.getUserId());
    	updDepts.setModify_timestamp(ldtnow);
    	deptsDao.updateByKey(updDepts);
    }
    
    public void deleteDepts(Eip00w040Case eipadm0w040Case) {
    	Depts delDepts = new Depts();
    	delDepts.setDept_id(eipadm0w040Case.getDept_id());
    	deptsDao.deleteByKey(delDepts);
    }


}
