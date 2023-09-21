package tw.gov.pcc.common.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.ConstantKey;
import tw.gov.pcc.common.dao.MmaplogDao;
import tw.gov.pcc.common.dao.MmquerylogDao;
import tw.gov.pcc.common.dao.PortalLogDao;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.domain.Mmaplog;
import tw.gov.pcc.common.domain.Mmquerylog;
import tw.gov.pcc.common.domain.PortalLog;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.framework.helper.ModifyAdviceHelper;
import tw.gov.pcc.eip.framework.helper.QueryAdviceHelper;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * 紀錄 PORTAL_LOG / MMACCESSLG / MMAPLOG / MMQUERYLOG 之 Service<br>
 *
 * @author Goston
 */
@Service
public class LoggingService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoggingService.class);
    private final PortalLogDao portalLogDao;
    private final MmquerylogDao mmquerylogDao;
    private final MmaplogDao mmaplogDao;

    public LoggingService(PortalLogDao portalLogDao, MmquerylogDao mmquerylogDao, MmaplogDao mmaplogDao) {
        this.portalLogDao = portalLogDao;
        this.mmquerylogDao = mmquerylogDao;
        this.mmaplogDao = mmaplogDao;
    }

    /**
     * 記錄 Portal Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     *
     * @param userData 使用者物件
     * @param request  HttpServletRequest
     * @return 成功回傳 <code>PORTAL_LOG.SYS_ID</code>, 失敗回傳 null
     */
    public String loggingPortalLog(FrameworkUserInfoBean userData, HttpServletRequest request) {
        if (userData == null) return null;
        String path = request.getServletPath();
        Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
        String now = DateUtil.parseTimestampToWestDateTime(date, true);
        PortalLog logData = new PortalLog();
        logData.setLogDateTime(now); // 紀錄時間
        logData.setUserId(userData.getUserId()); // 用戶代號
        logData.setUserIP(userData.getLoginIP()); // 用戶 IP 位址
        logData.setUserAction("Click Link"); // 用戶執行動作
        logData.setApCode(EnvFacadeHelper.getSystemId()); // 應用系統代號
        logData.setApName(EnvFacadeHelper.getSystemName()); // 應用系統名稱
        logData.setApFunctionCode(EnvFacadeHelper.getItemIdByServletPath(userData, path)); // 應用系統功能代號
        logData.setApFunctionName(EnvFacadeHelper.getItemNameByServletPath(userData, path)); // 應用系統功能名稱
        logData.setApUrl(request.getServletPath()); // 應用系統網址
        logData.setLogDescript(""); // 說明
        logData.setDateTime(date); // 系統時間
        logData.setToken(userData.getToken()); // 檢查資訊
        return portalLogDao.insertData(logData);
    }

    /**
     * 記錄 Portal Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 Portal Log
     *
     * @param logData <code>tw.gov.pcc.common.domain.PortalLog</code> 物件
     * @return 成功回傳 <code>PORTAL_LOG.SYS_ID</code>, 失敗回傳 <code>null</code>
     */
    public String loggingPortalLog(PortalLog logData) {
        return portalLogDao.insertData(logData);
    }

    /**
     * 記錄 Access Log 及 Query Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     *
     * @param userData  使用者物件
     * @param tableName 查詢的 TABLE 名稱
     * @param xmlData   查詢條件 XML 字串
     */
    public String loggingQueryLog(FrameworkUserInfoBean userData, String tableName, String xmlData) {
        if (userData == null) return null;
        Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
        String acsTime = DateUtil.parseTimestampToWestDateTime(date, true); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)
        Map<String, String> queryMap = QueryAdviceHelper.catchQueryMap(QueryAdviceHelper.getFieldList(), QueryAdviceHelper.getArgs());

        // 處理 MMQUERYLOG
        Mmquerylog queryLog = new Mmquerylog();
        queryLog.setTableName(tableName); // TABLE 名稱
        queryLog.setQyTime(acsTime); // 查詢時間
        queryLog.setPgmName(userData.getApFunctionName()); // 程式名稱
        queryLog.setPgmCode(userData.getApFunctionCode()); // 程式代碼
        queryLog.setDeptId(userData.getDeptId()); // 員工部門代號
        queryLog.setQueryMan(userData.getUserId()); // 員工編號
        queryLog.setTermEd(userData.getLoginIP()); // 終端機位址
        queryLog.setQyCode(ConstantKey.ACCESS_TYPE_QUERY); // 查詢代號
        queryLog.setQyCondition(xmlData); // 查詢條件
        queryLog.setMemo(""); // 備註

        queryLog.setSno(genSno()); // 編號 (MMACCESSLG.SNO)
        mmquerylogDao.insertData(queryLog);
        return queryLog.getSno();
    }
    
    private String genSno() {
        return DateUtil.getNowWestDateTime(true) + StringUtils.substring("000000" + portalLogDao.getSeq(), -6);
    }

    /**
     * 當 Query Result 成功 Return 時，額外記錄 Access Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     *
     * @param userData 使用者物件
     * @param qsno
     * @param keysMap
     */
    @Deprecated
    public void loggingQueryResultLog(FrameworkUserInfoBean userData, String qsno, Map<String, Boolean> keysMap) {

    }

    /**
     * 記錄 Query Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 Query Log
     *
     * @param logData <code>tw.gov.pcc.common.domain.Mmquerylog</code> 物件
     */
    public void loggingQueryLog(Mmquerylog logData) {
        mmquerylogDao.insertData(logData);
    }

    /**
     * 記錄 AP Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     *
     * @param userData   使用者物件
     * @param accessType 操作類別 (I/U/D/P/Q)
     * @param tableName  異動的 TABLE 名稱
     * @param pkField    異動 TABLE 主鍵
     * @param field      異動欄位
     * @param befImg     改前內容
     * @param aftImg     改後內容
     */
    public void loggingApLog(FrameworkUserInfoBean userData, String accessType, String tableName, String pkField, String field, String befImg, String aftImg) {
        if (userData == null) return;
        Timestamp date = DateUtil.getNowDateTimeAsTimestamp();

        // 處理 MMAPLOG
        Mmaplog apLog = new Mmaplog();
        apLog.setTableName(tableName); // 異動 TABLE 名稱
        apLog.setPkField(pkField); // 異動 TABLE 主鍵
        apLog.setChgTime(date); // 異動時間
        apLog.setPgmName(userData.getApFunctionName()); // 程式名稱
        apLog.setPgmCode(userData.getApFunctionCode()); // 程式代碼
        apLog.setDeptId(userData.getDeptId()); // 異動員工部門代號
        apLog.setModifyMan(userData.getUserId()); // 異動員工編號
        apLog.setTermEd(userData.getLoginIP()); // 終端機位址
        apLog.setChgCode(accessType); // 異動代號
        apLog.setField(field); // 異動欄位
        apLog.setBefImg(befImg); // 改前內容
        apLog.setAftImg(aftImg); // 改後內容
        apLog.setMemo(""); // 備註
        apLog.setSno(genSno()); // 編號 (MMACCESSLG.SNO)
        mmaplogDao.insertData(apLog);
    }

    /**
     * 記錄 AP Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     *
     * @param userData   使用者物件
     * @param accessType 操作類別 (I/U/D/P/Q)
     * @param tableName  異動的 TABLE 名稱
     * @param apLogList  內含 <code>tw.gov.pcc.common.domain.Mmaplog</code> 物件的 List
     */
    public void loggingApLog(FrameworkUserInfoBean userData, String accessType, String tableName, List<Mmaplog> apLogList) {
        if (userData == null) return;
        Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
        String pkField = apLogList.size() > 0 ? apLogList.get(0)
                .getPkField() : StringUtils.EMPTY;

        // 處理 MMAPLOG
        apLogList.forEach(apLog -> {
            apLog.setChgTime(date); // 異動時間
            apLog.setSno(genSno()); // 編號 (MMACCESSLG.SNO)ted
        });
        mmaplogDao.insertData(apLogList);
    }

    /**
     * 記錄 AP Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 AP Log
     *
     * @param logData <code>tw.gov.pcc.common.domain.Mmaplog</code> 物件
     */
    public void loggingApLog(Mmaplog logData) {
        mmaplogDao.insertData(logData);
    }

    /**
     * 記錄 AP Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 AP Log
     *
     * @param logList 內含 <code>tw.gov.pcc.common.domain.Mmaplog</code> 物件的 List
     */
    public void loggingApLog(List<Mmaplog> logList) {
        mmaplogDao.insertData(logList);
    }

    public void loggingQueryResultLog(FrameworkUserInfoBean userData, String sno, Map<String, Boolean> keysMap, Object result) {
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(((Iterable<?>) result).iterator(), Spliterator.ORDERED), false)
                .forEach(x -> {
                    QueryAdviceHelper.setQueryArgs(new ArrayList<>(), new Object[]{x});
                    loggingQueryResultLog(userData, sno, keysMap);
                });
    }

    public String getPortalLogSeq() {
        return portalLogDao.getSeq();
    }


    public enum LogType {
        INSERT("Insert Log"), DELETE("Delete Log"), UPDATE("Update Log"), QUERY("Query Log"), PORTALLOG("Portal Log"), APLOG("AP Log");
        private final String desc;

        LogType(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return this.desc;
        }
    }
}
