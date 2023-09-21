package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.RequiredInteger;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * RequiredIntegerValidator
 *
 * @author Goston
 */
public class RequiredIntegerValidator implements ConstraintValidator<RequiredInteger, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequiredIntegerValidator.class);
    private static final String PATTERN = "^-?\\d*$";

    @Override
    public void initialize(RequiredInteger parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            return RegexUtility.isMatches(value, PATTERN);
        } catch (Exception e) {
            log.error("@RequiredInteger Validator 發生錯誤");
            return false;
        }
    }
}
