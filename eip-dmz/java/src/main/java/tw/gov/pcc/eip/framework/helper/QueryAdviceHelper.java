package tw.gov.pcc.eip.framework.helper;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.common.util.ExceptionUtil;
import java.math.BigDecimal;
import java.util.*;

/**
 * 針對LOG做處理(查詢用)
 * 由於底層處理變數方式不一樣，配合分開處理
 * BE_MMACCESSLG.IDN,
 * BE_MMACCESSLG.UBNO,
 * BE_APLOG.IDNO
 * MMQUERYLOG
 * 
 * @author swho
 */
public class QueryAdviceHelper {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QueryAdviceHelper.class);
    //抓取個資欄位對應總和，若有新增種類必須增加 beBatchparam ( 個資欄位名 <-> 比對欄位名List)
    public static final Map<String, List<String>> ALL_STRING_LIST_MAP = new LinkedCaseInsensitiveMap<>();
    private static final ThreadLocal<Map<String, Object>> threadLocalLog = new ThreadLocal<>(); //緩存查詢條件
    private static final ThreadLocal<String> threadLocalSno = new ThreadLocal<>();
    private static final String KEY_FIELDLIST = "fieldist"; //查詢條件欄位名稱
    private static final String KEY_ARGS = "args"; //查詢條件內容 Key
    private static final String FOUND = "_found"; //查詢參數找到的註記

    public static void setMap(Map<String, Object> map) {
        threadLocalLog.set(map);
    }

    public static Map<String, Object> getMap() {
        return threadLocalLog.get();
    }

    public static void setSno(String sno) {
        threadLocalSno.set(sno);
    }

    public static String getSno() {
        return threadLocalSno.get();
    }

    /*
    底層處理查詢時，緩存查詢參數
     */
    public static void setQueryArgs(List<String> fieldList, Object[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_FIELDLIST, fieldList);
        map.put(KEY_ARGS, Arrays.asList(args));
        setMap(map);
    }

    /*
    取得參數名
     */
    @SuppressWarnings("unchecked")
    public static List<String> getFieldList() {
        return getMap() == null || getMap().get(KEY_FIELDLIST) == null ? Arrays.asList(ArrayUtils.EMPTY_STRING_ARRAY) : (List<String>) getMap().get(KEY_FIELDLIST);
    }

    /*
    取得參數值
     */
    @SuppressWarnings("unchecked")
    public static List<Object> getArgs() {
        return getMap() == null || getMap().get(KEY_ARGS) == null ? Arrays.asList(ArrayUtils.EMPTY_OBJECT_ARRAY) : (List<Object>) getMap().get(KEY_ARGS);
    }

    /*
    根據查詢條件，取出關鍵欄位map, key=IDNO, UNO
    dao 方法參數依下序取出檢查
    1. 遇到@Table的參數
    2. 遇到Map的參數
    3. 一般型態參數
     */
    public static Map<String, String> catchQueryMap(List<String> fieldList, List<Object> args) {
        try {
            Map<String, String> fieldMap = new LinkedCaseInsensitiveMap<>();
            Map<String, String> catchMap = new LinkedCaseInsensitiveMap<>();
            for (int i = 0; i < args.size(); i++) {
                String fieldName = i < fieldList.size() ? fieldList.get(i) : "X"; //X只是代表避開一般型態變數檢查
                String fieldValue = "";
                Object arg = args.get(i);
                if (arg == null) {
                    continue;
                }
                if (arg.getClass().isAnnotationPresent(Table.class)) {
                    try {
                        Map<String, String> innerMap = BeanUtils.describe(arg);
                        List<String> innerFieldList = new ArrayList<>();
                        List<Object> innerArgList = new ArrayList<>();
                        innerMap.forEach((key, value) -> {
                            innerFieldList.add(key);
                            innerArgList.add(value);
                        });
                        Map<String, String> deepMap = catchQueryMap(innerFieldList, innerArgList);
                        if (deepMap.containsKey(FOUND)) {
                            //找到相符名稱就不往下繼續找
                            return deepMap;
                        }
                    } catch (Exception e) {
                        log.error("自動帶入查詢條件功能轉型錯誤：" + ExceptionUtil.getStackTrace(e));
                    }
                } else if (arg instanceof Map) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<Object, Object> innerMap = (Map<Object, Object>) arg;
                        List<String> innerFieldList = new ArrayList<>();
                        List<Object> innerArgList = new ArrayList<>();
                        innerMap.forEach((key, value) -> {
                            innerFieldList.add(String.valueOf(key));
                            innerArgList.add(value);
                        });
                        Map<String, String> deepMap = catchQueryMap(innerFieldList, innerArgList);
                        if (deepMap.containsKey(FOUND)) {
                            //找到相符名稱就不往下繼續找
                            return deepMap;
                        }
                    } catch (Exception e) {
                        log.error("自動帶入查詢條件功能轉型錯誤：" + ExceptionUtil.getStackTrace(e));
                    }
                } else {
                    if (String.class.equals(arg.getClass())) {
                        Converter converter = ConvertUtils.lookup(String.class);
                        if (converter != null) {
                            fieldValue = converter.convert(String.class, arg);
                        }
                    }
                    fieldMap.put(fieldName, fieldValue);
                }
            }
            QueryAdviceHelper.ALL_STRING_LIST_MAP.forEach((key, value) -> value.stream().filter(fieldMap::containsKey).findFirst().ifPresent(s -> {
                catchMap.put(FOUND, "Y"); //上註記讓上層停止
                catchMap.put(key, fieldMap.get(s));
            }));
            return catchMap;
        } catch (Exception e) {
            log.error("Query log取值錯誤:" + ExceptionUtil.getStackTrace(e));
        }
        return new HashMap<>();
    }
}
