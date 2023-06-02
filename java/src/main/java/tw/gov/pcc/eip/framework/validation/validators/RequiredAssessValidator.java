package tw.gov.pcc.eip.framework.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tw.gov.pcc.eip.framework.validation.RequiredAssess;

public class RequiredAssessValidator implements ConstraintValidator<RequiredAssess, Boolean> {
	
	private boolean result;
	
	@Override
	public void initialize(RequiredAssess constraintAnnotation) {
		this.result = constraintAnnotation.result();
	}

	@Override
	public boolean isValid(Boolean value, ConstraintValidatorContext context) {
		return value == null || Boolean.logicalAnd(value, result);
	}

}
