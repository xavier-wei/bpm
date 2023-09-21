package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.IdNoFormatValidator;

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
 * 驗證欄位值是否屬於合法的身分證字號字元，若字串為 <code>null</code> 或 空值 將不予檢核。
 * 檢核邏輯：
 * <p>
 * 長度須為 10 碼<br>
 * 首碼須為英文字母<br>
 * 第二碼須為 1 或 2<br>
 * 其餘各碼須為數字<br>
 * <p>
 * 注意：身分證號檢查碼不檢核
 * <p>
 * 用法：
 * <p>
 * <code>@IdNoFormat(label = "欄位中文說明")</code>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = IdNoFormatValidator.class)
@Documented
public @interface IdNoFormat {

    String label();

    String message() default "{validation.idNoFormat.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        IdNoFormat[] value();
    }

}
