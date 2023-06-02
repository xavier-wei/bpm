package tw.gov.pcc.eip.dao;

import tw.gov.pcc.eip.domain.Sysmsg;

import java.util.List;

/**
 * 系統訊息檔 (SYSMSG)
 *
 * @author Goston
 */
public interface SysmsgDao {

    /**
     * 取得 系統訊息檔 的所有內容 (此方法不紀錄 LOG)
     *
     * @return
     */
    List<Sysmsg> selectAllData();

    void insertData(Sysmsg data);

    void updateData(Sysmsg data);

    void deleteData(Sysmsg data);

}
