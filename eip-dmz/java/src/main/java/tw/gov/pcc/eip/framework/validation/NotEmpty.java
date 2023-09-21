package tw.gov.pcc.eip.framework.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.NotEmptyValidator;

/**
 * 驗證欄位值是否為空或null。
 * <p>
 * 用法：
 * <p>
 * <code>@NotEmpty(label = "欄位中文說明")</code>
 *
 * @author hugo
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyValidator.class)
@Documented
public @interface NotEmpty {

    String label();

    String message() default "{validation.colNotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotEmpty[] value();
    }

}
