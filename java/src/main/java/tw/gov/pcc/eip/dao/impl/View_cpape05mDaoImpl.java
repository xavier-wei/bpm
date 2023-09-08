package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.View_cpape05mDao;
import tw.gov.pcc.eip.domain.View_cpape05m;

import java.util.HashMap;
import java.util.List;

/**
 * 人員資料表 DaoImpl
 */
@DaoTable(View_cpape05mDao.TABLE_NAME)
@Repository
public class View_cpape05mDaoImpl extends BaseDao<View_cpape05m> implements View_cpape05mDao {

    /**
     * 根據key選取資料(底層用)
     *
     * @param view_cpape05m 條件
     * @return 唯一值
     */
    @Override
    @SkipLog
    public View_cpape05m selectDataByPrimaryKey(View_cpape05m view_cpape05m) {
        String sql = "SELECT " + " * " + " FROM " + TABLE_NAME + " t WHERE t.login_id = :login_id ";
        List<View_cpape05m> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(view_cpape05m), BeanPropertyRowMapper.newInstance(View_cpape05m.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param login_id 登入帳號
     * @return 唯一值
     */
    @Override
    @SkipLog
    public View_cpape05m selectByKey(String login_id) {
        View_cpape05m view_cpape05m = new View_cpape05m();
        view_cpape05m.setLogin_id(login_id);
        return selectDataByPrimaryKey(view_cpape05m);
    }

    /**
     * 取得全部職稱資料
     *
     * @return
     */
    @Override
    @SkipLog
    public List<View_cpape05m> selectAllPetitTitle() {
        String sql = "SELECT petit, title " + "FROM (" + "    SELECT petit, title, " + "           ROW_NUMBER() OVER (PARTITION BY petit ORDER BY peupdate DESC) AS rn " + "    FROM " + TABLE_NAME + ") AS ranked " + "WHERE rn = 1";
        return getNamedParameterJdbcTemplate().query(sql, new HashMap<>(), BeanPropertyRowMapper.newInstance(View_cpape05m.class));
    }


    /**
     * 新增一筆資料
     *
     * @param view_cpape05m 新增資料
     */
    @Override
    @SkipLog
    public int insert(View_cpape05m view_cpape05m) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME + "(" + " PEORG, ORG_NAME, PEUNIT, UNIT_NAME, PEIDNO, PECARD, LOGIN_ID, PENAME, EMAIL, PETIT, " + " TITLE, PEHYEAR, PEHMON, PEHDAY, PESEX, PEBIRTHD, PECRKCOD, CRKCOD_NAME, PEMEMCOD, MEMCOD_NAME, " + " PEOVERHFEE, PEACTDATE, PELEVDATE, PEPOINT, PEYKIND, PERDAY, PERDAY1, PERDAY2, PEFSTDATE, PEHYEAR2, " + " PEHMON2, PETRAINING, PEAREA, CT_AREA_CODE, CT_TEL, CT_EXT, CT_MOBILE, CT_HOME_ADDR, IS_NATIONALITY, PEUPDATE" + ")" + " VALUES ( " + " :peorg, :org_name, :peunit, :unit_name, :peidno, :pecard, :login_id, :pename, :email, :petit, " + " :title, :pehyear, :pehmon, :pehday, :pesex, :pebirthd, :pecrkcod, :crkcod_name, :pememcod, :memcod_name, " + " :peoverhfee, :peactdate, :pelevdate, :pepoint, :peykind, :perday, :perday1, :perday2, :pefstdate, :pehyear2, " + " :pehmon2, :petraining, :pearea, :ct_area_code, :ct_tel, :ct_ext, :ct_mobile, :ct_home_addr, :is_nationality, :peupdate" + ")", new BeanPropertySqlParameterSource(view_cpape05m));
    }

    @Override
    @SkipLog
    public View_cpape05m selectMaxPeupdateRecordByPecard(String pecard) {
        View_cpape05m view_cpape05m = new View_cpape05m();
        view_cpape05m.setPecard(pecard);
        String sql = "SELECT " + " * " + " FROM " + TABLE_NAME + " t WHERE t.pecard = :pecard ORDER BY case PEUNIT when '600037' then '999999' else PEUNIT end, peupdate DESC";
        List<View_cpape05m> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(view_cpape05m), BeanPropertyRowMapper.newInstance(View_cpape05m.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    @SkipLog
    public void truncateAll() {
        getNamedParameterJdbcTemplate().update("TRUNCATE TABLE " + TABLE_NAME, new HashMap<>());
    }
}
