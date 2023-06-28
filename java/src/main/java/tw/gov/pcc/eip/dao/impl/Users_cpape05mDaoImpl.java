package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.dao.Users_cpape05mDao;
import tw.gov.pcc.eip.domain.Users_cpape05m;
import tw.gov.pcc.common.framework.dao.BaseDao;
import java.util.List;

/**
 * 人員資料表 DaoImpl
 */
@DaoTable(Users_cpape05mDao.TABLE_NAME)
@Repository
public class Users_cpape05mDaoImpl extends BaseDao<Users_cpape05m> implements Users_cpape05mDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" t.PEORG, t.ORG_NAME, t.PEUNIT, t.UNIT_NAME, t.PEIDNO, t.PECARD, t.LOGIN_ID, t.PENAME, t.EMAIL, t.PETIT, ")
                .append(" t.TITLE, t.PEHYEAR, t.PEHMON, t.PEHDAY, t.PESEX, t.PEBIRTHD, t.PECRKCOD, t.CRKCOD_NAME, t.PEMEMCOD, t.MEMCOD_NAME, ")
                .append(" t.PEOVERHFEE, t.PEACTDATE, t.PELEVDATE, t.PEPOINT, t.PEYKIND, t.PERDAY, t.PERDAY1, t.PERDAY2, t.PEFSTDATE, t.PEHYEAR2, ")
                .append(" t.PEHMON2, t.PETRAINING, t.PEAREA, t.CT_AREA_CODE, t.CT_TEL, t.CT_EXT, t.CT_MOBILE, t.CT_HOME_ADDR, t.IS_NATIONALITY, t.PEUPDATE")
                .toString();
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param users_cpape05m 條件
     * @return 唯一值
     */
    @Override
    public Users_cpape05m selectDataByPrimaryKey(Users_cpape05m users_cpape05m) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " t WHERE t.PECARD = :pecard ");
        List<Users_cpape05m> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(users_cpape05m), BeanPropertyRowMapper.newInstance(Users_cpape05m.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param pecard 員工編號
     * @return 唯一值
     */
    @Override
    public Users_cpape05m selectByKey(String pecard) {
        Users_cpape05m users_cpape05m = new Users_cpape05m();
        users_cpape05m.setPecard(pecard);
        return selectDataByPrimaryKey(users_cpape05m);
    }

    /**
     * 根據key刪除資料
     *
     * @param users_cpape05m 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Users_cpape05m users_cpape05m) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE PECARD = :pecard ",
                new BeanPropertySqlParameterSource(users_cpape05m));
    }

    /**
     * 根據key更新資料
     *
     * @param users_cpape05m 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Users_cpape05m users_cpape05m) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
                        " PEORG = :peorg, ORG_NAME = :org_name, PEUNIT = :peunit, UNIT_NAME = :unit_name, PEIDNO = :peidno, " +
                        " LOGIN_ID = :login_id, PENAME = :pename, EMAIL = :email, PETIT = :petit, TITLE = :title, " +
                        " PEHYEAR = :pehyear, PEHMON = :pehmon, PEHDAY = :pehday, PESEX = :pesex, PEBIRTHD = :pebirthd, " +
                        " PECRKCOD = :pecrkcod, CRKCOD_NAME = :crkcod_name, PEMEMCOD = :pememcod, MEMCOD_NAME = :memcod_name, PEOVERHFEE = :peoverhfee, " +
                        " PEACTDATE = :peactdate, PELEVDATE = :pelevdate, PEPOINT = :pepoint, PEYKIND = :peykind, PERDAY = :perday, " +
                        " PERDAY1 = :perday1, PERDAY2 = :perday2, PEFSTDATE = :pefstdate, PEHYEAR2 = :pehyear2, PEHMON2 = :pehmon2, " +
                        " PETRAINING = :petraining, PEAREA = :pearea, CT_AREA_CODE = :ct_area_code, CT_TEL = :ct_tel, CT_EXT = :ct_ext, " +
                        " CT_MOBILE = :ct_mobile, CT_HOME_ADDR = :ct_home_addr, IS_NATIONALITY = :is_nationality, PEUPDATE = :peupdate" +
                        " WHERE PECARD = :pecard ",
                new BeanPropertySqlParameterSource(users_cpape05m));
    }

    /**
     * 新增一筆資料
     *
     * @param users_cpape05m 新增資料
     */
    @Override
    public int insert(Users_cpape05m users_cpape05m) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " PEORG, ORG_NAME, PEUNIT, UNIT_NAME, PEIDNO, PECARD, LOGIN_ID, PENAME, EMAIL, PETIT, " +
                        " TITLE, PEHYEAR, PEHMON, PEHDAY, PESEX, PEBIRTHD, PECRKCOD, CRKCOD_NAME, PEMEMCOD, MEMCOD_NAME, " +
                        " PEOVERHFEE, PEACTDATE, PELEVDATE, PEPOINT, PEYKIND, PERDAY, PERDAY1, PERDAY2, PEFSTDATE, PEHYEAR2, " +
                        " PEHMON2, PETRAINING, PEAREA, CT_AREA_CODE, CT_TEL, CT_EXT, CT_MOBILE, CT_HOME_ADDR, IS_NATIONALITY, PEUPDATE" +
                        ")" +
                        " VALUES ( " +
                        " :peorg, :org_name, :peunit, :unit_name, :peidno, :pecard, :login_id, :pename, :email, :petit, " +
                        " :title, :pehyear, :pehmon, :pehday, :pesex, :pebirthd, :pecrkcod, :crkcod_name, :pememcod, :memcod_name, " +
                        " :peoverhfee, :peactdate, :pelevdate, :pepoint, :peykind, :perday, :perday1, :perday2, :pefstdate, :pehyear2, " +
                        " :pehmon2, :petraining, :pearea, :ct_area_code, :ct_tel, :ct_ext, :ct_mobile, :ct_home_addr, :is_nationality, :peupdate" +
                        ")",
                new BeanPropertySqlParameterSource(users_cpape05m));
    }
}