package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.dao.MeetingItemDao;
import tw.gov.pcc.eip.domain.MeetingItem;
import tw.gov.pcc.eip.domain.MeetingItemAndMeetingCode;

import java.util.HashMap;
import java.util.List;

import static tw.gov.pcc.eip.dao.MeetingItemDao.TABLE_NAME;

@DaoTable(MeetingDao.TABLE_NAME)
@Repository
public class MeetingItemDaoImpl extends BaseDao<MeetingItem> implements MeetingItemDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" T.MEETINGID as meetingId, T.ITEMID as itemId, T.QTY as qty ")
                .toString();
    }

    @Override
    public MeetingItem selectDataByPrimaryKey(MeetingItem meetingItem) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.MEETINGID = :meetingId");

        SqlParameterSource params = new MapSqlParameterSource("meetingId", meetingItem.getMeetingId());

        List<MeetingItem> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(MeetingItem.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<MeetingItem> selectAllData() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM MEETINGITEM T");

        return getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(MeetingItem.class));
    }

    @Override
    public int insertData(MeetingItem data) {
        return  insert(data);
    }

    @Override
    public int updateData(MeetingItem data) {
        return updateByPK(data);
    }

    @Override
    public int deleteData(MeetingItem data) {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE FROM MEETINGITEM ");
        sql.append("       WHERE MEETINGID = :meetingId ");

        HashMap<String, Object> params = new HashMap<>();
        params.put("meetingId", data.getMeetingId());

        return getNamedParameterJdbcTemplate().update(sql.toString(),params);
    }






    @Override
    public List<MeetingItem>  selectDataByMeetingIdAndItemID(String meetingId, String itemId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.MEETINGID = :meetingId");
        if(itemId != null){
            sql.append("  AND T.ITEMID LIKE '%'+ :itemId +'%'");
        }

        SqlParameterSource params = new MapSqlParameterSource("meetingId", meetingId).addValue("itemId", itemId);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(MeetingItem.class));
    }

    @Override
    public List<MeetingItemAndMeetingCode> selectDataByMeetingId(String meetingId, String itemTyp) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT a.MEETINGID, a.ITEMID, b.ITEMNAME, b.ITEMTYP, a.QTY ");
        sql.append("      FROM MEETINGITEM a ");
        sql.append(" LEFT JOIN MEETINGCODE b ");
        sql.append("        ON a.ITEMID = b.ITEMID ");
        sql.append("     WHERE a.MEETINGID = :meetingId ");
        sql.append("       AND b.ITEMTYP = :itemTyp ");

        SqlParameterSource params = new MapSqlParameterSource("meetingId", meetingId).addValue("itemTyp", itemTyp);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(MeetingItemAndMeetingCode.class));
    }
}
