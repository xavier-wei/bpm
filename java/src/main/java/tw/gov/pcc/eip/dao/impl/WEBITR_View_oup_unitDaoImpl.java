package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.ItrBaseDao;
import tw.gov.pcc.eip.dao.WEBITR_View_flowDao;
import tw.gov.pcc.eip.dao.WEBITR_View_oup_unitDao;
import tw.gov.pcc.eip.domain.View_oup_unit;

/**
 * webitr.dbo.view_flow DaoImpl
 */
@DaoTable(WEBITR_View_flowDao.TABLE_NAME)
@Repository
public class WEBITR_View_oup_unitDaoImpl extends ItrBaseDao<View_oup_unit> implements WEBITR_View_oup_unitDao {

  /**
   * 根據key選取資料(底層用)
   *
   * @param view_oup_unit 條件
   * @return 唯一值
   */
  @Override
  public View_oup_unit selectDataByPrimaryKey(View_oup_unit view_oup_unit) {
    return null;
  }
  @Override
  @SkipLog
  public List<View_oup_unit> selectAll() {
    String sql = "SELECT "
            + " * "
            + " FROM " + TABLE_NAME + " t";
    return getNamedParameterJdbcTemplate()
            .query(sql,new HashMap<>(),BeanPropertyRowMapper.newInstance(View_oup_unit.class));
  }
}
