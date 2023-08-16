package tw.gov.pcc.eip.annotation;

import java.lang.annotation.*;

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
