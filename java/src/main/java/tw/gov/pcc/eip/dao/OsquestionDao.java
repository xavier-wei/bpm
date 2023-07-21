package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Osquestion;

import java.util.List;

/**
 * 意見調查問題資料Dao
 * @Author Weith
 */
public interface OsquestionDao {

    public static final String TABLE_NAME = "OSQUESTION";

    public Osquestion findByPk(String osformno, Integer qseqno);

    public int insertData(Osquestion t);

    public int updateData(Osquestion t, String osformno, Integer qseqno);

    public int deleteData(String osformno, Integer qseqno);

    public int deleteByOsformnoList(List<String> osformnoList);

    /**
     * 取得全部資料
     * @return
     */
    public List<Osquestion> getAllByOsformno(String osformno);

    /**
     * 取得最大流水號
     * @return
     */
    public Integer getMaximumQseqno(String osformno);

    /**
     * 依表單編號取得資料
     * @return
     */
    public List<Osquestion> getListByOsformno(String osformno);

    /**
     * 依表單編號取得所有題目資料
     * @return
     */
    public List<Osquestion> getAllQuestionByOsformno(String osformno);

    /**
     * 依表單編號及部分標題序號取得資料
     * @return
     */
    public List<Osquestion> getQuestionsByOsformnoAndSectitleseq(String osformno, String sectitleseq);

    /**
     * 依表單編號及多個部分標題序號取得資料
     * @return
     */
    public List<Osquestion> getQuestionsByOsformnoAndSectitleseq(String osformno, List<String> sectitleseq);

    /**
     * 刪除所選部分標題
     * @param osformno
     */
    public int deleteByOsformnoAndSectitleseqList(String osformno, List<String> sectitleseq);

    /**
     * 刪除所選題目
     * @param osformno
     */
    public int deleteByOsformnoAndQseqnoList(String osformno, List<String> qseqno);

    /**
     * 依表單編號與部分標題序號取得單一部分資料
     * @return
     */
    public Osquestion getSinglePartData(String osformno, String sectitleseq);

    /**
     * 依表單編號、部分標題序號與題目序號取得單一題目資料
     * @return
     */
    public Osquestion getSingleQuestionData(String osformno, String sectitleseq, String topicseq);

    /**
     * 依表單編號、問題流水號取得單一問題資料
     * @return
     */
    public Osquestion getSingleQuestionData(String osformno, String qseqno);

    /**
     * 依表單編號、部分標題序號、目的標題序號及更新種類將其他部分標題序號加一
     *
     * @param osformno
     * @param sectitleseq
     * @param targetsectitleseq
     * @param isbehind
     */
    public int updateBatchSectitleseq(String osformno, String sectitleseq, String targetsectitleseq, boolean isbehind);

    /**
     * 依表單編號、部分標題序號、題目序號及更新種類將其他題目序號加一
     *
     * @param osformno
     * @param sectitleseq
     * @param topicseq
     * @param targetTopicseq
     * @param isbehind
     */
    public int updateBatchTopicseq(String osformno, String sectitleseq, String topicseq, String targetTopicseq, boolean isbehind);
}
