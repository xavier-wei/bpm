package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.ChineseYearMonthNotAfterThisMonth;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import tw.gov.pcc.eip.util.DateUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ChineseYearMonthNotAfterThisMonthValidator
 *
 * @author Goston
 */
public class ChineseYearMonthNotAfterThisMonthValidator implements ConstraintValidator<ChineseYearMonthNotAfterThisMonth, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChineseYearMonthNotAfterThisMonthValidator.class);
    private static final String PATTERN = "^([0-9]{3})(0[1-9]|1[0-2])$";

    @Override
    public void initialize(ChineseYearMonthNotAfterThisMonth parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        // 年月格式有錯則不進行檢核
        try {
            if (!RegexUtility.isMatches(value, PATTERN)) {
                return true;
            }
            // 年度不可能是 000 年
            if (StringUtils.equals(StringUtils.substring(value, 0, 3), "000")) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        try {
            return Integer.parseInt(value) <= Integer.parseInt(DateUtility.getNowChineseYearMonth());
        } catch (Exception e) {
            // Should not happen
            log.error("@ChineseYearMonthNotAfterThisMonth Validator 發生錯誤");
            return true;
        }
    }
}
