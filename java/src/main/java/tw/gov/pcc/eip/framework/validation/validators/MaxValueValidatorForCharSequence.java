package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.MaxValue;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * MaxValueValidatorForCharSequence
 *
 * @author Goston
 */
public class MaxValueValidatorForCharSequence implements ConstraintValidator<MaxValue, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MaxValueValidatorForCharSequence.class);
    private BigDecimal maxValue;

    @Override
    public void initialize(MaxValue parameters) {
        this.maxValue = BigDecimal.valueOf(parameters.value());
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) {
            return true;
        }
        try {
            return new BigDecimal(str.toString()).compareTo(maxValue) != 1;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
