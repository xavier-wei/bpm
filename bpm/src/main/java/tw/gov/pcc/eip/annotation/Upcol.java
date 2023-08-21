package tw.gov.pcc.eip.annotation;

import java.lang.annotation.*;

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
