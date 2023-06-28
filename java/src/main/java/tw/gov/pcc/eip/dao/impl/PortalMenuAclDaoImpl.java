package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.PortalMenuAclDao;
import tw.gov.pcc.eip.domain.CursorDeptAcl;

import java.util.List;

@Repository
public class PortalMenuAclDaoImpl extends BaseDao<CursorDeptAcl> implements PortalMenuAclDao {
    @Override
    public CursorDeptAcl selectDataByPrimaryKey(CursorDeptAcl cursorDeptAcl) {
        return null;
    }

    @Override
    @SkipLog
    public List<CursorDeptAcl> findDeptAcl(String sysId, String deptId) {
        String sql = "WITH DeptHierarchy AS (\n" +
                "    SELECT i.item_no, ii.item_no AS item_no_p, i.item_id,\n" +
                "           i.item_id_p, i.item_name, i.hyperlink AS url,\n" +
                "           i.DISABLE AS is_disabled, i.sort_order, o.dept_id,\n" +
                "           i.sub_link, 1 AS levelv,\n" +
                "           CAST(i.sort_order AS varchar(8000)) AS sort_path,\n" +
                "           CAST(i.item_id AS varchar(8000)) AS id_path\n" +
                "    FROM items i\n" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p\n" +
                "    LEFT JOIN dept_acl o ON o.item_id = i.item_id AND o.dept_id = :p_dept_id\n" +
                "    WHERE i.item_id = (SELECT DISTINCT item_id\n" +
                "                       FROM systems\n" +
                "                       WHERE sys_id = :p_sys_id)\n" +
                "    UNION ALL\n" +
                "    SELECT i.item_no, ii.item_no AS item_no_p, i.item_id,\n" +
                "           i.item_id_p, i.item_name, i.hyperlink AS url,\n" +
                "           i.DISABLE AS is_disabled, i.sort_order, dh.dept_id,\n" +
                "           i.sub_link, dh.levelv + 1,\n" +
                "           dh.sort_path + '.' + CAST(i.sort_order AS varchar(8000)),\n" +
                "           dh.id_path + '.' + CAST(i.item_id AS varchar(8000))\n" +
                "    FROM items i\n" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p\n" +
                "    INNER JOIN DeptHierarchy dh ON dh.item_id = i.item_id_p\n" +
                ")\n" +
                "SELECT item_no, item_no_p, item_id, item_id_p, item_name, url,\n" +
                "       is_disabled, sort_order, dept_id, levelv,\n" +
                "       CASE\n" +
                "           WHEN NOT EXISTS (SELECT * FROM DeptHierarchy dh2 WHERE dh2.item_id_p = dh.item_id)\n" +
                "           THEN 1\n" +
                "           ELSE 0\n" +
                "       END AS is_leaf, sub_link AS func_id\n" +
                "FROM DeptHierarchy dh\n" +
                "ORDER BY sort_path ;\n";

        SqlParameterSource params = new MapSqlParameterSource("p_dept_id", deptId)
                .addValue("p_sys_id", sysId);
        List<CursorDeptAcl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(CursorDeptAcl.class));
        return list;
    }
    
    @Override
    @SkipLog
    public List<CursorDeptAcl> findRoleAcl(String sysId, String roleid, String deptid) {
        String sql = "WITH DeptHierarchy AS (\n" +
                "    SELECT i.item_no, ii.item_no AS item_no_p, i.item_id,\n" +
                "           i.item_id_p, i.item_name, i.hyperlink AS url,\n" +
                "           i.DISABLE AS is_disabled, i.sort_order,\n" +
                "           i.sub_link, 1 AS levelv,\n" +
                "           CAST(i.sort_order AS varchar(8000)) AS sort_path,\n" +
                "           CAST(i.item_id AS varchar(8000)) AS id_path\n" +
                "    FROM items i\n" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p\n" +
                "    WHERE i.item_id = (SELECT DISTINCT item_id\n" +
                "                       FROM systems\n" +
                "                       WHERE sys_id = :sys_id)\n" +
                "    UNION ALL\n" +
                "    SELECT i.item_no, ii.item_no AS item_no_p, i.item_id,\n" +
                "           i.item_id_p, i.item_name, i.hyperlink AS url,\n" +
                "           i.DISABLE AS is_disabled, i.sort_order,\n" +
                "           i.sub_link, dh.levelv + 1,\n" +
                "           dh.sort_path + '.' + CAST(i.sort_order AS varchar(8000)),\n" +
                "           dh.id_path + '.' + CAST(i.item_id AS varchar(8000))\n" +
                "    FROM items i\n" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p\n" +
                "    INNER JOIN DeptHierarchy dh ON dh.item_id = i.item_id_p\n" +
                ")\n" +
                "SELECT dh.item_no, dh.item_no_p, dh.item_id, dh.item_id_p, dh.item_name, dh.url,\n" +
                "       dh.is_disabled, dh.sort_order, o.dept_id, dh.levelv,\n" +
                "       CASE\n" +
                "           WHEN NOT EXISTS (SELECT * FROM DeptHierarchy dh2 WHERE dh2.item_id_p = dh.item_id)\n" +
                "           THEN 1\n" +
                "           ELSE 0\n" +
                "       END AS is_leaf, sub_link AS func_id\n" +
                "FROM DeptHierarchy dh\n" +
                "LEFT JOIN role_acl o ON dh.item_id = o.item_id AND o.role_id = :role_id AND o.dept_id = :dept_id\n" +
                "ORDER BY sort_path ;\n";

        SqlParameterSource params = new MapSqlParameterSource("role_id", roleid)
        		                                    .addValue("sys_id", sysId)
        		                                    .addValue("dept_id", deptid);
        List<CursorDeptAcl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(CursorDeptAcl.class));
        return list;
    }
}
