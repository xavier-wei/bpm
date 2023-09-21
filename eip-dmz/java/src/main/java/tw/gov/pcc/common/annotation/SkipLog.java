package tw.gov.pcc.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 標示於 DAO 實作的方法上，用於略過自動紀錄 MMQUERYLOG、MMAPLOG 機制
 *
 * @author Goston
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SkipLog {

}
