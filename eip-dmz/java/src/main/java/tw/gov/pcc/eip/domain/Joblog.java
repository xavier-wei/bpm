package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.common.dao.JoblogDao;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批次執行QC記錄檔
 *
 * @author tunhao.hsu
 */
@Table(JoblogDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Joblog implements Serializable {
    private static final long serialVersionUID = 1L;
    @PkeyField("JOB_NO")
    @LogField("JOB_NO")
    private String job_no; // 工作代號
    @PkeyField("SEQ_NO")
    @LogField("SEQ_NO")
    private BigDecimal seq_no; // 序號
    @LogField("JOB_ID")
    private String job_id; // 程式代號
    @LogField("JOB_NAME")
    private String job_name; // 程式名稱
    @LogField("EXE_TMSTMP")
    private String exe_tmstmp; // 執行時間
    @LogField("STEP")
    private String step; // 處理階段
    @LogField("STEPINFO")
    private String stepinfo; // 處理階段資訊
    @LogField("MEMO")
    private String memo; // 備註說明

}
