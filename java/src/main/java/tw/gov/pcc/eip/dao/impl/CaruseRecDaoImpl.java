package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.CaruseRecDao;
import tw.gov.pcc.eip.domain.CaruseRec;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(CaruseRecDao.TABLE_NAME)
@Repository
public class CaruseRecDaoImpl extends BaseDao<CaruseRec> implements CaruseRecDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.carno1 , t.carno2, t.applyid, t.use_date, t.use_time_s, t.use_time_e, t.milage_start, "
        + " t.milage_end, t.milage, t.gas_used, t.cre_user, t.cre_datetime "
        + " t.upd_user, t.upd_datetime ";
    }
    
	/**
	 * 新增一筆資料
	 *
	 * @param caruseRec 新增資料
	 */
	@Override
	public int insert(CaruseRec caruseRec) {
		return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME + "("
				+ " carno1, carno2, applyid, use_date, use_time_s, use_time_e, milage_start, "
				+ " milage_end, milage, gas_used, driver_time_s, driver_time_e, drive_road, "
				+ " cre_user, cre_datetime, upd_user, upd_datetime )" 
				+ " VALUES ( "
				+ " :carno1, :carno2, :applyid, :use_date, :use_time_s, :use_time_e, :milage_start, "
				+ " :milage_end, :milage, :gas_used, :driver_time_s, :driver_time_e, :drive_road, "
				+ " :cre_user, :cre_datetime, :upd_user, :upd_datetime )",

				new BeanPropertySqlParameterSource(caruseRec));
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
                " FROM " + TABLE_NAME + " t WHERE t.carno1 = :carno1 "+ " AND t.carno2 = :carno2 ";
        List<CaruseRec> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(caruseRec), BeanPropertyRowMapper.newInstance(CaruseRec.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<Eip07w010Case> quaryCaruseRec(Eip07w010Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select   *  from CARUSE_REC ");
            sql.append(" WHERE carno1  = :carno1 ");
            sql.append(" AND carno2 =:carno2 ");
            sql.append(" Order by use_date, applyid ");
        SqlParameterSource params = new MapSqlParameterSource("carno1",caseData.getCarno1())
                .addValue("carno2", caseData.getCarno2());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip07w010Case.class));
    }
    
    /**
     * 根據applyid查詢
     *
     * @param CaruseRec 條件
     * @return 唯一值
     */
    @Override
    public CaruseRec selectDataByApplyid(CaruseRec caruseRec) {
        String sql = "SELECT * FROM " + TABLE_NAME + " t WHERE t.applyid = :applyid ";
        List<CaruseRec> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(caruseRec), BeanPropertyRowMapper.newInstance(CaruseRec.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

}