package tw.gov.pcc.eip.framework.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ClassUtils;

import tw.gov.pcc.eip.framework.validation.FieldCompare;

public class FieldCompareValidator implements ConstraintValidator<FieldCompare, Object> {

	private boolean nullable;
	private String fromField;
	private String thenField;
	private FieldCompare.Operator operator;
	
	@Override
	public void initialize(FieldCompare constraintAnnotation) {
		this.nullable = constraintAnnotation.nullable();
		this.fromField = constraintAnnotation.fromField();
		this.thenField = constraintAnnotation.thenField();
		this.operator = constraintAnnotation.operator();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper b = new BeanWrapperImpl(value);
		Class<?> fromClass = b.getPropertyType(fromField);
		Class<?> thenClass = b.getPropertyType(thenField);
		Object fromObject = b.getPropertyValue(fromField);
		Object thenObject = b.getPropertyValue(thenField);
		
		if (fromClass.equals(thenClass) 
				&& ClassUtils.isAssignableValue(Comparable.class, fromObject) 
				&& ClassUtils.isAssignableValue(Comparable.class, thenObject)) {
			Comparable<? extends Object> fromValue = getComparable(fromObject);
			Comparable<? extends Object> thenValue = getComparable(thenObject);
			if (fromValue != null && thenValue != null) {
				return compare(fromValue, thenValue);
			}
			if (!nullable) {
				if (fromValue == null && thenValue != null) {
					context.disableDefaultConstraintViolation();
					context
					.buildConstraintViolationWithTemplate("{validation.fieldCompare.requiredFrom.message}")
					.addConstraintViolation();
					return false;
				} else if (fromValue != null) {
					context.disableDefaultConstraintViolation();
					context
					.buildConstraintViolationWithTemplate("{validation.fieldCompare.requiredThen.message}")
					.addConstraintViolation();
					return false;
				}
			}
		}
		return true;
	}
	
	private Comparable<?> getComparable(Object value) {
		if (value instanceof String) {
			return StringUtils.trimToNull((String)value);
		}
		return (Comparable<?>)value;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean compare(Comparable fromValue, Comparable thenValue) {
		switch (operator) {
		case EQUAL:
			return fromValue.compareTo(thenValue) == 0;
		case MORE_THAN:
			return fromValue.compareTo(thenValue) > 0;
		case MORE_THAN_OR_EQUAL:
			return fromValue.compareTo(thenValue) >= 0;
		case LESS_THAN:
			return fromValue.compareTo(thenValue) < 0;
		case LESS_THAN_OR_EQUAL:
			return fromValue.compareTo(thenValue) <= 0;
		}
		return false;
	}
	
}
