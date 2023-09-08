package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.ItrBaseDao;
import tw.gov.pcc.eip.dao.CPAP_View_cpape05mDao;
import tw.gov.pcc.eip.domain.View_cpape05m;

import java.util.List;

/**
 * cpap.dbo.view_cpape05m DaoImpl
 */
@DaoTable(CPAP_View_cpape05mDao.TABLE_NAME)
@Repository
public class CPAP_View_cpape05mDaoImpl extends ItrBaseDao<View_cpape05m> implements CPAP_View_cpape05mDao {

    /**
     * 根據key選取資料(底層用)
     *
     * @param view_cpape05m 條件
     * @return 唯一值
     */
    @Override
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

    @Override
    @SkipLog
    public List<View_cpape05m> selectAll() {
        View_cpape05m view_cpape05m = new View_cpape05m();
        String sql = "SELECT " + " * " + " FROM " + TABLE_NAME;
        return getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(view_cpape05m), BeanPropertyRowMapper.newInstance(View_cpape05m.class));
    }
}
