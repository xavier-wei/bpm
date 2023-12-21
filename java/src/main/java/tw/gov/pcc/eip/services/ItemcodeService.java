package tw.gov.pcc.eip.services;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.domain.Itemcodeu;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.CollectionUtility;

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
		
		Integer bookCnt = rsItemcode.getBook_cnt()==null? 0 : rsItemcode.getBook_cnt();
		if(bookCnt >= takecnt) {
			return true;
		}else {
			return false;
		}
	}
	
    /**
     * 驗證該品項是否還可領取
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer purchaseCnt 進貨數量
     * @param Integer returnCnt 退貨數量
     * @return List
     */
	public boolean validItemCnt(String itemkind,String itemcodeno,Integer purchaseCnt ,Integer returnCnt) {
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(itemkind);
		qItemcode.setItemno(itemcodeno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		Integer bookCnt = rsItemcode.getBook_cnt();
		Integer bookCntRs = bookCnt + (purchaseCnt-returnCnt);
		if(bookCntRs >= 0) {
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
     * 領物單基本資料進出貨調整更新itemcode&itemcodeu
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer purchaseCnt 進貨數量
     * @param Integer returnCnt 退貨數量
     * @param String nowTimeString yyyMMddhhmmss
     */
	public void updateDetailItemcodeAndInsertItemcodeu(String itemkind,String itemcodeno,int purchaseCnt,int returnCnt, String nowTimeString) {
		
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(itemkind);
		qItemcode.setItemno(itemcodeno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		//1.更新itemcode
		Itemcode updItemcode = new Itemcode();
		BeanUtility.copyProperties(updItemcode, rsItemcode);
		Integer bookCnt = Nvl(rsItemcode.getBook_cnt()) + Nvl(purchaseCnt) - Nvl(returnCnt);
		Integer finalCnt = Nvl(rsItemcode.getFinal_cnt()) + Nvl(purchaseCnt) - Nvl(returnCnt);
		updItemcode.setPurchase_cnt(Nvl(purchaseCnt));
		updItemcode.setReturn_cnt(Nvl(returnCnt));
		updItemcode.setBook_cnt(Nvl(bookCnt));
		updItemcode.setFinal_cnt(Nvl(finalCnt));
		updItemcode.setUpd_user(userData.getUserId());
		updItemcode.setUpd_datetime(nowTimeString);
		itemcodeDao.updateByKey(updItemcode);
		
		//2.新增itemcodeu
		itemcodeInsertU(rsItemcode, nowTimeString, COCD_EDIT);
	}
	
    /**
     * 領物單基本資料進出貨調整更新itemcode&itemcodeu
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer purchaseCnt 進貨數量
     * @param Integer returnCnt 退貨數量
     * @param String nowTimeString yyyMMddhhmmss
     */
	public void updateMainItemcodeAndInsertItemcodeu(String itemkind,String itemcodeno,String itemname ,String nowTimeString) {
		
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(itemkind);
		qItemcode.setItemno(itemcodeno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		//1.更新itemcode
		Itemcode updItemcode = new Itemcode();
		BeanUtility.copyProperties(updItemcode, rsItemcode);
		updItemcode.setItemname(itemname);
		updItemcode.setUpd_user(userData.getUserId());
		updItemcode.setUpd_datetime(nowTimeString);
		itemcodeDao.updateByKey(updItemcode);
		
		//2.新增itemcodeu
		itemcodeInsertU(rsItemcode, nowTimeString, COCD_EDIT);
	}
	
    /**
     * 領物單基本資料刪除main itemcode
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer purchaseCnt 進貨數量
     * @param Integer returnCnt 退貨數量
     * @param String nowTimeString yyyMMddhhmmss
     */
	public void deleteMainItemcodeAndInsertItemcodeu(String mainItemkind,String MainItemno, String nowTimeString) {
		
		Itemcode qMainItemcode = new Itemcode();
		qMainItemcode.setItemkind(mainItemkind);
		qMainItemcode.setItemno(MainItemno);
		Itemcode rsMainItemcode = itemcodeDao.selectDataListByKey(qMainItemcode).get(0);
		
		//1.刪除main itemcode			
		Itemcode delMainItemcode = new Itemcode();
		BeanUtility.copyProperties(delMainItemcode, rsMainItemcode);
		itemcodeDao.deleteByKey(delMainItemcode);
		
		//2.新增main itemcodeu
		itemcodeInsertU(rsMainItemcode, nowTimeString, COCD_DELETE);
		
		//3.刪除detail itemcode
		List<Itemcode> rsDetailItemcodeList = itemcodeDao.findByItemkind(MainItemno);
		if(CollectionUtils.isNotEmpty(rsDetailItemcodeList)) {
			for(Itemcode dic:rsDetailItemcodeList) {
				deleteDetailItemcodeAndInsertItemcodeu(dic.getItemkind(), dic.getItemno(), nowTimeString);
			}
		}
	}
	
    /**
     * 領物單基本資料刪除detail itemcode
     *
     * @param String itemkind
     * @param String itemcodeno
     * @param Integer purchaseCnt 進貨數量
     * @param Integer returnCnt 退貨數量
     * @param String nowTimeString yyyMMddhhmmss
     */
	public void deleteDetailItemcodeAndInsertItemcodeu(String detailItemkind,String detailItemno, String nowTimeString) {
		
		Itemcode qItemcode = new Itemcode();
		qItemcode.setItemkind(detailItemkind);
		qItemcode.setItemno(detailItemno);
		Itemcode rsItemcode = itemcodeDao.selectDataListByKey(qItemcode).get(0);
		
		//1.刪除itemcode			
		Itemcode delItemcode = new Itemcode();
		BeanUtility.copyProperties(delItemcode, rsItemcode);
		itemcodeDao.deleteByKey(delItemcode);
		
		//2.新增itemcodeu
		itemcodeInsertU(rsItemcode, nowTimeString, COCD_DELETE);
	}
	
	
    /**
     * 領物申請刪除調整更新itemcode&itemcodeu
     *
     * @param Itemcode 要異動的itemcode
     * @param String nowTimeString yyyMMddhhmmss
     * @param String cocd A:新增 C:修改 D:刪除
     */
	public void itemcodeInsertU(Itemcode itemcode, String nowTimeString, String cocdtype) {
		Itemcodeu insItemcodeu = new Itemcodeu();
		BeanUtility.copyProperties(insItemcodeu, itemcode);
		insItemcodeu.setCocd(cocdtype);
		insItemcodeu.setU_date(nowTimeString.substring(0,8));
		insItemcodeu.setU_time(nowTimeString.substring(8,14));
		insItemcodeu.setU_user(userData.getUserId());
		itemcodeuDao.insert(insItemcodeu);
	}

	public Integer Nvl(Integer num) {
		if(num==null) {
			return 0 ;
		} else {
			return num;
		}
	}
}