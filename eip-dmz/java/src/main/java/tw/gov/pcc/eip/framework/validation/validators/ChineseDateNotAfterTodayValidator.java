package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.ChineseDateNotAfterToday;
import tw.gov.pcc.eip.util.DateUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ChineseDateNotAfterTodayValidator
 *
 * @author Goston
 */
public class ChineseDateNotAfterTodayValidator implements ConstraintValidator<ChineseDateNotAfterToday, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChineseDateNotAfterTodayValidator.class);

    @Override
    public void initialize(ChineseDateNotAfterToday parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value)) || !DateUtility.isValidDate(value)) // 日期格式有錯則不進行檢核
         return true;
        try {
            return Integer.parseInt(value) <= Integer.parseInt(DateUtility.getNowChineseDate());
        } catch (Exception e) {
            // Should not happen
            log.error("@ChineseDateNotAfterToday Validator 發生錯誤");
            return true;
        }
    }
}
