package tw.gov.pcc.eip.dao;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.common.cases.Eip03w040Case;
import tw.gov.pcc.eip.domain.KeepTrkDtl;

import java.util.List;

/**
 * 重要列管事項明細
 *
 * @author 2201009
 *
 */
@DaoTable(KeepTrkDtlDao.TABLE_NAME)
@Repository
public interface KeepTrkDtlDao {

    public static final String TABLE_NAME = "KEEPTRKDTL";

    public KeepTrkDtl selectDataByPrimaryKey(KeepTrkDtl KeepTrkDtl);

    public int insert(KeepTrkDtl keepTrkDtl);

    public int update(KeepTrkDtl keepTrkDtl);

    public int delete(KeepTrkDtl keepTrkDtl);

    /**
     * 查詢所有重要列管事項
     * @return
     */
    public List<Eip03w040Case> selectAll();

    /**
     * 查詢結案未結案統計件數
     *
     * @param trkObj  處室代碼
     * @param prcsts  結案狀態
     * @return
     */
    public List<Eip03w040Case> selectDatabytrkObjAndPrcsts(String trkObj, String prcsts);

    /**
     * 依照trkID或trkObj查詢件數
     *
     * @param trkID  列管事項編號
     * @param trkObj  列管對象(處室)
     * @return
     */
    public List<KeepTrkDtl> selectDataByTrkIDAndTrkObj(String trkID, @Nullable String trkObj);

    /**
     * 解除列管
     * @param ktd
     */
    public void closeByTrkIDAndTrkObj(KeepTrkDtl ktd);

    /**
     * 查詢除目前此筆外，是否還有未解列案件
     * @param trkID
     * @return
     */
    public int selectDoingCase(String trkID);


    /**
     * 列管事項編號新取號
     * @param today
     * @return
     */
    public String getNextTrkIDNumber(String today);


    /**
     * 依PK更新資料
     * @param ktd
     */
    public void updateByTrkIDAndTrkObj(KeepTrkDtl ktd);

    /**
     * 依PK刪除資料
     * @param trkID
     * @param trkObj
     * @return
     */
    public int deleteByTrkIDAndTrkObj(String trkID,  @Nullable String trkObj);
}
