package tw.gov.pcc.eip.framework.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import tw.gov.pcc.eip.framework.validation.validators.RequiredAssessValidator;

/**
 * 檢核物件的方法回傳值必須為指定的布林值，該方法名稱必須為<b>is</b>開頭<br/>
 * 適用於條件檢核，例如當Ａ欄位有值時，Ｂ欄位必須輸入或須為某個值
 * 
 * @author eric.mow
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RequiredAssessValidator.class})
public @interface RequiredAssess {

	String message() default "{validation.requiredString.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String label();
    
    boolean result() default true;
    
}
