package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.ItrBaseDao;
import tw.gov.pcc.eip.dao.View_cpape05mDao;
import tw.gov.pcc.eip.domain.View_cpape05m;

import java.util.HashMap;
import java.util.List;

/**
 * 人員資料表 DaoImpl
 */
@DaoTable(View_cpape05mDao.TABLE_NAME)
@Repository
public class View_cpape05mDaoImpl extends ItrBaseDao<View_cpape05m> implements View_cpape05mDao {

    /**
     * 根據key選取資料(底層用)
     *
     * @param view_cpape05m 條件
     * @return 唯一值
     */
    @Override
    public View_cpape05m selectDataByPrimaryKey(View_cpape05m view_cpape05m) {
        String sql = "SELECT "
                + " * "
                + " FROM cpap.dbo.view_cpape05m t WHERE t.login_id = :login_id ";
        List<View_cpape05m> list =
                getNamedParameterJdbcTemplate()
                        .query(
                                sql,
                                new BeanPropertySqlParameterSource(view_cpape05m),
                                BeanPropertyRowMapper.newInstance(View_cpape05m.class));
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
     * @return
     */
    @Override
    @SkipLog
    public List<View_cpape05m> selectAllPetitTitle() {
        String sql = "SELECT petit, title " +
                "FROM (" +
                "    SELECT petit, title, " +
                "           ROW_NUMBER() OVER (PARTITION BY petit ORDER BY peupdate DESC) AS rn " +
                "    FROM cpap.dbo.view_cpape05m " +
                ") AS ranked " +
                "WHERE rn = 1";
        return getNamedParameterJdbcTemplate()
                .query(sql,new HashMap<>(),BeanPropertyRowMapper.newInstance(View_cpape05m.class));
    }

    @Override
    @SkipLog
    public View_cpape05m selectMaxPeupdateRecordByPecard(String pecard) {
        View_cpape05m view_cpape05m = new View_cpape05m();
        view_cpape05m.setPecard(pecard);
        String sql = "SELECT "
                + " * "
                + " FROM cpap.dbo.view_cpape05m t WHERE t.pecard = :pecard AND case PEUNIT when '600037' then '999999' else PEUNIT end ORDER BY PEUNIT desc, peupdate DESC";
        List<View_cpape05m> list =
                getNamedParameterJdbcTemplate()
                        .query(
                                sql,
                                new BeanPropertySqlParameterSource(view_cpape05m),
                                BeanPropertyRowMapper.newInstance(View_cpape05m.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
