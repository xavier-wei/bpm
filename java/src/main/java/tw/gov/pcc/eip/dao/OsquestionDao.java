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

    /**
     * 取得全部資料
     * @return
     */
    public List<Osquestion> getAll();

    /**
     * 取得本月最大流水號
     * @return
     */
    public String getMaximumQseqno(String osformno);
}
