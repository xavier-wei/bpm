package tw.gov.pcc.eip.dao;

import java.util.List;

import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.eip.domain.View_oup_unit;

/**
 * 提供待批核的表單資料 Dao
 */
public interface View_out_unitDao {

    String TABLE_NAME = "view_oup_unit";

    @SkipLog
    List<View_oup_unit> selectAll();
  
}
