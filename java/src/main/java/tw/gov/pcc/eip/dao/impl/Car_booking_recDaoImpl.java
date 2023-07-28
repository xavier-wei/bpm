package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.Car_booking_recDao;
import tw.gov.pcc.eip.domain.Car_booking_rec;

/**
 * CAR_BOOKING_REC DaoImpl
 */
@DaoTable(Car_booking_recDao.TABLE_NAME)
@Repository
public class Car_booking_recDaoImpl extends BaseDao<Car_booking_rec> implements Car_booking_recDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" t.CARNO1, t.CARNO2, t.USING_DATE, t.APPLYID, t.USING_REC, t.COMBINE_MK, t.COMBINE_REASON, t.COMBINE_APPLYID, t.CRE_USER, t.CRE_DATETIME, ")
                .append(" t.UPD_USER, t.UPD_DATETIME ")
                .toString();
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param car_booking_rec 條件
     * @return 唯一值
     */
    @Override
    public Car_booking_rec selectDataByPrimaryKey(Car_booking_rec car_booking_rec) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM " + TABLE_NAME + " t WHERE t.APPLYID = :applyid ");
        List<Car_booking_rec> list = getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertySqlParameterSource(car_booking_rec), BeanPropertyRowMapper.newInstance(Car_booking_rec.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 根據key選取資料
     *
     * @param applyid APPLYID
     * @return 唯一值
     */
    @Override
    public Car_booking_rec selectByKey(String applyid) {
        Car_booking_rec car_booking_rec = new Car_booking_rec();
        car_booking_rec.setApplyid(applyid);
        return selectDataByPrimaryKey(car_booking_rec);
    }

    /**
     * 根據key刪除資料
     *
     * @param car_booking_rec 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Car_booking_rec car_booking_rec) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " t WHERE t.APPLYID = :applyid ",
                new BeanPropertySqlParameterSource(car_booking_rec));
    }

    /**
     * 根據key更新資料
     *
     * @param car_booking_rec 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Car_booking_rec car_booking_rec) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " t SET " +
                        " t.CARNO1 = :carno1, t.CARNO2 = :carno2, t.USING_DATE = :using_date, t.USING_REC = :using_rec, t.COMBINE_MK = :combine_mk, " +
                        " t.COMBINE_REASON = :combine_reason, t.COMBINE_APPLYID = :combine_applyid, t.CRE_USER = :cre_user, t.CRE_DATETIME = :cre_datetime, t.UPD_USER = :upd_user, " +
                        " t.UPD_DATETIME = :upd_datetime" +
                        " WHERE t.APPLYID = :applyid ",
                new BeanPropertySqlParameterSource(car_booking_rec));
    }

    /**
     * 新增一筆資料
     *
     * @param car_booking_rec 新增資料
     */
    @Override
    public int insert(Car_booking_rec car_booking_rec) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " CARNO1, CARNO2, USING_DATE, APPLYID, USING_REC, COMBINE_MK, COMBINE_REASON, COMBINE_APPLYID, CRE_USER, CRE_DATETIME, " +
                        " UPD_USER, UPD_DATETIME" +
                        ")" +
                        " VALUES ( " +
                        " :carno1, :carno2, :using_date, :applyid, :using_rec, :combine_mk, :combine_reason, :combine_applyid, :cre_user, :cre_datetime, " +
                        " :upd_user, :upd_datetime" +
                        ")",
                new BeanPropertySqlParameterSource(car_booking_rec));
    }


}