package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.User_auth_deptDao;
import tw.gov.pcc.eip.domain.User_auth_dept;

/**
 * 使用者資料 DaoImpl
 */
@DaoTable(User_auth_deptDao.TABLE_NAME)
@Repository
public class User_auth_deptDaoImpl extends BaseDao<User_auth_dept> implements User_auth_deptDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.USER_ID, t.DEPT_ID ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param users 條件
     * @return 唯一值
     */
    @Override
    public User_auth_dept selectDataByPrimaryKey(User_auth_dept users) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id and t.DEPT_ID = :dept_id ";
        List<User_auth_dept> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(User_auth_dept.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param user_id 使用者代號
     * @return 唯一值
     */
    @Override
    public List<User_auth_dept> selectByUser_id(String user_id) {
    	
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id  ";
        
    	User_auth_dept users = new User_auth_dept();
        users.setUser_id(user_id);
        
        List<User_auth_dept> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(User_auth_dept.class));
        return CollectionUtils.isEmpty(list) ? null : list;

    }

    /**
     * 根據user_id、dept_id選取資料
     *
     * @param user_id 員工編號
     * @param dept_id 部門代號
     * @return 唯一值
     */
    @Override
    public List<User_auth_dept> selectByUser_id_OR_Dept_id(String user_id, String dept_id) {

        String sql = "SELECT " +
                ALL_COLUMNS_SQL + ", USERS.USER_NAME , DEPTS.DEPT_NAME" +
                " FROM " + TABLE_NAME + " t " +
                " LEFT JOIN USERS on USERS.USER_ID = t.USER_ID " +
                " LEFT JOIN DEPTS on DEPTS.DEPT_ID = t.DEPT_ID " +
                " WHERE LOWER(t.USER_ID) like '%' + LOWER(ISNULL(:user_id ,t.USER_ID)) +'%' AND LOWER(t.DEPT_ID) like '%' + LOWER(ISNULL(:dept_id ,t.DEPT_ID)) +'%' ";

        User_auth_dept user_auth_dept = new User_auth_dept();
        user_auth_dept.setUser_id(user_id);
        user_auth_dept.setDept_id(dept_id);

        List<User_auth_dept> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(user_auth_dept), BeanPropertyRowMapper.newInstance(User_auth_dept.class));
        return CollectionUtils.isEmpty(list) ? null : list;

    }

    /**
     * 新增一筆資料
     *
     * @param user_auth_dept 新增資料
     */
    @Override
    public int insert(User_auth_dept user_auth_dept) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " USER_ID, DEPT_ID " +
                        ")" +
                        " VALUES ( " +
                        " :user_id, :dept_id " +
                        ")",
                new BeanPropertySqlParameterSource(user_auth_dept));
    }

    /**
     * 根據key刪除資料
     *
     * @param user_auth_dept 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(User_auth_dept user_auth_dept) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE USER_ID = :user_id and DEPT_ID = :dept_id ",
                new BeanPropertySqlParameterSource(user_auth_dept));
    }


}