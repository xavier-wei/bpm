package tw.gov.pcc.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RestInOutLog {
	
	String value() default "";

}
