package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.IdNoFormat;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * IdNoFormatValidator
 *
 * @author Goston
 */
public class IdNoFormatValidator implements ConstraintValidator<IdNoFormat, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IdNoFormatValidator.class);

    @Override
    public void initialize(IdNoFormat parameters) {
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        try {
            return isValidIdNoChar(value);
        } catch (Exception e) {
            log.error("@IdNoFormat Validator 發生錯誤");
            return false;
        }
    }

    private static boolean isValidIdNoChar(String idNo) {
        if (StringUtils.length(idNo) != 10) return false;
        String sIDN = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        String sNUM = "0123456789";
        // 首碼不是英文字母
        if (StringUtils.indexOf(sIDN, idNo.charAt(0)) == -1) {
            return false;
        }
        // 第二碼不是 1 或 2
        if (!StringUtils.equals(idNo.substring(1, 2), "1") && !StringUtils.equals(idNo.substring(1, 2), "2")) {
            return false;
        }
        // 其餘各碼須為數字
        for (int i = 2; i < idNo.length(); i++) {
            if (StringUtils.indexOf(sNUM, idNo.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}
