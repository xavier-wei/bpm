package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

/**
 *  Dao
 */
@DaoTable(CaruseRecDao.TABLE_NAME)
@Repository
public interface CaruseRecDao {

    String TABLE_NAME = "CARUSE_REC";
    public List<Eip07w010Case> quaryCaruseRec(Eip07w010Case updateDate) ;
}