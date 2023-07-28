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
import tw.gov.pcc.eip.dao.CarBaseDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(CarBaseDao.TABLE_NAME)
@Repository
public class CarBaseDaoImpl extends BaseDao<CarBase> implements CarBaseDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.driverid , t.name, t.staffno, t.id, t.brdte, t.title, t.cellphone, "
        + " t.phone, t.still_work, t.startwork_date, t.endwork_date, t.licence_expire_date "
        + " t.licence_car_type, t.carno1, t.carno2, t.temp_staff, t.cre_user "
        + " t.cre_datetime, t.upd_user, t.upd_datetime ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param CarBase 條件
     * @return 唯一值
     */
    @Override
    public CarBase selectDataByPrimaryKey(CarBase carBase) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE Where t.carno1=:carno1 And t.carno2=:carno2";//這要改
        List<CarBase> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(carBase), BeanPropertyRowMapper.newInstance(CarBase.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 新增一筆資料
     *
     * @param CarBase  新增資料
     */
    @Override
    public int insert(Eip07w010Case eip07w010Case) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " Carno1,Carno2,owned,cartype,carsource ,caryear ,carcolor ,ccsize ,boss_mk ,bossname ,carstatus ,insurance_company , " +
                        " insurance_start,Insurance_end,cre_user,cre_datetime,upd_user ,upd_datetime  " +



                        ")" +
                        " VALUES ( " +
                        " :carno1, :carno2,:owned,:carType, :carSource, :carYear, :carColor, :ccSize, :bossMk, :bossName, :carStatus, " +
                        " :insuranceCompany, :insuranceStart,:InsuranceEnd,:creUser, :creDatetime, :updUser , :updDatetime " +
                        ")",

                new BeanPropertySqlParameterSource(eip07w010Case));
    }

    @Override
    public int delete(Eip07w010Case caseData)  {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                        .append(" DELETE  from " ) .append(TABLE_NAME ).append( " Where carno1=:carno1 ")
                        .append( " And carno2=:carno2 ").toString(),
                new BeanPropertySqlParameterSource(caseData));
    }

    public int updateCarBase(Eip07w010Case updateDate) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                        .append(" UPDATE ").append(TABLE_NAME)
                        .append(" SET  ")

                        .append(" Carno1=:carno1, Carno2=:carno2, owned=:owned,cartype=:carType, ")
                        .append(" carsource=:carSource, caryear=:carYear, carcolor=:carColor,ccsize=:ccSize, ")
                        .append(" boss_mk=:bossMk, bossname=:bossName, carstatus=:carStatus,insurance_company=:insuranceCompany, ")
                        .append(" insurance_start=:insuranceStart, Insurance_end=:InsuranceEnd, cre_user=:creUser,cre_datetime=:creDatetime, ")
                        .append(" upd_user=:updUser, upd_datetime=:updDatetime ")
                        .append( " Where carno1=:carno1 ")
                        .append( " And carno2=:carno2 ")
                        .toString(),
                new BeanPropertySqlParameterSource(updateDate));
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<Eip07w010Case> quaryCarBase(Eip07w010Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select   *  from CarBase ");
        if (StringUtils.isNotBlank(caseData.getCarno1())){
            sql.append(" WHERE carno1  = :carno1 ");
            sql.append(" AND carno2 =:carno2 ");
        }
        SqlParameterSource params = new MapSqlParameterSource("carno1",caseData.getCarno1())
                .addValue("carno2", caseData.getCarno2());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip07w010Case.class));
    }

    /**
     * 根據key選取資料
     *
     * @param carno1 CARNO1
     * @param carno2 CARNO2
     * @return 唯一值
     */
    @Override
    public CarBase selectByKey(String carno1, String carno2) {
    	CarBase CarBase = new CarBase();
        CarBase.setCarno1(carno1);
        CarBase.setCarno2(carno2);
        return selectDataByPrimaryKey(CarBase);
    }

    /**
     * 根據key刪除資料
     *
     * @param CarBase 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(CarBase CarBase) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " t WHERE t.CARNO1 = :carno1 AND t.CARNO2 = :carno2 ",
                new BeanPropertySqlParameterSource(CarBase));
    }

    /**
     * 根據key更新資料
     *
     * @param CarBase 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(CarBase CarBase) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + "  SET " +
                        " OWNED = :owned, CARTYPE = :cartype, CARSOURCE = :carsource, CARYEAR = :caryear, CARCOLOR = :carcolor, " +
                        " CCSIZE = :ccsize, BOSS_MK = :boss_mk, BOSSNAME = :bossname, CARSTATUS = :carstatus, INSURANCE_COMPANY = :insurance_company, " +
                        " INSURANCE_START = :insurance_start, INSURANCE_END = :insurance_end, CRE_USER = :cre_user, CRE_DATETIME = :cre_datetime, UPD_USER = :upd_user, " +
                        " UPD_DATETIME = :upd_datetime" +
                        " WHERE CARNO1 = :carno1 AND CARNO2 = :carno2 ",
                new BeanPropertySqlParameterSource(CarBase));
    }

    /**
     * 新增一筆資料
     *
     * @param CarBase 新增資料
     */
    @Override
    public int insert(CarBase CarBase) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " CARNO1, CARNO2, OWNED, CARTYPE, CARSOURCE, CARYEAR, CARCOLOR, CCSIZE, BOSS_MK, BOSSNAME, " +
                        " CARSTATUS, INSURANCE_COMPANY, INSURANCE_START, INSURANCE_END, CRE_USER, CRE_DATETIME, UPD_USER, UPD_DATETIME" +
                        ")" +
                        " VALUES ( " +
                        " :carno1, :carno2, :owned, :cartype, :carsource, :caryear, :carcolor, :ccsize, :boss_mk, :bossname, " +
                        " :carstatus, :insurance_company, :insurance_start, :insurance_end, :cre_user, :cre_datetime, :upd_user, :upd_datetime" +
                        ")",
                new BeanPropertySqlParameterSource(CarBase));
    }

	@Override
	public List<CarBase> getAllData() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select   *  from  "+ TABLE_NAME);

        SqlParameterSource params = new MapSqlParameterSource();
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(CarBase.class));
	}

	@Override
	public CarBase selectByCarno1Plus2(String carno) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select   *  from  "+ TABLE_NAME + " carno1+carno2= :carno ");

        SqlParameterSource params = new MapSqlParameterSource("carno",carno);
        List<CarBase> list = getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(CarBase.class));
        return CollectionUtils.isEmpty(list)? null : list.get(0);

	}

	@Override
	public CarBase selectCarAndDriverByCarno(String carno1, String carno2) {
        StringBuilder sql = new StringBuilder();
        
        sql.append(" select name,cellphone,a.carno1,a.carno2,cartype,carcolor ");
        sql.append(" from  DRIVER_BASE  a , CAR_BASE b ");
        sql.append(" where a.carno1=:carno1 and a.carno2=:carno2 "); 
        sql.append(" and a.carno1  = b.Carno1  ");
        sql.append(" and a.carno2 = b.carno2 ");

        
        Map<String, String> map = new HashMap<String, String>();
        map.put("carno1", carno1);
        map.put("carno2", carno2);
        		
        List<CarBase> list = getNamedParameterJdbcTemplate().query(sql.toString(), map,
                BeanPropertyRowMapper.newInstance(CarBase.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}