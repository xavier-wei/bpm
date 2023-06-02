package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.StringLength;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * StringLengthValidator
 *
 * @author Goston
 */
public class StringLengthValidator implements ConstraintValidator<StringLength, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StringLengthValidator.class);
    private static final String DEFAULT_MESSAGE_KEY = "{validation.stringLength.message}";
    private int min;
    private int max;
    private boolean trim;
    private String message;

    @Override
    public void initialize(StringLength parameters) {
        min = parameters.min();
        max = parameters.max();
        trim = parameters.trim();
        message = parameters.message();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        if (min == 0 && max == 0) return true;
        int length = StringUtils.length(value);
        if (trim) length = StringUtils.length(StringUtils.trimToEmpty(value));
        if (min > 0 && max == 0) {
            if (StringUtils.equalsIgnoreCase(message, DEFAULT_MESSAGE_KEY)) {
                // 表示沒有自訂訊息
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("{validation.stringLength.min.message}").addConstraintViolation();
            }
            return length >= min;
        } else if (min == 0 && max > 0) {
            if (StringUtils.equalsIgnoreCase(message, DEFAULT_MESSAGE_KEY)) {
                // 表示沒有自訂訊息
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("{validation.stringLength.max.message}").addConstraintViolation();
            }
            return length <= max;
        } else {
            if (min == max) {
                if (StringUtils.equalsIgnoreCase(message, DEFAULT_MESSAGE_KEY)) {
                    // 表示沒有自訂訊息
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate("{validation.stringLength.equals.message}").addConstraintViolation();
                }
            }
            return length >= min && length <= max;
        }
    }

    private void validateParameters() {
        if (min < 0) min = 0;
        if (max < 0) max = 0;
        if ((min > max) && max > 0) {
            int temp = min;
            min = max;
            max = temp;
        }
    }
}
