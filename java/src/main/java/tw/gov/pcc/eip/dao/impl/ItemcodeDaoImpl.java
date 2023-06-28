package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;


import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.domain.Itemcode;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(ItemsDao.TABLE_NAME)
@Repository
public class ItemcodeDaoImpl extends BaseDao<Itemcode> implements ItemcodeDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.ITEMKIND, t.ITEMNO, t.ITEMNAME, t.PURCHASE_CNT, t.RETURN_CNT, t.FINAL_CNT, t.WITHHOLD_CNT, "
        		          + " t.BOOK_CNT, t.CRE_USER, t.CRE_DATETIME, t.UPD_USER, t.UPD_DATETIME ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param items 條件
     * @return 唯一值
     */
    @Override
    public Itemcode selectDataByPrimaryKey(Itemcode itemcode) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.ITEMKIND = :item_id AND t.ITEMNO = :itemno ";
        List<Itemcode> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(itemcode), BeanPropertyRowMapper.newInstance(Itemcode.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }



}