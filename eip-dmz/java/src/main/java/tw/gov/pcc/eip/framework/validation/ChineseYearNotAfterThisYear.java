package tw.gov.pcc.eip.framework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.ChineseYearNotAfterThisYearValidator;

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
 * 檢核民國年字串是否小於等於系統年，若字串為 <code>null</code> 或 空值 將不予檢核
 * 注意：若民國年不合法，則不會進行檢核，故靖配合 <code>@ChineseYear</code> 一起檢核。
 * <p>
 * 用法：
 * <p>
 * <code>@ChineseYearNotAfterThisYear(label = "欄位中文說明")</code>
 * <p>
 *
 * @author Goston
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ChineseYearNotAfterThisYearValidator.class)
@Documented
public @interface ChineseYearNotAfterThisYear {

    String label();

    String message() default "{validation.chineseYearNotAfterThisYear.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ChineseYearNotAfterThisYear[] value();
    }

}
