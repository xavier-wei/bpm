package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;

/**
 *  Dao
 */
@DaoTable(DriverBaseDao.TABLE_NAME)
@Repository
public interface DriverBaseDao {

    String TABLE_NAME = "DRIVER_BASE";

}