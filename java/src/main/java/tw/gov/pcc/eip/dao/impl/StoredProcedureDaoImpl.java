package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.StoredProcedureDao;
import tw.gov.pcc.eip.domain.Eipcode;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 共用的StoredProcedureDao
 */
@Repository
public class StoredProcedureDaoImpl extends BaseDao<Object> implements StoredProcedureDao {

    @Override
    @SkipLog
    public Object selectDataByPrimaryKey(Object t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SkipLog
    public Map<String, Object> getJobno() {
        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate()).withProcedureName("USP_GET_JOBNO");
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_jobno", "");
        return jdbcSpCall.execute(String.class, "");
    }

    /**
     * 紀錄執行批次作業 LOG
     *
     * @param jobno    流水號
     * @param jobid    程式編號
     * @param jobname  程式中文名稱
     * @param step     處理階段(START, PARAMS, INFO, EXCEPTION, END)
     * @param stepinfo 處理階段資訊
     * @param memo     備註說明
     */
    @Override
    public void callSpJobLog(String jobno, String jobid, String jobname, String step, String stepinfo, String memo) {
        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate()).withProcedureName("SP_ADD_EIPJOBLOG");
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_jobno", jobno).addValue("p_job_id", jobid)
                .addValue("p_job_name", jobname).addValue("p_step", step).addValue("p_stepinfo", stepinfo)
                .addValue("p_memo", memo);

        jdbcSpCall.execute(in);
    }

    @Override
    public Object callProcedure(String catalogName, String procedureName, SqlParameterSource param) {

        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate()).withCatalogName(catalogName)
                .withProcedureName(procedureName);

        return jdbcSpCall.execute(param);
    }

    @Override
    public String callFunction(String catalogName, String functionName, SqlParameterSource param) {

        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate()).withCatalogName(catalogName)
                .withFunctionName(functionName);

        if (!Objects.isNull(param)) {
            return jdbcSpCall.executeFunction(String.class, param);
        }
        return jdbcSpCall.executeFunction(String.class);
    }

    @Override
    public Object callProcedure(String procedureName, SqlParameterSource param) {

        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate()).withProcedureName(procedureName);
        return jdbcSpCall.execute(param);
    }

    @Override
    public Object callProcedure(String procedureName, SqlParameterSource param, SqlParameter[] sqlParameters) {
        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate()).withProcedureName(procedureName).declareParameters(sqlParameters);
        return jdbcSpCall.execute(param);
    }

    @Override
    public Map<String, Object> callProcedureWithoutMetaData(String catalogName, String procedureName,
            SqlParameterSource param, SqlParameter... sqlParameters) {

        SimpleJdbcCall jdbcSpCall = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(procedureName).declareParameters(sqlParameters);
        jdbcSpCall.setAccessCallParameterMetaData(false);

        return jdbcSpCall.execute(param);
    }

    @Override
    @SkipLog
    public List<Eipcode> findByCodeKind(String codeKind) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ")
			.append(" T.CODEKIND as codekind,T.CODENO as codeno,T.SCODEKIND as scodekind,T.SCODENO as scodeno,T.CODENAME as codename,T.STAFF as staff,")
            .append(" T.PRCDAT as prcdat,T.REMARK as remark")
            .append(" FROM UFN_GET_EIPCODE(:codekind) T" );

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eipcode.class));
    }


}
