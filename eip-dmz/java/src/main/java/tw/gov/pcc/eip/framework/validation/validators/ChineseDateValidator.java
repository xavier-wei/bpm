package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.ChineseDate;
import tw.gov.pcc.eip.util.DateUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ChineseDateValidator
 *
 * @author Goston
 */
public class ChineseDateValidator implements ConstraintValidator<ChineseDate, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChineseDateValidator.class);

    @Override
    public void initialize(ChineseDate parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            boolean bBeforeROC = false;
            if ((StringUtils.length(value) == 8 && !StringUtils.startsWith(value, "-"))) {
                return false;
            }
            if (Integer.parseInt(value) < 0) bBeforeROC = true;
            return DateUtility.isValidDate(value.replace("-", ""), bBeforeROC);
        } catch (Exception e) {
            log.error("@ChineseDate Validator 發生錯誤");
            return false;
        }
    }
}
