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


}