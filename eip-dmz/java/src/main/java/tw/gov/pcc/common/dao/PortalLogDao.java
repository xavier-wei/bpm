package tw.gov.pcc.common.dao;

import tw.gov.pcc.common.domain.PortalLog;

public interface PortalLogDao {

    /**
     * 紀錄 Portal Log
     *
     * @param logData <code>PortalLog</code> 物件
     * @return <code>PORTAL_LOG.SYS_ID</code>
     */
    String insertData(PortalLog logData);

    String getSeq();
}
