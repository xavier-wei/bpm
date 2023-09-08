package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Position;

import java.util.List;

/**
 * webitr.dbo.position Dao
 */
@DaoTable(WEBITR_PositionDao.TABLE_NAME)
@Repository
public interface WEBITR_PositionDao {

    String TABLE_NAME = "webitr.dbo.position";

    List<Position> selectAll();

}