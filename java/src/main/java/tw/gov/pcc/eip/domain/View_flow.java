package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.View_cpape05mDao;

/**
 * 提供待批核的表單資料
 *
 * @author swho
 */
@Table(View_cpape05mDao.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View_flow implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /** 下一個批核者員工編號 */
  @LogField
  private String next_card_id;
}
