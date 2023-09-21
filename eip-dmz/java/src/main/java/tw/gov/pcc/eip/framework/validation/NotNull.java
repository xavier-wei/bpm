package tw.gov.pcc.eip.framework.validation;

import tw.gov.pcc.eip.framework.validation.validators.NotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 驗證欄位值是否為空或null。 包括非String欄位
 * <p>
 * 用法：
 * <p>
 * <code>@NotEmpty(label = "欄位中文說明")</code>
 *
 * @author swho
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullValidator.class)
@Documented
public @interface NotNull {

    String label();

    String message() default "{W1005}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNull[] value();
    }

}
