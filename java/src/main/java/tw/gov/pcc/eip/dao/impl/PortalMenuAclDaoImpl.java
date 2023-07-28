package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.PortalMenuAclDao;
import tw.gov.pcc.eip.domain.CursorAcl;

import java.util.List;

@Repository
public class PortalMenuAclDaoImpl extends BaseDao<CursorAcl> implements PortalMenuAclDao {
    @Override
    public CursorAcl selectDataByPrimaryKey(CursorAcl cursorAcl) {
        return null;
    }

    @Override
    @SkipLog
    public List<CursorAcl> findAllAcl(String sysId) {
        String sql = "WITH DeptHierarchy AS (" +
                "    SELECT i.item_id," +
                "           i.item_id_p, i.item_name, i.hyperlink AS url," +
                "           i.DISABLE AS is_disabled, i.sort_order," +
                "           i.sub_link, 1 AS levelv," +
                "           CAST(i.sort_order AS varchar(8000)) AS sort_path," +
                "           CAST(i.item_id AS varchar(8000)) AS id_path" +
                "    FROM items i" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p" +
                "    WHERE i.item_id_p = 'root'" +
                "    UNION ALL" +
                "    SELECT i.item_id," +
                "           i.item_id_p, i.item_name, i.hyperlink AS url," +
                "           i.DISABLE AS is_disabled, i.sort_order, " +
                "           i.sub_link, dh.levelv + 1," +
                "           dh.sort_path + '.' + CAST(i.sort_order AS varchar(8000))," +
                "           dh.id_path + '.' + CAST(i.item_id AS varchar(8000))" +
                "    FROM items i" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p" +
                "    INNER JOIN DeptHierarchy dh ON dh.item_id = i.item_id_p" +
                ")" +
                "SELECT item_id, item_id_p, item_name, url," +
                "       is_disabled, sort_order, levelv," +
                "       CASE" +
                "           WHEN NOT EXISTS (SELECT * FROM DeptHierarchy dh2 WHERE dh2.item_id_p = dh.item_id)" +
                "           THEN 1 " +
                "           ELSE 0 " +
                "       END AS is_leaf, sub_link AS func_id " +
                "FROM DeptHierarchy dh " +
                "ORDER BY sort_path ; ";

        SqlParameterSource params = new MapSqlParameterSource("p_sys_id", sysId);
        List<CursorAcl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(CursorAcl.class));
        return list;
    }

    @Override
    @SkipLog
    public List<CursorAcl> findRoleAcl(String sysId, String roleid) {
        String sql = "WITH DeptHierarchy AS (" +
                "    SELECT i.item_id," +
                "           i.item_id_p, i.item_name, i.hyperlink AS url," +
                "           i.DISABLE AS is_disabled, i.sort_order," +
                "           i.sub_link, 1 AS levelv," +
                "           CAST(i.sort_order AS varchar(8000)) AS sort_path," +
                "           CAST(i.item_id AS varchar(8000)) AS id_path" +
                "    FROM items i" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p" +
                "    WHERE i.item_id_p = 'root'" +
                "    UNION ALL" +
                "    SELECT i.item_id," +
                "           i.item_id_p, i.item_name, i.hyperlink AS url," +
                "           i.DISABLE AS is_disabled, i.sort_order," +
                "           i.sub_link, dh.levelv + 1," +
                "           dh.sort_path + '.' + CAST(i.sort_order AS varchar(8000))," +
                "           dh.id_path + '.' + CAST(i.item_id AS varchar(8000))" +
                "    FROM items i" +
                "    INNER JOIN items ii ON ii.item_id = i.item_id_p" +
                "    INNER JOIN DeptHierarchy dh ON dh.item_id = i.item_id_p" +
                ")" +
                "SELECT dh.item_id, dh.item_id_p, dh.item_name, dh.url," +
                "       dh.is_disabled, dh.sort_order, dh.levelv," +
                "       CASE" +
                "           WHEN NOT EXISTS (SELECT * FROM DeptHierarchy dh2 WHERE dh2.item_id_p = dh.item_id)" +
                "           THEN 1 " +
                "           ELSE 0 " +
                "       END AS is_leaf, sub_link AS func_id, " +
                " CASE WHEN o.ROLE_ID IS NOT NULL THEN 'Y' ELSE '' END isChecked " +
                "FROM DeptHierarchy dh " +
                "LEFT JOIN role_acl o ON dh.item_id = o.item_id AND o.role_id = :role_id  " +
                "ORDER BY sort_path ;";

        SqlParameterSource params = new MapSqlParameterSource("role_id", roleid)
                .addValue("sys_id", sysId);
        List<CursorAcl> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(CursorAcl.class));
        return list;
    }
}
