package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.ChineseBirthdayValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class-Level Validation for 可能有民國前日期的出生日期，若字串為 <code>null</code> 或 空值 將不予檢核
 * <p>
 * 用法：
 * <p>
 * <code>@ChineseBirthday(label = "出生日期", beforeChineseFieldName = "民國前欄位名稱", birthdayFieldName = "出生日期欄位名稱")</code>
 * <p>
 *
 * @author Goston
 */
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ChineseBirthdayValidator.class)
@Documented
public @interface ChineseBirthday {

    String label();

    String message() default "{validation.chineseBirthday.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String beforeChineseFieldName();

    String birthdayFieldName();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ChineseBirthday[] value();
    }

}
