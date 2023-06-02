package tw.gov.pcc.eip.dao.impl;

import org.aspectj.weaver.ast.Or;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OrclassDao;
import tw.gov.pcc.eip.domain.Orclass;

import java.math.BigDecimal;
import java.util.*;

@DaoTable(OrclassDao.TABLE_NAME)
@Repository
public class OrclassDaoImpl extends BaseDao<Orclass> implements OrclassDao {

    private static final String ALL_COLUMNS_SQL = " ORCCODE, ORCNAME, ISCOURSE, SIGNFORM, " +
            "CREUSER, CREDT, UPDUSER, UPDDT ";
    private static final String UPDATE_SQL = new StringBuilder()
                 .append(" UPDATE " + TABLE_NAME)
                 .append(" SET ORCCODE = :orccode, ORCNAME = :orcname, ISCOURSE = :iscourse, SIGNFORM = :signform, ")
                 .append(" CREUSER = :creuser, CREDT = :credt, UPDUSER = :upduser, UPDDT = :upddt ")
                 .append(" WHERE ORCCODE = :orccode").toString();
    @Override
    public Orclass selectDataByPrimaryKey(Orclass orclass) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where orccode = :orccode" );

        SqlParameterSource params = new MapSqlParameterSource("orccode", orclass.getOrccode());

        List<Orclass> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orclass.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Orclass findByPk(Long orccode) {
        Orclass t = new Orclass();
        t.setOrccode(orccode);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Orclass t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Orclass t, Long orccode) {
        String sql = new StringBuilder()
                .append(" UPDATE " + TABLE_NAME)
                .append(" SET ORCCODE = :orccode, ORCNAME = :orcname, ISCOURSE = :iscourse, SIGNFORM = :signform, ")
                .append(" UPDUSER = :upduser, UPDDT = :upddt ")
                .append(" WHERE ORCCODE = :orccode").toString();
        Map<String, Object> params = new HashMap<>();
        params.put("orccode", orccode);
        params.put("orcname", t.getOrcname());
        params.put("iscourse", t.getIscourse());
        params.put("signform", t.getSignform());
        params.put("upddt", t.getUpddt());
        params.put("upduser", t.getUpduser());
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public int deleteData(Long orccode) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from orclass where orccode=:orccode");
        Map<String, Object> params = new HashMap<>();
        params.put("orccode", orccode);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public List<Orclass> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  ORDER BY ORCCODE ");

        List<Orclass> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Orclass.class));

        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public Long getOrccode() {
        StringBuilder sql = new StringBuilder();
        sql.append("select MAX(orccode)+1 maxOrccode FROM orclass");
        SqlParameterSource params = new MapSqlParameterSource();
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, Long.class);
    }
}
