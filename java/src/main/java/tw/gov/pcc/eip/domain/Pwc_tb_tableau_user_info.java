package tw.gov.pcc.eip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.Pwc_tb_tableau_user_infoDao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PWC_TB_TABLEAU_USER_INFO
 *
 * @author swho
 */
@Table(Pwc_tb_tableau_user_infoDao.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Pwc_tb_tableau_user_info implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * USER_ID
     */
    @PkeyField
    @LogField
    private String user_id;

    /**
     * DASHBOARD_FIG_ID
     */
    @PkeyField
    @LogField
    private String dashboard_fig_id;

    /**
     * DEPARTMENT_ID
     */
    @LogField
    private String department_id;

    /**
     * SORT_ORDER
     */
    @LogField
    private BigDecimal sort_order;

    /**
     * CREATE_TIME
     */
    @LogField
    private LocalDateTime create_time;

    /**
     * UPDATE_TIME
     */
    @LogField
    private LocalDateTime update_time;

}