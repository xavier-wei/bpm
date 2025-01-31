package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.common.cases.Eip02w010Case.addressBook;
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
        ALL_COLUMNS_SQL = " t.USER_ID, t.ACNT_IS_VALID, t.CREATE_TIMESTAMP, t.CREATE_USER_ID, t.DEPT_ID, t.EMAIL, t.EMP_ID, t.LAST_LOGIN_DATE, t.LAST_LOGIN_IP, t.LDAP_ID, " +
                " t.MODIFY_USER_ID, t.MODIFY_TIMESTAMP, t.USER_NAME, t.TEL1, t.TEL2, t.TITLE_ID, t.LINE_TOKEN, t.FROM_HR, t.ORG_ID ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param users 條件
     * @return 唯一值
     */
    @Override
    public Users selectDataByPrimaryKey(Users users) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.USER_ID = :user_id ";
        List<Users> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(Users.class));
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
                        " ACNT_IS_VALID = :acnt_is_valid, CREATE_TIMESTAMP = :create_timestamp, CREATE_USER_ID = :create_user_id, DEPT_ID = :dept_id, EMAIL = :email, " +
                        " EMP_ID = :emp_id, LAST_LOGIN_DATE = :last_login_date, LAST_LOGIN_IP = :last_login_ip, LDAP_ID = :ldap_id, MODIFY_USER_ID = :modify_user_id, " +
                        " MODIFY_TIMESTAMP = :modify_timestamp, USER_NAME = :user_name, TEL1 = :tel1, TEL2 = :tel2, TITLE_ID = :title_id, " +
                        " LINE_TOKEN = :line_token, ORG_ID = :org_id" +
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
                        " USER_ID, ACNT_IS_VALID, CREATE_TIMESTAMP, CREATE_USER_ID, DEPT_ID, EMAIL, EMP_ID, LAST_LOGIN_DATE, LAST_LOGIN_IP, LDAP_ID, " +
                        " MODIFY_USER_ID, MODIFY_TIMESTAMP, USER_NAME, TEL1, TEL2, TITLE_ID, LINE_TOKEN, ORG_ID" +
                        ")" +
                        " VALUES ( " +
                        " :user_id, :acnt_is_valid, :create_timestamp, :create_user_id, :dept_id, :email, :emp_id, :last_login_date, :last_login_ip, :ldap_id, " +
                        " :modify_user_id, :modify_timestamp, :user_name, :tel1, :tel2, :title_id, :line_token, :org_id" +
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
    public List<Users> selectDataByUserIdAndDeptId(String user_id, String dept_id) {
        Users users = new Users();
        if (StringUtils.isNotBlank(user_id)) {
            users.setUser_id(user_id);
        }
        if (StringUtils.isNotBlank(dept_id)) {
            users.setDept_id(dept_id);
        }


        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t " +
                " WHERE t.USER_ID = ISNULL(:user_id, t.USER_ID) " +
                " AND t.DEPT_ID = ISNULL(:dept_id, t.DEPT_ID) ";
        List<Users> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(Users.class));
        return list;
    }

    /**
     * 讀取全部users
     *
     * @return
     */
    @Override
    public List<Users> selectAll() {
        Users users = new Users();

        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t ";
        List<Users> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(users), BeanPropertyRowMapper.newInstance(Users.class));
        return list;
    }

    @Override
    public List<addressBook> getEip02wUsers(String dept_id, String sort, String user_name, String user_id, String ename,
            String email, String titlename) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT A.USER_ID, "); // 員工編號
        sql.append("        A.USER_NAME, "); // 姓名
        sql.append("        CASE WHEN A.EMAIL IS NULL OR (CHARINDEX('@', A.EMAIL) = 0) THEN '' ");
        sql.append("        ELSE SUBSTRING(A.EMAIL, 1, CHARINDEX('@', A.EMAIL) -1) END ENAME, "); // 英文名
        sql.append("        A.DEPT_ID, "); // 部門代號
        sql.append("        B.DEPT_NAME, "); // 部門
        sql.append("        D.CODENAME AS ORGNAME, "); // 機關名稱
        sql.append("        C.CODENAME AS TITLENAME, "); // 職稱
        sql.append("        ISNULL(A.EMAIL, '') EMAIL, "); // 電子郵件信箱
        sql.append("        ISNULL(A.TEL1, '') TEL1, "); // 聯絡電話
        sql.append("        ISNULL(A.TEL2,' ') TEL2 "); // 分機
        sql.append("   FROM USERS A, ");
        sql.append("        DEPTS B, ");
        sql.append("        (SELECT CODENO,CODENAME FROM EIPCODE WHERE CODEKIND ='TITLE') C, ");
        sql.append("        (SELECT CODENO,CODENAME FROM EIPCODE WHERE CODEKIND ='ORG') D ");
        sql.append("  WHERE A.ACNT_IS_VALID = 'Y' ");
        sql.append("    AND B.IS_VALID = 'Y' ");
        sql.append("    AND A.DEPT_ID = B.DEPT_ID ");
        sql.append("    AND A.TITLE_ID = C.CODENO ");
        sql.append("    AND A.ORG_ID = D.CODENO ");
        if ("2".equals(sort)) { // 2:查單一部門
            sql.append("    AND A.DEPT_ID = ISNULL(:dept_id, A.DEPT_ID) ");
        } else { // 1:查全處
            sql.append("    AND A.DEPT_ID in (SELECT DEPT_ID FROM DEPTS WHERE DEPT_ID_P = ISNULL(:dept_id, A.DEPT_ID) or DEPT_ID = ISNULL(:dept_id, A.DEPT_ID)) ");
        }
        sql.append("    AND A.TITLE_ID = ISNULL(:titlename, A.TITLE_ID) ");
        sql.append("    AND A.USER_NAME LIKE '%' + ISNULL(:user_name, A.USER_NAME) + '%' ");
        sql.append("    AND A.USER_ID = ISNULL(:user_id, A.USER_ID) ");
        sql.append("    AND A.EMAIL LIKE ISNULL(:ename, A.EMAIL) + '%' ");
        sql.append("    AND A.EMAIL LIKE '%' + ISNULL(:email, A.EMAIL) + '%' ");
        sql.append("  ORDER BY DEPT_ID, TITLE_ID ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept_id", dept_id);
        params.put("user_name", user_name);
        params.put("user_id", user_id);
        params.put("ename", ename);
        params.put("email", email);
        params.put("titlename", titlename);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(addressBook.class));
    }

    public List<Users> getEip03wUsers(List<String> deptID) {
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT DEPT_ID, ");
        sql.append("          EMP_ID, ");
        sql.append("          (SELECT B.DEPT_NAME FROM DEPTS B WHERE B.DEPT_ID = A.DEPT_ID) AS DEPT_NAME, ");
        sql.append("          USER_NAME ");
        sql.append("     FROM USERS A ");
        sql.append("    WHERE DEPT_ID IN (:deptID)   "); // 替換為畫面選擇之RPTDEPT
        sql.append("      AND ACNT_IS_VALID = 'Y' ");
        sql.append(" ORDER BY DEPT_ID, EMP_ID ");

        Map<String, Object> params = new HashMap<>();
        params.put("deptID", deptID);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Users.class));
    }



    @Override
    public List<Users> findNameByMultiID(List<String> userIDs) {
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("     FROM USERS t");
        sql.append("    WHERE EMP_ID in (:userIDs) ");
        sql.append(" ORDER BY EMP_ID  ");

        Map<String, Object> params = new HashMap<>();
        params.put("userIDs", userIDs);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Users.class));
    }

    @Override
    public List<Users> findUserIDByUserName(String userName) {
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("     FROM USERS t");
        sql.append("    WHERE USER_NAME like '%' + :userName + '%' ");
        sql.append(" ORDER BY EMP_ID  ");

        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Users.class));
    }

    /**
     * 將HR的人頭匯入USERS，拿來開發測試用，正式應由LDAP匯入
     */
    @Override
    public void insertUsersFromView_cpape05m(){
        String sql = "insert into USERS (USER_ID, USER_NAME) select v.PECARD USER_ID, v.PENAME  USER_NAME  from view_cpape05m v where v.PEUNIT!='600037'";
    }

    @Override
    public List<Users> getUsersByDeptOrTitle(List<String> deptID, List<String> titleID) {
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("     FROM USERS t");
        sql.append("    WHERE ACNT_IS_VALID = 'Y' ");
        if (!CollectionUtils.isEmpty(deptID) && !CollectionUtils.isEmpty(titleID)) {
            sql.append("      AND ( DEPT_ID in (:deptID) OR TITLE_ID in (:titleID) )");
        } else if (!CollectionUtils.isEmpty(deptID)) {
            sql.append("      AND DEPT_ID in (:deptID) ");
        } else if (!CollectionUtils.isEmpty(titleID)) {
            sql.append("      AND TITLE_ID in (:titleID) ");
        }
        sql.append(" ORDER BY EMP_ID  ");

        Map<String, Object> params = new HashMap<>();
        params.put("deptID", deptID);
        params.put("titleID", titleID);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Users.class));
    }


    @Override
    public List<Users> getEmailList(List<String> codeNameList) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT " + ALL_COLUMNS_SQL);
        sql.append("   FROM " + TABLE_NAME + " T ");
        sql.append("   WHERE USER_ID IN (:codeNameList)");

        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), new MapSqlParameterSource("codeNameList", codeNameList),BeanPropertyRowMapper.newInstance(Users.class));
    }
    
    /**
     * (like user_id or like user_name) and dept_id in (deptlist)
     *
     * @param user_id 條件
     * @return
     */
    @Override
    public List<Users> selectDataByLikeUserIdOrLikeNameAndEqualDeptid(String user_id, String user_name, List<String> deptidList) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t " +
                " WHERE (LOWER(t.USER_ID) like '%' + LOWER(ISNULL(:user_id, t.USER_ID)) + '%' " +
                " OR LOWER(t.USER_NAME) like '%' + LOWER(ISNULL(:user_name, t.USER_NAME)) + '%') ";
    	
    	if (StringUtils.isBlank(user_id)) {
        	user_id = null;
        }
        if (StringUtils.isBlank(user_name)) {
        	user_name = null;
        }
        if(!CollectionUtils.isEmpty(deptidList)) {
        	sql = sql + " AND t.DEPT_ID in (:deptidList)";
        }


        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("user_name", user_name);
        params.put("deptidList", deptidList);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Users.class));
    }

    @Override
    public List<Users> getDeptUsers(String deptId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select user_id, USER_NAME from users");
        sql.append(" where DEPT_ID = :deptId ");
        sql.append(" and ACNT_IS_VALID = 'Y' ");
        Map<String, Object> params = new HashMap<>();
        params.put("deptId", deptId);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Users.class));
    }

}