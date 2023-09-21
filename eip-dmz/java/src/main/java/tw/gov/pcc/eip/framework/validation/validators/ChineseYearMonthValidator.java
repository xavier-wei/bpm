package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.ChineseYearMonth;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ChineseYearMonthValidator
 *
 * @author Goston
 */
public class ChineseYearMonthValidator implements ConstraintValidator<ChineseYearMonth, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChineseYearMonthValidator.class);
    private static final String PATTERN = "^([0-9]{3})(0[1-9]|1[0-2])$";

    @Override
    public void initialize(ChineseYearMonth parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            if (!RegexUtility.isMatches(value, PATTERN)) {
                return false;
            }
            // 年度不可能是 000 年
            return !StringUtils.equals(StringUtils.substring(value, 0, 3), "000");
        } catch (Exception e) {
            log.error("@ChineseYearMonth Validator 發生錯誤");
            return false;
        }
    }
}
