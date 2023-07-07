package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Applyitem;

/**
 * 領物單 Dao
 */
@DaoTable(ApplyitemDao.TABLE_NAME)
@Repository
public interface ApplyitemDao {

    String TABLE_NAME = "APPLYITEM";

    Applyitem selectByKey(String applyno,String seqno,String itemkind,String itemno);

    int insert(Applyitem applyitem);

    int updateByKey(Applyitem applyitem);

    int deleteByKey(Applyitem applyitem);
    
    Applyitem getApplyno();

}