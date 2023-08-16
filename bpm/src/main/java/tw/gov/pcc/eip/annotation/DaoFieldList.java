package tw.gov.pcc.eip.annotation;

import java.lang.annotation.*;

/**
 * Annotation for DAO
 *
 * @author Goston
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DaoFieldList {

    String value();

}
