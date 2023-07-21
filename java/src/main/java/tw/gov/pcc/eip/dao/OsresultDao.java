package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Osresult;

import java.util.List;

/**
 * 意見調查結果資料Dao
 * @Author Weith
 */
public interface OsresultDao {

    public static final String TABLE_NAME = "OSRESULT";

    public Osresult findByPk(String osformno, Integer wriseq);

    public int insertData(Osresult t);

    public int updateData(Osresult t, String osformno, Integer wriseq);

    public int deleteData(String osformno, Integer wriseq);

    public Osresult getDataByCreuser(String osformno, String creuser);

    /**
     * 取得全部資料
     * @return
     */
    public List<Osresult> getAll();

    /**
     * 取得最大流水號
     * @return
     */
    public Integer getMaximumWriseq(String osformno);
}
