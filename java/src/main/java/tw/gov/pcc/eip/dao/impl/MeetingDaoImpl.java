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
        sql.append("            , M.ITEMNAME  ");
        sql.append("            , (SELECT count(1) FROM MEETINGITEM MI WHERE T.MEETINGID = MI.MEETINGID AND MI.ITEMID LIKE 'A%') as orderNum");
        sql.append("            , substring(T.ROOMID, 4, 2) as orders ");
        sql.append("            , itemList ");
        sql.append("      FROM MEETING T ");
        sql.append(" LEFT JOIN MEETINGCODE M on T.ROOMID = M.ITEMID ");

        //20231027 維護畫面欄位新增需求
        sql.append(" LEFT JOIN ( SELECT MI.MEETINGID, ");
        sql.append("                    STRING_AGG(MC.ITEMNAME,',') itemList ");
        sql.append("               FROM MEETINGITEM MI ");
        sql.append("         INNER JOIN MEETINGCODE MC on MI.ITEMID = MC.ITEMID ");
        sql.append("              WHERE MI.ITEMID LIKE 'B%' ");
        sql.append("           GROUP BY MI.MEETINGID ) subquery on T.MEETINGID = subquery.MEETINGID ");

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
        if(caseData.getUserIdList() != null && caseData.getUserIdList().size() > 0){
            sql.append("   AND T.ORGANIZERID in (:userIdList)");
            params.put("userIdList", caseData.getUserIdList());
        }
        sql.append("  ORDER BY orders, T.MEETINGDT, T.MEETINGBEGIN");
        return getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip06w010Case.class));
    }

    @Override
    public List<Eip06w040Report> selectValidMeetingByMeetingdt(String meetingdt) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT d.ITEMNAME as roomName, a.MEETINGID as meetingId, a.MEETINGBEGIN as meetingBegin, ");
        sql.append("           a.MEETINGNAME as meetingName, a.CHAIRMAN as chairman, a.ORGANIZERID as organizerId, ");
        sql.append("           a.QTY as meetingQty, b.ITEMID as itemId, c.ITEMNAME as itemName, b.qty as itemQty, ");
        sql.append("           RIGHT(a.ROOMID, 2) as orders ");
        sql.append("      FROM MEETING a ");
        sql.append(" LEFT JOIN MEETINGITEM b on a.MEETINGID = b.MEETINGID ");
        sql.append(" LEFT JOIN MEETINGCODE c on b.ITEMID = c.ITEMID ");
        sql.append(" LEFT JOIN MEETINGCODE d on a.ROOMID = d.ITEMID ");
        sql.append("     WHERE a.MEETINGDT = :meetingdt ");
        sql.append("       AND a.STATUS = 'A' ");
        sql.append("  ORDER BY orders, roomName, meetingBegin, meetingId, itemId ");

        SqlParameterSource params = new MapSqlParameterSource("meetingdt", meetingdt);

        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip06w040Report.class));
    }

    @Override
    public List<Meeting> findExistedMeeting(List<String> dateList, String using, String roomId) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("      FROM MEETING T ");
        sql.append("     WHERE T.MEETINGDT  in (:dateList) ");
        sql.append("       AND T.ROOMID = :roomId     ");
        sql.append("       AND (SELECT dbo.UFN_CHECK(USING, :using) AS RTN) = 'Y'     ");
        sql.append("  ORDER BY T.MEETINGID     ");

        SqlParameterSource params = new MapSqlParameterSource("dateList", dateList).addValue("using", using).addValue("roomId", roomId);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Meeting.class));
    }

    @Override
    public List<Meeting> findDueMeeting(String dueDate) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(ALL_COLUMNS_SQL);
        sql.append("      FROM MEETING T ");
        sql.append("     WHERE T.MEETINGDT  <= :dueDate ");

        SqlParameterSource params = new MapSqlParameterSource("dueDate", dueDate);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Meeting.class));
    }


    @Override
    public void deleteDataByMeetingId(List<Integer> meetingId) {
        StringBuilder sql = new StringBuilder();
        sql.append("    DELETE FROM MEETING  ");
        sql.append("     WHERE MEETINGID in (:meetingId) ");

        SqlParameterSource params = new MapSqlParameterSource("meetingId", meetingId);
        getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }
}
