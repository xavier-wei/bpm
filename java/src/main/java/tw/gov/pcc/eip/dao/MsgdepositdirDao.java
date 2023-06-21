package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;

/**
 * 訊息檔案存放目錄表
 * 
 * @author vita
 *
 */
@DaoTable(MsgdepositdirDao.TABLE_NAME)
@Repository
public interface MsgdepositdirDao {

    public static final String TABLE_NAME = "MSGDEPOSITDIR";

}
