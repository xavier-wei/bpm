package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.Eipcode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Weith
 *
 */
@DaoTable(EipcodeDao.TABLE_NAME)
@Repository
public class EipcodeDaoImpl extends BaseDao<Eipcode> implements EipcodeDao {

	private static final String ALL_COLUMNS_SQL;

	static {
		ALL_COLUMNS_SQL = new StringBuilder()
			.append(" T.CODEKIND as codekind,T.CODENO as codeno,T.SCODEKIND as scodekind,T.SCODENO as scodeno,T.CODENAME as codename,T.STAFF as staff,")
			.append(" T.PRCDAT as prcdat,T.REMARK as remark")
			.toString();
	}

	@Override
	@SkipLog
	public Eipcode selectDataByPrimaryKey(Eipcode eipcode) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(ALL_COLUMNS_SQL);
		sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND= :codekind and T.CODENO = :codeno" );

		SqlParameterSource params = new MapSqlParameterSource("codekind", eipcode.getCodekind()).addValue("codeno",
				eipcode.getCodeno());

		List<Eipcode> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Eipcode.class));

		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	@SkipLog
	public List<Eipcode> findByCodeKind(String codeKind) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(ALL_COLUMNS_SQL);
		sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind order by codeno");

		SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

		return getNamedParameterJdbcTemplate()
				.query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Eipcode.class));
	}

    @Override
    @SkipLog
    public List<Eipcode> findByCodeKindOrderByScodeno(String codeKind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind order by T.SCODENO");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eipcode.class));
    }

	@Override
	@SkipLog
	public List<Eipcode> findByCodeKindScodeno(String codeKind, String scodeno) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.SCODENO  =:scodeno ");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind).addValue("scodeno", scodeno);

        return getNamedParameterJdbcTemplate()
        		.query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Eipcode.class));
	}

    /**
     * 查詢 BECODE by codeKind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    @Override
    @SkipLog
    public List<Eipcode> findByCodekindOrderByCodeno(String codekind) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" ORDER BY T.codeno");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eipcode.class));
    }

    /**
     * 查詢 BECODE by codeKind, scodekind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    @Override
    @SkipLog
    public List<Eipcode> findByCodekindScodekindOrderByCodeno(String codekind, String scodekind) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.SCODEKIND = :scodekind ");
        sql.append(" ORDER BY T.codekind");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind).addValue("scodekind", scodekind);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eipcode.class));
    }

    @Override
    public Optional<Eipcode> findByCodeKindPresent(String afcodekind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODEKIND ");
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :afcodekind ");

        SqlParameterSource params = new MapSqlParameterSource("afcodekind", afcodekind);
        return getNamedParameterJdbcTemplate()
            .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eipcode.class))
            .stream()
            .findAny();
    }
    @Override
    public List<Eipcode> findAllCodeKind() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT CODEKIND FROM eipcode ORDER BY CODEKIND ");
        return getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Eipcode.class));
    }

    /**
     * 查詢 BECODE by codeKind and codeNo
     * @param codekind
     * @param codeno
     * @return
     */
    @Override
    @SkipLog
    public Optional<Eipcode> findByCodeKindCodeNo(String codekind, String codeno) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.CODENO  =:codeno ");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind).addValue("codeno", codeno);

        return getNamedParameterJdbcTemplate()
            .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eipcode.class))
            .stream()
            .findAny();
    }

    @Override
    public int insertEipcode(Eipcode eipcode) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" INSERT INTO ").append(TABLE_NAME)
                .append(" ( codekind, codeno, scodekind, scodeno, ")
                .append(" codename, staff, prcdat, remark )")
                .append(" VALUES ( :codekind, :codeno, :scodekind, ")
                .append(" :scodeno, :codename, :staff, :prcdat, :remark) ")
                .toString(),
                new BeanPropertySqlParameterSource(eipcode));
    }

    @Override
    public int updateEipcode(Eipcode eipcode) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" UPDATE ").append(TABLE_NAME)
                .append(" SET codeno = :afcodeno , ")
                .append(" scodekind = :scodekind, scodeno = :scodeno, ")
                .append(" codename = :codename, staff = :staff, ")
                .append(" prcdat = :prcdat, remark = :remark ")
                .append(" WHERE codekind = :codekind AND codeno = :codeno ")
                .toString(),
                new BeanPropertySqlParameterSource(eipcode));
    }

    @Override
    public int deleteEipcode(Eipcode eipcode) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" DELETE FROM ").append(TABLE_NAME)
                .append(" WHERE codekind = :codekind AND codeno = :codeno ")
                .toString(),
                new BeanPropertySqlParameterSource(eipcode));
    }

    @Override
    public int updateEipcode_batch(Eipcode eipcode) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" begin for code in (  select codekind from eipcode where codekind = :codekind ) loop ")
                .append(" update eipcode t set t.codekind = :afcodekind where codekind = code.codekind; ")
                .append(" end loop; ")
                .append(" end; ")
                .toString(),
                new BeanPropertySqlParameterSource(eipcode));
    }

    @Override
    public List<Eipcode> findLikeCodeKind(String codekind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND like :codekind || '%' order by codeno ");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind);

        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Eipcode.class));
    }

    @Override
    public List<String> findByCodeKindAndList(String codeKind, List<String> list) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODENAME");
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind and codeno in (:list)");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind).addValue("list",list);
        List<Eipcode>eiplist = getNamedParameterJdbcTemplate()
                .query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Eipcode.class));
        return eiplist.stream().map(t->t.getCodename()).collect(Collectors.toList());
    }

    @Override
    public List<Eipcode> findByCodeKindAndExcludeScodekind(String codeKind, String scodekind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT " + ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind and T.SCODEKIND <> :scodekind");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind).addValue("scodekind",scodekind);
        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Eipcode.class));
    }
}
