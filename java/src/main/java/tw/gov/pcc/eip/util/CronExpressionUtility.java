package tw.gov.pcc.eip.util;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.And;
import org.apache.commons.lang3.StringUtils;
import static com.cronutils.model.field.expression.FieldExpressionFactory.always;
import static com.cronutils.model.field.expression.FieldExpressionFactory.between;
import static com.cronutils.model.field.expression.FieldExpressionFactory.every;
import static com.cronutils.model.field.expression.FieldExpressionFactory.on;
import static com.cronutils.model.field.expression.FieldExpressionFactory.questionMark;

/**
 * Cron Expressions Utility
 *
 * @author Goston
 */
public class CronExpressionUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CronExpressionUtility.class);

    /**
     * 產生 Quartz Cron Expression
     * <p>
     * 注意：dayOfWeek 和 dayOfMonth 只能擇一指定
     * <p>
     * 範例
     * <p>
     * 呼叫：
     * <code>CronExpressionUtility.generateCronExpression(null, "1,3,5", null, null, null, "30", null);</code>
     * 將回傳：
     * <code>0 30 * ? * 1,3,5 *</code>
     *
     * @param year       西元年，傳入 <code>null</code> 表示不指定，傳入 <code>xxxx-yyyy</code> 表示區間，傳入 <code>xxxx,yyyy,zzzz</code> 表示特定多個年度
     * @param dayOfWeek  星期 (1 - 7: 1 為星期日，7 為星期六)，此參數只能和 dayOfMonth 擇一傳入，傳入 <code>null</code> 表示不指定，傳入 <code>x-y</code> 表示區間，傳入 <code>x,y,z</code> 表示特定多個星期
     * @param month      月份，傳入 <code>null</code> 表示不指定，傳入 <code>xx-yy</code> 表示區間，傳入 <code>xx,yy,zz</code> 表示特定多個月份，傳入 <code>*&#47;3</code> 表示每 3 個月做一次，傳入 <code>2/3</code> 表示從 2 月開始每 3 個月做一次
     * @param dayOfMonth 日期 (1 - 31)，此參數只能和 dayOfWeek 擇一傳入，傳入 <code>null</code> 表示不指定，傳入 <code>x-y</code> 表示區間，傳入 <code>x,y,z</code> 表示特定多個日期，傳入 <code>*&#47;3</code> 表示每 3 天做一次，傳入 <code>2/3</code> 表示從 2 日開始每 3 天做一次
     * @param hours      時 (0 - 23)，傳入 <code>null</code> 表示不指定 (每小時都執行)，傳入 <code>x-y</code> 表示區間，傳入 <code>x,y,z</code> 表示特定時，傳入 <code>*&#47;3</code> 表示每 3 小時做一次，傳入 <code>11/3</code> 表示從 11 點開始每 3 小時做一次
     * @param minutes    分 (0 - 59)，傳入 <code>null</code> 表示 "00" 分 (整點)，傳入 <code>x-y</code> 表示區間內每分鐘執行一次，傳入 <code>x,y,z</code> 表示特定時間，傳入 <code>*&#47;3</code> 表示每 3 分鐘做一次，傳入 <code>11/3</code> 表示從 11 分開始每 3 分鐘做一次
     * @param seconds    秒 (0 - 59)，傳入 <code>null</code> 表示 "00" 秒 (整點)，傳入 <code>x-y</code> 表示區間內每秒執行一次，傳入 <code>x,y,z</code> 表示特定時間，傳入 <code>*&#47;3</code> 表示每 3 秒鐘做一次，傳入 <code>11/3</code> 表示從 11 秒開始每 3 秒鐘做一次
     * @return
     */
    public static String generateCronExr(String year, String dayOfWeek, String month, String dayOfMonth, String hours, String minutes, String seconds) {
        CronBuilder cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        // 年度
        if (StringUtils.isBlank(year)) {
            cron.withYear(always());
        } else if (StringUtils.contains(year, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(year, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(year, "-"));
            cron.withYear(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(year, ",")) {
            String[] strs = StringUtils.split(year, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withYear(andExp);
        } else {
            cron.withYear(on(Integer.parseInt(year)));
        }
        // 星期
        if (StringUtils.isBlank(dayOfWeek)) {
            cron.withDoW(questionMark());
        } else if (StringUtils.contains(dayOfWeek, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(dayOfWeek, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(dayOfWeek, "-"));
            cron.withDoW(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(dayOfWeek, ",")) {
            String[] strs = StringUtils.split(dayOfWeek, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withDoW(andExp);
        } else {
            cron.withDoW(on(Integer.parseInt(dayOfWeek)));
        }
        // 月份
        if (StringUtils.isBlank(month)) {
            cron.withMonth(always());
        } else if (StringUtils.contains(month, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(month, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(month, "-"));
            cron.withMonth(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(month, ",")) {
            String[] strs = StringUtils.split(month, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withMonth(andExp);
        } else if (StringUtils.contains(month, "/")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(month, "/"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(month, "/"));
            if (StringUtils.equals(begin, "*")) cron.withMonth(every(Integer.parseInt(end)));
             else cron.withMonth(every(on(Integer.parseInt(begin)), Integer.parseInt(end)));
        } else {
            cron.withMonth(on(Integer.parseInt(month)));
        }
        // 日期
        if (StringUtils.isBlank(dayOfMonth)) {
            if (!StringUtils.isBlank(dayOfWeek)) cron.withDoM(questionMark());
             else cron.withDoM(always());
        } else if (StringUtils.contains(dayOfMonth, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(dayOfMonth, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(dayOfMonth, "-"));
            cron.withDoM(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(dayOfMonth, ",")) {
            String[] strs = StringUtils.split(dayOfMonth, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withDoM(andExp);
        } else if (StringUtils.contains(dayOfMonth, "/")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(dayOfMonth, "/"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(dayOfMonth, "/"));
            if (StringUtils.equals(begin, "*")) cron.withDoM(every(Integer.parseInt(end)));
             else cron.withDoM(every(on(Integer.parseInt(begin)), Integer.parseInt(end)));
        } else {
            cron.withDoM(on(Integer.parseInt(dayOfMonth)));
        }
        // 時
        if (StringUtils.isBlank(hours)) {
            cron.withHour(always());
        } else if (StringUtils.contains(hours, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(hours, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(hours, "-"));
            cron.withHour(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(hours, ",")) {
            String[] strs = StringUtils.split(hours, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withHour(andExp);
        } else if (StringUtils.contains(hours, "/")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(hours, "/"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(hours, "/"));
            if (StringUtils.equals(begin, "*")) cron.withHour(every(Integer.parseInt(end)));
             else cron.withHour(every(on(Integer.parseInt(begin)), Integer.parseInt(end)));
        } else {
            cron.withHour(on(Integer.parseInt(hours)));
        }
        // 分
        if (StringUtils.isBlank(minutes)) {
            cron.withMinute(on(0));
        } else if (StringUtils.contains(minutes, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(minutes, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(minutes, "-"));
            cron.withMinute(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(minutes, ",")) {
            String[] strs = StringUtils.split(minutes, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withMinute(andExp);
        } else if (StringUtils.contains(minutes, "/")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(minutes, "/"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(minutes, "/"));
            if (StringUtils.equals(begin, "*")) cron.withMinute(every(Integer.parseInt(end)));
             else cron.withMinute(every(on(Integer.parseInt(begin)), Integer.parseInt(end)));
        } else {
            cron.withMinute(on(Integer.parseInt(minutes)));
        }
        // 秒
        if (StringUtils.isBlank(seconds)) {
            cron.withSecond(on(0));
        } else if (StringUtils.contains(seconds, "-")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(seconds, "-"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(seconds, "-"));
            cron.withSecond(between(Integer.parseInt(begin), Integer.parseInt(end)));
        } else if (StringUtils.contains(seconds, ",")) {
            String[] strs = StringUtils.split(seconds, ",");
            And andExp = new And();
            for (int i = 0; i < strs.length; i++) {
                andExp.and(on(Integer.parseInt(StringUtils.trimToEmpty(strs[i]))));
            }
            cron.withSecond(andExp);
        } else if (StringUtils.contains(seconds, "/")) {
            String begin = StringUtils.trimToEmpty(StringUtils.substringBefore(seconds, "/"));
            String end = StringUtils.trimToEmpty(StringUtils.substringAfter(seconds, "/"));
            if (StringUtils.equals(begin, "*")) cron.withSecond(every(Integer.parseInt(end)));
             else cron.withSecond(every(on(Integer.parseInt(begin)), Integer.parseInt(end)));
        } else {
            cron.withSecond(on(Integer.parseInt(seconds)));
        }
        Cron quartzBuiltCronExpression = cron.instance();
        return quartzBuiltCronExpression.asString();
    }

    /**
     * 產生指定日期時間的 Quartz Cron Expression
     * <p>
     * 注意：如果只指定到日期，則會回傳每個整點的 0 分 0 秒皆執行的 Cron Expression
     * 如：傳入 <code>20181216</code>，將回傳 <code>0 0 * 16 12 ? 2018</code>
     *
     * @param dateTime 西元日期時間字串
     * @return
     */
    public static String generateCronExrFromDateTimeString(String dateTime) {
        String year = StringUtils.substring(dateTime, 0, 4);
        String month = StringUtils.substring(dateTime, 4, 6);
        String day = StringUtils.substring(dateTime, 6, 8);
        String hours = StringUtils.defaultString(StringUtils.substring(dateTime, 8, 10), "00");
        String minutes = StringUtils.defaultString(StringUtils.substring(dateTime, 10, 12), "00");
        String seconds = StringUtils.defaultString(StringUtils.substring(dateTime, 12, 14), "00");
        return generateCronExr(year, null, month, day, hours, minutes, seconds);
    }
}
