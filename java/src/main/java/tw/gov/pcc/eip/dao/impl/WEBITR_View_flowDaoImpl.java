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
                        "with cpap as (  " +
                                "select top 1 * from  cpap.dbo.view_cpape05m v where v.pecard = :next_card_id   " +
                                "), flow as( " +
                                "select * from webitr.dbo.view_flow where next_card_id = :next_card_id    " +
                                "), flow_d as( " +
                                "select * from webitr.dbo.view_flow where deputy_card_id = :next_card_id   and format(getdate(), 'yyyy-MM-dd HH:mm ') between begintime  and endtime  " +
                                "), flow_his as(  " +
                                "select * from webitr.dbo.view_flowhistory v where v.deputyid = (select PEIDNO  from cpap )  and format(getdate(), 'yyyy-MM-dd HH:mm ') between begintime  and endtime   " +
                                "), flow_d_a as( " +
                                "select * from webitr.dbo.view_deputy_active where " +
                                "((is_indefinite = '0' AND " +
                                "format(getdate(),'yyyyMMddHHmm') between  " +
                                "cast(cast(dateb as int)+  19110000 as NVARCHAR ) + timeb  and  " +
                                "cast(cast(datee as int)+  19110000 as NVARCHAR ) + timee ) " +
                                "OR is_indefinite = '1') " +
                                "and deputy_id = (select PEIDNO  from cpap ) " +
                                ") " +
                                "select (select COUNT(1) from flow) +  " +
                                "(select COUNT(1) from flow_d)+  " +
                                "(select COUNT(1) from flow_his)+ " +
                                "(select COUNT(1) from flow_d_a)as cnt  ",
                        new BeanPropertySqlParameterSource(viewFlow),
                        BigDecimal.class);
    }
}
