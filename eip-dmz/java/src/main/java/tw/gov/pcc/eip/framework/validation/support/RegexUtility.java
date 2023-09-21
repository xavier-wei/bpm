package tw.gov.pcc.eip.framework.validation.support;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regular Expression Utility
 *
 * @author Goston
 */
public class RegexUtility {

    /**
     * 傳入的 <code>value</code> 是否符合指定的 <code>expression</code> (區分大小寫)
     *
     * @param value      要比較的字串
     * @param expression Regular Expression
     * @return 注意：若傳入的 <code>value</code> 為 <code>null</code> 或 空值 將回傳 <code>true</code>
     */
    public static boolean isMatches(String value, String expression) {
        return isMatches(value, expression, true);
    }

    /**
     * 傳入的 <code>value</code> 是否符合指定的 <code>expression</code>
     *
     * @param value         要比較的字串
     * @param expression    Regular Expression
     * @param caseSensitive 是否區分大小寫
     * @return 注意：若傳入的 <code>value</code> 為 <code>null</code> 或 空值 將回傳 <code>true</code>
     */
    public static boolean isMatches(String value, String expression, boolean caseSensitive) {
        if (StringUtils.isBlank(value))
            return true;

        Pattern pattern;
        if (caseSensitive)
            pattern = Pattern.compile(expression);
        else
            pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }

}
