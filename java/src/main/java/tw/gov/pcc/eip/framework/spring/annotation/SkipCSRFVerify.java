package tw.gov.pcc.eip.framework.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 標示於 Controller 的方法上，用於忽略 CSRF 的檢查
 *
 * @author Goston
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SkipCSRFVerify {

}
