package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.msg.cases.Eip01w020PageCase;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;

/**
 * 訊息上稿
 * 
 * @author vita
 *
 */
@DaoTable(MsgdataDao.TABLE_NAME)
@Repository
public class MsgdataDaoImpl extends BaseDao<Msgdata> implements MsgdataDao {

    @Override
    public int insert(Msgdata m) {
        return super.insert(m);
    }

    @Override
    public int update(Msgdata m, String fseq) {
        m.setFseq(fseq);
        return super.updateByPK(m);
    }

    @Override
    public int delete(Msgdata m) {
        return super.deleteByPK(m);
    }

    @Override
    public Msgdata findbyfseq(String fseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM " + TABLE_NAME);
        sql.append("  WHERE fseq = :fseq ");

        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Msgdata.class)).stream().findFirst()
                .get();
//        Msgdata m = new Msgdata();
//        m.setFseq(fseq);
//        return this.selectDataByPrimaryKey(m);
    }

    @Override
    public Msgdata selectDataByPrimaryKey(Msgdata t) {
        return selectByPK(t);
    }

    @Override
    public List<Msgdata> findbyCreatidPagetype(String creatid, String pagetype, String subject, String status,
            String attributype) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append("   FROM " + TABLE_NAME + " A ");
        sql.append("  WHERE A.STATUS IN ('0','1','4') ");
        if (StringUtils.isNotEmpty(creatid)) {
            sql.append("    AND A.CREATID = isnull( :creatid , A.CREATID) ");
        }
        if (StringUtils.isNotEmpty(pagetype)) {
            sql.append("    AND A.PAGETYPE = isnull(:pagetype,  A.PAGETYPE) ");
        }
        if (StringUtils.isNotEmpty(subject)) {
            sql.append("    AND A.SUBJECT like '%' + isnull(:subject , A.SUBJECT) + '%' ");
        }
        if (StringUtils.isNotEmpty(status)) {
            sql.append("    AND A.STATUS = isnull(:status , A.STATUS) ");
        }
        if (StringUtils.isNotEmpty(attributype)) {
            sql.append("    AND A.ATTRIBUTYPE = isnull(:attributype , A.ATTRIBUTYPE) ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("creatid", creatid);
        params.put("pagetype", pagetype);
        params.put("subject", subject);
        params.put("status", status);
        params.put("attributype", attributype);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdata.class));
    }

    @Override
    public int updateStatus(List<String> fseq, String status) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE MSGDATA SET STATUS = :status ");
        sql.append(" WHERE FSEQ IN ( :fseq ) ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        params.put("status", status);
        return getNamedParameterJdbcTemplate().update(sql.toString(), params);
    }

    @Override
    public String getNextFseq() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT NEXT VALUE FOR MSGDATASEQ ");
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), new HashMap<String, String>(),
                String.class);
    }

    @Override
    public List<Eip01w020PageCase> getEip01w020DataList(String msgtype, String attributype, String subject,
            String contactunit, String creatid, String updid, String releasedts, String releasedte) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT B.DEPT_ID, B.DEPT_NAME AS DEPTNAME, COUNT(A.FSEQ) AS COUNT ");
        sql.append("   FROM MSGDATA A, DEPTS B ");
        sql.append("  WHERE A.CONTACTUNIT = B.DEPT_ID");
        sql.append("    AND B.IS_VALID = 'Y' ");
        sql.append("    AND A.MSGTYPE = ISNULL(:msgtype, A.MSGTYPE) ");
        sql.append("    AND A.ATTRIBUTYPE = ISNULL(:attributype, A.ATTRIBUTYPE) ");
        sql.append("    AND A.SUBJECT LIKE '%' + ISNULL(:subject, A.SUBJECT)+ '%' ");
        sql.append("    AND A.CONTACTUNIT = ISNULL(:contactunit, A.CONTACTUNIT) ");
        sql.append("    AND A.CREATID = ISNULL(:creatid, A.CREATID) ");
        sql.append("    AND A.UPDID = ISNULL(:updid, A.UPDID) ");
        sql.append(
                "    AND A.RELEASEDT >= ISNULL(:releasedts, A.RELEASEDT) AND A.RELEASEDT <= ISNULL(:releasedte, A.RELEASEDT) ");
        sql.append("  GROUP BY DEPT_ID, DEPT_NAME ");
        sql.append("  ORDER BY DEPT_ID ");
        Map<String, Object> params = new HashMap<>();
        params.put("msgtype", msgtype);
        params.put("attributype", attributype);
        params.put("subject", subject);
        params.put("contactunit", contactunit);
        params.put("creatid", creatid);
        params.put("updid", updid);
        params.put("releasedts", releasedts);
        params.put("releasedte", releasedte);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip01w020PageCase.class));
    }

    @Override
    public List<Eip01wPopCase> getEip01w030DataList(String deptId, String msgtype, String subject) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT A.FSEQ, ");
        sql.append("        A.SUBJECT, ");
        sql.append("        C.CODENAME MSGTYPE, ");
        sql.append("        STUFF(FORMAT(CONVERT(DATE, RELEASEDT), 'yyyy/MM/dd'), 1, 4, DATEPART(YEAR, RELEASEDT) - 1911) RELEASEDT, ");
        sql.append("        B.DEPT_NAME CONTACTUNIT ");
        sql.append("   FROM MSGDATA A , ");
        sql.append("        DEPTS B, ");
        sql.append("        EIPCODE C ");
        sql.append("  WHERE A.ATTRIBUTYPE = '1' ");
        sql.append("    AND A.STATUS = '4' ");
        sql.append("    AND A.CONTACTUNIT = B.DEPT_ID  ");
        sql.append("    AND B.IS_VALID = 'Y' ");
        sql.append("    AND C.CODEKIND = 'MESSAGETYPE' ");
        sql.append("    AND C.SCODEKIND = '1' ");
        sql.append("    AND C.CODENO = A.MSGTYPE  ");
        sql.append("    AND A.MSGTYPE = isnull(:msgtype, A.MSGTYPE) ");
        sql.append("    AND A.SUBJECT like '%' + isnull(:subject, A.SUBJECT) + '%' ");
        sql.append("    AND EXISTS (SELECT FSEQ ");
        sql.append("                  FROM MSGAVAILDEP D ");
        sql.append("                 WHERE (CASE WHEN D.AVAILABLEDEP  = '00'");
        sql.append("                             THEN :deptId ELSE D.AVAILABLEDEP");
        sql.append("                        END) = :deptId)");
        sql.append("  ORDER BY A.SHOWORDER, RELEASEDT DESC ");
        Map<String, Object> params = new HashMap<>();
        params.put("deptId", deptId);
        params.put("msgtype", msgtype);
        params.put("subject", subject);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip01wPopCase.class));
    }

    @Override
    public Eip01wPopCase getEip01wDetail(String fseq, String scodekind) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT B.CODENAME MSGTYPE, "); // 屬性下的訊息類別
        sql.append("        A.FSEQ, ");
        sql.append("        A.SUBJECT, "); // 主旨
        sql.append("        A.MCONTENT, "); // 內文
        sql.append("        C.DEPT_NAME CONTACTUNIT, "); // 聯絡單位
        sql.append("        STUFF(FORMAT(ISNULL(A.UPDDT, A.CREATDT), 'yyyy/MM/dd'), 1, 4, YEAR(ISNULL(A.UPDDT, A.CREATDT)) - 1911) UPDDT, ");
        sql.append("        E.USER_NAME CONTACTPERSON, "); // 聯絡人
        sql.append("        A.CONTACTTEL "); // 連絡電話
        sql.append("   FROM MSGDATA A, ");
        sql.append("        EIPCODE B, ");
        sql.append("        DEPTS C, ");
        sql.append("        USERS E ");
        sql.append("  WHERE A.FSEQ = :fseq ");
        sql.append("    AND B.CODEKIND = 'MESSAGETYPE' ");
        sql.append("    AND B.SCODEKIND = :scodekind "); // 1:公告事項4:下載專區5:輿情專區
        sql.append("    AND A.MSGTYPE = B.CODENO ");
        sql.append("    AND A.CONTACTUNIT = C.DEPT_ID ");
        sql.append("    AND C.IS_VALID = 'Y' ");
        sql.append("    AND A.CONTACTPERSON = E.USER_ID ");
        sql.append("    AND E.ACNT_IS_VALID = 'Y' ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        params.put("scodekind", scodekind);
        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip01wPopCase.class)).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Msgdata> getEip01w040byPath(String dept, String path) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT C.FSEQ, ");
        sql.append("        C.SUBJECT, ");
        sql.append("        ISNULL(C.UPDDT, C.CREATDT) UPDDT ");
        sql.append("   FROM MSGDATA C ");
        sql.append("  WHERE C.FSEQ IN ");
        sql.append("         (SELECT A.FSEQ   ");
        sql.append("            FROM MSGDEPOSIT A, ");
        sql.append("                 MSGAVAILDEP B ");
        sql.append("           WHERE A.FSEQ = B.FSEQ ");
        sql.append("             AND B.AVAILABLEDEP = :dept  ");
        sql.append("             AND C.ATTRIBUTYPE  = '4') ");
        sql.append("    AND C.INDIR = :path ");
        sql.append("    AND C.STATUS = '4' ");
        sql.append("    AND C.ATTRIBUTYPE = '4' ");
        sql.append("  ORDER BY C.SHOWORDER, C.CREATDT DESC; ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);
        params.put("path", path);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdata.class));
    }

    @Override
    public List<Msgdata> getEip01w040byKeyword(String dept, String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT C.FSEQ, ");
        sql.append("        C.SUBJECT, ");
        sql.append("        ISNULL(C.UPDDT, C.CREATDT) UPDDT ");
        sql.append("   FROM MSGDATA C ");
        sql.append("  WHERE C.FSEQ IN ");
        sql.append("        (SELECT A.FSEQ   ");
        sql.append("           FROM MSGDEPOSIT A, ");
        sql.append("                MSGAVAILDEP B ");
        sql.append("          WHERE A.FSEQ = B.FSEQ ");
        sql.append("            AND B.AVAILABLEDEP = :dept  ");
        sql.append("            AND C.ATTRIBUTYPE  = '4') ");
        sql.append("    AND C.STATUS = '4' ");
        sql.append("    AND C.ATTRIBUTYPE = '4' ");
        sql.append("    AND (C.SUBJECT LIKE '%' + :keyword + '%' ");
        sql.append("     OR C.MCONTENT LIKE '%' + :keyword + '%') ");
        sql.append("  ORDER BY C.SHOWORDER, C.CREATDT DESC; ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);
        params.put("keyword", keyword);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdata.class));
    }

    @Override
    public List<Eip01wPopCase> getEip01w050DataList(String dept) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT A.FSEQ, ");
        sql.append("        A.SUBJECT, ");
        sql.append(
                "        CAST(CAST(DATEPART(YEAR, A.UPDDT) AS NUMERIC(4))-1911 AS VARCHAR(3))+'/' + CONVERT(CHAR(5), A.UPDDT, 1) UPDDT ");
        sql.append("   FROM MSGDATA A ");
        sql.append("  WHERE A.ATTRIBUTYPE = '5' ");
        sql.append("    AND A.STATUS = '4' ");
        sql.append("    AND EXISTS (SELECT 1 ");
        sql.append("                  FROM MSGAVAILDEP B ");
        sql.append("                 WHERE A.FSEQ = B.FSEQ  ");
        sql.append("                   AND A.ATTRIBUTYPE = '5' ");
        sql.append("                   AND B.AVAILABLEDEP = :dept ");
        sql.append("  ) ");
        sql.append(" ORDER BY A.SHOWORDER, RELEASEDT DESC ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip01wPopCase.class));
    }

    @Override
    public List<Msgdata> getStatus4Mcontent(String attr, String contactunit) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT FSEQ, ");
        sql.append("        SUBJECT, ");
        sql.append("        MCONTENT ");
        sql.append("   FROM MSGDATA A ");
        sql.append("  WHERE ATTRIBUTYPE = :attr ");
        sql.append("    AND A.STATUS = '4' ");
        sql.append("    AND CONTACTUNIT = :contactunit ");
        Map<String, Object> params = new HashMap<>();
        params.put("attr", attr);
        params.put("contactunit", contactunit);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdata.class));
    }

    @Override
    public int getEffectiveCount(String attr, String contactunit) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(1) ");
        sql.append("   FROM MSGDATA ");
        sql.append("  WHERE ATTRIBUTYPE = :attr ");
        sql.append("    AND STATUS = '4' ");
        sql.append("    AND CONTACTUNIT = :contactunit ");
        sql.append("    AND RELEASEDT <= FORMAT(GETDATE(), 'yyyyMMdd') ");
        sql.append("    AND OFFTIME >= FORMAT(GETDATE(), 'yyyyMMdd') ");
        Map<String, Object> params = new HashMap<>();
        params.put("attr", attr);
        params.put("contactunit", contactunit);
        return Optional
                .ofNullable(getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, Integer.class))
                .orElse(0);
    }

}
