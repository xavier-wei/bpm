package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;

/**
 *  Dao
 */
@DaoTable(CaruseRecDao.TABLE_NAME)
@Repository
public interface CaruseRecDao {

    String TABLE_NAME = "CARUSE_REC";

}