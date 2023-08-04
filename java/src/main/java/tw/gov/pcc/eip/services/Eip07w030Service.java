package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w030Case;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 派車預約暨派車結果查詢作業Service
 *
 * @author ivy
 */
@Service
public class Eip07w030Service {

	@Autowired
	UserBean userData;
	@Autowired
	private EipcodeDao eipcodeDao;
	@Autowired
	private CarBookingDao carBookingDao;
	
	/**
	 * 依照申請日期起迄搜尋審核資料
	 *
	 * @param caseData
	 */
	public void getData(Eip07w030Case caseData) throws Exception {
		List<CarBooking> list = carBookingDao.selectByApplydate(caseData.getApplydateStart(),
				caseData.getApplydateEnd(),"eip07w030");
		List<Eip07w030Case> dataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CarBooking car : list) {
				Eip07w030Case data = new Eip07w030Case();
				data.setApplyid(car.getApplyid());
				data.setApply_user(car.getApply_user());
				data.setApply_dept(car.getApply_dept());
				data.setUsing_date(car.getUsing_date());
				data.setUsing_time_s(car.getUsing_time_s());
				data.setUsing_time_e(car.getUsing_time_e());
				data.setApply_memo(car.getApply_memo());
				Eipcode eipcode = new Eipcode();
				eipcode.setCodekind("CARPROCESSSTATUS");
				eipcode.setCodeno(car.getCarprocess_status());
				Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
				data.setCarprocess_status(code.getCodeno() + "-" + code.getCodename());
				dataList.add(data);
			}
		}
		caseData.setDataList(dataList);
	}

	/**
	 * 複核資料
	 * 
	 * @param caseData
	 * @return
	 */
	public void updateAll(Eip07w030Case caseData) throws Exception {
		String nowdatetime = DateUtility.getNowWestDateTime(true);

		for (String applyId : caseData.getApplyIdList()) {
			
			CarBooking updateData = carBookingDao.selectByApplyId(applyId);
			if ("agree".equals(caseData.getAgree())) {
				updateData.setCarprocess_status("2");
				updateData.setReconfirm_mk("Y");
			}

			if ("disagree".equals(caseData.getAgree())) {
				updateData.setCarprocess_status("T");
				updateData.setReconfirm_mk("N");
			}
			updateData.setReconfirm_user(userData.getUserId());
			updateData.setReconfirm_date(nowdatetime.substring(0,8));
			updateData.setUpd_user(userData.getUserId());
			updateData.setUpd_datetime(nowdatetime);
			carBookingDao.updateByKey(updateData);
			
		}
	}

}