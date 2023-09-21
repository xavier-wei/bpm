package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.CaruseRecDao;
import tw.gov.pcc.eip.dao.GasRecDao;
import tw.gov.pcc.eip.domain.CaruseRec;
import tw.gov.pcc.eip.domain.GasRec;

import java.util.List;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(CaruseRecDao.TABLE_NAME)
@Repository
public class CaruseRecDaoImpl extends BaseDao<CaruseRec> implements CaruseRecDao {

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
     * @param CaruseRec 條件
     * @return 唯一值
     */
    @Override
    public CaruseRec selectDataByPrimaryKey(CaruseRec caruseRec) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.driverid = :driverid ";//這要改
        List<CaruseRec> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(caruseRec), BeanPropertyRowMapper.newInstance(CaruseRec.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }



}