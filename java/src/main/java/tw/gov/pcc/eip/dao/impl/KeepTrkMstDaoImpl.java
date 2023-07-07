package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.common.cases.Eip03w030Case;
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
    public List<Eip03w030Case> selectByColumns(String trkID, String trkCont, String allStDtSt, String allStDtEnd, String trkSts, String prcSts) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> params = new HashMap<>();
        sql.append(" SELECT * FROM ( ");
        sql.append("            SELECT a.Trkid, ");
        sql.append("                   a.TrkCont, ");
        sql.append("                   a.AllStDt, ");
        sql.append("                   a.Trksts, ");
        sql.append("                   COUNT(1) AS cnt_all, ");
        sql.append("                   SUM(CASE b.PrcSts WHEN '1' THEN 1 ELSE 0 END) AS cnt_doing, ");
        sql.append("                   SUM(CASE b.PrcSts WHEN '2' THEN 1 ELSE 0 END) AS cnt_wait, ");
        sql.append("                   SUM(CASE b.PrcSts WHEN '3' THEN 1 ELSE 0 END) AS cnt_done ");
        sql.append("              FROM KeepTrkMst a, ");
        sql.append("                   KeepTrkDtl b ");
        sql.append("             WHERE a.TrkID = b.TrkID ");
        sql.append("               AND a.TrkSts != '0' ");
        if(StringUtils.isNotBlank(trkSts)){
            sql.append("           AND a.TrkSts = dbo.UFN_NVL(:trkSts, a.TrkSts)  "); //畫面選擇[列管狀態]，全部則為空字串
            params.put("trkSts", trkSts);
        }
        if(StringUtils.isNotBlank(trkID)){
            sql.append("           AND a.TrkID like '%' + :trkID + '%'  ");     // 畫面輸入[列管編號]
            params.put("trkID", trkID);
        }
        if(StringUtils.isNotBlank(trkCont)){  // CONTAINS為必須要用IF-ELSE，有值才能用的語法
            sql.append("           AND CONTAINS(a.TrkCont, :trkCont)          "); //畫面輸入[內容]
            params.put("trkCont", trkCont);
        }
        sql.append("               AND a.AllStDt >= dbo.UFN_NVL(:allStDtSt,a.AllStDt)  "); // 畫面輸入[全案列管日期起日] 民國轉西元查詢
        sql.append("               AND a.AllStDt <= dbo.UFN_NVL(:allStDtEnd,a.AllStDt)  "); // 畫面輸入[全案列管日期迄日] 民國轉西元查詢

        sql.append("          GROUP BY a.Trkid, a.TrkCont, a.AllStDt, a.Trksts ");
        sql.append("       ) subquery ");
        if(!StringUtils.equals(prcSts,"0") && prcSts != null){  // 畫面輸入[處理狀態]
            if (StringUtils.equals(prcSts, "1")){
                sql.append("   WHERE subquery.cnt_doing > 0  ");
            } else if (StringUtils.equals(prcSts, "2")) {
                sql.append("   WHERE subquery.cnt_wait > 0  ");
            } else if (StringUtils.equals(prcSts, "3")) {
                sql.append("   WHERE subquery.cnt_done > 0  ");
            }
        }
        sql.append("  ORDER BY subquery.Trkid ");

        params.put("allStDtSt", allStDtSt);
        params.put("allStDtEnd", allStDtEnd);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip03w030Case.class));
    }

    @Override
    public int updateByTrkID(KeepTrkMst ktm) {
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

        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }
}
