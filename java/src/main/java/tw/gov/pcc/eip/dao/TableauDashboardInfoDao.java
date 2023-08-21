package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.TableauDashboardInfo;

import java.util.List;


@DaoTable(TableauDashboardInfoDao.TABLE_NAME)
@Repository
public interface TableauDashboardInfoDao {

    String TABLE_NAME = "PWC_TB_TABLEAU_DASHBOARD_INFO";

    List<TableauDashboardInfo> selectByDashboardFigId(List<String> figIdList);

    List<TableauDashboardInfo> selectAll();

}