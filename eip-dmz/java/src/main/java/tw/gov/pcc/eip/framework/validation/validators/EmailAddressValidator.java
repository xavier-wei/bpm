package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.EmailAddress;
import tw.gov.pcc.eip.framework.validation.support.RegexUtility;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * EmailAddressValidator
 *
 * @author Goston
 */
public class EmailAddressValidator implements ConstraintValidator<EmailAddress, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmailAddressValidator.class);
    private static final String EMAIL_ADDRESS_PATTERN = "\\b^[\'_a-z0-9-\\+]+(\\.[\'_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|taipei|blog|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx|tech|cat)$\\b";
    private boolean trim;

    @Override
    public void initialize(EmailAddress parameters) {
        trim = parameters.trim();
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return true;
        String value = str.toString();
        if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return true;
        if (trim) value = StringUtils.trimToEmpty(value);
        return RegexUtility.isMatches(value, EMAIL_ADDRESS_PATTERN, false);
    }
}
