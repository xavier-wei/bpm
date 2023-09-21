package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.eip.dao.SysmsgDao;
import tw.gov.pcc.eip.domain.Sysmsg;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;

@DaoTable("ERRORMSG")
@Repository
public class SysmsgDaoImpl extends BaseDao<Sysmsg> implements SysmsgDao {

    @SkipLog
    @Override
    public Sysmsg selectDataByPrimaryKey(Sysmsg sysmsg) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append(ALL_COLUMNS_SQL);
            sql.append("FROM ERRORMSG A ");
            sql.append("WHERE A.CODE = :code ");

            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sysmsg);
            return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), namedParameters, BeanPropertyRowMapper.newInstance(Sysmsg.class));
        }
        catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    /**
     * 取得 系統訊息檔 的所有內容 (此方法不紀錄 LOG)
     *
     * @return
     */
    @SkipLog
    @Override
    public List<Sysmsg> selectAllData() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM ERRORMSG A");

        return getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(Sysmsg.class));
    }

    @Override
    public void insertData(Sysmsg data) {
        if (data != null) {
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(data);
            getNamedParameterJdbcTemplate().update(INSERT_SQL, namedParameters);
        }
    }

    @Override
    public void updateData(Sysmsg data) {
        if (data != null) {
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(data);
            getNamedParameterJdbcTemplate().update(UPDATE_SQL, namedParameters);
        }
    }

    @Override
    public void deleteData(Sysmsg data) {
        if (data != null) {
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(data);
            getNamedParameterJdbcTemplate().update(DELETE_SQL, namedParameters);
        }
    }

    private static final String ALL_COLUMNS_SQL = " A.ERRORCODE as \"code\", A.ERRORMEMO as \"value\" ";

    private static final String INSERT_SQL = "INSERT INTO ERRORMSG ( "
        + "    CODE, "
        + "    VALUE "
        + ") VALUES ( "
        + "    :code, "
        + "    :value "
        + ")";

    private static final String UPDATE_SQL = "UPDATE ERRORMSG SET "
        + "    VALUE = :value "
        + "WHERE CODE = :code ";

    private static final String DELETE_SQL = "DELETE FROM ERRORMSG WHERE CODE = :code ";
    
}
