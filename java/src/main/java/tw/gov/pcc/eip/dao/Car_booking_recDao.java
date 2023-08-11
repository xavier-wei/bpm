package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Car_booking_rec;

/**
 * CAR_BOOKING_REC Dao
 */
@DaoTable(Car_booking_recDao.TABLE_NAME)
@Repository
public interface Car_booking_recDao {

    public String TABLE_NAME = "CAR_BOOKING_REC";

    public Car_booking_rec selectByKey(String applyid);

    public int insert(Car_booking_rec car_booking_rec);

    public int updateByKey(Car_booking_rec car_booking_rec);

    public int deleteByKey(Car_booking_rec car_booking_rec);
    
}