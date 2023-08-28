package tw.gov.pcc.eip.services;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.apply.cases.Eip08w010Case;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 
 *
 * @author ivan
 */
@Service
public class Eip08w010Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w010Service.class);

    @Autowired
    UserBean userData;
    @Autowired
    ItemcodeDao itemcodeDao;
    @Autowired
    ItemcodeService itemcodeService;
    
    public void initCase(Eip08w010Case caseData) {
    	caseData.setMainKindList(null);
    	caseData.setDetailKindList(null);
    	caseData.setDetailkindno(null);
    	caseData.setMainkindno(null);
    }
    
    public List<Itemcode> findMainKindList(){
    	return itemcodeDao.findByItemkind("MAIN");
    }
   
    public List<Itemcode> findDetailKindList(String detailKind){
    	return itemcodeDao.findByItemkind(detailKind);
    }
    
    public List<Itemcode> findMainKindList(String mainitemno){
    	Itemcode itemcode=  new Itemcode();
    	if(StringUtils.isNotBlank(mainitemno)) {
    		itemcode.setItemkind("MAIN");
    		itemcode.setItemno(mainitemno);
    	}else {
    		itemcode.setItemkind("MAIN");
    	}
    	return itemcodeDao.selectDataListByKey(itemcode);
    }
    
    public List<Itemcode> findItemListCodeBykey(String itemkind,String itemno){
    	Itemcode itemcode = new Itemcode();
    	itemcode.setItemkind(itemkind);
    	itemcode.setItemno(itemno);
    	return itemcodeDao.selectDataListByKey(itemcode);
    }
    
    public List<Itemcode> findDetailKindList(Eip08w010Case caseData){
    	Itemcode itemcode=  new Itemcode();
    	itemcode.setItemkind(caseData.getMainkindno());
    	if(StringUtils.isNotBlank(caseData.getDetailkindno())) {
    		itemcode.setItemno(caseData.getDetailkindno());
    	}
    	return itemcodeDao.selectDataListByKey(itemcode);
    }
    
    public void validInsertMainItemcode(Eip08w010Case caseData, BindingResult bindingResult) {
    	if(StringUtils.isBlank(caseData.getAddMainItemno())) {
    		bindingResult.reject(null, "品名大類代號不可為空白");
    	}
    	if(StringUtils.isBlank(caseData.getAddMainItemname())) {
    		bindingResult.reject(null, "品名大類不可為空白");
    	}
    	List<Itemcode> rslist = findMainKindList(caseData.getAddMainItemno());
    	if(CollectionUtils.isNotEmpty(rslist)) {
    		bindingResult.reject(null, "此品名大類代號已存在");
    	}
    }
    
    public void validInsertDetailItemcode(Eip08w010Case caseData, BindingResult bindingResult) {
    	if(StringUtils.isBlank(caseData.getAddDetailItemno())) {
    		bindingResult.reject(null, "品名代號不可為空白");
    	}
    	if(StringUtils.isBlank(caseData.getAddDetailItemname())) {
    		bindingResult.reject(null, "品名不可為空白");
    	}
    	
    	List<Itemcode> rslist = findItemListCodeBykey(caseData.getMainkindno(),caseData.getAddDetailItemno());
    	if(CollectionUtils.isNotEmpty(rslist)) {
    		bindingResult.reject(null, "此品名代號已存在");
    	}
    }
    
    public void validDelMainItemcode(Eip08w010Case caseData, BindingResult bindingResult) {
    	long count = caseData.getResultKindList().stream().filter(a -> a.isCheckbox()).count();
    	if(count == 0) {
    		bindingResult.reject(null, "請勾選欲刪除之項目");
    	}
    	
    }
    
    public void validEditMainItemcode(Eip08w010Case caseData, BindingResult bindingResult) {
    	long count = caseData.getResultKindList().stream().filter(a -> a.isCheckbox()).count();
    	if(count == 0) {
    		bindingResult.reject(null, "請勾選欲修改之項目");
    	}else {
    		for(Itemcode itemcode:caseData.getResultKindList()) {
    			if(StringUtils.isEmpty(itemcode.getItemname())) {
    				bindingResult.reject(null, "品名不可為空白");
    			}
    		}
    	}
    }
    
    public void validEditDetailItemcode(Eip08w010Case caseData, BindingResult bindingResult) {
    	long count = caseData.getResultKindList().stream().filter(a -> a.isCheckbox()).count();
    	if(count == 0) {
    		bindingResult.reject(null, "請勾選欲修改之項目");
    	}else {
    		for(Itemcode itemcode:caseData.getResultKindList()) {
    			boolean validrs = itemcodeService.validItemCnt(itemcode.getItemkind(), itemcode.getItemno(), itemcode.getP_cnt()!=null?itemcode.getP_cnt():0, itemcode.getR_cnt()!=null?itemcode.getR_cnt():0);
    			if(!validrs) {
    				bindingResult.reject(null, itemcode.getItemname()+"帳目數量不足");
    			}
    		}
    	}
    	
    }
    
    public void validDeleteDetailItemcode(Eip08w010Case caseData, BindingResult bindingResult) {
    	long count = caseData.getResultKindList().stream().filter(a -> a.isCheckbox()).count();
    	if(count == 0) {
    		bindingResult.reject(null, "請勾選欲刪除之項目");
    	}
    }
    
    public void insertMainItemcode(Eip08w010Case caseData) {
    	String nowdtes= DateUtility.getNowWestDateTime(true);
    	Itemcode itemcode = new Itemcode();
    	itemcode.setItemkind("MAIN");
    	itemcode.setItemno(caseData.getAddMainItemno());
    	itemcode.setItemname(caseData.getAddMainItemname());
    	itemcode.setCre_user(userData.getUserId());
    	itemcode.setCre_datetime(nowdtes);
    	itemcode.setUpd_user(userData.getUserId());
    	itemcode.setUpd_datetime(nowdtes);
    	itemcodeDao.insert(itemcode);
    }
    
    public int insertDetailItemcode(Eip08w010Case caseData) {
    	String nowdtes= DateUtility.getNowWestDateTime(true);
    	Itemcode itemcode = new Itemcode();
    	itemcode.setItemkind(caseData.getMainkindno());
    	itemcode.setItemno(caseData.getAddDetailItemno());
    	itemcode.setItemname(caseData.getAddDetailItemname());
    	itemcode.setPurchase_cnt(0);
    	itemcode.setReturn_cnt(0);
    	itemcode.setFinal_cnt(0);
    	itemcode.setWithhold_cnt(0);
    	itemcode.setBook_cnt(0);
    	itemcode.setCre_user(userData.getUserId());
    	itemcode.setCre_datetime(nowdtes);
    	itemcode.setUpd_user(userData.getUserId());
    	itemcode.setUpd_datetime(nowdtes);
    	return itemcodeDao.insert(itemcode);
    }
    
    public void deleteMainItemcode(Eip08w010Case caseData) {
    	List<Itemcode> itemcodeList = caseData.getResultKindList();
    	String nowDteString = DateUtility.getNowWestDateTime(true);
    	
    	for(Itemcode itemcode : itemcodeList) {
    		if(itemcode.isCheckbox()) {
    			itemcodeService.deleteMainItemcodeAndInsertItemcodeu(itemcode.getItemkind(), itemcode.getItemno(), nowDteString);
    		}
    	}
    }
    
    public void editMainItemcode(Eip08w010Case caseData) {
    	List<Itemcode> itemcodeList = caseData.getResultKindList();
    	String nowDteString = DateUtility.getNowWestDateTime(true);
    	
    	for(Itemcode itemcode : itemcodeList) {
    		if(itemcode.isCheckbox()) {
    			itemcodeService.updateMainItemcodeAndInsertItemcodeu(itemcode.getItemkind(), itemcode.getItemno(), itemcode.getItemname(), nowDteString);
    		}
    	}
    }
    
    public void editDetailItemcode(Eip08w010Case caseData) {
    	List<Itemcode> itemcodeList = caseData.getResultKindList();
    	String nowDteString = DateUtility.getNowWestDateTime(true);
    	
    	for(Itemcode itemcode : itemcodeList) {
    		if(itemcode.isCheckbox()) {
    			itemcodeService.updateDetailItemcodeAndInsertItemcodeu(itemcode.getItemkind(), itemcode.getItemno(), itemcode.getP_cnt()!=null?itemcode.getP_cnt():0, itemcode.getR_cnt()!=null?itemcode.getR_cnt():0, nowDteString);
    		}
    	}
    }
    
    public void deleteDetailItemcode(Eip08w010Case caseData) {
    	List<Itemcode> itemcodeList = caseData.getResultKindList();

    	String nowDteString = DateUtility.getNowWestDateTime(true);
    	
    	for(Itemcode itemcode : itemcodeList) {
    		if(itemcode.isCheckbox()) {
    			itemcodeService.deleteDetailItemcodeAndInsertItemcodeu(itemcode.getItemkind(), itemcode.getItemno(), nowDteString);
    		}
    	}
    }
}
