package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.View_oup_unitDao;
import tw.gov.pcc.eip.domain.View_oup_unit;

import java.util.HashMap;
import java.util.List;

/**
 * VIEW_OUP_UNIT DaoImpl
 */
@DaoTable(View_oup_unitDao.TABLE_NAME)
@Repository
public class View_oup_unitDaoImpl extends BaseDao<View_oup_unit> implements View_oup_unitDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.ORG_ID, t.UNIT_ID, t.FUNIT_ID, t.NAME, t.SEQ, t.UHEAD_POS_ID, t.PLEVEL, t.UNIT_TYPE, t.IS_WHIP ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param view_oup_unit 條件
     * @return 唯一值
     */
    @Override
    public View_oup_unit selectDataByPrimaryKey(View_oup_unit view_oup_unit) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.ORG_ID = :org_id AND t.UNIT_ID = :unit_id ";
        List<View_oup_unit> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(view_oup_unit), BeanPropertyRowMapper.newInstance(View_oup_unit.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }


    @Override
    public void truncateAll() {
        getNamedParameterJdbcTemplate().update("TRUNCATE TABLE " + TABLE_NAME, new HashMap<>());
    }

    /**
     * 新增一筆資料
     *
     * @param view_oup_unit 新增資料
     */
    @Override
    public int insert(View_oup_unit view_oup_unit) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " ORG_ID, UNIT_ID, FUNIT_ID, NAME, SEQ, UHEAD_POS_ID, PLEVEL, UNIT_TYPE, IS_WHIP" +
                        ")" +
                        " VALUES ( " +
                        " :org_id, :unit_id, :funit_id, :name, :seq, :uhead_pos_id, :plevel, :unit_type, :is_whip" +
                        ")",
                new BeanPropertySqlParameterSource(view_oup_unit));
    }
}