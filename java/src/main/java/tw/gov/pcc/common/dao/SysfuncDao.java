package tw.gov.pcc.common.dao;

import java.util.List;
import java.util.Map;

import tw.gov.pcc.common.domain.SystemFunction;

public interface SysfuncDao {

    /**
     * 依應用系統代號取得系統功能清單 for CAS
     *
     * @return 內含 <code>tw.gov.pcc.common.domain.SystemFunction</code> 物件的 List
     */
    List<SystemFunction> selectCasSysFuncBySystemId();

    
}
