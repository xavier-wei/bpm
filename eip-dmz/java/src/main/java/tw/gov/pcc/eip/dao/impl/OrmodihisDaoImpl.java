package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OrmodihisDao;
import tw.gov.pcc.eip.domain.Ormodihis;
import tw.gov.pcc.eip.domain.Ormodihis;

import java.util.ArrayList;
import java.util.List;

@DaoTable(OrmodihisDao.TABLE_NAME)
@Repository
public class OrmodihisDaoImpl extends BaseDao<Ormodihis> implements OrmodihisDao {

    private static final String ALL_COLUMNS_SQL = " ORFORMNO, SEQNO, CHGTYPE, USERINFO, USERDEPT, CREUSER, CREDT, " +
            "UPDUSER, UPDDT";

    @Override
    public Ormodihis selectDataByPrimaryKey(Ormodihis ormodihis) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where ORFORMNO = :orformno and SEQNO = :seqno" );

        SqlParameterSource params = new MapSqlParameterSource("orformno", ormodihis.getOrformno())
                .addValue("seqno", ormodihis.getSeqno());

        List<Ormodihis> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Ormodihis.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Ormodihis findByPk(String orformno, String seqno) {
        Ormodihis t = new Ormodihis();
        t.setOrformno(orformno);
        t.setOrformno(seqno);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Ormodihis t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Ormodihis t, String orformno, String seqno) {
        t.setOrformno(orformno);
        t.setSeqno(seqno);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(Ormodihis t, String orformno, String seqno) {
        t.setOrformno(orformno);
        t.setSeqno(seqno);
        return super.deleteByPK(t);
    }

    @Override
    public List<Ormodihis> getDataByOrformno(String orformno) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME );
        sql.append(" WHERE orformno = :orformno ");
        sql.append(" ORDER BY seqno DESC");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno);
        List<Ormodihis> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Ormodihis.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public String getMaxSeqno(String orformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT MAX(seqno)+1 maxSeqno ");
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  WHERE orformno = :orformno");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno);
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }
}
