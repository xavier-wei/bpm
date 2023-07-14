package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.common.cases.Eip03w040Case;
import tw.gov.pcc.eip.dao.KeepTrkDtlDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.domain.KeepTrkDtl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 重要列管事項明細
 *
 * @author 2201009
 *
 */
@DaoTable(KeepTrkDtlDao.TABLE_NAME)
@Repository
public class KeepTrkDtlDaoImpl extends BaseDao<KeepTrkDtl> implements KeepTrkDtlDao {

    @Override
    public KeepTrkDtl selectDataByPrimaryKey(KeepTrkDtl keepTrkDtl) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.TRKID = :TRKID");
        sql.append("                             AND T.TRKOBJ = :TRKOBJ");

        SqlParameterSource params = new MapSqlParameterSource("TRKID", keepTrkDtl.getTrkID()).addValue("TRKOBJ", keepTrkDtl.getTrkObj());

        List<KeepTrkDtl> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(KeepTrkDtl.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int insert(KeepTrkDtl keepTrkDtl) {
        return super.insert(keepTrkDtl);
    }

    @Override
    public int update(KeepTrkDtl keepTrkDtl) {
        return super.updateByPK(keepTrkDtl);
    }

    @Override
    public int delete(KeepTrkDtl keepTrkDtl) {
        return super.deleteByPK(keepTrkDtl);
    }

    @Override
    public List<Eip03w040Case> selectAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" 	SELECT TrkObj, "); //處室代碼
        sql.append("           COUNT(1) AS cnt,  "); //案件數
        sql.append("           SUM(CASE WHEN PrcSts = '3' THEN 1 ELSE 0 END) AS cnt_cls,   "); //已結案
        sql.append("           SUM(CASE WHEN PrcSts = '3' THEN 0 ELSE 1 END) AS cnt_opn    "); //未結案
        sql.append("      FROM KeepTrkDtl ");
        sql.append("  GROUP BY TrkObj ORDER BY cnt DESC; ");

        return getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(Eip03w040Case.class));
    }

    @Override
    public List<Eip03w040Case> selectDatabytrkObjAndPrcsts(String trkObj, String prcsts) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT a.TrkID,    "); //列管編號
        sql.append("        a.TrkFrom,  "); //交辦來源
        sql.append("        a.TrkCont,  "); //列管事項
        sql.append("        b.TrkObj,   "); //處室
        sql.append("        b.PrcSts,   "); //處理狀態
        sql.append("        b.RptCont,  "); //辦理情形
        sql.append("        b.EndDt,    "); //列管迄日
        sql.append("        b.SupDt,    "); //解列日期
        sql.append("        b.SupAgree  ");
        sql.append("   FROM KeeptrkMst a, ");
        sql.append("        KeepTrkDtl b  ");
        sql.append("  WHERE a.TrkID = b.TrkID  ");
        sql.append("    AND b.TrkObj = :trkObj ");

        if (prcsts.equals("closed")) { //已結案
            sql.append(" AND b.prcsts = '3' ");
        }else {                     //未結案
            sql.append(" AND b.prcsts != '3' ");
        }
        sql.append(" ORDER BY a.TrkID DESC; ");

        Map<String, Object> params = new HashMap<>();
        params.put("trkObj", trkObj);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip03w040Case.class));
    }

    @Override
    public List<KeepTrkDtl> selectDataByTrkIDAndTrkObj(String trkID, String trkObj) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append(" SELECT * ");
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.TRKID = :trkID");

        if(StringUtils.isNotBlank(trkObj)){
            sql.append(" AND T.TRKOBJ = :trkObj");
            params.put("trkObj", trkObj);
        }
        params.put("trkID", trkID);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(KeepTrkDtl.class));
    }

    @Override
    public void closeByTrkIDAndTrkObj(KeepTrkDtl ktd) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append(" UPDATE KeepTrkDtl ");
        sql.append("    SET SupCont = :supCont, ");
        sql.append("        SupAgree = :supAgree, ");
        sql.append("        SupDept = :supDept, ");
        sql.append("        SupUser = :supUser, ");
        sql.append("        SupDt = :supDt, ");
        sql.append("        Prcsts = CASE :supAgree WHEN 'N' THEN '1' ELSE '3' END, ");
        sql.append("        RptRate = CASE :supAgree WHEN 'Y' THEN 100 ELSE RptRate END, ");
        sql.append("        RptAskEnd = CASE :supAgree WHEN 'N' THEN 'N' ELSE RptAskEnd END ");
        sql.append("  WHERE TrkID = :trkID AND TrkObj = :trkObj ");

        params.put("trkID", ktd.getTrkID());
        params.put("trkObj", ktd.getTrkObj());
        params.put("supCont", ktd.getSupCont());
        params.put("supAgree", ktd.getSupAgree());
        params.put("supDept", ktd.getSupDept());
        params.put("supUser", ktd.getSupUser());
        params.put("supDt", ktd.getSupDt());

        getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int selectDoingCase(String trkID) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("   SELECT COUNT(1) ");
        sql.append("     FROM KeepTrkDtl ");
        sql.append("    WHERE TrkID = :trkID ");
        sql.append("      AND PrcSts != '3' ");

        params.put("trkID", trkID);

        return Optional.ofNullable(getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, Integer.class)).orElse(0);
    }

    @Override
    public String getNextTrkIDNumber(String today) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("   SELECT (MAX(SUBSTRING(TrkID,9,2))+1) AS TrkID ");
        sql.append("     FROM KeepTrkDtl ");
        sql.append("    WHERE SUBSTRING(TrkID,1,7) = :nowDate ");

        params.put("nowDate", today);

        String currentTrkID = Optional.ofNullable(getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class)).orElse(null);
        if (currentTrkID != null) {
            if(currentTrkID.length() < 2){
                return today + "-0" + currentTrkID;
            }
            return today + "-" + currentTrkID;
        } else {
            return today + "-01";
        }
    }

    @Override
    public void updateByTrkIDAndTrkObj(KeepTrkDtl ktd) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append(" UPDATE KeepTrkDtl ");
        sql.append("    SET StDt = :stDt, ");
        sql.append("        EndDt = :endDt ");
        sql.append("  WHERE TrkID = :trkID AND TrkObj = :trkObj ");

        params.put("trkID", ktd.getTrkID());
        params.put("trkObj", ktd.getTrkObj());
        params.put("stDt", ktd.getStDt());
        params.put("endDt", ktd.getEndDt());

        getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public int deleteByTrkIDAndTrkObj(String trkID, String trkObj) {

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(TABLE_NAME);
        sql.append("  WHERE TrkID = :trkID ");
        if(StringUtils.isNotBlank(trkObj)){
            sql.append("    AND TrkObj = :trkObj ");
        }

        SqlParameterSource params =
                new MapSqlParameterSource("trkID", trkID).addValue("trkObj", trkObj);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }
}
