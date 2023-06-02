package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Systems;

import java.util.List;

/**
 * 應用系統資料 Dao
 */
@DaoTable(SystemsDao.TABLE_NAME)
@Repository
public interface SystemsDao {

    String TABLE_NAME = "SYSTEMS";

    Systems selectByKey(String sys_id);

    int insert(Systems systems);

    int updateByKey(Systems systems);

    int deleteByKey(Systems systems);

    List<Systems> selectOwnSystem(String userId);

	List<Systems> selectSystemList(String sys_id);

}