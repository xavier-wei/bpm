package tw.gov.pcc.eip.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RestInOutLog {

	String value() default "";

}
