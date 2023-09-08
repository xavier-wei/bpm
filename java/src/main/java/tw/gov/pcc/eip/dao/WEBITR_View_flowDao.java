package tw.gov.pcc.eip.dao;

import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.eip.domain.View_flow;

import java.math.BigDecimal;

/**
 * webitr.dbo.view_flow Dao
 */
public interface WEBITR_View_flowDao {

    String TABLE_NAME = "webitr.dbo.view_flow";

    @SkipLog
    BigDecimal selectCountByNext_card_id(View_flow viewFlow);
}
