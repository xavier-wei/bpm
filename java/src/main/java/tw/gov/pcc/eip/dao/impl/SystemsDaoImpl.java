package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.SystemsDao;
import tw.gov.pcc.eip.domain.Systems;

import java.util.List;

/**
 * 應用系統資料 DaoImpl
 */
@DaoTable(SystemsDao.TABLE_NAME)
@Repository
public class SystemsDaoImpl extends BaseDao<Systems> implements SystemsDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.SYS_ID, t.SYS_NAME, t.URL, t.ITEM_ID, t.SORT_ORDER, t.CREATE_USER_ID, t.CREATE_TIMESTAMP, t.MODIFY_USER_ID, t.MODIFY_TIMESTAMP ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param systems 條件
     * @return 唯一值
     */
    @Override
    public Systems selectDataByPrimaryKey(Systems systems) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.SYS_ID = :sys_id ";
        List<Systems> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(systems), BeanPropertyRowMapper.newInstance(Systems.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param sys_id 系統代號
     * @return 唯一值
     */
    @Override
    public Systems selectByKey(String sys_id) {
        Systems systems = new Systems();
        systems.setSys_id(sys_id);
        return selectDataByPrimaryKey(systems);
    }

    /**
     * 根據key刪除資料
     *
     * @param systems 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Systems systems) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE SYS_ID = :sys_id ",
                new BeanPropertySqlParameterSource(systems));
    }

    /**
     * 根據key更新資料
     *
     * @param systems 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Systems systems) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " SYS_NAME = :sys_name, URL = :url, ITEM_ID = :item_id, SORT_ORDER = :sort_order, CREATE_USER_ID = :create_user_id, " +
                        " CREATE_TIMESTAMP = :create_timestamp, MODIFY_USER_ID = :modify_user_id, MODIFY_TIMESTAMP = :modify_timestamp" +
                        " WHERE SYS_ID = :sys_id ",
                new BeanPropertySqlParameterSource(systems));
    }

    /**
     * 新增一筆資料
     *
     * @param systems 新增資料
     */
    @Override
    public int insert(Systems systems) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " SYS_ID, SYS_NAME, URL, ITEM_ID, SORT_ORDER, CREATE_USER_ID, CREATE_TIMESTAMP, MODIFY_USER_ID, MODIFY_TIMESTAMP" +
                        ")" +
                        " VALUES ( " +
                        " :sys_id, :sys_name, :url, :item_id, :sort_order, :create_user_id, :create_timestamp, :modify_user_id, :modify_timestamp" +
                        ")",
                new BeanPropertySqlParameterSource(systems));
    }


    @Override
    public List<Systems> selectOwnSystem(String user_id) {
        String sql = "SELECT *\n" +
                "FROM (\n" +
                "  SELECT sys_id , sys_name , sort_order\n" +
                "  FROM systems\n" +
                "  WHERE EXISTS (\n" +
                "    SELECT '' FROM user_roles WHERE user_id = :userId AND role_id = 'SYSADMIN'\n" +
                "  )\n" +
                ") t\n" +
                "ORDER BY t.sort_order\n";

        SqlParameterSource params = new MapSqlParameterSource("userId", user_id);
        List<Systems> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Systems.class));
        return list;
    }
    
    @Override
    public List<Systems> selectSystemList(String sys_id) {
        String sql = "SELECT " +
                     ALL_COLUMNS_SQL +
                     "FROM " + TABLE_NAME + " t WHERE t.SYS_ID = ISNULL(:sys_id,t.SYS_ID) ";

        SqlParameterSource params = new MapSqlParameterSource("sys_id", sys_id);
        List<Systems> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Systems.class));
        return list;
    }
}