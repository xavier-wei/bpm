package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.apply.cases.Eip08w040Case;
import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 領物單申請複核作業Service
 */
@Service
public class Eip08w040Service {

	@Autowired
	ItemcodeDao itemcodeDao;

	@Autowired
	ApplyitemDao applyitemDao;
	
	@Autowired
	ItemcodeuDao itemcodeuDao;
	
	@Autowired
	ItemcodeService itemcodeService;
	
	@Autowired
	EipcodeDao eipcodeDao;

	/**
	 * 查詢資料
	 * 
	 * @param caseData
	 * 
	 */
	public void getCaseData(Eip08w040Case caseData) {
		List<Applyitem>list = applyitemDao.selectByApply_dateAndProcess_status(caseData.getApply_dateStart(), caseData.getApply_dateEnd(),"2");
		List<Eip08w040Case>dataList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)) {
			for(Applyitem item : list) {
				Eip08w040Case data = new Eip08w040Case();
				data.setApplyno(item.getApplyno());
				data.setApply_user(item.getApply_user());
				data.setUnit(item.getUnit());
				Eipcode code = new Eipcode();
				code.setCodekind("APPLYSTATUS");
				code.setCodeno(item.getProcess_status());
				Eipcode process_status_nm = eipcodeDao.selectDataByPrimaryKey(code);
				data.setProcess_status(item.getProcess_status()+"-" + process_status_nm.getCodename());
				data.setApply_date(DateUtility.changeDateType(item.getApply_date()));
				dataList.add(data);
			}
		}
		caseData.setDataList(dataList);
	}
	
	/**
	 * 取得資料明細
	 * 
	 * @param caseData
	 * 
	 */
	public void setDetailData(Eip08w040Case caseData) {
		List<Itemcode> list = itemcodeDao.getStatus2List(caseData.getApplyno());
		List<Eip08w040Case>dataList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)) {
			caseData.setApply_user(list.get(0).getApply_user());
			caseData.setApply_dept(list.get(0).getApply_dept());
			caseData.setApply_date(list.get(0).getApply_date());
			caseData.setApply_memo(list.get(0).getApply_memo());
			for(Itemcode itemcode : list) {
				Eip08w040Case data = new Eip08w040Case();
				data.setItemkind_nm(itemcode.getItemkind_nm());
				data.setItemno_nm(itemcode.getItemno_nm());
				data.setApply_cnt(itemcode.getApply_cnt());
				data.setFinal_cnt(itemcode.getFinal_cnt());
				data.setBook_cnt(itemcode.getBook_cnt());
				data.setSeqno(itemcode.getSeqno());
				data.setWithhold_cnt(itemcode.getWithhold_cnt());
				data.setItemkind(itemcode.getItemkind());
				data.setItemno(itemcode.getItemno());
				dataList.add(data);
			}
		}
		caseData.setDetailList(dataList);
	}
	
	/**
	 * 更新資料
	 * 
	 * @param caseData
	 * 
	 */
	public void updateAll(Eip08w040Case caseData,UserBean userData) {
		String nowDateTime = DateUtility.getNowWestDateTime(true);
		for(Eip08w040Case data : caseData.getDetailList()) {
			Applyitem ai = applyitemDao.selectByKey(caseData.getApplyno(), data.getSeqno(), data.getItemkind(),data.getItemno());
			ai.setProcess_status("3");
			ai.setApprove_cnt(data.getProvide_num());
			ai.setUpd_user(userData.getUserId());
			ai.setUpd_datetime(nowDateTime);
			applyitemDao.updateByKey(ai);
			
			Itemcode itemcode = new Itemcode();
			itemcode.setItemkind(data.getItemkind());
			itemcode.setItemno(data.getItemno());
			Itemcode it = itemcodeDao.selectDataByPrimaryKey(itemcode);
			itemcodeService.itemcodeInsertU(it,nowDateTime,"C");
			
			it.setWithhold_cnt(it.getWithhold_cnt()-data.getApply_cnt());
			it.setFinal_cnt(it.getFinal_cnt()-data.getProvide_num());
			it.setBook_cnt(it.getFinal_cnt()-it.getWithhold_cnt());
			
			itemcodeDao.updateByKey(it);
		}


	}
}