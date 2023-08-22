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
import tw.gov.pcc.eip.dao.GasRecDao;
import tw.gov.pcc.eip.domain.CarBase;
import tw.gov.pcc.eip.domain.GasRec;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.orderCar.cases.Eip07w010Case;

import java.util.List;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(GasRecDao.TABLE_NAME)
@Repository
public class GasRecDaoImpl extends BaseDao<GasRec> implements GasRecDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.carno1 , t.carno2, t.fuel_date, t.fuel_time, t.gas_money, t.gas_amount, t.cre_user, "
        + " t.cre_datetime, t.upd_user, t.upd_datetime ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param gasRec 條件
     * @return 唯一值
     */
    @Override
    public GasRec selectDataByPrimaryKey(GasRec gasRec) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.carno1 = :carno1 "+ " AND t.carno2 = :carno2 ";
        List<GasRec> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(gasRec), BeanPropertyRowMapper.newInstance(GasRec.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     *
     *
     * @param
     */
    @Override
    public List<Eip07w010Case> quaryGasRec(Eip07w010Case caseData) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select   *  from GAS_REC ");
            sql.append(" WHERE carno1  = :carno1 ");
            sql.append(" AND carno2 =:carno2 ");
            sql.append(" Order by fuel_date ");
        SqlParameterSource params = new MapSqlParameterSource("carno1",caseData.getCarno1())
                .addValue("carno2", caseData.getCarno2());
        return getNamedParameterJdbcTemplate().query(sql.toString(),params, BeanPropertyRowMapper.newInstance(Eip07w010Case.class));
    }

    /**
     * 新增一筆資料
     *
     * @param gasRec 新增資料
     */
    @Override
   public int insert(GasRec gasRec) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " carno1, carno2, fuel_date, fuel_time, gas_money, gas_amount, cre_user, " +
                        " cre_datetime, upd_user, upd_datetime" +
                        ")" +
                        " VALUES ( " +
                        " :carno1, :carno2, :fuel_date, :fuel_time, :gas_money, :gas_amount, :cre_user, " +
                        " :cre_datetime, :upd_user, :upd_datetime" +
                        ")",
                new BeanPropertySqlParameterSource(gasRec));
    }

}