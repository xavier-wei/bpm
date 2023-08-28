package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.DriverBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

/**
 *  Dao
 */
@DaoTable(CarBaseDao.TABLE_NAME)
@Repository
public interface CarBaseDao {

    String TABLE_NAME = "CAR_BASE";
    
    public int insert(Eip07w010Case eip07w010Case);
    
    public int delete(Eip07w010Case deleteDate);

    public int updateCarBase(Eip07w010Case caseData);

    public List<Eip07w010Case> quaryCarBase(Eip07w010Case updateDate) ;
    
    public CarBase selectByKey(String carno1, String carno2);

    public int insert(CarBase car_base);

    public int updateByKey(CarBase car_base);

    public int deleteByKey(CarBase car_base);
    
    public List<CarBase> getAllData();
    
    public CarBase selectByCarno1Plus2(String carno);
    
    public CarBase selectCarAndDriverByCarno(String carno1,String carno2);

    public List<CarBase> getCarno();

    public List<CarBase> selectBosscarList();

    public List<CarBase> getCarNo(String carno1,String carno2) ;

}