package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.View_out_unitDao;

/**
 * 單位資料檔
 *
 * @author swho
 */
@Table(View_out_unitDao.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View_oup_unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @LogField
    private String org_id;

    @LogField
    private String unit_id;

    @LogField
    private String funit_id;

    @LogField
    private String name;

    @LogField
    private String seq;
}
