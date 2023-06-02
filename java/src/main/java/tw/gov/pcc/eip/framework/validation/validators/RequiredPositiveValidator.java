package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.RequiredPositive;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * RequiredPositiveValidator
 *
 * @author Goston
 */
public class RequiredPositiveValidator implements ConstraintValidator<RequiredPositive, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequiredPositiveValidator.class);
    private static final String PATTERN = "^\\d*$";

    @Override
    public void initialize(RequiredPositive parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            return RegexUtility.isMatches(value, PATTERN);
        } catch (Exception e) {
            log.error("@RequiredPositive Validator 發生錯誤");
            return false;
        }
    }
}
