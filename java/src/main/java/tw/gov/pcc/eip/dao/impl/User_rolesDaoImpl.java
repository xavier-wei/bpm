package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.common.framework.dao.BaseDao;
import java.util.List;
import java.math.BigDecimal;

/**
 * 使用者可使用的角色資料 DaoImpl
 */
@DaoTable(User_rolesDao.TABLE_NAME)
@Repository
public class User_rolesDaoImpl extends BaseDao<User_roles> implements User_rolesDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" t.USER_ID, t.SYS_ID, t.DEPT_ID, t.ROLE_ID, t.CREATE_USER_ID, t.CREATE_TIMESTAMP ")
                .toString();
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param user_roles 條件
     * @return 唯一值
     */
    @Override
    public User_roles selectDataByPrimaryKey(User_roles user_roles) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id AND t.SYS_ID = :sys_id AND t.DEPT_ID = :dept_id AND t.ROLE_ID = :role_id ");
        List<User_roles> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(user_roles), BeanPropertyRowMapper.newInstance(User_roles.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param user_id 使用者代碼：USERS.USER_ID
     * @param sys_id SYS_ID
     * @param dept_id DEPT_ID
     * @param role_id 單位角色識別碼 單位角色主檔：ROLES.ROLE_ID
     * @return 唯一值
     */
    @Override
    public User_roles selectByKey(String user_id, String sys_id, String dept_id, String role_id) {
        User_roles user_roles = new User_roles();
        user_roles.setUser_id(user_id);
        user_roles.setSys_id(sys_id);
        user_roles.setDept_id(dept_id);
        user_roles.setRole_id(role_id);
        return selectDataByPrimaryKey(user_roles);
    }

    /**
     * 根據key刪除資料
     *
     * @param user_roles 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(User_roles user_roles) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE USER_ID = :user_id AND SYS_ID = :sys_id AND DEPT_ID = :dept_id AND ROLE_ID = :role_id ",
                new BeanPropertySqlParameterSource(user_roles));
    }

    /**
     * 根據key更新資料
     *
     * @param user_roles 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(User_roles user_roles) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " CREATE_USER_ID = :create_user_id, CREATE_TIMESTAMP = :create_timestamp" +
                        " WHERE USER_ID = :user_id AND SYS_ID = :sys_id AND DEPT_ID = :dept_id AND ROLE_ID = :role_id ",
                new BeanPropertySqlParameterSource(user_roles));
    }

    /**
     * 新增一筆資料
     *
     * @param user_roles 新增資料
     */
    @Override
    public int insert(User_roles user_roles) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " USER_ID, SYS_ID, DEPT_ID, ROLE_ID, CREATE_USER_ID, CREATE_TIMESTAMP" +
                        ")" +
                        " VALUES ( " +
                        " :user_id, :sys_id, :dept_id, :role_id, :create_user_id, :create_timestamp" +
                        ")",
                new BeanPropertySqlParameterSource(user_roles));
    }

    /**
     * 根據role_id選取資料
     *
     * @param role_id 條件
     * @return 
     */
    @Override
    public List<User_roles> selectDataByRoleId(String role_id) {
    	User_roles userroles = new User_roles();
    	userroles.setRole_id(role_id);
    	
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t WHERE t.ROLE_ID = :role_id ");
        List<User_roles> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(userroles), BeanPropertyRowMapper.newInstance(User_roles.class));
        return list;
    }
    
    /**
     * 根據user_id選取資料
     *
     * @param role_id 條件
     * @return 
     */
    @Override
    public List<User_roles> selectDataByUserId(String user_id) {
    	User_roles userroles = new User_roles();
    	userroles.setUser_id(user_id);
    	
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id ");
        List<User_roles> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(userroles), BeanPropertyRowMapper.newInstance(User_roles.class));
        return list;
    }
}