package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.ChineseDateNotAfterTodayValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 檢核日期是否大於今天日期，若日期為為 <code>null</code> 或 空值 將不予檢核。
 * 注意：若日期不是合法日期，則不會進行檢核，故靖配合 <code>@ChineseDate</code> 一起檢核。
 * <p>
 * 用法：
 * <p>
 * <code>@ChineseDateNotAfterToday(label = "欄位中文說明")</code>
 * <p>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ChineseDateNotAfterTodayValidator.class)
@Documented
public @interface ChineseDateNotAfterToday {

    String label();

    String message() default "{validation.chineseDateNotAfterToday.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ChineseDateNotAfterToday[] value();
    }

}
