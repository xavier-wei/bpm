package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.msg.cases.Eip01w020PageCase;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;

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
    public List<Eip01wPopCase> getEip01w030LatestDataList();

    /**
     * 取得畫面條件 查詢的公告事項
     * 
     * @param deptId  登入者部門代號
     * @param msgtype 訊息類別
     * @param subject 主旨
     * @return
     */
    public List<Eip01wPopCase> getEip01w030DataList(String deptId, String msgtype, String subject);

    /**
     * 取得單筆fseq pop視窗明細
     * 
     * @param fseq
     * @param scodekind 1:公告事項4:下載專區5:輿情專區
     * @return
     */
    public Eip01wPopCase getEip01wDetail(String fseq, String scodekind);

    /**
     * 取得輿情專區初始資料 (依登入者部門查詢)
     * 
     * @param dept
     * @return
     */
    public List<Eip01wPopCase> getEip01w050DataList(String dept);

    /**
     * 取得單筆輿情資料明細
     * 
     * @param fseq
     * @return
     */
    public Eip01wPopCase getEip01w050Detail(String fseq);

    /**
     * 取得已上架的文章
     * 
     * @param attr 6:單位簡介 7:業務資訊
     * @param dept 登入者部門 聯絡人
     * @return
     */
    public List<Msgdata> getMcontentWithStatus4(String attr, String dept);

    /**
     * 下載專區 - 路徑查詢
     * 
     * @param dept
     * @param path
     * @return
     */
    public List<Msgdata> getbyPath(String dept, String path);

    /**
     * 下載專區 - 依關鍵字查詢
     * 
     * @param dept
     * @param keyword
     * @return
     */
    public List<Msgdata> getbyKeyword(String dept, String keyword);
}
