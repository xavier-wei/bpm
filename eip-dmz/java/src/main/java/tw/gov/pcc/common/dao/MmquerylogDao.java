package tw.gov.pcc.common.dao;

import tw.gov.pcc.common.domain.Mmquerylog;

public interface MmquerylogDao {

    /**
     * 紀錄 Query Log
     *
     * @param logData <code>Mmquerylog</code> 物件
     */
    void insertData(Mmquerylog logData);

}
