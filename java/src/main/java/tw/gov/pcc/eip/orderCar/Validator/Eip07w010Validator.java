package tw.gov.pcc.eip.orderCar.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.StringUtility;

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
	public void driverValidate(Eip07w010Case Validate, Errors errors) {
		if (StringUtils.isBlank(Validate.getName())){
			errors.reject(null, "尚未輸入駕駛人姓名");
		} else if (StringUtils.isBlank(Validate.getCellphone())) {
			errors.reject(null, "尚未輸入手機號碼");
		}else if (StringUtils.isBlank(Validate.getId())) {
			errors.reject(null, "尚未輸入身分證號(駕照證號)");
		}else if (Validate.getId().length()!=10) {
			errors.reject(null, "身分證號格式不符");
		}else if (StringUtils.isBlank(Validate.getCarno1())||StringUtils.isBlank(Validate.getCarno2())) {
			errors.reject(null, "尚未輸入預定保管車號");
		}else if (StringUtils.isNotBlank(Validate.getBrdte())) {
			if (!DateUtility.isValidDate(Validate.getBrdte(), false)){
			errors.reject(null, "出生日期請輸入民國年月日");}
		}else if (StringUtils.isNotBlank(Validate.getStartworkDate())) {
			if (!DateUtility.isValidDate(Validate.getStartworkDate(), false)) {
				errors.reject(null, "到職日期請輸入民國年月日");}
		}else if (StringUtils.isNotBlank(Validate.getEndworkDate())) {
			if (!DateUtility.isValidDate(Validate.getEndworkDate(), false)) {
				errors.reject(null, "離職日期請輸入民國年月日");}
		}else if (StringUtils.isNotBlank(Validate.getLicenceExpireDate())) {
			if (!DateUtility.isValidDate(Validate.getLicenceExpireDate(), false)) {
				errors.reject(null, "駕照到期日請輸入民國年月日");}
		}else if (StringUtils.isNotBlank(Validate.getCellphone())){
			if (Validate.getCellphone().length()!=10){
				errors.reject(null, "手機號碼需輸入10位");}
		}else if (StringUtils.isNotBlank(Validate.getPhone())){
			if (Validate.getPhone().length()!=10){
				errors.reject(null, "電話需輸入10位");}
		}
	}


	public void carValidate(Eip07w010Case Validate, Errors errors) {
		if (StringUtils.isBlank(Validate.getOwned())){
			errors.reject(null, "尚未輸入財產編號");
		} else if (StringUtils.isBlank(Validate.getCarno1())||StringUtils.isBlank(Validate.getCarno2())) {
			errors.reject(null, "尚未輸入車牌號碼");
		}else if (StringUtils.isNotBlank(Validate.getInsuranceStart())) {
			if (!DateUtility.isValidDate(Validate.getInsuranceStart(), false)) {
				errors.reject(null, "保險期間(起)請輸入民國年月日");}
		}else if (StringUtils.isNotBlank(Validate.getInsuranceEnd())) {
			if (!DateUtility.isValidDate(Validate.getInsuranceEnd(), false)) {
				errors.reject(null, "保險期間(迄)請輸入民國年月日");}
		}
		}
	}

