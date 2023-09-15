package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.TableauUserInfoDao;
import tw.gov.pcc.eip.domain.TableauUserInfo;

import java.util.List;

@DaoTable(TableauUserInfoDao.TABLE_NAME)
@Repository
public class TableauUserInfoDaoImpl extends BaseDao<TableauUserInfo> implements TableauUserInfoDao {

    @Override
    public List<TableauUserInfo> selectByUserId(String userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " where USER_ID = :userId ");
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        List<TableauUserInfo> list = getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(TableauUserInfo.class));
        return list;
    }

    @Override
    public List<TableauUserInfo> selectByFigId(String fig_id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " where DASHBOARD_FIG_ID = :figId ");
        SqlParameterSource params = new MapSqlParameterSource("figId", fig_id);
        List<TableauUserInfo> list = getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(TableauUserInfo.class));
        return list;
    }

    @Override
    public TableauUserInfo selectDataByPrimaryKey(TableauUserInfo tableauUserInfo) {
        return null;
    }

}