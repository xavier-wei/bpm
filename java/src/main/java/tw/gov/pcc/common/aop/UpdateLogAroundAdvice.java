package tw.gov.pcc.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.common.services.LoggingService;
import tw.gov.pcc.common.util.ExceptionUtil;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用於資料 UPDATE 時紀錄 <code>MMAPLOG</code> 的 Method Around Advice<br>
 * 於紀錄時會先新增一筆紀錄到 <code>MMACCESSLG</code> (Master) 中, 再紀錄 <code>MMAPLOG</code><br>
 * 注意: 未使用 Framework 提供之 Logging 機制者, 請勿使用此 Advice...<br>
 * <br>
 * 欲使用本 Advice 請於 Spring Config 定義如下 (Pointcut 建議定於 DAO 實做):<br>
 * <code><pre>
 * &lt;aop:config&gt;
 *     &lt;aop:pointcut id=&quot;updateLogPointcut&quot; expression=&quot;execution(* tw.gov.pcc.base.dao..*.update*(..))&quot; /&gt;
 *     &lt;aop:aspect id=&quot;updateLog&quot; ref=&quot;updateLogAroundAdvice&quot;&gt;
 *         &lt;aop:around method=&quot;doUpdateLog&quot; pointcut-ref=&quot;updateLogPointcut&quot; /&gt;
 *     &lt;/aop:aspect&gt;
 * &lt;/aop:config&gt;
 *
 * &lt;bean id=&quot;updateLogAroundAdvice&quot; class=&quot;tw.gov.pcc.common.aop.UpdateLogAroundAdvice&quot; /&gt;
 * </pre></code>
 * 詳細說明請見 Spring 官方文件
 *
 * @author Goston
 */
public class UpdateLogAroundAdvice {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UpdateLogAroundAdvice.class);
    public static final String DAO_GET_DATA_BY_PKEY_METHOD = "selectDataByPrimaryKey";

    @SuppressWarnings({"rawtypes"})
    public Object doUpdateLog(ProceedingJoinPoint pjp) throws Throwable {
        boolean skip = false;
        boolean isList = false;
        boolean exceptionHappend = false;
        List<Object> beforeList = new ArrayList<>();
        Object beforeObj = null;
        Class<?> argClass;
        Method daoMethod = null;
        Object targetDao = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("進入了 doUpdateLog ... {}", pjp.getSignature().getName());
            }
            Method method = getTargetMethod(pjp);
            // 有標示 @SkipLog 則不紀錄 Log
            SkipLog skipLog = method.getAnnotation(SkipLog.class);
            if (skipLog != null) skip = true;
            if (!skip) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1 && params[0] != null) {
                    if (List.class.isAssignableFrom(params[0])) {
                        isList = true;
                        if (pjp.getArgs().length == 1) {
                            Object[] args = pjp.getArgs();
                            try {
                                List afterList = (List) args[0];
                                if (afterList != null && afterList.size() > 0) {
                                    argClass = afterList.get(0).getClass();
                                    targetDao = Objects.requireNonNull(getSelectOneDao(pjp, argClass));
                                    daoMethod = getSelectOneMethod(targetDao.getClass(), argClass);
                                    for (Object afterObj : afterList) {
                                        Object tempBeforeObj = daoMethod.invoke(targetDao, afterObj);
                                        if (tempBeforeObj != null) {
                                            beforeList.add(tempBeforeObj);
                                        }
                                    }
                                }
                            } catch (NoSuchMethodException nsme) {
                                log.debug("{} 沒有定義 {}()，無法紀錄 MMAPLOG", pjp.getTarget().getClass().getName(), DAO_GET_DATA_BY_PKEY_METHOD);
                            }
                        }
                    } else {
                        if (pjp.getArgs().length == 1) {
                            Object[] args = pjp.getArgs();
                            try {
                                argClass = args[0].getClass();
                                targetDao = Objects.requireNonNull(getSelectOneDao(pjp, argClass));
                                daoMethod = getSelectOneMethod(targetDao.getClass(), argClass);
                                beforeObj = daoMethod.invoke(targetDao, args);
                            } catch (NoSuchMethodException nsme) {
                                log.debug("{} 沒有定義 {}( {} )，無法紀錄 MMAPLOG", pjp.getTarget().getClass().getName(), DAO_GET_DATA_BY_PKEY_METHOD, args[0].getClass().getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Update Log 發生錯誤, 原因: {}", ExceptionUtil.getStackTrace(e));
            }
            exceptionHappend = true;
        }
        Object retVal = pjp.proceed();
        try {
            if (!skip && !exceptionHappend && daoMethod != null) {
                if (isList) {
                    if (pjp.getArgs().length == 1) {
                        try {
                            List<Object> afterList = new ArrayList<>();
                            if (beforeList.size() > 0) {
                                for (Object listBeforeObj : beforeList) {
                                    Object tempAfterObj = daoMethod.invoke(targetDao, listBeforeObj);
                                    if (tempAfterObj != null) {
                                        afterList.add(tempAfterObj);
                                    }
                                }
                                FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
                            }
                        } catch (Exception nsme) {
                            log.debug("{} 沒有定義 {}()，無法紀錄 MMAPLOG", pjp.getTarget().getClass().getName(), DAO_GET_DATA_BY_PKEY_METHOD);
                        }
                    }
                } else {
                    if (pjp.getArgs().length == 1) {
                        Object[] args = pjp.getArgs();
                        try {
                            Object afterObj = daoMethod.invoke(targetDao, args);
                            if (beforeObj != null && afterObj != null) {
                                FrameworkLogUtil.doUpdateLog(beforeObj, afterObj);
                            }
                        } catch (Exception nsme) {
                            log.debug("{} 沒有定義 {}( {} )，無法紀錄 MMAPLOG", pjp.getTarget().getClass().getName(), DAO_GET_DATA_BY_PKEY_METHOD, args[0].getClass().getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.UPDATE, e);
        }
        return retVal;
    }

    private Object getSelectOneDao(ProceedingJoinPoint pjp, Class<?> o) {
        Object baseDao = pjp.getTarget();
        String daoBeanId = StringUtils.uncapitalize(String.format("%sDaoImpl", o.getSimpleName()));
        try {
            baseDao = SpringHelper.getBeanById(daoBeanId);
        } catch (Exception ignored) {
            log.debug("執行{}，查無 Bean {} ！", o.getSimpleName(), baseDao);
        }
        return baseDao;
    }

    private Method getSelectOneMethod(Class<?> target, Class<?> arg) throws NoSuchMethodException {
        return target.getMethod(DAO_GET_DATA_BY_PKEY_METHOD, arg);
    }

    private Method getTargetMethod(JoinPoint pointcut) throws Exception {
        MethodSignature methodSig = (MethodSignature) pointcut.getSignature();
        return pointcut.getTarget().getClass().getMethod(pointcut.getSignature().getName(), methodSig.getMethod().getParameterTypes());
    }
}
