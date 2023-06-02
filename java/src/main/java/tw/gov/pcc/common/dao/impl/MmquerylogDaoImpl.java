package tw.gov.pcc.common.dao.impl;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.dao.MmquerylogDao;
import tw.gov.pcc.common.domain.Mmquerylog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.common.util.StrUtil;

@Repository
public class MmquerylogDaoImpl extends BaseDao<Mmquerylog> implements MmquerylogDao {

    @SkipLog
    @Override
    public Mmquerylog selectDataByPrimaryKey(Mmquerylog mmquerylog) {
        return null; // 底層不實作，其它系統功能 DAO 勿學
    }

    /**
     * 紀錄 Query Log
     *
     * @param logData <code>Mmquerylog</code> 物件
     */
    @SkipLog
    @Override
    public void insertData(Mmquerylog logData) {
        if (logData == null)
            return;

        // 檢核各欄位長度
        // [
        if (StrUtil.stringRealLength(logData.getQyCondition()) > 4000) // 查詢條件
            logData.setQyCondition(StrUtil.chtStrSubstring(logData.getQyCondition(), 0, 4000));

        if (StrUtil.stringRealLength(logData.getMemo()) > 256) // 備註
            logData.setQyCondition(StrUtil.chtStrSubstring(logData.getMemo(), 0, 256));
        // ]

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(logData);
        getNamedParameterJdbcTemplate().update(INSERT_SQL, namedParameters);
    }

    private static final String INSERT_SQL = "INSERT INTO MMQUERYLOG ( "
        + "    TABLENAME, "
        + "    QYTIME, "
        + "    PGMNAME, "
        + "    PGMCODE, "
        + "    DEPTID, "
        + "    QUERYMAN, "
        + "    TERMED, "
        + "    QYCODE, "
        + "    QYCONDITION, "
        + "    IDNO, "
        + "    MEMO, "
        + "    SNO "
        + ") VALUES ( "
        + "    :tableName, "
        + "    :qyTime, "
        + "    COALESCE(:pgmName,' '), "
        + "    COALESCE(:pgmCode,' '), "
        + "    :deptId, "
        + "    :queryMan, "
        + "    :termEd, "
        + "    :qyCode, "
        + "    :qyCondition, "
        + "    :idNo, "
        + "    :memo, "
        + "    :sno "
        + ")";

}
