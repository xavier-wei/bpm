package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.MaxValueValidatorForCharSequence;
import tw.gov.pcc.eip.framework.validation.validators.MaxValueValidatorForNumber;

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
 * 檢核值是否小於等於指定的數字，<code>null</code> 或 空值 將不予檢核。
 * 注意：只能比較整數值，無法比較浮點數；若欄位值不是合法的數字格式將被視為檢核不通過。
 * <p>
 * 用法：
 * <p>
 * <code>@MaxValue(label = "欄位中文說明", value=10)</code>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {MaxValueValidatorForCharSequence.class, MaxValueValidatorForNumber.class})
@Documented
public @interface MaxValue {

    String label();

    long value();

    String message() default "{W1004}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MaxValue[] value();
    }

}
