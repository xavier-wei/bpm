package tw.gov.pcc.eip.apply.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.util.StringUtility;

@Component
public class Eip08w060CaseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Eip08w060Case.class.isAssignableFrom(clazz);
	}

	public void addError(Object target, Errors errors) {
	}

	@Override
	public void validate(Object target, Errors errors) {
		Eip08w060Case caseData = (Eip08w060Case) target;
		for (Eip08w060Case data:caseData.getEip08w060CaseList()) {
			if (StringUtils.isNotBlank(data.getItem())){
				if (StringUtils.isBlank(data.getDesc_memo())){
				errors.reject(null, "用途說明尚未輸入");
				}
				if(StringUtils.isBlank(data.getCnt())){
				errors.reject(null, "數量尚未輸入");
				}
				if(StringUtility.stringRealLength(data.getDesc_memo())>200){
				errors.reject(null, "用途說明文字為100字");
				}
				if(StringUtility.stringRealLength(data.getItem())>100){
				errors.reject(null, "品名及規格上限為50字");
				}
				if(StringUtility.stringRealLength(data.getUnit())>8){
					errors.reject(null, "單位上限為4字");
				}
			}
		}

	}
}
