package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OsresultDao;
import tw.gov.pcc.eip.domain.Osresult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(OsresultDao.TABLE_NAME)
@Repository
public class OsresultDaoImpl extends BaseDao<Osresult> implements OsresultDao {

    private static final String ALL_COLUMNS_SQL = " OSFORMNO, WRISEQ, WRIJOGTITLE, WRINAME, WRIAD, WRICONTENT, " +
            "CREUSER, CREDT, UPDUSER, UPDDT ";
    @Override
    public Osresult selectDataByPrimaryKey(Osresult Osresult) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where osformno = :osformno" );

        SqlParameterSource params = new MapSqlParameterSource("osformno", Osresult.getOsformno());

        List<Osresult> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osresult.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Osresult findByPk(String osformno, Integer wriseq) {
        Osresult t = new Osresult();
        t.setOsformno(osformno);
        t.setWriseq(wriseq);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Osresult t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Osresult t, String osformno, Integer wriseq) {
        t.setOsformno(osformno);
        t.setWriseq(wriseq);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(String osformno, Integer wriseq) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from Osresult where osformno=:osformno and wriseq=:wriseq");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("wriseq", wriseq);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public List<Osresult> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  order by wriseq ");

        List<Osresult> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Osresult.class));

        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public String getMaximumWriseq(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ISNULL(MAX(wriseq),0)+1 ");
        sql.append("   FROM OSQUESTION ");
        sql.append("  WHERE OSFORMNO = :osformno ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }

}
