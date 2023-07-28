package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Itemcode;

/**
 *  Dao
 */
@DaoTable(ItemcodeDao.TABLE_NAME)
@Repository
public interface ItemcodeDao {

    String TABLE_NAME = "ITEMCODE";
    
    Itemcode selectDataByPrimaryKey(Itemcode itemcode);
    
    List<Itemcode>selectAllData(Itemcode itemcode);
    
    public List<Itemcode> findByItemkind(String itemkind);

    public List<Itemcode> selectDataListByKey(Itemcode itemcode);
    
    public int insert(Itemcode itemcode);
    
    public int deleteByKey(Itemcode itemcode);
    
    public int updateByKey(Itemcode itemcode);
    
    public Itemcode getItemname(String itemno);
    
    public List<Itemcode>getStatus2List(String applyno);
}