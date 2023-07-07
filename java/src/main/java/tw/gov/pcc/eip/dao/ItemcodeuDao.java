package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Itemcodeu;

/**
 * ITEMCODEU Dao
 */
@DaoTable(ItemcodeuDao.TABLE_NAME)
@Repository
public interface ItemcodeuDao {

    public String TABLE_NAME = "ITEMCODEU";

    public Itemcodeu selectByKey(String itemkind, String itemno, String u_date, String u_time, String u_user);

    public int insert(Itemcodeu itemcodeu);

    public int updateByKey(Itemcodeu itemcodeu);

    public int deleteByKey(Itemcodeu itemcodeu);

}