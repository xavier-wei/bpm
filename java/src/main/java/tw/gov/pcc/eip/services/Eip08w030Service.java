package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.apply.cases.Eip08w030Case;
import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 領物單申請複核作業Service
 * 
 * @author Ivy
 * @since 2023710
 */
@Service
public class Eip08w030Service {

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

	private static final String PROCESS_STATUS_AGREE = "2";
	private static final String PROCESS_STATUS_DISAGREE = "9";
	private static final String RECONFIRM_MK_AGREE = "Y";
	private static final String RECONFIRM_MK_DISAGREE = "N";

	/**
	 * 查詢資料
	 * 
	 * @param caseData
	 * 
	 */
	public void getCaseData(Eip08w030Case caseData) {
		List<Applyitem> list = applyitemDao.selectByApply_dateAndProcess_status(caseData.getApplydateStart(),
				caseData.getApplydateEnd(), "1");
		List<Eip08w030Case> dataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Applyitem item : list) {
				Eip08w030Case data = new Eip08w030Case();
				data.setApplyno(item.getApplyno());
				data.setApply_user(item.getApply_user());
				data.setApply_dept(item.getApply_dept());
				dataList.add(data);
			}
		}
		caseData.setDataList(dataList);
	}

	/**
	 * 以領物單號查詢申請資料
	 * 
	 * @param caseData
	 * @return
	 */
	public void getApplyItemByApplyno(Eip08w030Case caseData) throws Exception {
		List<Applyitem> list = applyitemDao.selectByApplyno(caseData.getApplyno());
		List<Eip08w030Case> dataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			caseData.setApply_user(list.get(0).getApply_user());
			caseData.setApply_dept(list.get(0).getApply_dept());
			caseData.setApply_date(list.get(0).getApply_date());
			caseData.setApply_memo(list.get(0).getApply_memo());
			for (Applyitem item : list) {
				Eip08w030Case data = new Eip08w030Case();
				Itemcode itc = itemcodeDao.getItemname(item.getItemno());
				data.setItemkind(item.getItemkind() + "-" + itc.getItemkind());
				data.setItemno(item.getItemno() + "-" + itc.getItemname());
				data.setApply_cnt(item.getApply_cnt());
				data.setUnit(item.getUnit());
				dataList.add(data);
			}
		}
		caseData.setDetailList(dataList);
	}

	/**
	 * 複核資料
	 * 
	 * @param caseData
	 * @return
	 */
	public void updateAll(Eip08w030Case caseData) throws Exception {
		List<String> applynos = caseData.getDataList().stream()
				.filter(it -> it.isCheck() && StringUtils.isNotEmpty(it.getApplyno())).map(e -> e.getApplyno())
				.collect(Collectors.toList());

		caseData.setApplynoList(applynos);
		String nowdatetime = DateUtility.getNowWestDateTime(true);

		for (String applyno : caseData.getApplynoList()) {
			// 1.更改applyitem複核狀態
			List<Applyitem> updateList = applyitemDao.selectByApplyno(applyno);
			if ("agree".equals(caseData.getAgree())) {
				for (Applyitem item : updateList) {
					item.setProcess_status(PROCESS_STATUS_AGREE);
					item.setReconfirm_mk(RECONFIRM_MK_AGREE);
					item.setReconfirm_user(userData.getUserId());
					item.setReconfirm_date(nowdatetime.substring(0, 7));
					applyitemDao.updateByKey(item);
				}
			}

			if ("disagree".equals(caseData.getAgree())) {
				for (Applyitem item : updateList) {
					item.setProcess_status(PROCESS_STATUS_DISAGREE);
					item.setReconfirm_mk(RECONFIRM_MK_DISAGREE);
					item.setReconfirm_user(userData.getUserId());
					item.setReconfirm_date(DateUtility.getNowWestDate());
					applyitemDao.updateByKey(item);
				}

				List<Applyitem> dataN = applyitemDao.selectReconfirm_mkNData(applynos);

//		      (統計全部核準的數量)
//				STEP2:先將改前資料搬至itemcodeu
//				INSERT INTO ITEMCODEU 
//				SELECT * FROM ITEMCODE
//				WHERE ITEMKIND =[step1查詢結果].品名大類
//				  AND ITEMNO=[ step1查詢結果].品名

//				STEP3:改後
//				 P_withhold_cnt= STEP2查詢結果withhold_cnt -step1統計結果.withhold_cnt
//				 P_book_cnt= STEP2查詢結果book_cnt + p_withhold_cnt

				for (Applyitem item : dataN) {
					itemcodeService.applyUpdateItemcodeAndInsertItemcodeu(item.getItemkind(), item.getItemno(),
							item.getWithhold_cnt_a(), nowdatetime, "2");
				}

			}
		}

	}

}