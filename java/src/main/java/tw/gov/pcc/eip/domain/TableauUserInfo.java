package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.TableauUserInfoDao;

import java.time.LocalDateTime;

/**
 * Tableau使用者訂閱資訊
 */
@Table(TableauUserInfoDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class TableauUserInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 使用者代碼
     */
    @PkeyField
    @LogField
    private String user_id;

    /**
     * 部門代碼
     */
    @LogField
    private String department_id;

    /**
     * 儀表板圖表ID
     */
    @LogField
    private String dashboard_fig_id;

    /**
     * 建立時間
     */
    @LogField
    private LocalDateTime create_time;

    /**
     * 更新時間
     */
    @LogField
    private LocalDateTime update_time;
}
