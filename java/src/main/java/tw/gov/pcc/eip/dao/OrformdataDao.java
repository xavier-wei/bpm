package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Orformdata;

import java.util.List;

/**
 * 線上報名表單資料Dao
 * @Author Weith
 */
public interface OrformdataDao {

    public static final String TABLE_NAME = "ORFORMDATA";

    public Orformdata findByPk(String orformno);

    public int insertData(Orformdata t);

    public int updateData(Orformdata t, String orformno);

    public int deleteData(String orformno);

    public List<Orformdata> getAll();

    /**
     * 依多項條件取得資料
     * @param orformdata
     * @return
     */
    public List<Orformdata> getListByMultiCondition(Orformdata orformdata);

    /**
     * 使用分類代碼取得已上架資料
     * @param orccode
     * @return
     */
    public List<Orformdata> getOrstartingData(Long orccode);

    /**
     * 取得本月最大流水號
     * @return
     */
    public String getMaximumOrformno(String ym);

    /**
     * 刪除所選表單
     * @param orformno
     */
    public int deleteCheckedForm(List<String> orformno);

    /**
     * 將表單更新為指定狀態
     * @param orformno
     * @param status
     * @return
     */
    public int updateStatus(List<String> orformno, String status);

    /**
     * 使用狀態取得資料
     * @param statusList
     * @return
     */
    public List<Orformdata> getDataByStatus(List<String>statusList, String deptno, String jobtitle);
    
    /**
     * 批次更新狀態
     * @return
     */
    public int updateStatusBatch();

    /**
     * 使用課程分類代碼取得資料
     * @param courseclacode
     * @return
     */
    public List<Orformdata> getListByCourseclacode(Long courseclacode, String orformno);
}
