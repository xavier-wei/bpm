package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.MaxValue;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * MaxValueValidatorForNumber
 *
 * @author Goston
 */
public class MaxValueValidatorForNumber implements ConstraintValidator<MaxValue, Number> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MaxValueValidatorForNumber.class);
    private long maxValue;

    @Override
    public void initialize(MaxValue parameters) {
        this.maxValue = parameters.value();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(maxValue)) != 1;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(maxValue)) != 1;
        } else {
            long longValue = value.longValue();
            return longValue <= maxValue;
        }
    }
}
