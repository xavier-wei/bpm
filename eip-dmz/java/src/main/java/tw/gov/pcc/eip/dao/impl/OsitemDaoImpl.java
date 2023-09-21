package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OsitemDao;
import tw.gov.pcc.eip.domain.Ositem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(OsitemDao.TABLE_NAME)
@Repository
public class OsitemDaoImpl extends BaseDao<Ositem> implements OsitemDao {

    private static final String ALL_COLUMNS_SQL = " OSFORMNO, ISEQNO, QSEQNO, ITEMSEQ, ITEMDESC, ISADDTEXT, CREUSER, CREDT, " +
            "UPDUSER, UPDDT ";
    @Override
    public Ositem selectDataByPrimaryKey(Ositem ositem) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where osformno = :osformno" );
        sql.append("  AND iseqno = :iseqno" );
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", ositem.getOsformno());
        params.put("iseqno", ositem.getIseqno());
        List<Ositem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Ositem.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Ositem findByPk(String osformno, Integer iseqno) {
        Ositem t = new Ositem();
        t.setOsformno(osformno);
        t.setIseqno(iseqno);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Ositem t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Ositem t, String osformno, Integer iseqno) {
        t.setOsformno(osformno);
        t.setIseqno(iseqno);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(String osformno, Integer iseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ositem where osformno=:osformno and iseqno=:iseqno");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("iseqno", iseqno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int deleteDataByQseqno(String osformno, Integer qseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ositem where osformno=:osformno and qseqno=:qseqno");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("qseqno", qseqno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int deleteByOsformnoList(List<String> osformnoList) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ositem where osformno in (:osformnoList)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformnoList", osformnoList);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int deleteByOsformnoAndIseqnoList(String osformno, List<String> iseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ositem ");
        sql.append(" where osformno = :osformno ");
        sql.append("   and iseqno in (:iseqno)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("iseqno", iseqno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public List<Ositem> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  order by iseqno ");

        List<Ositem> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Ositem.class));

        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public String getMaximumIseqno(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ISNULL(MAX(iseqno),0)+1 ");
        sql.append("   FROM OSITEM ");
        sql.append("  WHERE OSFORMNO = :osformno ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }

    @Override
    public List<Ositem> getItemsByOsformnoAndQseqno(String osformno, String qseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME);
        sql.append(" WHERE OSFORMNO = :osformno ");
        sql.append("   AND QSEQNO = :qseqno");
        sql.append(" ORDER BY ITEMSEQ ");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("qseqno", qseqno);
        List<Ositem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Ositem.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

}
