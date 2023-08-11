package tw.gov.pcc.eip.orderCar.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.StringUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Eip07w010Validator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Eip08w060Case.class.isAssignableFrom(clazz);
	}

	public void addError(Object target, Errors errors) {
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public void driverValidate(Eip07w010Case Validate, Errors errors) {
		if (StringUtils.isBlank(Validate.getName())){
			errors.reject(null, "尚未輸入駕駛人姓名");
		}
			if (StringUtils.isBlank(Validate.getCellphone())) {
			errors.reject(null, "尚未輸入手機號碼");
		}
			if (StringUtils.isBlank(Validate.getId())) {
			errors.reject(null, "尚未輸入身分證號(駕照證號)");
		}
			if (Validate.getId().length()!=10) {
			errors.reject(null, "身分證號格式不符");
		}
			if (StringUtils.isBlank(Validate.getCarno())) {
			errors.reject(null, "尚未輸入預定保管車號");
		}
			if (StringUtils.isNotBlank(Validate.getBrdte())) {
			if (!DateUtility.isValidDate(Validate.getBrdte(), false)){
			errors.reject(null, "出生日期請輸入民國年月日");}
		}
			if (StringUtils.isNotBlank(Validate.getStartworkDate())) {
			if (!DateUtility.isValidDate(Validate.getStartworkDate(), false)) {
				errors.reject(null, "到職日期請輸入民國年月日");}
		}
			if (StringUtils.isNotBlank(Validate.getEndworkDate())) {
			if (!DateUtility.isValidDate(Validate.getEndworkDate(), false)) {
				errors.reject(null, "離職日期請輸入民國年月日");}
		}
			if (StringUtils.isNotBlank(Validate.getLicenceExpireDate())) {
			if (!DateUtility.isValidDate(Validate.getLicenceExpireDate(), false)) {
				errors.reject(null, "駕照到期日請輸入民國年月日");}
		}
			if (StringUtils.isNotBlank(Validate.getCellphone())){
			if (Validate.getCellphone().length()!=10){
				errors.reject(null, "手機號碼需輸入10位");}
		}
			if (StringUtils.isNotBlank(Validate.getPhone())){
			if (Validate.getPhone().length()!=10){
				errors.reject(null, "電話需輸入10位");}
		}
			if (StringUtils.isBlank(Validate.getStartworkDate())){
			errors.reject(null, "尚未輸入到職日期");
			}

		if ("N".equals(Validate.getStillWork())){
			if (StringUtils.isBlank(Validate.getEndworkDate())){
				errors.reject(null, "尚未輸入離職日期");
			}

		}

		}





	public void carValidate(Eip07w010Case validate, Errors errors) throws ParseException {
//		Date insuranceS = format.parse(DateUtility.changeDateType(validate.getInsuranceStart()));
//		Date insuranceE = format.parse(DateUtility.changeDateType(validate.getInsuranceEnd()));
		if (StringUtils.isBlank(validate.getOwned())){
			errors.reject(null, "尚未輸入財產編號");
		}
		if (StringUtils.isBlank(validate.getCarno1())||StringUtils.isBlank(validate.getCarno2())) {
			errors.reject(null, "尚未輸入車牌號碼");
		}
		if (StringUtils.isNotBlank(validate.getInsuranceStart())) {
			if (!DateUtility.isValidDate(validate.getInsuranceStart(), false)) {
				errors.reject(null, "保險期間(起)請輸入民國年月日");}
		}
		if (StringUtils.isNotBlank(validate.getInsuranceEnd())) {
			if (!DateUtility.isValidDate(validate.getInsuranceEnd(), false)) {
				errors.reject(null, "保險期間(迄)請輸入民國年月日");
			}
			if (Integer.parseInt(validate.getInsuranceStart())>Integer.parseInt(validate.getInsuranceEnd())) {
				errors.reject(null, "保險期間(起)不可大於(迄)");
			}
		}
	}

	}

