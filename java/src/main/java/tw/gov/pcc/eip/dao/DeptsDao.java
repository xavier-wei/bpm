package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.domain.Items;

import java.util.List;

/**
 *  Dao
 */
@DaoTable(DeptsDao.TABLE_NAME)
@Repository
public interface DeptsDao {

    String TABLE_NAME = "DEPTS";

	public List<Depts> findByDeptid(String dept_id);
	
	public int insert(Depts depts);

	public int updateByKey(Depts depts);

	public int deleteByKey(Depts depts);

}