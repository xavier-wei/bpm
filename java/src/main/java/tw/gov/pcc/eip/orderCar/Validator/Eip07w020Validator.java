package tw.gov.pcc.eip.orderCar.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;
import tw.gov.pcc.eip.util.DateUtility;

@Component
public class Eip07w020Validator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Eip08w060Case.class.isAssignableFrom(clazz);
	}

	public void addError(Object target, Errors errors) {
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	public void eip07w020QValidate(Eip07w020Case validate, Errors errors) {
		if (StringUtils.isBlank(validate.getApplyName())) {
			errors.reject(null, "尚未輸入申請人");
		} else if (StringUtils.isBlank(validate.getApplyUnit())) {
			errors.reject(null, "尚未輸入申請單位");
		} else if ("A".equals(validate.getWorkTy())) {
			if (StringUtils.isBlank(validate.getApplyDate())) {
				errors.reject(null, "尚未輸入申請日期");
			}
			if (!DateUtility.isValidDate(validate.getApplyDate(), false)) {
				errors.reject(null, "申請日期請輸入民國年月日");
			}
		}
	}

	public void eip07w020AValidate(Eip07w020Case validate, Errors errors) {
		if (StringUtils.isBlank(validate.getUseCarMemo())) {
			errors.reject(null, "尚未輸入用車事由");
		} else if (StringUtils.isBlank(validate.getDestination())) {
			errors.reject(null, "尚未輸入目的地");
		} else if (StringUtils.isBlank(validate.getNumber())) {
			errors.reject(null, "尚未輸入人數");
		} else if (StringUtils.isBlank(validate.getUseDate())) {
			errors.reject(null, "尚未輸入用車日期");
		}else if (StringUtils.isBlank(validate.getStarH())||StringUtils.isBlank(validate.getStarM())||
				StringUtils.isBlank(validate.getEndH())||StringUtils.isBlank(validate.getEndM())
		) {
			errors.reject(null, "尚未輸入用車日期");
		}
	}

	public void eip07w020DValidate(Eip07w020Case date, Errors errors) {
		CarBooking oldData=date.getDetailsList().get(0);
		CarBooking 	rmData=date.getChangeMkList().get(0);
		updateValidate( oldData, errors);
//		if (StringUtils.isBlank(oldData.getApply_memo())) {
//			errors.reject(null, "尚未輸入用車事由");
//		} else if (StringUtils.isBlank(oldData.getDestination())) {
//			errors.reject(null, "尚未輸入目的地");
//		} else if (StringUtils.isBlank(oldData.getNum_of_people())) {
//			errors.reject(null, "尚未輸入人數");
//		} else if (StringUtils.isBlank(oldData.getUsing_date())) {
//			errors.reject(null, "尚未輸入用車日期");
//		}else if (StringUtils.isBlank(oldData.getStarH())||StringUtils.isBlank(oldData.getStarM())||
//				StringUtils.isBlank(oldData.getEndH())||StringUtils.isBlank(oldData.getEndM())
//		) {
//			errors.reject(null, "尚未輸入用車日期");
//		}

		//判斷異動申請相關資料
		if ("true".equals(date.getCheckMk())){
			if (StringUtils.isBlank(rmData.getApply_memo())) {
				errors.reject(null, "尚未輸入異動申請_用車事由");
			} else if (StringUtils.isBlank(rmData.getDestination())) {
				errors.reject(null, "尚未輸入異動申請_目的地");
			} else if (StringUtils.isBlank(rmData.getNum_of_people())) {
				errors.reject(null, "尚未輸入異動申請_人數");
			} else if (StringUtils.isBlank(rmData.getStarH())||StringUtils.isBlank(rmData.getStarM())||
					StringUtils.isBlank(rmData.getEndH())||StringUtils.isBlank(rmData.getEndM())
			) {
				errors.reject(null, "尚未輸入異動申請_用車日期");
			}
		}

	}

	public void updateValidate(CarBooking data, Errors errors) {
		if (StringUtils.isBlank(data.getApply_memo())) {
			errors.reject(null, "尚未輸入用車事由");
		} else if (StringUtils.isBlank(data.getDestination())) {
			errors.reject(null, "尚未輸入目的地");
		} else if (StringUtils.isBlank(data.getNum_of_people())) {
			errors.reject(null, "尚未輸入人數");
		} else if (StringUtils.isBlank(data.getUsing_date())) {
			errors.reject(null, "尚未輸入用車日期");
		}else if (StringUtils.isBlank(data.getStarH())||StringUtils.isBlank(data.getStarM())||
				StringUtils.isBlank(data.getEndH())||StringUtils.isBlank(data.getEndM())
		) {
			errors.reject(null, "尚未輸入用車日期");
		}
		}
	}

