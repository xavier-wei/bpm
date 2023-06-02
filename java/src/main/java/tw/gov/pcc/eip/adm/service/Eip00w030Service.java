package tw.gov.pcc.eip.adm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.adm.cases.Eip00w030Case;
import tw.gov.pcc.eip.dao.SystemsDao;
import tw.gov.pcc.eip.domain.Systems;
import tw.gov.pcc.eip.framework.domain.UserBean;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip00w030Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w030Service.class);
    @Autowired
    private UserBean userData;
    @Autowired
    private SystemsDao systemsDao;
    
    public void initCase(Eip00w030Case eipadm0w030Case) {
    	eipadm0w030Case.setSys_id(null);
    	eipadm0w030Case.setSys_name(null);
    	eipadm0w030Case.setQuerySystems(null);
    	eipadm0w030Case.setUrl(null);
    	eipadm0w030Case.setSort_order(null);
    }
    
    public Systems selectSystemDataBySysid(Eip00w030Case eipadm0w030Case) {
    	return systemsDao.selectByKey(eipadm0w030Case.getSys_id());
    }
    
    public void settingSystemList(Eip00w030Case eipadm0w030Case) {
    	List<Systems> systemList = systemsDao.selectSystemList(eipadm0w030Case.getSys_id());
    	eipadm0w030Case.setSystemLits(systemList);
    }
    
    public void validInsert(Eip00w030Case eipadm0w030Case,BindingResult bindingResult) {
    	if(StringUtils.isBlank(eipadm0w030Case.getSys_id()) || StringUtils.isBlank(eipadm0w030Case.getSys_name()) 
    			|| StringUtils.isBlank(eipadm0w030Case.getUrl()) || StringUtils.isBlank(eipadm0w030Case.getSort_order())) {
    		bindingResult.reject(null, "必輸欄位為空值");
    	}
    }
    
    public void insertSystem(Eip00w030Case eipadm0w030Case) {
    	Systems system = new Systems();
    	system.setSys_id(eipadm0w030Case.getSys_id());
    	system.setItem_id(eipadm0w030Case.getSys_id());
    	system.setSys_name(eipadm0w030Case.getSys_name());
    	system.setUrl(eipadm0w030Case.getUrl());
    	system.setSort_order(new BigDecimal(eipadm0w030Case.getSort_order()));
    	system.setCreate_user_id(userData.getUserId());
    	system.setCreate_timestamp(LocalDateTime.now());
    	
    	systemsDao.insert(system);
    }
    
    public void validUpdate(Eip00w030Case eipadm0w030Case,BindingResult bindingResult) {
    	if( StringUtils.isBlank(eipadm0w030Case.getSys_name()) || StringUtils.isBlank(eipadm0w030Case.getUrl()) 
    			|| StringUtils.isBlank(eipadm0w030Case.getSort_order())) {
    		bindingResult.reject(null, "必輸欄位為空值");
    	}
    }
    
    public void updateSystem(Eip00w030Case eipadm0w030Case) {
    	Systems system = eipadm0w030Case.getQuerySystems();
    	system.setSys_id(eipadm0w030Case.getSys_id());
    	system.setItem_id(eipadm0w030Case.getSys_id());
    	system.setSys_name(eipadm0w030Case.getSys_name());
    	system.setUrl(eipadm0w030Case.getUrl());
    	system.setSort_order(new BigDecimal(eipadm0w030Case.getSort_order()));
    	system.setModify_user_id(userData.getUserId());
    	system.setModify_timestamp(LocalDateTime.now());
    	
    	systemsDao.updateByKey(system);
    }
    
    public void deleteSystem(Eip00w030Case eipadm0w030Case) {
    	Systems system = eipadm0w030Case.getQuerySystems();
    	system.setSys_id(eipadm0w030Case.getSys_id());
    	system.setItem_id(eipadm0w030Case.getSys_id());
    	system.setSys_name(eipadm0w030Case.getSys_name());
    	system.setUrl(eipadm0w030Case.getUrl());
    	system.setSort_order(new BigDecimal(eipadm0w030Case.getSort_order()));
    	system.setModify_user_id(userData.getUserId());
    	system.setModify_timestamp(LocalDateTime.now());
    	
    	systemsDao.deleteByKey(system);
    }
   

}
