package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MsgavaildepDao;
import tw.gov.pcc.eip.domain.Msgavaildep;

/**
 * 訊息分眾表
 * 
 * @author vita
 *
 */
@DaoTable(MsgavaildepDao.TABLE_NAME)
@Repository
public class MsgavaildepDaoImpl extends BaseDao<Msgavaildep> implements MsgavaildepDao {

    @Override
    public int insert(Msgavaildep m) {
        return super.insert(m);
    }

    @Override
    public int delete(Msgavaildep m) {
        return super.deleteByPK(m);
    }

    @Override
    public Msgavaildep findbyfseq(String fseq, String availabledep) {
        Msgavaildep m = new Msgavaildep();
        m.setFseq(fseq);
        m.setAvailabledep(availabledep);
        return this.selectDataByPrimaryKey(m);
    }

    @Override
    public Msgavaildep selectDataByPrimaryKey(Msgavaildep t) {
        return super.selectByPK(t);
    }

    @Override
    public List<Msgavaildep> findbyfseq(String fseq) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append("   FROM " + TABLE_NAME + " A ");
        sql.append("  WHERE A.fseq = :fseq ");
        Map<String, Object> params = new HashMap<>();
        params.put("fseq", fseq);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgavaildep.class));
    }

}
