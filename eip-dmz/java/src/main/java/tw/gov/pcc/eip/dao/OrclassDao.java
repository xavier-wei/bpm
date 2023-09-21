package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Orclass;
import tw.gov.pcc.eip.domain.Orformdata;

import java.util.List;

/**
 * 線上報名分類資料Dao
 * @Author Weith
 */
public interface OrclassDao {

    public static final String TABLE_NAME = "ORCLASS";

    public Orclass findByPk(Long orccode);

    public int insertData(Orclass t);

    public int updateData(Orclass t, Long orccode);

    public int deleteData(Long orccode);

    /**
     * 取得全部分類
     * @return
     */
    public List<Orclass> getAll();

    /**
     * 取得最大分類代碼
     * @return
     */
    public Long getOrccode();
}
