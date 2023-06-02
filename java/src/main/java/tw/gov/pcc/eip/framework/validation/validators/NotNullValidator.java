package tw.gov.pcc.eip.framework.validation.validators;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.framework.validation.NotNull;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * NotNullValidator
 *
 * @author swho
 */
public class NotNullValidator implements ConstraintValidator<NotNull, Object> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NotNullValidator.class);

    @Override
    public void initialize(NotNull parameters) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (obj == null) return false;
        String value = obj.toString();
        return !StringUtils.isBlank(StringUtils.trimToEmpty(value));
    }
}
