package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.domain.Msgdeposit;

/**
 * 訊息存放檔案表
 * 
 * @author vita
 *
 */
@DaoTable(MsgdepositDao.TABLE_NAME)
@Repository
public class MsgdepositDaoImpl extends BaseDao<Msgdeposit> implements MsgdepositDao {

    @Override
    public int insert(Msgdeposit m) {
        return super.insert(m);
    }

    @Override
    public int update(Msgdeposit m, String fseq, String seq, String filetype) {
        return super.updateByPK(m);
    }

    @Override
    public int delete(Msgdeposit m) {
        return super.deleteByPK(m);
    }

    @Override
    public Msgdeposit findbyPk(String fseq, String seq) {
        Msgdeposit m = new Msgdeposit();
        m.setFseq(fseq);
        m.setSeq(seq);
        return this.selectDataByPrimaryKey(m);
    }

    @Override
    public Msgdeposit selectDataByPrimaryKey(Msgdeposit t) {
        return selectByPK(t);
    }

    @Override
    public List<Msgdeposit> findbyfseq(List<String> fseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append("   FROM MSGDEPOSIT ");
        sql.append("  WHERE FSEQ in ( :fseq )");
        sql.append("  ORDER BY SEQ; ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdeposit.class));
    }

    @Override
    public List<Msgdeposit> findbyFseqSeq(String fseq, List<String> seq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append("   FROM MSGDEPOSIT ");
        sql.append("  WHERE FSEQ = :fseq ");
        sql.append(" AND SEQ IN ( :seq ) ");
        sql.append("  ORDER BY SEQ; ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        params.put("seq", seq);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdeposit.class));
    }
}
