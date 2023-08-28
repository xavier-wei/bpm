package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.DriverBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

/**
 *  Dao
 */
@DaoTable(DriverBaseDao.TABLE_NAME)
@Repository
public interface DriverBaseDao {

    String TABLE_NAME = "DRIVER_BASE";
    public int insert(Eip07w010Case eip07w010Case);

    public List<Eip07w010Case> queryDriver(Eip07w010Case eip07w010Case);

    public List<Eip07w010Case> getDriverid() ;

    public List<Eip07w010Case> getTempStaff() ;

    public int delete(Eip07w010Case updateDate);

    public int updateDriverBase(Eip07w010Case caseData);

	public DriverBase getDataByCarno12(DriverBase driverbase);

    public List<DriverBase> getDriveId(String id) ;
}