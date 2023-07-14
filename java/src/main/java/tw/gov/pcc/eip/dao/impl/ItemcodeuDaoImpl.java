package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Itemcodeu;

/**
 * ITEMCODEU DaoImpl
 */
@DaoTable(ItemcodeuDao.TABLE_NAME)
@Repository
public class ItemcodeuDaoImpl extends BaseDao<Itemcodeu> implements ItemcodeuDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" ITEMKIND, ITEMNO, ITEMNAME, U_DATE, U_TIME, U_USER, COCD, PURCHASE_CNT, RETURN_CNT, FINAL_CNT, ")
                .append(" WITHHOLD_CNT, BOOK_CNT, CRE_USER, CRE_DATETIME, UPD_USER, UPD_DATETIME ")
                .toString();
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param itemcodeu 條件
     * @return 唯一值
     */
    @Override
    public Itemcodeu selectDataByPrimaryKey(Itemcodeu itemcodeu) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM " + TABLE_NAME + " t WHERE ITEMKIND = :itemkind AND ITEMNO = :itemno AND U_DATE = :u_date AND U_TIME = :u_time AND U_USER = :u_user ");
        List<Itemcodeu> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(itemcodeu), BeanPropertyRowMapper.newInstance(Itemcodeu.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param itemkind ITEMKIND
     * @param itemno ITEMNO
     * @param u_date U_DATE
     * @param u_time U_TIME
     * @param u_user U_USER
     * @return 唯一值
     */
    @Override
    public Itemcodeu selectByKey(String itemkind, String itemno, String u_date, String u_time, String u_user) {
        Itemcodeu itemcodeu = new Itemcodeu();
        itemcodeu.setItemkind(itemkind);
        itemcodeu.setItemno(itemno);
        itemcodeu.setU_date(u_date);
        itemcodeu.setU_time(u_time);
        itemcodeu.setU_user(u_user);
        return selectDataByPrimaryKey(itemcodeu);
    }

    /**
     * 根據key刪除資料
     *
     * @param itemcodeu 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Itemcodeu itemcodeu) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " t WHERE ITEMKIND = :itemkind AND ITEMNO = :itemno AND U_DATE = :u_date AND U_TIME = :u_time AND U_USER = :u_user ",
                new BeanPropertySqlParameterSource(itemcodeu));
    }

    /**
     * 根據key更新資料
     *
     * @param itemcodeu 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Itemcodeu itemcodeu) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + "  SET " +
                        " ITEMNAME = :itemname, COCD = :cocd, PURCHASE_CNT = :purchase_cnt, RETURN_CNT = :return_cnt, FINAL_CNT = :final_cnt, " +
                        " WITHHOLD_CNT = :withhold_cnt, BOOK_CNT = :book_cnt, CRE_USER = :cre_user, CRE_DATETIME = :cre_datetime, UPD_USER = :upd_user, " +
                        " UPD_DATETIME = :upd_datetime" +
                        " WHERE ITEMKIND = :itemkind AND ITEMNO = :itemno AND U_DATE = :u_date AND U_TIME = :u_time AND U_USER = :u_user ",
                new BeanPropertySqlParameterSource(itemcodeu));
    }

    /**
     * 新增一筆資料
     *
     * @param itemcodeu 新增資料
     */
    @Override
    public int insert(Itemcodeu itemcodeu) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " ITEMKIND, ITEMNO, ITEMNAME, U_DATE, U_TIME, U_USER, COCD, PURCHASE_CNT, RETURN_CNT, FINAL_CNT, " +
                        " WITHHOLD_CNT, BOOK_CNT, CRE_USER, CRE_DATETIME, UPD_USER, UPD_DATETIME" +
                        ")" +
                        " VALUES ( " +
                        " :itemkind, :itemno, :itemname, :u_date, :u_time, :u_user, :cocd, :purchase_cnt, :return_cnt, :final_cnt, " +
                        " :withhold_cnt, :book_cnt, :cre_user, :cre_datetime, :upd_user, :upd_datetime" +
                        ")",
                new BeanPropertySqlParameterSource(itemcodeu));
    }
}