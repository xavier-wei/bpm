package tw.gov.pcc.eip;

/*
 * 訊息參數
 * 
 * "訊息:"為畫面下方顯示之訊息
 * 
 */
public class MessageKey {

    public static final String MSG_SAVE_SUCCESS = "G1001"; // 訊息: 資料新增成功
    public static final String MSG_UPDATE_SUCCESS = "G1002"; // 訊息: 資料更新成功
    public static final String MSG_DELETE_SUCCESS = "G1003"; // 訊息: 資料刪除成功
    public static final String MSG_QUERY_SUCCESS = "G0001"; // 訊息: 資料查詢成功

    public static final String MSG_SAVE_FAIL = "E1001"; // 訊息: 資料新增失敗
    public static final String MSG_UPDATE_FAIL = "E1002"; // 訊息: 資料更新失敗
    public static final String MSG_DELETE_FAIL = "E1003"; // 訊息: 資料刪除失敗
    public static final String MSG_QUERY_FAIL = "E0005"; // 訊息: 資料查詢失敗
    public static final String MSG_QUERY_EMPTY = "W0039"; // 訊息: 無查詢資料
    public static final String MSG_REPORT_ERROR = "E1004"; // 訊息: 報表產製失敗
    
    public static final String MSG_PROCESS_ERROR = "E9019"; // 訊息: 處理失敗!
    public static final String MSG_ATLEAST_ONE = "W0007"; //至少需輸入一個查詢條件！
    public static final String MSG_DATA_EXISTS = "W0031"; // 訊息: {0}已存在，請重新輸入！
    public static final String NODATA = "E0201"; //查無資料
    public static final String NOVALID = "E0202"; //驗證失敗
}
