package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.util.DateUtility;

@DaoTable(ApplyitemDao.TABLE_NAME)
@Repository


public class ApplyitemDaoImpl extends BaseDao<Applyitem> implements ApplyitemDao {

    @Override
	public Applyitem selectByKey(String applyno,String seqno,String itemkind,String itemno) {
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT  * ");
        sql.append(" FROM " + TABLE_NAME + " T WHERE  t.APPLYNO=:applyno and t.SEQNO=:seqno and  ");
        sql.append(" t.ITEMKIND=:itemkind and t.ITEMNO = :itemno ");
        SqlParameterSource params = new MapSqlParameterSource("applyno", applyno);

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }


    @Override
    public int insert(Applyitem applyitem) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                "(" +
                " APPLYNO, SEQNO, ITEMKIND, ITEMNO, APPLY_USER, APPLY_DATE, APPLY_DEPT, APPLY_MEMO, WITHHOLD_CNT_B, WITHHOLD_CNT_A, " +
                " BOOK_CNT_B, BOOK_CNT_A, APPLY_CNT, UNIT, APPROVE_CNT, PROCESS_STATUS, RECONFIRM_MK, RECONFIRM_USER, RECONFIRM_DATE, CRE_USER, " +
                " CRE_DATETIME, UPD_USER, UPD_DATETIME" +
                ")" +
                " VALUES ( " +
                " :applyno, :seqno, :itemkind, :itemno, :apply_user, :apply_date, :apply_dept, :apply_memo, :withhold_cnt_b, :withhold_cnt_a, " +
                " :book_cnt_b, :book_cnt_a, :apply_cnt, :unit, :approve_cnt, :process_status, :reconfirm_mk, :reconfirm_user, :reconfirm_date, :cre_user, " +
                " :cre_datetime, :upd_user, :upd_datetime" +
                ")",
        new BeanPropertySqlParameterSource(applyitem));
    }

    @Override
    public int updateByKey(Applyitem applyitem) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " t SET " +
                " t.APPLYNO = :applyno, t.SEQNO = :seqno, t.ITEMKIND = :itemkind, t.ITEMNO = :itemno, t.APPLY_USER = :apply_user, " +
                " t.APPLY_DATE = :apply_date, t.APPLY_DEPT = :apply_dept, t.APPLY_MEMO = :apply_memo, t.WITHHOLD_CNT_B = :withhold_cnt_b, t.WITHHOLD_CNT_A = :withhold_cnt_a, " +
                " t.BOOK_CNT_B = :book_cnt_b, t.BOOK_CNT_A = :book_cnt_a, t.APPLY_CNT = :apply_cnt, t.UNIT = :unit, t.APPROVE_CNT = :approve_cnt, " +
                " t.PROCESS_STATUS = :process_status, t.RECONFIRM_MK = :reconfirm_mk, t.RECONFIRM_USER = :reconfirm_user, t.RECONFIRM_DATE = :reconfirm_date, t.CRE_USER = :cre_user, " +
                " t.CRE_DATETIME = :cre_datetime, t.UPD_USER = :upd_user, t.UPD_DATETIME = :upd_datetime" +
                " WHERE t.",
        new BeanPropertySqlParameterSource(applyitem));
    }

    @Override
    public int deleteByKey(Applyitem applyitem) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                " t WHERE ",
        new BeanPropertySqlParameterSource(applyitem));
    }


	@Override
	public Applyitem selectDataByPrimaryKey(Applyitem t) {
		return selectByKey(t.getApplyno(),t.getSeqno(),t.getItemkind(),t.getItemno());
	}


	@Override
	public Applyitem getApplyno() {
		Map<String, String> params = new HashMap<>();
		params.put("nowDate", DateUtility.getNowWestDate()+"%");

		StringBuilder sql = new StringBuilder().append(" select applyno from Applyitem where apply_date like :nowDate  ");
		List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Applyitem.class));
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}