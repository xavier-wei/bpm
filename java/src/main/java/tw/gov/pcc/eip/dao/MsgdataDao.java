package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.msg.cases.Eip01w020PageCase;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.msg.cases.Eip01w050Case;

/**
 * 訊息上稿
 * 
 * @author vita
 *
 */
@DaoTable(MsgdataDao.TABLE_NAME)
@Repository
public interface MsgdataDao {

    public static final String TABLE_NAME = "MSGDATA";

    public int insert(Msgdata m);

    public int update(Msgdata m, String fseq);

    public int delete(Msgdata m);

    public Msgdata findbyfseq(String fseq);

    public List<Msgdata> findbyCreatidPagetype(String creatid, String pagetype, String subject, String status);

    public int updateStatus(List<String> fseq, String status);

    /**
     * 取號
     * 
     * @return
     */
    public String getNextFseq();

    /**
     * 取得訊息篇數統計
     * 
     * @param msgtype     分類名稱 (訊息類別)
     * @param attributype 屬性
     * @param subject     主旨/連結網址
     * @param contactunit 聯絡單位
     * @param creatid     建立人員
     * @param updid       更新人員
     * @param releasedts  上架時間起
     * @param releasedte  上架時間迄
     * @return
     */
    public List<Eip01w020PageCase> getEip01w020DataList(String msgtype, String attributype, String subject,
            String contactunit, String creatid, String updid, String releasedts, String releasedte);

    /**
     * 取得畫面初始 最新的公告事項
     * 
     * @return
     */
    public List<Eip01w030Case.Detail> getEip01w030LatestDataList();

    /**
     * 取得畫面條件 查詢的公告事項
     * 
     * @param msgtype
     * @param subject
     * @return
     */
    public List<Eip01w030Case.Detail> getEip01w030DataList(String msgtype, String subject);

    /**
     * 取得單筆公告事項明細
     * 
     * @param fseq
     * @return
     */
    public Eip01w030Case.Detail getEip01w030Detail(String fseq);

    /**
     * 取得輿情專區初始資料 (依登入者部門查詢)
     * 
     * @param dept
     * @return
     */
    public List<Eip01w050Case.Detail> getEip01w050DataList(String dept);

    /**
     * 取得單筆輿情資料明細
     * 
     * @param fseq
     * @return
     */
    public Eip01w050Case.Detail getEip01w050Detail(String fseq);
}
