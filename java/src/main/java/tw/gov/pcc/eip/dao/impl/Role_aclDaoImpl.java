package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.Role_aclDao;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.domain.Role_acl;

import java.util.List;

/**
 * 單位角色權限資料 DaoImpl
 */
@DaoTable(Role_aclDao.TABLE_NAME)
@Repository
public class Role_aclDaoImpl extends BaseDao<Role_acl> implements Role_aclDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.SYS_ID, t.ROLE_ID, t.DEPT_ID, t.ITEM_ID, t.CREATE_USER_ID, t.CREATE_TIMESTAMP ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param role_acl 條件
     * @return 唯一值
     */
    @Override
    public Role_acl selectDataByPrimaryKey(Role_acl role_acl) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.SYS_ID = :sys_id AND t.ROLE_ID = :role_id AND t.DEPT_ID = :dept_id AND t.ITEM_ID = :item_id ";
        List<Role_acl> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(role_acl), BeanPropertyRowMapper.newInstance(Role_acl.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param sys_id  系統代碼：SYSTEMS.SYS_ID
     * @param role_id 單位角色代碼：ROLES.ROLE_ID
     * @param dept_id 單位代碼：DEPTS.DEPT_ID
     * @param item_id 系統項目代碼：ITEMS.ITEM_ID
     * @return 唯一值
     */
    @Override
    public Role_acl selectByKey(String sys_id, String role_id, String dept_id, String item_id) {
        Role_acl role_acl = new Role_acl();
        role_acl.setSys_id(sys_id);
        role_acl.setRole_id(role_id);
        role_acl.setDept_id(dept_id);
        role_acl.setItem_id(item_id);
        return selectDataByPrimaryKey(role_acl);
    }

    /**
     * 根據key刪除資料
     *
     * @param role_acl 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Role_acl role_acl) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE SYS_ID = :sys_id AND ROLE_ID = :role_id AND DEPT_ID = :dept_id AND ITEM_ID = :item_id ",
                new BeanPropertySqlParameterSource(role_acl));
    }


    /**
     * 根據key更新資料
     *
     * @param role_acl 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Role_acl role_acl) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " CREATE_USER_ID = :create_user_id, CREATE_TIMESTAMP = :create_timestamp" +
                        " WHERE SYS_ID = :sys_id AND ROLE_ID = :role_id AND DEPT_ID = :dept_id AND ITEM_ID = :item_id ",
                new BeanPropertySqlParameterSource(role_acl));
    }

    /**
     * 新增一筆資料
     *
     * @param role_acl 新增資料
     */
    @Override
    public int insert(Role_acl role_acl) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " SYS_ID, ROLE_ID, DEPT_ID, ITEM_ID, CREATE_USER_ID, CREATE_TIMESTAMP" +
                        ")" +
                        " VALUES ( " +
                        " :sys_id, :role_id, :dept_id, :item_id, :create_user_id, :create_timestamp" +
                        ")",
                new BeanPropertySqlParameterSource(role_acl));
    }


    @Override
    public List<Role_acl> findByItemAndExcludeAdmin(String item_id) {
        String sql = "SELECT * FROM ROLE_ACL WHERE ITEM_ID LIKE :item_id + '%' " +
                "AND ROLE_ID != :p_adm_roleid ORDER BY DEPT_ID ";
        SqlParameterSource params = new MapSqlParameterSource("p_adm_roleid", User_rolesDao.SYSTEM_ADMIN_ROLE_ID)
                .addValue("item_id", item_id);
        List<Role_acl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Role_acl.class));
        return list;
    }

    @Override
    public List<Role_acl> findByItem(String item_id) {
        String sql = "SELECT * FROM ROLE_ACL WHERE ITEM_ID LIKE :item_id + '%' " +
                " ORDER BY DEPT_ID ";
        SqlParameterSource params = new MapSqlParameterSource("item_id", item_id);
        List<Role_acl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Role_acl.class));
        return list;
    }
    
    /**
     * 根據key刪除資料
     *
     * @param role_acl 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByRoleid(Role_acl role_id) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE ROLE_ID = :role_id ",
                new BeanPropertySqlParameterSource(role_id));
    }
    
    @Override
    public List<Role_acl> findByRoleId(String role_id) {
        String sql = "SELECT * FROM ROLE_ACL WHERE ROLE_ID = :role_id " +
                " ORDER BY DEPT_ID ";
        SqlParameterSource params = new MapSqlParameterSource("role_id", role_id);
        List<Role_acl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Role_acl.class));
        return list;
    }
}