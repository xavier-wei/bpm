package tw.gov.pcc.common.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.dao.PortalLogDao;
import tw.gov.pcc.common.domain.PortalLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.common.util.StrUtil;

import java.util.*;

@Repository
public class PortalLogDaoImpl extends BaseDao<PortalLog> implements PortalLogDao {

    public static final String SELECT_SEQ_SQL = "SELECT NEXT VALUE FOR portal_id";

    @SkipLog
    @Override
    public PortalLog selectDataByPrimaryKey(PortalLog portalLog) {
        return null; // 底層不實作，其它系統功能 DAO 勿學
    }

    /**
     * 紀錄 Portal Log
     *
     * @param logData <code>PortalLog</code> 物件
     * @return <code>PORTAL_LOG.SYS_ID</code>
     */
    @SkipLog
    @Override
    public String insertData(PortalLog logData) {
        if (logData == null)
            return null;

        // 檢核各欄位長度
        // [
        if (StrUtil.stringRealLength(logData.getApUrl()) > 200) // 應用系統網址
            logData.setApUrl(StringUtils.defaultString(StrUtil.chtStrSubstring(logData.getApUrl(), 0, 200)));

        if (StrUtil.stringRealLength(logData.getLogDescript()) > 100) // 說明
            logData.setLogDescript(StringUtils.defaultString(StrUtil.chtStrSubstring(logData.getLogDescript(), 0, 100)));
        // ]

        String seq = getSeq();
        logData.setSno(seq);
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(logData);

        int n = getNamedParameterJdbcTemplate().update(INSERT_SQL, namedParameters);

        
        
        
        if (n > 0) {
            return logData.getSno();
        }
        else {
            return null;
        }
    }

    private static final String INSERT_SQL = "INSERT INTO PORTAL_LOG ( "
        + "    SNO, "
        + "    LOG_DATETIME, "
        + "    USER_ID, "
        + "    USER_IP, "
        + "    USER_ACTION, "
        + "    AP_CODE, "
        + "    AP_NAME, "
        + "    AP_FUNCTION_CODE, "
        + "    AP_FUNCTION_NAME, "
        + "    AP_URL, "
        + "    LOG_DESCRIPT, "
        + "    DATE_TIME, "
        + "    TOKEN "
        + ") VALUES ( "
        + "    NEXT VALUE FOR portal_id, "
        + "    :logDateTime, "
        + "    :userId, "
        + "    :userIP, "
        + "    :userAction, "
        + "    :apCode, "
        + "    :apName, "
        + "    :apFunctionCode, "
        + "    :apFunctionName, "
        + "    :apUrl, "
        + "    :logDescript, "
        + "    :dateTime, "
        + "    :token "
        + ")";
    
    @SkipLog
    @Override
    public String getSeq(){
        return getNamedParameterJdbcTemplate().queryForObject(SELECT_SEQ_SQL, new HashMap<>(), String.class);
    }

}
