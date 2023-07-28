package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.domain.Items;

import java.util.List;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(ItemsDao.TABLE_NAME)
@Repository
public class ItemsDaoImpl extends BaseDao<Items> implements ItemsDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.ITEM_ID, t.SORT_ORDER, t.ITEM_ID_P, t.ITEM_NAME, t.HYPERLINK, t.SUB_LINK, t.DISABLE, " +
                " t.CREATE_USER_ID, t.CREATE_TIMESTAMP, t.MODIFY_USER_ID, t.MODIFY_TIMESTAMP ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param items 條件
     * @return 唯一值
     */
    @Override
    public Items selectDataByPrimaryKey(Items items) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.ITEM_ID = :item_id ";
        List<Items> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(items), BeanPropertyRowMapper.newInstance(Items.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param item_id 系統項目代碼
     * @return 唯一值
     */
    @Override
    public Items selectByKey(String item_id) {
        Items items = new Items();
        items.setItem_id(item_id);
        return selectDataByPrimaryKey(items);
    }

    /**
     * 根據key刪除資料
     *
     * @param items 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Items items) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE ITEM_ID = :item_id ",
                new BeanPropertySqlParameterSource(items));
    }

    /**
     * 根據key更新資料
     *
     * @param items 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Items items) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " SORT_ORDER = :sort_order, ITEM_ID_P = :item_id_p, ITEM_NAME = :item_name, HYPERLINK = :hyperlink, SUB_LINK = :sub_link, " +
                        " DISABLE = :disable, " +
                        " CREATE_USER_ID = :create_user_id, CREATE_TIMESTAMP = :create_timestamp, MODIFY_USER_ID = :modify_user_id, MODIFY_TIMESTAMP = :modify_timestamp" +
                        " WHERE ITEM_ID = :item_id ",
                new BeanPropertySqlParameterSource(items));
    }

    /**
     * 新增一筆資料
     *
     * @param items 新增資料
     */
    @Override
    public int insert(Items items) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " ITEM_ID, SORT_ORDER, ITEM_ID_P, ITEM_NAME, HYPERLINK, SUB_LINK, DISABLE, " +
                        " CREATE_USER_ID, CREATE_TIMESTAMP, MODIFY_USER_ID, MODIFY_TIMESTAMP" +
                        ")" +
                        " VALUES ( " +
                        " :item_id, :sort_order, :item_id_p, :item_name, :hyperlink, :sub_link, :disable, " +
                        " :create_user_id, :create_timestamp, :modify_user_id, :modify_timestamp" +
                        ")",
                new BeanPropertySqlParameterSource(items));
    }


    @Override
    public List<Items> findItemAndChild(String item_id) {
        String sql = "SELECT * FROM ITEMS WHERE ITEM_ID LIKE :item_id + '%' ORDER BY ITEM_ID DESC";
        SqlParameterSource params = new MapSqlParameterSource("item_id", item_id);
        List<Items> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Items.class));
        return list;
    }


}