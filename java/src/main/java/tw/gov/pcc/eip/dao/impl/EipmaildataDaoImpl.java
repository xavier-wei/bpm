package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.EipmaildataDao;
import tw.gov.pcc.eip.domain.Eipmaildata;

import java.util.HashMap;
import java.util.List;

/**
 * 郵件寄件資料檔 DaoImpl
 * 
 * @author swho 
 */
@DaoTable(EipmaildataDao.TABLE_NAME)
@Repository
public class EipmaildataDaoImpl extends BaseDao<Eipmaildata> implements EipmaildataDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" t.MAIL_ID, t.BATCH_NO, t.MAIL_KIND, t.EMAIL, t.SUBJECT, t.MESSAGE, t.FILE_PATH, t.IS_MAILED, t.PROCESS_TIMESTAMP, ")
                .append(" t.RETURN_MESSAGE, t.ATTACH_FILE_NAME ")
                .toString();
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param eipmaildata 條件
     * @return 唯一值
     */
    @Override
    public Eipmaildata selectDataByPrimaryKey(Eipmaildata eipmaildata) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t WHERE t.MAIL_ID = :mail_id ");
        List<Eipmaildata> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(eipmaildata), BeanPropertyRowMapper.newInstance(Eipmaildata.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param mail_id Mail ID
     * @return 唯一值
     */
    @Override
    public Eipmaildata selectByKey(String mail_id) {
        Eipmaildata eipmaildata = new Eipmaildata();
        eipmaildata.setMail_id(mail_id);
        return selectDataByPrimaryKey(eipmaildata);
    }

    /**
     * 根據key刪除資料
     *
     * @param eipmaildata 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Eipmaildata eipmaildata) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE MAIL_ID = :mail_id ",
                new BeanPropertySqlParameterSource(eipmaildata));
    }

    /**
     * 根據key更新資料
     *
     * @param eipmaildata 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Eipmaildata eipmaildata) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " BATCH_NO = :batch_no, MAIL_KIND = :mail_kind, EMAIL = :email, SUBJECT = :subject, " +
                        " MESSAGE = :message, FILE_PATH = :file_path, IS_MAILED = :is_mailed, PROCESS_TIMESTAMP = :process_timestamp, RETURN_MESSAGE = :return_message, " +
                        " ATTACH_FILE_NAME = :attach_file_name" +
                        " WHERE MAIL_ID = :mail_id ",
                new BeanPropertySqlParameterSource(eipmaildata));
    }

    /**
     * 新增一筆資料
     *
     * @param eipmaildata 新增資料
     */
    @Override
    public int insert(Eipmaildata eipmaildata) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " MAIL_ID, BATCH_NO, MAIL_KIND, EMAIL, SUBJECT, MESSAGE, FILE_PATH, IS_MAILED, PROCESS_TIMESTAMP, " +
                        " RETURN_MESSAGE, ATTACH_FILE_NAME" +
                        ")" +
                        " VALUES ( " +
                        " :mail_id, :batch_no, :mail_kind, :email, :subject, :message, :file_path, :is_mailed, :process_timestamp, " +
                        " :return_message, :attach_file_name" +
                        ")",
                new BeanPropertySqlParameterSource(eipmaildata));
    }

    @Override
    public String getMail_id(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("FORMAT (getdate(), 'yyyyMMddHHmmss') + RIGHT(REPLICATE('0', 6) + CAST(NEXT VALUE FOR EIP_COMMON as NVARCHAR), 6) ");
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), new HashMap<>(), String.class);
    }
    
    @Override
    @SkipLog
    public List<Eipmaildata> getListByIsMailedIsNull() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM EIPMAILDATA T WHERE NULLIF(TRIM(IS_MAILED), '') IS NULL");
        return getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Eipmaildata.class));
    }
    
}