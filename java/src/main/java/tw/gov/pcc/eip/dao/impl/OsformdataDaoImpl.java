package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.domain.Orformdata;
import tw.gov.pcc.eip.domain.Osformdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@DaoTable(OsformdataDao.TABLE_NAME)
@Repository
public class OsformdataDaoImpl extends BaseDao<Osformdata> implements OsformdataDao {

    private static final String ALL_COLUMNS_SQL = " OSFORMNO, OSCCODE, TOPICNAME, OSFMDT, OSENDT, TOPICDESC, ORGANIZER, " +
            "PROMPTMSG, ISDISSTATIC, ISLIMITONE, ISANONYMOUS, LIMITVOTE, MAILSUBJECT, MAILMSG, STATUS, CREUSER, CREDT, " +
            "UPDUSER, UPDDT";

    @Override
    public Osformdata selectDataByPrimaryKey(Osformdata osformdata) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where OSFORMNO = :osformno" );

        SqlParameterSource params = new MapSqlParameterSource("osformno", osformdata.getOsformno());

        List<Osformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osformdata.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Osformdata findByPk(String osformno) {
        Osformdata t = new Osformdata();
        t.setOsformno(osformno);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Osformdata t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Osformdata t, String osformno) {
        t.setOsformno(osformno);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(String osformno) {
        return 0;
    }

    @Override
    public List<Osformdata> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME + " t");
        sql.append(" ORDER BY osformno");
        List<Osformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Osformdata.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Osformdata> getListByMultiCondition(Osformdata osformdata) {

        return new ArrayList<>();
    }


    @Override
    public String getMaximumOsformno(String ym) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUBSTRING(MAX(osformno), 8, 4)+1 ");
        sql.append("   FROM osformdata ");
        sql.append("  WHERE osformno LIKE :ym ");
        SqlParameterSource params = new MapSqlParameterSource("ym", "OS" + ym + "%");
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }

    @Override
    public int deleteCheckedForm(List<String> osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from osformdata where osformno in (:osformno)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int updateStatus(List<String> osformno, String status) {
        String sql = new StringBuilder()
                .append(" UPDATE " + TABLE_NAME)
                .append(" SET STATUS = :status ")
                .append(" WHERE osformno in (:osformno) ").toString();
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("status", status);
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<Osformdata> getStartingData(Integer osccode) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME);
        sql.append(" WHERE osccode = :osccode ");
        sql.append("   AND status NOT IN ('N') ");
        Map<String, Object> params = new HashMap<>();
        params.put("osccode", osccode);
        List<Osformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),params,
                BeanPropertyRowMapper.newInstance(Osformdata.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

}
