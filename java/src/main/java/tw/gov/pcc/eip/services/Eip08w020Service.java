package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.apply.cases.Eip08w020Case;
import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 領物單申請作業Service
 * 
 * @author Ivy
 * @param <R>
 * @since 20230626
 */
@Service
public class Eip08w020Service{

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

	@Autowired
	EipcodeDao eipcodeDao;
	
	@Autowired
	private DeptsDao deptsDao;

	/**
	 * 設定申請頁面資料
	 * 
	 * @param caseData
	 * 
	 */
	public void setCaseData(Eip08w020Case caseData) {
		caseData.setApply_date(DateUtility.getNowChineseDate());// 申請日期(預設系統日)
		String deptname = deptsDao.findByPk(userData.getDeptId()).getDept_name();
		caseData.setApply_dept(deptname);// 申請單位
		caseData.setApply_user(userData.getUserName());// 申請人
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
	 * 新增申請資料
	 * 
	 * @param caseData
	 * 
	 */
	public int insertApplyItem(Eip08w020Case caseData) throws Exception {
		String nowDate = DateUtility.getNowWestDate();
		String nowDatetime = DateUtility.getNowWestDateTime(true);
		Integer index = 1;
		String applyno = nowDate+StringUtils.leftPad(applyitemDao.getApplynoSeq(), 3, '0');
		
		for (Eip08w020Case data : caseData.getAllData()) {
			if (!StringUtils.isAnyEmpty(data.getItemkind(), data.getItemno())
					&& data.getBook_cnt() != null && data.getBook_cnt() > 0) {

				// 1.insert Applyitem
				Applyitem applyitem = new Applyitem();
				applyitem.setApplyno(applyno); // 領物單號
				applyitem.setSeqno(String.valueOf(index)); // 序號
				applyitem.setItemkind(data.getItemkind()); // 品名大類
				applyitem.setItemno(data.getItemno()); // 品名
				applyitem.setApply_user(caseData.getOriApply_user()); // 申請人
				applyitem.setApply_date(nowDate); // 申請日期
				applyitem.setApply_dept(caseData.getOriApply_dept()); // 申請部門
				applyitem.setApply_memo(caseData.getApply_memo()); // 申請用途
				applyitem.setWithhold_cnt_b(data.getWithhold_cnt());// 申請前預扣數量
				applyitem.setWithhold_cnt_a(data.getWithhold_cnt() + data.getApply_cnt());// 申請前預扣數量+[畫面].申請數量
				applyitem.setBook_cnt_b(data.getBook_cnt()); // 申請前帳面數量
				applyitem.setBook_cnt_a(data.getBook_cnt() - data.getApply_cnt());// 申請前帳面數量-[畫面].申請數量
				applyitem.setApply_cnt(data.getApply_cnt()); // [畫面].申請數量
				applyitem.setUnit(StringUtils.isEmpty(data.getUnit())? "" : data.getUnit()); // [畫面].單位單位
				applyitem.setProcess_status("1");
				applyitem.setCre_user(caseData.getOriApply_user()); // [畫面輸入].申請人
				applyitem.setCre_datetime(nowDatetime); // 系統日期時間
				applyitem.setUpd_user(caseData.getOriApply_user()); // =[畫面輸入].申請人
				applyitem.setUpd_datetime(nowDatetime); // 同cre_datetime
				applyitemDao.insert(applyitem);
				index++;

				itemcodeService.applyUpdateItemcodeAndInsertItemcodeu(data.getItemkind(), data.getItemno(),
						data.getApply_cnt(), nowDatetime, "1");
			}

		}
		return index;
	}

	/**
	 * 查詢ApplyItem
	 * 
	 * @param caseData
	 * @return 
	 */
	public void getApplyItem(Eip08w020Case caseData) throws Exception{
		List<Applyitem>list = applyitemDao.selectByApplyUserAndApply_deptAndapplyDate(caseData.getOriApply_user(), caseData.getOriApply_dept(), 
				StringUtils.isNotEmpty(caseData.getApply_date())? DateUtility.changeDateType(caseData.getApply_date()) : "");

		if(CollectionUtils.isNotEmpty(list)){			
			list.stream().map(
					e -> {
						Eipcode code = new Eipcode();
						code.setCodekind("APPLYSTATUS");
						code.setCodeno(e.getProcess_status());
						Eipcode process_status_nm = eipcodeDao.selectDataByPrimaryKey(code);
						e.setProcess_status(e.getProcess_status()+"-" + process_status_nm.getCodename());
						return e;
					}).collect(Collectors.toList());
			caseData.setApplyitemList(list);
		}
	}

	/**
	 * 以領物單號查詢申請資料
	 * 
	 * @param caseData
	 * @return
	 */
	public void getApplyItemByApplyno(Eip08w020Case caseData) throws Exception {
		List<Applyitem> list = applyitemDao.selectByApplyno(caseData.getApplyno());

		List<Applyitem> dataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			caseData.setProcessStatus(list.get(0).getProcess_status());
			for (Applyitem item : list) {
				Applyitem data = new Applyitem();
				Itemcode itc = itemcodeDao.getItemname(item.getItemno());
				data.setItemkind(item.getItemkind() + "-" + itc.getItemkind());
				data.setItemno(item.getItemno() + "-" + itc.getItemname());
				data.setApply_cnt(item.getApply_cnt());
				data.setUnit(item.getUnit());
				Eipcode code = new Eipcode();
				code.setCodekind("APPLYSTATUS");
				code.setCodeno(item.getProcess_status());
				Eipcode process_status_nm = eipcodeDao.selectDataByPrimaryKey(code);
				data.setProcess_status(item.getProcess_status() +"-"+ process_status_nm.getCodename());
				data.setApprove_cnt(item.getApprove_cnt() == null? 0 : item.getApprove_cnt());
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
	public void deleteByApplyno(Eip08w020Case caseData) throws Exception {

		List<Applyitem> list = applyitemDao.selectByApplyno(caseData.getApplyno());
		String nowDateTime = DateUtility.getNowWestDateTime(true);
		for (Applyitem item : list) {
			itemcodeService.applyUpdateItemcodeAndInsertItemcodeu(item.getItemkind(), item.getItemno(),
					item.getApply_cnt(), nowDateTime, "2");
			applyitemDao.deleteByKey(item);
		}
	}
	
	public String validateAllData(Eip08w020Case caseData) throws Exception{
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isEmpty(caseData.getApply_memo())) {
			sb.append("「申請用途」必須輸入\r\n");
		}
		

		for(int i=0; i<caseData.getAllData().size() ; i++) {
			Eip08w020Case data = caseData.getAllData().get(i);
			StringBuffer each = new StringBuffer();
			
			if(StringUtils.isNotEmpty(data.getItemkind()) ){
				
				if(StringUtils.isEmpty(data.getItemno())) {
					each.append("品名、");
				}
				
				if(StringUtils.isNotEmpty(data.getItemno()) && (data.getBook_cnt()==null || data.getBook_cnt()==0)) {
					sb.append("序號"+(i+1)+"：庫存為零，請調整品項\r\n");
				}
				
				if(data.getApply_cnt()==null && data.getBook_cnt()!=null) {
					each.append("數量、");
				}
				
				if(StringUtils.isNotBlank(each.toString())) {
					sb.append("序號"+(i+1)+"："+ StringUtils.removeEnd(each.toString(),"、")+"未填寫\r\n");
				}
				

			}
			
			if(data.getApply_cnt()!=null && data.getBook_cnt()!=null && data.getApply_cnt()>data.getBook_cnt()) {
				sb.append("序號"+(i+1)+"："+"申請數量不得大於庫存數量\r\n");
			}
			
		}
		if(StringUtils.isEmpty(sb.toString())) {
			List<String>itemkindList = new ArrayList<>();
			List<String>itemnoList = new ArrayList<>();
			List<Integer>bookCntList = new ArrayList<>();
			for(Eip08w020Case each : caseData.getAllData()) {
				
				if(StringUtils.isNotBlank(each.getItemkind())) {
					itemkindList.add(each.getItemkind());
				}
				
				if(StringUtils.isNotBlank(each.getItemno())) {
					itemnoList.add(each.getItemno());
				}
				
				if(each.getBook_cnt()!=null) {
					bookCntList.add(each.getBook_cnt());
				}
			}
			
			if(CollectionUtils.isEmpty(itemkindList)|| CollectionUtils.isEmpty(bookCntList) || CollectionUtils.isEmpty(itemnoList) ) {
				sb.append("請至少申請一項");
			}
		}

		
		return sb.toString();
	}
	
	public String validateCnt(Eip08w020Case caseData) {
		StringBuffer sb = new StringBuffer();
		
		for(Eip08w020Case data : caseData.getAllData()) {
			if (!StringUtils.isAnyEmpty(data.getItemkind(), data.getItemno())
					&& data.getBook_cnt() != null && data.getBook_cnt() > 0) {
				if (!itemcodeService.validItemTakeCnt(data.getItemkind(), data.getItemno(), data.getApply_cnt())) {
					Itemcode code = new Itemcode();
					code.setItemkind(data.getItemkind());
					code.setItemno(data.getItemno());
					Itemcode item = itemcodeDao.selectDataByPrimaryKey(code);
					sb.append(item.getItemno() + "-" + item.getItemname() + "數量不足\r\n");
				}
			}
		}

		return sb.toString();
	}

	public String alert0Cnt(Eip08w020Case caseData) {
		for(int i=0; i<caseData.getAllData().size() ; i++) {
			Eip08w020Case data = caseData.getAllData().get(i);
			StringBuffer sb = new StringBuffer();
			if(!StringUtils.isAnyEmpty(data.getItemkind(), data.getItemno())
					&& data.getBook_cnt() != null && data.getBook_cnt() == 0) {
				sb.append(i+1+",");
			}
			if(StringUtils.isNotBlank(sb.toString())){
				return StringUtils.removeEnd(sb.toString(),",");
			}
		}
		return null;
	}
}