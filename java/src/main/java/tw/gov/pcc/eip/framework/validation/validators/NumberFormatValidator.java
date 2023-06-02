package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.NumberFormat;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import tw.gov.pcc.eip.util.StringUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * NumberFormatValidator
 *
 * @author Goston
 */
public class NumberFormatValidator implements ConstraintValidator<NumberFormat, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NumberFormatValidator.class);
    private int integer;
    private int decimal;

    @Override
    public void initialize(NumberFormat parameters) {
        integer = parameters.integer();
        decimal = parameters.decimal();
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        // 若 integer 和 decimal > 0 才驗
        if (integer < 1 || decimal < 1) return true;
        String pattern = "^\\d{1," + integer + "}\\.\\d{1," + decimal + "}$";
        try {
            if (RegexUtility.isMatches(value, pattern)) {
                return true;
            }
        } catch (Exception e) {
            log.error("@NumberFormat Validator 發生錯誤，請檢查 pattern 的格式是否設定正確，目前所指定的 pattern 為：{}", StringUtility.safeLog(StringUtils.defaultString(pattern, "null 或 空值")));
            return false;
        }
        return false;
    }
}
