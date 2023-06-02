
package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.eip.dao.CodeDao;
import tw.gov.pcc.eip.domain.Code;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;

/**
 * Code 說明 DAO
 *
 * @author jerrywu
 *
 */
@DaoTable(CodeDao.TABLE_NAME)
@Repository
public class CodeDaoImpl extends BaseDao<Code> implements CodeDao {

	private static final String ALL_COLUMNS_SQL;

	static {
		ALL_COLUMNS_SQL = new StringBuilder()
			.append(" T.CODEKIND as codekind,T.CODENO as codeno,T.SCODEKIND as scodekind,T.SCODENO as scodeno,T.CODENAME as codename,T.STAFF as staff,")
			.append(" T.PRCDAT as prcdat,T.REMARK as remark")
			.toString();
	}

	@Override
	@SkipLog
	public Code selectDataByPrimaryKey(Code code) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(ALL_COLUMNS_SQL);
		sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND= :codekind and T.CODENO = :codeno");

		SqlParameterSource params = new MapSqlParameterSource("codekind", code.getCodekind()).addValue("codeno",
				code.getCodeno());

		List<Code> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Code.class));

		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	@SkipLog
	public List<Code> findByCodeKind(String codeKind) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(ALL_COLUMNS_SQL);
		sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");

		SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

		return getNamedParameterJdbcTemplate()
				.query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Code.class));
	}

    @Override
    @SkipLog
    public List<Code> findByCodeKindOrderByScodeno(String codeKind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind order by T.SCODENO");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Code.class));
    }

    @Override
    public List<Code> getPaperInfo(List<String> reportList) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT scodekind AS paperType,");
        sql.append(" codename AS notice, codeno AS reportNo");
        sql.append(" FROM code");
        sql.append(" WHERE codekind = 'DUEINPARAM'");
        sql.append(" AND codeno IN (:reportList) ");
        SqlParameterSource params = new MapSqlParameterSource("reportList", reportList);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Code.class));
    }

    /**
     * 轉帳約定檔查詢約定檔狀態對應代號名稱用
     */
    @Override
    @SkipLog
    public Optional<Code> findByCodeKindForProstat(String codeKind) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.PROCSTAT IN ('0', '1') ");
        sql.append(" AND CODE.CODEKIND = 'PROCSTAT' ");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind);

        return getNamedParameterJdbcTemplate()
        	.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Code.class))
        	.stream()
        	.findAny();
    }

	@Override
	@SkipLog
	public List<Code> findByCodeKindScodeno(String codeKind, String scodeno) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.SCODENO  =:scodeno ");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codeKind).addValue("scodeno", scodeno);

        return getNamedParameterJdbcTemplate()
        		.query(sql.toString(), params,BeanPropertyRowMapper.newInstance(Code.class));
	}

    /**
     * 查詢 CODE by codeKind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    @Override
    @SkipLog
    public List<Code> findByCodekindOrderByCodeno(String codekind) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" ORDER BY T.codeno");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Code.class));
    }

    /**
     * 查詢 CODE by codeKind, scodekind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    @Override
    @SkipLog
    public List<Code> findByCodekindScodekindOrderByCodeno(String codekind, String scodekind) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.SCODEKIND = :scodekind ");
        sql.append(" ORDER BY T.codeno");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind).addValue("scodekind", scodekind);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Code.class));
    }

    @Override
    public Optional<Code> findByCodeKindPresent(String afcodekind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODEKIND ");
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :afcodekind ");

        SqlParameterSource params = new MapSqlParameterSource("afcodekind", afcodekind);
        return getNamedParameterJdbcTemplate()
            .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Code.class))
            .stream()
            .findAny();
    }
    @Override
    public List<Code> findAllCodeKind() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT CODEKIND FROM code ORDER BY CODEKIND ");
        return getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Code.class));
    }

    /**
     * 查詢 CODE by codeKind and codeNo
     * @param codekind
     * @param codeno
     * @return
     */
    @Override
    @SkipLog
    public Optional<Code> findByCodeKindCodeNo(String codekind, String codeno) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.CODENO  =:codeno ");

        SqlParameterSource params = new MapSqlParameterSource("codekind", codekind).addValue("codeno", codeno);

        return getNamedParameterJdbcTemplate()
            .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Code.class))
            .stream()
            .findAny();
    }

    @Override
    @SkipLog
    public Optional<Code> findReceiver(String codekind, String codeno, String scodekind, String scodeno) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T where T.CODEKIND = :codekind ");
        sql.append(" AND T.CODENO like :codeno ");
        sql.append(" AND T.SCODEKIND = :scodekind ");
        sql.append(" AND T.SCODENO = UPPER(:scodeno) ");

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("codekind", codekind)
            .addValue("codeno", codeno + "%")
            .addValue("scodekind", scodekind)
            .addValue("scodeno", scodeno);

        return getNamedParameterJdbcTemplate()
            .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Code.class))
            .stream()
            .findAny();
    }

    @Override
    public int insertCode(Code code) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" INSERT INTO ").append(TABLE_NAME)
                .append(" ( codekind, codeno, scodekind, scodeno, ")
                .append(" codename, staff, prcdat, remark )")
                .append(" VALUES ( :codekind, :codeno, :scodekind, ")
                .append(" :scodeno, :codename, :staff, :prcdat, :remark) ")
                .toString(),
                new BeanPropertySqlParameterSource(code));
    }

    @Override
    public int updateCode(Code code) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" UPDATE ").append(TABLE_NAME)
                .append(" SET codeno = :afcodeno , ")
                .append(" scodekind = :scodekind, scodeno = :scodeno, ")
                .append(" codename = :codename, staff = :staff, ")
                .append(" prcdat = :prcdat, remark = :remark ")
                .append(" WHERE codekind = :codekind AND codeno = :codeno ")
                .toString(),
                new BeanPropertySqlParameterSource(code));
    }

    @Override
    public int deleteCode(Code code) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" DELETE FROM ").append(TABLE_NAME)
                .append(" WHERE codekind = :codekind AND codeno = :codeno ")
                .toString(),
                new BeanPropertySqlParameterSource(code));
    }

    @Override
    public int updateCode_batch(Code code) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" begin for code in (  select codekind from code where codekind = :codekind ) loop ")
                .append(" update code t set t.codekind = :afcodekind where codekind = code.codekind; ")
                .append(" end loop; ")
                .append(" end; ")
                .toString(),
                new BeanPropertySqlParameterSource(code));
    }
    /**
     * 查詢批次執行狀態
     */
    @Override
    @SkipLog
    public String getLocks() {
        StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" PKG_BEICUTIL.FN_GETLOCKALL() locks ");
            sql.append(" FROM dual");
            return getNamedParameterJdbcTemplate()
                    .queryForObject(sql.toString(), new HashMap<String,String>(), String.class);

    }

    @Override
    public Optional<Code> getNotice() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT codename as notice FROM code WHERE codekind IN ");
        sql.append(" ('DUEINNOTICE')");
        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), BeanPropertyRowMapper.newInstance(Code.class))
                .stream()
                .findAny();
    }
    
	@Override
    @SkipLog
	public String getBatchLocksMesg() {

		StringBuilder sql = new StringBuilder();
		sql.append(
				" select CODENAME from code WHERE codekind LIKE 'BEIC_LOCK_' AND codeno = 'Y' ORDER BY codekind desc ");

		List<Code> list = getNamedParameterJdbcTemplate().query(sql.toString(),
				BeanPropertyRowMapper.newInstance(Code.class));

		return list.size() > 0 ? StringUtils.trimToEmpty(list.get(0).getCodename()) : "";

	}
}
