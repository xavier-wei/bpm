package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OrresultDao;
import tw.gov.pcc.eip.domain.Orresult;

import java.util.ArrayList;
import java.util.List;

@DaoTable(OrresultDao.TABLE_NAME)
@Repository
public class OrresultDaoImpl extends BaseDao<Orresult> implements OrresultDao {

    private static final String ALL_COLUMNS_SQL = " ORFORMNO, SEQNO, REGISWAY, REGISNAME, REGISIDN, REGISSEX, " +
            "REGISBRTH, REGISEMAIL, REGISPHONE, FAX, COMPANY, DEGREEN, DEPT, JOGTITLE, REGISADDRES, MEALSTATUS, " +
            "ISPAY, ISPASS, REGISDT, ISNOTIFY, LASTNOTIDT, CERTIPHOURS, CERTIDHOURS, CREUSER, CREDT, UPDUSER, UPDDT ";

    @Override
    public Orresult selectDataByPrimaryKey(Orresult orresult) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where ORFORMNO = :orformno and SEQNO = :seqno" );

        SqlParameterSource params = new MapSqlParameterSource("orformno", orresult.getOrformno())
                .addValue("seqno", orresult.getSeqno());

        List<Orresult> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orresult.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Orresult findByPk(String orformno, String seqno) {
        Orresult t = new Orresult();
        t.setOrformno(orformno);
        t.setSeqno(seqno);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Orresult t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Orresult t, String orformno, String seqno) {
        t.setOrformno(orformno);
        t.setSeqno(seqno);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(Orresult t, String orformno, String seqno) {
        t.setOrformno(orformno);
        t.setSeqno(seqno);
        return super.deleteByPK(t);
    }

    @Override
    public List<Orresult> getDataByOrformno(String orformno, String isPass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME );
        sql.append(" WHERE orformno = :orformno ");
        if (StringUtils.isNotBlank(isPass)) {
            sql.append("   AND ISNULL(isPass,'X') != :isPass ");
        }
        sql.append(" ORDER BY seqno");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno).addValue("isPass",isPass);
        List<Orresult> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orresult.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Orresult> getDataByMultiCondition(String orformno, String name, String status) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME );
        sql.append(" WHERE orformno = :orformno ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND regisname like :name ");
        }
        if ("E".equals(status)) {
            sql.append(" AND isPass is null ");
        } else if (StringUtils.isNotBlank(status)) {
            sql.append(" AND isPass = :status ");
        }
        sql.append(" ORDER BY seqno");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno)
                .addValue("name",'%'+name+'%').addValue("status",status);
        List<Orresult> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orresult.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public Orresult getDataByPerson(String orformno, String userid) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME );
        sql.append(" WHERE orformno = :orformno ");
        sql.append("   AND creuser = :userid ");
        sql.append(" ORDER BY seqno desc");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno).addValue("userid",userid);
        List<Orresult> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orresult.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public String getMaximumSeqno(String orformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ISNULL(MAX(seqno),0)+1 ");
        sql.append("   FROM ORRESULT ");
        sql.append("  WHERE ORFORMNO = :orformno ");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno);
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }

    @Override
    public Orresult getDataByOrformnoAndIdno(String orformno, String idno) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME );
        sql.append(" WHERE orformno = :orformno ");
        sql.append("   AND regisidn = :idno AND ISNULL(ispass,'X') <> 'D' ");
        sql.append(" ORDER BY seqno desc");
        SqlParameterSource params = new MapSqlParameterSource("orformno", orformno).addValue("idno", idno);
        List<Orresult> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orresult.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
