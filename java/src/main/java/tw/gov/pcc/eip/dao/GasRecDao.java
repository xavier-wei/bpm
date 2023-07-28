package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

/**
 *  Dao
 */
@DaoTable(GasRecDao.TABLE_NAME)
@Repository
public interface GasRecDao {

    String TABLE_NAME = "GAS_REC";

    public List<Eip07w010Case> quaryGasRec(Eip07w010Case updateDate) ;
}