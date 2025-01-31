package tw.gov.pcc.common.dao.impl;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.dao.PortalDao;
import tw.gov.pcc.common.domain.User;
import tw.gov.pcc.common.framework.dao.FrameworkBaseDao;
import tw.gov.pcc.eip.dao.User_rolesDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PortalDaoImpl extends FrameworkBaseDao implements PortalDao {

    private static final String SELECT_USER_ITEMLIST_SQL = "WITH CTE_items AS (" +
            "    SELECT i.item_id, i.item_id_p, i.item_name, i.hyperlink url," +
            "        i.DISABLE is_disabled, i.sort_order, i.sub_link" +
            "    FROM items i" +
            "    INNER JOIN items i2 ON i.item_id_p = i2.item_id" +
            "    WHERE (i.DISABLE IS NULL OR i.DISABLE <> 'Y')" +
            ")" +
            ", CTE_user_roles AS (" +
            "    SELECT DISTINCT item_id" +
            "    FROM user_roles u" +
            "    INNER JOIN role_acl r ON u.sys_id = r.sys_id AND u.role_id = r.role_id" +
            "    WHERE u.user_id = :userId AND u.sys_id = :systemId AND " +
            "            u.dept_id <> '"+ User_rolesDao.SYSTEM_ADMIN_DEPT_ID +"' " +
            "          AND u.role_id = 'EIP-DMZ'" +
            ")" +
            ", CTE_eip_dmz AS (" + 
            "   SELECT item_id FROM role_acl WHERE role_id = 'EIP-DMZ'" +
            ")"+
            ", CTE_items_hierarchy AS (" +
            "    SELECT i.item_id, i.item_id_p, i.item_name, i.url, i.is_disabled, i.sort_order," +
            "        1 AS LEVEL, CAST(1 AS BIT) AS is_leaf, NULL AS dept_id, i.sub_link AS func_id" +
            "    FROM CTE_items i" +
            "    WHERE i.item_id_p = 'root'" +
            "    UNION ALL" +
            "    SELECT i.item_id, i.item_id_p, i.item_name, i.url, i.is_disabled, i.sort_order," +
            "        LEVEL + 1 AS LEVEL, CAST(1 AS BIT) AS is_leaf, NULL AS dept_id, i.sub_link AS func_id" +
            "    FROM CTE_items i" +
            "    INNER JOIN CTE_items_hierarchy h ON i.item_id_p = h.item_id" +
            "    WHERE (i.item_id IN (SELECT item_id FROM CTE_user_roles)" +
            "    OR EXISTS(select 1 from USER_ROLES ur where ur.USER_ID = :userId" +
            "    AND ur.SYS_ID = :systemId" +
            "    AND ur.DEPT_ID = '"+User_rolesDao.SYSTEM_ADMIN_DEPT_ID+"'" +
            "    AND ur.ROLE_ID = '"+User_rolesDao.SYSTEM_ADMIN_ROLE_ID+"')) "+
            "    AND i.item_id in (SELECT * from CTE_eip_dmz)" +
            ")" +
            "SELECT FUNC_ID as \"itemId\" "
            + "FROM   CTE_items_hierarchy "
            + "WHERE  IS_DISABLED IS NULL OR "
            + "       IS_DISABLED <> 'Y' "
            + "ORDER  BY FUNC_ID";
    private static final String SELECT_USER_MENU_SQL = "WITH CTE_items AS (" +
            "    SELECT i.item_id, i.item_id_p, i.item_name, i.hyperlink url," +
            "        i.DISABLE is_disabled, i.sort_order, i.sub_link" +
            "    FROM items i" +
            "    INNER JOIN items i2 ON i.item_id_p = i2.item_id" +
            "    WHERE (i.DISABLE IS NULL OR i.DISABLE <> 'Y')" +
            ")" +
            ", CTE_user_roles AS (" +
            "    SELECT DISTINCT item_id" +
            "    FROM user_roles u" +
            "    INNER JOIN role_acl r ON u.sys_id = r.sys_id AND u.role_id = r.role_id" +
            "    WHERE u.user_id = :userId AND u.sys_id = :systemId AND " +
            "            u.dept_id <> '"+User_rolesDao.SYSTEM_ADMIN_DEPT_ID+"' " +
            "          AND u.role_id = 'EIP-DMZ'" +
            ")" +
            ", CTE_eip_dmz AS (" +
            "   SELECT item_id FROM role_acl WHERE role_id = 'EIP-DMZ'" +
            ")"+
            ", CTE_items_hierarchy AS (" +
            "    SELECT i.item_id, i.item_id_p, i.item_name, i.url, i.is_disabled, i.sort_order," +
            "        1 AS LEVEL, CAST(1 AS BIT) AS is_leaf, NULL AS dept_id, i.sub_link AS func_id" +
            "    FROM CTE_items i" +
            "    WHERE i.item_id_p = 'root'" +
            "    UNION ALL" +
            "    SELECT i.item_id, i.item_id_p, i.item_name, i.url, i.is_disabled, i.sort_order," +
            "        LEVEL + 1 AS LEVEL, CAST(1 AS BIT) AS is_leaf, NULL AS dept_id, i.sub_link AS func_id" +
            "    FROM CTE_items i" +
            "    INNER JOIN CTE_items_hierarchy h ON i.item_id_p = h.item_id" +
            "    WHERE (i.item_id IN (SELECT item_id FROM CTE_user_roles)" +
            "    OR EXISTS(select 1 from USER_ROLES ur where ur.USER_ID = :userId" +
            "    AND ur.SYS_ID = :systemId" +
            "    AND ur.DEPT_ID = '"+User_rolesDao.SYSTEM_ADMIN_DEPT_ID+"'" +
            "    AND ur.ROLE_ID = '"+User_rolesDao.SYSTEM_ADMIN_ROLE_ID+"')) "+
            "    AND i.item_id in (SELECT * from CTE_eip_dmz)" +
            ")" +
            "SELECT ITEM_ID as \"itemId\", "
            + "       ITEM_ID_P as \"itemIdP\", "
            + "       ITEM_NAME as \"itemName\", "
            + "       URL as \"url\", "
            + "       IS_DISABLED as \"isDisabled\", "
            + "       SORT_ORDER as \"sortOrder\", "
            + "       LEVEL as \"levelNo\", "
            + "       IS_LEAF as \"isLeaf\", "
            + "       FUNC_ID as \"funcId\" "
            + "FROM   CTE_items_hierarchy "
            + "WHERE  LEVEL > 1 AND  "
            + "       (IS_DISABLED IS NULL OR IS_DISABLED <> 'Y') ";
    private static final String SELECT_USER = "SELECT A.USER_ID as \"userId\", "
            + "       A.USER_NAME as \"userName\", "
            + "       A.EMP_ID as \"empId\", "
            + "       A.DEPT_ID as \"deptId\", "
            + "       A.EMAIL as \"email\", "
            + "       A.TEL1 as \"tel1\", "
            + "       A.TEL2 as \"tel2\", "
            + "       A.TITLE_ID as \"titleId\", "
            + "       A.LINE_TOKEN as \"lineToken\", "
            + "       A.ORG_ID as \"orgId\" "
            + "FROM   USERS A "
            + "WHERE  A.USER_ID = :userId";

    /**
     * 登入時, 取得使用者所能執行的系統項目代碼
     *
     * @param systemId 應用系統代號
     * @param userId   使用者代碼
     * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
     */
    @SkipLog
    @Override
    public List<String> selectUserItemList(String systemId, String userId, String deptId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("systemId", systemId)
                .addValue("userId", userId)
                .addValue("deptId", deptId);
        return getNamedParameterJdbcTemplate().queryForList(SELECT_USER_ITEMLIST_SQL, namedParameters, String.class);
    }

    /**
     * 登入時, 取得使用者功能選單
     *
     * @param systemId 應用系統代號
     * @param userId   使用者代碼
     * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
     */
    @SkipLog
    @Override
    public List<HashMap<String, String>> selectUserMenu(String systemId, String userId, String deptId) {

        SqlParameterSource namedParameters = new MapSqlParameterSource("systemId", systemId)
                .addValue("userId", userId)
                .addValue("deptId", deptId);

        List<HashMap<String, String>> result = getNamedParameterJdbcTemplate().query(SELECT_USER_MENU_SQL, namedParameters, (resultSet, rowNum) -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("itemId", resultSet.getString("itemId"));
            map.put("itemIdP", resultSet.getString("itemIdP"));
            map.put("itemName", resultSet.getString("itemName"));
            map.put("url", resultSet.getString("url"));
            map.put("isDisabled", resultSet.getString("isDisabled"));
            map.put("sortOrder", resultSet.getString("sortOrder"));
            map.put("levelNo", resultSet.getString("levelNo"));
            map.put("isLeaf", resultSet.getString("isLeaf"));
            map.put("funcId", resultSet.getString("funcId"));
            return map;
        });

        List<HashMap<String, String>> roots = result.stream()
                .filter(x -> systemId.equals(x.get("itemIdP")))
                .collect(Collectors.toList());

        Comparator<HashMap<String, String>> sortBySortOrder = Comparator.comparing(
                x -> Integer.parseInt(x.get("sortOrder")),
                Comparator.nullsLast(Comparator.naturalOrder())
        );

        roots.sort(sortBySortOrder);
        
        
        List<HashMap<String, String>> sortedNodes = new ArrayList<>();

        for (HashMap<String, String> root : roots) {
            traverseRoot(root, result, sortedNodes);
        }

        return sortedNodes;
    }

    private void traverseRoot(HashMap<String, String> node, List<HashMap<String, String>> nodes, List<HashMap<String, String>> sortedNodes) {
        sortedNodes.add(node);

        List<HashMap<String, String>> children = new ArrayList<>();
        for (HashMap<String, String> n : nodes) {
            if (n.get("itemIdP") != null && n.get("itemIdP")
                    .equals(node.get("itemId"))) {
                children.add(n);
            }
        }

        children.sort(Comparator.comparing(o -> o.get("sortOrder")));

        for (HashMap<String, String> child : children) {
            traverseRoot(child, nodes, sortedNodes);
        }

    }

    /**
     * 登入時, 取得使用者資料
     *
     * @param userId 使用者代碼
     * @return <code>CasUser</code> 物件
     */
    @SkipLog
    @Override
    public User selectUser(String userId) {
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
            return getNamedParameterJdbcTemplate().queryForObject(SELECT_USER, namedParameters, BeanPropertyRowMapper.newInstance(User.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}
