package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Items;

import java.util.List;

/**
 *  Dao
 */
@DaoTable(ItemcodeDao.TABLE_NAME)
@Repository
public interface ItemcodeDao {

    String TABLE_NAME = "ITEMCODE";

}