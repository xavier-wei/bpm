package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.ItrBaseDao;
import tw.gov.pcc.eip.dao.WEBITR_View_flowDao;
import tw.gov.pcc.eip.domain.View_flow;

import java.math.BigDecimal;

/**
 * webitr.dbo.view_flow DaoImpl
 */
@DaoTable(WEBITR_View_flowDao.TABLE_NAME)
@Repository
public class WEBITR_View_flowDaoImpl extends ItrBaseDao<View_flow> implements WEBITR_View_flowDao {

    /**
     * 根據key選取資料(底層用)
     *
     * @param viewFlow 條件
     * @return 唯一值
     */
    @Override
    public View_flow selectDataByPrimaryKey(View_flow viewFlow) {
        return null;
    }

    @Override
    @SkipLog
    public BigDecimal selectCountByNext_card_id(View_flow viewFlow) {
        return getNamedParameterJdbcTemplate()
                .queryForObject(
                        "WITH cpap AS" +
                                " (SELECT top 1 *" +
                                "    FROM cpap.dbo.view_cpape05m v" +
                                "   WHERE v.pecard = :next_card_id)," +
                                "flow AS" +
                                " (SELECT *" +
                                "    FROM webitr.dbo.view_flow" +
                                "   WHERE next_card_id = :next_card_id)," +
                                "flow_his AS" +
                                " (SELECT *" +
                                "    FROM webitr.dbo.view_flow" +
                                "   WHERE next_card_id IN (SELECT v.card_id" +
                                "                            FROM webitr.dbo.view_flowhistory v" +
                                "                           WHERE v.deputyid = (SELECT peidno" +
                                "                                                 FROM cpap)" +
                                "                             AND format(getdate(), 'yyyy-MM-dd HH:mm ') BETWEEN" +
                                "                                 begintime AND endtime))," +
                                "flow_d_a AS" +
                                " (SELECT *" +
                                "    FROM webitr.dbo.view_deputy_active" +
                                "   WHERE ((is_indefinite = '0' AND" +
                                "         format(getdate(), 'yyyyMMddHHmm') BETWEEN" +
                                "         CAST(CAST(dateb AS INT) + 19110000 AS nvarchar) + timeb AND" +
                                "         CAST(CAST(datee AS INT) + 19110000 AS nvarchar) + timee) OR" +
                                "         is_indefinite = '1')" +
                                "     AND deputy_id = (SELECT peidno" +
                                "                        FROM cpap))" +
                                "SELECT (SELECT COUNT(1) FROM flow) + " +
                                "(SELECT COUNT(1) FROM flow_his) +" +
                                "(SELECT COUNT(1) FROM flow_d_a) AS cnt",
                        new BeanPropertySqlParameterSource(viewFlow),
                        BigDecimal.class);
    }
}
