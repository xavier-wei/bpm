package tw.gov.pcc.eip.dao.impl;

import java.math.BigDecimal;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.ItrBaseDao;
import tw.gov.pcc.eip.dao.View_flowDao;
import tw.gov.pcc.eip.domain.View_flow;

/** 人員資料表 DaoImpl */
@DaoTable(View_flowDao.TABLE_NAME)
@Repository
public class View_flowDaoImpl extends ItrBaseDao<View_flow> implements View_flowDao {

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
            " select count(1) cnt from view_flow where next_card_id = :next_card_id ",
            new BeanPropertySqlParameterSource(viewFlow),
            BigDecimal.class);
  }
}
