package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.Time;
import tw.gov.pcc.eip.util.DateUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * TimeValidator
 *
 * @author Goston
 */
public class TimeValidator implements ConstraintValidator<Time, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TimeValidator.class);

    @Override
    public void initialize(Time parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            if (StringUtils.length(value) != 4 && StringUtils.length(value) != 6) return false;
            return DateUtility.isValidTime(value);
        } catch (Exception e) {
            log.error("@Time Validator 發生錯誤");
            return false;
        }
    }
}
