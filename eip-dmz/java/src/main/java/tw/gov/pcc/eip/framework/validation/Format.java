package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.FormatValidator;

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
 * 透過 Regular Expression 檢核字串格式，若字串為 <code>null</code> 或 空值 將不予檢核。
 * 預設為進行 Trim 後再檢核，若不想 Trim 須將 <code>trim</code> 手動設為 <code>false</code>。
 * 另外預設將不區分大小寫，若欲區分需把 <code>caseSensitive</code> 設為 <code>true</code>。
 * <p>
 * 用法：
 * <p>
 * <code>@Format(label = "欄位中文說明", pattern="...")</code>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = FormatValidator.class)
@Documented
public @interface Format {

    String label();

    String pattern();

    boolean caseSensitive() default false;

    boolean trim() default true;

    String message() default "{validation.format.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Format[] value();
    }

}
