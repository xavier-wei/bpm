package tw.gov.pcc.eip.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.domain.Depts;
import tw.gov.pcc.eip.annotation.DaoTable;
import tw.gov.pcc.eip.dao.BaseDao;
import tw.gov.pcc.eip.dao.DeptsDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(DeptsDao.TABLE_NAME)
@Repository
public class DeptsDaoImpl extends BaseDao<Depts> implements DeptsDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " t.DEPT_ID, t.DEPT_NAME, t.SORT_ORDER, t.DEPT_DESC, t.IS_VALID, t.DEPT_ID_P, t.CREATE_USER_ID, "
        		          + " t.CREATE_TIMESTAMP, t.MODIFY_USER_ID, t.MODIFY_TIMESTAMP, t.FROM_HR ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param items 條件
     * @return 唯一值
     */
    @Override
    public Depts selectDataByPrimaryKey(Depts depts) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE t.DEPT_ID = :dept_id ";
        List<Depts> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(depts), BeanPropertyRowMapper.newInstance(Depts.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<Depts> findByDeptid(String dept_id) {
        String sql = "SELECT " + ALL_COLUMNS_SQL + " FROM " + TABLE_NAME
        		      + " t WHERE t.DEPT_ID =  ISNULL(:dept_id, t.DEPT_ID)";
        SqlParameterSource params = new MapSqlParameterSource("dept_id", StringUtils.isNotBlank(dept_id)?dept_id:null);
        List<Depts> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Depts.class));
        return list;
    }

    /**
     * 新增一筆資料
     *
     * @param depts 新增資料
     */
    @Override
    public int insert(Depts depts) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " DEPT_ID, DEPT_NAME, SORT_ORDER, DEPT_DESC, IS_VALID, DEPT_ID_P, CREATE_USER_ID, " +
      		            " CREATE_TIMESTAMP, MODIFY_USER_ID, MODIFY_TIMESTAMP, FROM_HR " +
                        ")" +
                        " VALUES ( " +
                        " :dept_id, :dept_name, :sort_order, :dept_desc, :is_valid, :dept_id_p, :create_user_id, " +
                        " :create_timestamp, :modify_user_id, :modify_timestamp, :from_hr " +
                        ")",
                new BeanPropertySqlParameterSource(depts));
    }

    /**
     * 根據key更新資料
     *
     * @param depts 更新條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Depts depts) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + " SET " +
        		" DEPT_NAME = :dept_name, SORT_ORDER = :sort_order, DEPT_DESC = :dept_desc, IS_VALID = :is_valid, "
        		+ " DEPT_ID_P = :dept_id_p, CREATE_USER_ID = :create_user_id, "
        		+ " CREATE_TIMESTAMP = :create_timestamp, MODIFY_USER_ID = :modify_user_id, MODIFY_TIMESTAMP  = :modify_timestamp, FROM_HR = :from_hr "
        		+ " WHERE DEPT_ID = :dept_id ",
                new BeanPropertySqlParameterSource(depts));
    }

    /**
     * 根據key刪除資料
     *
     * @param depts 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Depts depts) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE DEPT_ID = :dept_id ",
                new BeanPropertySqlParameterSource(depts));
    }


    @Override
    public Depts findByPk(String dept_id) {
        Depts d = new Depts();
        d.setDept_id(dept_id);
        return this.selectDataByPrimaryKey(d);
    }

    @Override
    public List<Depts> getEip01wDepts() {
        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT '00' DEPT_ID, '全會人員' DEPT_NAME ");
//        sql.append(" UNION ");
        sql.append("SELECT DEPT_ID,DEPT_NAME FROM DEPTS WHERE IS_VALID ='Y' ORDER BY SORT_ORDER ");
        return getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Depts.class));
    }

    @Override
    public List<Depts> getRelevantDeptByAttr(String attr, String deptId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DEPT_ID, ");
        sql.append("        DEPT_NAME ");
        sql.append("   FROM DEPTS C ");
        sql.append("  WHERE IS_VALID ='Y' ");
        sql.append("    AND DEPT_ID IN (SELECT DISTINCT A.CONTACTUNIT ");
        sql.append("                      FROM MSGDATA A, ");
        sql.append("                           MSGAVAILDEP B ");
        sql.append("                     WHERE A.FSEQ = B.FSEQ ");
        sql.append("                       AND A.STATUS = '4' ");
        sql.append("                       AND A.ATTRIBUTYPE = :attr ");
        sql.append("                       AND (CASE WHEN TRIM(B.AVAILABLEDEP) = '00' THEN :deptId ELSE TRIM(B.AVAILABLEDEP) END) = :deptId ) ");
        sql.append("  ORDER BY DEPT_ID ");
        Map<String, Object> params = new HashMap<>();
        params.put("attr", attr);
        params.put("deptId", deptId);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Depts.class));
    }

    public List<Depts> getEip03wDepts(String level, String trkObj) {
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("     FROM DEPTS T");
        sql.append("    WHERE DEPT_ID_P = CASE :level WHEN '1' THEN DEPT_ID "); //根部門
        sql.append("                                  WHEN '2' THEN :trkObj END ");  //改成畫面入trkobj
        sql.append("      AND IS_VALID = 'Y'  ");
        sql.append(" ORDER BY SORT_ORDER, DEPT_ID  ");

        Map<String, Object> params = new HashMap<>();
        params.put("level", level);
        params.put("trkObj", trkObj);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Depts.class));
    }

    @Override
    public List<Depts> findNameByMultiID(List<String> deptIDs) {
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("     FROM DEPTS t");
        sql.append("    WHERE DEPT_ID in (:deptIDs) ");
        sql.append(" ORDER BY DEPT_ID  ");

        Map<String, Object> params = new HashMap<>();
        params.put("deptIDs", deptIDs);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Depts.class));
    }
}
