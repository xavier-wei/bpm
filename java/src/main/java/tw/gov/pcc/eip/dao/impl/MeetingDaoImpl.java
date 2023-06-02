package tw.gov.pcc.eip.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.domain.Eip06w040Report;
import tw.gov.pcc.eip.domain.Meeting;
import tw.gov.pcc.eip.domain.MeetingItemAndMeetingCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DaoTable(MeetingDao.TABLE_NAME)
@Repository
public class MeetingDaoImpl extends BaseDao<Meeting> implements MeetingDao{

    private static final String ALL_COLUMNS_SQL;
    private static final String INSERT_SQL;

    static {
        ALL_COLUMNS_SQL = new StringBuilder()
                .append(" T.MEETINGID as meetingId, T.MEETINGNAME as meetingName, T.CHAIRMAN as chairman, T.MEETINGDT as meetingdt, ")
                .append(" T.MEETINGBEGIN as meetingBegin, T.MEETINGEND as meetingEnd, T.ORGANIZERID as organizerId, T .ROOMID as roomId, ")
                .append(" T.QTY as qty, T.APPLYDT as applydt, T.UPDT as updt, T.STATUS as status, T.USING as using ")
                .toString();

        INSERT_SQL = new StringBuilder()
                .append(" INSERT INTO " + TABLE_NAME + " ( ")
                .append("   MEETINGID, MEETINGNAME, CHAIRMAN, MEETINGDT, MEETINGBEGIN, MEETINGEND, ORGANIZERID, ROOMID, ")
                .append("   QTY, APPLYDT, UPDT, STATUS, USING ")
                .append(" ) ")
                .append(" VALUES(")
                .append("   :meetingid, :meetingname, :chairman, :meetingdt, :meetingbegin, :meetingend, :organizerid, :roomid, ")
                .append("   :qty, :applydt, :updt, :status, :using ")
                .append(" ) ")
                .toString();
    }
    @Override
    public int insert(Meeting data) {
        return  insert(data);
    }
//    @Override
//    public int update(Meeting data) {
////        return updateByPK(data);
//        return updateByPKForMssql(data);
//    }

    @Override
    public int update(Meeting data) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE MEETING ");
        sql.append("    SET MEETINGNAME = :meetingname, CHAIRMAN = :chairman, MEETINGDT = :meetingdt, ");
        sql.append("        MEETINGBEGIN = :meetingbegin, MEETINGEND = :meetingend, ROOMID = :roomid, ");
        sql.append("        QTY = :qty, UPDT = :updt, STATUS = :status, USING = :using ");
        sql.append("  WHERE MEETINGID = :meetingid ");

        HashMap<String, Object> params = new HashMap<>();
        params.put("meetingid", data.getMeetingId());
        params.put("meetingname", data.getMeetingName());
        params.put("chairman", data.getChairman());
        params.put("meetingdt", data.getMeetingdt());
        params.put("meetingbegin", data.getMeetingBegin());
        params.put("meetingend", data.getMeetingEnd());
        params.put("roomid", data.getRoomId());
        params.put("qty", data.getQty());
        params.put("updt", data.getUpdt());
        params.put("status", data.getStatus()); //A 申請 B 取消
        params.put("using", data.getUsing());

        return getNamedParameterJdbcTemplate().update(sql.toString(),params);
    }

    @Override
    public int delete(Meeting data) {
        return deleteByPK(data);
    }

    @Override
    public Meeting selectDataByPrimaryKey(Meeting meeting) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append(" FROM " + TABLE_NAME + " T WHERE T.MEETINGID = :meetingId");

        SqlParameterSource params = new MapSqlParameterSource("meetingId", meeting.getMeetingId());

        List<Meeting> list = getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Meeting.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<Meeting> selectAllData() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("FROM MEETING T");

        return getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(Meeting.class));
    }

    public int insertData(Meeting data){
        HashMap<String, Object> params = new HashMap<>();
        params.put("meetingid", data.getMeetingId());
        params.put("meetingname", data.getMeetingName());
        params.put("chairman", data.getChairman());
        params.put("meetingdt", data.getMeetingdt());
        params.put("meetingbegin", data.getMeetingBegin());
        params.put("meetingend", data.getMeetingEnd());
        params.put("organizerid", data.getOrganizerId());
        params.put("roomid", data.getRoomId());
        params.put("qty", data.getQty());
        params.put("applydt", data.getApplydt());
        params.put("updt", data.getUpdt());
        params.put("status", "A"); //A 申請 B 取消
        params.put("using", data.getUsing());

        return getNamedParameterJdbcTemplate().update(INSERT_SQL, params);
    }

    @Override
    public Meeting findMaxMeetingId() {
        StringBuilder sql = new StringBuilder();
//        sql.append("   SELECT max(a.MEETINGID) FROM MEETING a ");
        sql.append("  SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("    FROM MEETING T ");
        sql.append("   WHERE T.MEETINGID =( ");
        sql.append("            SELECT max(b.MEETINGID) FROM MEETING b) ");

        List<Meeting> list = getNamedParameterJdbcTemplate().query(sql.toString(), BeanPropertyRowMapper.newInstance(Meeting.class));

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<Eip06w010Case> selectDataByColumns(Eip06w010Case caseData) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("    SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("            , M.ITEMNAME FROM MEETING T ");
        sql.append(" LEFT JOIN MEETINGCODE M on T.ROOMID = M.ITEMID ");
        sql.append("     WHERE ");
        sql.append("            T.STATUS = 'A' ");
        if(StringUtils.isNotBlank(caseData.getMeetingName())){
            sql.append("   AND T.MEETINGNAME LIKE '%'+ :meetingName +'%' ");
            params.put("meetingName", caseData.getMeetingName());
        }
        if(StringUtils.isNotBlank(caseData.getChairman())){
            sql.append("   AND T.CHAIRMAN LIKE '%'+ :chairman +'%' ");
            params.put("chairman", caseData.getChairman());
        }
        if(StringUtils.isNotBlank(caseData.getMeetingdtBegin())){
            sql.append("   AND T.MEETINGDT >= :meetingdtBegin ");
            params.put("meetingdtBegin", caseData.getMeetingdtBegin());
        }
        if(StringUtils.isNotBlank(caseData.getMeetingdtEnd())){
            sql.append("   AND T.MEETINGDT <= :meetingdtEnd ");
            params.put("meetingdtEnd", caseData.getMeetingdtEnd());
        }

        if(StringUtils.isNotBlank(caseData.getMeetingdtBegin()) && StringUtils.isNotBlank(caseData.getMeetingdtEnd())){
            sql.append("   AND T.MEETINGDT BETWEEN :meetingdtBegin AND :meetingdtEnd ");
            params.put("meetingdtBegin", caseData.getMeetingdtBegin());
            params.put("meetingdtEnd", caseData.getMeetingdtEnd());
        }

        if(StringUtils.isNotBlank(caseData.getMeetingBegin())){
            sql.append("   AND T.MEETINGBEGIN >= :meetingBegin ");
            params.put("meetingBegin", caseData.getMeetingBegin());
        }

        if(StringUtils.isNotBlank(caseData.getMeetingEnd())){
            sql.append("   AND T.MEETINGEND <= :meetingEnd ");
            params.put("meetingEnd", caseData.getMeetingEnd());
        }

        if(StringUtils.isNotBlank(caseData.getMeetingBegin()) && StringUtils.isNotBlank(caseData.getMeetingEnd())){
            sql.append("   AND T.MEETINGBEGIN >= :meetingBegin ");
            sql.append("   AND T.MEETINGEND <= :meetingEnd ");
            params.put("meetingBegin", caseData.getMeetingBegin());
            params.put("meetingEnd", caseData.getMeetingEnd());
        }
        if(StringUtils.isNotBlank(caseData.getRoomId())){
            sql.append("   AND T.ROOMID = :roomId ");
            params.put("roomId", caseData.getRoomId());
        }
        if(StringUtils.isNotBlank(caseData.getOrganizerId())){
            sql.append("   AND T.ORGANIZERID LIKE '%'+ :organizerId +'%'");
            params.put("organizerId", caseData.getOrganizerId());
        }
        sql.append("  ORDER BY T.MEETINGDT, T.MEETINGBEGIN");
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip06w010Case.class));
    }

    @Override
    public List<Eip06w040Report> selectDataByMeetingdt(String meetingdt) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT d.ITEMNAME as roomName, a.MEETINGID as meetingId, a.MEETINGBEGIN as meetingBegin, ");
        sql.append("           a.MEETINGNAME as meetingName, a.CHAIRMAN as chairman, a.ORGANIZERID as organizerId, ");
        sql.append("           a.QTY as meetingQty, b.ITEMID as itemId, c.ITEMNAME as itemName, b.qty as itemQty, ");
        sql.append("           RIGHT(REPLICATE('0', 4) + SUBSTRING(a.ROOMID,2, len(a.ROOMID)), 4) as orders ");
        sql.append("      FROM MEETING a ");
        sql.append(" LEFT JOIN MEETINGITEM b on a.MEETINGID = b.MEETINGID ");
        sql.append(" LEFT JOIN MEETINGCODE c on b.ITEMID = c.ITEMID ");
        sql.append(" LEFT JOIN MEETINGCODE d on a.ROOMID = d.ITEMID ");
        sql.append("     WHERE a.MEETINGDT = :meetingdt ");
        sql.append("  ORDER BY orders, roomName, meetingBegin, meetingId, itemId ");

        SqlParameterSource params = new MapSqlParameterSource("meetingdt", meetingdt);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip06w040Report.class));
    }
}
