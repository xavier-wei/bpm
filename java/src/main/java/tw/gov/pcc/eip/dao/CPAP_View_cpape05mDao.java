package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.eip.domain.View_cpape05m;

import java.util.List;

/**
 * cpap.dbo.view_cpape05m Dao
 */
@DaoTable(CPAP_View_cpape05mDao.TABLE_NAME)
@Repository
public interface CPAP_View_cpape05mDao {

    String TABLE_NAME = "cpap.dbo.view_cpape05m";

    View_cpape05m selectByKey(String login_id);

    @SkipLog
    List<View_cpape05m> selectAll();
}