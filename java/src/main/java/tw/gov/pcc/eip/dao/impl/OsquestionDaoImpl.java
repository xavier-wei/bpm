package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OsquestionDao;
import tw.gov.pcc.eip.domain.Osquestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(OsquestionDao.TABLE_NAME)
@Repository
public class OsquestionDaoImpl extends BaseDao<Osquestion> implements OsquestionDao {

    private static final String ALL_COLUMNS_SQL = " OSFORMNO, QSEQNO, SECTITLESEQ, SECTITLE, TOPICSEQ, TOPIC, " +
            "OPTIONTYPE, ISREQUIRED, CREUSER, CREDT, UPDUSER, UPDDT ";
    @Override
    public Osquestion selectDataByPrimaryKey(Osquestion osquestion) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where osformno = :osformno" );

        SqlParameterSource params = new MapSqlParameterSource("osformno", osquestion.getOsformno());

        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Osquestion findByPk(String osformno, Integer qseqno) {
        Osquestion t = new Osquestion();
        t.setOsformno(osformno);
        t.setQseqno(qseqno);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Osquestion t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Osquestion t, String osformno, Integer qseqno) {
        t.setOsformno(osformno);
        t.setQseqno(qseqno);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(String osformno, Integer qseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from osquestion where osformno=:osformno and qseqno=:qseqno");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("qseqno", qseqno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public List<Osquestion> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  order by qseqno ");

        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Osquestion.class));

        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public String getMaximumQseqno(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ISNULL(MAX(qseqno),0)+1 ");
        sql.append("   FROM OSQUESTION ");
        sql.append("  WHERE OSFORMNO = :osformno ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }

}
