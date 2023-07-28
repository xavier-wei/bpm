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

    /**
     * 訊息上稿 查詢
     * 
     * @param creatid
     * @param pagetype
     * @param subject
     * @param status
     * @param attributype
     * @return
     */
    public List<Msgdata> findbyCreatidPagetype(String creatid, String pagetype, String subject, String status,
            String attributype);

    /**
     * 更新狀態
     * 
     * @param fseq
     * @param status
     * @return
     */
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
     * 下載專區 - 路徑查詢
     * 
     * @param dept
     * @param path
     * @return
     */
    public List<Msgdata> getEip01w040byPath(String dept, String path);
    
    /**
     * 下載專區 - 依關鍵字查詢
     * 
     * @param dept
     * @param keyword
     * @return
     */
    public List<Msgdata> getEip01w040byKeyword(String dept, String keyword);

    /**
     * 取得輿情專區初始資料 (依登入者部門查詢)
     * 
     * @param dept
     * @return
     */
    public List<Eip01wPopCase> getEip01w050DataList(String dept);

    /**
     * 取得該聯絡單位(之單位簡介或業務資訊)已上架的文章內容
     * 
     * @param attr        6:單位簡介 7:業務資訊
     * @param contactunit 聯絡單位
     * @return
     */
    public List<Msgdata> getStatus4Mcontent(String attr, String contactunit);

    /**
     * 取得該連絡單位(之單位簡介或業務資訊)有效的文章總數
     * 
     * @param attr        6:單位簡介 7:業務資訊
     * @param contactunit 聯絡單位
     * @return
     */
    public int getEffectiveCount(String attr, String contactunit);
}
