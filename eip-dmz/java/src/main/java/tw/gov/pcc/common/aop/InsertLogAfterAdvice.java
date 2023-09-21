package tw.gov.pcc.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.services.LoggingService;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 用於資料 INSERT 時紀錄 <code>MMAPLOG</code> 的 <code>MethodBeforeAdvice</code><br>
 * 於紀錄時會先新增一筆紀錄到 <code>MMACCESSLG</code> (Master) 中, 再紀錄 <code>MMAPLOG</code><br>
 * 注意: 未使用 Framework 提供之 Logging 機制者, 請勿使用此 Advice...<br>
 * <br>
 * 欲使用本 Advice 請於 Spring Config 定義如下 (Pointcut 建議定於 DAO 實做):<br>
 * <code><pre>
 * &lt;aop:config&gt;
 *     &lt;aop:pointcut id=&quot;insertLogPointcut&quot; expression=&quot;execution(* tw.gov.pcc.base.dao..*.insert*(..))&quot; /&gt;
 *     &lt;aop:aspect id=&quot;insertLog&quot; ref=&quot;insertLogAfterAdvice&quot;&gt;
 *         &lt;aop:after-returning method=&quot;doInsertLog&quot; pointcut-ref=&quot;insertLogPointcut&quot; /&gt;
 *     &lt;/aop:aspect&gt;
 * &lt;/aop:config&gt;
 *
 * &lt;bean id=&quot;insertLogAfterAdvice&quot; class=&quot;tw.gov.pcc.common.aop.InsertLogAfterAdvice&quot; /&gt;
 * </pre></code>
 * 詳細說明請見 Spring 官方文件
 *
 * @author Goston
 */
public class InsertLogAfterAdvice {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InsertLogAfterAdvice.class);

    @SuppressWarnings({"rawtypes"})
    public void doInsertLog(JoinPoint pointcut) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("進入了 doInsertLog ... {}", pointcut.getSignature().getName());
            }
            Method method = getTargetMethod(pointcut);
            // 有標示 @SkipLog 則不紀錄 Log
            SkipLog skipLog = method.getAnnotation(SkipLog.class);
            if (skipLog != null) return;
            Class<?>[] params = method.getParameterTypes();
            if (params.length == 1 && params[0] != null) {
                if (List.class.isAssignableFrom(params[0])) {
                    if (pointcut.getArgs().length == 1) {
                        Object[] args = pointcut.getArgs();
                        List list = (List) args[0];
                        if (list != null && list.size() > 0) {
                            FrameworkLogUtil.doInsertListLog(list);
                        }
                    }
                } else {
                    if (pointcut.getArgs().length == 1) {
                        Object[] args = pointcut.getArgs();
                        if (args[0] != null) {
                            FrameworkLogUtil.doInsertLog(args[0]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.INSERT, e);
        }
    }

    private Method getTargetMethod(JoinPoint pointcut) throws Exception {
        MethodSignature methodSig = (MethodSignature) pointcut.getSignature();
        return pointcut.getTarget().getClass().getMethod(pointcut.getSignature().getName(), methodSig.getMethod().getParameterTypes());
    }
}
