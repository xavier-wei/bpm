package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.domain.Orformdata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@DaoTable(OrformdataDao.TABLE_NAME)
@Repository
public class OrformdataDaoImpl extends BaseDao<Orformdata> implements OrformdataDao {

    private static final String ALL_COLUMNS_SQL = " ORFORMNO, ORCCODE, COURSECLACODE, COURSECODE, CLASSCODE, PERIOD, " +
            "TOPICNAME, TOPICDESC, STATUS, ORGANIZER, CONTACTER, CONTACTNUM, FAX, EMAIL, ADDRES, COUNTRY, PROFMDT, " +
            "PROENDT, ACCEPTAPPNUM, ALLOWAPPNUM, ALLOWAPPWAY, FEE, ACCOUNT, ISMEALS, CLASSHOURS, CERTIHOURS, REGISFMDT, " +
            "REGISENDT, LECTURERCODE, PASSMSG, REJECTMST, REGISQUAL, REMARK, CREUSER, CREDT, UPDUSER, UPDDT, SUBJECT";

    @Override
    public Orformdata selectDataByPrimaryKey(Orformdata orformdata) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " where ORFORMNO = :orformno" );

        SqlParameterSource params = new MapSqlParameterSource("orformno", orformdata.getOrformno());

        List<Orformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Orformdata.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public Orformdata findByPk(String orformno) {
        Orformdata t = new Orformdata();
        t.setOrformno(orformno);
        return selectDataByPrimaryKey(t);
    }

    @Override
    public int insertData(Orformdata t) {
        return super.insert(t);
    }

    @Override
    public int updateData(Orformdata t, String orformno) {
        t.setOrformno(orformno);
        return super.updateByPKForMssql(t);
    }

    @Override
    public int deleteData(String orformno) {
        return 0;
    }

    @Override
    public List<Orformdata> getAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" ,(select count(*) ");
        sql.append("     from ORRESULT a ");
        sql.append("    where t.ORFORMNO = a.ORFORMNO ");
        sql.append("      and ISNULL(a.ISPASS,'X') <> 'D') actualappnum, ");
        sql.append("  (select count(*) ");
        sql.append("     from ORRESULT a ");
        sql.append("    where t.ORFORMNO = a.ORFORMNO ");
        sql.append("      and a.ISPASS = 'Y') passnum ");
        sql.append(" FROM " + TABLE_NAME + " t");
        sql.append(" ORDER BY orformno");


        List<Orformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Orformdata.class));

        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Orformdata> getListByMultiCondition(Orformdata orformdata) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" ,(select count(*) ");
        sql.append("     from ORRESULT a ");
        sql.append("    where t.ORFORMNO = a.ORFORMNO ");
        sql.append("      and ISNULL(a.ISPASS,'X') <> 'D') actualappnum, ");
        sql.append("  (select count(*) ");
        sql.append("     from ORRESULT a ");
        sql.append("    where t.ORFORMNO = a.ORFORMNO ");
        sql.append("      and a.ISPASS = 'Y') passnum ");
        sql.append("  FROM ORFORMDATA t");
        sql.append(" WHERE STATUS = ISNULL(:status, STATUS) ");
        if (isNotBlank(orformdata.getTopicname())) {
            sql.append("   AND TOPICNAME like '%' + :topicname + '%' ");
        }
        if (isNotBlank(orformdata.getRegisfmdtStr())) {
            sql.append("   AND CONVERT(varchar(6), REGISFMDT, 112) >= :regisfmdt ");
        }
        if (isNotBlank(orformdata.getRegisendtStr())) {
            sql.append("   AND CONVERT(varchar(6), REGISENDT, 112) <= :regisendt ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("topicname", orformdata.getTopicname());
        params.put("status", orformdata.getStatus());
        params.put("regisfmdt", orformdata.getRegisfmdtStr());
        params.put("regisendt", orformdata.getRegisendtStr());
        List<Orformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),params,
                BeanPropertyRowMapper.newInstance(Orformdata.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Orformdata> getOrstartingData(Long orccode) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME);
        sql.append(" WHERE orccode = :orccode ");
//        sql.append("   AND status NOT IN ('N') ");
        Map<String, Object> params = new HashMap<>();
        params.put("orccode", orccode);
        List<Orformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),params,
                BeanPropertyRowMapper.newInstance(Orformdata.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public String getMaximumOrformno(String ym) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUBSTRING(MAX(orformno), 8, 4)+1 ");
        sql.append("   FROM orformdata ");
        sql.append("  WHERE orformno LIKE :ym ");
        SqlParameterSource params = new MapSqlParameterSource("ym", "OR" + ym + "%");
        return getNamedParameterJdbcTemplate().queryForObject(
                sql.toString(), params, String.class);
    }

    @Override
    public int deleteCheckedForm(List<String> orformno) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from orformdata where orformno in (:orformno)");
        Map<String, Object> params = new HashMap<>();
        params.put("orformno", orformno);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int updateStatus(List<String> orformno, String status) {
        String sql = new StringBuilder()
                .append(" UPDATE " + TABLE_NAME)
                .append(" SET STATUS = :status ")
                .append(" WHERE orformno in (:orformno) ").toString();
        Map<String, Object> params = new HashMap<>();
        params.put("orformno", orformno);
        params.put("status", status);
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<Orformdata> getDataByStatus(List<String> statusList, String deptno, String jobtitle) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME);
        sql.append(" WHERE status IN (:statusList) ");
        sql.append("   AND ((regisqual = :deptno ");
        sql.append("        OR regisqual like :deptno + ',%' ");
        sql.append("        OR regisqual like '%,' + :deptno ");
        sql.append("        OR regisqual like '%,' + :deptno + ',%')");
        sql.append("    OR (regisqual = :jobtitle ");
        sql.append("        OR regisqual like :jobtitle + ',%' ");
        sql.append("        OR regisqual like '%,' + :jobtitle ");
        sql.append("        OR regisqual like '%,' + :jobtitle + ',%'))");
        Map<String, Object> params = new HashMap<>();
        params.put("statusList", statusList);
        params.put("deptno", deptno);
        params.put("jobtitle", jobtitle);
        List<Orformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),params,
                BeanPropertyRowMapper.newInstance(Orformdata.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }
    
    @Override
    public int updateStatusBatch() {
    	String sql = new StringBuffer()
    			.append(" UPDATE " + TABLE_NAME)
				.append(" SET STATUS = ( ")
				.append(" 		CASE ")
				.append(" 			WHEN STATUS = 'P' ")
				.append(" 				THEN 'A' ")
				.append(" 			WHEN STATUS = 'A' ")
				.append(" 				THEN 'I' ")
				.append(" 			WHEN STATUS = 'I' ")
				.append(" 				THEN 'C' ")
				.append(" 			ELSE STATUS ")
				.append(" 			END ")
				.append(" 		) ")
				.append(" WHERE 1 = ( ")
				.append(" 		CASE ")
				.append(" 			WHEN STATUS = 'P' ")
				.append(" 				AND REGISFMDT <= :curdttime ")
				.append(" 				AND REGISENDT > :curdttime ")
				.append(" 				THEN '1' ")
				.append(" 			WHEN STATUS = 'A' ")
				.append(" 				AND PROFMDT <= :curdttime ")
				.append(" 				AND PROENDT > :curdttime ")
				.append(" 				THEN '1' ")
				.append(" 			WHEN STATUS = 'I' ")
				.append(" 				AND PROENDT <= :curdttime ")
				.append(" 				THEN '1' ")
				.append(" 			ELSE '0' ")
				.append(" 			END ")
				.append(" 		) ").toString();
        Map<String, Object> params = new HashMap<>();
        params.put("curdttime", LocalDateTime.now());
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<Orformdata> getListByCourseclacode(Long courseclacode, String orformno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME);
        sql.append(" WHERE courseclacode = :courseclacode ");
        sql.append("   AND orformno <> :orformno ");
        Map<String, Object> params = new HashMap<>();
        params.put("courseclacode", courseclacode);
        params.put("orformno", orformno);
        List<Orformdata> list = getNamedParameterJdbcTemplate().query(sql.toString(),params,
                BeanPropertyRowMapper.newInstance(Orformdata.class));
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }
}
