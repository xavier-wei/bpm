package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.TableauUserInfo;

import java.util.List;


@DaoTable(TableauUserInfoDao.TABLE_NAME)
@Repository
public interface TableauUserInfoDao {

    String TABLE_NAME = "PWC_TB_TABLEAU_USER_INFO";

    List<TableauUserInfo> selectByUserId(String user_id);
}