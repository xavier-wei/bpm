package tw.gov.pcc.eip.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberUtility {

    // alternative to Double.valueOf(String), (IMPORTANT!!!!) but input string must be validated outside or else return 0
    public static Double toDouble(String input) {
        return NumberUtils.toDouble(input);
    }

    // alternative to Double.valueOf(double)
    public static Double toDouble(double input) {
        return NumberUtils.toDouble(String.valueOf(input));
    }

    // alternative to BigDecimal.doubleValue(BigDecimal)
    public static double toDouble(BigDecimal bigDecimal) {
        return toDouble(Optional.of(bigDecimal)
                .get()
                .toString());
    }

    public static BigDecimal nvl(final BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }
    
    public static BigDecimal toBigdecimal(String input) {
        return StringUtils.isBlank(input) ? null : new BigDecimal(input);
    }
}
