package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

/**
 *  Dao
 */
@DaoTable(CarBaseDao.TABLE_NAME)
@Repository
public interface CarBaseDao {

    String TABLE_NAME = "CAR_BASE";
    public int insert(Eip07w010Case eip07w010Case);
    public int delete(Eip07w010Case updateDate);

    public int updateCarBase(Eip07w010Case caseData);
}