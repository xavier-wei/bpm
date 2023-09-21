package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.KeepTrkMstDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.domain.KeepTrkMst;

import java.util.HashMap;
import java.util.List;

/**
 * 重要列管事項主檔
 *
 * @author 2201009
 *
 */
@DaoTable(MsgdataDao.TABLE_NAME)
@Repository
public class KeepTrkMstDaoImpl extends BaseDao<KeepTrkMst> implements KeepTrkMstDao {


    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" T.TRKID as trkID, T.TRKCONT as trkCont, T.TRKFROM as trkFrom, T.TRKSTS as trkSts,  ")
                .append(" T.ALLSTDT as allStDt, T.CLSDT as clsDt, T.CREDEPT as creDept, T.CREUSER as creUser, ")
                .append(" T.CREDT as creDt, T.UPDDEPT as updDept, T.UPDUSER as updUser, T.UPDDT as updDt ")
                .toString();
    }


    @Override
    public KeepTrkMst selectDataByPrimaryKey(KeepTrkMst keepTrkMst) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.TRKID = :TRKID");

        SqlParameterSource params = new MapSqlParameterSource("TRKID", keepTrkMst.getTrkID());

        List<KeepTrkMst> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(KeepTrkMst.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int insert(KeepTrkMst keepTrkMst) {
        return super.insert(keepTrkMst);
    }

    @Override
    public int update(KeepTrkMst keepTrkMst) {
        return super.updateByPK(keepTrkMst);
    }

    @Override
    public int delete(KeepTrkMst keepTrkMst) {
        return super.deleteByPK(keepTrkMst);
    }

    @Override
    public void closeByTrkID(KeepTrkMst ktm) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> params = new HashMap<>();

        sql.append(" UPDATE KeepTrkMst");
        sql.append("    SET ClsDt = :clsDt, ");
        sql.append("        TrkSts = '9',");
        sql.append("        UpdDept = :updDept,");
        sql.append("        UpdUser = :updUser,");
        sql.append("        UpdDt = :updDt");
        sql.append("  WHERE TrkID = :trkID");

        params.put("clsDt", ktm.getClsDt());
        params.put("updDept", ktm.getUpdDept());
        params.put("updUser", ktm.getUpdUser());
        params.put("updDt", ktm.getUpdDt());
        params.put("trkID", ktm.getTrkID());

        getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public void updateByTrkID(KeepTrkMst ktm) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> params = new HashMap<>();

        sql.append(" UPDATE KeepTrkMst");
        sql.append("    SET TrkCont = :trkCont, ");
        sql.append("        TrkFrom = :trkFrom, ");
        sql.append("        TrkSts = :trkSts, ");
        sql.append("        AllStDt = :allStDt, ");
        sql.append("        UpdDept = :updDept, ");
        sql.append("        UpdUser = :updUser, ");
        sql.append("        UpdDt = :updDt, ");
        sql.append("        ClsDt = :clsDt ");
        sql.append("  WHERE TrkID = :trkID");

        params.put("trkCont", ktm.getTrkCont());
        params.put("trkFrom", ktm.getTrkFrom());
        params.put("trkSts", ktm.getTrkSts());
        params.put("allStDt", ktm.getAllStDt());
        params.put("updDept", ktm.getUpdDept());
        params.put("updUser", ktm.getUpdUser());
        params.put("updDt", ktm.getUpdDt());
        params.put("clsDt", ktm.getClsDt());
        params.put("trkID", ktm.getTrkID());

        getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int deleteByTrkID(String trkID ) {
        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(TABLE_NAME);
        sql.append("  WHERE TrkID = :trkID");

        SqlParameterSource params =
                new MapSqlParameterSource("trkID", trkID);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }
}
