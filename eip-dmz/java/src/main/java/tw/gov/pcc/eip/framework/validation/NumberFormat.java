package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.NumberFormatValidator;

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
 * 驗證欄位值是否符合指定的數字格式，若字串為 <code>null</code> 或 空值 將不予檢核。
 * 參數:<br>
 * <code>integer</code> 指定有幾位整數，預設值為 <code>4</code>，最小須設為 <code>1</code>，若小於 <code>1</code> 則不檢核
 * <code>decimal</code> 指定有幾位小數，預設值為 <code>2</code>，最小須設為 <code>1</code>，若小於 <code>1</code> 請改用 <code>@RequiredInteger</code>
 * <p>
 * 用法：
 * <p>
 * <code>@NumberFormat(label = "欄位中文說明")</code>
 * <code>@NumberFormat(label = "欄位中文說明", integer = 6, decimal = 2)</code>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NumberFormatValidator.class)
@Documented
public @interface NumberFormat {

    String label();

    int integer() default 4;

    int decimal() default 2;

    String message() default "{validation.numberFormat.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NumberFormat[] value();
    }

}
