package tw.gov.pcc.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Upcol {

    /**
     * 異動項目,定義的畫面欄位中文
     *
     * @return
     */
    String value() default "";

}