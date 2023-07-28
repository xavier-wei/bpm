package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Applyitem;

/**
 * 領物單 Dao
 */
@DaoTable(ApplyitemDao.TABLE_NAME)
@Repository
public interface ApplyitemDao {

    String TABLE_NAME = "APPLYITEM";

    Applyitem selectByKey(String applyno,String seqno,String itemkind,String itemno);

    int insert(Applyitem applyitem);

    int updateByKey(Applyitem applyitem);

    int deleteByKey(Applyitem applyitem);
    
    Applyitem getApplyno();
    
    List<Applyitem> selectByApplyUserAndApply_deptAndapplyDate(String apply_user,String apply_dept,String apply_date);

    List<Applyitem> selectByApplyno(String applyno);
    
    List<Applyitem> selectByApply_dateAndProcess_status(String apply_dateStart, String apply_dateEnd,String process_status);

    List<Applyitem> selectReconfirm_mkNData(List<String>applynos);
    
    List<Applyitem> selectApplyItemReportByUnit(String applyYearMonth);

    List<Applyitem> selectApplyItemReportByItem(String applyYearMonth);

    String getApplynoSeq();
    
    void updateSequence();
}