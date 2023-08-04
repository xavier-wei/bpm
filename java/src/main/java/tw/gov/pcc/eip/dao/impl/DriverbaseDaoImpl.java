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
import tw.gov.pcc.eip.dao.DriverBaseDao;
import tw.gov.pcc.eip.domain.DriverBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(DriverBaseDao.TABLE_NAME)
@Repository
public class DriverbaseDaoImpl extends BaseDao<DriverBase> implements DriverBaseDao {

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
     * @param DriverBbase 條件
     * @return 唯一值
     */
    @Override
    public DriverBase selectDataByPrimaryKey(DriverBase driverBbase) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.driverid = :driverid ";
        List<DriverBase> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(driverBbase), BeanPropertyRowMapper.newInstance(DriverBase.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 新增一筆資料
     *
     * @param DriverBbase  新增資料
     */
    @Override
    public int insert(Eip07w010Case eip07w010Case) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " driverid,name ,staffno,id,brdte , title, cellphone,phone ,still_work ,startwork_date ,endwork_date ,licence_expire_date , " +
                        " licence_car_type,carno1 ,carno2,temp_staff, cre_user, cre_datetime , upd_user , upd_datetime " +



                        ")" +
                        " VALUES ( " +
                        " :driverid, :name,:staffno,:id, :brdte, :title, :cellphone, :phone, :stillWork, :startworkDate, :endworkDate, :licenceExpireDate, " +
                        " :licenceCarType, :carno1,:carno2,:tempStaff, :creUser, :creDatetime , :updUser, :updDatetime " +
                        ")",

                new BeanPropertySqlParameterSource(eip07w010Case));
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<Eip07w010Case> queryDriver(Eip07w010Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select * From driver_base  Where ");
        if (StringUtils.isNotBlank(caseData.getName())){
            sql.append("  name=:name And  ");
        }
        if (StringUtils.isNotBlank(caseData.getDriveridDetail())){
            sql.append("  driverid=:driveridDetail And  ");
        }
        if ("A".equals(caseData.getStillWork())){//判斷是否查詢全部
            sql.append(" still_work= dbo.ufn_nvl('' , still_work) ");
        }else {
            sql.append(" still_work= :stillWork ");
        }
        sql.append(" Order by driverid, still_work ");
        SqlParameterSource params = new MapSqlParameterSource("stillWork",caseData.getStillWork())
                .addValue("name", caseData.getName())
                .addValue("driveridDetail", caseData.getDriveridDetail());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip07w010Case.class));
    }

    public List<Eip07w010Case> getDriverid() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT  t.driverid  from driver_base t  ");
        sql.append(" order by t.driverid desc ");
        SqlParameterSource params = new MapSqlParameterSource("","");
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip07w010Case.class));
    }

    public List<Eip07w010Case> getTempStaff() {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select distinct driverid, name  ");
        sql.append(" From driver_base ");
        SqlParameterSource params = new MapSqlParameterSource("","");
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip07w010Case.class));
    }

    @Override
    public int delete(Eip07w010Case caseData)  {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                        .append(" DELETE  from " ) .append(TABLE_NAME ).append( " WHERE driverid=:driverid ")
                        .append( " AND still_work ='Y' ").toString(),
                new BeanPropertySqlParameterSource(caseData));
    }
    @Override
    public int updateDriverBase(Eip07w010Case updateDate) {
        return getNamedParameterJdbcTemplate().update(
                new StringBuilder()
                        .append(" UPDATE ").append(TABLE_NAME)
                        .append(" SET  ")
                        .append(" driverid=:driverid, name=:name,staffno=:staffno,id=:id, brdte=:brdte, title=:title, cellphone=:cellphone,  ")
                        .append(" phone=:phone,still_work =:stillWork, startwork_date=:startworkDate, endwork_date=:endworkDate, ")
                        .append(" licence_expire_date=:licenceExpireDate, licence_car_type=:licenceCarType,carno1 =:carno1,carno2=:carno2, ")
                        .append(" temp_staff=:tempStaff, cre_user=:creUser, cre_datetime=:creDatetime ,upd_user=:updUser, upd_datetime=:updDatetime  ")
                        .append(" WHERE driverid=:driverid  ")
                        .toString(),
                new BeanPropertySqlParameterSource(updateDate));
    }


}