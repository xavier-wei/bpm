package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.User_profileDao;
import tw.gov.pcc.eip.domain.User_profile;

import java.util.List;

/**
 * 使用者設定 DaoImpl
 */
@DaoTable(User_profileDao.TABLE_NAME)
@Repository
public class User_profileDaoImpl extends BaseDao<User_profile> implements User_profileDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.USER_ID, t.PROFILE ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param user_profile 條件
     * @return 唯一值
     */
    @Override
    public User_profile selectDataByPrimaryKey(User_profile user_profile) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id ";
        List<User_profile> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(user_profile), BeanPropertyRowMapper.newInstance(User_profile.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param user_id 使用者代號
     * @return 唯一值
     */
    @Override
    public User_profile selectByKey(String user_id) {
        User_profile user_profile = new User_profile();
        user_profile.setUser_id(user_id);
        return selectDataByPrimaryKey(user_profile);
    }

    /**
     * 根據key刪除資料
     *
     * @param user_profile 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(User_profile user_profile) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE USER_ID = :user_id ",
                new BeanPropertySqlParameterSource(user_profile));
    }

    /**
     * 根據key更新資料
     *
     * @param user_profile 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(User_profile user_profile) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " PROFILE = :profile" +
                        " WHERE USER_ID = :user_id ",
                new BeanPropertySqlParameterSource(user_profile));
    }

    /**
     * 新增一筆資料
     *
     * @param user_profile 新增資料
     */
    @Override
    public int insert(User_profile user_profile) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " USER_ID, PROFILE" +
                        ")" +
                        " VALUES ( " +
                        " :user_id, :profile" +
                        ")",
                new BeanPropertySqlParameterSource(user_profile));
    }
}