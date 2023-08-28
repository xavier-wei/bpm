package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Eipmaildata;
import java.util.List;

/**
 * 郵件寄件資料檔 Dao
 */
@DaoTable(EipmaildataDao.TABLE_NAME)
@Repository
public interface EipmaildataDao {

    public String TABLE_NAME = "EIPMAILDATA";

    public Eipmaildata selectByKey(String mail_id);

    public int insert(Eipmaildata eipmaildata);

    public int updateByKey(Eipmaildata eipmaildata);

    public int deleteByKey(Eipmaildata eipmaildata);

    String getMail_id();

    List<Eipmaildata> getListByIsMailedIsNull();
}