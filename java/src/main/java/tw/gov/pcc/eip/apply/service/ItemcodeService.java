package tw.gov.pcc.eip.apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.domain.Itemcodeu;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * itemcode&itemcodeu service
 *
 * @author ivan
 */
@Service
public class ItemcodeService {
	
	@Autowired
	ItemcodeDao itemcodeDao;
	@Autowired
	ItemcodeuDao itemcodeuDao;
	@Autowired
	UserBean userData;
	
	private final String COCD_ADD = "A";
	private final String COCD_EDIT = "C";
	private final String COCD_DELETE = "D";
	
    /**
     * 驗證該品項是否還可領取
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer takecnt 領取數量
     * @return List
     */
	public boolean validItemTakeCnt(String itemkind,String itemcodeno,Integer takecnt) {
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(itemkind);
		qItemcode.setItemno(itemcodeno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		Integer bookCnt = rsItemcode.getBook_cnt();
		if(bookCnt >= takecnt) {
			return true;
		}else {
			return false;
		}
	}
	
	public void updateItemcodeAndInsertItemcodeu(String itemkind,String itemcodeno,Integer applycnt, String nowTimeString) {
		
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(itemkind);
		qItemcode.setItemno(itemcodeno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		//1.更新itemcode
		Integer bookCnt = rsItemcode.getBook_cnt() - applycnt;
		Integer withholdCnt = rsItemcode.getWithhold_cnt() + applycnt;
		Itemcode updItemcode = new Itemcode();
		BeanUtility.copyProperties(updItemcode, qItemcode);
		updItemcode.setBook_cnt(bookCnt);
		updItemcode.setWithhold_cnt(withholdCnt);
		updItemcode.setUpd_user(userData.getUserId());
		updItemcode.setUpd_datetime(nowTimeString);
		itemcodeDao.updateByKey(updItemcode);
		
		//2.新增itemcodeu
		Itemcodeu insItemcodeu = new Itemcodeu();
		BeanUtility.copyProperties(insItemcodeu, qItemcode);
		insItemcodeu.setCocd(COCD_EDIT);
		insItemcodeu.setU_date(nowTimeString.substring(0,8));
		insItemcodeu.setU_time(nowTimeString.substring(8,14));
		insItemcodeu.setU_user(userData.getUserId());
		itemcodeuDao.insert(insItemcodeu);
	}
	
}
