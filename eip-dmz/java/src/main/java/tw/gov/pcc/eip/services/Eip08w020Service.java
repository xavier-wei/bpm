package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tw.gov.pcc.eip.apply.cases.Eip08w020Case;
import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 領物單申請作業Service
 * 
 * @author Ivy
 * @since 20230626
 */
@Service
public class Eip08w020Service {

	@Autowired
	UserBean userData;

	@Autowired
	ItemcodeDao itemcodeDao;

	@Autowired
	ApplyitemDao applyitemDao;
	
	@Autowired
	ItemcodeuDao itemcodeuDao;
	
	@Autowired
	ItemcodeService itemcodeService;

	/**
	 * 設定申請頁面資料
	 * 
	 * @param caseData
	 * 
	 */
	public void setCaseData(Eip08w020Case caseData) {
		caseData.setApply_date(DateUtility.getNowChineseDate());// 申請日期(預設系統日)
		caseData.setApply_dept(userData.getDeptId());// 申請單位
		caseData.setApply_user(userData.getUserId());// 申請人
		Itemcode itemcode = new Itemcode();
		itemcode.setItemkind("MAIN");
		List<Itemcode> itemslist = itemcodeDao.selectAllData(itemcode);
		caseData.setItemList(itemslist);
	}

	/**
	 * 取得品名
	 * 
	 * @param caseData
	 * @return list
	 */
	public List<Itemcode> getItemCodekind(Eip08w020Case caseData) {
		Itemcode itemcode = new Itemcode();
		itemcode.setItemkind(caseData.getItemkind());
		List<Itemcode> itemnolist = itemcodeDao.selectAllData(itemcode);
		return itemnolist;
	}

	/**
	 * 取得庫存數量
	 * 
	 * @param caseData
	 * @return Integer
	 */
	public Itemcode getWithhold_cntAndBook_cnt(String itemkind, String itemno) {
		Itemcode itemcode = new Itemcode();
		itemcode.setItemkind(itemkind);
		itemcode.setItemno(itemno);
		Itemcode data = itemcodeDao.selectDataByPrimaryKey(itemcode);
		return data;
	}
	
	/**
	 * 驗證各項庫存是否足夠，若有不足，則跳通知
	 * 
	 */
	public BindingResult validateCnt(Eip08w020Case caseData,BindingResult result) {
		StringBuffer sb = new StringBuffer();
		for (Eip08w020Case data : caseData.getAllData()) {
			if (!StringUtils.isAnyEmpty(data.getItemkind(), data.getItemno(), data.getUnit())
					&& data.getBook_cnt() != null && data.getBook_cnt()>0) {
				if(!itemcodeService.validItemTakeCnt(data.getItemkind(), data.getItemno(), data.getApply_cnt())) {
					Itemcode code = new Itemcode();
					code.setItemkind(data.getItemkind());
					code.setItemno(data.getItemno());
					Itemcode item = itemcodeDao.selectDataByPrimaryKey(code);
					sb.append(item.getItemno()+"-"+item.getItemname()+"數量不足，");
				}
			}
		}
		
		if(StringUtils.isNotEmpty(sb.toString())) {
			result.rejectValue("", "validation.eip08w020", new Object[] {"數量不足"}, StringUtils.removeEnd(sb.toString(), ",") );
		}
		
		return null;
	}

	/**
	 * 新增申請資料
	 * 
	 * @param caseData
	 * 
	 */
	public void insertApplyItem(Eip08w020Case caseData) throws Exception{
		String nowDate = DateUtility.getNowWestDate();
		String nowDatetime = DateUtility.getNowWestDateTime(true);
		Integer index = 1;
		String applyno = "";

		//取得applyno，若當日沒有資料則從001開始編
		Applyitem api = applyitemDao.getApplyno();
		if (api == null) {
			applyno = DateUtility.getNowWestDate() + "001";
		} else {
			Integer num = NumberUtils.toInt(api.getApplyno().substring(8, 11)) + 1;
			applyno = DateUtility.getNowWestDate() + StringUtils.leftPad(num.toString(), 3, '0');
		}

		for (Eip08w020Case data : caseData.getAllData()) {
			if (!StringUtils.isAnyEmpty(data.getItemkind(), data.getItemno(), data.getUnit())
					&& data.getBook_cnt() != null && data.getBook_cnt()>0) {

				//1.insert Applyitem
				Applyitem applyitem = new Applyitem();
				applyitem.setApplyno(applyno); // 領物單號
				applyitem.setSeqno(String.valueOf(index)); // 序號
				applyitem.setItemkind(data.getItemkind()); // 品名大類
				applyitem.setItemno(data.getItemno()); // 品名
				applyitem.setApply_user(caseData.getApply_user()); // 申請人
				applyitem.setApply_date(nowDate); // 申請日期
				applyitem.setApply_dept(caseData.getApply_dept()); // 申請部門
				applyitem.setApply_memo(caseData.getApply_memo()); // 申請用途
				applyitem.setWithhold_cnt_b(data.getWithhold_cnt());// 申請前預扣數量
				applyitem.setWithhold_cnt_a(data.getWithhold_cnt()+data.getApply_cnt());// 申請前預扣數量+[畫面].申請數量
				applyitem.setBook_cnt_b(data.getBook_cnt()); // 申請前帳面數量
				applyitem.setBook_cnt_a(data.getBook_cnt()-data.getApply_cnt());// 申請前帳面數量-[畫面].申請數量
				applyitem.setApply_cnt(data.getApply_cnt()); // [畫面].申請數量
				applyitem.setUnit(data.getUnit()); // [畫面].單位單位
				applyitem.setProcess_status("1");
				applyitem.setCre_user(caseData.getApply_user()); // [畫面輸入].申請人
				applyitem.setCre_datetime(nowDatetime); // 系統日期時間
				applyitem.setUpd_user(caseData.getApply_user()); // =[畫面輸入].申請人
				applyitem.setUpd_datetime(nowDatetime); // 同cre_datetime
				applyitemDao.insert(applyitem);
				index++;
				
				itemcodeService.applyUpdateItemcodeAndInsertItemcodeu(data.getItemkind(), data.getItemno(), data.getApply_cnt(),nowDatetime,"1");
			}

		}

	}
	
	/**
	 * 查詢ApplyItem
	 * 
	 * @param caseData
	 * @return 
	 */
	public void getApplyItem(Eip08w020Case caseData) throws Exception{

		List<Applyitem>list = applyitemDao.selectByApplyUserAndApply_deptAndapplyDate(caseData.getApply_user(), caseData.getApply_dept(), 
				StringUtils.isNotEmpty(caseData.getApply_date())? DateUtility.changeDateType(caseData.getApply_date()) : "");
		caseData.setApplyitemList(list);
	}
	
	/**
	 * 以領物單號查詢申請資料
	 * 
	 * @param caseData
	 * @return 
	 */
	public void getApplyItemByApplyno(Eip08w020Case caseData) throws Exception{
		List<Applyitem>list = applyitemDao.selectByApplyno(caseData.getApplyno());
		List<Applyitem>dataList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)) {
			for(Applyitem item : list) {
				Applyitem data = new Applyitem();
				Itemcode itc = itemcodeDao.getItemname(item.getItemno());
				data.setItemkind(item.getItemkind()+"-"+itc.getItemkind());
				data.setItemno(item.getItemno()+"-"+itc.getItemname());
				data.setApply_cnt(item.getApply_cnt());
				data.setApply_dept(item.getApply_dept());
				data.setProcess_status(item.getProcess_status());
				dataList.add(data);
			}
		}
		caseData.setDetailList(dataList);
	}
	
	/**
	 * 刪除申請單
	 * 
	 * @param caseData
	 * @return 
	 */
	public void deleteByApplyno(Eip08w020Case caseData) throws Exception{
		
		List<Applyitem> list = applyitemDao.selectByApplyno(caseData.getApplyno());
		String nowDateTime = DateUtility.getNowWestDateTime(true);
		for(Applyitem item : list) {
			itemcodeService.applyUpdateItemcodeAndInsertItemcodeu(item.getItemkind(), item.getItemno(), item.getApply_cnt(), nowDateTime,"2");
			applyitemDao.deleteByKey(item);
		}
	}
}