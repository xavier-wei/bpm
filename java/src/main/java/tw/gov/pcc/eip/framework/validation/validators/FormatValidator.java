package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.Format;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import tw.gov.pcc.eip.util.StringUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * FormatValidator
 *
 * @author Goston
 */
public class FormatValidator implements ConstraintValidator<Format, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FormatValidator.class);
    private boolean trim;
    private String pattern;
    private boolean caseSensitive;

    @Override
    public void initialize(Format parameters) {
        trim = parameters.trim();
        pattern = parameters.pattern();
        caseSensitive = parameters.caseSensitive();
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        if (trim) value = StringUtils.trimToEmpty(value);
        try {
            if (RegexUtility.isMatches(value, pattern, caseSensitive)) return true;
        } catch (Exception e) {
            log.error("@Format Validator 發生錯誤，請檢查 pattern 的格式是否設定正確，目前所指定的 pattern 為：{}", StringUtility.safeLog(StringUtils.defaultString(pattern, "null 或 空值")));
            return false;
        }
        return false;
    }
}
