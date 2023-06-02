package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.MinValue;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * MinValueValidatorForCharSequence
 *
 * @author Goston
 */
public class MinValueValidatorForCharSequence implements ConstraintValidator<MinValue, CharSequence> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MinValueValidatorForCharSequence.class);
    private BigDecimal minValue;

    @Override
    public void initialize(MinValue parameters) {
        this.minValue = BigDecimal.valueOf(parameters.value());
    }

    @Override
    public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) {
            return true;
        }
        try {
            return new BigDecimal(str.toString()).compareTo(minValue) != -1;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
