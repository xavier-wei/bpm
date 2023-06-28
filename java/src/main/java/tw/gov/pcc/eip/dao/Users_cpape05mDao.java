package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Users_cpape05m;

/**
 * 人員資料表 Dao
 */
@DaoTable(Users_cpape05mDao.TABLE_NAME)
@Repository
public interface Users_cpape05mDao {

    public String TABLE_NAME = "USERS_CPAPE05M";

    public Users_cpape05m selectByKey(String pecard);

    public int insert(Users_cpape05m users_cpape05m);

    public int updateByKey(Users_cpape05m users_cpape05m);

    public int deleteByKey(Users_cpape05m users_cpape05m);

}