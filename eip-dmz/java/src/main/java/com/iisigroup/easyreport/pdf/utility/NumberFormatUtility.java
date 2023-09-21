package com.iisigroup.easyreport.pdf.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import com.iisigroup.easyreport.pdf.Helper.EnvHelper;
import com.iisigroup.easyreport.pdf.exception.ReportException;

/**
 * 數值格式化之相關功能<br>
 * 可使用於報表中數值欄位之三位一撇功能，如將 <code>1000000</code> 格式化為 <code>1,000,000</code>
 * 
 * @author Goston
 */
public class NumberFormatUtility {

    /**
     * 以預設 Pattern 格式化傳入的數值<br>
     * 預設 Pattern 可於 <code>easyreport.properties</code> 中以 <code>default.pdf.decimal.format</code> 指定<br>
     * 若未於 <code>easyreport.properties</code> 設定，則預設的 Pattern 為 <code>#,###,##0.00</code>
     * 
     * @param value 欲格式化的數值
     * @return 格式化後之字串
     */
    public static String formatDecimal(double value) {
        return formatDecimal(new BigDecimal(value));
    }

    /**
     * 以自訂的 Pattern 格式化傳入的數值
     * 
     * @param value 欲格式化的數值
     * @param pattern Pattern 字串，如: <code>#,###,##0.00</code>，若傳入 <code>null</code> 或空值會使用預設 Pattern
     * @return 格式化後之字串
     */
    public static String formatDecimal(double value, String pattern) {
        return formatDecimal(new BigDecimal(value), pattern);
    }

    /**
     * 以預設 Pattern 格式化傳入的數值<br>
     * 預設 Pattern 可於 <code>easyreport.properties</code> 中以 <code>default.pdf.decimal.format</code> 指定<br>
     * 若未於 <code>easyreport.properties</code> 設定，則預設的 Pattern 為 <code>#,###,##0.00</code>
     * 
     * @param value 欲格式化的數值
     * @return 格式化後之字串，若傳入的 <code>value</code> 是 <code>null</code> 則回傳值亦是 <code>null</code>
     */
    public static String formatDecimal(BigDecimal value) {
        if (value == null)
            return null;

        return formatDecimal(value, EnvHelper.getDecimalFormat());
    }

    /**
     * 以自訂的 Pattern 格式化傳入的數值
     * 
     * @param value 欲格式化的數值
     * @param pattern Pattern 字串，如: <code>#,###,##0.00</code>，若傳入 <code>null</code> 或空值會使用預設 Pattern
     * @return 格式化後之字串，若傳入的 <code>value</code> 是 <code>null</code> 則回傳值亦是 <code>null</code>
     */
    public static String formatDecimal(BigDecimal value, String pattern) {
        if (value == null)
            return null;

        if (StringUtils.isBlank(pattern))
            pattern = EnvHelper.getDecimalFormat();

        int scale = StringUtils.length(StringUtils.substring(pattern, StringUtils.lastIndexOf(pattern, ".") + 1, StringUtils.length(pattern)));

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value.setScale(scale, BigDecimal.ROUND_FLOOR));
    }

    /**
     * 以預設 Pattern 格式化傳入的數值<br>
     * 預設 Pattern 可於 <code>easyreport.properties</code> 中以 <code>default.pdf.integer.format</code> 指定<br>
     * 若未於 <code>easyreport.properties</code> 設定，則預設的 Pattern 為 <code>#,###,##0</code>
     * 
     * @param value 欲格式化的數值
     * @return 格式化後之字串
     */
    public static String formatNumber(double value) {
        return formatNumber(new BigDecimal(value));
    }

    /**
     * 以自訂的 Pattern 格式化傳入的數值
     * 
     * @param value 欲格式化的數值
     * @param pattern Pattern 字串，如: <code>#,###,##0</code>，若傳入 <code>null</code> 或空值會使用預設 Pattern
     * @return 格式化後之字串
     */
    public static String formatNumber(double value, String pattern) {
        return formatNumber(new BigDecimal(value), pattern);
    }

    /**
     * 以預設 Pattern 格式化傳入的數值<br>
     * 預設 Pattern 可於 <code>easyreport.properties</code> 中以 <code>default.pdf.integer.format</code> 指定<br>
     * 若未於 <code>easyreport.properties</code> 設定，則預設的 Pattern 為 <code>#,###,##0</code>
     * 
     * @param value 欲格式化的數值
     * @return 格式化後之字串，若傳入的 <code>value</code> 是 <code>null</code> 則回傳值亦是 <code>null</code>
     */
    public static String formatNumber(BigDecimal value) {
        if (value == null)
            return null;

        return formatNumber(value, EnvHelper.getIntegerFormat());
    }

    /**
     * 以自訂的 Pattern 格式化傳入的數值
     * 
     * @param value 欲格式化的數值
     * @param pattern Pattern 字串，如: <code>#,###,##0</code>，若傳入 <code>null</code> 或空值會使用預設 Pattern
     * @return 格式化後之字串，若傳入的 <code>value</code> 是 <code>null</code> 則回傳值亦是 <code>null</code>
     */
    public static String formatNumber(BigDecimal value, String pattern) {
        if (value == null)
            return null;

        if (StringUtils.isBlank(pattern))
            pattern = EnvHelper.getIntegerFormat();

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value.setScale(0, BigDecimal.ROUND_FLOOR));
    }

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})$"); // 不考慮分隔符的正確性
    private static final char[] TWD_NUMS = "零壹貳參肆伍陸柒捌玖".toCharArray();
    private static final String[] UNITS = { "元", "角", "分", "整" };
    private static final String[] U1 = { "", "拾", "佰", "仟" };
    private static final String[] U2 = { "", "萬", "億" };

    /**
     * 將金額轉為中文大寫國字（可到千億，只支援正整數，無法算到角）
     * 
     * @param money 金額數字字串
     * @param padding 是否於結尾加入「整」字
     * @return 中文大寫金額
     */
    public static String toChineseCurrency(String money, boolean padding) {
        if (StringUtils.isBlank(money))
            return "";

        try {
            return toChineseCurrency(new BigDecimal(money), padding);
        }
        catch (Exception e) {
            throw new ReportException("轉換為中文大寫金額發生錯誤，金額：" + StringUtils.trimToEmpty(money));
        }
    }

    /**
     * 將金額轉為中文大寫國字（可到千億，只支援正整數，無法算到角）
     * 
     * @param money 金額數字字串
     * @param padding 是否於結尾加入「整」字
     * @return 中文大寫金額
     */
    public static String toChineseCurrency(BigDecimal money, boolean padding) {
        String amount = "";

        if (money != null)
            amount = money.toPlainString();

        if (StringUtils.isBlank(amount))
            return "";

        // 去掉分隔符
        amount = amount.replace(",", "");

        // 驗證金額正確性
        if (Long.parseLong(amount) == 0l) {
            return "零元整";
        }
        Matcher matcher = AMOUNT_PATTERN.matcher(amount);
        if (!matcher.find()) {
            throw new ReportException("金額有誤，金額：" + StringUtils.trimToEmpty(amount));
        }

        String integer = matcher.group(1); // 整數部分

        String result = "";
        if (!integer.equals("0")) {
            result += integer2TWD(integer) + UNITS[0]; // 整數部分
        }

        if (padding)
            return result + "整";
        else
            return result;
    }

    /**
     * 將金額整數部分轉換為中文大寫
     * 
     * @param integer
     * @return
     */
    private static String integer2TWD(String integer) {
        StringBuilder buffer = new StringBuilder();
        // 從個位數開始轉換
        int i, j;
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
            char n = integer.charAt(i);
            if (n == '0') {
                // 當n是0且n的右邊一位不是0時，插入[零]
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
                    buffer.append(TWD_NUMS[0]);
                }
                // 插入[萬]或者[億]
                if (j % 4 == 0) {
                    if (i > 0 && integer.charAt(i - 1) != '0' || i > 1 && integer.charAt(i - 2) != '0' || i > 2 && integer.charAt(i - 3) != '0') {
                        buffer.append(U2[j / 4]);
                        String temp = buffer.toString();
                        if (StringUtils.substring(temp, (temp.length() - 4), (temp.length() - 3)).equals(U1[3]))
                            buffer.deleteCharAt(temp.length() - 2);
                    }
                }
            }
            else {
                if (j % 4 == 0) {
                    buffer.append(U2[j / 4]); // 插入[萬]或者[億]
                }
                buffer.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
                buffer.append(TWD_NUMS[n - '0']); // 插入數字
            }
        }
        return buffer.reverse().toString();
    }

}
