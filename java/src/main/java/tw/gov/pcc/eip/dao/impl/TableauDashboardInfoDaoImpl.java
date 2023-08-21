package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.TableauDashboardInfoDao;
import tw.gov.pcc.eip.domain.TableauDashboardInfo;

import java.util.List;

@DaoTable(TableauDashboardInfoDao.TABLE_NAME)
@Repository
public class TableauDashboardInfoDaoImpl extends BaseDao<TableauDashboardInfo> implements TableauDashboardInfoDao {

    @Override
    public List<TableauDashboardInfo> selectByDashboardFigId(List<String> figIdList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " where DASHBOARD_FIG_ID in ( :figId ) ");
        SqlParameterSource params = new MapSqlParameterSource("figId", figIdList);
        List<TableauDashboardInfo> list = getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(TableauDashboardInfo.class));
        return list;
    }

    @Override
    public TableauDashboardInfo selectDataByPrimaryKey(TableauDashboardInfo tableauDashboardInfo) {
        return null;
    }
}