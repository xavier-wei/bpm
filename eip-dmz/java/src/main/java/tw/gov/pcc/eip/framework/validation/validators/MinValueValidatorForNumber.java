package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.MinValue;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * MinValueValidatorForNumber
 *
 * @author Goston
 */
public class MinValueValidatorForNumber implements ConstraintValidator<MinValue, Number> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MinValueValidatorForNumber.class);
    private long minValue;

    @Override
    public void initialize(MinValue parameters) {
        this.minValue = parameters.value();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(minValue)) != -1;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(minValue)) != -1;
        } else {
            double longValue = value.doubleValue();
            return longValue >= minValue;
        }
    }
}
