package tw.gov.pcc.eip.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w050Case;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 派車預約暨派車結果查詢作業Service
 *
 * @author ivy
 */
@Service
public class Eip07w050Service {

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
	public void getData(Eip07w050Case caseData) throws Exception {
		List<CarBooking> list = carBookingDao.selectByApplydate(caseData.getApplydateStart(),
				caseData.getApplydateEnd(), "eip07w050",null);
		List<Eip07w050Case> dataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CarBooking car : list) {
				Eip07w050Case data = new Eip07w050Case();
				data.setApplyid(car.getApplyid());
				data.setApply_user(car.getApply_user());
				data.setApply_dept(car.getApply_dept());
				data.setApply_dept(car.getApply_dept());
				data.setUsing_date(car.getUsing_date());
				data.setUsing_time_s(car.getApprove_using_time_s());
				data.setUsing_time_e(car.getApprove_using_time_e());
				data.setApply_memo(car.getApply_memo());
				Eipcode eipcode = new Eipcode();
				eipcode.setCodekind("CARPROCESSSTATUS");
				eipcode.setCodeno(car.getCarprocess_status());
				Eipcode code = eipcodeDao.selectDataByPrimaryKey(eipcode);
				data.setCarprocess_status(code.getCodeno() + "-" + code.getCodename());
				data.setApply_date(car.getApply_date());
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
	public void updateAll(Eip07w050Case caseData) throws Exception {
		List<String> applyids = caseData.getDataList().stream()
				.filter(it -> it.isCheck() && StringUtils.isNotEmpty(it.getApplyid())).map(e -> e.getApplyid())
				.collect(Collectors.toList());

		caseData.setApplyIdList(applyids);
		String nowdatetime = DateUtility.getNowWestDateTime(true);

		for (String applyId : caseData.getApplyIdList()) {

			CarBooking updateData = carBookingDao.selectByApplyId(applyId);
			if ("agree".equals(caseData.getAgree())) {
				updateData.setReconfirm_mk2("Y");
			}

			if ("disagree".equals(caseData.getAgree())) {
				updateData.setReconfirm_mk2("N");
			}
			updateData.setReconfirm_user2(userData.getUserId());
			updateData.setReconfirm_date2(nowdatetime.substring(0, 8));
			updateData.setUpd_user(userData.getUserId());
			updateData.setUpd_datetime(nowdatetime);
			carBookingDao.updateByKey(updateData);

		}
	}

}