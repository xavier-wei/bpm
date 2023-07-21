package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Depts;

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
	
	public Depts findByPk(String dept_id);
	/**
	 * 取得訊息上稿 分眾 下拉選單內容
	 * @return
	 */
	public List<Depts> getEip01wDepts();

    /**
     * 取得登入者部門可檢視之單位簡介或業務資訊的初始下拉選單
     * 
     * @param attr   6:單位簡介 7:業務資訊
     * @param deptId 登入者部門
     * @return
     */
    public List<Depts> getRelevantDeptByAttr(String attr, String deptId);
}