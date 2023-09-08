package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Position;

/**
 * POSITION Dao
 */
@DaoTable(PositionDao.TABLE_NAME)
@Repository
public interface PositionDao {

    String TABLE_NAME = "POSITION";

    int insert(Position position);

    void truncateAll();

}