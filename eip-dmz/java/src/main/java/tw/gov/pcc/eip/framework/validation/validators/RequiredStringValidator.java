package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.RequiredString;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * RequiredStringValidator
 *
 * @author Goston
 */
public class RequiredStringValidator implements ConstraintValidator<RequiredString, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequiredStringValidator.class);
    private boolean trim;

    @Override
    public void initialize(RequiredString parameters) {
        trim = parameters.trim();
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) return false;
        String value = str.toString();
        if (trim) value = StringUtils.trimToEmpty(value);
        return StringUtils.length(value) > 0;
    }
}
