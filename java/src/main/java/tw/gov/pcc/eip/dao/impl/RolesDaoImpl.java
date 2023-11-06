package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.Role_aclDao;
import tw.gov.pcc.eip.dao.RolesDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Role_acl;
import tw.gov.pcc.eip.domain.Roles;
import tw.gov.pcc.eip.domain.Users;

/**
 * 單位角色權限資料 DaoImpl
 */
@DaoTable(Role_aclDao.TABLE_NAME)
@Repository
public class RolesDaoImpl extends BaseDao<Roles> implements RolesDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.SYS_ID, t.DEPT_ID, t.ROLE_ID, t.ROLE_DESC, t.CREATE_USER_ID, t.CREATE_TIMESTAMP, t.MODIFY_USER_ID, t.MODIFY_TIMESTAMP ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param role_acl 條件
     * @return 唯一值
     */
    @Override
    public Roles selectDataByPrimaryKey(Roles roles) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.SYS_ID = :sys_id  AND t.DEPT_ID = :dept_id AND t.ROLE_ID = :role_id ";
        List<Roles> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(roles), BeanPropertyRowMapper.newInstance(Roles.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @return 唯一值
     */
    @Override
    public Roles selectByKey(String sys_id, String dept_id, String role_id) {
        Roles roles = new Roles();
        roles.setSys_id(sys_id);
        roles.setRole_id(role_id);
        roles.setDept_id(dept_id);
        return selectDataByPrimaryKey(roles);
    }
    
    /**
     * 根據key選取資料
     *
     * @return 唯一值
     */
    @Override
    public List<Roles> selectDataList(String role_id) {
        Roles roles = new Roles();
        roles.setSys_id("EI");
        if(StringUtils.isNotBlank(role_id)) {
        	roles.setRole_id(role_id);
        }
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.SYS_ID = :sys_id AND t.ROLE_ID = ISNULL(:role_id ,t.ROLE_ID) ";
        List<Roles> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(roles), BeanPropertyRowMapper.newInstance(Roles.class));
        return list;
    }

    /**
     * 新增一筆資料
     *
     * @param roles 新增資料
     */
    @Override
    public int insert(Roles roles) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " SYS_ID, DEPT_ID, ROLE_ID, ROLE_DESC, CREATE_USER_ID, CREATE_TIMESTAMP, MODIFY_USER_ID, MODIFY_TIMESTAMP " +
                        ")" +
                        " VALUES ( " +
                        " :sys_id, :dept_id, :role_id, :role_desc, :create_user_id, :create_timestamp, :modify_user_id, :modify_timestamp " +
                        ")",
                new BeanPropertySqlParameterSource(roles));
    }
    
    /**
     * 根據key刪除資料
     *
     * @param roles 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Roles roles) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE ROLE_ID = :role_id  ",
                new BeanPropertySqlParameterSource(roles));
    }
    
    /**
     * 根據key更新資料
     *
     * @param roles 更新條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Roles roles) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
        		" ROLE_DESC = :role_desc, CREATE_USER_ID = :create_user_id, CREATE_TIMESTAMP = :create_timestamp, MODIFY_USER_ID = :modify_user_id, "
        		+ " MODIFY_TIMESTAMP = :modify_timestamp "
        		+ " WHERE ROLE_ID = :role_id ",
                new BeanPropertySqlParameterSource(roles));
    }
    
    /**
     * 根據role_id更新role_desc
     *
     * @param roles 更新條件
     * @return 異動筆數
     */
    @Override
    public int updateDescByRoleId(Roles roles) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
        		" ROLE_DESC = :role_desc, MODIFY_USER_ID = :modify_user_id, MODIFY_TIMESTAMP = :modify_timestamp "
        		+ " WHERE ROLE_ID = :role_id ",
                new BeanPropertySqlParameterSource(roles));
    }
    
    /**
     * 根據key選取資料
     *
     * @return 唯一值
     */
    @Override
    public List<Roles> selectListLikeRoleid(String role_id) {
        Roles roles = new Roles();
        roles.setSys_id("EI");
        if(StringUtils.isNotBlank(role_id)) {
        	roles.setRole_id(role_id);
        }
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.SYS_ID = :sys_id AND LOWER(t.ROLE_ID) like '%' + LOWER(ISNULL(:role_id ,t.ROLE_ID)) +'%' ";
        List<Roles> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(roles), BeanPropertyRowMapper.newInstance(Roles.class));
        return list;
    }


}