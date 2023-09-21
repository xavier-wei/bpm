package tw.gov.pcc.common.dao.impl;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.dao.MmaplogDao;
import tw.gov.pcc.common.domain.Mmaplog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.common.util.StrUtil;
import java.util.List;

@Repository
public class MmaplogDaoImpl extends BaseDao<Mmaplog> implements MmaplogDao {

    @SkipLog
    @Override
    public Mmaplog selectDataByPrimaryKey(Mmaplog mmaplog) {
        return null; // 底層不實作，其它系統功能 DAO 勿學
    }

    /**
     * 紀錄 AP Log (單筆)
     *
     * @param logData <code>Mmaplog</code> 物件
     */
    @SkipLog
    @Override
    public void insertData(Mmaplog logData) {
        if (logData == null)
            return;

        // 檢核各欄位長度
        // [
        if (StrUtil.stringRealLength(logData.getPkField()) > 256) // 異動 TABLE主 鍵
            logData.setPkField(StrUtil.chtStrSubstring(logData.getPkField(), 0, 256));
        if (StrUtil.stringRealLength(logData.getField()) > 2000) // 異動欄位
            logData.setField(StrUtil.chtStrSubstring(logData.getField(), 0, 2000));
        if (StrUtil.stringRealLength(logData.getBefImg()) > 2000) // 改前內容
            logData.setBefImg(StrUtil.chtStrSubstring(logData.getBefImg(), 0, 2000));
        if (StrUtil.stringRealLength(logData.getAftImg()) > 2000) // 改後內容
            logData.setAftImg(StrUtil.chtStrSubstring(logData.getAftImg(), 0, 2000));
        // ]

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(logData);
        getNamedParameterJdbcTemplate().update(INSERT_SQL, namedParameters);
    }

    /**
     * 紀錄 AP Log (多筆)
     *
     * @param logList 內含 <code>Mmaplog</code> 物件的 List
     */
    @SkipLog
    @Override
    public void insertData(List<Mmaplog> logList) {
        if (logList == null || logList.size() == 0)
            return;

        for (Mmaplog logData : logList) {
            // 檢核各欄位長度
            // [
            if (StrUtil.stringRealLength(logData.getPkField()) > 256) // 異動 TABLE主 鍵
                logData.setPkField(StrUtil.chtStrSubstring(logData.getPkField(), 0, 256));
            if (StrUtil.stringRealLength(logData.getField()) > 2000) // 異動欄位
                logData.setField(StrUtil.chtStrSubstring(logData.getField(), 0, 2000));
            if (StrUtil.stringRealLength(logData.getBefImg()) > 2000) // 改前內容
                logData.setBefImg(StrUtil.chtStrSubstring(logData.getBefImg(), 0, 2000));
            if (StrUtil.stringRealLength(logData.getAftImg()) > 2000) // 改後內容
                logData.setAftImg(StrUtil.chtStrSubstring(logData.getAftImg(), 0, 2000));
            // ]
        }

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(logList.toArray());
        getNamedParameterJdbcTemplate().batchUpdate(INSERT_SQL, batch);
    }

    private static final String INSERT_SQL = "INSERT INTO APLOG ( "
        + "    TABLENAME, "
        + "    PKFIELD, "
        + "    CHGTIME, "
        + "    PGMNAME, "
        + "    PGMCODE, "
        + "    DEPTID, "
        + "    MODIFYMAN, "
        + "    TERMED, "
        + "    CHGCODE, "
        + "    FIELD, "
        + "    BEFIMG, "
        + "    AFTIMG, "
        + "    MEMO, "
        + "    SNO "
        + ") VALUES ( "
        + "    :tableName, "
        + "    :pkField, "
        + "    :chgTime, "
        + "    :pgmName, "
        + "    :pgmCode, "
        + "    :deptId, "
        + "    :modifyMan, "
        + "    :termEd, "
        + "    :chgCode, "
        + "    :field, "
        + "    :befImg, "
        + "    :aftImg, "
        + "    :memo, "
        + "    :sno "
        + ")";

}
