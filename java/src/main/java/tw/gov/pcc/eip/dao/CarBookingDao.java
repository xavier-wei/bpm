package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;
import tw.gov.pcc.eip.orderCar.cases.Eip07w040Case;

/**
 *  Dao
 */
@DaoTable(CarBookingDao.TABLE_NAME)
@Repository
public interface CarBookingDao {

    String TABLE_NAME = "CAR_BOOKING";

    public int insert(CarBooking car_booking);

    public List<CarBooking> getApplyId();

    public List<CarBooking> quaryData(Eip07w020Case Date);
    

    /**
     * 用申請日期及表單狀態查詢資料
     * @param applydateStart 申請日期起日
     * @param applydateEnd   申請日期迄日
     * @param type eip07w030：派車預約暨派車結果查詢作業-carprocess_status in ('1','U')，
     * @param type eip07w050：秘書處主管核派作業-carprocess_status in ('3','4')
     * 
     **/ 
    List<CarBooking> selectByApplydate(String applydateStart, String applydateEnd, String type, List<String> apply_dept);
    
    /**
     * 用申請單號查詢資料
     * 
     **/     
    CarBooking selectByApplyId(String applyId);
    
    public int updateByKey(CarBooking car_booking);

    public int deleteByKey(CarBooking car_booking);

    /**
     * 用派車狀態=2,3,4的資料
     * @param applydateStart 申請日期起日
     * @param applydateEnd   申請日期迄日
     * @param dataCondition = 1 ， 第一次進入，查詢carprocess_status in 2,3,4 && using_date between 當月一號 ~ 99991231
     * @param dataCondition = 2 ， 依照畫面鍵入條件來查詢
     * 
     **/ 
//    List<CarBooking> selectForEip07w040(Eip07w040Case caseData,String dataCondition);
    
    public List<CarBooking> getDataByCarnoAndUsing_date(CarBooking carBooking);
    
    public CarBooking checkTime(String using1 , String using2) ;
    
    public CarBooking selectCarDriveDataBycarno(String carno1,String carno2);
    
    public List<CarBooking> getEip07w070ReportData(CarBooking carBooking,String OrderCondition);

    public CarBooking selectByApplyidAndStatusIn3467F(String applyid);

    String getApplyCarnoSeq();

    void updateSequence();

	public List<CarBooking> selectOneMonthApplyidAndStatusIn3467F(String westYearMonth);
	
	//carstatus='2'的資料
	public List<CarBooking> notHandledDataForEip07w040(Eip07w040Case caseData, String type);
	
	//carstatus='3,4,6,7'的資料 type=default(只查usingDate) type=condition(依據畫面條件來查)
	public List<CarBooking> handledDataForEip07w040(Eip07w040Case caseData, String type);
	
	//carprocess_status秘書室長官核派案件
	//type all => 全撈， Y=>reconfime_mk2=Y ，Y=>reconfime_mk2=N
	public List<CarBooking> selectByReconfime_mk2ForEip07w040(Eip07w040Case caseData,String type);
	
}