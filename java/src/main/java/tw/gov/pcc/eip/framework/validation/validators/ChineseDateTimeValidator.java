package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.ChineseDateTime;
import tw.gov.pcc.eip.util.DateUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ChineseDateTimeValidator
 *
 * @author Goston
 */
public class ChineseDateTimeValidator implements ConstraintValidator<ChineseDateTime, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChineseDateTimeValidator.class);

    @Override
    public void initialize(ChineseDateTime parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            if (StringUtils.length(value) != 11 && StringUtils.length(value) != 13) return false;
            String date = DateUtility.splitChineseDateTime(value, true);
            String time = DateUtility.splitChineseDateTime(value, false);
            return (DateUtility.isValidDate(date) && DateUtility.isValidTime(time));
        } catch (Exception e) {
            log.error("@ChineseDateTimeValidator Validator 發生錯誤");
            return false;
        }
    }
}
