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
                        " with cpap as (" +
                                "select top 1 * from  cpap.dbo.view_cpape05m v where v.pecard = :next_card_id " +
                                "), flow as(" +
                                "select * from webitr.dbo.view_flow o " +
                                "where o.next_card_id = (select  PECARD  from cpap )" +
                                "), flow_his as(" +
                                "select * from webitr.dbo.view_flowhistory v where v.deputyid = (select PEIDNO  from cpap )" +
                                ")" +
                                "select (select COUNT(1) from flow) + (select COUNT(1) from flow_his) as cnt ",
                        new BeanPropertySqlParameterSource(viewFlow),
                        BigDecimal.class);
    }
}
