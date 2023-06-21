package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Msgdeposit;

/**
 * 訊息存放檔案表
 * 
 * @author vita
 *
 */
@DaoTable(MsgdepositDao.TABLE_NAME)
@Repository
public interface MsgdepositDao {

    public static final String TABLE_NAME = "MSGDEPOSIT";

    public int insert(Msgdeposit m);

    public int update(Msgdeposit m, String fseq, String seq, String filetype);

    public int delete(Msgdeposit m);

    public Msgdeposit findbyfseq(String fseq, String seq, String filetype);

    public List<Msgdeposit> findbyfseqfiletype1(String fseq);
}
