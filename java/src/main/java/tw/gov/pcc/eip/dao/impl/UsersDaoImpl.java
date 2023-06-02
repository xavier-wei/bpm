package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Users;

/**
 * 使用者資料 DaoImpl
 */
@DaoTable(UsersDao.TABLE_NAME)
@Repository
public class UsersDaoImpl extends BaseDao<Users> implements UsersDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" t.USER_ID, t.ACNT_EXPIRED_DATE, t.ACNT_IS_VALID, t.COUNT, t.CREATE_TIMESTAMP, t.CREATE_USER_ID, t.DEPT_ID, t.REPORT_TO_USER_ID, t.EMAIL, t.EMP_ID, ")
                .append(" t.IDNO, t.IS_ADMIN, t.IS_USE_CERT, t.USER_KIND, t.LAST_LOGIN_DATE, t.LAST_LOGIN_IP, t.LDAP_ID, t.LEAVING_DATE, t.MODIFY_USER_ID, t.MODIFY_TIMESTAMP, ")
                .append(" t.USER_NAME, t.OLD_USER_ID, t.PW_EXPIRED_DATE, t.PW_POLICY, t.TEL1, t.TEL2, t.TITLE_ID ")
                .toString();
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param users 條件
     * @return 唯一值
     */
    @Override
    public Users selectDataByPrimaryKey(Users users) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id ");
        List<Users> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(Users.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param user_id 使用者代號
     * @return 唯一值
     */
    @Override
    public Users selectByKey(String user_id) {
        Users users = new Users();
        users.setUser_id(user_id);
        return selectDataByPrimaryKey(users);
    }

    /**
     * 根據key刪除資料
     *
     * @param users 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Users users) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE USER_ID = :user_id ",
                new BeanPropertySqlParameterSource(users));
    }

    /**
     * 根據key更新資料
     *
     * @param users 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Users users) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " ACNT_EXPIRED_DATE = :acnt_expired_date, ACNT_IS_VALID = :acnt_is_valid, COUNT = :count, CREATE_TIMESTAMP = :create_timestamp, CREATE_USER_ID = :create_user_id, " +
                        " DEPT_ID = :dept_id, REPORT_TO_USER_ID = :report_to_user_id, EMAIL = :email, EMP_ID = :emp_id, IDNO = :idno, " +
                        " IS_ADMIN = :is_admin, IS_USE_CERT = :is_use_cert, USER_KIND = :user_kind, LAST_LOGIN_DATE = :last_login_date, LAST_LOGIN_IP = :last_login_ip, " +
                        " LDAP_ID = :ldap_id, LEAVING_DATE = :leaving_date, MODIFY_USER_ID = :modify_user_id, MODIFY_TIMESTAMP = :modify_timestamp, USER_NAME = :user_name, " +
                        " OLD_USER_ID = :old_user_id, PW_EXPIRED_DATE = :pw_expired_date, PW_POLICY = :pw_policy, TEL1 = :tel1, TEL2 = :tel2, " +
                        " TITLE_ID = :title_id" +
                        " WHERE USER_ID = :user_id ",
                new BeanPropertySqlParameterSource(users));
    }

    /**
     * 新增一筆資料
     *
     * @param users 新增資料
     */
    @Override
    public int insert(Users users) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " USER_ID, ACNT_EXPIRED_DATE, ACNT_IS_VALID, COUNT, CREATE_TIMESTAMP, CREATE_USER_ID, DEPT_ID, REPORT_TO_USER_ID, EMAIL, EMP_ID, " +
                        " IDNO, IS_ADMIN, IS_USE_CERT, USER_KIND, LAST_LOGIN_DATE, LAST_LOGIN_IP, LDAP_ID, LEAVING_DATE, MODIFY_USER_ID, MODIFY_TIMESTAMP, " +
                        " USER_NAME, OLD_USER_ID, PW_EXPIRED_DATE, PW_POLICY, TEL1, TEL2, TITLE_ID" +
                        ")" +
                        " VALUES ( " +
                        " :user_id, :acnt_expired_date, :acnt_is_valid, :count, :create_timestamp, :create_user_id, :dept_id, :report_to_user_id, :email, :emp_id, " +
                        " :idno, :is_admin, :is_use_cert, :user_kind, :last_login_date, :last_login_ip, :ldap_id, :leaving_date, :modify_user_id, :modify_timestamp, " +
                        " :user_name, :old_user_id, :pw_expired_date, :pw_policy, :tel1, :tel2, :title_id" +
                        ")",
                new BeanPropertySqlParameterSource(users));
    }
    
    /**
     * 根據key更新Acnt_is_valid
     *
     * @param users 條件
     * @return 異動筆數
     */
    @Override
    public int updateAcntisvalidByKey(Users users) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " ACNT_IS_VALID = :acnt_is_valid, MODIFY_USER_ID = :modify_user_id, MODIFY_TIMESTAMP = :modify_timestamp " +
                        " WHERE USER_ID = :user_id ",
                new BeanPropertySqlParameterSource(users));
    }
    
    /**
     * 根據user_id選取資料
     *
     * @param user_id 條件
     * @return 
     */
    @Override
    public List<Users> selectDataByUserIdAndDeptId(String user_id,String dept_id) {
    	Users users = new Users();
    	if(StringUtils.isNotBlank(user_id)) {
    		users.setUser_id(user_id);
    	}
    	if(StringUtils.isNotBlank(dept_id)) {
    		users.setDept_id(dept_id);
    	}
    	
    	
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t ");
        sql.append(" WHERE t.USER_ID = ISNULL(:user_id, t.USER_ID) ");
        sql.append(" AND t.DEPT_ID = ISNULL(:dept_id, t.DEPT_ID) ");
        List<Users> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(Users.class));
        return list;
    }
    
    /**
     * 讀取全部users demo用
     *
     * @return 
     */
    @Override
    public List<Users> selectAll() {
    	Users users = new Users();
    	
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t ");
        List<Users> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(Users.class));
        return list;
    }
    
}