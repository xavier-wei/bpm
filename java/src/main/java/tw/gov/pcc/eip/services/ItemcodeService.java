package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.domain.Itemcodeu;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.BeanUtility;

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
	
    /**
     * 領物單申請刪除調整更新itemcode&itemcodeu
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer applycnt 領取數量
     * @param String nowTimeString yyyMMddhhmmss
     * @param String mode 1:新增 2.刪除
     */
	public void applyUpdateItemcodeAndInsertItemcodeu(String itemkind,String itemcodeno,int applycnt, String nowTimeString, String mode) {
		
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(itemkind);
		qItemcode.setItemno(itemcodeno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		//1.更新itemcode
		Integer bookCnt = 0;
		Integer withholdCnt = 0;
		if(StringUtils.equals("1", mode)) {
			bookCnt = rsItemcode.getBook_cnt() - applycnt;
			withholdCnt = rsItemcode.getWithhold_cnt() + applycnt;
		}else if(StringUtils.equals("2", mode)){
			bookCnt = rsItemcode.getBook_cnt() + applycnt;
			withholdCnt = withholdCnt + rsItemcode.getWithhold_cnt() - applycnt;
		}	
		Itemcode updItemcode = new Itemcode();
		BeanUtility.copyProperties(updItemcode, rsItemcode);
		updItemcode.setBook_cnt(bookCnt);
		updItemcode.setWithhold_cnt(withholdCnt);
		updItemcode.setUpd_user(userData.getUserId());
		updItemcode.setUpd_datetime(nowTimeString);
		itemcodeDao.updateByKey(updItemcode);
		
		//2.新增itemcodeu
		itemcodeInsertU(rsItemcode, nowTimeString, COCD_EDIT);
	}
	
	
	/**
     * 領物申請刪除調整更新itemcode&itemcodeu
     *
     * @param Itemcode 要異動的itemcode
     * @param String nowTimeString yyyMMddhhmmss
     * @param String cocd A:新增 C:修改 D:刪除
     */
	private void itemcodeInsertU(Itemcode itemcode, String nowTimeString, String cocdtype) {
		Itemcodeu insItemcodeu = new Itemcodeu();
		BeanUtility.copyProperties(insItemcodeu, itemcode);
		insItemcodeu.setCocd(cocdtype);
		insItemcodeu.setU_date(nowTimeString.substring(0,8));
		insItemcodeu.setU_time(nowTimeString.substring(8,14));
		insItemcodeu.setU_user(userData.getUserId());
		itemcodeuDao.insert(insItemcodeu);
	}
	
}