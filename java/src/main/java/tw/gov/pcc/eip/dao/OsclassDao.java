package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Osclass;

import java.util.List;

/**
 * 意見調查分類資料Dao
 * @Author Weith
 */
public interface OsclassDao {

    public static final String TABLE_NAME = "OSCLASS";

    public Osclass findByPk(Integer osccode);

    public int insertData(Osclass t);

    public int updateData(Osclass t, Integer osccode);

    public int deleteData(Integer osccode);

    /**
     * 取得全部分類
     * @return
     */
    public List<Osclass> getAll();

    /**
     * 取得最大分類代碼
     * @return
     */
    public Integer getOsccode();
}
