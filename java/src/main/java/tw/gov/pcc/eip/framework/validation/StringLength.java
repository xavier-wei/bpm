package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import tw.gov.pcc.eip.framework.validation.validators.StringLengthValidator;

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
 * 檢核字串長度，若字串為 <code>null</code> 或 空值 將不予檢核，預設為進行 Trim 後再驗證，若不想 Trim 須將 <code>trim</code> 手動設為 <code>false</code>
 * <p>
 * 用法：
 * <p>
 * <code>@StringLength(label = "欄位中文說明", min = 8)</code> - 不可小於 8 個字
 * <code>@StringLength(label = "欄位中文說明", max = 8)</code> - 不可大於 8 個字
 * <code>@StringLength(label = "欄位中文說明", min = 4, max = 8, trim = false)</code> - 須介於 4 - 8 個字
 * <code>@StringLength(label = "欄位中文說明", min = 8, max = 8)</code> - 須為 8 個字
 * <p>
 * 若 <code>min</code>、<code>max</code> 為 <code>0</code> 或負值，則該參數將被略過（因為本 Validator 將不檢核空值）
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StringLengthValidator.class)
@ReportAsSingleViolation
@Documented
public @interface StringLength {

    String label();

    int min() default 0;

    int max() default 0;

    boolean trim() default true;

    String message() default "{validation.stringLength.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        StringLength[] value();
    }

}
