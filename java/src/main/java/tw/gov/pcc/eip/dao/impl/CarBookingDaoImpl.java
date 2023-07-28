package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;
import tw.gov.pcc.eip.orderCar.cases.Eip07w040Case;
import tw.gov.pcc.eip.util.DateUtility;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(CarBookingDao.TABLE_NAME)
@Repository
public class CarBookingDaoImpl extends BaseDao<CarBooking> implements CarBookingDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.applyid , t.apply_user, t.apply_date, t.apply_dept, t.apply_memo, t.using_date, t.using_time_s, "
               + " t.using_time_e , t.using, t.destination, t.apply_car_type, t.num_of_people, t.carprocess_status, t.carno1, "
               +" t.carno2 , t.name, t.cellphone, t.cartype, t.carcolor, t.change_mk, t.change_count, "
               + " t.change_reason, t.b_apply_memo, t.b_apply_time_s, t.b_apply_time_e, t.b_using, t.b_destination, t.b_apply_car_type, t.b_num_of_people, t.reconfirm_mk ,"
               + " t.reconfirm_user, t.reconfirm_date, t.reconfirm_mk2, t.reconfirm_user2, t.reconfirm_date2, t.combine_mk, t.combine_applyid, t.combine_reason, t.print_mk, "
               +" t.cre_user, t.cre_datetime, t.upd_user, t.upd_datetime ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param CarBooking 條件
     * @return 唯一值
     */
    @Override
    public CarBooking selectDataByPrimaryKey(CarBooking carBooking) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.carno1 = :carno1 "+ " AND t.carno2 = :carno2 ";
        List<CarBooking> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(carBooking), BeanPropertyRowMapper.newInstance(CarBooking.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 新增一筆資料
     *
     * @param CarBooking  新增資料
     */
    @Override
    public int insert(Eip07w020Case eip07w020Case) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " apply_user,apply_dept,applyid,using,carprocess_status,apply_date,apply_memo,destination ,apply_car_type ,num_of_people ,using_date ,using_time_s ,using_time_e ,cre_user ,  " +
                        " cre_datetime,upd_user,upd_datetime " +

                        ")" +
                        " VALUES ( " +
                        " :applyName,:applyUnit,:applyId, :using,:processStaus,:applyDate, :useCarMemo,:destination, :carTy, :number, :useDate, :applyTimeS, :applyTimeE, :creUser, " +
                        " :creDatetime, :updUser,:updDatetime " +
                        ")",

                new BeanPropertySqlParameterSource(eip07w020Case));
    }


    public List<CarBooking> getApplyId() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT  t.applyid  from car_booking t  ");
        sql.append(" order by t.applyid desc ");
        SqlParameterSource params = new MapSqlParameterSource("","");
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(CarBooking.class));
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<CarBooking> quaryData(Eip07w020Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select *  ");
        sql.append(" , (SELECT codename  FROM EIPCODE e where e.codekind ='CARPROCESSSTATUS' AND e.codeno = CARProcess_status) codeName ");
        sql.append(" from CAR_BOOKING ");
        sql.append(" Where apply_user=:applyName ");
        sql.append("   And apply_dept =dbo.ufn_nvl(:applyUnit , apply_dept) ");
        sql.append("   And apply_date >=dbo.ufn_nvl(:applyDateStar, apply_date) ");
        sql.append(" And apply_date <=dbo.ufn_nvl(:applyDateEnd , apply_date) ");
        sql.append("   And using_date >= dbo.ufn_nvl(:useDateStar , using_date) ");
        sql.append("   And using_date <= dbo.ufn_nvl(:useDateEnd , using_date) ");
        sql.append(" ORDER BY APPLYID ");

        SqlParameterSource params = new MapSqlParameterSource("applyName",caseData.getApplyName())
                .addValue("applyUnit", caseData.getApplyUnit())
                .addValue("applyDateStar", caseData.getApplyDateStar())
                .addValue("applyDateEnd", caseData.getApplyDateEnd())
                .addValue("useDateStar", caseData.getUseDateStar())
                .addValue("useDateEnd", caseData.getUseDateEnd());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(CarBooking.class));
    }

	@Override
	public List<CarBooking> selectByApplydate(String applydateStart, String applydateEnd, String type) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" Select * from CAR_BOOKING ");
        sql.append(" Where apply_date >= ISNULL(:applydateStart, apply_date) ");
        sql.append(" And apply_date <=:applydateEnd ");
        
        if("eip07w030".equals(type)) {        	
        	sql.append(" And CARProcess_status in ('1','U') ");
        } else if ("eip07w050".equals(type)) {
        	sql.append(" And CARProcess_status in ('3','4') ");
        }
        
        SqlParameterSource params = new MapSqlParameterSource("applydateStart", DateUtility.changeDateType(applydateStart))
        .addValue("applydateEnd", DateUtility.changeDateType(applydateEnd));

        List<CarBooking> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(CarBooking.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}

	@Override
	public CarBooking selectByApplyId(String applyId) {
        StringBuilder sql=new StringBuilder();
        sql.append(" Select * from "  + TABLE_NAME  + " Where applyid = :applyId ");

        SqlParameterSource params = new MapSqlParameterSource("applyId", applyId);

        List<CarBooking> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(CarBooking.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
    /**
     * 根據key更新資料
     *
     * @param car_booking 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(CarBooking car_booking) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + "  SET " +
                        " APPLYID = :applyid, APPLY_USER = :apply_user, APPLY_DATE = :apply_date, APPLY_DEPT = :apply_dept, APPLY_MEMO = :apply_memo, " +
                        " USING_DATE = :using_date, USING_TIME_S = :using_time_s, USING_TIME_E = :using_time_e, DESTINATION = :destination, APPLY_CAR_TYPE = :apply_car_type, " +
                        " NUM_OF_PEOPLE = :num_of_people, CARPROCESS_STATUS = :carprocess_status, CARNO1 = :carno1, CARNO2 = :carno2, NAME = :name, " +
                        " CELLPHONE = :cellphone, CARTYPE = :cartype, CARCOLOR = :carcolor, CHANGE_MK = :change_mk, CHANGE_COUNT = :change_count, " +
                        " CHANGE_REASON = :change_reason, B_APPLY_MEMO = :b_apply_memo, B_APPLY_TIME_S = :b_apply_time_s, B_APPLY_TIME_E = :b_apply_time_e, B_USING = :b_using, " +
                        " B_DESTINATION = :b_destination, B_APPLY_CAR_TYPE = :b_apply_car_type, B_NUM_OF_PEOPLE = :b_num_of_people, RECONFIRM_MK = :reconfirm_mk, RECONFIRM_USER = :reconfirm_user, " +
                        " RECONFIRM_DATE = :reconfirm_date, RECONFIRM_MK2 = :reconfirm_mk2, RECONFIRM_USER2 = :reconfirm_user2, RECONFIRM_DATE2 = :reconfirm_date2, COMBINE_MK = :combine_mk, " +
                        " COMBINE_APPLYID = :combine_applyid, COMBINE_REASON = :combine_reason, PRINT_MK = :print_mk, CRE_USER = :cre_user, CRE_DATETIME = :cre_datetime, " +
                        " UPD_USER = :upd_user, UPD_DATETIME = :upd_datetime" +
                        " WHERE applyid = :applyid ",
                new BeanPropertySqlParameterSource(car_booking));
    }

    /**
     * 根據key刪除資料
     *
     * @param car_booking 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(CarBooking car_booking) {
        return getNamedParameterJdbcTemplate().update(" DELETE " + TABLE_NAME +
                        " WHERE applyid = :applyid ",
                new BeanPropertySqlParameterSource(car_booking));
    }
    
	@Override
	public List<CarBooking> selectByStatusIn234(Eip07w040Case caseData) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" Select * from CAR_BOOKING ");
        
        if(StringUtils.isNotEmpty(caseData.getCarprocess_status())) {
        	 sql.append(" Where carprocess_status in (:carprocess_status) ");
        }else {
        	 sql.append(" Where carprocess_status in ('2','3','4') ");
        }
        
        if(StringUtils.isNotEmpty(caseData.getApplydateStart()) && StringUtils.isNotEmpty(caseData.getApplydateEnd())) {
        	sql.append(" AND apply_date  BETWEEN :applydatestart AND :applydateend ");
        }
        
        if(StringUtils.isNotEmpty(caseData.getUsing_time_s()) && StringUtils.isNotEmpty(caseData.getUsing_time_e())) {
        	sql.append(" AND using_date  BETWEEN :using_time_s AND :using_time_e ");
        }
        
        sql.append(" order by print_mk, applyid ");
        
        Map<String,String> map = new HashMap<String, String>();
        map.put("carprocess_status", caseData.getCarprocess_status());

        if(StringUtils.isNotEmpty(caseData.getApplydateStart()) && StringUtils.isEmpty(caseData.getApplydateEnd())) {
            map.put("applydatestart", caseData.getApplydateStart());
            map.put("applydateend", DateUtility.getNowWestDate());
        } else {
            map.put("applydatestart", StringUtils.isNotEmpty(caseData.getApplydateStart())?DateUtility.changeDateType(caseData.getApplydateStart()) : "");
            map.put("applydateend", StringUtils.isNotEmpty(caseData.getApplydateEnd())?DateUtility.changeDateType(caseData.getApplydateEnd()) : "");
        }
        
        if(StringUtils.isNotEmpty(caseData.getUsing_time_s()) && StringUtils.isEmpty(caseData.getUsing_time_e())) {
        	map.put("using_time_s", caseData.getUsing_time_s());
        	map.put("using_time_e", DateUtility.getNowWestDate());
        }else {        	
        	map.put("using_time_s", StringUtils.isNotEmpty(caseData.getUsing_time_s())?DateUtility.changeDateType(caseData.getUsing_time_s()) : "");
        	map.put("using_time_e", StringUtils.isNotEmpty(caseData.getUsing_time_e())?DateUtility.changeDateType(caseData.getUsing_time_e()) : "");
        }
        List<CarBooking> list = getNamedParameterJdbcTemplate().query(sql.toString(), map,BeanPropertyRowMapper.newInstance(CarBooking.class));

        return CollectionUtils.isEmpty(list) ? null : list;
	}

	@Override
	public List<CarBooking> getDataByCarnoAndUsing_date(CarBooking carBooking) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" Select distinct c.APPLYid, c.using_rec,b.Using_date,b.Using_time_s,b.Using_time_e,b.apply_memo,b.apply_dept,c.using_rec as using "); 
		sql.append(" From  car_booking_rec c , Car_Booking b   "); 
		sql.append(" Where c.CARNO1+c.CARNO2 =:carno  "); 
		sql.append(" AND c.using_date= :using_date  "); 
		sql.append(" AND c.APPLYid = b.APPLYid "); 
		sql.append(" AND c.Carno1 = b.Carno1  "); 
		sql.append(" AND c.Carno2 = b.Carno2 "); 
		sql.append(" Order by APPLYid   "); 
		
        SqlParameterSource params = new MapSqlParameterSource("carno",carBooking.getCarno1()+carBooking.getCarno2())
        		.addValue("using_date", DateUtility.changeDateType(carBooking.getUsing_date()));
        List<CarBooking> list = getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(CarBooking.class));
        return CollectionUtils.isEmpty(list)? null : list;
	}

	@Override
	public String checkTime(String using1 , String using2) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT dbo.UFN_CHECK( :using1, :using2 ) as using ");
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("using1", using1);
        map.put("using2", using2);
        		
        List<String> list = getNamedParameterJdbcTemplate().query(sql.toString(), map,
                BeanPropertyRowMapper.newInstance(String.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public CarBooking selectCarDriveDataBycarno(String carno1, String carno2) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" select cartype,carcolor,name,phone cellphone from car_Base c , DRIVER_BASE d ");
        sql.append(" where   ");
        sql.append(" c.carno1 = :carno1   ");
        sql.append(" and c.carno2 = :carno2  ");
        sql.append(" and c.carno1 = d.carno1  ");
        sql.append(" and c.carno2 = d.carno2  ");
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("carno1", carno1);
        map.put("carno2", carno2);
        		
        List<CarBooking> list = getNamedParameterJdbcTemplate().query(sql.toString(), map,
                BeanPropertyRowMapper.newInstance(CarBooking.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}