package tw.gov.pcc.eip.dao;

import java.util.List;

import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.eip.domain.View_oup_unit;

/**
 * webitr.dbo.view_oup_unit Dao
 */
public interface WEBITR_View_oup_unitDao {

    String TABLE_NAME = "webitr.dbo.view_oup_unit";

    @SkipLog
    List<View_oup_unit> selectAll();
  
}
