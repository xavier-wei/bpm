package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.TableauDepartmentInfoDao;
import tw.gov.pcc.eip.dao.TableauUserInfoDao;
import tw.gov.pcc.eip.domain.TableauDepartmentInfo;
import tw.gov.pcc.eip.domain.TableauUserInfo;

import java.util.List;

@DaoTable(TableauDepartmentInfoDao.TABLE_NAME)
@Repository
public class TableauDepartmentInfoDaoImpl extends BaseDao<TableauDepartmentInfo> implements TableauDepartmentInfoDao {

    @Override
    public List<TableauDepartmentInfo> selectByDepartmentId(String deptId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + TABLE_NAME + " where DEPARTMENT_ID = :deptId ");
        SqlParameterSource params = new MapSqlParameterSource("deptId", deptId);
        List<TableauDepartmentInfo> list = getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(TableauDepartmentInfo.class));
        return list;
    }


    @Override
    public TableauDepartmentInfo selectDataByPrimaryKey(TableauDepartmentInfo tableauDepartmentInfo) {
        return null;
    }
}