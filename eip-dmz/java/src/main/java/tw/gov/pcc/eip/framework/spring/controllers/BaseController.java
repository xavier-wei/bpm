package tw.gov.pcc.eip.framework.spring.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import tw.gov.pcc.eip.MessageKey;
import tw.gov.pcc.eip.framework.helper.SystemMessageHelper;
import tw.gov.pcc.common.helper.HttpHelper;

import java.util.Locale;

/**
 * BaseController
 * <p>
 * 提供存取預設系統操作訊息及 MessageSource 的方法
 *
 * @author Goston
 */
public class BaseController {

    private static final boolean DEFAULT_SYSMSG_ALERT = false; // 預設情況下系統訊息是否以 Alert 的方式在前端呈現

//    private static final String MSG_SAVE_SUCCESS = "G1001"; // 訊息: 資料新增成功
//    private static final String MSG_UPDATE_SUCCESS = "G1002"; // 訊息: 資料更新成功
//    private static final String MSG_DELETE_SUCCESS = "G1003"; // 訊息: 資料刪除成功
//    private static final String MSG_QUERY_SUCCESS = "G0001"; // 訊息: 資料查詢成功
//
//    private static final String MSG_SAVE_FAIL = "E1001"; // 訊息: 資料新增失敗
//    private static final String MSG_UPDATE_FAIL = "E1002"; // 訊息: 資料更新失敗
//    private static final String MSG_DELETE_FAIL = "E1003"; // 訊息: 資料刪除失敗
//    private static final String MSG_QUERY_FAIL = "E0005"; // 訊息: 資料查詢失敗
//    private static final String MSG_QUERY_EMPTY = "W0039"; // 訊息: 無查詢資料
//
//    private static final String MSG_REPORT_ERROR = "E0021"; // 訊息: 報表產製件敗
//    private static final String MSG_PROCESS_ERROR = "E9019"; // 訊息: 處理失敗!

    private static final String KEY_CONFIRM_RESULT = "_confirm_result";

    @Autowired
    public MessageSource messageSource;

    /**
     * 設定要顯示在系統訊息區的訊息 (依 <code>DEFAULT_SYSMSG_ALERT</code> 的設定來決定是否以 Alert 的方式在前端呈現)
     *
     * @param message 訊息內容
     */
    protected void setSystemMessage(String message) {
        setSystemMessage(message, DEFAULT_SYSMSG_ALERT);
    }

    /**
     * 設定要顯示在系統下方訊息區的訊息
     *
     * @param message 訊息內容
     * @param alert   <code>true</code> Alert 呈現；<code>false</code> 在訊息區顯示
     */
    protected void setSystemMessage(String message, boolean alert) {
        if (!alert)
            SystemMessageHelper.setMessage(message);
        else
            SystemMessageHelper.setAlertMessage(message);
    }

    protected String getSaveSuccessMessage() {
        return getMessage(MessageKey.MSG_SAVE_SUCCESS);
    }

    protected String getUpdateSuccessMessage() {
        return getMessage(MessageKey.MSG_UPDATE_SUCCESS);
    }

    protected String getDeleteSuccessMessage() {
        return getMessage(MessageKey.MSG_DELETE_SUCCESS);
    }

    protected String getQuerySuccessMessage() {
        return getMessage(MessageKey.MSG_QUERY_SUCCESS);
    }

    protected String getSaveFailMessage() {
        return getMessage(MessageKey.MSG_SAVE_FAIL);
    }

    protected String getUpdateFailMessage() {
        return getMessage(MessageKey.MSG_UPDATE_FAIL);
    }

    protected String getDeleteFailMessage() {
        return getMessage(MessageKey.MSG_DELETE_FAIL);
    }

    protected String getQueryFailMessage() {
        return getMessage(MessageKey.MSG_QUERY_FAIL);
    }

    protected String getQueryEmptyMessage() {
        return getMessage(MessageKey.MSG_QUERY_EMPTY);
    }

    protected String getReportErrorMessage() {
        return getMessage(MessageKey.MSG_REPORT_ERROR);
    }

    protected String getProcessErrorMessage() {
        return getMessage(MessageKey.MSG_PROCESS_ERROR);
    }

    protected String getMessage(String key) {
        if (StringUtils.isNotBlank(key))
            return messageSource.getMessage(key, null, key + " 未被定義", Locale.getDefault());
        else
            return "";
    }

    protected String getMessage(String key, Object... args) {
        if (StringUtils.isNotBlank(key))
            return messageSource.getMessage(key, args, key + " 未被定義", Locale.getDefault());
        else
            return "";
    }
    
    @InitBinder("form")
    public void customizeBinding(WebDataBinder binder) {
        binder.setAllowedFields("");
    }

    /**
     * 增加確認訊息
     *
     * @param message
     */
    public void addConfirm(String message) {
        SystemMessageHelper.addConfirmMessage(message, StringUtils.EMPTY);
    }
    
    /**
     * 增加確認訊息
     *
     * @param message
     */
    public void addConfirm(String message, String id) {
        SystemMessageHelper.addConfirmMessage(message, id);
    }

    /**
     * 是否有確認要求
     *
     * @return
     */
    public boolean hasConfirms() {
        return SystemMessageHelper.hasConfirms();
    }

    /**
     * 清除還未返回畫面的確認要求
     *
     * @return
     */
    public void cleanConfirms() {
        SystemMessageHelper.cleanConfirms();
    }

    /**
     * 是否已被畫面確認
     * 
     * @return
     */
    public boolean isConfirmed(){
        return StringUtils.equals("Y",  HttpHelper.getHttpRequest().getParameter(KEY_CONFIRM_RESULT));
    }

}
