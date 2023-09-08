package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Pwc_tb_tableau_user_info;

import java.util.List;

/**
 * PWC_TB_TABLEAU_USER_INFO Dao
 */
@DaoTable(Pwc_tb_tableau_user_infoDao.TABLE_NAME)
@Repository
public interface Pwc_tb_tableau_user_infoDao {

    String TABLE_NAME = "PWC_TB_TABLEAU_USER_INFO";

    Pwc_tb_tableau_user_info selectByKey(String user_id, String dashboard_fig_id);

    int insert(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info);

    List<Pwc_tb_tableau_user_info> findUnauthoriedList();

    List<Pwc_tb_tableau_user_info> findByUserId(String userId);

    int updateByKey(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info);

    int deleteByKey(Pwc_tb_tableau_user_info pwc_tb_tableau_user_info);
}