package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OsclassDao;
import tw.gov.pcc.eip.domain.Osclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(OsclassDao.TABLE_NAME)
@Repository
public class OsclassDaoImpl extends BaseDao<Osclass> implements OsclassDao {

    private static final String ALL_COLUMNS_SQL = " OSCCODE, OSCNAME, CREUSER, CREDT, UPDUSER, UPDDT";

    @Override
    public Osclass selectDataByPrimaryKey(Osclass osclass) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where osccode = :osccode" );

        SqlParameterSource params = new MapSqlParameterSource("osccode", osclass.getOsccode());

        List<Osclass> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osclass.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Osclass findByPk(Integer osccode) {
        Osclass t = new Osclass();
        t.setOsccode(osccode);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Osclass t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Osclass t, Integer osccode) {
        t.setOsccode(osccode);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(Integer osccode) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from osclass where osccode=:osccode");
        Map<String, Object> params = new HashMap<>();
        params.put("osccode", osccode);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public List<Osclass> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  ORDER BY OSCCODE ");
        List<Osclass> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Osclass.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public Integer getOsccode() {
        StringBuilder sql = new StringBuilder();
        sql.append("select ISNULL(MAX(osccode),0)+1 maxOsccode FROM osclass");
        SqlParameterSource params = new MapSqlParameterSource();
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, Integer.class);
    }
}
