package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.TableauDashboardInfoDao;
import tw.gov.pcc.eip.dao.TableauDepartmentInfoDao;

import java.time.LocalDateTime;

/**
 * Tableau儀錶板資訊
 */
@Table(TableauDashboardInfoDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class TableauDashboardInfo {


    private static final long serialVersionUID = 1L;

    /**
     * 儀表板主題ID
     */
    @LogField
    private String dashboard_topic_id;

    /**
     * 儀表板主題名稱
     */
    @LogField
    private String dashboard_topic_nm;

    /**
     * 儀表板圖表ID
     */
    @LogField
    private String dashboard_fig_id;

    /**
     * 儀表板圖表名稱
     */
    @LogField
    private String dashboard_fig_nm;

    /**
     * 儀表板圖表資料夾
     */
    @LogField
    private String dashboard_fig_folder;

    /**
     * 儀表板圖表檔案名稱
     */
    @LogField
    private String dashboard_fig_file_nm;

    /**
     * 儀表板URL
     */
    @LogField
    private String dashboard_url;

    /**
     * 儀表板主題類型代碼
     */
    @LogField
    private String dashboard_topic_type_cd;

    /**
     * 儀表板主題類型名稱
     */
    @LogField
    private String dashboard_topic_type_nm;

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
