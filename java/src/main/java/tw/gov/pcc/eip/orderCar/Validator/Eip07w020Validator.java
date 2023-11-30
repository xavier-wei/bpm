package tw.gov.pcc.eip.orderCar.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;
import tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

@Component
public class Eip07w020Validator implements Validator {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w020Controller.class);
	@Override
	public boolean supports(Class<?> clazz) {
		return Eip08w060Case.class.isAssignableFrom(clazz);
	}

	public void addError(Object target, Errors errors) {
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
	SimpleDateFormat format = new SimpleDateFormat("MMdd");

	public void eip07w020QValidate(Eip07w020Case validate, Errors errors) {


		if ("A".equals(validate.getWorkTy())) {
			if (StringUtils.isBlank(validate.getApplyDate())) {
				errors.reject(null, "尚未輸入申請日期");
			}
			if (!DateUtility.isValidDate(validate.getApplyDate(), false)) {
				errors.reject(null, "申請日期請輸入民國年月日");
			}
			if (DateUtility.asDate(DateUtility.calDay(DateUtil.getNowChineseDate(), 0),false).after(DateUtility.asDate(validate.getApplyDate(),false))){
				errors.reject(null, "申請日期需大於等於系統日期");
			}
		}

		if ("Q".equals(validate.getWorkTy())){
			if (StringUtils.isNotBlank(validate.getApplyDateEnd())){
				if (StringUtils.isBlank(validate.getApplyDateStar())){
					errors.reject(null, "尚未輸入申請日期(起)");
				}
			}
			if (StringUtils.isNotBlank(validate.getApplyDateStar())&&StringUtils.isNotBlank(validate.getApplyDateEnd())){
				if(DateUtility.asDate(validate.getApplyDateStar(),false).after(DateUtility.asDate(validate.getApplyDateEnd(),false))){
					errors.reject(null, "申請日期(迄)需大於申請日期(起)");
				}
			}

			if (StringUtils.isNotBlank(validate.getUseDateEnd())){
				if (StringUtils.isBlank(validate.getUseDateStar())){
					errors.reject(null, "尚未輸入用車日期(起)");
				}
			}

			if (StringUtils.isNotBlank(validate.getUseDateStar())&&StringUtils.isNotBlank(validate.getUseDateEnd())) {
				if (DateUtility.asDate(validate.getUseDateStar(), false).after(DateUtility.asDate(validate.getUseDateEnd(), false))) {
					errors.reject(null, "用車日期(迄)需大於用車日期(起)");
				}
			}

		}




	}

	public void eip07w020AValidate(Eip07w020Case validate,String workTy ,Errors errors) {

		if (StringUtils.isBlank(validate.getUseCarMemo())) {
			errors.reject(null, "用車事由必需輸入");
		}
		if (StringUtils.isBlank(validate.getDestination())) {
			errors.reject(null, "目的地必需輸入");
		}
		if (StringUtils.isBlank(validate.getNumber())) {
			errors.reject(null, "人數必需輸入");
		}
		if (StringUtils.isBlank(validate.getUseDate())) {
			errors.reject(null, "用車日期必需輸入");
		}
		if (StringUtils.isBlank(validate.getStarH())||StringUtils.isBlank(validate.getStarM())||
				StringUtils.isBlank(validate.getEndH())||StringUtils.isBlank(validate.getEndM())
		) {
			errors.reject(null, "用車時間必需輸入");
		}
		try {

			Date timeSart = format.parse(validate.getStarH()+validate.getStarM());
			Date timeEnd = format.parse(validate.getEndH()+validate.getEndM());

		if (timeSart.compareTo(timeEnd)>0){
			errors.reject(null, "用車時間起始時間不可小於結束時間");
		}

		//判斷是該作業是"A"新增或"R"補單
		if ("A".equals(workTy)){
			if (DateUtility.asDate(DateUtility.calDay(DateUtil.getNowChineseDate(), 0),false).after(DateUtility.asDate(validate.getUseDate(),false))){
				errors.reject(null, "用車日期需大於等於系統日期");
			}
		}else {
			if (DateUtility.asDate(DateUtility.calDay(validate.getUseDate(), 1),false).after(DateUtility.asDate(DateUtil.getNowChineseDate(),false))){
				errors.reject(null, "補單案件，用車日期只允許登打小於系統日期");
			}
		}

		}catch (Exception E){

		}

	}

	public void eip07w020DValidate(Eip07w020Case date, Errors errors) {
	try {
		Date timeSart = format.parse(date.getChangeMkList().get(0).getStarH() + date.getChangeMkList().get(0).getStarM());
		Date timeEnd = format.parse(date.getChangeMkList().get(0).getEndH() + date.getChangeMkList().get(0).getEndM());


		CarBooking oldData=date.getDetailsList().get(0);
		CarBooking 	rmData=date.getChangeMkList().get(0);
		updateValidate( oldData, errors);
		//判斷異動申請相關資料
//		if ("true".equals(date.getCheckMk())){
			if (StringUtils.isBlank(rmData.getApply_memo())) {
				errors.reject(null, "尚未輸入異動申請_用車事由");
			}
			if (StringUtils.isBlank(rmData.getDestination())) {
				errors.reject(null, "尚未輸入異動申請_目的地");
			}
			if (StringUtils.isBlank(rmData.getNum_of_people())) {
				errors.reject(null, "尚未輸入異動申請_人數");
			}
			if (StringUtils.isBlank(rmData.getStarH())||StringUtils.isBlank(rmData.getStarM())||
					StringUtils.isBlank(rmData.getEndH())||StringUtils.isBlank(rmData.getEndM())
			) {
				errors.reject(null, "尚未輸入異動申請_用車日期");
			}
			if (timeSart.compareTo(timeEnd)>0){
				errors.reject(null, "用車時間起始時間不可小於結束時間");
			}
	}catch (Exception e){
		log.error("驗證失敗，原因:{}", ExceptionUtility.getStackTrace(e));
	}
	}

	public void updateValidate(CarBooking data, Errors errors){
		try{
		Date timeSart = format.parse(data.getStarH()+data.getStarM());
		Date timeEnd = format.parse(data.getEndH()+data.getEndM());
		if (StringUtils.isBlank(data.getApply_memo())) {
			errors.reject(null, "尚未輸入用車事由");
		}
		if (StringUtils.isBlank(data.getDestination())) {
			errors.reject(null, "尚未輸入目的地");
		}
		if (StringUtils.isBlank(data.getNum_of_people())) {
			errors.reject(null, "尚未輸入人數");
		}
		if (StringUtils.isBlank(data.getUsing_date())) {
			errors.reject(null, "尚未輸入用車日期");
		}
		if (StringUtils.isBlank(data.getStarH())||StringUtils.isBlank(data.getStarM())||
				StringUtils.isBlank(data.getEndH())||StringUtils.isBlank(data.getEndM())
		) {
			errors.reject(null, "尚未輸入用車日期");
		}
		if (timeSart.compareTo(timeEnd)>0){
			errors.reject(null, "用車時間起始時間不可小於結束時間");
		}
		}catch (Exception e){
			log.error("驗證失敗，原因:{}", ExceptionUtility.getStackTrace(e));
		}
		}
	}

