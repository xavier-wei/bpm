package tw.gov.pcc.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.common.services.LoggingService;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用於資料 DELETE 時紀錄 <code>MMAPLOG</code> 的 <code>MethodBeforeAdvice</code><br>
 * 於紀錄時會先新增一筆紀錄到 <code>MMACCESSLG</code> (Master) 中, 再紀錄 <code>MMAPLOG</code><br>
 * 注意: 未使用 Framework 提供之 Logging 機制者, 請勿使用此 Advice...<br>
 * <br>
 * 欲使用本 Advice 請於 Spring Config 定義如下 (Pointcut 建議定於 DAO 實做):<br>
 * <code><pre>
 * &lt;aop:config&gt;
 *     &lt;aop:pointcut id=&quot;deleteLogPointcut&quot; expression=&quot;execution(* tw.gov.pcc.base.dao..*.delete*(..))&quot; /&gt;
 *     &lt;aop:aspect id=&quot;deleteLog&quot; ref=&quot;deleteLogBeforeAdvice&quot;&gt;
 *         &lt;aop:before method=&quot;doDeleteLog&quot; pointcut-ref=&quot;deleteLogPointcut&quot; /&gt;
 *     &lt;/aop:aspect&gt;
 * &lt;/aop:config&gt;
 *
 * &lt;bean id=&quot;deleteLogBeforeAdvice&quot; class=&quot;tw.gov.pcc.common.aop.DeleteLogBeforeAdvice&quot; /&gt;
 * </pre></code>
 * 詳細說明請見 Spring 官方文件
 *
 * @author Goston
 */
public class DeleteLogBeforeAdvice {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeleteLogBeforeAdvice.class);

    @SuppressWarnings({"rawtypes"})
    public void doDeleteLog(JoinPoint pointcut) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("進入了 doDeleteLog ... {}", pointcut.getSignature().getName());
            }
            Method method = getTargetMethod(pointcut);
            Class<?> argClass;
            Method daoMethod;
            Object targetDao;
            // 有標示 @SkipLog 則不紀錄 Log
            SkipLog skipLog = method.getAnnotation(SkipLog.class);
            if (skipLog != null) return;
            Class<?>[] params = method.getParameterTypes();
            if (params.length == 1 && params[0] != null) {
                if (List.class.isAssignableFrom(params[0])) {
                    if (pointcut.getArgs().length == 1) {
                        Object[] args = pointcut.getArgs();
                        try {
                            List list = (List) args[0];
                            List<Object> beforeList = new ArrayList<Object>();
                            if (list != null && list.size() > 0) {
                                argClass = list.get(0).getClass();
                                targetDao = Objects.requireNonNull(getSelectOneDao(pointcut, argClass));
                                daoMethod = getSelectOneMethod(targetDao.getClass(), argClass);
                                //Method daoMethod = pointcut.getTarget().getClass().getMethod(UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD, list.get(0).getClass());
                                for (Object listObj : list) {
                                    Object beforeObj = daoMethod.invoke(targetDao, listObj);
                                    if (beforeObj != null) {
                                        beforeList.add(beforeObj);
                                    }
                                }
                                if (beforeList.size() > 0) {
                                    FrameworkLogUtil.doDeleteListLog(beforeList);
                                }
                            }
                        } catch (NoSuchMethodException nsme) {
                            log.info("{} 沒有定義 {}()，無法紀錄 MMAPLOG", pointcut.getTarget().getClass().getName(), UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD);
                        }
                    }
                } else {
                    if (pointcut.getArgs().length == 1) {
                        Object[] args = pointcut.getArgs();
                        try {
                            if (args[0] != null) {
                                argClass = args[0].getClass();
                                targetDao = Objects.requireNonNull(getSelectOneDao(pointcut, argClass));
                                daoMethod = getSelectOneMethod(targetDao.getClass(), argClass);
//                                Method daoMethod = pointcut.getTarget().getClass().getMethod(UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD, args[0].getClass());
                                Object beforeObj = daoMethod.invoke(targetDao, args);
                                if (beforeObj != null) {
                                    FrameworkLogUtil.doDeleteLog(beforeObj);
                                }
                            }
                        } catch (NoSuchMethodException nsme) {
                            // CHECKSTYLE.OFF: LineLength - Much more readable than wrap line
                            log.info("{} 沒有定義 {}( {} )，無法紀錄 MMAPLOG", pointcut.getTarget().getClass().getName(), UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD, args[0].getClass().getName());
                        }
                    }
                    // CHECKSTYLE.ON: LineLength
                }
            }
        } catch (Exception e) {
            FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.DELETE, e);
        }
    }

    private Object getSelectOneDao(JoinPoint pjp, Class<?> o) throws Exception {
        Object baseDao = pjp.getTarget();
        String daoBeanId = StringUtils.uncapitalize(String.format("%sDaoImpl", o.getSimpleName()));
        try {
            baseDao = SpringHelper.getBeanById(daoBeanId);
        } catch (Exception ignored) {
            log.error("執行{}，查無 Bean {}！ {}", o.getSimpleName(), baseDao, ExceptionUtility.getStackTrace(ignored));
        }
        return baseDao;
    }

    private Method getSelectOneMethod(Class<?> target, Class<?> arg) throws NoSuchMethodException {
        return target.getMethod(UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD, arg);
    }

    private Method getTargetMethod(JoinPoint pointcut) throws Exception {
        MethodSignature methodSig = (MethodSignature) pointcut.getSignature();
        return pointcut.getTarget().getClass().getMethod(pointcut.getSignature().getName(), methodSig.getMethod().getParameterTypes());
    }
}
