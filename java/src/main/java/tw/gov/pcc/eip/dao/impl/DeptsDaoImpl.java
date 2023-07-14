package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.domain.Users;

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
        sql.append(" SELECT '00' DEPT_ID, '全會人員' DEPT_NAME ");
        sql.append(" UNION ");
        sql.append(" SELECT DEPT_ID,DEPT_NAME FROM DEPTS WHERE IS_VALID ='Y' ");
        return getNamedParameterJdbcTemplate().query(sql.toString(), 
                BeanPropertyRowMapper.newInstance(Depts.class));
    }
}