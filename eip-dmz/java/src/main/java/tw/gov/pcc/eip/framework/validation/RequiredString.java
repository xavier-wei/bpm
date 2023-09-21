package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import tw.gov.pcc.eip.framework.validation.validators.RequiredStringValidator;

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
 * 檢核字串是否不為 <code>null</code> 及空值，預設為進行 Trim 後再驗證，若不想 Trim 須將 <code>trim</code> 手動設為 <code>false</code>
 * <p>
 * 用法：
 * <p>
 * <code>@RequiredString(label = "欄位中文說明")</code>
 * <code>@RequiredString(label = "欄位中文說明", trim = false)</code>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = RequiredStringValidator.class)
@ReportAsSingleViolation
@Documented
public @interface RequiredString {

    String label();

    boolean trim() default true;

    String message() default "{validation.requiredString.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        RequiredString[] value();
    }

}
