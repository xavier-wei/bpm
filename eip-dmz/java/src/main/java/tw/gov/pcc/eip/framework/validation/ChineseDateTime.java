package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.ChineseDateTimeValidator;

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
 * 檢核字串是否為合法的民國日期時間，時間可接受時分及時分秒，若字串為 <code>null</code> 或 空值 將不予檢核
 * <p>
 * 用法：
 *
 * <code>@ChineseDateTime(label = "欄位中文說明")</code>
 * <p>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ChineseDateTimeValidator.class)
@Documented
public @interface ChineseDateTime {

    String label();

    String message() default "{validation.chineseDateTime.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ChineseDateTime[] value();
    }

}
