package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.Pwc_tb_tableau_user_infoDao;
import tw.gov.pcc.eip.domain.Pwc_tb_tableau_user_info;

import java.util.HashMap;
import java.util.List;

/**
 * PWC_TB_TABLEAU_USER_INFO DaoImpl
 */
@DaoTable(Pwc_tb_tableau_user_infoDao.TABLE_NAME)
@Repository
public class Pwc_tb_tableau_user_infoDaoImpl extends BaseDao<Pwc_tb_tableau_user_info> implements Pwc_tb_tableau_user_infoDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.USER_ID, t.DEPARTMENT_ID, t.DASHBOARD_FIG_ID, t.SORT_ORDER, t.CREATE_TIME, t.UPDATE_TIME ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param pwc_tb_tableau_user_info 條件
     * @return 唯一值
     */
    @Override
    public Pwc_tb_tableau_user_info selectDataByPrimaryKey(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id AND t.DASHBOARD_FIG_ID = :dashboard_fig_id ";
        List<Pwc_tb_tableau_user_info> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(pwc_tb_tableau_user_info), BeanPropertyRowMapper.newInstance(Pwc_tb_tableau_user_info.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param user_id          USER_ID
     * @param dashboard_fig_id DASHBOARD_FIG_ID
     * @return 唯一值
     */
    @Override
    public Pwc_tb_tableau_user_info selectByKey(String user_id, String dashboard_fig_id) {
        Pwc_tb_tableau_user_info pwc_tb_tableau_user_info = new Pwc_tb_tableau_user_info();
        pwc_tb_tableau_user_info.setUser_id(user_id);
        pwc_tb_tableau_user_info.setDashboard_fig_id(dashboard_fig_id);
        return selectDataByPrimaryKey(pwc_tb_tableau_user_info);
    }

    /**
     * 根據key刪除資料
     *
     * @param pwc_tb_tableau_user_info 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE USER_ID = :user_id AND DASHBOARD_FIG_ID = :dashboard_fig_id ",
                new BeanPropertySqlParameterSource(pwc_tb_tableau_user_info));
    }

    @Override
    public List<Pwc_tb_tableau_user_info> findUnauthoriedList() {
        String sql = "SELECT * " +
                "FROM PWC_TB_TABLEAU_USER_INFO p " +
                "WHERE NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM USER_ROLES u " +
                "    INNER JOIN role_acl o ON u.ROLE_ID = o.ROLE_ID " +
                "    INNER JOIN ITEMS i ON o.ITEM_ID = i.ITEM_ID " +
                "    WHERE p.USER_ID = u.USER_ID " +
                "    AND CHARINDEX('/', REVERSE(i.HYPERLINK)) > 0 " +
                "    AND p.DASHBOARD_FIG_ID = RIGHT(i.HYPERLINK, CHARINDEX('/', REVERSE(i.HYPERLINK)) - 1) " +
                "    AND (i.DISABLE IS NULL OR i.DISABLE <> 'Y') " +
                ")";
        return getNamedParameterJdbcTemplate().query(sql, new HashMap<>(), BeanPropertyRowMapper.newInstance(Pwc_tb_tableau_user_info.class));
    }

    @Override
    public List<Pwc_tb_tableau_user_info> findByUserId(String userId) {
        String sql = "SELECT * " +
                "FROM PWC_TB_TABLEAU_USER_INFO p " +
                "WHERE USER_ID = :user_id ORDER BY SORT_ORDER";
        SqlParameterSource params = new MapSqlParameterSource("user_id", userId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Pwc_tb_tableau_user_info.class));
    }

    /**
     * 根據key更新資料
     *
     * @param pwc_tb_tableau_user_info 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " DEPARTMENT_ID = :department_id, SORT_ORDER = :sort_order, CREATE_TIME = :create_time, UPDATE_TIME = :update_time" +
                        " WHERE USER_ID = :user_id AND DASHBOARD_FIG_ID = :dashboard_fig_id ",
                new BeanPropertySqlParameterSource(pwc_tb_tableau_user_info));
    }

    /**
     * 新增一筆資料
     *
     * @param pwc_tb_tableau_user_info 新增資料
     */
    @Override
    public int insert(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " USER_ID, DEPARTMENT_ID, DASHBOARD_FIG_ID, SORT_ORDER, CREATE_TIME, UPDATE_TIME" +
                        ")" +
                        " VALUES ( " +
                        " :user_id, :department_id, :dashboard_fig_id, :sort_order, :create_time, :update_time" +
                        ")",
                new BeanPropertySqlParameterSource(pwc_tb_tableau_user_info));
    }
}