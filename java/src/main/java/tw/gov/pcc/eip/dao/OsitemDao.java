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
}
