package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;

/**
 *  Dao
 */
@DaoTable(GasRecDao.TABLE_NAME)
@Repository
public interface GasRecDao {

    String TABLE_NAME = "GAS_REC";

}