package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.View_oup_unit;

/**
 * VIEW_OUP_UNIT Dao
 */
@DaoTable(View_oup_unitDao.TABLE_NAME)
@Repository
public interface View_oup_unitDao {

    String TABLE_NAME = "VIEW_OUP_UNIT";

    void truncateAll();

    int insert(View_oup_unit view_oup_unit);

}