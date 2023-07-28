package tw.gov.pcc.common.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.dao.MmaplogDao;
import tw.gov.pcc.common.dao.RoitemDao;
import tw.gov.pcc.common.domain.Mmaplog;
import tw.gov.pcc.common.domain.Roitem;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.common.util.StrUtil;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;


import java.util.List;

@Repository
public class RoitemDaoImpl extends BaseDao<Roitem> implements RoitemDao {

    protected static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" t.ITEMID, t.ITEMID,t.DESC_MEMO, t.CNT, t.UNIT, t.APPLY_TYPE, t.APPLY_STAFF, t.APPLY_DATE, t.TEMPMK, t.CRE_USER, t.CRE_DATETIME, ")
                .append(" t.UPD_USER, t.UPD_DATETIME ")
                .toString();
    }
    @Override
    public Roitem selectDataByPrimaryKey(Roitem roitem) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT ");
//        sql.append(ALL_COLUMNS_SQL);
//        sql.append("FROM " + TABLE_NAME + " t WHERE t.APNO = :apno ");
//        List<Roitem> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(roitem), BeanPropertyRowMapper.newInstance(Roitem.class));
//        return CollectionUtils.isEmpty(list) ? null : list.get(0);
        return null;
    }

    /**
     * 根據key刪除資料
     *
     * @param roitem 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Roitem roitem) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " t WHERE t.ITEMID = :ITEMID ",
                new BeanPropertySqlParameterSource(roitem));
    }

    /**
     * 新增一筆資料
     *
     * @param roitem 新增資料
     */
    @Override
    public int insert(Roitem roitem) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " itemid, itemno,item,desc_memo, cnt, unit, apply_type, apply_staff, apply_date, tempmk, cre_user, cre_datetime, " +
                        " upd_user, upd_datetime " +


                        ")" +
                        " VALUES ( " +
                        " :itemid, :itemno,:item,:desc_memo, :cnt, :unit, :apply_type, :apply_staff, :apply_date, :tempmk, :cre_user, :cre_datetime, " +
                        " :upd_user, :upd_datetime " +
                        ")",

                new BeanPropertySqlParameterSource(roitem));
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<Roitem> count(String time) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select DISTINCT ITEMID from roitem r  ");
        sql.append(" WHERE r.apply_date =:time ");
        sql.append(" order BY ITEMID desc ");
        SqlParameterSource params = new MapSqlParameterSource("time",time);
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Roitem.class));
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<Eip08w060Case> quaryItemId(Eip08w060Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select   *  from roitem");
        sql.append(" WHERE apply_type  = :apply_type ");
        sql.append(" AND apply_staff =:apply_staff ");
        sql.append(" and apply_date =:apply_date ");
        sql.append(" order by ITEMID ,ITEMNO ");
        SqlParameterSource params = new MapSqlParameterSource("apply_type",caseData.getApplyTpNm().substring(0,1))
                .addValue("apply_staff", caseData.getUser())
                .addValue("apply_date", caseData.getApplyDate());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip08w060Case.class));
    }

    /**
     * 刪除一筆資料
     *
     * @param roitem 刪除資料
     */
    @Override
    public int delete(Eip08w060Case caseData)  {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                .append(" DELETE  from " ) .append(TABLE_NAME ).append( " WHERE ITEMID=:itemId ").toString(),
                new BeanPropertySqlParameterSource(caseData));
 }
    public int updateRoitem(Eip08w060Case updateDate) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                        .append(" UPDATE ").append(TABLE_NAME)
                        .append(" SET  ")
                        .append(" item=:item,desc_memo=:desc_memo, cnt=:cnt, unit=:unit,  ")
                        .append(" upd_user=:upd_user, upd_datetime=:upd_datetime ")
                        .append(" WHERE itemid=:itemId AND itemno=:itemNo ")
                        .toString(),
                new BeanPropertySqlParameterSource(updateDate));
    }

    @Override
    public List<Eip08w060Case> distinctItemId(Eip08w060Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select    DISTINCT ITEMID  from roitem");
        sql.append(" WHERE apply_type  = :apply_type ");
        sql.append(" AND apply_staff =:apply_staff ");
        sql.append(" and apply_date =:apply_date ");
        sql.append(" order by ITEMID  ");
        SqlParameterSource params = new MapSqlParameterSource("apply_type",caseData.getApplyTpNm().substring(0,1))
                .addValue("apply_staff", caseData.getUser())
                .addValue("apply_date", caseData.getApplyDate());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip08w060Case.class));
    }
}
