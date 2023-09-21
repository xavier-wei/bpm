package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.RoomIsableDao;
import tw.gov.pcc.eip.domain.RoomIsable;

import java.util.List;

@DaoTable(RoomIsableDao.TABLE_NAME)
@Repository


public class RoomIsableDaoImpl extends BaseDao<RoomIsable> implements RoomIsableDao {

	private static final String ALL_COLUMNS_SQL;



    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append("T.ITEMID as itemId, T.ITEMNAME as itemName, T.ISABLEDATE as isableDate, T.MEETINGBEGIN as meetingBegin, T.MEETINGEND as meetingEnd, T.ISABLETIME as isableTime")
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
        return insert(data);
    }

    @Override
    public int updateData(RoomIsable data) {
        return updateByPK(data);
    }

    @Override
    public int deleteData(RoomIsable data) {
        return deleteByPK(data);
    }
}
