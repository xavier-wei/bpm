package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.User_profile;

/**
 * 使用者設定 Dao
 */
@DaoTable(User_profileDao.TABLE_NAME)
@Repository
public interface User_profileDao {

    String TABLE_NAME = "USER_PROFILE";

    User_profile selectByKey(String user_id);

    int insert(User_profile user_profile);

    int updateByKey(User_profile user_profile);

    int deleteByKey(User_profile user_profile);

}