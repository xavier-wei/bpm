package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Ositem;

import java.util.List;

/**
 * 意見調查問題選項Dao
 * @Author Weith
 */
public interface OsitemDao {

    public static final String TABLE_NAME = "OSITEM";

    public Ositem findByPk(String osformno, Integer iseqno);

    public int insertData(Ositem t);

    public int updateData(Ositem t, String osformno, Integer iseqno);

    public int deleteData(String osformno, Integer iseqno);

    public int deleteDataByQseqno(String osformno, Integer qseqno);

    public int deleteByOsformnoList(List<String> osformnoList);

    /**
     * 刪除所選選項
     * @param osformno
     */
    public int deleteByOsformnoAndIseqnoList(String osformno, List<String> iseqno);

    /**
     * 取得全部資料
     * @return
     */
    public List<Ositem> getAll();

    /**
     * 取得本月最大流水號
     * @return
     */
    public String getMaximumIseqno(String osformno);

    /**
     * 取得全部資料
     * @return
     */
    public List<Ositem> getAllByOsformno(String osformno);

    /**
     * 取得資料根據傳入序號list
     * @return
     */
    public List<Ositem> getAllByIseqnoAndQseqnoList(String osformno, List<String> iseqnoList, List<String> qseqnoList);

    /**
     * 依表單編號及問題流水號取得資料
     * @return
     */
    public List<Ositem> getItemsByOsformnoAndQseqno(String osformno, String qseqno);

    /**
     * 依表單編號、問題流水號及選項序號取得資料
     * @param osformno
     * @param qseqno
     * @param itemseq
     * @return
     */
    public Ositem getSingleOptionData(String osformno, String qseqno, String itemseq);

    /**
     * 依表單編號、問題流水號、目的選項序號及更新種類將其他序號加一
     * @param osformno
     * @param qseqno
     * @param targeitemseq
     * @param isbehind
     * @return
     */
    public int updateBatchItemseq(String osformno, String qseqno, String itemseq, String targeitemseq, boolean isbehind);

    /**
     * 取得全部題目資料(包含選擇題、填空題)
     * @return
     */
    public List<Ositem> getTopicByOsformno(String osformno);
}
