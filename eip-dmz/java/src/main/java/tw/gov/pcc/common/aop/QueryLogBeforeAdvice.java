package tw.gov.pcc.common.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ClassUtils;
import tw.gov.pcc.eip.framework.helper.QueryAdviceHelper;
import tw.gov.pcc.common.annotation.DaoFieldList;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.helper.HttpHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.services.LoggingService;
import tw.gov.pcc.common.util.ExceptionUtil;
import tw.gov.pcc.common.util.FrameworkLogUtil;
import tw.gov.pcc.common.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用來紀錄 <code>MMQUERYLOG</code> 的 <code>MethodBeforeAdvice</code><br>
 * 於紀錄時會先新增一筆紀錄到 <code>MMACCESSLG</code> (Master) 中, 再紀錄 <code>MMQUERYLOG</code><br>
 * 注意: 未使用 Framework 提供之 Logging 機制者, 請勿使用此 Advice...<br>
 * <br>
 * 欲使用本 Advice 請於 Spring Config 定義如下 (Pointcut 建議定於 DAO 實做):<br>
 * <code><pre>
 * &lt;aop:config&gt;
 *     &lt;aop:pointcut id=&quot;queryLogPointcut&quot; expression=&quot;execution(* tw.gov.pcc.base.dao..*.*(..)) and !execution(* tw.gov.pcc.base.dao..*.insert*(..)) and !execution(* tw.gov.pcc.base.dao..*.update*(..)) and !execution(* tw.gov.pcc.base.dao..*.delete*(..))&quot; /&gt;
 *     &lt;aop:aspect id=&quot;queryLog&quot; ref=&quot;queryLogBeforeAdvice&quot;&gt;
 *         &lt;aop:before method=&quot;doQueryLog&quot; pointcut-ref=&quot;queryLogPointcut&quot; /&gt;
 *     &lt;/aop:aspect&gt;
 * &lt;/aop:config&gt;
 *
 * &lt;bean id=&quot;queryLogBeforeAdvice&quot; class=&quot;tw.gov.pcc.common.aop.QueryLogBeforeAdvice&quot;&gt;
 *     &lt;property name=&quot;loggingService&quot; ref=&quot;loggingService&quot; /&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * 詳細說明請見 Spring 官方文件
 *
 * @author Goston
 */
public class QueryLogBeforeAdvice {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QueryLogBeforeAdvice.class);
    private LoggingService loggingService;

    public void doQueryLog(JoinPoint pointcut) {
        HttpServletRequest request = HttpHelper.getHttpRequest();
        // 判斷是否需紀錄 Log
        if (EnvFacadeHelper.isNeedLogging(request)) {
            if (log.isDebugEnabled()) {
                log.debug("進入了 doQueryLog ... {}", pointcut.getSignature().getName());
            }
            try {
                FrameworkUserInfoBean userData = UserSessionHelper.getFrameworkUserData(request);
                // 使用者沒登入, 或為批次作業
                if (userData == null) return;
                String table = "";
                String[] fieldList = {};
                Method method = getTargetMethod(pointcut);
                // 有標示 @SkipLog 則不紀錄 Log
                SkipLog skipLog = method.getAnnotation(SkipLog.class);
                if (skipLog != null) return;
                // 底層紀錄 Update Log 時呼叫的 Query 方法不紀錄 Log
                if (StringUtils.equals(method.getName(), UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD)) return;
                // 先看 Method Level 有沒有定義 Table Name, 若無再檢核 Class Level 是否定義 Table Name
                if (method.isAnnotationPresent(DaoTable.class)) table = StringUtils.defaultString(method.getAnnotation(DaoTable.class).value()).trim();
                 else if (pointcut.getTarget().getClass().isAnnotationPresent(DaoTable.class)) table = StringUtils.defaultString(pointcut.getTarget().getClass().getAnnotation(DaoTable.class).value()).trim();
                // 有定義 Table Name 再繼續取得 Field List
                if (StringUtils.isNotBlank(table)) {
                    if (method.isAnnotationPresent(DaoFieldList.class)) fieldList = StrUtil.splitTrim(StringUtils.defaultString(method.getAnnotation(DaoFieldList.class).value()), ",");
                    // 參數長度相等
                    if (fieldList != null) {
                        if (pointcut.getSignature() instanceof CodeSignature && ArrayUtils.isEmpty(fieldList)) {
                            String[] renewFieldList = ((CodeSignature) pointcut.getSignature()).getParameterNames();
                            fieldList = (String[]) ObjectUtils.defaultIfNull(renewFieldList, fieldList);
                        }
                        String xmlData = FrameworkLogUtil.generateQueryLogXML(table, method.getName(), fieldList, pointcut.getArgs());
                        // 紀錄 MMACCESSLG 及 MMQUERYLOG
                        if (StringUtils.isNotBlank(xmlData)) {
                            QueryAdviceHelper.setQueryArgs(Arrays.asList(fieldList), pointcut.getArgs());
                            QueryAdviceHelper.setSno(loggingService.loggingQueryLog(userData, table, xmlData));
                        }
                    }
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Query Log 寫入錯誤, 原因: {}", ExceptionUtil.getStackTrace(e));
                }
                FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.QUERY, e);
            }
        }
    }

    public void doQueryResultLog(JoinPoint pointcut, Object result) {
        if (result == null) {
            return;
        }
        HttpServletRequest request = HttpHelper.getHttpRequest();
        // 判斷是否需紀錄 Log
        if (EnvFacadeHelper.isNeedLogging(request)) {
            if (log.isDebugEnabled()) {
                log.debug("進入了 doQueryResultLog ... {}", pointcut.getSignature().getName());
            }
            try {
                FrameworkUserInfoBean userData = UserSessionHelper.getFrameworkUserData(request);
                // 使用者沒登入, 或為批次作業
                if (userData == null) return;
                Method method = getTargetMethod(pointcut);
                // 有標示 @SkipLog 則不紀錄 Log
                SkipLog skipLog = method.getAnnotation(SkipLog.class);
                if (skipLog != null) return;
                // 底層紀錄 Update Log 時呼叫的 Query 方法不紀錄 Log
                if (StringUtils.equals(method.getName(), UpdateLogAroundAdvice.DAO_GET_DATA_BY_PKEY_METHOD)) return;
                Map<String, Boolean> keysMap = new ConcurrentHashMap<>();
                if (ClassUtils.isAssignableValue(Iterable.class, result)) {
                    loggingService.loggingQueryResultLog(userData, QueryAdviceHelper.getSno(), keysMap, result);
                } else {
                    QueryAdviceHelper.setQueryArgs(new ArrayList<>(), new Object[] {result});
                    loggingService.loggingQueryResultLog(userData, QueryAdviceHelper.getSno(), keysMap);
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Query Result Log 寫入錯誤, 原因: {}", ExceptionUtil.getStackTrace(e));
                }
                FrameworkLogUtil.logLoggingErrorMessage(LoggingService.LogType.QUERY, e);
            }
        }
    }

    public Method getTargetMethod(JoinPoint pointcut) throws Exception {
        MethodSignature methodSig = (MethodSignature) pointcut.getSignature();
        return pointcut.getTarget().getClass().getMethod(pointcut.getSignature().getName(), methodSig.getMethod().getParameterTypes());
    }

    public void setLoggingService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }
}
