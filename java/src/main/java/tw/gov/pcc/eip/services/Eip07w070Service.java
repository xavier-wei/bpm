package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.dao.CarBaseDao;
import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.orderCar.cases.Eip07w070Case;
import tw.gov.pcc.eip.orderCar.report.Eip07w070l00;

/**
 * 派車預約暨派車結果查詢作業Service
 *
 * @author ivy
 */
@Service
public class Eip07w070Service {

	@Autowired
	UserBean userData;
	@Autowired
	private CarBookingDao carBookingDao;
	@Autowired
	private CarBaseDao carBaseDao;
	
	public void getCarno(Eip07w070Case caseData) {
		List<CarBase>carnoList = carBaseDao.getAllData();
		caseData.setCarnoList(carnoList);
	}

	public void setQueryData(Eip07w070Case caseData) {
		CarBooking cb = new CarBooking();
		if(StringUtils.isNotEmpty(caseData.getCarno())) {			
			String [] carno = caseData.getCarno().split("-");
			cb.setCarno1(carno[0]);
			cb.setCarno2(carno[1]);
		}
		cb.setName(caseData.getName());
		cb.setUsing_date_s(caseData.getUsing_date_s());
		cb.setUsing_date_e(caseData.getUsing_date_e());
		List<CarBooking> list = carBookingDao.getEip07w070ReportData(cb,caseData.getOrderCondition());
		if(CollectionUtils.isNotEmpty(list)) {
			caseData.setDataList(list);
		}else {
			caseData.setDataList(null);
		}
	}

	/**
	 * 列印報表
	 * 
	 * @param caseData
	 * @return 
	 */
	public ByteArrayOutputStream getPrintData(Eip07w070Case caseData) throws Exception{
		setQueryData(caseData);
		if(CollectionUtils.isEmpty(caseData.getDataList())) {
			return null;
		}

		Eip07w070l00 report = new Eip07w070l00();
		report.createPdf(caseData);
		return report.getOutputStream();
		
	}

}