package tw.gov.pcc.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.dao.SysfuncDao;
import tw.gov.pcc.common.domain.SystemFunction;
import tw.gov.pcc.common.framework.dao.BaseDao;

@Repository
public class SysfuncDaoImpl extends BaseDao<SystemFunction> implements SysfuncDao {

    @SkipLog
    @Override
    public SystemFunction selectDataByPrimaryKey(SystemFunction systemFunction) {
        return null; // 底層不實作，其它系統功能 DAO 勿學
    }

    /**
     * 依應用系統代號取得系統功能清單 for CAS
     *
     * @return 內含 <code>tw.gov.pcc.common.domain.SystemFunction</code> 物件的 List
     */
    @SkipLog
    @Override
    public List<SystemFunction> selectCasSysFuncBySystemId() {
        return getNamedParameterJdbcTemplate().query(SELECT_CAS_SYSFUNC_BY_SYSTEMID_SQL, BeanPropertyRowMapper.newInstance(SystemFunction.class));
    }

    private static final String SELECT_CAS_SYSFUNC_BY_SYSTEMID_SQL = "( "
        + "    SELECT A.ITEM_ID as \"itemId\", "
        + "           '' as \"itemName\", "
        + "           A.URL as \"url\", "
        + "           A.URLDESC as \"urlDesc\" "
        + "    FROM   ITEM_URL A "
        + "    WHERE  A.ITEM_ID NOT LIKE '%-' "
        + ") UNION ( "
        + "    SELECT A.ITEM_ID as \"itemId\", "
        + "           '' as \"itemName\", "
        + "           UPPER(A.PROG_ID) as \"url\", "
        + "           A.PROGDESC as \"urlDesc\" "
        + "    FROM   ITEM_PROG A "
        + "    WHERE  A.ITEM_ID NOT LIKE '%-' "
        + ")";
}
