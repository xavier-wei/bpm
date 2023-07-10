package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.msg.cases.Eip01w020PageCase;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case.Detail;
import tw.gov.pcc.eip.msg.cases.Eip01w050Case;

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
    public List<Msgdata> findbyCreatidPagetype(String creatid, String pagetype, String subject, String status) {
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
        Map<String, Object> params = new HashMap<>();
        params.put("creatid", creatid);
        params.put("pagetype", pagetype);
        params.put("subject", subject);
        params.put("status", status);
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
        sql.append(" SELECT B.CODENO, B.CODENAME AS DEPTNAME, COUNT(A.FSEQ) AS COUNT ");
        sql.append("   FROM MSGDATA A, EIPCODE B ");
        sql.append("  WHERE A.CONTACTUNIT = B.CODENO ");
        sql.append("    AND B.CODEKIND = 'DEPT' ");
        sql.append("    AND A.MSGTYPE = ISNULL(:msgtype ,A.MSGTYPE) ");
        sql.append("    AND A.ATTRIBUTYPE = ISNULL(:attributype ,A. ATTRIBUTYPE) ");
        sql.append("    AND A.SUBJECT LIKE '%' + ISNULL(:subject , A.SUBJECT)+ '%' ");
        sql.append("    AND A.CONTACTUNIT = ISNULL(:contactunit,A.CONTACTUNIT) ");
        sql.append("    AND A.CREATID = ISNULL(:creatid, A.CREATID) ");
        sql.append("    AND A.UPDID = ISNULL(:updid,A.UPDID) ");
        sql.append(
                "    AND A.RELEASEDT >= ISNULL(:releasedts, A.RELEASEDT) AND  A.RELEASEDT <= ISNULL(:releasedte, A.RELEASEDT) ");
        sql.append("  GROUP BY CODENO, CODENAME ");
        sql.append("  ORDER BY CODENO; ");
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
    public List<Detail> getEip01w030LatestDataList() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT A.FSEQ, A.SUBJECT, C.CODENAME  MSGTYPE,  ");
        sql.append("         RELEASEDT,  ");
        sql.append("        B.CODENAME as CONTACTUNIT ");
        sql.append("   FROM MSGDATA A, ");
        sql.append("        EIPCODE B, ");
        sql.append("        EIPCODE C ");
        sql.append("  WHERE A.ATTRIBUTYPE = '1' ");
        sql.append("    AND A.STATUS = '4' ");
        sql.append("    AND B.CODEKIND = 'DEPT' ");
        sql.append("    AND A.CONTACTUNIT = B.CODENO ");
        sql.append("    AND C.CODEKIND = 'MESSAGETYPE' ");
        sql.append("    AND C.SCODEKIND = '1' ");
        sql.append("    AND C.CODENO = A.MSGTYPE ");
        return getNamedParameterJdbcTemplate().query(sql.toString(),
                BeanPropertyRowMapper.newInstance(Eip01w030Case.Detail.class));
    }

    @Override
    public List<Detail> getEip01w030DataList(String msgtype, String subject) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT A.FSEQ, ");
        sql.append("        A.SUBJECT, ");
        sql.append("        C.CODENAME MSGTYPE,  ");
        sql.append("        RELEASEDT,   ");
        sql.append("        B.CODENAME as CONTACTUNIT ");
        sql.append("   FROM MSGDATA A , ");
        sql.append("        EIPCODE B, ");
        sql.append("        EIPCODE C ");
        sql.append("  WHERE A.ATTRIBUTYPE = '1' ");
        sql.append("    AND A.STATUS = '4' ");
        sql.append("    AND B.CODEKIND = 'DEPT' ");
        sql.append("    AND A.CONTACTUNIT = B.CODENO  ");
        sql.append("    AND C.CODEKIND = 'MESSAGETYPE' ");
        sql.append("    AND C.SCODEKIND = '1' ");
        sql.append("    AND C.CODENO = A.MSGTYPE  ");
        sql.append("    AND A.MSGTYPE = isnull(:msgtype, A.MSGTYPE) ");
        sql.append("    AND A.SUBJECT like '%' + isnull(:subject, A.SUBJECT) + '%' ");
        sql.append("  ORDER BY A.SHOWORDER, RELEASEDT DESC ");
        Map<String, Object> params = new HashMap<>();
        params.put("msgtype", msgtype);
        params.put("subject", subject);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip01w030Case.Detail.class));
    }

    @Override
    public Eip01w030Case.Detail getEip01w030Detail(String fseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT B.CODENAME MSGTYPE, ");
        sql.append("        C.CODENAME CONTACTUNIT, ");
        sql.append("        A.FSEQ, ");
        sql.append("        A.SUBJECT, ");
        sql.append("        A.MCONTENT, ");
        sql.append(
                "        CAST(CAST(DATEPART(YEAR, A.UPDDT) AS NUMERIC(4))-1911 AS VARCHAR(3))+'/' + CONVERT(CHAR(5), A.UPDDT, 1) UPDDT, ");
        sql.append("        A.CONTACTPERSON, ");
        sql.append("        A.CONTACTTEL ");
        sql.append("   FROM MSGDATA A, ");
        sql.append("        EIPCODE B, ");
        sql.append("        EIPCODE C ");
        sql.append("  WHERE A.FSEQ = :fseq ");
        sql.append("    AND B.CODEKIND = 'MESSAGETYPE' ");
        sql.append("    AND B.SCODEKIND = '1' ");
        sql.append("    AND B.CODENO = A.MSGTYPE ");
        sql.append("    AND C.CODEKIND = 'DEPT' ");
        sql.append("    AND C.CODENO = A.CONTACTUNIT ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip01w030Case.Detail.class)).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Eip01w050Case.Detail> getEip01w050DataList(String dept) {
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
//        sql.append("                   AND B.AVAILABLEDEP = :dept ");
        sql.append("  ) ");
        sql.append(" ORDER BY A.SHOWORDER, RELEASEDT DESC ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Eip01w050Case.Detail.class));
    }

    @Override
    public Eip01w050Case.Detail getEip01w050Detail(String fseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT A.SUBJECT, ");
        sql.append("        A.MCONTENT, ");
        sql.append("        C.CODENAME DEPNAME, ");
        sql.append("        A.CONTACTPERSON, ");
        sql.append("        A.CONTACTTEL, ");
        sql.append("        B.CODENAME , ");
        sql.append(
                "        CAST(CAST(DATEPART(YEAR, A.UPDDT) AS NUMERIC(4))-1911 AS VARCHAR(3))+'/' + CONVERT(CHAR(5), A.UPDDT, 1) UPDDT ");
        sql.append("   FROM MSGDATA A, ");
        sql.append("        EIPCODE B, ");
        sql.append("        EIPCODE C ");
        sql.append("  WHERE A.FSEQ = :fseq ");
        sql.append("        AND B.CODEKIND = 'MESSAGETYPE' ");
        sql.append("        AND B.SCODEKIND = '5' ");
        sql.append("        AND B.CODENO = A.MSGTYPE ");
        sql.append("        AND C.CODEKIND = 'DEPT' ");
        sql.append("        AND C.CODENO = A.CONTACTUNIT ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        return getNamedParameterJdbcTemplate()
                .query(sql.toString(), params, BeanPropertyRowMapper.newInstance(Eip01w050Case.Detail.class)).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Msgdata> getMcontentWithStatus4(String attr, String dept) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT FSEQ, ");
        sql.append("        MCONTENT ");
        sql.append("   FROM MSGDATA A ");
        sql.append("  WHERE ATTRIBUTYPE = :attr ");
        sql.append("    AND A.STATUS = '4' ");
        sql.append("    AND CONTACTUNIT = :dept ");
        Map<String, Object> params = new HashMap<>();
        params.put("attr", attr);
        params.put("dept", dept);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdata.class));
    }

    @Override
    public List<Msgdata> getbyDefaultPath(String dept, String defaultPath) {
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
        sql.append("    AND C.INDIR = :defaultPath ");
        sql.append("    AND C.STATUS = '4' ");
        sql.append("    AND C.ATTRIBUTYPE = '4' ");
        sql.append("  ORDER BY C.SHOWORDER, C.CREATDT DESC; ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);
        params.put("defaultPath", defaultPath);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdata.class));
    }
    
    @Override
    public List<Msgdata> getbyKeyword(String dept, String keyword) {
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


}
