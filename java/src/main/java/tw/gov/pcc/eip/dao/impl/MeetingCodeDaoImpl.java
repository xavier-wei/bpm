package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.Sysmsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(MeetingCodeDao.TABLE_NAME)
@Repository


public class MeetingCodeDaoImpl extends BaseDao<MeetingCode> implements MeetingCodeDao {

	private static final String ALL_COLUMNS_SQL;



    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" T.ITEMTYP as itemTyp, T.ITEMID as itemId, T.ITEMNAME as itemName, T.QTY as qty ")
                .toString();
    }

    @Override
    public List<MeetingCode> selectAllData() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(  ALL_COLUMNS_SQL );
        sql.append(" FROM " + TABLE_NAME + " T " );
        sql.append(" ORDER BY itemid " );
        return getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }

    @Override
    public MeetingCode findByPK(String itemId) {
        MeetingCode t=new MeetingCode();
        t.setItemId(itemId);
        return  selectDataByPrimaryKey(t);
    }

    @Override
    public MeetingCode selectDataByPrimaryKey(MeetingCode meetingCode) {
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT ");
        sql.append(  ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.ITEMID = :itemId ");

        SqlParameterSource params = new MapSqlParameterSource("itemId", meetingCode.getItemId());

        List<MeetingCode> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(MeetingCode.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int insertData(MeetingCode data) {
        return insert(data);
    }

    @Override
    public int findByitemId(String itemTyp,String itemId){
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.ITEMTYP = :itemTyp and SUBSTRING(T.ITEMID, 2, 4)=:itemId ");
        Map<String, String> params=new HashMap<>();
        params.put("itemTyp", itemTyp);
        params.put("itemId", itemId);
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, Integer.class);
    }

    @Override
    public int findItemIdByMeetingItem(String itemId){
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT COUNT(*) " + " FROM " + TABLE_NAME + " T ");
        sql.append(" WHERE ( EXISTS ( SELECT T.ITEMID FROM MEETINGITEM B " );
        sql.append("                 WHERE T.ITEMID = B.ITEMID ) ");
        sql.append("    OR EXISTS (SELECT T.ITEMID FROM ROOMISABLE C ");
        sql.append("                 WHERE T.ITEMID = C.ITEMID ) ) ");
        sql.append(" AND (T.ITEMID = :itemid)");
        sql.append("  OR (T.ITEMID = :itemid AND T.FLAG='N') ");

        Map<String, Object> params=new HashMap<>();
        params.put("itemid", itemId);//不能駝峰命名
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, Integer.class);
    }



    @Override
    public int findByitemName(String itemName){
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.ITEMNAME = :itemName ");
        Map<String, String> params=new HashMap<>();
        params.put("itemName", itemName);
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, Integer.class);
    }

    @Override
    public int updateData(MeetingCode data, String itemId) {
        String sql=new StringBuilder()
                .append(" UPDATE " + TABLE_NAME )
                .append(" SET ITEMTYP = :itemTyp, ITEMID = :itemid, ITEMNAME = :itemName, QTY = :qty ")
                .append(" WHERE ITEMID = :itemid ").toString();
        Map<String, Object> params = new HashMap<>();
        params.put("itemTyp", data.getItemTyp());
        params.put("itemid", itemId);//不能駝峰命名
        params.put("itemName", data.getItemName());
        params.put("qty", data.getQty());
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public int updateItemId(MeetingCode data, String itemId) {
        String sql=new StringBuilder()
                .append(" UPDATE " + TABLE_NAME )
                .append(" SET ITEMTYP = CASE WHEN ITEMTYP = 'FX' THEN 'F' ELSE 'FX' END")
                .append(" WHERE ITEMID = :itemid ").toString();
        Map<String, Object> params = new HashMap<>();
        params.put("itemid", itemId);//不能駝峰命名
        return getNamedParameterJdbcTemplate().update(sql, params);
    }
    

    @Override
    public int deleteData(String itemId) {
        String sql=new StringBuilder()
            .append(" DELETE T FROM " + TABLE_NAME + " T ")
            .append(" WHERE NOT EXISTS ( SELECT T.ITEMID FROM MEETINGITEM B " )
            .append("                     WHERE T.ITEMID = B.ITEMID ) ")
            .append(" AND T.ITEMID = :itemid AND T.FLAG='Y' ").toString();

        Map<String, Object> params=new HashMap<>();
        params.put("itemid", itemId);//不能駝峰命名
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<MeetingCode> selectDataByItemType(String itemtyp) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM MEETINGCODE T");
        sql.append(" WHERE T.ITEMTYP = :itemtyp");

        Map<String, Object> params = new HashMap<>();
        params.put("itemtyp", itemtyp);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }


    @Override
    public List<MeetingCode> selectDataByItemTypeF(String itemtyp) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM MEETINGCODE T");
        sql.append(" WHERE T.ITEMTYP LIKE :itemtyp + '%' ");
        sql.append(" ORDER BY itemid " );

        Map<String, Object> params = new HashMap<>();
        params.put("itemtyp", itemtyp);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }



    @Override
    public List<MeetingCode> selectDataByItemId(String itemId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM MEETINGCODE T");
        sql.append(" WHERE T.ITEMID = :itemId");

        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }

    @Override
    public List<MeetingCode> selectDataByItemName(String itemName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM MEETINGCODE T");
        sql.append(" WHERE T.ITEMNAME = :itemName");

        Map<String, Object> params = new HashMap<>();
        params.put("itemName", itemName);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }

    @Override
    public List<MeetingCode> findValidRoomByDtandUsing(String meetingDt, String using) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT a.ITEMID, a.ITEMNAME FROM MEETINGCODE a ");
        sql.append("     WHERE a.ITEMTYP = 'F' ");
        sql.append("       AND NOT exists ( ");
        sql.append("            SELECT b.ROOMID FROM MEETING b ");
        sql.append("             WHERE b.MEETINGDT = :meetingDt ");
        sql.append("               AND b.STATUS = 'A' ");
        sql.append("               AND (SELECT dbo.UFN_CHECK(b.USING, :using) AS RTN) = 'Y' ");
        sql.append("               AND a.ITEMID = b.ROOMID ");
        sql.append("                 ) ");
        sql.append(" UNION ALL ");
        sql.append("    SELECT a.ITEMID, a.ITEMNAME FROM MEETINGCODE a ");
        sql.append("     WHERE a.ITEMTYP = 'FX' ");
        sql.append("       AND exists(SELECT distinct d.ITEMID ");
        sql.append("      FROM ROOMISABLE d ");
        sql.append("     WHERE d.ITEMID = a.ITEMID ");
        sql.append("       AND d.ISABLEDATE = :meetingDt ");
        sql.append("       AND (SELECT dbo.UFN_CHECK(d.ISABLETIME, :using) AS RTN) = 'N') ");
        sql.append("  ORDER BY ITEMID, ITEMNAME ");

        Map<String, Object> params = new HashMap<>();
        params.put("meetingDt", meetingDt);
        params.put("using", using);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }

    @Override
    public List<MeetingCode> findValidRoominclBookedByDtandUsing(String meetingId, String meetingDt, String using) {
        StringBuilder sql = new StringBuilder();

        sql.append("     SELECT a.ITEMID, a.ITEMNAME FROM MEETINGCODE a ");
        sql.append("      WHERE a.ITEMTYP = 'F' ");
        //判斷是否為同一筆會議 相同就列出該會議室
        sql.append("        AND (exists ( ");
        sql.append("             SELECT b.ROOMID FROM MEETING b ");
        sql.append("              WHERE b.MEETINGID = :meetingId ");
        sql.append("                AND a.ITEMID = b.ROOMID ");
        sql.append("                AND b.MEETINGDT = :meetingDt ");
        sql.append("                AND b.USING = :using ");
        sql.append("             ) ");
        sql.append("        OR (NOT exists ( ");
        //判斷相同日期時間MEETING是否已存在且
        //ROOMISABLE沒有該間會議室或ROOMISABLE有該會議室且在可預約時段內
        sql.append("             SELECT c.ROOMID FROM MEETING c ");
        sql.append("              WHERE c.MEETINGDT = :meetingDt ");
        sql.append("                AND (SELECT dbo.UFN_CHECK(c.USING, :using) AS RTN) = 'Y' ");
        sql.append("                AND a.ITEMID = c.ROOMID) ");
        sql.append("             ) ");
        sql.append("           ) ");
        sql.append(" UNION ALL ");
        sql.append("    SELECT a.ITEMID, a.ITEMNAME FROM MEETINGCODE a ");
        sql.append("     WHERE a.ITEMTYP = 'FX' ");
        sql.append("       AND exists(SELECT distinct e.ITEMID ");
        sql.append("      FROM ROOMISABLE e ");
        sql.append("     WHERE e.ITEMID = a.ITEMID ");
        sql.append("       AND e.ISABLEDATE = :meetingDt ");
        sql.append("       AND (SELECT dbo.UFN_CHECK(e.ISABLETIME, :using) AS RTN) = 'N') ");

        Map<String, Object> params = new HashMap<>();
        params.put("meetingId", meetingId);
        params.put("meetingDt", meetingDt);
        params.put("using", using);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }

    @Override
    public List<MeetingCode> selectDataByItemTypes(List<String> itemtyps) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("      FROM MEETINGCODE T ");
        sql.append("     WHERE T.ITEMTYP in (:itemtyps)");
        sql.append("  ORDER BY T.ITEMNAME, SUBSTRING(T.ITEMID,2,2)");

        Map<String, Object> params = new HashMap<>();
        params.put("itemtyps", itemtyps);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(MeetingCode.class));
    }
}
