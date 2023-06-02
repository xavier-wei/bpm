package tw.gov.pcc.common.dao;

import tw.gov.pcc.common.domain.Mmaplog;
import java.util.List;

public interface MmaplogDao {

    /**
     * 紀錄 AP Log (單筆)
     *
     * @param logData <code>Mmaplog</code> 物件
     */
    void insertData(Mmaplog logData);

    /**
     * 紀錄 AP Log (多筆)
     *
     * @param logList 內含 <code>Mmaplog</code> 物件的 List
     */
    void insertData(List<Mmaplog> logList);

}
