package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Items;

import java.util.List;

/**
 * 選單項目資料 Dao
 */
@DaoTable(ItemsDao.TABLE_NAME)
@Repository
public interface ItemsDao {

    String TABLE_NAME = "ITEMS";

    Items selectByKey(String item_id);

    int insert(Items items);

    int updateByKey(Items items);

    int deleteByKey(Items items);

    List<Items> findItemAndChild(String item_id);
}