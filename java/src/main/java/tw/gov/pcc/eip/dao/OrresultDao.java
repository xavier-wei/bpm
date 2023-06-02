package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Orresult;

import java.util.List;

/**
 * 線上報名結果資料Dao
 * @Author Weith
 */
public interface OrresultDao {

    public static final String TABLE_NAME = "ORRESULT";

    public Orresult findByPk(String orformno, String seqno);

    public int insertData(Orresult t);

    public int updateData(Orresult t, String orformno, String seqno);

    public int deleteData(Orresult t, String orformno, String seqno);

    /**
     * 依表單編號與審核結果取得資料
     * @param orformno
     * @param isPass
     * @return
     */
    public List<Orresult> getDataByOrformno(String orformno, String isPass);

    /**
     * 進階查詢報名結果
     * @param orformno
     * @param name
     * @param status
     * @return
     */
    public List<Orresult> getDataByMultiCondition(String orformno, String name, String status);

    /**
     * 取得特定會內人員報名結果
     * @param orformno
     * @param userid
     * @return
     */
    public Orresult getDataByPerson(String orformno, String userid);

    /**
     * 取得本月最大序號
     * @return
     */
    public String getMaximumSeqno(String orformno);
}
