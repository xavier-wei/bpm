package tw.gov.pcc.eip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.WEBITR_View_oup_unitDao;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 單位資料檔
 *
 * @author swho
 */
@Table(WEBITR_View_oup_unitDao.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View_oup_unit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ORG_ID
     */
    @PkeyField
    @LogField
    private String org_id;

    /**
     * UNIT_ID
     */
    @PkeyField
    @LogField
    private String unit_id;

    /**
     * FUNIT_ID
     */
    @LogField
    private String funit_id;

    /**
     * NAME
     */
    @LogField
    private String name;

    /**
     * SEQ
     */
    @LogField
    private String seq;

    /**
     * UHEAD_POS_ID
     */
    @LogField
    private String uhead_pos_id;

    /**
     * PLEVEL
     */
    @LogField
    private String plevel;

    /**
     * UNIT_TYPE
     */
    @LogField
    private String unit_type;

    /**
     * IS_WHIP
     */
    @LogField
    private BigDecimal is_whip;

}
