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

    public Msgdeposit findbyPk(String fseq, String seq);

    /**
     * 取得檔案清單
     * 
     * @param fseq
     * @return
     */
    public List<Msgdeposit> findbyfseq(List<String> fseq);

    /**
     * 取得檔案清單
     * 
     * @param fseq
     * @param seq
     * @return
     */
    public List<Msgdeposit> findbyFseqSeq(String fseq, List<String> seq);

    /**
     * 取得該fseq下檔案總數與最大序號
     * 
     * @param fseq
     * @param filetype
     * @return
     */
    public Msgdeposit getCnt(String fseq, String filetype);
}
