package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.eip.domain.View_cpape05m;

import java.util.List;

/**
 * 人員資料表 Dao
 */
@DaoTable(View_cpape05mDao.TABLE_NAME)
@Repository
public interface View_cpape05mDao {

    String TABLE_NAME = "view_cpape05m";

    View_cpape05m selectByKey(String login_id);

    @SkipLog
    List<View_cpape05m> selectAllPetitTitle();

    int insert(View_cpape05m view_cpape05m);

    @SkipLog
    View_cpape05m selectMaxPeupdateRecordByPecard(String pecard);

    @SkipLog
    void truncateAll();
}