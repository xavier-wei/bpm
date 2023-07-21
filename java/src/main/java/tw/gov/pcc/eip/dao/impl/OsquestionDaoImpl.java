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
        sql.append(" FROM " + TABLE_NAME + " where osformno = :osformno and qseqno = :qseqno" );
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osquestion.getOsformno());
        params.put("qseqno", osquestion.getQseqno());
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
    public int deleteByOsformnoList(List<String> osformnoList) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from osquestion where osformno in (:osformnoList)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformnoList", osformnoList);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public List<Osquestion> getAllByOsformno(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  WHERE OSFORMNO = :osformno ");
        sql.append("  order by qseqno ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public Integer getMaximumQseqno(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ISNULL(MAX(qseqno),0)+1 ");
        sql.append("   FROM OSQUESTION ");
        sql.append("  WHERE OSFORMNO = :osformno ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, Integer.class);
    }

    @Override
    public List<Osquestion> getListByOsformno(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME);
        sql.append(" WHERE OSFORMNO = :osformno ");
        sql.append("   AND TOPICSEQ IS NULL");
        sql.append(" ORDER BY SECTITLESEQ ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Osquestion> getAllQuestionByOsformno(String osformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(",(select count(OSFORMNO) from OSQUESTION b where OSFORMNO = :osformno and b.SECTITLESEQ = t.SECTITLESEQ AND TOPICSEQ IS NOT NULL group by OSFORMNO,SECTITLESEQ)rowspan");
        sql.append("  FROM " + TABLE_NAME + " T");
        sql.append(" WHERE OSFORMNO = :osformno ");
        sql.append("   AND TOPICSEQ IS NOT NULL");
        sql.append(" ORDER BY SECTITLESEQ,TOPICSEQ ");
        SqlParameterSource params = new MapSqlParameterSource("osformno", osformno);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Osquestion> getQuestionsByOsformnoAndSectitleseq(String osformno, String sectitleseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME);
        sql.append(" WHERE OSFORMNO = :osformno ");
        sql.append("   AND SECTITLESEQ = :sectitleseq");
        sql.append("   AND TOPICSEQ IS NOT NULL");
        sql.append(" ORDER BY TOPICSEQ ");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Osquestion> getQuestionsByOsformnoAndSectitleseq(String osformno, List<String> sectitleseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("  FROM " + TABLE_NAME);
        sql.append(" WHERE OSFORMNO = :osformno ");
        sql.append("   AND SECTITLESEQ in (:sectitleseq)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public int deleteByOsformnoAndSectitleseqList(String osformno, List<String> sectitleseq) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from osquestion ");
        sql.append(" where osformno = :osformno ");
        sql.append("   and sectitleseq in (:sectitleseq)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int deleteByOsformnoAndQseqnoList(String osformno, List<String> qseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from osquestion ");
        sql.append(" where osformno = :osformno ");
        sql.append("   and qseqno in (:qseqno)");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("qseqno", qseqno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public Osquestion getSinglePartData(String osformno, String sectitleseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  WHERE osformno = :osformno ");
        sql.append("    AND sectitleseq = :sectitleseq ");
        sql.append("    AND topicseq is null ");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Osquestion getSingleQuestionData(String osformno, String sectitleseq, String topicseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  WHERE osformno = :osformno ");
        sql.append("    AND sectitleseq = :sectitleseq ");
        sql.append("    AND topicseq = :topicseq ");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        params.put("topicseq", topicseq);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Osquestion getSingleQuestionData(String osformno, String qseqno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME);
        sql.append("  WHERE osformno = :osformno ");
        sql.append("    AND qseqno = :qseqno ");
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("qseqno", qseqno);
        List<Osquestion> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Osquestion.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int updateBatchSectitleseq(String osformno, String sectitleseq, String targetsectitleseq, boolean isbehind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE " + TABLE_NAME);
        sql.append("    SET SECTITLESEQ = sectitleseq+1 ");
        sql.append("  WHERE OSFORMNO = :osformno ");
        if (isbehind) {
            sql.append("    AND SECTITLESEQ >= :sectitleseq and SECTITLESEQ < 99 ");
        } else {
            sql.append("    AND (SECTITLESEQ < :sectitleseq and SECTITLESEQ >= :targetsectitleseq ) and SECTITLESEQ < 99 ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        params.put("targetsectitleseq", targetsectitleseq);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int updateBatchTopicseq(String osformno, String sectitleseq, String topicseq, String targetTopicseq, boolean isbehind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE " + TABLE_NAME);
        sql.append("    SET TOPICSEQ = topicseq+1 ");
        sql.append("  WHERE OSFORMNO = :osformno ");
        sql.append("    AND SECTITLESEQ = :sectitleseq ");
        if (isbehind) {
            sql.append("    AND topicseq >= :topicseq and topicseq < 99 ");
        } else {
            sql.append("    AND (TOPICSEQ < :topicseq and TOPICSEQ >= :targetTopicseq ) and TOPICSEQ < 99 ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("osformno", osformno);
        params.put("sectitleseq", sectitleseq);
        params.put("topicseq", topicseq);
        params.put("targetTopicseq", targetTopicseq);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

}
