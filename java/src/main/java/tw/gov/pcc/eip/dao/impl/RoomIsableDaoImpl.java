package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.RoomIsableDao;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.domain.RoomIsable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(RoomIsableDao.TABLE_NAME)
@Repository


public class RoomIsableDaoImpl extends BaseDao<RoomIsable> implements RoomIsableDao {

	private static final String ALL_COLUMNS_SQL;
    private static final String INSERT_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append("T.ITEMNO as itemNo, T.ITEMID as itemId, T.ITEMNAME as itemName, T.ISABLEDATE as isableDate, T.MEETINGBEGIN as meetingBegin, T.MEETINGEND as meetingEnd, T.ISABLETIME as isableTime")
                .toString();
        INSERT_SQL=new StringBuilder()
                .append("INSERT INTO " + TABLE_NAME + " ( ")
                .append("ITEMID, ITEMNAME, ISABLEDATE, MEETINGBEGIN, MEETINGEND , ISABLETIME )")
                .append("VALUES(")
                .append(":itemid, :itemname, :isabledate, :meetingbegin,  :meetingend , :isabletime)")
                .toString();
    }

    @Override
    public RoomIsable selectDataByPrimaryKey(RoomIsable roomIsable) {
        StringBuilder sql=new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.ITEMID = :itemId AND T.ISABLETIME = :isableTime AND T.ISABLEDATE = :isableDate");

        SqlParameterSource params = new MapSqlParameterSource("itemId", roomIsable.getItemId()).addValue("isableTime", roomIsable.getIsableTime()).addValue("isableDate", roomIsable.getIsableDate());

        List<RoomIsable> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(RoomIsable.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<RoomIsable> selectAllData() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM ROOMISABLE T");
        return getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(RoomIsable.class));
    }

    @Override
    public int insertData(RoomIsable data) {
        HashMap<String, Object> params=new HashMap<>();

        params.put("itemid", data.getItemId());
        params.put("itemname", data.getItemName());
        params.put("isabledate", data.getIsableDate());
        params.put("meetingbegin", data.getMeetingBegin());
        params.put( "meetingend", data.getMeetingEnd());
        params.put("isabletime", data.getIsableTime());

        return getNamedParameterJdbcTemplate().update(INSERT_SQL, params);
    }

    @Override
    public int updateData(RoomIsable data) {
        return updateByPK(data);
    }

    @Override
    public int deleteData(String itemid) {
        String sql=new StringBuilder()
                .append(" DELETE T FROM " + TABLE_NAME + " T ")
                .append(" WHERE  T.ITEMID = :itemid" ).toString();

        Map<String, Object> params=new HashMap<>();
        params.put("itemid", itemid);
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public int deleteSingleData(String itemno) {
        String sql=new StringBuilder()
                .append(" DELETE T FROM " + TABLE_NAME + " T ")
                .append(" WHERE  T.itemno = :itemno").toString();

        Map<String, Object> params=new HashMap<>();
        params.put("itemno", itemno);
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<RoomIsable> selectDataByItemId(String itemId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM "+ TABLE_NAME + " T ");
        sql.append(" WHERE T.ITEMID = :itemId");
        sql.append(" ORDER BY ISABLEDATE, MEETINGBEGIN");

        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(RoomIsable.class));
    }

    @Override
    public List<RoomIsable> selectItemIdByDate(String itemId, String isableDate){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM "+ TABLE_NAME + " T ");
        sql.append(" WHERE T.ITEMID = :itemId AND T.ISABLEDATE = :isableDate");
        sql.append(" ORDER BY ISABLETIME");

        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        params.put("isableDate", isableDate);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(RoomIsable.class));
    }

}
