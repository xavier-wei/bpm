package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Osformdata;

import java.util.List;

/**
 * 意見調查表單資料Dao
 * @Author Weith
 */
public interface OsformdataDao {

    public static final String TABLE_NAME = "OSFORMDATA";

    public Osformdata findByPk(String osformno);

    public int insertData(Osformdata t);

    public int updateData(Osformdata t, String osformno);

    public int deleteData(String osformno);

    public List<Osformdata> getAll();

    /**
     * 依多項條件取得資料
     * @param osformdata
     * @return
     */
    public List<Osformdata> getListByMultiCondition(Osformdata osformdata);

    /**
     * 取得本月最大流水號
     * @return
     */
    public String getMaximumOsformno(String ym);

    /**
     * 刪除所選表單
     * @param osformno
     */
    public int deleteByOsformnoList(List<String> osformno);

    /**
     * 將表單更新為指定狀態
     * @param osformno
     * @param status
     * @return
     */
    public int updateStatus(List<String> osformno, String status);

    /**
     * 使用分類代碼取得已上架資料
     * @param osccode
     * @return
     */
    public List<Osformdata> getStartingData(Integer osccode);

    /**
     * 使用狀態與部門代碼查詢意見調查列表
     * @param statusList
     * @param deptno
     * @return
     */
    public List<Osformdata> getListByStatus(List<String> statusList, String deptno);
    
    /**
     * 批次更新狀態
     * @return
     */
    public int updateStatusBatch();
}
