package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
        sql.append(" FROM " + TABLE_NAME + " T WHERE  APPLYNO=:applyno and SEQNO=:seqno and  ");
        sql.append(" ITEMKIND=:itemkind and ITEMNO = :itemno ");
        SqlParameterSource params = new MapSqlParameterSource("applyno", applyno)
        		.addValue("seqno", seqno).addValue("itemkind", itemkind).addValue("itemno", itemno);

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
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + "  SET " +
                " APPLYNO = :applyno, SEQNO = :seqno, ITEMKIND = :itemkind, ITEMNO = :itemno, APPLY_USER = :apply_user, " +
                " APPLY_DATE = :apply_date, APPLY_DEPT = :apply_dept, APPLY_MEMO = :apply_memo, WITHHOLD_CNT_B = :withhold_cnt_b, WITHHOLD_CNT_A = :withhold_cnt_a, " +
                " BOOK_CNT_B = :book_cnt_b, BOOK_CNT_A = :book_cnt_a, APPLY_CNT = :apply_cnt, UNIT = :unit, APPROVE_CNT = :approve_cnt, " +
                " PROCESS_STATUS = :process_status, RECONFIRM_MK = :reconfirm_mk, RECONFIRM_USER = :reconfirm_user, RECONFIRM_DATE = :reconfirm_date, CRE_USER = :cre_user, " +
                " CRE_DATETIME = :cre_datetime, UPD_USER = :upd_user, UPD_DATETIME = :upd_datetime" +
                " WHERE APPLYNO = :applyno and SEQNO = :seqno",
        new BeanPropertySqlParameterSource(applyitem));
    }

    @Override
    public int deleteByKey(Applyitem applyitem) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                "  WHERE applyno= :applyno ",
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

		StringBuilder sql = new StringBuilder().append(" select applyno from Applyitem where apply_date like :nowDate order by applyno desc ");
		List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Applyitem.class));
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}


	@Override
	public List<Applyitem> selectByApplyUserAndApply_deptAndapplyDate(String apply_user,String apply_dept,String applydateStart,String applydateEnd,String itemnoStr) {
        StringBuilder sql=new StringBuilder();
        Map<String, String> params = new HashMap<>();

        sql.append(" SELECT   distinct applyno ,apply_date,apply_memo,process_status,reconfirm_user ");
        sql.append(" FROM " + TABLE_NAME + " WHERE    ");
        sql.append(" apply_user = :apply_user ");
        
        if(StringUtils.isNotEmpty(apply_dept)) {
        	sql.append(" and  apply_dept =  :apply_dept ");
        }
        
        if(StringUtils.isNotEmpty(applydateStart) && StringUtils.isNotEmpty(applydateEnd)) {
        	sql.append(" and  apply_date between :applydateStart  and :applydateEnd ");
            params.put("applydateStart", applydateStart);
            params.put("applydateEnd", applydateEnd);
        } else if(StringUtils.isNotEmpty(applydateStart) && StringUtils.isEmpty(applydateEnd)){
        	sql.append(" and  apply_date between :applydateStart  and :applydateEnd ");
            params.put("applydateStart", applydateStart);
            params.put("applydateEnd", DateUtility.getNowWestDate());
        }
        
        if(StringUtils.isNotEmpty(itemnoStr)) {
        	sql.append("and itemno in (select itemno from APPLYITEM a WHERE a.ITEMNO in (select itemno from ITEMCODE where itemname like :itemnoStr)) ");
        	
        	params.put("itemnoStr", "%"+itemnoStr.trim()+"%");
        }
        
        sql.append(" order by applyno desc ");
        
        
        params.put("apply_user", apply_user);
        params.put("apply_dept", apply_dept );

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}


	@Override
	public List<Applyitem> selectByApplyno(String applyno) {
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT  * ");
        sql.append(" FROM " + TABLE_NAME + " WHERE    ");
        sql.append(" applyno = :applyno ");
        
        SqlParameterSource params = new MapSqlParameterSource("applyno", applyno);

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}


	@Override
	public List<Applyitem> selectByApply_dateAndProcess_status(String apply_dateStart, String apply_dateEnd,String process_status,List<String> apply_dept) {
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT  * ");
        sql.append(" FROM " + TABLE_NAME + " WHERE    ");
        sql.append(" apply_date >= :apply_dateStart ");
        sql.append(" And apply_date <= :apply_dateEnd ");
        sql.append(" And process_status= :process_status ");
        if(CollectionUtils.isNotEmpty(apply_dept)) {        	
        	sql.append(" And apply_dept in (:apply_dept) ");
        }
        sql.append(" And seqno = '1' order by applyno");
        
        SqlParameterSource params = new MapSqlParameterSource("apply_dateStart", DateUtility.changeDateType(apply_dateStart))
        		.addValue("apply_dateEnd", DateUtility.changeDateType(apply_dateEnd))
        			.addValue("apply_dept", apply_dept)
        		.addValue("process_status", process_status);

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}
	

	@Override
	public List<Applyitem> selectReconfirm_mkNData(List<String>applynos) {
        StringBuilder sql=new StringBuilder();
        sql.append(" Select itemkind, itemno, sum(withhold_cnt_a) withhold_cnt_a from " + TABLE_NAME  );
        sql.append(" Where applyno in (:applynos) ");
        sql.append(" AND reconfirm_mk='N' ");
        sql.append(" Group by itemkind, itemno ");
        
        Map<String, List<String>> params = new HashMap<>();
        params.put("applynos", applynos);

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}


	@Override
	public List<Applyitem> selectApplyItemReportByUnit(String applyYearMonth) {
        StringBuilder sql=new StringBuilder();
        
        sql.append(" Select itemkind, itemno,(select top(1)dept_name from DEPTS  where dept_id = a.apply_dept ) as apply_dept , ");
        sql.append(" (select itemno+'-'+itemname from itemcode where itemkind='MAIN' AND  substring(a.itemno,1,1)=itemno) as itemkind_nm, ");
        sql.append(" (select itemno+'-'+itemname from itemcode where itemkind=a.itemkind And itemno=a.itemno) as itemno_nm, ");
        sql.append(" sum(approve_cnt) approve_cnt ");
        sql.append(" from applyitem a ");
        sql.append(" Where substring(apply_date,1,6) like :applyYearMonth and process_status = '3' ");
        sql.append(" Group by apply_dept, itemkind, itemno ");
        sql.append(" Order by apply_dept, itemkind, itemno ");
        
        SqlParameterSource params = new MapSqlParameterSource("applyYearMonth", DateUtility.changeChineseYearMonthType(applyYearMonth) +"%");

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}


	@Override
	public List<Applyitem> selectApplyItemReportByItem(String applyYearMonth) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" Select itemkind, itemno, sum(approve_cnt) approve_cnt, ");
        sql.append(" (select itemno+'-'+itemname from itemcode where itemkind='MAIN' AND  substring(a.itemno,1,1)=itemno) as itemkind_nm, ");
        sql.append(" (select itemno+'-'+itemname from itemcode where itemkind=a.itemkind And itemno=a.itemno) as itemno_nm ");
        sql.append("  from applyitem a ");
        sql.append(" Where substring(apply_date,1,6) like :applyYearMonth and process_status = '3' ");
        sql.append(" Group by itemkind, itemno ");
        sql.append(" ORDER BY ITEMKIND, ITEMNO ");
        
        SqlParameterSource params = new MapSqlParameterSource("applyYearMonth", DateUtility.changeChineseYearMonthType(applyYearMonth)+"%");

        List<Applyitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Applyitem.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}

    @Override
    public String getApplynoSeq() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT NEXT VALUE FOR EIP_APPLYNO ");
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), new HashMap<String, String>(),
                String.class);
    }


	@Override
	public void updateSequence() {
		String sql = " ALTER SEQUENCE EIP_APPLYNO RESTART  WITH 1 ";
		Map<String, Object> param = new HashMap<String, Object>();
		getNamedParameterJdbcTemplate().update(sql, param);
	}
}