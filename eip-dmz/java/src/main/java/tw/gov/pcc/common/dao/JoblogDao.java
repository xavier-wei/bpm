package tw.gov.pcc.common.dao;

import tw.gov.pcc.eip.domain.Joblog;

import java.util.List;

/**
 * 批次執行QC記錄檔 DAO
 *
 * @author tunhao.hsu
 */
public interface JoblogDao {

    /**
     * 批次執行QC記錄檔
     */
    public static final String TABLE_NAME = "JOBLOG";

    List<Joblog> findByFields(String begin, String end, String jobId, String jobNo);

}
