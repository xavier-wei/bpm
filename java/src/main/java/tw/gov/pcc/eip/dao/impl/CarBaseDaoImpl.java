package tw.gov.pcc.eip.dao.impl;

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
import tw.gov.pcc.eip.dao.DriverBaseDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.DriverBbase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

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


}