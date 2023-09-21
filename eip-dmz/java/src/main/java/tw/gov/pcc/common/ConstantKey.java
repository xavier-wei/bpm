package tw.gov.pcc.common;

public class ConstantKey {

    /**
     * 應用系統代號 於 web.xml 中 <code>&lt;context-param&gt;</code> 定義的 <code>&lt;param-name&gt;</code>
     */
    public static final String SYSTEM_ID = "systemId";

    /**
     * 應用系統名稱 於 web.xml 中 <code>&lt;context-param&gt;</code> 定義的 <code>&lt;param-name&gt;</code>
     */
    public static final String SYSTEM_NAME = "systemName";

    /**
     * <code>tw.gov.pcc.common.services.SysfuncService</code> 於 Spring Config 中定義的 Bean ID
     */
    public static final String SYSTEM_FUNCTION_SERVICE_ID = "sysfuncService";

    /**
     * <code>tw.gov.pcc.common.services.LoggingService</code> 於 Spring Config 中定義的 Bean ID
     */
    public static final String LOGGING_SERVICE_ID = "loggingService";

    /**
     * MMACCESSLG.ACCTYPE = 'I'
     */
    public static final String ACCESS_TYPE_INSERT = "I";

    /**
     * MMACCESSLG.ACCTYPE = 'U'
     */
    public static final String ACCESS_TYPE_UPDATE = "U";

    /**
     * MMACCESSLG.ACCTYPE = 'D'
     */
    public static final String ACCESS_TYPE_DELETE = "D";

    /**
     * MMACCESSLG.ACCTYPE = 'Q'
     */
    public static final String ACCESS_TYPE_QUERY = "Q";

    /**
     * MMACCESSLG.ACCTYPE = 'P'
     */
    public static final String ACCESS_TYPE_PRINT = "P";
    
    
    //回傳代碼
    public static final String STATUS_CODE_SUCCESS = "0";// 0：查詢成功
    public static final String STATUS_CODE_SUCCESS_MSG = "查詢成功";// 0：查詢成功
    public static final String STATUS_CODE_NO_DATA = "1";// 1：查無資料
    public static final String STATUS_CODE_IDN_DUPLICATE = "2";//2：身分證重號
    public static final String STATUS_CODE_ERROR = "9";//9：查詢失敗

}
