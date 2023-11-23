package tw.gov.pcc.eip.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.domain.Eipcode;
import tw.gov.pcc.eip.annotation.DaoTable;
import tw.gov.pcc.eip.annotation.SkipLog;
import tw.gov.pcc.eip.dao.BaseDao;
import tw.gov.pcc.eip.dao.EipcodeDao;

import java.util.List;

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
    public List<Eipcode> findByCodeKindOrderByScodeno(String codeKind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind order by T.SCODENO");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

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

}
