package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Msgavaildep;

/**
 * 訊息分眾表
 * 
 * @author vita
 *
 */
@DaoTable(MsgavaildepDao.TABLE_NAME)
@Repository
public interface MsgavaildepDao {

    public static final String TABLE_NAME = "MSGAVAILDEP";

    public int insert(Msgavaildep m);

    public int delete(Msgavaildep m);

    public Msgavaildep findbyfseq(String fseq, String availabledep);

    public List<Msgavaildep> findbyfseq(String fseq);

}
