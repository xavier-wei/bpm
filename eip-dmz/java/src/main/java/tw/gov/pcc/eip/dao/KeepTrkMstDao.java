package tw.gov.pcc.eip.dao;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.KeepTrkMst;


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

    }
