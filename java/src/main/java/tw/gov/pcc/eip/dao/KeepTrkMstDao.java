package tw.gov.pcc.eip.dao;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.common.cases.Eip03w010Case;
import tw.gov.pcc.eip.common.cases.Eip03w020Case;
import tw.gov.pcc.eip.common.cases.Eip03w030Case;
import tw.gov.pcc.eip.domain.KeepTrkMst;

import java.util.List;

/**
 * 重要列管事項主檔
 *
 * @author 2201009
 *
 */
@DaoTable(KeepTrkMstDao.TABLE_NAME)
@Repository
public interface KeepTrkMstDao {

    public static final String TABLE_NAME = "KEEPTRKMST";

    public KeepTrkMst selectDataByPrimaryKey(KeepTrkMst keepTrkMst);

    public int insert(KeepTrkMst keepTrkMst);

    public int update(KeepTrkMst keepTrkMst);

    public int delete(KeepTrkMst keepTrkMst);

    /**
     * 查詢所有重要列管事項+處理狀態筆數(for 解除列管)
     *
     * @param trkID
     * @param trkCont
     * @param allStDtSt
     * @param allStDtEnd
     * @param trkSts  全部/未完成/結案/作廢
     * @param prcSts  全部/待處理/待解列/已解列
     * @return
     */
    public List<Eip03w030Case> selectByColumnsForCaclControl(@Nullable String trkID, @Nullable String trkCont,
                                               @Nullable String allStDtSt, @Nullable String allStDtEnd,
                                               @Nullable String trkSts, @Nullable String prcSts);

    /**
     * 查詢所有重要列管事項+處理狀態筆數(for 重要列管事項維護)
     *
     * @param trkID
     * @param trkCont
     * @param allStDtSt
     * @param allStDtEnd
     * @param trkSts  全部/未完成/結案/作廢
     * @return
     */
    public List<Eip03w010Case> selectByColumns(@Nullable String trkID, @Nullable String trkCont,
                                               @Nullable String allStDtSt, @Nullable String allStDtEnd,
                                               @Nullable String trkSts);


    /**
     * 查詢所有重要列管事項+處理狀態筆數(for 填報辦理進度)
     *
     * @param trkID
     * @param trkCont
     * @param allStDtSt
     * @param allStDtEnd
     * @param trkSts  全部/未完成/結案/作廢
     * @param prcSts  全部/待處理/待解列/已解列
     * @param userDept  操作者部室代碼
     * @return
     */
    public List<Eip03w020Case> selectByColumnsForFillInProgress(@Nullable String trkID, @Nullable String trkCont,
                                                                @Nullable String allStDtSt, @Nullable String allStDtEnd,
                                                                @Nullable String trkSts, @Nullable String prcSts, @Nullable String userDept);

    /**
     * 均已解列，可結案
     *
     * @param ktm
     */
    public void closeByTrkID(KeepTrkMst ktm);

    /**
     * 更新KeepTrkMst
     *
     * @param ktm
     */
    public void updateByTrkID(KeepTrkMst ktm);

    /**
     * 刪除KeepTrkMst
     *
     * @param trkID
     */
    public int deleteByTrkID(String trkID);


    /**
     * 確認編號是否已存在
     * @param trkID
     * @return
     */
    public Integer findByTrkID(String trkID);

    }
