package tw.gov.pcc.common.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.domain.Roitem;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;

import tw.gov.pcc.eip.domain.Joblog;

import java.util.List;

/**
 * 批次執行QC記錄檔 DAO
 *
 * @author tunhao.hsu
 */
@DaoTable(RoitemDao.TABLE_NAME)
@Repository
public interface RoitemDao {
    public String TABLE_NAME = "ROITEM";
    public int deleteByKey(Roitem roitem);

    public int insert(Roitem roitem);

    public List<Roitem> count(String time) ;

    public List<Eip08w060Case> quaryItemId(Eip08w060Case caseData) ;

    public int delete(Eip08w060Case caseData);

    public int updateRoitem(Eip08w060Case caseData);

    public List<Eip08w060Case> distinctItemId(Eip08w060Case caseData) ;

}
