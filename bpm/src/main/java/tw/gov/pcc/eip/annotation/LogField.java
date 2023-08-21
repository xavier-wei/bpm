package tw.gov.pcc.eip.annotation;

import java.lang.annotation.*;

/**
 * Annotation for Domain Object
 *
 * @author Goston
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LogField {

    String value() default "";

}
