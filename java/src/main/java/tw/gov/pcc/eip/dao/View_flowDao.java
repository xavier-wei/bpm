package tw.gov.pcc.eip.dao;

import java.math.BigDecimal;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.eip.domain.View_flow;

/** 提供待批核的表單資料 Dao */
public interface View_flowDao {

  String TABLE_NAME = "view_flow";

  @SkipLog
  BigDecimal selectCountByNext_card_id(View_flow viewFlow);
}
